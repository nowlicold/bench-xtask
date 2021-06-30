package com.bench.xtask.annotation;

import java.lang.annotation.*;

/**
 * 应用级分布式task的 注解，
 * 注意，时间参数只用来首次注册，后续的执行时间参数，以各个配置地为准
 * 如 xxl-job的 则以xxl-job-admin的配置为准
 *
 * @className DistributeTask
 * @autor cold
 * @DATE 2021/6/28 15:30
 **/
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  DistributeTask {
    /**
     * 每次执行的间隔毫秒数，默认1分钟
     *
     * @return
     */
    int repeatIntervalMillseconds() default 60000;

    /**
     * cron表达式
     *
     * @return
     */
    String cronExpression() default "";
}
