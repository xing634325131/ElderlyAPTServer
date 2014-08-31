package com.kingtime.elderlyapt.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.impl.ActivityInfoDaoImpl;
import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.service.ActivityStateTaskService;
import com.kingtime.elderlyapt.util.StringUtils;

/**
 * @author xp
 *
 * @created 2014-8-22
 */
public class ActivityStateTaskServiceImpl implements ActivityStateTaskService{

	@Override
	public int updateActivityState(int stateId, int delayMin) {
		int result = 0;
		ActivityInfoDao infoDao = new ActivityInfoDaoImpl();
		List<MyActivity> myActivities = infoDao.getAllNeedTaskActivity(stateId);
		Calendar calendar = Calendar.getInstance();
		for(MyActivity myActivity:myActivities){
			//�ָ��ʱ���뵱ǰʱ��Ƚϣ����ǵ�����ԭ��������һ�㷶Χ�����ӳ٣��ڱȽϺ�ϵͳ���Զ��ı�״̬
			Date toCompareDate = StringUtils.toDate(myActivity.getCloseTime());
			if(StringUtils.compareDateDelay(toCompareDate, calendar.getTime(), delayMin)){
				System.out.println("activityID:"+myActivity.getActivityId());
				infoDao.updateActivityState(myActivity.getActivityId(), myActivity.getStateId() + 1);//���µ���һ��״̬
				result++;
			}
		}
		return result;
	}

}
