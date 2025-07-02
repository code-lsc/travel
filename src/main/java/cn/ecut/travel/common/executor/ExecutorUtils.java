package cn.ecut.travel.common.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorUtils {

    private static Integer core = 4;

    private static Integer max = 10;

    private static ThreadPoolExecutor executors = new ThreadPoolExecutor(
            core,
            max,
            3,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getExecutor() {
        return executors;
    }
}
