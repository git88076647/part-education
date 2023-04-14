/*
 Navicat Premium Data Transfer

 Source Server         : home_master
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : nas.tanghx.com:33061
 Source Schema         : czylplatform

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 13/03/2020 18:26:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `options` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (1, 'sys_org', '组织', 'SysOrg', 'crud', 'com.ruoyi.project.system', 'system', 'org', '组织', 'ruoyi', NULL, '1', '2019-12-02 10:45:23', '1', NULL, NULL, 1);
INSERT INTO `gen_table` VALUES (2, 'sys_psndoc', '人员信息', 'SysPsndoc', 'crud', 'com.ruoyi.project.system', 'system', 'psndoc', '人员管理', 'tanghx', '{}', '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', NULL, 1);
INSERT INTO `gen_table` VALUES (6, 'sys_file', '文件管理', 'SysFile', 'crud', 'com.czyl.project.system', 'system', 'file', '文件管理', 'tanghx', '{}', '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', NULL, 4);
INSERT INTO `gen_table` VALUES (9, 'sys_job', '定时任务调度表', 'SysJob', 'crud', 'com.czyl.project.job', 'job', 'job', '定时任务调度', 'tanghx', '{}', '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', NULL, 2);
INSERT INTO `gen_table` VALUES (10, 'sys_job_log', '定时任务调度日志表', 'SysJobLog', 'crud', 'com.czyl.project.job', 'job', 'joblog', '定时任务调度日志', 'tanghx', '{}', '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', NULL, 3);
INSERT INTO `gen_table` VALUES (11, 'sys_org2', '组织演示', 'SysOrg2', 'crud', 'com.czyl.project.trans', 'trans', 'org2', '组织演示', 'tanghx', '{}', '1', '2020-03-04 03:34:56', NULL, '2020-03-04 03:43:40', NULL, 3);
INSERT INTO `gen_table` VALUES (12, 'sys_org_lulu', '组织lulu', 'SysOrgLulu', 'crud', 'com.czyl.project.translulu', 'translulu', 'orglulu', '组织_陆璐', 'tanghx', '{}', '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:26', NULL, 8);
INSERT INTO `gen_table` VALUES (13, 'sys_org_xieh', '组织xieh', 'SysOrgXieh', 'crud', 'com.czyl.xieh.org', 'xiehorg', 'xiehorg', '组织xieh', 'tanghx', '{}', '1', '2020-03-04 07:30:07', NULL, '2020-03-04 07:34:42', NULL, 2);
INSERT INTO `gen_table` VALUES (14, 'sys_org_tangsu', '组织', 'SysOrgTangsu', 'crud', 'com.czyl.project.tangsutest', 'tangsutest', 'tangsutest', '组织tangsu', 'tanghx', '{}', '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', NULL, 3);
INSERT INTO `gen_table` VALUES (15, 'sys_org_shizheng', '组织_师政', 'SysOrgShizheng', 'crud', 'com.czyl.project.trans', 'trans', 'shizheng', '组织_师政', 'tanghx', '{}', '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:25', NULL, 3);
INSERT INTO `gen_table` VALUES (16, 'sys_org_yw', '杨卫组织', 'SysOrgYw', 'crud', 'com.czyl.project.ywtrain', 'ywtrain', 'yw', '杨卫组织演示', 'yangwei', '{}', '1', '2020-03-04 15:31:54', NULL, '2020-03-04 15:47:42', NULL, 5);
INSERT INTO `gen_table` VALUES (19, 'temp_emc_record', '', 'TempEmcRecord', 'crud', 'com.czyl.project.file', 'file', 'record', NULL, 'tanghx', NULL, '1', '2020-03-11 11:30:46', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 289 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (1, '1', 'org_id', '组织id', 'bigint(20)', 'Long', 'orgId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (2, '1', 'parent_id', '上级组织id', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (3, '1', 'ancestors', '祖级列表', 'varchar(50)', 'String', 'ancestors', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (4, '1', 'org_code', '组织编码', 'varchar(50)', 'String', 'orgCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (5, '1', 'org_name', '组织名称', 'varchar(100)', 'String', 'orgName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 5, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (6, '1', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (7, '1', 'leader', '负责人', 'varchar(20)', 'String', 'leader', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (8, '1', 'phone', '联系电话', 'varchar(11)', 'String', 'phone', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (9, '1', 'email', '邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (10, '1', 'status', '部门状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 10, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (11, '1', 'del_flag', '删除标志（0代表存在 2代表删除）', 'char(1)', 'String', 'delFlag', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 11, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (12, '1', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 12, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (13, '1', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 13, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (14, '1', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 14, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (15, '1', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 15, '1', '2019-12-02 10:45:24', '1', NULL, 1);
INSERT INTO `gen_table_column` VALUES (16, '2', 'psn_id', '人员ID', 'bigint(20)', 'Long', 'psnId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (17, '2', 'org_id', '组织ID', 'bigint(20)', 'Long', 'orgId', '0', '0', '1', '1', NULL, '1', '1', 'EQ', 'input', '', 2, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (18, '2', 'dept_id', '部门ID', 'bigint(20)', 'Long', 'deptId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (19, '2', 'dept_id1', '兼职部门1', 'bigint(20)', 'Long', 'deptId1', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 4, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (20, '2', 'dept_id2', '兼职部门2', 'bigint(20)', 'Long', 'deptId2', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 5, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (21, '2', 'dept_id3', '兼职部门3', 'bigint(20)', 'Long', 'deptId3', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 6, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (22, '2', 'psn_code', '人员工号', 'varchar(30)', 'String', 'psnCode', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 7, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (23, '2', 'psn_name', '人员姓名', 'varchar(30)', 'String', 'psnName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 8, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (24, '2', 'email', '电子邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 9, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (25, '2', 'phonenumber', '手机号码', 'varchar(11)', 'String', 'phonenumber', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 10, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (26, '2', 'homephone', '家庭电话', 'varchar(11)', 'String', 'homephone', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 11, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (27, '2', 'sex', '用户性别（0男 1女 2未知）', 'char(1)', 'String', 'sex', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'select', 'sys_user_sex', 12, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (28, '2', 'avatar', '头像地址', 'varchar(100)', 'String', 'avatar', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (29, '2', 'status', '人员状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', 'sys_normal_disable', 14, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (30, '2', 'address', '地址', 'varchar(100)', 'String', 'address', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 15, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (31, '2', 'del_flag', '删除标志（0代表存在 1代表删除）', 'char(1)', 'String', 'delFlag', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 16, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (32, '2', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 17, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (33, '2', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 18, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (34, '2', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, NULL, '1', NULL, NULL, 'EQ', 'input', '', 19, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (35, '2', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, NULL, '1', NULL, NULL, 'EQ', 'datetime', '', 20, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (36, '2', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 21, '1', '2019-12-11 14:16:51', '1', '2019-12-11 14:31:26', 1);
INSERT INTO `gen_table_column` VALUES (78, '6', 'file_id', '文件编码', 'bigint(64)', 'Long', 'fileId', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (79, '6', 'name', '文件名称', 'varchar(150)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (80, '6', 'contentType', '文件类型', 'varchar(20)', 'String', 'contenttype', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'select', 'sys_file_type', 3, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (81, '6', 'isImg', '是否图片', 'char(1)', 'String', 'isimg', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'select', 'sys_yes_no', 4, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (82, '6', 'size', '文件大小', 'int(15)', 'Long', 'size', '0', '0', '1', '1', '1', '1', NULL, 'EQ', 'input', '', 5, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (83, '6', 'path', '文件路径', 'varchar(500)', 'String', 'path', '0', '0', '1', '1', '1', '1', NULL, 'EQ', 'textarea', '', 6, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (84, '6', 'url', 'OSS地址', 'varchar(300)', 'String', 'url', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 7, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (85, '6', 'source', '存储源', 'varchar(20)', 'String', 'source', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', 'sys_file_store', 8, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (86, '6', 'create_by', '创建者', 'bigint(64)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 9, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (87, '6', 'create_by_code', '创建者编码', 'varchar(20)', 'String', 'createByCode', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 10, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (88, '6', 'create_by_name', '创建者名称', 'varchar(20)', 'String', 'createByName', '0', '0', NULL, '1', '1', '1', NULL, 'LIKE', 'input', '', 11, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (89, '6', 'create_time', '上传时间', 'datetime', 'Date', 'createTime', '0', '0', '1', '1', '1', '1', '1', 'BETWEEN', 'datetime', '', 12, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (90, '6', 'src_id', '来源ID', 'bigint(64)', 'Long', 'srcId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (91, '6', 'src_type', '来源类型', 'varchar(10)', 'String', 'srcType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (92, '6', 'version', '版本', 'bigint(64)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 15, '1', '2020-01-15 14:54:31', NULL, '2020-01-15 14:58:27', 4);
INSERT INTO `gen_table_column` VALUES (117, '9', 'job_id', '任务ID', 'bigint(20)', 'Long', 'jobId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (118, '9', 'job_name', '任务名称', 'varchar(64)', 'String', 'jobName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (119, '9', 'job_group', '任务组名', 'varchar(64)', 'String', 'jobGroup', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (120, '9', 'invoke_target', '调用目标字符串', 'varchar(500)', 'String', 'invokeTarget', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'textarea', '', 4, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (121, '9', 'cron_expression', 'cron执行表达式', 'varchar(255)', 'String', 'cronExpression', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (122, '9', 'misfire_policy', '计划执行错误策略（1立即执行 2执行一次 3放弃执行）', 'varchar(20)', 'String', 'misfirePolicy', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (123, '9', 'concurrent', '是否并发执行（0允许 1禁止）', 'tinyint(1)', 'Integer', 'concurrent', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (124, '9', 'status', '状态（0正常 1暂停）', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 8, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (125, '9', 'create_by', '创建者', 'bigint(64)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 9, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (126, '9', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 10, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (127, '9', 'update_by', '更新者', 'bigint(64)', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 11, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (128, '9', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 12, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (129, '9', 'remark', '备注信息', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 13, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (130, '9', 'version', '版本', 'bigint(64)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 14, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (131, '9', 'exe_params', '参数', 'varchar(500)', 'String', 'exeParams', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 15, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:38:52', 2);
INSERT INTO `gen_table_column` VALUES (132, '10', 'job_log_id', '任务日志ID', 'bigint(64)', 'Long', 'jobLogId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (133, '10', 'job_name', '任务名称', 'varchar(64)', 'String', 'jobName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (134, '10', 'job_group', '任务组名', 'varchar(64)', 'String', 'jobGroup', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (135, '10', 'invoke_target', '调用目标字符串', 'varchar(500)', 'String', 'invokeTarget', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'textarea', '', 4, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (136, '10', 'job_message', '日志信息', 'varchar(500)', 'String', 'jobMessage', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 5, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (137, '10', 'status', '执行状态（0正常 1失败）', 'char(1)', 'Ingeter', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', 'sys_common_status', 6, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (138, '10', 'exception_info', '异常信息', 'longtext', 'String', 'exceptionInfo', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (139, '10', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 8, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (140, '10', 'version', '版本', 'bigint(64)', 'Long', 'version', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 9, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (141, '10', 'exe_params', '参数', 'varchar(500)', 'String', 'exeParams', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 10, '1', '2020-02-25 03:38:37', NULL, '2020-02-25 03:43:45', 3);
INSERT INTO `gen_table_column` VALUES (142, '11', 'org_id', '组织id', 'bigint(20)', 'Long', 'orgId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-03-04 03:34:56', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (143, '11', 'parent_id', '上级组织id', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 2, '1', '2020-03-04 03:34:56', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (144, '11', 'org_code', '组织编码', 'varchar(50)', 'String', 'orgCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-03-04 03:34:56', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (145, '11', 'org_name', '组织名称', 'varchar(100)', 'String', 'orgName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 4, '1', '2020-03-04 03:34:56', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (146, '11', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 5, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (147, '11', 'leader', '负责人', 'varchar(20)', 'String', 'leader', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 6, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (148, '11', 'phone', '联系电话', 'varchar(11)', 'String', 'phone', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 7, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (149, '11', 'email', '邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 8, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (150, '11', 'status', '部门状态（0正常 1停用）', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', 'sys_normal_disable', 9, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (151, '11', 'dr', '删除标志（0代表存在 2代表删除）', 'tinyint(1)', 'Integer', 'dr', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 10, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (152, '11', 'create_by', '创建者', 'bigint(20)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 11, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (153, '11', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 12, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (154, '11', 'update_by', '更新者', 'bigint(20)', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 13, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (155, '11', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 14, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (156, '11', 'org_short_name', '组织简称', 'varchar(100)', 'String', 'orgShortName', '0', '0', NULL, '1', '1', '1', NULL, 'LIKE', 'input', '', 15, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (157, '11', 'credit_code', '社会统一信用代码', 'varchar(18)', 'String', 'creditCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (158, '11', 'address', '组织地址', 'varchar(255)', 'String', 'address', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 17, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (159, '11', 'version', '版本', 'bigint(20)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 18, '1', '2020-03-04 03:34:57', NULL, '2020-03-04 03:43:40', 3);
INSERT INTO `gen_table_column` VALUES (160, '12', 'org_id', '组织id', 'bigint(20)', 'Long', 'orgId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:26', 8);
INSERT INTO `gen_table_column` VALUES (161, '12', 'parent_id', '上级组织id', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 2, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:26', 8);
INSERT INTO `gen_table_column` VALUES (162, '12', 'org_code', '组织编码', 'varchar(50)', 'String', 'orgCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (163, '12', 'org_name', '组织名称', 'varchar(100)', 'String', 'orgName', '0', '0', NULL, '1', '1', '1', NULL, 'LIKE', 'input', '', 4, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (164, '12', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 5, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (165, '12', 'leader', '负责人', 'varchar(20)', 'String', 'leader', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'select', '', 6, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (166, '12', 'phone', '联系电话', 'varchar(11)', 'String', 'phone', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 7, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (167, '12', 'email', '邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 8, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (168, '12', 'status', '部门状态（0正常 1停用）', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'select', 'sys_normal_disable', 9, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (169, '12', 'dr', '删除标志（0代表存在 2代表删除）', 'tinyint(1)', 'Integer', 'dr', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 10, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (170, '12', 'create_by', '创建者', 'bigint(64)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 11, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (171, '12', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 12, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (172, '12', 'update_by', '更新者', 'bigint(64)', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 13, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (173, '12', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 14, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (174, '12', 'org_short_name', '组织简称', 'varchar(100)', 'String', 'orgShortName', '0', '0', NULL, '1', '1', '1', NULL, 'LIKE', 'input', '', 15, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (175, '12', 'credit_code', '社会统一信用代码', 'varchar(18)', 'String', 'creditCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (176, '12', 'address', '组织地址', 'varchar(255)', 'String', 'address', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 17, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (177, '12', 'version', '版本', 'bigint(64)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 18, '1', '2020-03-04 06:56:05', NULL, '2020-03-04 12:17:27', 8);
INSERT INTO `gen_table_column` VALUES (178, '13', 'org_id', '组织id', 'bigint(20)', 'Long', 'orgId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-03-04 07:30:07', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (179, '13', 'parent_id', '上级组织id', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', 'sys_job_group', 2, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (180, '13', 'org_code', '组织编码', 'varchar(50)', 'String', 'orgCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (181, '13', 'org_name', '组织名称', 'varchar(100)', 'String', 'orgName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 4, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (182, '13', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 5, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (183, '13', 'leader', '负责人', 'varchar(20)', 'String', 'leader', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 6, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (184, '13', 'phone', '联系电话', 'varchar(11)', 'String', 'phone', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 7, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (185, '13', 'email', '邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 8, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (186, '13', 'status', '部门状态（0正常 1停用）', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 9, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (187, '13', 'dr', '删除标志（0代表存在 2代表删除）', 'tinyint(1)', 'Integer', 'dr', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 10, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (188, '13', 'create_by', '创建者', 'bigint(64)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 11, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (189, '13', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 12, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (190, '13', 'update_by', '更新者', 'bigint(64)', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 13, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (191, '13', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 14, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (192, '13', 'org_short_name', '组织简称', 'varchar(100)', 'String', 'orgShortName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 15, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (193, '13', 'credit_code', '社会统一信用代码', 'varchar(18)', 'String', 'creditCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (194, '13', 'address', '组织地址', 'varchar(255)', 'String', 'address', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:42', 2);
INSERT INTO `gen_table_column` VALUES (195, '13', 'version', '版本', 'bigint(64)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 18, '1', '2020-03-04 07:30:08', NULL, '2020-03-04 07:34:43', 2);
INSERT INTO `gen_table_column` VALUES (196, '14', 'org_id', '组织id', 'bigint(20)', 'Long', 'orgId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (197, '14', 'parent_id', '上级组织id', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', 'sys_user_sex', 2, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (198, '14', 'org_code', '组织编码', 'varchar(50)', 'String', 'orgCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (199, '14', 'org_name', '组织名称', 'varchar(100)', 'String', 'orgName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 4, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (200, '14', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (201, '14', 'leader', '负责人', 'varchar(20)', 'String', 'leader', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (202, '14', 'phone', '联系电话', 'varchar(11)', 'String', 'phone', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (203, '14', 'email', '邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (204, '14', 'status', '部门状态（0正常 1停用）', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 9, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (205, '14', 'dr', '删除标志（0代表存在 2代表删除）', 'tinyint(1)', 'Integer', 'dr', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 10, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (206, '14', 'create_by', '创建者', 'bigint(64)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 11, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (207, '14', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 12, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (208, '14', 'update_by', '更新者', 'bigint(64)', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 13, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (209, '14', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 14, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (210, '14', 'org_short_name', '组织简称', 'varchar(100)', 'String', 'orgShortName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 15, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (211, '14', 'credit_code', '社会统一信用代码', 'varchar(18)', 'String', 'creditCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (212, '14', 'address', '组织地址', 'varchar(255)', 'String', 'address', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (213, '14', 'version', '版本', 'bigint(64)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 18, '1', '2020-03-04 08:34:24', NULL, '2020-03-04 09:35:14', 3);
INSERT INTO `gen_table_column` VALUES (214, '15', 'org_id', '组织id', 'bigint(20)', 'Long', 'orgId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:25', 3);
INSERT INTO `gen_table_column` VALUES (215, '15', 'parent_id', '上级组织id', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 2, '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:25', 3);
INSERT INTO `gen_table_column` VALUES (216, '15', 'org_code', '组织编码', 'varchar(50)', 'String', 'orgCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:25', 3);
INSERT INTO `gen_table_column` VALUES (217, '15', 'org_name', '组织名称', 'varchar(100)', 'String', 'orgName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 4, '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:25', 3);
INSERT INTO `gen_table_column` VALUES (218, '15', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 5, '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:25', 3);
INSERT INTO `gen_table_column` VALUES (219, '15', 'leader', '负责人', 'varchar(20)', 'String', 'leader', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (220, '15', 'phone', '联系电话', 'varchar(11)', 'String', 'phone', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '1', '2020-03-04 12:48:46', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (221, '15', 'email', '邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (222, '15', 'status', '部门状态（0正常 1停用）', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'radio', 'sys_normal_disable', 9, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (223, '15', 'dr', '删除标志（0代表存在 2代表删除）', 'tinyint(1)', 'Integer', 'dr', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 10, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (224, '15', 'create_by', '创建者', 'bigint(20)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 11, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (225, '15', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 12, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (226, '15', 'update_by', '更新者', 'bigint(64)', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 13, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (227, '15', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 14, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (228, '15', 'org_short_name', '组织简称', 'varchar(100)', 'String', 'orgShortName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 15, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:26', 3);
INSERT INTO `gen_table_column` VALUES (229, '15', 'credit_code', '社会统一信用代码', 'varchar(18)', 'String', 'creditCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:27', 3);
INSERT INTO `gen_table_column` VALUES (230, '15', 'address', '组织地址', 'varchar(255)', 'String', 'address', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:27', 3);
INSERT INTO `gen_table_column` VALUES (231, '15', 'version', '版本', 'bigint(20)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 18, '1', '2020-03-04 12:48:47', NULL, '2020-03-04 13:19:27', 3);
INSERT INTO `gen_table_column` VALUES (232, '16', 'org_id', '组织id', 'bigint(20)', 'Long', 'orgId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-03-04 15:31:54', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (233, '16', 'parent_id', '上级组织id', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 2, '1', '2020-03-04 15:31:54', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (234, '16', 'org_code', '组织编码', 'varchar(50)', 'String', 'orgCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (235, '16', 'org_name', '组织名称', 'varchar(100)', 'String', 'orgName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 4, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (236, '16', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 5, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (237, '16', 'leader', '负责人', 'varchar(20)', 'String', 'leader', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 6, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (238, '16', 'phone', '联系电话', 'varchar(11)', 'String', 'phone', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 7, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (239, '16', 'email', '邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 8, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (240, '16', 'status', '部门状态（0正常 1停用）', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'select', 'sys_normal_disable', 9, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (241, '16', 'dr', '删除标志（0代表存在 2代表删除）', 'tinyint(1)', 'Integer', 'dr', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'select', '', 10, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (242, '16', 'create_by', '创建者', 'bigint(20)', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 11, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (243, '16', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 12, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (244, '16', 'update_by', '更新者', 'bigint(20)', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 13, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (245, '16', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 14, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (246, '16', 'org_short_name', '组织简称', 'varchar(100)', 'String', 'orgShortName', '0', '0', NULL, '1', '1', '1', NULL, 'LIKE', 'input', '', 15, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (247, '16', 'credit_code', '社会统一信用代码', 'varchar(18)', 'String', 'creditCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (248, '16', 'address', '组织地址', 'varchar(255)', 'String', 'address', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 17, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (249, '16', 'version', '版本', 'bigint(20)', 'Long', 'version', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 18, '1', '2020-03-04 15:31:55', NULL, '2020-03-04 15:47:42', 5);
INSERT INTO `gen_table_column` VALUES (276, '19', 'id', 'id', 'int(11)', 'Long', 'id', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (277, '19', 'base', '基地', 'varchar(100)', 'String', 'base', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (278, '19', 'sendOrderCode', '发货单号', 'varchar(100)', 'String', 'sendordercode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (279, '19', 'emcCode', '电子监管码', 'varchar(100)', 'String', 'emccode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (280, '19', 'custCode', '客户编码', 'varchar(100)', 'String', 'custcode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (281, '19', 'custName', '客户名称', 'varchar(100)', 'String', 'custname', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (282, '19', 'sendOrderDate', '发货单日期', 'varchar(100)', 'String', 'sendorderdate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (283, '19', 'batchCode', '批次号码', 'varchar(100)', 'String', 'batchcode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (284, '19', 'invCode', '物料编码', 'varchar(100)', 'String', 'invcode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, '1', '2020-03-11 11:30:46', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (285, '19', 'invName', '物料名称', 'varchar(100)', 'String', 'invname', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 10, '1', '2020-03-11 11:30:47', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (286, '19', 'status', '状态', 'int(11)', 'Long', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 11, '1', '2020-03-11 11:30:47', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (287, '19', 'ts', '时间戳', 'varchar(100)', 'String', 'ts', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, '1', '2020-03-11 11:30:47', NULL, NULL, 1);
INSERT INTO `gen_table_column` VALUES (288, '19', 'dr', '删除标志', 'int(11)', 'Long', 'dr', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 13, '1', '2020-03-11 11:30:47', NULL, NULL, 1);

-- ----------------------------
-- Table structure for md_class
-- ----------------------------
DROP TABLE IF EXISTS `md_class`;
CREATE TABLE `md_class`  (
  `class_id` bigint(64) NOT NULL COMMENT '主键',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元数据编码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元数据名称',
  `en_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `table_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元数据表名',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '元数据状态（0正常 1停用）',
  `dr` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` int(20) NULL DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` bigint(64) NOT NULL COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', '1', '2018-03-16 11:33:00', '1', '2020-01-10 02:54:42', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow', 1);
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '初始化密码 123456', 1);
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '深色主题theme-dark，浅色主题theme-light', 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(64) NOT NULL COMMENT '部门id',
  `org_id` bigint(64) NULL DEFAULT NULL COMMENT '组织',
  `parent_id` bigint(64) NULL DEFAULT NULL COMMENT '父部门id',
  `dept_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `dept_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编码',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `dr` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 200, NULL, 'XX总部', NULL, 0, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2020-01-10 02:50:17', 4);
INSERT INTO `sys_dept` VALUES (101, 200, 100, '深圳XX基地', NULL, 1, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2020-01-10 02:50:17', 2);
INSERT INTO `sys_dept` VALUES (102, 200, 100, '长沙YY基地', NULL, 2, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2020-01-07 14:58:11', 1);
INSERT INTO `sys_dept` VALUES (103, 200, 101, '研发部门1111111111', NULL, 1, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2020-01-10 02:50:17', 3);
INSERT INTO `sys_dept` VALUES (104, 200, 101, '市场部门', NULL, 2, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', 1);
INSERT INTO `sys_dept` VALUES (105, 200, 101, '测试部门', NULL, 3, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', 1);
INSERT INTO `sys_dept` VALUES (106, 200, 101, '财务部门', NULL, 4, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', 1);
INSERT INTO `sys_dept` VALUES (107, 200, 101, '运维部门', NULL, 5, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', 1);
INSERT INTO `sys_dept` VALUES (108, 200, 102, '市场部门', NULL, 1, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', 1);
INSERT INTO `sys_dept` VALUES (109, 200, 102, '财务部门', NULL, 2, '大佬', '15888888888', 'ry@qq.com', 0, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', 1);
INSERT INTO `sys_dept` VALUES (200, 203, NULL, '11', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2019-12-02 17:38:40', 1, NULL, 1);
INSERT INTO `sys_dept` VALUES (201, 203, 200, '22', NULL, 2, NULL, NULL, NULL, 0, 0, 1, '2019-12-02 17:38:48', 1, NULL, 1);
INSERT INTO `sys_dept` VALUES (202, 203, NULL, '12', NULL, 2, NULL, NULL, NULL, 0, 0, 1, '2019-12-02 17:38:56', 1, NULL, 1);
INSERT INTO `sys_dept` VALUES (203, 203, 202, '21', NULL, 21, NULL, NULL, NULL, 0, 0, 1, '2019-12-02 17:39:04', 1, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941220011247230976, 200, NULL, '1', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:14:49', 1, '2020-01-07 14:34:33', 1);
INSERT INTO `sys_dept` VALUES (3941221110758858752, 200, 3941220011247230976, '2', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:15:21', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941221282557550592, 200, 3941221110758858752, '3', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:15:25', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941221419996504064, 200, 3941221282557550592, '4', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:15:30', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941221557435457536, 200, 3941221419996504064, '5', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:15:34', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941235335690543104, 200, 3941221557435457536, '6', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:22:15', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941235507489234944, 200, 3941235335690543104, '7', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:22:19', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941235644928188416, 200, 3941235507489234944, '8', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:22:24', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941235782367141888, 200, 3941235644928188416, '9', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:22:28', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941236125964525568, 200, 3941235782367141888, '10', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 14:22:37', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (3941498050250129408, 201, NULL, '1', NULL, 1, NULL, NULL, NULL, 0, 0, 1, '2020-01-07 16:29:39', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(64) NOT NULL COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '性别男', 2);
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '性别女', 2);
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '性别未知', 2);
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '显示菜单', 1);
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '隐藏菜单', 1);
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '正常状态', 1);
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '停用状态', 1);
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '正常状态', 1);
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '停用状态', 1);
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '默认分组', 1);
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '系统分组', 1);
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '系统默认是', 1);
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '系统默认否', 1);
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '通知', 1);
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '公告', 1);
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '正常状态', 1);
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '关闭状态', 1);
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '新增操作', 1);
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '修改操作', 1);
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '删除操作', 1);
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '授权操作', 1);
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '导出操作', 1);
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '导入操作', 1);
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '强退操作', 1);
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '生成操作', 1);
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '清空操作', 1);
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '正常状态', 2);
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '停用状态', 2);
INSERT INTO `sys_dict_data` VALUES (100, 0, '在职', '0', 'psn_status', NULL, NULL, 'N', 0, 1, '2019-12-11 15:23:25', 1, '2019-12-11 15:26:12', NULL, 1);
INSERT INTO `sys_dict_data` VALUES (101, 5, '离职', '1', 'psn_status', NULL, NULL, 'N', 0, 1, '2019-12-11 15:23:31', 1, '2019-12-11 15:26:17', NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3887269861889744896, 10, '文件上传', '10', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2019-12-20 10:06:06', 1, NULL, '文件上传', 1);
INSERT INTO `sys_dict_data` VALUES (3887308001199341568, 11, '文件下载', '11', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2019-12-20 10:24:36', 1, '2019-12-20 10:24:47', '文件下载', 1);
INSERT INTO `sys_dict_data` VALUES (3938437147317223424, 12, '服务上线', '12', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2020-01-06 15:45:03', NULL, NULL, '服务上线', 1);
INSERT INTO `sys_dict_data` VALUES (3938437490914607104, 13, '服务下线', '13', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2020-01-06 15:45:13', NULL, NULL, '服务下线', 1);
INSERT INTO `sys_dict_data` VALUES (3965475546993344512, 15, '阿里云', 'aliyun', 'sys_file_store', NULL, NULL, 'N', 0, 1, '2020-01-15 10:28:50', NULL, NULL, '阿里云OSS存储', 1);
INSERT INTO `sys_dict_data` VALUES (3965476680864710656, 10, '七牛云', 'qiniu', 'sys_file_store', NULL, NULL, 'N', 0, 1, '2020-01-15 10:29:22', NULL, NULL, '七牛云OSS存储', 1);
INSERT INTO `sys_dict_data` VALUES (3965477677297123328, 5, 'FastDFS', 'fastdfs', 'sys_file_store', NULL, NULL, 'N', 0, 1, '2020-01-15 10:29:51', NULL, NULL, 'FastDFS存储', 1);
INSERT INTO `sys_dict_data` VALUES (3965478433211367424, 0, '本地文件存储', 'localfs', 'sys_file_store', NULL, NULL, 'N', 0, 1, '2020-01-15 10:30:14', NULL, NULL, '本地文件存储', 1);
INSERT INTO `sys_dict_data` VALUES (3965576461544972288, 0, 'image/png', 'image/png', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-15 11:17:47', 1, '2020-01-19 07:35:53', NULL, 3);
INSERT INTO `sys_dict_data` VALUES (3967613066317348864, 5, 'image/jpeg', 'image/jpeg', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-16 03:45:39', 1, '2020-01-19 07:35:48', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (3976992587697266688, 15, 'text/plain', 'text/plain', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:35:19', 1, '2020-01-19 07:35:39', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (3976993103093342208, 20, 'application/pdf', 'application/pdf', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:35:35', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976995336476336128, 6, 'image/jpg', 'image/jpg', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:36:39', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976995920591888384, 7, 'image/bmp', 'image/bmp', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:36:57', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976996264189272064, 8, 'image/gif', 'image/gif', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:37:07', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976996985743777792, 25, 'text/html', 'text/html', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:37:28', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976997638578806784, 27, 'text/xml', 'text/xml', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:37:47', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976998875529388032, 30, 'doc', 'application/msword', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:38:23', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976999390925463552, 31, 'docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:38:38', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3976999906321539072, 35, 'xls', 'application/vnd.ms-excel', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:38:53', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3977000352998137856, 36, 'xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:39:06', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3977001108912381952, 40, 'ppt', 'application/vnd.ms-powerpoint', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:39:28', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (3977001555588980736, 41, 'pptx', 'application/vnd.openxmlformats-officedocument.presentationml.presentation', 'sys_file_type', NULL, NULL, 'N', 0, 1, '2020-01-19 07:39:41', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4083252243265003520, 5, '立即执行', '1', 'sys_job_policy', NULL, NULL, 'N', 0, 1, '2020-02-24 02:38:01', 1, '2020-02-26 03:29:17', NULL, 3);
INSERT INTO `sys_dict_data` VALUES (4083252724301340672, 10, '执行一次', '2', 'sys_job_policy', NULL, NULL, 'N', 0, 1, '2020-02-24 02:38:15', NULL, NULL, NULL, 2);
INSERT INTO `sys_dict_data` VALUES (4083252999179247616, 30, '放弃执行', '3', 'sys_job_policy', NULL, NULL, 'N', 0, 1, '2020-02-24 02:38:23', NULL, NULL, NULL, 2);
INSERT INTO `sys_dict_data` VALUES (4083282926511366144, 0, '允许', '0', 'sys_allow_forbidden', NULL, NULL, 'N', 0, 1, '2020-02-24 02:52:54', NULL, NULL, NULL, 3);
INSERT INTO `sys_dict_data` VALUES (4083283098310057984, 0, '禁止', '1', 'sys_allow_forbidden', NULL, NULL, 'N', 0, 1, '2020-02-24 02:53:00', NULL, NULL, NULL, 3);
INSERT INTO `sys_dict_data` VALUES (4086542703609864192, 14, '启动', '14', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2020-02-25 05:14:06', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4086543081566986240, 15, '停机', '15', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2020-02-25 05:14:17', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4086543459524108288, 16, '重启', '16', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2020-02-25 05:14:28', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4086543803121491968, 17, '重新注册', '17', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2020-02-25 05:14:39', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4086544112359137280, 18, '执行', '18', 'sys_oper_type', NULL, NULL, 'N', 0, 1, '2020-02-25 05:14:48', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4089294884293468160, 0, '默认', '0', 'sys_job_policy', NULL, NULL, 'N', 0, 1, '2020-02-26 03:29:08', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4092813184063520768, 0, '全部数据权限', '1', 'sys_data_scope', NULL, NULL, 'N', 0, 1, '2020-02-27 07:55:42', NULL, NULL, '不可修改', 1);
INSERT INTO `sys_dict_data` VALUES (4092813424581689344, 10, '自定数据权限', '2', 'sys_data_scope', NULL, NULL, 'N', 0, 1, '2020-02-27 07:55:49', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4092813768179073024, 30, '本部门数据限定', '3', 'sys_data_scope', NULL, NULL, 'N', 0, 1, '2020-02-27 07:55:59', 1, '2020-02-27 08:01:45', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (4092814111776456704, 40, '本部门及以下数据权限', '4', 'sys_data_scope', NULL, NULL, 'N', 0, 1, '2020-02-27 07:56:09', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4092814421014102016, 50, '仅本人数据权限', '5', 'sys_data_scope', NULL, NULL, 'N', 0, 1, '2020-02-27 07:56:18', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4092824660216135680, 60, '本组织数据限定', '6', 'sys_data_scope', NULL, NULL, 'N', 0, 1, '2020-02-27 08:01:16', 1, '2020-02-27 08:01:54', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (4133815209614901248, 0, '自由', '0', 'mec_record_status', NULL, NULL, 'N', 0, 1, '2020-03-12 03:24:19', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4133815415773331456, 1, '发货', '1', 'mec_record_status', NULL, NULL, 'N', 0, 1, '2020-03-12 03:24:25', 1, '2020-03-12 03:25:08', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (4133816377846005760, 2, '上架', '2', 'mec_record_status', NULL, NULL, 'N', 0, 1, '2020-03-12 03:24:53', 1, '2020-03-12 03:25:12', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (4133816721443389440, 3, '出库', '3', 'mec_record_status', NULL, NULL, 'N', 0, 1, '2020-03-12 03:25:03', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4133817236839464960, 4, '退库', '4', 'mec_record_status', NULL, NULL, 'N', 0, 1, '2020-03-12 03:25:18', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4133817477357633536, 5, '退货', '5', 'mec_record_status', NULL, NULL, 'N', 0, 1, '2020-03-12 03:25:25', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4134900599390486528, 0, '每10秒执行一次', '0/10 * * * * ?', 'sys_job_cron', NULL, NULL, 'N', 0, 1, '2020-03-12 12:10:48', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4134901149146300416, 10, '没10分钟执行一次', '0 0/10 * * * ?', 'sys_job_cron', NULL, NULL, 'N', 0, 1, '2020-03-12 12:11:03', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4134901664542375936, 20, '每2小时执行一次', '0 0 0/2 * * ?', 'sys_job_cron', NULL, NULL, 'N', 0, 1, '2020-03-12 12:11:18', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_data` VALUES (4134902283017666560, 30, '每天23点10分执行一次', '0 10 23 * * ?', 'sys_job_cron', NULL, NULL, 'N', 0, 1, '2020-03-12 12:11:36', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(64) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', 0, 1, '2018-03-16 11:33:00', 1, '2020-01-10 02:54:36', '用户性别列表', 2);
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '菜单状态列表', 1);
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '系统开关列表', 1);
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '任务状态列表', 1);
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '任务分组列表', 1);
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '系统是否列表', 1);
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '通知类型列表', 1);
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '通知状态列表', 1);
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '操作类型列表', 1);
INSERT INTO `sys_dict_type` VALUES (10, '成功/失败', 'sys_common_status', 0, 1, '2018-03-16 11:33:00', 1, '2020-02-25 03:43:01', '登录状态列表', 2);
INSERT INTO `sys_dict_type` VALUES (11, '数据权限范围', 'sys_data_scope', 0, 1, '2020-02-27 07:54:05', NULL, NULL, '数据权限范围', 1);
INSERT INTO `sys_dict_type` VALUES (100, '人员状态', 'psn_status', 0, 1, '2019-12-11 15:22:58', 1, NULL, NULL, 1);
INSERT INTO `sys_dict_type` VALUES (3965461974896680960, '文件存储引擎', 'sys_file_store', 0, 1, '2020-01-15 10:22:15', NULL, NULL, '文件存储引擎', 1);
INSERT INTO `sys_dict_type` VALUES (3965575808709943296, '文件类型', 'sys_file_type', 0, 1, '2020-01-15 11:17:28', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_type` VALUES (4083251178113114112, '任务调度策略', 'sys_job_policy', 0, 1, '2020-02-24 02:37:30', 1, '2020-02-24 02:50:00', NULL, 3);
INSERT INTO `sys_dict_type` VALUES (4083282308036075520, '允许/禁止', 'sys_allow_forbidden', 0, 1, '2020-02-24 02:52:36', 1, '2020-02-24 02:54:08', NULL, 3);
INSERT INTO `sys_dict_type` VALUES (4110719211958558720, '组织xieh', 'sys_org_xieh', 0, 1, '2020-03-04 08:41:15', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_type` VALUES (4133814075743535104, '电子监管码记录状态', 'mec_record_status', 0, 1, '2020-03-12 03:23:47', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict_type` VALUES (4134899396799643648, '任务cron', 'sys_job_cron', 0, 1, '2020-03-12 12:10:12', NULL, NULL, '调度任务建议cron', 1);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `file_id` bigint(64) NOT NULL DEFAULT 0 COMMENT '文件编码',
  `name` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `contentType` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件类型',
  `isImg` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否图片',
  `size` int(15) NOT NULL COMMENT '文件大小',
  `path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `url` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OSS地址',
  `source` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储源',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_by_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者编码',
  `create_by_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `create_time` datetime(0) NOT NULL COMMENT '上传时间',
  `src_id` bigint(64) NULL DEFAULT NULL COMMENT '来源ID',
  `src_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源类型',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (3967623820915482624, '1.txt', 'text/plain', 'N', 1238, '20200119\\1.txt', '/common/download/3967623820915482624', 'localfs', 1, 'admin', '系统管理员', '2020-01-16 11:50:51', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3968140213423415296, '1.pdf', 'application/pdf', 'N', 22979, '20200119\\1.pdf', '/common/download/3968140213423415296', 'localfs', 1, 'admin', '系统管理员', '2020-01-16 16:01:22', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3968258720161112064, '头像.jpeg', 'image/jpeg', 'Y', 45515, '20200116\\30775f8efdd9898f1c78678d1a4003fe.jpeg', '/common/download/3968258720161112064', 'localfs', 1, 'admin', '系统管理员', '2020-01-16 16:58:50', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3968264492597157888, '头像.jpeg', 'image/jpeg', 'Y', 45515, '20200116\\a2820502810e9ae8e3ba0b1fc22cbb4d.jpeg', '/common/download/3968264492597157888', 'localfs', 1, 'admin', '系统管理员', '2020-01-16 17:01:38', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3968283081215614976, 'enterprisePlatformCloud.png', 'image/jpeg', 'Y', 23730, '20200116\\7d21eeae0e64ef81f1ec14aa8aff0a6b.jpeg', '/common/download/3968283081215614976', 'localfs', 1, 'admin', '系统管理员', '2020-01-16 17:10:39', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3976846455730012160, '头像.jpg', 'image/jpeg', 'Y', 45515, '20200119\\54864ab0025457e371c50f0bc377bf20.jpeg', '/common/download/3976846455730012160', 'localfs', 1, 'admin', '系统管理员', '2020-01-19 14:24:25', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3976846455730012161, '头像.jpg', 'image/jpeg', 'Y', 45515, '20200119\\80832fef2d75b2b69e011c0ad74836de.jpeg', '/common/download/3976846455730012161', 'localfs', 1, 'admin', '系统管理员', '2020-01-19 14:24:25', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3979382685457981440, 'psn.jpg', 'image/jpeg', 'Y', 66919, '20200120\\7d3d88aae37aa90582f90316bcdf1a4a.jpg', '/common/download/3979382685457981440', 'localfs', 1, 'admin', '系统管理员', '2020-01-20 10:54:39', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3979383578811179008, 'psn.jpg', 'image/jpeg', 'Y', 66919, '20200120\\cd013904990eb6eb4254860ac45375d1.jpg', '/common/download/3979383578811179008', 'localfs', 1, 'admin', '系统管理员', '2020-01-20 10:55:05', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3979385743474696192, 'psn.jpg', 'image/jpeg', 'Y', 66919, '20200120\\dbcac9e1a25edbe57a98737bdc8a6601.jpg', '/common/download/3979385743474696192', 'localfs', 1, 'admin', '系统管理员', '2020-01-20 10:56:08', NULL, NULL, 1, NULL);
INSERT INTO `sys_file` VALUES (3979422027358437376, 'psn.jpg', 'image/jpeg', 'Y', 66919, '20200120\\61a20baf3aace34855403eae48e20ab3.jpg', '/common/download/3979422027358437376', 'localfs', 1, 'admin', '系统管理员', '2020-01-20 11:13:45', NULL, NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(64) NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` tinyint(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `ext_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展url',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 'M', '0', '', 'system', 1, '2018-03-16 11:33:00', 1, '2020-02-10 08:52:42', '系统管理目录', NULL, 10);
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, 1, 'M', '0', '', 'monitor', 1, '2018-03-16 11:33:00', 1, '2019-12-26 16:21:50', '系统监控目录', NULL, 1);
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, 1, 'M', '0', '', 'tool', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '系统工具目录', NULL, 1);
INSERT INTO `sys_menu` VALUES (4, '打印设置', 0, 4, 'http://127.0.0.1:8080/print_czyl/index.html', NULL, 0, 'M', '0', '', 'bug', 1, '2018-03-16 11:33:00', 1, '2020-02-24 07:20:38', '若依官网地址', NULL, 2);
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 15, 'user', 'system/user/index', 1, 'C', '0', 'system:user:list', 'user', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:22', '用户管理菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 10, 'role', 'system/role/index', 1, 'C', '0', 'system:role:list', 'peoples', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:17', '角色管理菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 0, 'menu', 'system/menu/index', 1, 'C', '0', 'system:menu:list', 'tree-table', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:04', '菜单管理菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 20, 'dept', 'system/dept/index', 1, 'C', '0', 'system:dept:list', 'tree', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:27', '部门管理菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 25, 'post', 'system/post/index', 1, 'C', '0', 'system:post:list', 'post', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:31', '岗位管理菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 30, 'dict', 'system/dict/index', 1, 'C', '0', 'system:dict:list', 'dict', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:40', '字典管理菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 35, 'config', 'system/config/index', 1, 'C', '0', 'system:config:list', 'edit', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:45', '参数设置菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 40, 'notice', 'system/notice/index', 1, 'C', '0', 'system:notice:list', 'message', 1, '2018-03-16 11:33:00', 1, '2019-12-11 10:54:50', '通知公告菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (108, '日志管理', 2, 45, 'log', 'system/virtualmenu/index', 1, 'M', '0', '', 'log', 1, '2018-03-16 11:33:00', 1, '2020-01-06 15:38:27', '日志管理菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', 1, 'C', '0', 'monitor:online:list', 'online', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '在线用户菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (110, '任务调度', 2, 2, 'job', 'monitor/job/index', 1, 'C', '0', 'dispatcher:job:list', 'job', 1, '2018-03-16 11:33:00', 1, '2020-02-24 05:47:01', '定时任务菜单', NULL, 3);
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 30, 'druid', 'monitor/druid/index', 1, 'C', '0', 'monitor:druid:list', 'druid', 1, '2018-03-16 11:33:00', 1, '2020-02-19 11:49:32', '数据监控菜单', NULL, 2);
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 40, 'server/:appId/:instanceId', 'monitor/server/index', 1, 'C', '1', 'monitor:server:list', 'server', 1, '2018-03-16 11:33:00', 1, '2020-03-09 09:36:05', '服务监控菜单', NULL, 4);
INSERT INTO `sys_menu` VALUES (113, '表单构建', 3, 1, 'build', 'tool/build/index', 1, 'C', '0', 'tool:build:list', 'build', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '表单构建菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (114, '代码生成', 3, 2, 'gen', 'tool/gen/index', 1, 'C', '0', 'tool:gen:list', 'code', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '代码生成菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (115, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', 1, 'C', '0', 'tool:swagger:list', 'swagger', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '系统接口菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', 1, 'C', '0', 'monitor:operlog:list', 'form', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '操作日志菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', 1, 'C', '0', 'monitor:logininfor:list', 'logininfor', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '登录日志菜单', NULL, 1);
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', 'system:user:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', 'system:user:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', 'system:user:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', 'system:user:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', 'system:user:export', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', 'system:user:import', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', 'system:user:resetPwd', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', 'system:role:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', 'system:role:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', 'system:role:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', 'system:role:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', 'system:role:export', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', 'system:menu:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', 'system:menu:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', 'system:menu:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', 'system:menu:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', 1, 'F', '0', 'system:dept:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', 1, 'F', '0', 'system:dept:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', 1, 'F', '0', 'system:dept:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', 1, 'F', '0', 'system:dept:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', 1, 'F', '0', 'system:post:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', 1, 'F', '0', 'system:post:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', 1, 'F', '0', 'system:post:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', 1, 'F', '0', 'system:post:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', 1, 'F', '0', 'system:post:export', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', 1, 'F', '0', 'system:dict:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', 1, 'F', '0', 'system:dict:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', 1, 'F', '0', 'system:dict:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', 1, 'F', '0', 'system:dict:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', 1, 'F', '0', 'system:dict:export', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', 1, 'F', '0', 'system:config:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', 1, 'F', '0', 'system:config:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', 1, 'F', '0', 'system:config:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', 1, 'F', '0', 'system:config:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', 1, 'F', '0', 'system:config:export', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1036, '公告查询', 107, 1, '#', '', 1, 'F', '0', 'system:notice:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1037, '公告新增', 107, 2, '#', '', 1, 'F', '0', 'system:notice:add', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1038, '公告修改', 107, 3, '#', '', 1, 'F', '0', 'system:notice:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1039, '公告删除', 107, 4, '#', '', 1, 'F', '0', 'system:notice:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, 1, '#', '', 1, 'F', '0', 'monitor:operlog:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, 2, '#', '', 1, 'F', '0', 'monitor:operlog:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', 1, 'F', '0', 'monitor:operlog:export', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', 1, 'F', '0', 'monitor:logininfor:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', 1, 'F', '0', 'monitor:logininfor:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', 1, 'F', '0', 'monitor:logininfor:export', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', 1, 'F', '0', 'monitor:online:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', 1, 'F', '0', 'monitor:online:batchLogout', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', 1, 'F', '0', 'monitor:online:forceLogout', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', 1, 'F', '0', 'dispatcher:job:query', '#', 1, '2018-03-16 11:33:00', 1, '2020-02-24 05:47:11', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', 1, 'F', '0', 'dispatcher:job:add', '#', 1, '2018-03-16 11:33:00', 1, '2020-02-24 05:47:16', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', 1, 'F', '0', 'dispatcher:job:edit', '#', 1, '2018-03-16 11:33:00', 1, '2020-02-24 05:47:25', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', 1, 'F', '0', 'dispatcher:job:remove', '#', 1, '2018-03-16 11:33:00', 1, '2020-02-24 05:47:32', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', 1, 'F', '0', 'dispatcher:job:changeStatus', '#', 1, '2018-03-16 11:33:00', 1, '2020-02-24 05:48:00', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 114, 1, '#', '', 1, 'F', '0', 'tool:gen:query', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 114, 2, '#', '', 1, 'F', '0', 'tool:gen:edit', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 114, 3, '#', '', 1, 'F', '0', 'tool:gen:remove', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 114, 2, '#', '', 1, 'F', '0', 'tool:gen:import', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 114, 4, '#', '', 1, 'F', '0', 'tool:gen:preview', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 114, 5, '#', '', 1, 'F', '0', 'tool:gen:code', '#', 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2000, '组织管理', 1, 5, 'org', 'system/org/index', 1, 'C', '0', 'system:org:list', 'international', 1, '2018-03-01 00:00:00', 1, '2020-03-05 13:36:11', '组织菜单', NULL, 2);
INSERT INTO `sys_menu` VALUES (2001, '组织查询', 2000, 1, '#', '', 1, 'F', '0', 'system:org:query', '#', 1, '2018-03-01 00:00:00', 1, '2020-03-05 13:36:19', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (2002, '组织新增', 2000, 2, '#', '', 1, 'F', '0', 'system:org:add', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2003, '组织修改', 2000, 3, '#', '', 1, 'F', '0', 'system:org:edit', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2004, '组织删除', 2000, 4, '#', '', 1, 'F', '0', 'system:org:remove', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2005, '组织导出', 2000, 5, '#', '', 1, 'F', '0', 'system:org:export', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2006, '人员管理', 1, 23, 'psndoc', 'system/psndoc/index', 1, 'C', '0', 'system:psndoc:list', 'people', 1, '2018-03-01 00:00:00', 1, '2020-03-05 13:35:49', '人员管理菜单', NULL, 2);
INSERT INTO `sys_menu` VALUES (2007, '人员管理查询', 2006, 1, '#', '', 1, 'F', '0', 'system:psndoc:query', '#', 1, '2018-03-01 00:00:00', 1, '2020-03-05 13:35:55', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (2008, '人员管理新增', 2006, 2, '#', '', 1, 'F', '0', 'system:psndoc:add', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2009, '人员管理修改', 2006, 3, '#', '', 1, 'F', '0', 'system:psndoc:edit', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2010, '人员管理删除', 2006, 4, '#', '', 1, 'F', '0', 'system:psndoc:remove', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (2011, '人员管理导出', 2006, 5, '#', '', 1, 'F', '0', 'system:psndoc:export', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3884229334281682944, 'XX系统集成', 2, 60, 'extsystem', 'base/extsystem/index', 1, 'C', '0', 'monitor:extsystem:list', 'server', 1, '2019-12-19 09:31:19', 1, '2020-02-19 11:48:56', '', 'http://www.baidu.com?a=1&b=1&b=1&b=2&asd=1四川', 2);
INSERT INTO `sys_menu` VALUES (3884926974409605120, 'YY系统集成', 2, 70, 'sys2', 'base/extsystem/index', 0, 'C', '0', '', 'server', 1, '2019-12-19 15:09:43', 1, '2020-02-19 11:49:01', '', 'http://bing.com?B=2', 2);
INSERT INTO `sys_menu` VALUES (3937822004921221120, '服务治理', 2, 1, 'apps', 'monitor/apps/index', 1, 'C', '0', 'monitor:apps:list', 'monitor', 1, '2020-01-06 10:46:41', 1, '2020-01-06 16:00:01', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3941372087449264128, '服务查询', 3937822004921221120, 1, '', NULL, 1, 'F', '0', 'monitor:apps:list', '#', 1, '2020-01-07 15:28:34', NULL, NULL, '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3941373152601153536, '服务上下线', 3937822004921221120, 2, '', NULL, 1, 'F', '0', 'monitor:apps:status', '#', 1, '2020-01-07 15:29:05', NULL, NULL, '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3965875734866198528, '文件管理', 1, 1, 'file', 'system/file/index', 1, 'C', '0', 'system:file:view', 'file', 1, '2018-03-01 00:00:00', 1, '2020-02-19 02:50:38', '文件管理菜单', NULL, 2);
INSERT INTO `sys_menu` VALUES (3965875734866198529, '文件管理查询', 3965875734866198528, 1, '#', '', 1, 'F', '0', 'system:file:list', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3965875734866198530, '文件管理新增', 3965875734866198528, 2, '#', '', 1, 'F', '0', 'system:file:add', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3965875734866198531, '文件管理修改', 3965875734866198528, 3, '#', '', 1, 'F', '0', 'system:file:edit', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3965875734866198532, '文件管理删除', 3965875734866198528, 4, '#', '', 1, 'F', '0', 'system:file:remove', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3965875734866198533, '文件管理导出', 3965875734866198528, 5, '#', '', 1, 'F', '0', 'system:file:export', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (3971080204437028864, 'fontawesome', 0, 50, 'http://www.fontawesome.com.cn/', NULL, 0, 'M', '0', '', 'guide', 1, '2020-01-17 07:47:25', 1, '2020-01-17 07:50:10', '', NULL, 3);
INSERT INTO `sys_menu` VALUES (4068400143276097536, 'iconfont', 0, 50, 'https://www.iconfont.cn/', NULL, 0, 'M', '0', '', 'guide', 1, '2020-02-19 02:33:49', 1, '2020-02-19 02:34:02', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (4069480001133551616, '分布式锁', 2, 20, 'lock', 'monitor/lock/index', 1, 'C', '0', 'monitor:distributedLock:view', 'lock', 1, '2020-02-19 11:17:37', 1, '2020-02-19 11:40:40', '', NULL, 4);
INSERT INTO `sys_menu` VALUES (4086230717185482752, '调度日志', 108, 1, 'joblog', 'monitor/joblog/index', 1, 'C', '0', 'dispatcher:joblog:view', 'joblog', 1, '2018-03-01 00:00:00', 1, '2020-02-26 05:58:38', '任务调度日志菜单', NULL, 5);
INSERT INTO `sys_menu` VALUES (4086230717185482753, '任务调度日志查询', 4086230717185482752, 1, '#', '', 1, 'F', '0', 'job:joblog:list', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4086230717185482754, '任务调度日志新增', 4086230717185482752, 2, '#', '', 1, 'F', '0', 'job:joblog:add', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4086230717185482755, '任务调度日志修改', 4086230717185482752, 3, '#', '', 1, 'F', '0', 'job:joblog:edit', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4086230717185482756, '任务调度日志删除', 4086230717185482752, 4, '#', '', 1, 'F', '0', 'job:joblog:remove', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4086230717185482757, '任务调度日志导出', 4086230717185482752, 5, '#', '', 1, 'F', '0', 'job:joblog:export', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4131442841479290000, '监管码列表', 4131451946809950208, 1, 'emc', 'emc/index', 1, 'C', '0', 'emc:index:view', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '电子监管码', NULL, 1);
INSERT INTO `sys_menu` VALUES (4131442841479290001, '查询', 4131442841479290000, 1, '#', '', 1, 'F', '0', 'emc:record:query', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4131442841479290002, '新增', 4131442841479290000, 2, '#', '', 1, 'F', '0', 'emc:record:insert', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4131442841479290003, '修改', 4131442841479290000, 3, '#', '', 1, 'F', '0', 'emc:record:update', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4131442841479290004, '删除', 4131442841479290000, 4, '#', '', 1, 'F', '0', 'emc:record:delete', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4131442841479290005, '导出', 4131442841479290000, 5, '#', '', 1, 'F', '0', 'emc:record:export', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu` VALUES (4131451946809950208, '电子监管码', 0, 10, 'emc', NULL, 1, 'M', '0', '', 'component', 1, '2020-03-11 08:17:57', 1, '2020-03-11 08:18:28', '', NULL, 2);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` bigint(64) NOT NULL COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 xx新版本发布啦', '2', '新版本内容', 0, 1, '2018-03-16 11:33:00', 1, '2020-01-10 02:54:46', '管理员', 2);
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 xx系统凌晨维护', '1', '<p>维护内容</p>', 0, 1, '2018-03-16 11:33:00', 1, '2019-12-31 16:10:01', '管理员', 1);

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `org_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '上级组织id',
  `org_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织编码',
  `org_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '组织名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `dr` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `org_short_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织简称',
  `credit_code` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '社会统一信用代码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织地址',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3882052679215800321 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (200, NULL, '001', 'XX集团', 0, '11', '18111111111', '1@q.com', 0, 0, 1, '2019-11-27 14:55:31', 1, '2020-01-10 03:27:29', '11', '11', '11', 8);
INSERT INTO `sys_org` VALUES (201, 200, '00101', 'X1公司X1公司X1公司X1公司X1公司X1公司', 1, NULL, NULL, NULL, 0, 0, 1, '2019-11-27 16:31:33', 1, '2020-01-10 03:27:29', '22', NULL, NULL, 6);
INSERT INTO `sys_org` VALUES (202, 200, '00102', 'X2公司', 5, NULL, NULL, NULL, 0, 0, 1, '2019-11-27 16:31:46', 1, '2019-12-21 16:48:51', NULL, NULL, NULL, 1);
INSERT INTO `sys_org` VALUES (203, NULL, '002', 'YY集团', 5, NULL, NULL, NULL, 0, 0, 1, '2019-11-27 16:32:16', 1, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_org` VALUES (204, 203, '00201', 'Y1公司', 5, NULL, NULL, NULL, 0, 0, 1, '2019-11-27 16:32:28', 1, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_org` VALUES (205, 203, '00202', 'Y2公司', 10, NULL, NULL, NULL, 0, 0, 1, '2019-11-27 16:32:40', 1, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_org` VALUES (206, 201, '0010101', 'X1子公司', 1, NULL, NULL, NULL, 0, 0, 1, '2019-11-27 16:35:36', 1, '2020-01-10 03:27:29', NULL, NULL, NULL, 4);
INSERT INTO `sys_org` VALUES (207, 201, '1', '1', 1, '1', '18111111111', '1@Q.COM', 0, 1, 1, '2019-12-11 14:15:48', 1, NULL, '1', '1', '1', 1);
INSERT INTO `sys_org` VALUES (208, 206, '1', '1', 1, '1', NULL, NULL, 0, 1, 1, '2019-12-11 14:16:09', 1, NULL, '1', '1', '1', 1);
INSERT INTO `sys_org` VALUES (3882052679215800320, 202, '0010201', 'X22公司', 1, NULL, NULL, NULL, 0, 0, 1, '2019-12-18 15:55:34', 1, '2019-12-21 16:48:50', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` tinyint(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, 0, 1, '2018-03-16 11:33:00', 1, '2020-01-10 02:54:31', '', 2);
INSERT INTO `sys_post` VALUES (2, 'pm', '项目经理', 2, 0, 1, '2018-03-16 11:33:00', 1, '2019-12-03 17:26:41', '', 1);
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '', 1);
INSERT INTO `sys_post` VALUES (4, 'normal', '普通员工', 4, 0, 1, '2018-03-16 11:33:00', 1, '2019-12-03 17:26:51', '', 1);
INSERT INTO `sys_post` VALUES (5, 'P2', 'P2', 7, 0, 1, '2019-12-03 17:26:05', 1, '2019-12-03 17:26:23', NULL, 1);

-- ----------------------------
-- Table structure for sys_psndoc
-- ----------------------------
DROP TABLE IF EXISTS `sys_psndoc`;
CREATE TABLE `sys_psndoc`  (
  `psn_id` bigint(20) NOT NULL COMMENT '人员ID',
  `org_id` bigint(20) NOT NULL COMMENT '组织ID',
  `dept_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '部门ID',
  `dept_id1` bigint(20) NULL DEFAULT 0 COMMENT '兼职部门1',
  `dept_id2` bigint(20) NULL DEFAULT 0 COMMENT '兼职部门2',
  `dept_id3` bigint(20) NULL DEFAULT 0 COMMENT '兼职部门3',
  `post_id` bigint(64) NULL DEFAULT 0 COMMENT '岗位',
  `psn_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员工号',
  `psn_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员姓名',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '电子邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `homephone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭电话',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '人员状态（0在职 1离职）',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `dr` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(20) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`psn_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_psndoc
-- ----------------------------
INSERT INTO `sys_psndoc` VALUES (100, 200, 104, 102, 104, 109, NULL, '1', '张张张张', '', '', NULL, '0', '/dev-api/file-api/common/download/3968283081215614976', 0, NULL, 0, 1, '2019-12-12 16:51:29', 1, '2020-01-16 17:10:41', NULL, 6);
INSERT INTO `sys_psndoc` VALUES (101, 200, 105, NULL, NULL, NULL, NULL, '2', '2', '', '', NULL, '0', '/common/download/3967536409741066240', 0, NULL, 0, 1, '2019-12-12 17:16:19', 1, '2020-01-16 11:08:29', NULL, 3);
INSERT INTO `sys_psndoc` VALUES (102, 203, 203, NULL, NULL, NULL, NULL, '11', '11', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-13 11:49:34', 1, NULL, NULL, 1);
INSERT INTO `sys_psndoc` VALUES (103, 200, 102, NULL, NULL, NULL, NULL, '3', '3', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-13 13:56:31', 1, '2019-12-19 14:56:58', NULL, 1);
INSERT INTO `sys_psndoc` VALUES (104, 200, 100, NULL, NULL, NULL, NULL, '4', '4', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-13 15:09:32', 1, '2019-12-13 15:10:06', NULL, 1);
INSERT INTO `sys_psndoc` VALUES (105, 200, 100, NULL, NULL, NULL, NULL, '9', '9', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-17 17:38:41', 1, NULL, NULL, 1);
INSERT INTO `sys_psndoc` VALUES (106, 200, 100, NULL, NULL, NULL, NULL, '5', '5', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-17 17:40:51', 1, NULL, NULL, 1);
INSERT INTO `sys_psndoc` VALUES (107, 200, 100, NULL, NULL, NULL, NULL, '6', '6', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-17 17:41:20', 1, NULL, NULL, 1);
INSERT INTO `sys_psndoc` VALUES (108, 200, 100, NULL, NULL, NULL, NULL, '6', '6', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-17 17:43:20', 1, NULL, NULL, 1);
INSERT INTO `sys_psndoc` VALUES (109, 200, 100, NULL, NULL, NULL, NULL, '7', '7', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-17 17:50:17', 1, NULL, NULL, 1);
INSERT INTO `sys_psndoc` VALUES (110, 200, 100, 100, 100, NULL, NULL, '10', '10', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-18 10:11:16', 1, '2019-12-18 10:13:36', NULL, 1);
INSERT INTO `sys_psndoc` VALUES (3881347583024685056, 200, 100, NULL, NULL, NULL, NULL, '11', '11', '', '', NULL, '0', '', 0, NULL, 0, 1, '2019-12-18 10:21:22', 1, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(64) NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` tinyint(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `dr` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', 1, '1', 0, 0, 1, '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', '管理员', 1);
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 0, 0, 1, '2018-03-16 11:33:00', 1, '2020-02-27 09:55:15', '普通角色', 3);
INSERT INTO `sys_role` VALUES (100, '在线用户', 'role1', 0, '1', 0, 0, 1, '2019-12-03 17:20:50', 1, '2020-03-05 15:08:46', NULL, 39);
INSERT INTO `sys_role` VALUES (4113824404594352128, 'XX集团XX角色', 'xx', 0, '1', 0, 0, 1, '2020-03-05 09:47:28', 1, '2020-03-10 07:35:11', NULL, 10);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(64) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(64) NOT NULL COMMENT '部门ID',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100, 1);
INSERT INTO `sys_role_dept` VALUES (2, 101, 1);
INSERT INTO `sys_role_dept` VALUES (2, 105, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(64) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(64) NOT NULL COMMENT '菜单ID',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1, 1);
INSERT INTO `sys_role_menu` VALUES (2, 107, 1);
INSERT INTO `sys_role_menu` VALUES (2, 1036, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1, 1);
INSERT INTO `sys_role_menu` VALUES (100, 2, 1);
INSERT INTO `sys_role_menu` VALUES (100, 103, 1);
INSERT INTO `sys_role_menu` VALUES (100, 104, 1);
INSERT INTO `sys_role_menu` VALUES (100, 109, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1017, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1018, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1019, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1020, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1021, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1022, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1023, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1024, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1025, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1046, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1047, 1);
INSERT INTO `sys_role_menu` VALUES (100, 1048, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 100, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 101, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 102, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 103, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 104, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 105, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 106, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 107, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 111, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 112, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1001, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1002, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1003, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1004, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1005, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1006, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1007, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1008, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1009, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1010, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1011, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1012, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1013, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1014, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1015, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1016, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1017, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1018, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1019, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1020, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1021, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1022, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1023, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1024, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1025, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1026, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1027, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1028, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1029, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1030, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1031, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1032, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1033, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1034, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1035, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1036, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1037, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1038, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 1039, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2000, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2001, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2002, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2003, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2004, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2005, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2006, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2007, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2008, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2009, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2010, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 2011, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3937822004921221120, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3941372087449264128, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3941373152601153536, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3965875734866198528, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3965875734866198529, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3965875734866198530, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3965875734866198531, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3965875734866198532, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 3965875734866198533, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110105890628575232, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110105890628575233, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110105890628575234, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110105890628575235, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110105890628575236, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110105890628575237, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892416, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892417, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892418, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892419, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892420, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892421, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892426, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892427, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892428, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110582322760892429, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110830846748475392, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110830846748475393, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110830846748475394, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110830846748475395, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110830846748475396, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4110830846748475397, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111013400038416384, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111013400038416385, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111013400038416386, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111013400038416387, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111013400038416388, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111013400038416389, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111293809863311360, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111293809863311361, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111293809863311362, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111293809863311363, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111293809863311364, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111293809863311365, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111598718181744640, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111598718181744641, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111598718181744642, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111598718181744643, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111598718181744644, 1);
INSERT INTO `sys_role_menu` VALUES (4113824404594352128, 4111598718181744645, 1);

-- ----------------------------
-- Table structure for sys_role_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_org`;
CREATE TABLE `sys_role_org`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色主键',
  `org_id` bigint(20) NOT NULL COMMENT '组织主键',
  `version` bigint(20) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`role_id`, `org_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_org
-- ----------------------------
INSERT INTO `sys_role_org` VALUES (2, 203, 1);
INSERT INTO `sys_role_org` VALUES (2, 204, 1);
INSERT INTO `sys_role_org` VALUES (2, 205, 1);
INSERT INTO `sys_role_org` VALUES (4113824404594352128, 200, 1);
INSERT INTO `sys_role_org` VALUES (4113824404594352128, 202, 1);
INSERT INTO `sys_role_org` VALUES (4113824404594352128, 203, 1);
INSERT INTO `sys_role_org` VALUES (4113824404594352128, 204, 1);
INSERT INTO `sys_role_org` VALUES (4113824404594352128, 3882052679215800320, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `org_id` bigint(20) NULL DEFAULT NULL,
  `user_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `psn_id` bigint(20) NULL DEFAULT NULL COMMENT '人员',
  `user_type` tinyint(1) NULL DEFAULT 0 COMMENT '用户类型（1普通用户,2系统用户）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `dr` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `version` bigint(20) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, NULL, 'admin', '系统管理员', NULL, 1, '', '', '0', '', '$2a$10$VadcOwRAk6/UwF/LQR7KGOK1b6IIcrumJqFOKXO5rj3792KYhaH3e', 0, 0, '127.0.0.1', '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', NULL, '2018-03-16 11:33:00', '管理员', '561f0e61147e4b47b1b4644d0a938acc', 19);
INSERT INTO `sys_user` VALUES (2, NULL, 'super', 'SUPER系统管理员', NULL, 1, '', '', '0', '', '$2a$10$qc4NDGd/5jZekfBDJN1Ku.xiL8K67RNGGtCE1u.eGeHSugcf1uc8W', 0, 0, '127.0.0.1', '2018-03-16 11:33:00', 1, '2018-03-16 11:33:00', 1, '2020-02-10 10:15:00', 'SUPER系统管理员', 'b1d8f402e1e94625be08be60043fd36b', 3);
INSERT INTO `sys_user` VALUES (3, 203, 'ceshi1', '测试1', NULL, 1, '', '', '0', '', '$2a$10$XmoszHlUqGhn7C0b.5ALT.Stvuz4m87/XX23WCdtaxOadyZBxEbcu', 0, 0, '', NULL, 1, '2019-12-30 15:50:50', 1, '2020-03-05 09:35:18', NULL, 'c14e70f6847b49ef82fe1147559d51e2', 47);
INSERT INTO `sys_user` VALUES (4102669790410588160, 200, 'ceshi2', '测试2', NULL, 1, '', '', '0', '', '$2a$10$cNNwI6IkjLqdM7P6Mm4QBuzAzuxZbcoPJMcoFH5UwpzXdHyOs7Qn6', 0, 0, '', NULL, 1, '2020-03-01 15:36:46', 1, '2020-03-05 10:03:06', NULL, 'd242c76ef7974e9b9e116635ce53019e', 6);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(64) NOT NULL COMMENT '用户ID',
  `post_id` bigint(64) NOT NULL COMMENT '岗位ID',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(64) NOT NULL COMMENT '用户ID',
  `role_id` bigint(64) NOT NULL COMMENT '角色ID',
  `version` bigint(64) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 1);
INSERT INTO `sys_user_role` VALUES (2, 100, 1);
INSERT INTO `sys_user_role` VALUES (3, 2, 1);
INSERT INTO `sys_user_role` VALUES (3917667200869605376, 2, 1);
INSERT INTO `sys_user_role` VALUES (4102669790410588160, 4113824404594352128, 1);


CREATE TABLE `sys_appreg` (
  `appreg_id` bigint(20) NOT NULL COMMENT '主键',
  `code` varchar(255) NOT NULL COMMENT '应用编码',
  `name` varchar(255) NOT NULL COMMENT '应用名称',
  `ssologinurl` varchar(500) DEFAULT NULL COMMENT '单点登录地址',
  `ssoregurl` varchar(500) DEFAULT NULL COMMENT '单点注册地址',
  `itfurl` varchar(500) DEFAULT NULL COMMENT '接口地址',
  `account` varchar(50) DEFAULT NULL COMMENT '账户',
  `password` varchar(500) DEFAULT NULL COMMENT '密码',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态(0正常,1停用)',
  `def1` varchar(100) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(100) DEFAULT NULL COMMENT '预留字段2',
  `def3` varchar(100) DEFAULT NULL COMMENT '预留字段3',
  `def4` varchar(100) DEFAULT NULL COMMENT '预留字段4',
  `def5` varchar(100) DEFAULT NULL COMMENT '预留字段5',
  `version` bigint(20) DEFAULT '1' COMMENT '版本',
  `dr` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`appreg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用注册';

INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4247515397808726016, '应用注册', 4247517974789103616, 1, 'appreg', 'integrate/appreg/index', 1, 'C', '0', 'integrate:appreg:list', '#', 1, '2020-04-19 00:00:00', 1, '2020-04-19 00:00:00', '应用注册菜单', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4247515397808726017, '应用注册查询', 4247515397808726016, 1, '#', '', 1, 'F', '0', 'integrate:appreg:query', '#', 1, '2020-04-19 00:00:00', 1, '2020-04-19 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4247515397808726018, '应用注册新增', 4247515397808726016, 2, '#', '', 1, 'F', '0', 'integrate:appreg:add', '#', 1, '2020-04-19 00:00:00', 1, '2020-04-19 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4247515397808726019, '应用注册修改', 4247515397808726016, 3, '#', '', 1, 'F', '0', 'integrate:appreg:edit', '#', 1, '2020-04-19 00:00:00', 1, '2020-04-19 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4247515397808726020, '应用注册删除', 4247515397808726016, 4, '#', '', 1, 'F', '0', 'integrate:appreg:remove', '#', 1, '2020-04-19 00:00:00', 1, '2020-04-19 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4247515397808726021, '应用注册导出', 4247515397808726016, 5, '#', '', 1, 'F', '0', 'integrate:appreg:export', '#', 1, '2020-04-19 00:00:00', 1, '2020-04-19 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4247517974789103616, '系统集成', 0, 5, '', NULL, 1, 'M', '0', NULL, 'international', 1, '2020-04-19 10:37:25', NULL, NULL, '', NULL, 1);


-- ----------------------------
-- Table structure for temp_emc_record
-- ----------------------------
DROP TABLE IF EXISTS `temp_emc_record`;
CREATE TABLE `temp_emc_record`  (
  `id` int(11) NULL DEFAULT NULL COMMENT 'id',
  `base` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基地',
  `sendOrderCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货单号',
  `emcCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子监管码',
  `custCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编码',
  `custName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `sendOrderDate` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货单日期',
  `batchCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批次号码',
  `invCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物料编码',
  `invName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物料名称',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `ts` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间戳',
  `dr` int(11) NULL DEFAULT NULL COMMENT '删除标志'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Function structure for queryDeptChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `queryDeptChildList`;
delimiter ;;
CREATE FUNCTION `queryDeptChildList`(rootId bigint)
 RETURNS varchar(4000) CHARSET utf8
BEGIN   
DECLARE fid varchar(4000) default NULL;   
DECLARE str varchar(4000) default rootId;   
DECLARE tempchar varchar(4000) default rootId;   
  
WHILE tempchar is not null  do   
    SET fid =(SELECT group_concat(dept_id) FROM sys_dept WHERE FIND_IN_SET(parent_id,tempchar) > 0);
    IF fid is not null and fid > 0 THEN   
        SET str = concat(str, ',', fid);   
        SET tempchar = fid;   
    ELSE   
        SET tempchar = fid;   
    END IF;   
	
END WHILE;   
return str;  
END
;;
delimiter ;

-- ----------------------------
-- Function structure for queryDeptParentList
-- ----------------------------
DROP FUNCTION IF EXISTS `queryDeptParentList`;
delimiter ;;
CREATE FUNCTION `queryDeptParentList`(rootId bigint)
 RETURNS varchar(4000) CHARSET utf8
BEGIN   
DECLARE fid bigint(64) default -1;   
DECLARE str varchar(4000) default rootId;   
  
WHILE rootId is not null  do   
    SET fid =(SELECT parent_id FROM sys_dept WHERE dept_id = rootId);   
    IF fid is not null and fid > 0  THEN   
        SET str = concat(str, ',', fid);   
        SET rootId = fid;   
    ELSE   
        SET rootId = fid;   
    END IF;   
	
END WHILE;   
return str;  
END
;;
delimiter ;

-- ----------------------------
-- Function structure for queryOrgParentList
-- ----------------------------
DROP FUNCTION IF EXISTS `queryOrgParentList`;
delimiter ;;
CREATE FUNCTION `queryOrgParentList`(rootId bigint)
 RETURNS varchar(4000) CHARSET utf8
BEGIN   
DECLARE fid bigint(64) default -1;   
DECLARE str varchar(4000) default rootId;   
  
WHILE rootId is not null  do   
    SET fid =(SELECT parent_id FROM sys_org WHERE org_id = rootId);   
    IF fid is not null and fid > 0 THEN   
        SET str = concat(str, ',', fid);   
        SET rootId = fid;   
    ELSE   
        SET rootId = fid;   
    END IF;   
	
END WHILE;   
return str;  
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
