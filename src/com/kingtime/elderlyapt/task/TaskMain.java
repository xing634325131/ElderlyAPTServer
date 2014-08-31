package com.kingtime.elderlyapt.task;

import java.util.Timer;

/**
 * 系统任务执行主程序入口
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public class TaskMain {

	private static final int DELAY = 5 * 60 * 1000;// 5分钟执行一次

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Timer mainTimer = new Timer();
		ActivityTask activityTask = new ActivityTask();
		mainTimer.scheduleAtFixedRate(activityTask, 0, DELAY);
	}

}
