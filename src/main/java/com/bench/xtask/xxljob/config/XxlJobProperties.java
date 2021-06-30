package com.bench.xtask.xxljob.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @className XxlJobProperties
 * @autor cold
 * @DATE 2021/6/29 11:53
 **/

@Data
@Component
public class XxlJobProperties {
    @Value("${spring.application.name}")
    private String appCode;
    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${spring.application.name}-Excuter")
    private String executerName;

    @Value("${xxl.job.executor.address}")
    private String address;

    @Value("${xxl.job.executor.ip:'''}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;
    @Value("${xxl.job.timeOut:3}")
    private int timeOut;

    private String benchJobGroupInitUrl= "/bench/jobgroup/init";
    private String benchJobInfoInitUrl= "/bench/jobInfo/init";

}
