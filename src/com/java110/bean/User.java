package com.java110.bean;

import java.util.Date;

/**
 * 用户实体
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
public class User {

	private String userId;
	
	private String name;
	
	private String sex;
	
	private String phone;
	
	private String email;
	
	private String passwd;
	
	private String wOpenId;
	
	private String zOpenId;
	
	private Date createDate;
	
	private int months;
	
	private int days;
	
	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getwOpenId() {
		return wOpenId;
	}

	public void setwOpenId(String wOpenId) {
		this.wOpenId = wOpenId;
	}

	public String getzOpenId() {
		return zOpenId;
	}

	public void setzOpenId(String zOpenId) {
		this.zOpenId = zOpenId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
