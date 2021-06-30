package com.bench.xtask.xxljob;

import com.bench.xtask.BenchDistributeTaskWrapper;
import com.bench.xtask.BenchDistributeTask;
import com.bench.xtask.DistributeTaskExecuter;
import com.xxl.job.core.biz.model.ReturnT;

/**
 * @className XxlJobDistributeSchedulerWrapper
 * @autor cold
 * @DATE 2021/6/30 2:16
 * xxlJob的任务包装类，用来适配xxljob
 **/
public class XxlJobDistributeSchedulerWrapper <TE extends DistributeTaskExecuter> extends BenchDistributeTaskWrapper<TE> {
    public XxlJobDistributeSchedulerWrapper(BenchDistributeTask task, TE executer) {
        super(task, executer);
    }
    public ReturnT<String> execute(String param) {
        //创建context
        ReturnT result=new ReturnT();
        result.setCode(ReturnT.SUCCESS_CODE);
        try {
            super.execute();
        }catch (Exception e){
            result.setCode(ReturnT.FAIL_CODE);
            result.setMsg("任务执行失败,task="+this.toString()+"，exception="+e.getMessage());
            return result;
        }
        return result;
    }
}
