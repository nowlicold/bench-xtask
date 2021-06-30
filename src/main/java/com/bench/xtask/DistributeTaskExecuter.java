package com.bench.xtask;


import com.bench.lang.base.instance.annotations.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式task执行器
 * @author cold
 * @version DistributeTaskExecuter, v 0.1 2020/5/13 9:59 DistributeTaskExecuterImpl Exp $
 */
@Singleton
public class DistributeTaskExecuter {

	private static final Logger log = LoggerFactory.getLogger(DistributeTaskExecuter.class);

	public void execute(BenchDistributeTask task) {
		//TODO 需要补全日志
		//获取task配置，判断当前环境是否可执行

		try {

			task.execute();

		}catch (Exception e){
			log.error("执行分布式任务异常taskName=" + task.getTaskName(), e);
		}
	}
}
