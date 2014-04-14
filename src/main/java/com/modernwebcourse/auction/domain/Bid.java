package com.modernwebcourse.auction.domain;

import com.modernwebcourse.auction.common.Money;

import java.time.LocalDateTime;

public class Bid implements Comparable<Bid> {

    public int id;
    public Product product;
    public Money amount;
    public int desiredQuantity; // How many items the user wants
    public User user;
    public LocalDateTime bidTime;
    public boolean isWinning;

    public Bid() {
    }

    public Bid(int id, Product product, Money amount, int desiredQuantity,
               User user, LocalDateTime bidTime, boolean isWinning) {

        this.id = id;
        this.product = product;
        this.amount = amount;
        this.desiredQuantity = desiredQuantity;
        this.user = user;
        this.bidTime = bidTime;
        this.isWinning = isWinning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        if (id != bid.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", product=" + product.id +
                ", amount=" + amount.get() +
                ", desiredQuantity=" + desiredQuantity +
                ", user=" + user.id +
                ", bidTime=" + bidTime +
                ", isWinning=" + isWinning +
                '}';
    }

    @Override
    public int compareTo(Bid o) {
        return (int)(this.amount.get() - o.amount.get());
    }
}
