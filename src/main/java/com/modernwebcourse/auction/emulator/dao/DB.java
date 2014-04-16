package com.modernwebcourse.auction.emulator.dao;

import com.modernwebcourse.auction.domain.Bid;
import com.modernwebcourse.auction.domain.Product;
import com.modernwebcourse.auction.domain.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DB {

    // access only from package (from DAOs)
    final static ConcurrentMap<Integer, User> USERS = new ConcurrentHashMap<>();
    final static ConcurrentMap<Integer, Product> PRODUCTS = new ConcurrentHashMap<>();
    final static ConcurrentMap<Integer, Bid> BIDS = new ConcurrentHashMap<>();

    public static void clean() {
        USERS.clear();
        PRODUCTS.clear();
        BIDS.clear();
    }
}
