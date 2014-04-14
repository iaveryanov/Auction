package com.modernwebcourse.auction.domain;

import com.modernwebcourse.auction.common.Money;

import java.time.LocalDateTime;

public class Product {

    public Product() {
    }

    public Product(int id, String title, String thumb, String description,
                   int quantity, LocalDateTime auctionEndTime,
                   int watchers, Money minimalPrice, Money reservedPrice) {

        this.id = id;
        this.title = title;
        this.thumb = thumb;
        this.description = description;

        this.quantity = quantity;
        this.auctionEndTime = auctionEndTime;
        this.watchers = watchers;

        this.minimalPrice = minimalPrice;
        this.reservedPrice = reservedPrice;
    }

    public int id;
    public String title;
    public String thumb;
    public String description;
    public int quantity;   // How many items the seller has
    public LocalDateTime auctionEndTime;
    public int watchers;
    public Money minimalPrice;     // Don't sell unless the bid is more than min price
    public Money reservedPrice;   // If a bidder offers reserved price, the auction is closed

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        if (id != product.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", auctionEndTime=" + auctionEndTime +
                ", watchers=" + watchers +
                ", minimalPrice=" + minimalPrice +
                ", reservedPrice=" + reservedPrice +
                '}';
    }

    public String toStringShort() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
