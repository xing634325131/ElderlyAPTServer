package com.kingtime.elderlyapt.task;

import java.util.Timer;

/**
 * ϵͳ����ִ�����������
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public class TaskMain {

	private static final int DELAY = 5 * 60 * 1000;// 5����ִ��һ��

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Timer mainTimer = new Timer();
		ActivityTask activityTask = new ActivityTask();
		mainTimer.scheduleAtFixedRate(activityTask, 0, DELAY);
	}

}
