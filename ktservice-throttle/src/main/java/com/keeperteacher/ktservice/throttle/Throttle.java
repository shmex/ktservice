package com.keeperteacher.ktservice.throttle;

import java.util.concurrent.atomic.AtomicLong;

public class Throttle {

    private AtomicLong lastran = new AtomicLong(0);
    private final long interval;

    public Throttle(double frequency) {
        interval = (long) (1000 / frequency);
    }

    public void throttledRun(ThrottledTask throttledTask) {
        if(System.currentTimeMillis() - lastran.get() < interval) {
            return;
        }

        lastran.set(System.currentTimeMillis());
        throttledTask.performTask();
    }
}
