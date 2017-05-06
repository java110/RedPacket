package com.java110.bean.exception;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 程序异常实体
 * 
 * @author wuxw
 * @date 2015-12-28
 * version 1.0
 */
public class RunExcBean {

	private Integer id; //主键
	
	private Integer excCode; //自定义编码，详情请查看Global.java
	
	private String excMsg; //自定义报错信息
	
	private String cause; // 异常引起
	
	private String message; //异常信息
	
	private String excInfo; //异常具体内容
	
	private Integer personId; //那个用户访问是出现异常
	
	private String url; //访问路径
	
	private Date createDate = new Date();
	
	private int month = Calendar.getInstance().get(Calendar.MONTH+1);
	
	private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExcCode() {
		return excCode;
	}

	public void setExcCode(Integer excCode) {
		this.excCode = excCode;
	}
	@Column(length = 500)
	public String getExcMsg() {
		return excMsg;
	}

	public void setExcMsg(String excMsg) {
		this.excMsg = excMsg;
	}
	@Column(length = 500)
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	@Column(length = 1000)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@Lob
	public String getExcInfo() {
		return excInfo;
	}

	public void setExcInfo(String excInfo) {
		this.excInfo = excInfo;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getUrl() {
		return url;
	}
	@Column(length = 1000)
	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
