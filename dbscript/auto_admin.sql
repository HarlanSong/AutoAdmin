/*
 Navicat MySQL Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost
 Source Database       : auto_admin

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : utf-8

 Date: 05/23/2018 20:12:20 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `menu`
-- ----------------------------
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

-- ----------------------------
--  Records of `menu`
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES ('1', '系统', null, null, '0', '0', '0', '2018-05-23 19:47:02', '2018-05-23 19:47:04'), ('2', '菜单管理', '/admin/menu/menuView', null, '1', '0', '0', '2018-05-23 19:49:01', '2018-05-23 19:49:03'), ('3', '角色管理', '/admin/role/roleView', '', '1', '0', '0', '2018-05-23 20:06:24', '2018-05-23 20:06:24'), ('4', '系统用户', '/admin/sysUser/sysUserView', '', '1', '0', '0', '2018-05-23 20:10:14', '2018-05-23 20:10:14');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `des` varchar(300) DEFAULT NULL COMMENT '描述',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '超级管理员', '0', '2018-05-23 20:10:28', '2018-05-23 19:47:42');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1', '0', '2018-05-23 19:48:03', '2018-05-23 19:48:04'), ('2', '1', '2', '0', '2018-05-23 19:49:19', '2018-05-23 19:49:21'), ('3', '1', '3', '0', '2018-05-23 20:09:20', '2018-05-23 20:09:20'), ('4', '1', '4', '0', '2018-05-23 20:10:28', '2018-05-23 20:10:28');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', '5a8595d82228d6015aea78682df657f9', 'Admin', 'df54a192-bbf0-43ca-b4be-0484e56559bc', '0', '2018-05-23 19:46:08', '2018-05-23 19:46:10');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '0', '2018-05-23 19:48:18', '2018-05-23 19:48:20');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
