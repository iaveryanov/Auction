package com.modernwebcourse.auction;

import com.modernwebcourse.auction.dao.IBidDao;
import com.modernwebcourse.auction.domain.Bid;
import com.modernwebcourse.auction.domain.User;
import com.modernwebcourse.auction.emulator.dao.BidDao;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BidCommand {

    private final Bid bid;

    private final IBidDao bidDao = new BidDao();
    private final EmailSender emailSender = new EmailSender();

    public enum BidResult {
        ERR_AUCTION_EXPIRED,
        ERR_ALL_SOLD,
        ERR_LESS_MIN_PRICE, // Sorry_email
        ERR_NOT_OVERBIDDED,
        OK_GREATER_RESERVED_PRICE, // Winner_email
        OK_OVERBIDDED, // Overbidded_email
        OK_FIRST_BID,
    }

    public BidCommand(Bid bid) {
        if (bid == null) {
            throw new IllegalArgumentException("bid is null");
        }
        this.bid = bid;
    }

    // here all business logic:
    // if amount < minimalPrice then send Sorry_email
    // if amount >= reservedPrice then send Winner_email and close auction for this product
    // get all bids for this product, if amount > currentMaxAmountBid
    // then send Overbidded_email to all users with flag getOverbidNotifications=true
    public BidResult process() {
        if (LocalDateTime.now().isAfter(bid.product.auctionEndTime)) {
            // auction is off by endTime
//            System.out.println("auction is off by endTime");
            return BidResult.ERR_AUCTION_EXPIRED;
        }

        if (bid.product.quantity == 0) {
//            System.out.println("all sold for " + bid.product.title);
            // everything is sold! auction if off by winner?
            return BidResult.ERR_ALL_SOLD;
        }

        if (bid.amount.get() < bid.product.minimalPrice.get()) {
            // if amount < minimalPrice then send Sorry_email
            emailSender.send(bid.user, EmailSender.EmailType.Sorry);
            return BidResult.ERR_LESS_MIN_PRICE;
        }

        // if amount >= reservedPrice then send Winner_email and close auction for this product
        if (bid.amount.get() >= bid.product.reservedPrice.get()) {
            bid.isWinning = true; // save in db
            bidDao.insert(bid);
            bid.product.quantity = bid.product.quantity - bid.desiredQuantity;
            System.out.println("send Winner_email (" + bid.product.quantity + ")");
            emailSender.send(bid.user, EmailSender.EmailType.Winning);
            return BidResult.OK_GREATER_RESERVED_PRICE;
        }

        List<Bid> bidList = bidDao.selectByProductId(bid.product.id);
        Optional<Bid> leadBidOpt = getLeadBid(bidList);

        if (leadBidOpt.isPresent()) {
            // non-first bid
            if (bid.amount.get() > leadBidOpt.get().amount.get()) {
                bidDao.insert(bid);
                // create set users for Overbidded_email with flag getOverbidNotifications=true
                getNotificationSet(bidList)
                        .stream()
                        .filter((u) -> !u.equals(bid.user)) // don't send himself
                        .forEach((u) -> emailSender.send(u, EmailSender.EmailType.Overbidded));
                return BidResult.OK_OVERBIDDED;
            } else {
                // less then current leading bid
                return BidResult.ERR_NOT_OVERBIDDED;
            }
        } else {
            // first bid, no any Overbidded_email
            bidDao.insert(bid);
            return BidResult.OK_FIRST_BID;
        }
    }

    private Optional<Bid> getLeadBid(List<Bid> bidList) {
        return bidList
                .stream()
                .max((o1, o2) -> o1.compareTo(o2));
    }

    // get all bids for this product, if amount > currentMaxAmountBid
    // then send Overbidded_email to all users with flag getOverbidNotifications=true
    private Set<User> getNotificationSet(List<Bid> bidList) {
        return bidList
                .stream()
                .filter((b) -> b.user.getOverbidNotifications // send only with flag
                        && b.amount.get() < b.product.reservedPrice.get()) // don't see to winning
                .map((b) -> b.user)
                .collect(Collectors.toSet());
    }
}
