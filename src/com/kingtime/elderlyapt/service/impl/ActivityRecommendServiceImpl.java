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
 * 活动推荐接口实现
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
		String mainString = myActivity.getPostName() + " " + myActivity.getContent();// 主串
		System.out.println("主串：" + mainString);
		for (User user : users) {
			if(StringUtils.isEmpty(user.getInterest())){
				break;
			}
			
			String searchString[] = user.getInterest().split(";");// 模式串
			for (int i = 0; i < searchString.length; i++) {
				System.out.println("正在匹配：" + searchString[i]);
				if (mainString.contains(searchString[i])) {// 匹配成功，添加推送消息
					System.out.println("匹配成功！");
					messagePush = new MessagePush(user.getUid(), "用户“" + userDao.getUser(myActivity.getPostUserId()).getName()
							+ "”创建了一个新活动：" + myActivity.getPostName() + "，我们觉得挺适合你的，快去看一看吧！", MessagePush.CATEGORY_JOIN);
					messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
					pushDao.createMessage(messagePush);
					break;
				}
			}
		}
	}

}
