package com.modernwebcourse.auction

import com.modernwebcourse.auction.common.Money
import com.modernwebcourse.auction.domain.Bid
import com.modernwebcourse.auction.domain.Product
import com.modernwebcourse.auction.domain.User
import com.modernwebcourse.auction.emulator.dao.BidDao
import com.modernwebcourse.auction.emulator.dao.DB
import com.modernwebcourse.auction.emulator.dao.ProductDao
import com.modernwebcourse.auction.emulator.dao.UserDao

import java.time.LocalDateTime

import static com.modernwebcourse.auction.BidCommand.BidResult.*

class BidCommandTest extends spock.lang.Specification {

    def MIN_PRICE = 10;
    def RESERVED_PRICE = 20;
    def product = new Product(1, "table", "img/img1.jpg", "table", 1, LocalDateTime.now().plusDays(1),
            0, Money.of(MIN_PRICE), Money.of(RESERVED_PRICE));
    def user1 = new User(1, "Ivan", "ivan@example.com", false)
    def user2 = new User(2, "Petr", "petr@example.com", false)

    // DAOs
    def productDao = new ProductDao();
    def bidDao = new BidDao();
    def userDao = new UserDao();

    def setup() {
        DB.clean() // before each test
        userDao.insert(user1)
        userDao.insert(user2)
        productDao.insert(product)
    }

    def "not bidded, auction is expired"() {
        setup:
        product.auctionEndTime = LocalDateTime.now().minusDays(1);
        def bid = new Bid(1, product, Money.of(MIN_PRICE + 1), 1, user1, LocalDateTime.now(), false)

        expect:
        new BidCommand(bid).process() == ERR_AUCTION_EXPIRED;
    }

    def "not bidded, product is all sold"() {
        setup:
        product.quantity = 0;
        def bid = new Bid(1, product, Money.of(RESERVED_PRICE + 1), 1, user1, LocalDateTime.now(), false)

        expect:
        new BidCommand(bid).process() == ERR_ALL_SOLD;
    }

    def "not bidded, bid has price less then min product's price"() {
        setup:
        def bid = new Bid(1, product, Money.of(MIN_PRICE - 1), 1, user1, LocalDateTime.now(), false)

        expect:
        new BidCommand(bid).process() == ERR_LESS_MIN_PRICE;
    }

    def "bidded, bid has price greater then reserved product's price"() {
        setup:
        def bid = new Bid(1, product, Money.of(RESERVED_PRICE + 1), 1, user1, LocalDateTime.now(), false)

        expect:
        new BidCommand(bid).process() == OK_GREATER_RESERVED_PRICE;
    }

    def "bidded, first bid on product"() {
        setup:
        def bid = new Bid(1, product, Money.of(MIN_PRICE + 1), 1, user1, LocalDateTime.now(), false)

        expect:
        new BidCommand(bid).process() == OK_FIRST_BID;
    }

    def "bidded, overbidded"() {
        setup:
        def bid1 = new Bid(1, product, Money.of(MIN_PRICE + 1), 1, user1, LocalDateTime.now(), false)
        bidDao.insert(bid1);
        def bid2 = new Bid(2, product, Money.of(MIN_PRICE + 2), 1, user2, LocalDateTime.now(), false)

        expect:
        new BidCommand(bid2).process() == OK_OVERBIDDED;
    }

    def "not bidded, because not overbidded"() {
        setup:
        def bid1 = new Bid(1, product, Money.of(MIN_PRICE + 1), 1, user1, LocalDateTime.now(), false)
        bidDao.insert(bid1);
        def bid2 = new Bid(2, product, Money.of(MIN_PRICE + 1), 1, user2, LocalDateTime.now(), false)

        expect:
        new BidCommand(bid2).process() == ERR_NOT_OVERBIDDED;
    }
}
