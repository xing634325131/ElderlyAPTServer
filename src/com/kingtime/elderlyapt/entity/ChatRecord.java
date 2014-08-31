package com.kingtime.elderlyapt.entity;

import java.util.List;

/**
 * 讨论实体
 * 
 * @author xp
 * @created 2014年5月10日
 */
public class ChatRecord {

	private int chatId;
	private int activityId;
	private int uid;
	private String chatTime;
	private String content;
	private String remark;

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 生成JSON数据
	 * 
	 * @param recordList
	 * @return
	 */
	public static String toString(List<ChatRecord> recordList) {
		if (recordList.size() < 1) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (ChatRecord record : recordList) {
			builder.append("{");
			builder.append("chatId:").append(record.getChatId()).append(",");
			builder.append("activityId:").append(record.getActivityId()).append(",");
			builder.append("uid:").append(record.getUid()).append(",");
			builder.append("chatTime:\"").append(record.getChatTime()).append("\",");
			builder.append("content:\"").append(record.getContent()).append("\",");
			builder.append("remark:\"").append(record.getRemark()).append("\"");
			builder.append("},");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("]");
		return builder.toString();
	}
}
