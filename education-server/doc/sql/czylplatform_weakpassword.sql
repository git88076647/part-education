CREATE TABLE `sys_weakpassword` (
  `pwd_id` bigint(20) NOT NULL COMMENT '主键',
  `password` varchar(30) binary CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT '1' COMMENT '版本',
  `dr` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志(0存在 1删除)',
  PRIMARY KEY (`pwd_id`),
  KEY `I_WEAPWD_PWD2` (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='弱口令管理';

INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4213904392498249728, '弱口令管理', 1, 40, 'weakpassword', 'system/weakpassword/index', 1, 'C', '0', 'system:weakpassword:list', 'password', 1, '2018-03-01 00:00:00', 1, '2020-04-08 02:56:18', '弱口令管理菜单', NULL, 2);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4213904392498249729, '弱口令管理查询', 4213904392498249728, 1, '#', '', 1, 'F', '0', 'system:weakpassword:query', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4213904392498249730, '弱口令管理新增', 4213904392498249728, 2, '#', '', 1, 'F', '0', 'system:weakpassword:add', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4213904392498249731, '弱口令管理修改', 4213904392498249728, 3, '#', '', 1, 'F', '0', 'system:weakpassword:edit', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4213904392498249732, '弱口令管理删除', 4213904392498249728, 4, '#', '', 1, 'F', '0', 'system:weakpassword:remove', '#', 1, '2018-03-01 00:00:00', 1, '2018-03-01 00:00:00', '', NULL, 1);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4213904392498249733, '弱口令管理导出', 4213904392498249728, 10, '#', '', 1, 'F', '0', 'system:weakpassword:export', '#', 1, '2018-03-01 00:00:00', 1, '2020-04-13 09:31:57', '', NULL, 2);
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `ext_url`, `version`) VALUES (4229570646488432640, '弱口令管理导入', 4213904392498249728, 5, '', NULL, 1, 'F', '0', 'system:weakpassword:import', '#', 1, '2020-04-13 09:31:47', NULL, NULL, '', NULL, 1);
