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
	 * ��ͨ��ʱ��ָ�״̬����������ֹ������������ʼ������״̬�任Ϊ��һ��״̬��û������Ҫ��
	 */
	private static final int COMMON_DELAY_TIME = 2;
	/**
	 * �������ʱ�����ǵ���ĸ��ֲ�ȷ���ԣ��磺��Ա����û�е�λ����������ƣ��ڻ�������л�������״̬��ʱ�趨�ϸ�
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
