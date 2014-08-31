package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.Record;

/**
 * 参与记录接口
 * 
 * @author xp
 * 
 * @created 2014-8-9
 */
public interface RecordDao {

	/**
	 * 根据活动编号获取当前已参加该活动的人员记录
	 * 
	 * @param activityId
	 *            活动编号
	 * @return
	 */
	public List<Record> getRecordsByActivityId(int activityId);

	/**
	 * 根据职责获取当前参与该职责的人员记录
	 * 
	 * @param dutyId
	 * @return
	 */
	public List<Record> getRecordsByDutyId(int dutyId);

	/**
	 * 根据记录编号获取记录内容
	 * 
	 * @param recordId
	 *            记录编号
	 * @return
	 */
	public Record getRecordByRecordId(int recordId);

	/**
	 * 获取用户参与活动状态
	 * 
	 * @param uid
	 *            用户编号
	 * @param activityId
	 *            活动编号
	 * @return 返回记录实例，如果没有参与，则返回为空
	 */
	public Record getRecord(int uid, int activityId);

	/**
	 * 新活动职责申请
	 * 
	 * @param uid
	 *            用户编号
	 * @param dutyId
	 *            职责编号
	 * @return 操作结果
	 */
	public boolean userApplyDuty(int uid, int dutyId);

	/**
	 * 取消职责申请
	 * 
	 * @param cancelList
	 *            取消记录列表
	 * @return
	 */
	public boolean cancelUserDuty(int[] cancelList);
	
	/**
	 * 根据活动编号更新用户参与活动状态
	 * @param activityId 活动编号
	 * @param stateId 状态编号
	 * @return
	 */
	public boolean updateStateId(int activityId, int stateId);
	
	/**
	 * 根据用户编号获取该用户参与的所有职责编号
	 * @param uid 用户编号
	 * @return 记录列表
	 */
	public List<Record> getRecordByUid(int uid);
	
	/**
	 * 评价活动
	 * @param evaluate 评价实体
	 * @return 操作结果
	 */
	public boolean evaluateActivity(Evaluate evaluate);
}
