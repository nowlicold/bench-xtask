package com.bench.xtask.xxljob.register;

import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import com.bench.common.exception.CommonErrorEnum;
import com.bench.lang.base.annotation.utils.AnnotationUtils;
import com.bench.lang.base.bool.utils.BooleanUtils;
import com.bench.lang.base.clasz.method.utils.MethodUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.properties.utils.PropertiesUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.xtask.BenchDistributeTask;
import com.bench.xtask.DistributeTaskExecuter;
import com.bench.xtask.register.DistributeTaskRegister;
import com.bench.xtask.xxljob.XxlJobDistributeSchedulerWrapper;
import com.bench.xtask.xxljob.config.XxlJobProperties;
import com.bench.xtask.xxljob.enums.ScheduleTypeEnum;
import com.bench.xtask.xxljob.request.XxlJobGroupInitRequest;
import com.bench.xtask.xxljob.request.XxlJobTaskInfoRequest;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSimpleExecutor;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/**
 * @className DistributeTaskXxlJobRegister
 * @autor cold
 * @DATE 2021/6/29 21:20
 **/
@Component
@Slf4j
public class DistributeTaskXxlJobRegister implements DistributeTaskRegister {
    @Autowired
    private XxlJobProperties xxlJobProperties;
    @Autowired
    private Executor registeredXxlJobTaskExecutor;
    @Autowired
    private WebApplicationContext applicationContext;

    @Override
    public void registered(List<BenchDistributeTask> tasks) {

        // ??????/???????????????????????? ???????????????????????????
        initXxlJobGroup();
        Map<String, String> envMap = System.getenv();
        log.debug("??????????????????{}", PropertiesUtils.convert2String(envMap, false));
        boolean taskEnabled = true;
        if (StringUtils.isNotEmpty(envMap.get("TASK_ENABLED"))) {
            taskEnabled = BooleanUtils.toBoolean(envMap.get("TASK_ENABLED"));
        }
        if (taskEnabled) {
            // ??????/????????????????????????????????????
            registerJobGroup();
        }
        // ?????????task????????????
        List<FutureTask<BenchDistributeTask>> futureTaskList = new ArrayList<>();
        for (BenchDistributeTask task : tasks) {
            FutureTask<BenchDistributeTask> futureTask = new FutureTask<>(() -> {
                // ?????????task
                initTask(task);
                // ??????task
                registerTask(task);
                return task;
            });
            registeredXxlJobTaskExecutor.execute(futureTask);
            futureTaskList.add(futureTask);

        }

        futureTaskList.forEach(futureTask -> {
            BenchDistributeTask task = null;
            try {
				// ????????????????????????barrier
                task = futureTask.get();
            } catch (Exception e) {
                log.error("?????????????????????xxlJob??????,taskName={},taskClass={}", task.getTaskName(), task.getClass(), e);
                throw new BenchRuntimeException(CommonErrorEnum.SYSTEM_ERROR, "?????????????????????xxlJob??????,taskName={}=" + task.getTaskName());

            }
        });

    }

    /**
     * ?????????jobgroup
     */
    private void initXxlJobGroup() {
        // ??????????????????

        XxlJobGroupInitRequest request = new XxlJobGroupInitRequest();
        // ????????? ????????????+execute
        request.setAppName(xxlJobProperties.getExecuterName());
        request.setTitle(xxlJobProperties.getExecuterName());

        String url = xxlJobProperties.getAdminAddresses() + xxlJobProperties.getBenchJobGroupInitUrl();
        ReturnT<?> returnT = XxlJobRemotingUtil.postBody(url, "", xxlJobProperties.getTimeOut(), request, String.class);
        if (returnT.getCode() != 200) {
            // ?????????
            log.error("?????????xxlJobGroup??????????????????=" + xxlJobProperties.getExecuterName());
            System.exit(SpringApplication.exit(applicationContext));
        }

    }

    private void registerJobGroup() {

        XxlJobSimpleExecutor xxlJobExecutor = new XxlJobSimpleExecutor();
        xxlJobExecutor.setAdminAddresses(xxlJobProperties.getAdminAddresses());
        xxlJobExecutor.setAppname(xxlJobProperties.getExecuterName());
        xxlJobExecutor.setAddress(xxlJobProperties.getAddress());
        xxlJobExecutor.setIp(xxlJobProperties.getIp());
        xxlJobExecutor.setPort(xxlJobProperties.getPort());
        xxlJobExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobExecutor.setLogPath(xxlJobProperties.getLogPath());
        xxlJobExecutor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        try {
            xxlJobExecutor.start();
        } catch (Exception e) {
            log.error("?????????????????????={}", e.getMessage());
            throw new BenchRuntimeException(CommonErrorEnum.SYSTEM_ERROR, "??????xxlJobGroup??????executerName:" + xxlJobProperties.getExecuterName(), e);
        }
    }

    private void initTask(BenchDistributeTask task) {

        com.bench.xtask.annotation.DistributeTask annotationTask = AnnotationUtils.getAnnotation(task.getClass(), com.bench.xtask.annotation.DistributeTask.class);
        if (annotationTask == null) {
            // ???????????????
            throw new BenchRuntimeException(CommonErrorEnum.SYSTEM_ERROR.errorCode(), "?????????task" + task + "??????,?????????@DistributeTask??????");
        }
        // ????????????
        String url = xxlJobProperties.getAdminAddresses() + xxlJobProperties.getBenchJobInfoInitUrl();
        // ????????????
        XxlJobTaskInfoRequest xxlJobTaskInfoRequest = new XxlJobTaskInfoRequest();
        xxlJobTaskInfoRequest.setJobGroupAppName(xxlJobProperties.getExecuterName());
        xxlJobTaskInfoRequest.setJobDesc(xxlJobProperties.getAppCode() + "-" + task.getTaskName());
        if (!StringUtils.isEmpty(annotationTask.cronExpression())) {
            xxlJobTaskInfoRequest.setScheduleType(ScheduleTypeEnum.CRON.name());
            xxlJobTaskInfoRequest.setScheduleConf(annotationTask.cronExpression());
        } else if (annotationTask.repeatIntervalMillseconds() > 0) {
            xxlJobTaskInfoRequest.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
            //???????????????????????????
            xxlJobTaskInfoRequest.setScheduleConf(ObjectUtils.toString(annotationTask.repeatIntervalMillseconds() / 1000));
        } else {
            // ??????????????????????????????
            throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "????????????????????????,task=" + task);

        }
        // ????????????
        xxlJobTaskInfoRequest.setExecutorHandler(task.getTaskName());
        ReturnT<?> returnT = XxlJobRemotingUtil.postBody(url, "", xxlJobProperties.getTimeOut(), xxlJobTaskInfoRequest, String.class);
        if (returnT.getCode() != 200) {
            // ???????????????????????????
            throw new BenchRuntimeException(CommonErrorEnum.SYSTEM_ERROR.errorCode(), "?????????task=" + task + "??????,??????xxlJobInfo????????? returnT=" + returnT);

        }
    }

    private void registerTask(BenchDistributeTask task) {
        // ???????????????
        DistributeTaskExecuter distributeTaskExecuter = new DistributeTaskExecuter();
        XxlJobDistributeSchedulerWrapper<DistributeTaskExecuter> wrapper = new XxlJobDistributeSchedulerWrapper<>(task, distributeTaskExecuter);

        Method executeMethod = MethodUtils.getMethod(wrapper.getClass(), "execute", String.class);
        executeMethod.setAccessible(true);
        Method initMethod = null;
        Method destroyMethod = null;
        MethodJobHandler methodJobHandler = new MethodJobHandler(wrapper, executeMethod, initMethod, destroyMethod);

        // registry jobhandler
        XxlJobExecutor.registJobHandler(task.getTaskName(), methodJobHandler);

    }


}
