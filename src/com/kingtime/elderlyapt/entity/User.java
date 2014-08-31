package com.kingtime.elderlyapt.entity;

/**
 * 用户实体
 * 
 * @author xp
 * 
 * @created 2014-4-24
 */
public class User {
	private int uid;
	private int roleId;
	private int locationId;
	private String name;
	private String pwd;
	private String gender;
	private String email;
	private String phone;
	private String resPhone;
	private int integral;
	private float credibility;
	private String interest;
	private String photoName;
	private int evaluateTimes;
	private String address;
	private String createTime;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getResPhone() {
		return resPhone;
	}

	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public float getCredibility() {
		return credibility;
	}

	public void setCredibility(float credibility) {
		this.credibility = credibility;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public int getEvaluateTimes() {
		return evaluateTimes;
	}

	public void setEvaluateTimes(int evaluateTimes) {
		this.evaluateTimes = evaluateTimes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/*
	 * 将对象转为JSON对象
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[{");
		builder.append("uid:").append(this.uid).append(',');
		builder.append("pwd:\"").append(this.pwd).append("\",");
		builder.append("photoName:\"").append(this.photoName).append("\",");
		builder.append("name:\"").append(this.name).append("\",");
		builder.append("interest:\"").append(this.interest).append("\",");
		builder.append("gender:\"").append(this.gender).append("\",");
		builder.append("email:\"").append(this.email).append("\",");
		builder.append("phone:\"").append(this.phone).append("\",");
		builder.append("roleId:").append(this.roleId).append(",");
		builder.append("locationId:").append(this.locationId).append(",");
		builder.append("resPhone:\"").append(this.resPhone).append("\",");
		builder.append("evaluateTimes:").append(this.evaluateTimes).append(",");
		builder.append("integral:").append(this.integral).append(",");
		builder.append("address:\"").append(this.address).append("\",");
		builder.append("credibility:").append(this.credibility).append(",");
		builder.append("createTime:\"").append(this.createTime).append("\"");
		builder.append("}]");
		return builder.toString();
	}
}
