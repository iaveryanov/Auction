package com.modernwebcourse.auction.emulator;

import java.util.concurrent.atomic.AtomicInteger;

public class Sequence {

    private Sequence() {
    }

    private static AtomicInteger seq = new AtomicInteger(10);

    public static int cur() {
        return seq.get();
    }

    public static int nextBidId() {
        return seq.incrementAndGet();
    }

    public static int nextUserId() {
        return seq.incrementAndGet();
    }

    public static int nextProductId() {
        return seq.incrementAndGet();
    }


}
