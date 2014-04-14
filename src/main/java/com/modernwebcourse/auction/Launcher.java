package com.modernwebcourse.auction;

import com.modernwebcourse.auction.emulator.AuctionEmulator;
import com.modernwebcourse.auction.emulator.BidEngineEmulator;

import java.util.concurrent.TimeUnit;

public class Launcher {

    public static final int AUCTION_DURATION_SEC = 100;

    public static void main(String[] args) throws InterruptedException {

        IBidEngine bidEngine = new BidEngineEmulator();
        IAuction auction = new AuctionEmulator(bidEngine.getQueue());

        // starting...
        System.out.println("auction is opening...");
        bidEngine.start();
        auction.start();

        TimeUnit.SECONDS.sleep(AUCTION_DURATION_SEC);

        // stopping...
        System.out.println("auction is closing...");
        bidEngine.stop();
        auction.stop();
    }
}
