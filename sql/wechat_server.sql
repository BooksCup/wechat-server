/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.27-log : Database - wechat_server
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wechat_server` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `wechat_server`;

/*Table structure for table `t_friend_apply` */

DROP TABLE IF EXISTS `t_friend_apply`;

CREATE TABLE `t_friend_apply` (
  `apply_id` varchar(32) NOT NULL COMMENT '好友申请表主键',
  `apply_from_user_id` varchar(32) DEFAULT NULL COMMENT '申请人用户ID',
  `apply_to_user_id` varchar(32) DEFAULT NULL COMMENT '被申请人用户ID',
  `apply_remark` varchar(200) DEFAULT NULL COMMENT '申请备注',
  `apply_status` varchar(2) DEFAULT '0' COMMENT '"0":未处理 "1":"同意"',
  `apply_create_time` varchar(20) DEFAULT NULL COMMENT '申请时间',
  `apply_modify_time` varchar(20) DEFAULT NULL COMMENT '修改时间/处理时间',
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_friends_circle` */

DROP TABLE IF EXISTS `t_friends_circle`;

CREATE TABLE `t_friends_circle` (
  `circle_id` varchar(32) NOT NULL COMMENT '朋友圈表主键',
  `circle_user_id` varchar(32) DEFAULT NULL COMMENT '发布人用户ID',
  `circle_content` varchar(1024) DEFAULT NULL COMMENT '朋友圈内容',
  `circle_photos` varchar(1024) DEFAULT NULL COMMENT '朋友圈图片(最多九张,jsonList格式)',
  `circle_create_time` varchar(20) DEFAULT NULL COMMENT '发布时间',
  `circle_timestamp` bigint(20) DEFAULT NULL COMMENT '发布时间戳',
  PRIMARY KEY (`circle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_friends_circle_like` */

DROP TABLE IF EXISTS `t_friends_circle_like`;

CREATE TABLE `t_friends_circle_like` (
  `like_id` varchar(32) DEFAULT NULL COMMENT '朋友圈点赞表主键',
  `like_circle_id` varchar(32) DEFAULT NULL COMMENT '朋友圈表主键',
  `like_user_id` varchar(32) DEFAULT NULL COMMENT '用户表主键',
  `like_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group` */

DROP TABLE IF EXISTS `t_group`;

CREATE TABLE `t_group` (
  `group_id` varchar(32) NOT NULL COMMENT '群组表主键',
  `group_owner` varchar(32) DEFAULT NULL COMMENT '群主username',
  `group_name` varchar(1000) DEFAULT NULL COMMENT '群组名',
  `group_desc` varchar(250) DEFAULT NULL COMMENT '群组描述',
  `group_create_time` varchar(20) DEFAULT NULL COMMENT '群组创建时间',
  `group_update_time` varchar(20) DEFAULT NULL COMMENT '群组修改时间',
  `group_jim_id` bigint(20) DEFAULT NULL COMMENT '群组ID(极光)',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group_members` */

DROP TABLE IF EXISTS `t_group_members`;

CREATE TABLE `t_group_members` (
  `members_id` varchar(32) NOT NULL COMMENT '群组组员表',
  `members_group_id` varchar(32) DEFAULT NULL COMMENT '群组主键',
  `members_user_id` varchar(32) DEFAULT NULL COMMENT '用户主键',
  `members_is_owner` tinyint(1) DEFAULT NULL COMMENT '0:普通群成员 1:群主',
  `members_create_time` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`members_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `message_id` varchar(32) NOT NULL COMMENT '消息表主键',
  `message_target_type` varchar(20) NOT NULL COMMENT '发送目标类型 single:个人 group:群组 chatroom:聊天室',
  `message_from_type` varchar(20) NOT NULL COMMENT '发送消息者身份 当前只限admin用户，必须先注册admin用户',
  `message_msg_type` varchar(20) NOT NULL COMMENT '发消息类型 text:文本，image:图片, custom:自定义消息(msg_body为json对象即可，服务端不做校验) voice:语音',
  `message_from_id` varchar(32) NOT NULL COMMENT '发送者的username',
  `message_target_id` varchar(32) NOT NULL COMMENT '目标id single填username group填Groupid chatroom填chatroomid',
  `message_body` varchar(4096) DEFAULT NULL COMMENT 'Json对象的消息体 限制为4096byte',
  `message_create_time` varchar(20) DEFAULT NULL COMMENT '消息创建时间',
  `message_jim_id` bigint(20) DEFAULT NULL COMMENT '消息ID(极光返回)',
  `message_jim_ctime` bigint(20) DEFAULT NULL COMMENT '消息创建时间(极光返回)',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户表主键',
  `user_wx_id` varchar(200) DEFAULT NULL COMMENT '用户微信号',
  `user_nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `user_avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `user_phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `user_sex` varchar(2) DEFAULT NULL COMMENT '"1":"男" "2":"女"',
  `user_password` varchar(50) DEFAULT NULL COMMENT '密码',
  `user_im_password` varchar(20) DEFAULT NULL COMMENT '极光密码',
  `user_sign` varchar(100) DEFAULT NULL COMMENT '签名',
  `user_qr_code` varchar(200) DEFAULT NULL COMMENT '用户二维码地址',
  `user_lastest_circle_photos` varchar(1024) DEFAULT NULL COMMENT '最新n张朋友圈照片,n=4',
  `user_create_time` varchar(20) DEFAULT NULL COMMENT '用户创建时间',
  `user_modify_time` varchar(20) DEFAULT NULL COMMENT '用户修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user_rela` */

DROP TABLE IF EXISTS `t_user_rela`;

CREATE TABLE `t_user_rela` (
  `rela_id` varchar(32) NOT NULL COMMENT '用户关系表主键',
  `rela_user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `rela_friend_id` varchar(32) DEFAULT NULL COMMENT '好友ID',
  `rela_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`rela_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
