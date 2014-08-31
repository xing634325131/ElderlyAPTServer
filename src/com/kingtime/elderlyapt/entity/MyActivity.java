package com.kingtime.elderlyapt.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kingtime.elderlyapt.dao.impl.ImageDaoImpl;

/**
 * 活动实体
 * 
 * @author xp
 * @created 2014年8月6日
 */
public class MyActivity {

	/**
	 * 待审核
	 */
	public static final int ACTIVITY_REVIEW = 0x01;
	/**
	 * 报名中
	 */
	public static final int ACTIVITY_APPLY = 0x02;
	/**
	 * 报名截止
	 */
	public static final int ACTIVITY_CLOSED = 0x03;
	/**
	 * 即将开始
	 */
	public static final int ACTIVITY_WILL_START = 0x04;
	/**
	 * 进行中
	 */
	public static final int ACTIVITY_RUNNING = 0x05;
	/**
	 * 已结束
	 */
	public static final int ACTIVITY_END = 0x06;

	public static final String[] ACTIVITY_STATE = new String[] { "待审核", "报名中", "报名截止", "即将开始", "进行中", "已结束" };

	private int activityId;
	private int postUserId;
	private int categoryId;
	private int locationId;
	private String postName;
	private String content;
	private String postTime;
	private String beginTime;
	private String endTime;
	private String closeTime;
	private int needNum;
	private int nowNum;
	private int stateId;
	private String address;
	private int sumIntegral;
	private String remark;
	private String mainPic;

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getPostUserId() {
		return postUserId;
	}

	public void setPostUserId(int postUserId) {
		this.postUserId = postUserId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
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

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSumIntegral() {
		return sumIntegral;
	}

	public void setSumIntegral(int sumIntegral) {
		this.sumIntegral = sumIntegral;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	/**
	 * 获取请求类别ID
	 * 
	 * @param requestCategory
	 * @return
	 */
	public static int getCategoryId(String requestCategory) {
		int id = 0;
		try {
			id = Integer.valueOf(requestCategory) + 1;
		} catch (NumberFormatException e) {
			if (requestCategory.equals("recommend")) {
				id = 10;
			}
		}
		return id;
	}

	/*
	 * 生成JSON数据
	 */
	public String toString() {
		List<MyActivity> infoList = new ArrayList<MyActivity>();
		infoList.add(this);
		return toString(infoList);
	}

	/**
	 * 由MyActivity实体生成JSON数据
	 * 
	 * @param myActivities
	 * @return
	 */
	public static String toString(List<MyActivity> myActivities) {
		if (myActivities.size() < 1) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (MyActivity activity : myActivities) {
			stringBuilder.append("{");
			stringBuilder.append("activityId:").append(activity.getActivityId()).append(",");
			stringBuilder.append("postUserId:").append(activity.getPostUserId()).append(",");
			stringBuilder.append("address:\"").append(activity.getAddress()).append("\",");
			stringBuilder.append("beginTime:\"").append(activity.getBeginTime()).append("\",");
			stringBuilder.append("categoryId:").append(activity.getCategoryId()).append(",");
			stringBuilder.append("closeTime:\"").append(activity.getCloseTime()).append("\",");
			stringBuilder.append("content:\"").append(activity.getContent()).append("\",");
			stringBuilder.append("endTime:\"").append(activity.getEndTime()).append("\",");
			stringBuilder.append("locationId:").append(activity.getLocationId()).append(",");
			stringBuilder.append("needNum:").append(activity.getNeedNum()).append(",");
			stringBuilder.append("nowNum:").append(activity.getNowNum()).append(",");
			stringBuilder.append("postName:\"").append(activity.getPostName()).append("\",");
			stringBuilder.append("postTime:\"").append(activity.getPostName()).append("\",");
			stringBuilder.append("stateId:").append(activity.getStateId()).append(",");
			stringBuilder.append("remark:\"").append(activity.getRemark()).append("\",");
			stringBuilder.append("mainPic:\"").append(new ImageDaoImpl().getActivityMainPic(activity.activityId)).append("\",");
			stringBuilder.append("sumIntegral:").append(activity.getSumIntegral());
			stringBuilder.append("},");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	/**
	 * 解析JSON数据，转化为MyActivity对象
	 * 
	 * @param createActivityJSON
	 * @return
	 * @throws JSONException
	 */
	public static MyActivity praseToMyActivity(String createActivityJSON) throws JSONException {
		MyActivity myActivity = new MyActivity();
		JSONArray array = new JSONArray(createActivityJSON);
		if (array.length() == 1) {
			JSONObject object = array.getJSONObject(0);
			myActivity.postUserId = object.getInt("postUserId");
			myActivity.address = object.getString("address");
			myActivity.beginTime = object.getString("beginTime");
			myActivity.categoryId = object.getInt("categoryId");
			myActivity.closeTime = object.getString("closeTime");
			myActivity.content = object.getString("content");
			myActivity.endTime = object.getString("endTime");
			myActivity.locationId = object.getInt("locationId");
			myActivity.needNum = object.getInt("needNum");
			myActivity.postName = object.getString("postName");
			myActivity.sumIntegral = object.getInt("sumIntegral");
			myActivity.mainPic = object.getString("mainPic");
			// System.out.println(myActivity.postName);
		}
		return myActivity;
	}

	/**
	 * 解析JSON数据，转化为List<Duty>对象
	 * 
	 * @param createActivityJSON
	 * @return
	 * @throws JSONException
	 */
	public static List<Duty> praseToDuty(String createActivityJSON) throws JSONException {
		List<Duty> duties = new ArrayList<Duty>();
		JSONArray array = new JSONArray(createActivityJSON);
		if (array.length() == 1) {
			JSONObject object = array.getJSONObject(0);
			JSONArray dutyArray = object.getJSONArray("duty");
			for (int i = 0; i < dutyArray.length(); i++) {
				Duty duty = new Duty();
				System.out.println(dutyArray.length());
				JSONObject jsonObject = dutyArray.getJSONObject(i);
				duty.setDutyContent(jsonObject.getString("dutyContent"));
				duty.setDutyIntegral(jsonObject.getInt("dutyIntegral"));
				duty.setDutyName(jsonObject.getString("dutyName"));
				duty.setNeedNum(jsonObject.getInt("needNum"));
				// System.out.println(duty.getDutyContent());
				duties.add(duty);
			}
		}
		System.out.println("duty length:" + duties.size());
		return duties;
	}
}
