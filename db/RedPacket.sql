/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.5.54-log : Database - redpacket
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`redpacket` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `redpacket`;

/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
  `NAME` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sequence` */

insert  into `sequence`(`NAME`,`current_value`,`increment`) values ('accountId',2,1),('exchangeRedPacketId',1,1),('getRedPacketId',1,1),('MovieSeq',3,5),('payId',2,1),('sendRedPacketId',1,1),('userId',2,1);

/*Table structure for table `t_account` */

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `accountId` varchar(50) NOT NULL,
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `amount` varchar(10) DEFAULT NULL COMMENT '金额',
  `createDate` datetime DEFAULT NULL,
  `status` varchar(10) NOT NULL COMMENT '数据状态',
  `alipayAccount` varchar(50) DEFAULT NULL COMMENT '账户'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account` */

/*Table structure for table `t_account_trade_log` */

DROP TABLE IF EXISTS `t_account_trade_log`;

CREATE TABLE `t_account_trade_log` (
  `accountId` varchar(50) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `amount` varchar(20) NOT NULL,
  `months` int(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL COMMENT '数据状态',
  `accountFlag` varchar(10) DEFAULT NULL COMMENT '跳转标识',
  `channelId` varchar(50) DEFAULT NULL COMMENT '支付渠道'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account_trade_log` */

/*Table structure for table `t_exchangeredpacket` */

DROP TABLE IF EXISTS `t_exchangeredpacket`;

CREATE TABLE `t_exchangeredpacket` (
  `exchangeRedPacketId` varchar(50) NOT NULL,
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `money` varchar(20) NOT NULL COMMENT '金额',
  `alipayAccount` varchar(50) NOT NULL COMMENT '支付宝账号',
  `rate` varchar(10) NOT NULL,
  `status` varchar(10) NOT NULL COMMENT '数据状态',
  `createDate` datetime DEFAULT NULL,
  `months` int(11) DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `finishDate` datetime DEFAULT NULL,
  `dealUserId` int(11) NOT NULL COMMENT '处理用户Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_exchangeredpacket` */

/*Table structure for table `t_getredpacket` */

DROP TABLE IF EXISTS `t_getredpacket`;

CREATE TABLE `t_getredpacket` (
  `getRedPacketId` varchar(50) NOT NULL,
  `sendRedPacketId` int(11) NOT NULL,
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `money` varchar(20) NOT NULL COMMENT '金额',
  `status` varchar(10) NOT NULL COMMENT '数据状态',
  `createDate` datetime DEFAULT NULL,
  `months` int(11) DEFAULT NULL,
  `days` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_getredpacket` */

/*Table structure for table `t_payLog` */

DROP TABLE IF EXISTS `t_payLog`;

CREATE TABLE `t_payLog` (
  `payId` varchar(11) NOT NULL,
  `WIDtradeNo` varchar(50) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `dealDate` datetime DEFAULT NULL,
  `money` varchar(20) NOT NULL,
  `months` int(11) DEFAULT NULL,
  `personId` int(11) NOT NULL,
  `score` varchar(20) DEFAULT NULL,
  `status` varchar(10) NOT NULL COMMENT '数据状态',
  `wIDoutTradeNo` varchar(50) DEFAULT NULL COMMENT '外部交易流水',
  `redirectFlag` varchar(10) DEFAULT NULL COMMENT '跳转标识',
  `channelId` varchar(50) DEFAULT NULL COMMENT '支付渠道',
  `returnPayUrl` varchar(200) DEFAULT NULL COMMENT '跳转URL'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_payLog` */

/*Table structure for table `t_sendredpacket` */

DROP TABLE IF EXISTS `t_sendredpacket`;

CREATE TABLE `t_sendredpacket` (
  `sendRedPacketId` varchar(50) NOT NULL,
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `redType` varchar(20) NOT NULL COMMENT '红包类型',
  `copies` varchar(20) DEFAULT NULL,
  `money` varchar(20) NOT NULL COMMENT '金额',
  `status` varchar(10) NOT NULL COMMENT '数据状态',
  `userOrMerchant` varchar(10) NOT NULL COMMENT '用户还是商家',
  `productId` int(11) DEFAULT NULL COMMENT '产品Id',
  `createDate` datetime DEFAULT NULL,
  `months` int(11) DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `finishDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_sendredpacket` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `userId` varchar(50) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `sex` varchar(10) DEFAULT NULL COMMENT '用户性别',
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `passwd` varchar(20) DEFAULT NULL,
  `wOpenId` varchar(50) DEFAULT NULL,
  `zOpenId` varchar(50) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `months` int(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL COMMENT '数据状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`userId`,`name`,`sex`,`phone`,`email`,`passwd`,`wOpenId`,`zOpenId`,`createDate`,`days`,`months`,`status`) values ('702017050700002','学文','m',NULL,NULL,NULL,NULL,'20881009290965360287569652612626','2017-05-07 00:00:00',7,5,'0');

/*Table structure for table `td_s_admin` */

DROP TABLE IF EXISTS `td_s_admin`;

CREATE TABLE `td_s_admin` (
  `id` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_s_admin` */

/* Function  structure for function  `currval` */

/*!50003 DROP FUNCTION IF EXISTS `currval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`red`@`%` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int(11)
BEGIN
	 DECLARE VALUE INTEGER;
	  SET VALUE = 0;
	  SELECT current_value INTO VALUE
	  FROM sequence
	  WHERE NAME = seq_name;
	  RETURN VALUE;
    END */$$
DELIMITER ;

/* Function  structure for function  `nextval` */

/*!50003 DROP FUNCTION IF EXISTS `nextval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`red`@`%` FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(11)
BEGIN
	UPDATE sequence
	   SET          current_value = current_value + increment
	   WHERE NAME = seq_name;
	   RETURN currval(seq_name);
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
