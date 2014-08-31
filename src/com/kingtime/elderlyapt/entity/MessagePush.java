package com.kingtime.elderlyapt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ��Ϣ����ʵ��
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public class MessagePush {

	/**
	 * ���������[REMARK:activityId]
	 */
	public static final int CATEGORY_JOIN = 0x01;
	/**
	 * �״̬[REMARK:activityId]
	 */
	public static final int CATEGORY_STATE = 0x02;
	/**
	 * ���ֱ仯[REMARK:uid,& unnecessary]
	 */
	public static final int CATEGORY_INTEGRAL = 0x03;
	/**
	 * ϵͳ��Ϣ
	 */
	public static final int CATEGORY_SYSTEM = 0x04;
	/**
	 * �û���Ϣ���ݶ��ڸ������߷��û�������Ϣ[REMARK:activityId]
	 */
	public static final int CATEGORY_USER = 0x05;

	/**
	 * ������[REMARK:activityId]
	 */
	public static final int CATEGORY_VERIFY = 0x06;

	/**
	 * �����״��[REMARK:evaluateContent,& part]
	 */
	public static final int CATEGORY_CREDIBILITY = 0x07;

	private int messageId;
	private int toPushUserId;
	private String pushUserName;
	private String createTime;
	private String pushContent;
	private int pushCategoryId;
	private int isPushed;
	private String remark;

	public MessagePush() {
		pushUserName = "ϵͳ";// Ĭ����Ϣ������Ϊϵͳ
		isPushed = 0;
	}

	/**
	 * ���ٹ������
	 * 
	 * @param toPushUserId
	 * @param pushContent
	 * @param pushCategoryId
	 */
	public MessagePush(int toPushUserId, String pushContent, int pushCategoryId) {
		this.toPushUserId = toPushUserId;
		this.pushContent = pushContent;
		this.pushCategoryId = pushCategoryId;
		pushUserName = "ϵͳ";// Ĭ����Ϣ������Ϊϵͳ
		isPushed = 0;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getToPushUserId() {
		return toPushUserId;
	}

	public void setToPushUserId(int toPushUserId) {
		this.toPushUserId = toPushUserId;
	}

	public String getPushUserName() {
		return pushUserName;
	}

	public void setPushUserName(String pushUserName) {
		this.pushUserName = pushUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public int getPushCategoryId() {
		return pushCategoryId;
	}

	public void setPushCategory(int pushCategoryId) {
		this.pushCategoryId = pushCategoryId;
	}

	public int getIsPushed() {
		return isPushed;
	}

	public void setIsPushed(int isPushed) {
		this.isPushed = isPushed;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/*
	 * ����JSON����
	 */
	public String toString() {
		List<MessagePush> pushList = new ArrayList<MessagePush>();
		pushList.add(this);
		return toString(pushList);
	}

	/**
	 * ��MessagePushʵ������JSON����
	 * 
	 * @param pushList
	 * @return
	 */
	public static String toString(List<MessagePush> pushList) {
		if (pushList.size() < 1) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		MessagePush messagePush = null;
		for (int i = pushList.size(); i > 0; i--) {// ������json���ݣ�ʹʱ�������Ϊ����
			messagePush = pushList.get(i - 1);
			stringBuilder.append("{");
			stringBuilder.append("messageId:").append(messagePush.getMessageId()).append(",");
			stringBuilder.append("toPushUserId:").append(messagePush.getToPushUserId()).append(",");
			stringBuilder.append("pushUserName:\"").append(messagePush.getPushUserName()).append("\",");
			stringBuilder.append("pushContent:\"").append(messagePush.getPushContent()).append("\",");
			stringBuilder.append("createTime:\"").append(messagePush.getCreateTime()).append("\",");
			stringBuilder.append("pushCategoryId:").append(messagePush.getPushCategoryId()).append(",");
			stringBuilder.append("isPushed:").append(messagePush.getIsPushed()).append(",");
			stringBuilder.append("remark:\"").append(messagePush.getRemark()).append("\"");
			stringBuilder.append("},");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
