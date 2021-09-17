package com.b106.aipjt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    private static int TASK_CORE_POOL_SIZE = 1000;
    private static int TASK_MAX_POOL_SIZE = 100000;
    private static int TASK_QUEUE_CAPACITY = 100;
    private static String BEAN_NAME = "baseExecutor";

    @Bean
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
        executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
        executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
        executor.setBeanName(BEAN_NAME);
        executor.initialize();
        return executor;
    }
}
