package com.miguan.youmi.core.thread.factory;

import java.util.concurrent.ThreadFactory;

/**
 * Created by lmy on 2018/4/10.
 */

public class PriorityThreadFactory {
    public PriorityThreadFactory() {
    }

    public static ThreadFactory createNormPriorityThread() {
        return new ThreadFactory() {
            public Thread newThread(Runnable r) {
                PriorityThread t = new PriorityThread(r);
                t.setOSPriority(10);
                t.setPriority(5);
                return t;
            }
        };
    }

    public static ThreadFactory createMinPriorityThread() {
        return new ThreadFactory() {
            public Thread newThread(Runnable r) {
                PriorityThread t = new PriorityThread(r);
                t.setOSPriority(19);
                t.setPriority(1);
                return t;
            }
        };
    }

    public static ThreadFactory createMaxPriorityThread() {
        return new ThreadFactory() {
            public Thread newThread(Runnable r) {
                PriorityThread t = new PriorityThread(r);
                t.setOSPriority(-4);
                t.setPriority(10);
                return t;
            }
        };
    }
}

