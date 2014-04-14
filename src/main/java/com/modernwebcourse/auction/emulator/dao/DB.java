package com.modernwebcourse.auction.emulator.dao;

import com.modernwebcourse.auction.common.Money;
import com.modernwebcourse.auction.domain.Bid;
import com.modernwebcourse.auction.domain.Product;
import com.modernwebcourse.auction.domain.User;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DB {

    // access only from package (from DAOs)
    final static ConcurrentMap<Integer, User> USERS = new ConcurrentHashMap<>();
    final static ConcurrentMap<Integer, Product> PRODUCTS = new ConcurrentHashMap<>();
    final static ConcurrentMap<Integer, Bid> BIDS = new ConcurrentHashMap<>();

    // load on start, only once!
    static {

        System.out.println("----- load tested data (begin) -----");

        insertUser(new User(1, "Vasya", "vasya@email.com", true));
        insertUser(new User(2, "Klava", "klava@email.com", true));

        insertProduct(new Product(1, "Nexus 5", "/img/nexus5.jpg", "smartphone Nexus 5", 3,
                LocalDateTime.now().plusWeeks(1), 0, new Money(300), new Money(340)));
        insertProduct(new Product(2, "IPhone 5S", "/img/iphone5s.jpg", "smartphone IPhone 5S", 3,
                LocalDateTime.now().plusWeeks(1), 0, new Money(600), new Money(630)));

        System.out.println("----- load tested data (end) -----");
    }

    private static void insertUser(User user) {
        USERS.put(user.id, user);
        System.out.println("inserted " + user);
    }

    private static void insertProduct(Product product) {
        PRODUCTS.put(product.id, product);
        System.out.println("inserted " + product);
    }

}
