CREATE TABLE  rep_semantic (
	pk_semantic bigint(20) NOT NULL COMMENT '表id',
	code varchar(100)  NOT NULL COMMENT '编码',
	name varchar(100)  NOT NULL COMMENT '名字',
	datasource varchar(100)  NULL COMMENT '数据源',
	filednamejson json NULL COMMENT '结果集字段显示名称对照表json',
	formathtml TEXT NULL COMMENT '前端格式化设计生成的html内容',
	scripttxt varchar(5000) NULL COMMENT '脚本语句（比如完整的sql）',
	querclass varchar(100)  NULL  COMMENT '数据查询实现类',
	create_by bigint(20) NULL COMMENT '创建用户',
	create_time datetime DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
	update_by bigint(20) NULL COMMENT '修改人',
	update_time datetime NULL COMMENT '修改时间',
	version int(11) DEFAULT 0 NULL COMMENT '版本号-乐观锁',
	description varchar(255)  NULL COMMENT '备注',
	dr smallint(6) DEFAULT 0 NULL COMMENT '删除标志',
	statue tinyint(4) DEFAULT 0 NULL COMMENT '状态',
	org_id bigint(20) NULL COMMENT '组织id',
	CONSTRAINT primary_rep_semantic PRIMARY KEY (pk_semantic)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='语义模型';

-- 菜单 SQL
insert into sys_menu (menu_id,menu_name, parent_id, order_num, path
, component, is_frame, menu_type, visible, perms, icon, create_by
, create_time, update_by, update_time, remark)
values(4196921061017264128,'语义模型', '4196923328759996416', '1', 'semantic'
, 'rep/semantic/index', 1, 'C', '0', 'rep:semantic:list', '#', '1'
, '2018-03-01', '1', '2018-03-01', '语义模型菜单');

-- 按钮父菜单ID
SELECT @parentId := 4196921061017264128;

-- 按钮 SQL
insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017264129,'语义模型查询', @parentId, '1',  '#', '', 1,  'F', '0', 'rep:semantic:query',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017264130,'语义模型新增', @parentId, '2',  '#', '', 1,  'F', '0', 'rep:semantic:add',          '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017264131,'语义模型修改', @parentId, '3',  '#', '', 1,  'F', '0', 'rep:semantic:edit',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017264132,'语义模型删除', @parentId, '4',  '#', '', 1,  'F', '0', 'rep:semantic:remove',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017264133,'语义模型导出', @parentId, '5',  '#', '', 1,  'F', '0', 'rep:semantic:export',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

