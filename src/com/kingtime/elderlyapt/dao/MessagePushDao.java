package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.MessagePush;

/**
 * 推送消息接口
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public interface MessagePushDao {

	/**
	 * 创建消息
	 * 
	 * @param messagePush
	 *            消息实体
	 * @return 操作结果
	 */
	public boolean createMessage(MessagePush messagePush);

	/**
	 * 批量创建消息
	 * 
	 * @param messagePushs
	 *            消息列表
	 * @return 操作结果
	 */
	public boolean createMessage(List<MessagePush> messagePushs);

	/**
	 * 
	 * 得到用户的推送消息列表
	 * 
	 * @param uid
	 *            用户编号
	 * @return 消息列表
	 */
	public List<MessagePush> getMessages(int uid);

	/**
	 * 根据消息类别获取推送消息
	 * 
	 * @param uid
	 *            用户编号
	 * @param messageCategoryId
	 *            消息类别编号
	 * @return 消息列表
	 */
	public List<MessagePush> getMessages(int uid, int messageCategoryId);

	/**
	 * 设定推送状态为已推送
	 * 
	 * @param uid
	 *            用户编号
	 * @return 操作结果
	 */
	public boolean updateToPushed(int uid);

	/**
	 * 设定某一类别推送的推送状态为已推送
	 * 
	 * @param uid
	 *            用户编号
	 * @param messageCategoryId
	 *            操作结果
	 * @return
	 */
	public boolean updateToPushed(int uid, int messageCategoryId);
}
