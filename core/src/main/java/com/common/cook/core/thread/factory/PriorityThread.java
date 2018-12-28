package com.common.cook.core.thread.factory;

import android.os.Process;

/**
 * Created by lmy on 2018/4/10.
 */

public class PriorityThread extends Thread {
    public static final int THREAD_PRIORITY_DEFAULT = 0;
    public static final int THREAD_PRIORITY_LOWEST = 19;
    public static final int THREAD_PRIORITY_BACKGROUND = 10;
    public static final int THREAD_PRIORITY_FOREGROUND = -2;
    public static final int THREAD_PRIORITY_DISPLAY = -4;
    public static final int THREAD_PRIORITY_URGENT_DISPLAY = -8;
    public static final int THREAD_PRIORITY_AUDIO = -16;
    public static final int THREAD_PRIORITY_URGENT_AUDIO = -19;
    public static final int THREAD_PRIORITY_MORE_FAVORABLE = -1;
    public static final int THREAD_PRIORITY_LESS_FAVORABLE = 1;
    private int mOSPriority = 0;

    public PriorityThread(Runnable r) {
        super(r);
    }

    public void setOSPriority(int p) {
        this.mOSPriority = p;
    }

    public void run() {
        Process.setThreadPriority(this.mOSPriority);
        super.run();
    }
}
