package com.modernwebcourse.auction;

import com.modernwebcourse.auction.domain.User;

public class EmailSender {

    public enum EmailType {
        Winning, // If a bid is greater or equal to the Product reserved price, send the bidder a winning email.
        Sorry, // If a bid is less that a min Product price, send a bidder a sorry email.
        Overbidded // If somebody have done greater bid then others was.
    }

    public void send(User user, EmailType emailType) {
        System.out.println(">>> send to " + user.email + " about " + emailType);
    }
}
