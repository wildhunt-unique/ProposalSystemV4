/*
 Navicat Premium Data Transfer

 Source Server         : CouldServer
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 120.24.186.116:3306
 Source Schema         : proposal

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 16/05/2018 10:17:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for conference
-- ----------------------------
DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference`  (
  `con_id` int(255) NOT NULL,
  `con_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  INDEX `con_id`(`con_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `dept_id` int(10) NOT NULL,
  `dept_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `dept_manager` bigint(255) NULL DEFAULT NULL,
  `dept_master` bigint(255) NULL DEFAULT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE,
  INDEX `dept_master`(`dept_master`) USING BTREE,
  INDEX `dept_manager`(`dept_manager`) USING BTREE,
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`dept_master`) REFERENCES `member` (`member_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `department_ibfk_2` FOREIGN KEY (`dept_manager`) REFERENCES `member` (`member_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for deploy
-- ----------------------------
DROP TABLE IF EXISTS `deploy`;
CREATE TABLE `deploy`  (
  `prop_id` bigint(20) NOT NULL,
  `dept_helpId` int(10) NOT NULL,
  PRIMARY KEY (`prop_id`, `dept_helpId`) USING BTREE,
  INDEX `dept_id`(`dept_helpId`) USING BTREE,
  CONSTRAINT `dept_id` FOREIGN KEY (`dept_helpId`) REFERENCES `department` (`dept_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `prop_id` FOREIGN KEY (`prop_id`) REFERENCES `proposal` (`prop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for deputation
-- ----------------------------
DROP TABLE IF EXISTS `deputation`;
CREATE TABLE `deputation`  (
  `depu_id` int(10) NOT NULL,
  `depu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `manage_id` bigint(20) NULL DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`depu_id`) USING BTREE,
  INDEX `manage_id`(`manage_id`) USING BTREE,
  CONSTRAINT `deputation_ibfk_2` FOREIGN KEY (`manage_id`) REFERENCES `member` (`member_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `member_id` bigint(20) NOT NULL,
  `member_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `member_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `depu_id` int(10) NULL DEFAULT NULL,
  `member_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept_id` int(10) NULL DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`member_id`) USING BTREE,
  INDEX `member_ibfk_2`(`dept_id`) USING BTREE,
  INDEX `member_ibfk_1`(`depu_id`) USING BTREE,
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`depu_id`) REFERENCES `deputation` (`depu_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `member_ibfk_2` FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `news_id` int(11) NOT NULL,
  `news_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `news_content` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `news_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for proposal
-- ----------------------------
DROP TABLE IF EXISTS `proposal`;
CREATE TABLE `proposal`  (
  `prop_date` date NOT NULL,
  `prop_id` bigint(20) NOT NULL,
  `prop_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `prop_owner` bigint(20) NOT NULL,
  `prop_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '[save check notPsse passed process complete]',
  `prop_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `prop_last` date NULL DEFAULT NULL,
  `prop_content` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `prop_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'meetting proposal',
  `prop_passType` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `prop_deptId` int(11) NULL DEFAULT NULL,
  `prop_deplTime` date NULL DEFAULT NULL,
  `prop_implTime` date NULL DEFAULT NULL,
  `prop_impl` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `prop_alterIdea` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `prop_satisf` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `prop_handshake` int(255) NULL DEFAULT NULL,
  `prop_conId` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`prop_id`) USING BTREE,
  UNIQUE INDEX `prop_no`(`prop_no`) USING BTREE,
  INDEX `prop_owner`(`prop_owner`) USING BTREE,
  INDEX `prop_deptId`(`prop_deptId`) USING BTREE,
  INDEX `prop_conId`(`prop_conId`) USING BTREE,
  CONSTRAINT `proposal_ibfk_1` FOREIGN KEY (`prop_owner`) REFERENCES `member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `proposal_ibfk_2` FOREIGN KEY (`prop_deptId`) REFERENCES `department` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `proposal_ibfk_3` FOREIGN KEY (`prop_conId`) REFERENCES `conference` (`con_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for support
-- ----------------------------
DROP TABLE IF EXISTS `support`;
CREATE TABLE `support`  (
  `prop_id` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL,
  PRIMARY KEY (`prop_id`, `member_id`) USING BTREE,
  INDEX `member_id`(`member_id`) USING BTREE,
  CONSTRAINT `support_ibfk_1` FOREIGN KEY (`prop_id`) REFERENCES `proposal` (`prop_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `support_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
