package com.java110.bean;

/**
 * 异步请求返回json对象
 * 
 * @author wuxw
 * @date 2016-2-16 version 1.0
 */
public class OutJson {
	// 返回编码
	private String resultCode;

	// 返回内容
	private String resultInfo;

	// 分享url
	private String shareUrl;

	// 分享title
	private String shareTitle;

	// 分享ico
	private String shareIco;

	// 分享描述
	private String shareDesc;

	// 金额
	private Double money;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareIco() {
		return shareIco;
	}

	public void setShareIco(String shareIco) {
		this.shareIco = shareIco;
	}

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ResultCode: " + this.getResultCode() + " resultInfo:"
				+ this.getResultInfo() + " ShareDesc:" + this.getShareDesc()
				+" ShareIco:" + this.getShareIco() + " ShareTitle:" +this.getShareTitle()
				+" ShareUrl:" + this.getShareUrl() + " Money:"+this.getMoney();
	}

}
