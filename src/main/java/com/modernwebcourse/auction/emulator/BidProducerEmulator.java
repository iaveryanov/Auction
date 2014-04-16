package com.modernwebcourse.auction.emulator;

import com.modernwebcourse.auction.IBidProducer;
import com.modernwebcourse.auction.common.Money;
import com.modernwebcourse.auction.dao.IBidDao;
import com.modernwebcourse.auction.dao.IProductDao;
import com.modernwebcourse.auction.dao.IUserDao;
import com.modernwebcourse.auction.domain.Bid;
import com.modernwebcourse.auction.domain.Product;
import com.modernwebcourse.auction.domain.User;
import com.modernwebcourse.auction.emulator.dao.BidDao;
import com.modernwebcourse.auction.emulator.dao.ProductDao;
import com.modernwebcourse.auction.emulator.dao.UserDao;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

public class BidProducerEmulator implements IBidProducer {

    private static final int TIMER_PERIOD_MS = 1000;
    public static final int MAX_RANDOM_DELTA = 10;

    private final BlockingQueue<Bid> queue;
    private final ScheduledExecutorService timer;

    private final IProductDao productDao = new ProductDao();
    private final IUserDao userDao = new UserDao();
    private final IBidDao bidDao = new BidDao();

    private final Random random = new Random();

    public BidProducerEmulator() {
        this.queue = new LinkedBlockingQueue<>();
        this.timer = Executors.newScheduledThreadPool(1);
    }

    public BlockingQueue<Bid> getQueue() {
        return queue;
    }

    public void start() {
        timer.scheduleAtFixedRate(() -> {
            try {
                emulateBiddingProcess();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, TIMER_PERIOD_MS, TIMER_PERIOD_MS, TimeUnit.MILLISECONDS);
    }

    //    Multiple people can bid on a Product. Just create a couple of Product instances
//    with hardcoded data. Use TimerTask and random number generator to emulate
//    bidding process.
    private void emulateBiddingProcess() {
        // product id 1 or 2
        // user id 1 or 2
        // amount can be less, equals or more
        Product randomProduct = randomProduct();
        Bid newBid = new Bid(
                Sequence.nextBidId(),
                randomProduct,
                randomAmount(randomProduct),
                1,
                randomUser(),
                LocalDateTime.now(),
                false);

//        System.out.println("new bid... " + newBid);
        queue.offer(newBid);
    }

    /**
     * @return product id 1 or 2
     */
    private User randomUser() {
        int randomId = random.nextInt(MAX_RANDOM_DELTA) % 2 + 1;
        return userDao.selectByUserId(randomId).get();
    }

    /**
     * @param product
     * @return amount can be less, equals or more
     */
    private Money randomAmount(Product product) {
        int r = random.nextInt(MAX_RANDOM_DELTA);
        // even "+" => can be (or not) send "winning email"
        // odd "-" => will send "sorry email"
        int delta = (int) (Math.signum((r % 2) == 0 ? 1 : -1) * r);

        Optional<Bid> maxOpt = bidDao.selectByProductId(product.id)
                .stream()
                .max((o1, o2) -> o1.compareTo(o2));

        // todo can be shorter? ifPresent
        double base = Math.max(product.minimalPrice.get(),
                maxOpt.isPresent() ? maxOpt.get().amount.get() : 0);

        return Money.of(base + delta);
    }

    /**
     * @return product id 1 or 2
     */
    private Product randomProduct() {
        // todo get only where nowTime < auctionEndTime
        // todo if no any bids with bidAmount >= reservedPrice

        int randomId = random.nextInt(MAX_RANDOM_DELTA) % 2 + 1;
        return productDao.selectByProductId(randomId).get();
    }

    public void stop() {
        timer.shutdown();
    }
}
