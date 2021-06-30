package com.bench.xtask;

/**
 * @className BenchDistributeTask
 * @autor cold
 * @DATE 2021/6/28 15:25
 **/
public interface BenchDistributeTask {

    /**
     * 类的简单名称就是task的名称
     *
     * @return
     */
    public default String getTaskName() {
        return this.getClass().getSimpleName();
    }

    /**
     * task是否可执行
     *
     * @return
     */
    public default boolean runnable() {
        return true;
    }

    /**
     * 执行Task
     */
    public void execute();
}
