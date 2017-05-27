
<h1>红包系统说明：</h1>

红包系统是有maven3 + spring3.2.4.RELEASE + mybaits3.3.0 + mysql5.6 架构

整个系统的架构比较简单入门，凡是有一点java基础的朋友，很容易入门二次开发

<h2>安装说明:</h2>

1.0 安装java

	此系统要求java版本为1.6以上，目前最新版为java1.9 版本，比较常用版本为java1.8版本，为了配合后面maven最新版，官方建议安装java1.7或java1.8.首先在www.oracle.com 中下载对应操作系统的java版本，这里以windows 为例 ，然后傻瓜式下一步，配置环境变量JAVA_HOME和path,不会的可以查看
https://jingyan.baidu.com/article/925f8cb836b26ac0dde0569e.html(有详细说明)

2.0 安装 maven3 

	进入Apache 官方下载 maven3，目前最新版为maven3.5.0 下载地址为[这里](http://maven.apache.org/download.cgi),配置MAVEN_HOME环境变量和path 变量，这个和配置java环境变量是一致的这里不再详细说明，可以参考官方安装说明http://maven.apache.org/install.html

3.0 安装git版本

	这里对于git 不做过多的说明，不了解的朋友可以查看这里 https://git-scm.com/book/zh/v1/%E8%B5%B7%E6%AD%A5-%E5%AE%89%E8%A3%85-Git

4.0 MySQL安装

	MySQL版本没有要求，这里以MySQL5.7.18.1（目前为最新版） 为例  ，到这里https://dev.mysql.com/downloads/installer/ 傻瓜式安装不会的请参考这里 http://jingyan.baidu.com/article/e75057f2c7d4ebebc91a89cb.html?st=2&os=0&bd_page_type=1&net_type=2
	然后创建数据 redpacket 编码方式设置为UTF-8，根据E:\myproject\RedPacket\db 下的RedPacket.sql 创建 表信息


5.0 本地安装项目

	进入 要存放代码的目录，这里我们以E:\myproject为例
	
	cmd命令进入当前目录或右键点击git bash here 进入当前目录
	
	执行命令 git init (初始化git环境)
	
	克隆代码到本地执行命令 git clone https://github.com/java110/RedPacket.git
	
	修改在E:\myproject\RedPacket\etc目录下db.properties
	
	driver=com.mysql.jdbc.Driver
	url=jdbc\:mysql\://135.192.86.200\:31057/redpacket?useUnicode\=yes&characterEncoding\=UTF8&autoReconnect\=true
	username=red
	password=red\#123
	
	修改为自己的MySQL信息，135.192.86.200 修改为自己数据库地址 ，31057修改为自己数据库端口（如果数据库信息没有修改默认为3306）
	填写自己的数据库用户名和密码
	
	进入 RedPacket 目录 执行 mvn clean install 命令 
	
	在 target 目录下会有打包好的 war文件 (E:\myproject\RedPacket\target\RedPacket-0.0.1-SNAPSHOT.war)
	
6.0 下载安装Tomcat

	官方获取Tomcat，这里以Tomcat8为例 http://tomcat.apache.org/download-80.cgi 下载解压，将上一步生成的RedPacket-0.0.1-SNAPSHOT.war的war包
改名为RedPacket.war 放入到解压后的Tomcat目录下webapps 下，然后再bin 目录下运行 startup.bat 启动

7.0 访问项目

	该项目是对接微信和支付宝，所以必须要求有微信公众号或支付宝服务窗，微信对接地址为：http://ip:port/RedPacket/WGatewayController.indexPage
这里ip:port ip 对应公网ip port对应 公网开放端口，微信目前只支持80端口，所以这个对接时请修改为80，首页访问地址为：http://ip:port/RedPacket/WIndexPacketController.indexPage
支付宝对接地址为：http://ip:port/RedPacket/GatewayController.indexPage这里ip:port ip 对应公网ip port对应 公网开放端口，所以这个对接时请修改为80，首页访问地址为：http://ip:port/RedPacket/IndexPacketController.indexPage
	如果没有微信公众号和支付宝服务窗的情况下需要测试时，BaseController 类下的方法
	
	/**
	 * 登录校验
	 * 
	 * add by wuxw 2016-1-31
	 * 
	 * @param request
	 * @return
	 */
	public boolean loginValidate(HttpServletRequest request) {
		// 生产获取用户
		User user = this.getUser();
		// 测试获取用户
		 //User user = this.getTestUser();
		if (user == null) {
			return false;
		}
		return true;
	}
	
	注释 生产获取用户，放开测试用获取用户,并且在数据库表t_user表中插入数据如下：
	
	insert into t_user(name,passwd,phone,userId,wOpenId,zOpenId,email) 
	values('java110官方测试','123456','15897089471','10020160223001','123','','928255095@qq.com');
	
<h2>二次开发项目搭建:</h2>

	1.0 开发工具选择
	
	官方是由Myeclipse 8.5 开发，二次开发完全可以用eclipse 或任意版本的MyEclipse开发，配置maven信息，按maven导入就可以了，各位idea的朋友只需要导入项目是选择以eclipse方式导入也是可以的
	
	
	
	
	


