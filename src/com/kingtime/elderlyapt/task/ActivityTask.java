package com.kingtime.elderlyapt.task;

import java.util.TimerTask;

import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.service.ActivityStateTaskService;
import com.kingtime.elderlyapt.service.impl.ActivityStateTaskServiceImpl;
import com.kingtime.elderlyapt.util.StringUtils;

/**
 * @author xp
 *
 * @created 2014-8-22
 */
public class ActivityTask extends TimerTask{
	
	public static int[] activityTaskRecord = new int[4];
	/**
	 * 普通延时，指活动状态：报名、截止报名、即将开始这三个状态变换为下一个状态，没有特殊要求
	 */
	private static final int COMMON_DELAY_TIME = 2;
	/**
	 * 结束活动延时，考虑到活动的各种不确定性，如：人员管理还没有到位、活动本身限制，在活动进行中切换到结束状态延时设定较高
	 */
	private static final int END_DELAY_TIME = 20;

	@Override
	public void run() {
		System.out.println("Activity Task time:"+StringUtils.getNow());
		ActivityStateTaskService activityTaskService = new ActivityStateTaskServiceImpl();
		activityTaskRecord[0] = activityTaskService.updateActivityState(MyActivity.ACTIVITY_APPLY, COMMON_DELAY_TIME);
		activityTaskRecord[1] = activityTaskService.updateActivityState(MyActivity.ACTIVITY_CLOSED, COMMON_DELAY_TIME);
		activityTaskRecord[2] = activityTaskService.updateActivityState(MyActivity.ACTIVITY_WILL_START, COMMON_DELAY_TIME);
		activityTaskRecord[3] = activityTaskService.updateActivityState(MyActivity.ACTIVITY_RUNNING, END_DELAY_TIME);
	}

}
