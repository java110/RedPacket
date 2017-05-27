
<h1>红包系统说明：</h1>

红包系统是有maven3 + spring3.2.4.RELEASE + mybaits3.3.0 + mysql5.6 架构

整个系统的架构比较简单入门，凡是有一点java基础的朋友，很容易入门二次开发

<h2>安装说明:</h2>

1.0 安装java

	此系统要求java版本为1.6以上，目前最新版为java1.9 版本，比较常用版本为java1.8版本，为了配合后面maven最新版，官方建议安装java1.7或java1.8.
首先在www.oracle.com 中下载对应操作系统的java版本，这里以windows 为例 ，然后傻瓜式下一步，配置环境变量JAVA_HOME和path,不会的可以查看
https://jingyan.baidu.com/article/925f8cb836b26ac0dde0569e.html(有详细说明)

2.0 安装 maven3 

	进入Apache 官方下载 maven3，目前最新版为maven3.5.0 下载地址为(这里)[http://maven.apache.org/download.cgi],配置MAVEN_HOME环境变量
和path 变量，这个和配置java环境变量是一致的这里不再详细说明，可以参考官方安装说明http://maven.apache.org/install.html

3.0 安装git版本

	这里对于git 不做过多的说明，不了解的朋友可以查看这里 https://git-scm.com/book/zh/v1/%E8%B5%B7%E6%AD%A5-%E5%AE%89%E8%A3%85-Git

4.0 本地安装项目

	进入 要存放代码的目录，这里我们以E:\myproject为例
	
	cmd命令进入当前目录或右键点击git bash here 进入当前目录
	
	执行命令 git init (初始化git环境)
	
	克隆代码到本地执行命令 git clone https://github.com/java110/RedPacket.git
	
	进入 RedPacket 目录 执行 mvn clean install 命令 

