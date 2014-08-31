package com.kingtime.elderlyapt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xp
 * 
 * @created 2014-8-9
 */
public class Duty {

	private int dutyId;
	private int activityId;
	private int needNum;
	private int nowNum;
	private String dutyContent;
	private int dutyIntegral;
	private String remark;
	private String dutyName;

	public int getDutyId() {
		return dutyId;
	}

	public void setDutyId(int dutyId) {
		this.dutyId = dutyId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getNeedNum() {
		return needNum;
	}

	public void setNeedNum(int needNum) {
		this.needNum = needNum;
	}

	public int getNowNum() {
		return nowNum;
	}

	public void setNowNum(int nowNum) {
		this.nowNum = nowNum;
	}

	public String getDutyContent() {
		return dutyContent;
	}

	public void setDutyContent(String dutyContent) {
		this.dutyContent = dutyContent;
	}

	public int getDutyIntegral() {
		return dutyIntegral;
	}

	public void setDutyIntegral(int dutyIntegral) {
		this.dutyIntegral = dutyIntegral;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	/*
	 * 生成JSON数据
	 */
	public String toString() {
		List<Duty> dutyList = new ArrayList<Duty>();
		dutyList.add(this);
		return toString(dutyList);
	}

	/**
	 * 由DutyList实体生成JSON数据
	 * 
	 * @param dutyList
	 * @return
	 */
	public static String toString(List<Duty> dutyList) {
		if (dutyList.size() < 1) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (Duty duty : dutyList) {
			stringBuilder.append("{");
			stringBuilder.append("dutyId:").append(duty.getDutyId())
					.append(",");
			stringBuilder.append("activityId:").append(duty.getActivityId())
					.append(",");
			stringBuilder.append("dutyContent:\"")
					.append(duty.getDutyContent()).append("\",");
			stringBuilder.append("nowNum:").append(duty.getNowNum())
					.append(",");
			stringBuilder.append("needNum:").append(duty.getNeedNum())
					.append(",");
			stringBuilder.append("dutyName:\"").append(duty.getDutyName())
					.append("\",");
			stringBuilder.append("remark:\"").append(duty.getRemark())
					.append("\",");
			stringBuilder.append("dutyIntegral:")
					.append(duty.getDutyIntegral());
			stringBuilder.append("},");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
