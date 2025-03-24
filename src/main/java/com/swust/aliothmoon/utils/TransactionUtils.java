package com.swust.aliothmoon.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.util.Lazy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

@UtilityClass
public class TransactionUtils {
    private static final Lazy<PlatformTransactionManager> TX = Lazy.of(() -> SpringUtils.getBean(PlatformTransactionManager.class));

    public static void tx(Runnable runnable) {
        PlatformTransactionManager manager = TX.get();
        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());
        try {
            runnable.run();
            manager.commit(status);
        } catch (Exception e) {
            manager.rollback(status);
            ThrowsUtils.doThrow(e);
        }
    }

    public static <T> T tx(Supplier<T> supplier) {
        PlatformTransactionManager manager = TX.get();
        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());
        try {
            T t = supplier.get();
            manager.commit(status);
            return t;
        } catch (Exception e) {
            manager.rollback(status);
            return ThrowsUtils.doThrow(e);
        }
    }

    public static void addAfterCommitHook(Runnable runnable) {
        boolean active = TransactionSynchronizationManager.isSynchronizationActive();
        if (active) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    public static void addAfterCompletionHook(Consumer<Integer> consumer) {
        boolean active = TransactionSynchronizationManager.isSynchronizationActive();
        if (active) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    consumer.accept(status);
                }
            });
        } else {
            consumer.accept(-1);
        }
    }
}
