package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Duty;

/**
 * 职责接口
 * 
 * @author xp
 * 
 * @created 2014-8-9
 */
public interface DutyDao {

	/**
	 * 根据活动编号获取活动职责列表
	 * 
	 * @param activityId
	 *            活动编号
	 * @return
	 */
	public List<Duty> getDutyList(int activityId);

	/**
	 * 根据职责编号获取职责内容
	 * 
	 * @param dutyId
	 *            职责编号
	 * @return
	 */
	public Duty getDuty(int dutyId);

	/**
	 * 在参与人数改变后，更新职责目前人数
	 * 
	 * @param dutyId
	 *            需更新职责编号
	 * @param updateNum
	 *            更新数量，负数表示取消参与
	 * @return 操作结果
	 */
	public boolean updateDutyNowNum(int dutyId, int updateNum);

	/**
	 * 
	 * 获取职责当前参与人数
	 * 
	 * @param dutyId
	 *            职责编号
	 * @return
	 */
	public int getNowNum(int dutyId);

	/**
	 * 创建职责
	 * 
	 * @param duty
	 *            职责实体
	 * @return 操作结果
	 */
	public boolean createDuty(Duty duty);

	/**
	 * 搜索职责
	 * 
	 * @param searchString
	 *            搜索字符串
	 * @return 搜索结果：活动ID列表
	 */
	public int[] searchDuty(String searchString);
}
