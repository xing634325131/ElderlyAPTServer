package com.kingtime.elderlyapt.service;

import java.util.List;

import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.User;

/**
 * ��Ƽ��ӿ�
 * 
 * @author xp
 * 
 * @created 2014-8-23
 */
public interface ActivityRecommendService {

	/**
	 * ���ɻ�Ƽ�������Ϣ���Զ����浽���ݿ�
	 * 
	 * @param users
	 *            �û��б�
	 * @param myActivity
	 *            �
	 */
	public void formRecommend(List<User> users, MyActivity myActivity);
}
