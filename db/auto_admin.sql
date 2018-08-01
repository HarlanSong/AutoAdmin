/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.5.19 : Database - auto_admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`auto_admin` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `auto_admin`;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL DEFAULT '0',
  `order_no` int(11) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `menu` */

insert  into `menu`(`id`,`name`,`url`,`icon`,`parent_id`,`order_no`,`deleted`,`update_time`,`create_time`) values (1,'系统',NULL,NULL,0,0,0,'2018-05-23 19:47:02','2018-05-23 19:47:04'),(2,'菜单管理','/admin/menu/menuView',NULL,1,0,0,'2018-05-23 19:49:01','2018-05-23 19:49:03'),(3,'角色管理','/admin/sysRole/sysRoleView','',1,0,0,'2018-05-23 20:06:24','2018-05-23 20:06:24'),(4,'系统用户','/admin/sysUser/sysUserView','',1,0,0,'2018-05-23 20:10:14','2018-05-23 20:10:14');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `des` varchar(300) DEFAULT NULL COMMENT '描述',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`des`,`deleted`,`update_time`,`create_time`) values (1,'超级管理员','超级管理员',0,'2018-05-23 20:10:28','2018-05-23 19:47:42'),(2,'werw','qwer',1,'2018-08-01 11:54:33','2018-08-01 11:15:00'),(3,'234','234',1,'2018-08-01 11:54:52','2018-08-01 11:54:40');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`role_id`,`menu_id`,`deleted`,`update_time`,`create_time`) values (1,1,1,0,'2018-05-23 19:48:03','2018-05-23 19:48:04'),(2,1,2,0,'2018-05-23 19:49:19','2018-05-23 19:49:21'),(3,1,3,0,'2018-05-23 20:09:20','2018-05-23 20:09:20'),(4,1,4,0,'2018-05-23 20:10:28','2018-05-23 20:10:28'),(5,2,1,1,'2018-08-01 12:00:13','2018-08-01 11:15:00'),(6,3,1,1,'2018-08-01 12:00:13','2018-08-01 11:54:40');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL COMMENT '账号',
  `password` varchar(36) NOT NULL COMMENT '密码',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `salt` varchar(36) NOT NULL COMMENT '密码加密盐',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`account`,`password`,`name`,`salt`,`deleted`,`update_time`,`create_time`) values (1,'admin','5a8595d82228d6015aea78682df657f9','Admin','df54a192-bbf0-43ca-b4be-0484e56559bc',0,'2018-08-01 11:55:04','2018-05-23 19:46:10'),(2,'test','cb034e9c6c7346298f93d67a8eccf55f','test','eafc7fa6-4b78-4b0e-afc0-e53bc8a2a2f4',1,'2018-08-01 11:09:34','2018-08-01 10:56:58');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`deleted`,`update_time`,`create_time`) values (1,1,1,0,'2018-05-23 19:48:18','2018-05-23 19:48:20'),(2,2,1,0,'2018-08-01 10:56:58','2018-08-01 10:56:58'),(3,1,2,1,'2018-08-01 12:00:13','2018-08-01 11:46:10'),(4,1,3,1,'2018-08-01 12:00:13','2018-08-01 11:55:04');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
