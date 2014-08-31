package com.kingtime.elderlyapt.service.impl;

import java.util.List;

import com.kingtime.elderlyapt.dao.MessagePushDao;
import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.dao.impl.MessagePushDaoImpl;
import com.kingtime.elderlyapt.dao.impl.UserDaoImpl;
import com.kingtime.elderlyapt.entity.MessagePush;
import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.User;
import com.kingtime.elderlyapt.service.ActivityRecommendService;
import com.kingtime.elderlyapt.util.StringUtils;

;
/**
 * ��Ƽ��ӿ�ʵ��
 * 
 * @author xp
 * 
 * @created 2014-8-23
 */
public class ActivityRecommendServiceImpl implements ActivityRecommendService {

	@Override
	public void formRecommend(List<User> users, MyActivity myActivity) {
		MessagePush messagePush = null;
		MessagePushDao pushDao = new MessagePushDaoImpl();
		UserDao userDao = new UserDaoImpl();
		String mainString = myActivity.getPostName() + " " + myActivity.getContent();// ����
		System.out.println("������" + mainString);
		for (User user : users) {
			if(StringUtils.isEmpty(user.getInterest())){
				break;
			}
			
			String searchString[] = user.getInterest().split(";");// ģʽ��
			for (int i = 0; i < searchString.length; i++) {
				System.out.println("����ƥ�䣺" + searchString[i]);
				if (mainString.contains(searchString[i])) {// ƥ��ɹ������������Ϣ
					System.out.println("ƥ��ɹ���");
					messagePush = new MessagePush(user.getUid(), "�û���" + userDao.getUser(myActivity.getPostUserId()).getName()
							+ "��������һ���»��" + myActivity.getPostName() + "�����Ǿ���ͦ�ʺ���ģ���ȥ��һ���ɣ�", MessagePush.CATEGORY_JOIN);
					messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
					pushDao.createMessage(messagePush);
					break;
				}
			}
		}
	}

}
