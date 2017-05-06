package com.java110.common;

/**
 * 全局配置类
 * 
 * @author wuxw
 * @date 2015-11-28 version 1.0
 */
public class Global {
	public final static String LOGPLUS = "+++++++++++++++++++++++++++";

	public final static int PAGE = 1; // 页数，默认是第一页

	public final static int ROWS = 10; // 每页显示的记录条数，默认为10

	public final static int TOTAL = 0; // 总数据数
	// 数据初始状态（数据有效状态）
	public final static String INIT_DATA_STATUS = "0";
	// 数据过时状态（数据无效状态）
	public final static String DEL_DATA_STATUS = "1";

	public final static String TEST = "TEST";

	// 视频缩略图上传目录

	public final static String VEDIO_IMG_UPLAOD_DIR = "/upload/image";

	public final static String VEDIO_IMG_VIEW_DIR = "/upload/image/";

	// 默认相关视频 defaultFlag 为 1
	public final static Integer DEFAULT_RELATED_VEDIO = 1;

	// 相关视频页面显示个数
	public final static Integer PAGE_VEW_RELATED_VEDIO_COUNT = 4;

	public final static String NOTICE_TYPE_SHOW_INDEX_2 = "2";

	// 配置表中domain 为MAIL 为发送邮件发方的地址

	public final static String MAIL = "MAIL";

	// 配置表中domain 为SITEMAP 为导航大类

	public final static String SITEMAP = "SITEMAP";

	// 导航大类在页面中支持的个数
	public final static Integer SITEMAP_TITLE_SIZE = 5;

	// 导航小类在页面中支持的个数
	public final static Integer SITEMAP_CONTENT_SIZE = 6;

	// 用户中心做导航在表td_s_codemapping 表中的 domain
	public final static String USER_NAV = "USER_NAV";

	public final static String WEB_INFO_DOMAIN = "WEB_INFO_DOMAIN";

	// 站点名称
	public final static String WEB_NAME = "WEB_NAME";

	// 站点url
	public final static String WEB_URL = "WEB_URL";

	// 站点静态资源url(js css)
	public final static String WEB_STATIC_URL = "WEB_STATIC_URL";

	public final static String WEB_IMG_URL = "WEB_IMG_URL";

	// 文件上传路径
	public final static String WEB_UPLOAD_PATH = "WEB_UPLOAD_PATH";

	// ---------------------------------------实例状态相关开始----------------------------------//
	public static final String DEAL_W = "W";// 待处理

	public static final String DEAL_C = "C"; // 处理完成

	public static final String U = "U";// 用戶

	public static final String M = "M";// 商家

	public static final String USING = "0"; // 数据在用

	public static final String UNUSING = "1"; // 用户数据失效

	// ---------------------------------------实例状态相关结束----------------------------------//

	// ---------------------------------------渠道开始----------------------------------//

	public static final String CHANNEL_B = "B"; // 包红包渠道

	public static final String CHANNEL_F = "F"; // 发红包渠道

	public static final String CHANNEL_D = "D"; // 兑红包渠道

	public static final String CHANNEL_Q = "Q"; // 兑红包渠道

	// ---------------------------------------渠道结束----------------------------------//

	// ---------------------------------------账户标志开始(充值，还是消费)----------------------------------//

	public static final String ACCOUNT_UP = "U";// 增加

	public static final String ACCOUNT_DOWN = "D";// 减少

	// ---------------------------------------账户标志结束----------------------------------//

	public static final String MONEY_TYPE = "1,2";// 提现类型

	public static final String MONEY_TYPE_2 = "2"; // 强红包渠道

	public static final String RATE = "1.0";

	public static final Double MONEY_200 = 200.00;

	// 系统session中用户
	public static String PERSON = "PERSON";

	// 商家
	public static String MERCHANT = "MERCHANT";

	// 站点信息
	public static String WEBINFO = "WEBINFO";

	// 用户信息
	public static String USERINFO = "USERINFO";

	public static final String ERRORMSG = "errorMsg"; // 站点错误变量

	public static final String MSGINFO = "MSGINFO";
	
	public static final String CLIENT_FLAG_APP = "APP";	

	// ---------------------------------------向前台返回相关变量定义开始----------------------------------//

	public static final String RETURN_OK = "200"; // 返回成功

	public static final String RETURN_ERROR = "199"; // 返回失败

	// ---------------------------------------向前台返回相关变量定义结束----------------------------------//

	// ---------------------------------------红包类型相关变量定义开始----------------------------------//

	public static final String RED_TYPE_01 = "red_type_01";// 随机红包

	public static final String RED_TYPE_02 = "red_type_02";// 平均红包

	// ---------------------------------------红包类型相关变量定义结束----------------------------------//

	// ---------------------------------------站点相关信息定义开始----------------------------------//

	 public static final String DOMAIN = "http://www.java110.com/RedPacket";

	// public static final String DOMAIN = "http://135.192.74.6:8080";

//	public static final String DOMAIN = "http://192.168.43.5:8080";

	public static final String IMG_DOMAIN = "http://www.java110.com";

	public static final String STATIC_DOMAIN = "http://www.java110.com";

	// ---------------------------------------站点相关信息定义开始----------------------------------//

	public static final String RED_MONEY = "money";

	public static final String GET_PACKET_USER = "GET_PACKET_USER";

	public static final String TRADE_NO = "10000";

	// ---------------------------------------微信部分定义开始----------------------------------//

	public static final String TOKEN = "weixin";

	public static final String MSG_TYPE_TEXT = "text";
	public static final String MSG_TYPE_LOCATION = "location";
	public static final String MSG_TYPE_VOICE = "voice";
	public static final String MSG_TYPE_VIDEO = "video";
	public static final String MSG_TYPE_IMAGE = "image";
	public static final String MSG_TYPE_EVENT = "event";
	public static final String MSG_REGX_TYPE_PART = "0";
	public static final String MSG_REGX_TYPE_ALL = "1";
	public static final String MSG_MENU_ID = "menu";
	public static final String GZ = "1";// 关注
	public static final String QXGZ = "0";// 取消关注
	public static final String DEL = "0";
	public static final String NODEL = "1";

	public static final String OPEN_AUTH = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URL&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	public static final String APPID = "wx46155e147fdb92e4";
	public static final String APPSECRET = "b4c1ff90aa0cb33a88127cf69d16c049";
	public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
	public static final String GET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public static final String GET_USER_INFO_SNSURL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public static final String WX_CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// ---------------------------------------微信部分定义结束----------------------------------//
	public static final String APP_OPEN_AUTH = "http://app.java110.com/oauth2Controller/authorize?appid=APPID&redirect_uri=REDIRECT_URL&response_type=code&scope=SCOPE&state=STATE&web=main#wechat_redirect";
	// public static final String
	// OPEN_AUTH="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URL&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public static final String APP_APPID = "1";
	public static final String APP_APPSECRET = "123456";
	public static final String APP_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
	public static final String APP_GET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	public static final String APP_GET_ACCESS_TOKEN_URL = "http://app.java110.com/oauth2Controller/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public static final String APP_GET_USER_INFO_SNSURL = "http://app.java110.com/oauth2Controller/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public static final String APP_WX_CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// ---------------------------------------app部分定义结束----------------------------------//

	// ---------------------------------------分享部分定义开始----------------------------------//

	public static final String SHARE_DESC = "测试红包来啦，红包系统正在内测中，抢到的红包不给提现";
	public static final String SHARE_ICO = "http://img.java110.com/RedImg/redPacketImg.png";
	public static final String SHARE_TITLE = "测试红包来啦";
	public static final String SHARE_URL = Global.DOMAIN
			+ "/WGetPacketController.indexPage?sendRedPacketId=SENDREDPACKETID";

	public static final String shareTitle = "java视频教程-java110";
	public static final String shareIco = "http://static.java110.com/img/java110_logo.png";
	public static final String shareDesc = "java110是由love编程团队提供的java相关视频分享平台，主要包括java视频，java项目，移动开发视频，大数据视频，网页设计视频和数据库视频教程，帮助每一位编程爱好者快速成长。";
	public static final String shareUrl = "http://www.java110.com";
	// ---------------------------------------分享部分定义结束----------------------------------//
}
