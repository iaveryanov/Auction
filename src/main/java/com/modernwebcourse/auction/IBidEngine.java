package com.modernwebcourse.auction;

import com.modernwebcourse.auction.domain.Bid;

import java.util.concurrent.BlockingQueue;

public interface IBidEngine {

    public BlockingQueue<Bid> getQueue();

    void start();

    void stop();
}
