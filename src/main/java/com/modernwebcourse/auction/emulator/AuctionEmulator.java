package com.modernwebcourse.auction.emulator;

import com.modernwebcourse.auction.IBidConsumer;
import com.modernwebcourse.auction.IBidProducer;
import com.modernwebcourse.auction.common.Money;
import com.modernwebcourse.auction.dao.IBidDao;
import com.modernwebcourse.auction.dao.IProductDao;
import com.modernwebcourse.auction.dao.IUserDao;
import com.modernwebcourse.auction.domain.Product;
import com.modernwebcourse.auction.domain.User;
import com.modernwebcourse.auction.emulator.BidConsumerEmulator;
import com.modernwebcourse.auction.emulator.BidProducerEmulator;
import com.modernwebcourse.auction.emulator.dao.BidDao;
import com.modernwebcourse.auction.emulator.dao.DB;
import com.modernwebcourse.auction.emulator.dao.ProductDao;
import com.modernwebcourse.auction.emulator.dao.UserDao;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class AuctionEmulator {

    public static final int AUCTION_DURATION_SEC = 100;

    private static final IProductDao productDao = new ProductDao();
    private static final IUserDao userDao = new UserDao();

    // todo add AuctionCloser timer, watcher for auctionEndTime and announce winner's bid
    // todo concurrent issues
    public static void main(String[] args) throws InterruptedException {

        IBidProducer bidProducer = new BidProducerEmulator();
        IBidConsumer bidConsumer = new BidConsumerEmulator(bidProducer.getQueue());

        loadTestData();

        // starting...
        System.out.println("auction is opening...");
        bidProducer.start();
        bidConsumer.start();

        TimeUnit.SECONDS.sleep(AUCTION_DURATION_SEC);

        // stopping...
        System.out.println("auction is closing...");
        bidProducer.stop();
        bidConsumer.stop();
    }

    private static void loadTestData() {
        System.out.println("----- load tested data (begin) -----");

        DB.clean();
        userDao.insert(new User(1, "Vasya", "vasya@email.com", true));
        userDao.insert(new User(2, "Klava", "klava@email.com", true));

        productDao.insert(new Product(1, "Nexus 5", "/img/nexus5.jpg", "smartphone Nexus 5", 3,
                LocalDateTime.now().plusWeeks(1), 0, Money.of(300), Money.of(340)));
        productDao.insert(new Product(2, "IPhone 5S", "/img/iphone5s.jpg", "smartphone IPhone 5S", 3,
                LocalDateTime.now().plusWeeks(1), 0, Money.of(600), Money.of(630)));

        System.out.println("----- load tested data (end) -----");
    }
}
