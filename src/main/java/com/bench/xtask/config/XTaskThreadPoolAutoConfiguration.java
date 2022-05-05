package com.bench.xtask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @className XTaskThreadPoolAutoConfiguration
 * @autor cold
 * @DATE 2022-01-19 9:44
 **/
@EnableAsync
@Configuration
public class XTaskThreadPoolAutoConfiguration {
    @Bean(name = "registeredXxlJobTaskExecutor")
    public Executor meetCloudRecordStopTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);//配置核心线程数
        executor.setMaxPoolSize(20);//配置最大线程数
        executor.setKeepAliveSeconds(5);
        executor.setQueueCapacity(200);//配置队列大小
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//拒绝策略
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();//执行初始化
        return executor;
    }
}
