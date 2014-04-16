package com.modernwebcourse.auction.emulator;

import com.modernwebcourse.auction.BidCommand;
import com.modernwebcourse.auction.IBidConsumer;
import com.modernwebcourse.auction.domain.Bid;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BidConsumerEmulator implements IBidConsumer {

    private final BlockingQueue<Bid> bidQueue;

    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    public BidConsumerEmulator(BlockingQueue<Bid> bidQueue) {
        this.bidQueue = bidQueue;
    }

    @Override
    public void start() {
        executor.execute(() -> process());
    }

    // event-loop
    private void process() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Bid bid = bidQueue.take();
                BidCommand.BidResult bidResult = new BidCommand(bid).process();
                System.out.printf("%-30s %s %n", bidResult, bid);
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
            Thread.currentThread().interrupt();
        }
        System.out.println("process is off");
    }

    @Override
    public void stop() {
        executor.shutdownNow();
    }
}
