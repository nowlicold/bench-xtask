package com.bench.xtask;

import com.bench.xtask.register.DistributeTaskRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className DistributeComponent
 * @autor cold
 * @DATE 2021/6/28 15:36
 **/
@Component
@Slf4j
public class DistributeTaskComponent implements ApplicationRunner {
	@Autowired
	private DistributeTaskRegister distributeTaskRegister;

	//所有实现task的类集合
	@Autowired
	private List<BenchDistributeTask> tasks;

	/**
	 * 注册分布式task
	 */
	public void registered() {
		distributeTaskRegister.registered(tasks);

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		registered();
	}
}
