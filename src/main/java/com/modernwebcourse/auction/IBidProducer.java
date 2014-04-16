package com.modernwebcourse.auction;

import com.modernwebcourse.auction.domain.Bid;

import java.util.concurrent.BlockingQueue;

/**
 * Bid Engine
 */
public interface IBidProducer {

    public BlockingQueue<Bid> getQueue();

    void start();

    void stop();
}
