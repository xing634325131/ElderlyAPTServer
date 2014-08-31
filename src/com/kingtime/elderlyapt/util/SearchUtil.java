package com.kingtime.elderlyapt.util;

import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.DutyDao;
import com.kingtime.elderlyapt.dao.impl.ActivityInfoDaoImpl;
import com.kingtime.elderlyapt.dao.impl.DutyDaoImpl;
import com.kingtime.elderlyapt.entity.MyActivity;

/**
 * @author xp
 * 
 * @created 2014-8-21
 */
public class SearchUtil {

	/**
	 * �����
	 * 
	 * @param searchString
	 *            �����ؼ���
	 * @return ������б�
	 */
	public static List<MyActivity> searchForActivity(String searchString) {
		List<MyActivity> myActivities = new ArrayList<MyActivity>();
		ActivityInfoDao activityInfoDao = new ActivityInfoDaoImpl();
		DutyDao dutyDao = new DutyDaoImpl();
		myActivities = activityInfoDao.searchActivity(searchString);// ������Ľ��
		int[] activityIds = dutyDao.searchDuty(searchString);// ����ְ��Ľ�����ID�б�

		// �ϲ�ȥ�أ������������Ⱥ���
		for (int j = 0; j < activityIds.length; j++) {
			boolean flag = true;
			for (int i = 0; i < myActivities.size(); i++) {
				if (myActivities.get(i).getActivityId() == activityIds[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				myActivities.add(activityInfoDao.getActivityInfo(activityIds[j]));
			}
		}
		System.out.println("combine search name:" + searchString + ",search result size:" + myActivities.size());

		return myActivities;
	}

	/**
	 * ������ͨ����˵Ļ
	 * 
	 * @param searchString
	 *            �����ؼ���
	 * @return �������
	 */
	public static List<MyActivity> searchForHaveVerifiedActivity(String searchString) {
		List<MyActivity> myActivities = searchForActivity(searchString);
		for (int i = 0; i < myActivities.size(); i++) {
			if (myActivities.get(i).getStateId() == MyActivity.ACTIVITY_REVIEW) {
				myActivities.remove(i);
			}
		}
		return myActivities;
	}
}
