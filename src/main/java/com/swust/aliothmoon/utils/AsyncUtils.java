package com.swust.aliothmoon.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.util.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

@UtilityClass
public class AsyncUtils {
    private static final Lazy<ThreadPoolTaskExecutor> POOL = Lazy.of(() -> SpringUtils.getBean("simpleAsyncTaskTaskExecutor", ThreadPoolTaskExecutor.class));

    public static ThreadPoolTaskExecutor getExecutor() {
        return POOL.get();
    }

    public static void execute(Runnable task) {
        POOL.get().execute(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return POOL.get().submit(task);
    }

    public static Future<?> submit(Runnable task) {
        return POOL.get().submit(task);
    }

    public static <T> CompletableFuture<T> submitTask(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, POOL.get());
    }

    public static CompletableFuture<Void> submitTask(Runnable task) {
        return CompletableFuture.runAsync(task, POOL.get());
    }
}
