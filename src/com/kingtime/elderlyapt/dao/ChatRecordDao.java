package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.ChatRecord;

/**
 * 讨论消息接口
 * 
 * @author xp
 * 
 * @created 2014-8-26
 */
public interface ChatRecordDao {

	/**
	 * 通过活动编号获取该活动的聊天记录
	 * 
	 * @param acivityId
	 *            活动编号
	 * @return
	 */
	public List<ChatRecord> getChatRecords(int activityId);

	/**
	 * 创建记录
	 * 
	 * @param record
	 *            记录实体
	 * @return 操作结果
	 */
	public boolean createChatRecord(ChatRecord record);
}
