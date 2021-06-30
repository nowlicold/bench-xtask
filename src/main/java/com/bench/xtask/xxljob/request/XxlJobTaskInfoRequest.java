package com.bench.xtask.xxljob.request;

import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @className XxlJobTaskInfoRequest
 * @autor cold
 * @DATE 2021/6/30 0:52
 **/
@Data
public class XxlJobTaskInfoRequest {

    private String jobGroupAppName; //执行器的appName

    private String jobDesc;


    private String author = "bench-auto";

    private String scheduleType;			// 调度类型
    private String scheduleConf;			// 调度配置，值含义取决于调度类型
    private String misfireStrategy  = "DO_NOTHING";			// 调度过期策略

    private String executorRouteStrategy = "FIRST";	// 执行器路由策略
    //task的名称
    private String executorHandler;		    // 执行器，任务Handler名称

    private String executorBlockStrategy = ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name();	// 阻塞处理策略

    private String glueType = GlueTypeEnum.BEAN.name();		// GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum

}
