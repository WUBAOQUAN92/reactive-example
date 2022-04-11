package com.wubq.reactive.service.reactor;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wubq
 * @since 2022/3/20 23:55
 */
public class UiUtils {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void errorPopup(Throwable error) {
        System.out.println(error);
    }

    public static void submitOnUiThread(Runnable runnable) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void show(Favorite s) {

        System.out.println(s + " thread name : " + Thread.currentThread().getName());
    }

    public static Scheduler uiThreadScheduler() {
        return Schedulers.boundedElastic();
    }
}
