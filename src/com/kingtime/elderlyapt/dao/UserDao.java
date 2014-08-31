package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.User;

/**
 * 用户接口类
 * 
 * @author xp
 * 
 * @created 2014-4-25
 */
public interface UserDao {

	/**
	 * 登录方法
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 返回该用户实例，如果登录失败返回为空
	 */
	public User login(String username, String password);

	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param uid
	 *            用户ID
	 * @return 返回该用户实例，如果不存在返回为空
	 */
	public User getUser(int uid);

	/**
	 * 获取所有系统用户，以便进行活动推荐（系统参与用户为老年人）
	 * 
	 * @return 用户列表
	 */
	public List<User> getAllUser();

	/**
	 * 根据用户信息进行用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return 返回该用户实例，如果注册失败返回为空
	 */
	public User register(User user);

	/**
	 * 根据活动编号获取发起人信息
	 * 
	 * @param activityId
	 *            活动编号
	 * @return 发起人信息
	 */
	public User getUserByActivityId(int activityId);

	/**
	 * 更新用户
	 * 
	 * @param updateUser
	 *            用户实体
	 * @return 更新完成后返回完整用户实体
	 */
	public User updateUserInfo(User updateUser);

	/**
	 * 更新用户信誉度，参与者更新创建者的信誉度
	 * 
	 * @param evaluate
	 *            评论实体
	 * @return 操作结果
	 */
	public boolean updateUserCredibility(Evaluate evaluate);

	/**
	 * 更新用户信誉度,创建者更新参与用户的信誉度
	 * 
	 * @param evaluate
	 * @return
	 */
	public boolean updateUserCredibilityByAppraise(Evaluate evaluate);

	/**
	 * 更新用户积分
	 * 
	 * @param uid
	 *            用户编号
	 * @param integral
	 *            积分
	 * @return 操作结果
	 */
	public boolean updateUserIntegral(int uid, int integral);
}
