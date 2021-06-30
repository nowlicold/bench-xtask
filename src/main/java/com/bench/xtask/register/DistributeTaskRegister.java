package com.bench.xtask.register;

import com.bench.xtask.BenchDistributeTask;

import java.util.List;

/**
 * @className DistributeTaskRegister
 * @autor cold
 * @DATE 2021/6/29 21:03
 * 分布式task注册器
 **/
public interface DistributeTaskRegister {
    /**
     * 进行注册
     * @return
     */
    public void registered(List<BenchDistributeTask> tasks);

}
