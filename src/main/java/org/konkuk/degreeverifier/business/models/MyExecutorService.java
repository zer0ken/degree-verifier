package org.konkuk.degreeverifier.business.models;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutorService {
    private static final ExecutorService instance = Executors.newCachedThreadPool();

    public static ExecutorService getInstance() {
        return instance;
    }
}
