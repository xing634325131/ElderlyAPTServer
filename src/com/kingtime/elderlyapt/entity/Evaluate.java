package com.kingtime.elderlyapt.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xp
 * @created 2014年8月19日
 */
public class Evaluate {

	private int uid;
	private int activityId;
	private String content;
	private float credibility;
	private String createTime;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getCredibility() {
		return credibility;
	}

	public void setCredibility(float credibility) {
		this.credibility = credibility;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 将JSON数据转换为Evaluate实体
	 * 
	 * @param appraiseJson
	 * @return
	 * @throws JSONException
	 */
	public static List<Evaluate> praseToEvaluate(String appraiseJson) throws JSONException {
		List<Evaluate> evaluates = new ArrayList<Evaluate>();
		JSONArray array = new JSONArray(appraiseJson);
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			Evaluate evaluate = new Evaluate();
			evaluate.setUid(object.getInt("uid"));
			evaluate.setCredibility(object.getLong("credibility"));
			evaluates.add(evaluate);
		}
		
		System.out.println("prase evaluate size:" + evaluates.size());
		return evaluates;
	}

}
