package com.modernwebcourse.auction.emulator.dao;

import com.modernwebcourse.auction.dao.IBidDao;
import com.modernwebcourse.auction.domain.Bid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class BidDao implements IBidDao {

    public BidDao() {
    }

    @Override
    public void insert(Bid bid) {
        DB.BIDS.put(bid.id, bid);
    }

    @Override
    public Optional<Bid> selectByBidId(int bidId) {
        return Optional.ofNullable(DB.BIDS.get(bidId));
    }

    @Override
    public List<Bid> selectByProductId(int productId) {
        return DB.BIDS.values()
                .stream()
                .filter((b) -> b.product.id ==  productId)
                .collect(Collectors.toList());
    }
}
