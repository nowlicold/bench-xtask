package com.bench.xtask;



/**
 * task封装器
 * 
 * @author cold
 *
 * @version $Id: TaskWrapper.java, v 0.1 2018年3月5日 下午4:42:08 cold Exp $
 */
public class BenchDistributeTaskWrapper<TE extends DistributeTaskExecuter> {

	/**
	 * 分布式task
	 */
	private BenchDistributeTask task;

	/**
	 * 执行器
	 */
	private TE executer;

	/**
	 * 执行分布式Task
	 */
	public void execute() {
		executer.execute(task);
	}

	public BenchDistributeTask getTask() {
		return task;
	}

	public TE getExecuter() {
		return executer;
	}

	public BenchDistributeTaskWrapper(BenchDistributeTask task, TE executer) {
		super();
		this.task = task;
		this.executer = executer;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return task.getTaskName();
	}
}
