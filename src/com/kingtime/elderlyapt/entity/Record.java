package com.kingtime.elderlyapt.entity;

import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.DutyDao;
import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.dao.impl.DutyDaoImpl;
import com.kingtime.elderlyapt.dao.impl.UserDaoImpl;

/**
 * �û������¼ʵ��
 * 
 * @author xp
 * 
 * @created 2014-8-9
 */
public class Record {
	
	/**
	 * δ�����
	 */
	public static final int RECORD_NOT_JOIN = 0x00;
	/**
	 * ��������
	 */
	public static final int RECORD_COMMON = 0x01;
	/**
	 * ��ȡ������
	 */
	public static final int RECORD_CANCLED = 0x02;
	/**
	 * ���ڱ�����
	 */
	public static final int RECORD_MANAGEING = 0x03;

	/**
	 * δ����
	 */
	public static final int RECORD_NOT_EVALUATED = 0x04;
	/**
	 * ������
	 */
	public static final int RECORD_EVALUATED = 0x05;
	
	private int recordId;
	private int uid;
	private int dutyId;
	private int stateId;
	private String inTime;
	private String remark;

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getDutyId() {
		return dutyId;
	}

	public void setDutyId(int dutyId) {
		this.dutyId = dutyId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
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
		List<Record> records = new ArrayList<Record>();
		records.add(this);
		return toString(records);
	}

	/**
	 * ��DutyListʵ������JSON���ݣ�Ϊ��¼������û��Ķ�����Ϣ
	 * 
	 * @param dutyList
	 * @return
	 */
	public static String toString(List<Record> records) {
		User user = new User();
		Duty duty = new Duty();
		UserDao userDao = new UserDaoImpl();
		DutyDao dutyDao = new DutyDaoImpl();
		if (records.size() < 1) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (Record record : records) {
			stringBuilder.append("{");
			stringBuilder.append("recordId:").append(record.getRecordId())
					.append(",");
			stringBuilder.append("uid:").append(record.getUid()).append(",");
			stringBuilder.append("dutyId:").append(record.getDutyId())
					.append(",");
			stringBuilder.append("stateId:").append(record.getStateId())
					.append(",");
			stringBuilder.append("inTime:\"").append(record.getInTime())
					.append("\",");
			stringBuilder.append("remark:\"").append(record.getRemark())
					.append("\",");

			duty = dutyDao.getDuty(record.getDutyId());
			stringBuilder.append("dutyName:\"").append(duty.getDutyName())
					.append("\",");
			
			user = userDao.getUser(record.getUid());
			stringBuilder.append("userName:\"").append(user.getName())
					.append("\",");
			stringBuilder.append("photoName:\"").append(user.getPhotoName())
					.append("\",");
			stringBuilder.append("roleId:").append(user.getRoleId())
					.append(",");
			stringBuilder.append("credibility:").append(user.getCredibility());
			stringBuilder.append("},");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
