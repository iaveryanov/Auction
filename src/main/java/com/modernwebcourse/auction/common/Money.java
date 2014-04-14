package com.modernwebcourse.auction.common;

import java.math.BigDecimal;

// Immutable
public class Money implements Comparable<Money> {

    private final BigDecimal amount;

    public Money(double amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money add(double val) {
        return new Money(amount.add(new BigDecimal(val)).doubleValue());
    }

    public Money subtract(double val) {
        return new Money(amount.subtract(new BigDecimal(val)).doubleValue());
    }

    public double get() {
        return amount.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (!amount.equals(money.amount)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                '}';
    }

    @Override
    public int compareTo(Money o) {
        return amount.compareTo(o.amount);
    }
}
