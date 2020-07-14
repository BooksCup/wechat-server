/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.43-log : Database - wechat_server
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

/*Table structure for table `t_friends_circle_comment` */

DROP TABLE IF EXISTS `t_friends_circle_comment`;

CREATE TABLE `t_friends_circle_comment` (
  `comment_id` varchar(32) NOT NULL COMMENT '朋友圈评论表主键',
  `comment_circle_id` varchar(32) DEFAULT NULL COMMENT '朋友圈主键',
  `comment_user_id` varchar(32) DEFAULT NULL COMMENT '评论人主键',
  `comment_reply_to_user_id` varchar(32) DEFAULT NULL COMMENT '回复用户主键',
  `comment_content` varchar(1024) DEFAULT NULL COMMENT '评论内容',
  `comment_create_time` varchar(20) DEFAULT NULL COMMENT '评论时间',
  `comment_delete_flag` varchar(2) DEFAULT '0' COMMENT '"0":"未删除" "1":"已删除"',
  `comment_delete_time` varchar(20) DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`comment_id`)
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

/*Table structure for table `t_people_nearby` */

DROP TABLE IF EXISTS `t_people_nearby`;

CREATE TABLE `t_people_nearby` (
  `nearby_id` varchar(32) NOT NULL COMMENT '附近的人表主键',
  `nearby_user_id` varchar(32) DEFAULT NULL COMMENT '用户表主键',
  `nearby_longitude` varchar(100) DEFAULT NULL COMMENT '经度',
  `nearby_latitude` varchar(100) DEFAULT NULL COMMENT '纬度',
  `nearby_region` varchar(100) DEFAULT NULL COMMENT '区域',
  `nearby_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `nearby_modify_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`nearby_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_search_history` */

DROP TABLE IF EXISTS `t_search_history`;

CREATE TABLE `t_search_history` (
  `search_id` varchar(32) NOT NULL COMMENT '搜索历史表主键',
  `search_user_id` varchar(32) DEFAULT NULL COMMENT '用户表主键',
  `search_keyword` varchar(200) DEFAULT NULL COMMENT '搜索关键字',
  `search_create_time` varchar(20) DEFAULT NULL COMMENT '搜索时间',
  PRIMARY KEY (`search_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_sys_log` */

DROP TABLE IF EXISTS `t_sys_log`;

CREATE TABLE `t_sys_log` (
  `log_id` varchar(32) NOT NULL COMMENT '系统日志表主键',
  `log_type` varchar(20) DEFAULT NULL COMMENT '日志类型',
  `log_user_id` varchar(32) DEFAULT NULL COMMENT '日志相关用户表主键',
  `log_content` varchar(300) DEFAULT NULL COMMENT '日志内容',
  `log_create_time` varchar(20) DEFAULT NULL COMMENT '日志创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_temp_city` */

DROP TABLE IF EXISTS `t_temp_city`;

CREATE TABLE `t_temp_city` (
  `city_id` varchar(32) DEFAULT NULL COMMENT '市表主键',
  `province_id` varchar(32) DEFAULT NULL COMMENT '省表主键',
  `city_name` varchar(100) DEFAULT NULL COMMENT '市名',
  `city_seq` int(5) DEFAULT NULL COMMENT '排序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_temp_district` */

DROP TABLE IF EXISTS `t_temp_district`;

CREATE TABLE `t_temp_district` (
  `district_id` varchar(32) NOT NULL COMMENT '区表主键',
  `city_id` varchar(32) DEFAULT NULL COMMENT '市表主键',
  `district_name` varchar(100) DEFAULT NULL COMMENT '区名',
  `district_post_code` varchar(50) DEFAULT NULL COMMENT '区邮编',
  `district_seq` int(5) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_temp_province` */

DROP TABLE IF EXISTS `t_temp_province`;

CREATE TABLE `t_temp_province` (
  `province_id` varchar(32) NOT NULL COMMENT '省表主键',
  `province_name` varchar(100) DEFAULT NULL COMMENT '省名',
  `province_seq` int(5) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户表主键',
  `user_wx_id` varchar(200) DEFAULT NULL COMMENT '用户微信号',
  `user_nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `user_avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `user_phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `user_email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `user_qq_id` varchar(50) DEFAULT NULL COMMENT '用户QQ号',
  `user_qq_password` varchar(200) DEFAULT NULL COMMENT '用户QQ密码',
  `user_sex` varchar(2) DEFAULT NULL COMMENT '"1":"男" "2":"女"',
  `user_password` varchar(50) DEFAULT NULL COMMENT '密码',
  `user_im_password` varchar(20) DEFAULT NULL COMMENT '极光密码',
  `user_sign` varchar(100) DEFAULT NULL COMMENT '签名',
  `user_qr_code` varchar(200) DEFAULT NULL COMMENT '用户二维码地址',
  `user_is_email_linked` varchar(2) DEFAULT '0' COMMENT '用户邮箱是否绑定 0:未绑定 1:未验证 2:已验证',
  `user_is_qq_id_linked` varchar(2) DEFAULT NULL COMMENT '用户QQ号是否绑定',
  `user_lastest_circle_photos` varchar(1024) DEFAULT NULL COMMENT '最新n张朋友圈照片,n=4',
  `user_wx_id_modify_flag` varchar(2) DEFAULT '0' COMMENT '微信号修改标记',
  `user_last_login_time` varchar(20) DEFAULT NULL COMMENT '用户最后一次登录时间',
  `user_create_time` varchar(20) DEFAULT NULL COMMENT '用户创建时间',
  `user_modify_time` varchar(20) DEFAULT NULL COMMENT '用户修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user_address` */

DROP TABLE IF EXISTS `t_user_address`;

CREATE TABLE `t_user_address` (
  `address_id` varchar(32) NOT NULL COMMENT '地址表主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户表主键',
  `address_name` varchar(100) DEFAULT NULL COMMENT '收货人',
  `address_phone` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `address_province` varchar(20) DEFAULT NULL COMMENT '地区信息-省',
  `address_city` varchar(20) DEFAULT NULL COMMENT '地区信息-市',
  `address_district` varchar(20) DEFAULT NULL COMMENT '地区信息-区',
  `address_detail` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `address_post_code` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `address_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `address_modify_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user_login_device` */

DROP TABLE IF EXISTS `t_user_login_device`;

CREATE TABLE `t_user_login_device` (
  `device_id` varchar(32) NOT NULL COMMENT '登录设备表主键',
  `device_user_id` varchar(32) DEFAULT NULL COMMENT '用户表主键',
  `device_phone_brand` varchar(100) DEFAULT NULL COMMENT '手机品牌',
  `device_phone_model` varchar(100) DEFAULT NULL COMMENT '手机型号',
  `device_phone_model_alias` varchar(100) DEFAULT NULL COMMENT '手机型号别名(用户备注)',
  `device_os` varchar(100) DEFAULT NULL COMMENT '操作系统版本',
  `device_resolution` varchar(100) DEFAULT NULL COMMENT '分辨率',
  `device_operator` varchar(100) DEFAULT NULL COMMENT '运营商信息',
  `device_login_time` varchar(20) DEFAULT NULL COMMENT '设备登录时间',
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user_rela` */

DROP TABLE IF EXISTS `t_user_rela`;

CREATE TABLE `t_user_rela` (
  `rela_id` varchar(32) NOT NULL COMMENT '用户关系表主键',
  `rela_user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `rela_friend_id` varchar(32) DEFAULT NULL COMMENT '好友ID',
  `rela_status` varchar(2) DEFAULT NULL COMMENT '"0":陌生人 "1":好友 "2":黑名单',
  `rela_friend_phone` varchar(20) DEFAULT NULL COMMENT '好友手机(设置备注和标签中添加,和t_user表中的user_phone无关系)',
  `rela_friend_remark` varchar(200) DEFAULT NULL COMMENT '好友备注(设置备注和标签中添加)',
  `rela_friend_desc` varchar(200) DEFAULT NULL COMMENT '好友描述(设置备注和标签中添加)',
  `rela_is_star_friend` varchar(1) DEFAULT '0' COMMENT '是否星标好友 "0":否 "1":是',
  `rela_auth` varchar(1) DEFAULT '0' COMMENT '朋友权限 "0":聊天、朋友圈、微信运动  "1":仅聊天',
  `rela_not_see_me` varchar(1) DEFAULT '0' COMMENT '朋友圈和视频动态 "0":可以看我 "1":不让他看我',
  `rela_not_see_him` varchar(1) DEFAULT '0' COMMENT '朋友圈和视频动态 "0":可以看他 "1":不看他',
  `rela_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `rela_update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`rela_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_verification_code` */

DROP TABLE IF EXISTS `t_verification_code`;

CREATE TABLE `t_verification_code` (
  `vc_id` varchar(32) NOT NULL COMMENT '验证码表主键',
  `vc_phone` varchar(100) DEFAULT NULL COMMENT '手机号',
  `vc_code` varchar(50) DEFAULT NULL COMMENT '验证码内容',
  `vc_service_type` varchar(50) DEFAULT NULL COMMENT '验证码业务类型 "0":"登录"',
  `vc_create_time` varchar(20) DEFAULT NULL COMMENT '验证码创建时间',
  `vc_expire_time` varchar(20) DEFAULT NULL COMMENT '验证码失效时间',
  PRIMARY KEY (`vc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
