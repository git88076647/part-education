package com.czyl.framework.config;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.czyl.common.utils.ThreadsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @author tanghx
 **/
@Configuration
@EnableAsync
@Slf4j
public class ThreadPoolConfig {
    /**
     * 核心线程池大小
     */
    private int corePoolSize = 50;

    /**
     * 最大可创建的线程数
     */
    private int maxPoolSize = 200;

    /**
     * 队列最大长度
     */
    private int queueCapacity = 1000;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private int keepAliveSeconds = 300;

    /**
     * 线程池名前缀
     */
    private static final String THREAD_NAME_PREFIX = "Async-Service-";

    /**
     * 这是{@link ThreadPoolTaskExecutor}的一个简单替换，可搭配TransmittableThreadLocal实现父子线程之间的数据传递
     */
    public class CustomThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
        private static final long serialVersionUID = 1L;

        @Override
        public void execute(Runnable runnable) {
            Runnable ttlRunnable = TtlRunnable.get(runnable);
            super.execute(ttlRunnable);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            Callable<T> ttlCallable = TtlCallable.get(task);
            return super.submit(ttlCallable);
        }

        @Override
        public Future<?> submit(Runnable task) {
            Runnable ttlRunnable = TtlRunnable.get(task);
            return super.submit(ttlRunnable);
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            Runnable ttlRunnable = TtlRunnable.get(task);
            return super.submitListenable(ttlRunnable);
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            Callable<T> ttlCallable = TtlCallable.get(task);
            return super.submitListenable(ttlCallable);
        }
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new CustomThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        /** 线程池对拒绝任务(无线程可用)的处理策略*/
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * ttl 处理线程池数据传递BUG
     * @return
     */
    @Bean
    public ScheduledExecutorService ttlExecutorService() {
        return TtlExecutors.getTtlScheduledExecutorService(scheduledExecutorService());
    }


    /**
     * 执行周期性或定时任务
     */
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(corePoolSize, new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                ThreadsUtils.printException(r, t);
            }
        };
    }
}
