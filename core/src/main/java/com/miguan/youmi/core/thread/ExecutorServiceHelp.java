package com.miguan.youmi.core.thread;

import com.miguan.youmi.core.thread.factory.PriorityThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lmy on 2018/4/10.
 */

public class ExecutorServiceHelp {
    private static ExecutorService displayExecutor;
    private static ExecutorService defaultExecutor;
    private static ExecutorService lowestExecutor;
    private static ExecutorService httpExecutor;

    public ExecutorServiceHelp() {
    }

    public static synchronized void executeDisplay(Runnable runnable) {
        if (displayExecutor == null) {
            displayExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createMaxPriorityThread());
        }

        displayExecutor.execute(runnable);
    }

    public static synchronized void executeBackground(Runnable runnable) {
        if (defaultExecutor == null) {
            defaultExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createNormPriorityThread());
        }

        defaultExecutor.execute(runnable);
    }

    public static synchronized void executeLowest(Runnable runnable) {
        if (lowestExecutor == null) {
            lowestExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createMinPriorityThread());
        }

        lowestExecutor.execute(runnable);
    }

    public static synchronized ExecutorService getHttpExecutor() {
        if (httpExecutor == null) {
            httpExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createMaxPriorityThread());
        }

        return httpExecutor;
    }
}
