package com.kingtime.elderlyapt.service;

import java.util.List;

import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.User;

/**
 * 活动推荐接口
 * 
 * @author xp
 * 
 * @created 2014-8-23
 */
public interface ActivityRecommendService {

	/**
	 * 生成活动推荐推送消息，自动保存到数据库
	 * 
	 * @param users
	 *            用户列表
	 * @param myActivity
	 *            活动
	 */
	public void formRecommend(List<User> users, MyActivity myActivity);
}
