package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.MyActivity;

/**
 * 活动信息接口类
 * 
 * @author xp
 * 
 * @created 2014-4-28
 */
public interface ActivityInfoDao {

	/**
	 * 根据活动类别获取活动信息列表实例
	 * 
	 * @param requestCategory
	 *            活动类别
	 * @return 活动列表
	 */
	public List<MyActivity> getActivityInfoList(String requestCategory);

	/**
	 * 得到特定的需定时改变状态的活动列表。包括：报名中，报名截止，即将开始，进行中的活动
	 * 
	 * @param stateId
	 *            要更新的活动状态
	 * @return 活动列表
	 */
	public List<MyActivity> getAllNeedTaskActivity(int stateId);

	/**
	 * 根据活动id获取活动信息
	 * 
	 * @param activityId
	 *            活动ID
	 * @return 返回该物品信息实例，如果不存在则返回为空
	 */
	public MyActivity getActivityInfo(int activityId);

	/**
	 * 创建活动
	 * 
	 * @param myActivity
	 *            活动部分试题
	 * @param duties
	 *            活动职责
	 * @return 返回创建的实例
	 */
	public MyActivity createActivity(MyActivity myActivity, List<Duty> duties);

	/**
	 * 更新活动状态
	 * 
	 * @param activityId
	 *            活动编号
	 * @param stateId
	 *            状态编号
	 * @return
	 */
	public boolean updateActivityState(int activityId, int stateId);

	/**
	 * 更新当前参与人数
	 * 
	 * @param acitvityId
	 *            活动编号
	 * @param num
	 *            更新人数
	 * @return
	 */
	public boolean updateActivityNowNum(int activityId, int num);

	/**
	 * 请求待审核活动列表
	 * 
	 * @return
	 */
	public List<MyActivity> getActivitiesByVerify();

	/**
	 * 根据用户编号获取该用户发布的所有活动
	 * 
	 * @param uid
	 *            用户编号
	 * @return 活动列表
	 */
	public List<MyActivity> getPostActivity(int uid);

	/**
	 * 根据用户编号获取当前用户正参与（尚未结束）的活动
	 * 
	 * @param uid
	 *            用户编号
	 * @return 活动列表
	 */
	public List<MyActivity> getNowJoinedActivity(int uid);

	/**
	 * 根据用户编号获取该用户所有已经参与过的活动（已经结束）
	 * 
	 * @param uid
	 *            用户编号
	 * @return 活动编号
	 */
	public List<MyActivity> getEndedActivity(int uid);

	/**
	 * 获取活动结束后是否被活动发起人评价
	 * 
	 * @param activityId
	 *            活动编号
	 * @return 评论状态（true：被评价过，false：没有被评价过）
	 */
	public boolean getAppraiseState(int activityId);

	/**
	 * 创建者评价活动参与者
	 * 
	 * @param evaluates
	 *            评价
	 * @param activityId
	 *            活动编号
	 * @return 操作结果
	 */
	public boolean appraiseJoinedUser(List<Evaluate> evaluates, int activityId);

	/**
	 * 搜索活动
	 * 
	 * @param searchString
	 *            搜索关键字
	 * @return 搜索结果：即想要的活动列表
	 */
	public List<MyActivity> searchActivity(String searchString);
}
