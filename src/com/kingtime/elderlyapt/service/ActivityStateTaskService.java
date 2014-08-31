package com.kingtime.elderlyapt.service;

/**
 * 活动状态检测任务 在超过规定的活动时间后，如果用户没有手动切换活动状态，系统会主动切换
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public interface ActivityStateTaskService {

	/**
	 * 更新活动状态
	 * 
	 * @param stateId
	 *            要更新的活动状态
	 * @param delayMin
	 *            延迟更新时间
	 * @return 操作结果：更新数据个数
	 */
	public int updateActivityState(int stateId, int delayMin);
}
