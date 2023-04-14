CREATE TABLE rep_semscript (
	pk_semscript bigint(20) NOT NULL COMMENT '表id',
	code varchar(100)  NOT NULL COMMENT '编码',
	name varchar(100)  NOT NULL COMMENT '名字',
	scripttxt text  NULL COMMENT '脚本内容(比如SQL语句)',
	def1 varchar(100)  NULL,
	def2 varchar(100)  NULL,
	def3 varchar(100)  NULL,
	def4 varchar(100)  NULL,
	def5 varchar(100)  NULL,
	create_by bigint(20) NULL COMMENT '创建用户',
	create_time datetime DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
	update_by bigint(20) NULL COMMENT '修改人',
	update_time datetime NULL COMMENT '修改时间',
	version int(11) DEFAULT 0 NULL COMMENT '版本号-乐观锁',
	description varchar(255)  NULL COMMENT '备注',
	dr smallint(6) DEFAULT 0 NULL COMMENT '删除标志',
	statue tinyint(4) DEFAULT 0 NULL COMMENT '状态',
	org_id bigint(20) NULL COMMENT '组织id',
	CONSTRAINT primary_rep_semscript PRIMARY KEY (pk_semscript)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='语义脚本';


-- 菜单 SQL
insert into sys_menu (menu_id,menu_name, parent_id
, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921370254909440,'语义脚本', '4196923328759996416', '1'
, 'semscript', 'rep/semscript/index', 1, 'C', '0', 'rep:semscript:list', '#', '1', '2018-03-01', '1', '2018-03-01', '语义脚本菜单');

-- 按钮父菜单ID
SELECT @parentId := 4196921370254909440;

-- 按钮 SQL
insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921370254909441,'语义脚本查询', @parentId, '1',  '#', '', 1,  'F', '0', 'rep:semscript:query',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921370254909442,'语义脚本新增', @parentId, '2',  '#', '', 1,  'F', '0', 'rep:semscript:add',          '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921370254909443,'语义脚本修改', @parentId, '3',  '#', '', 1,  'F', '0', 'rep:semscript:edit',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921370254909444,'语义脚本删除', @parentId, '4',  '#', '', 1,  'F', '0', 'rep:semscript:remove',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921370254909445,'语义脚本导出', @parentId, '5',  '#', '', 1,  'F', '0', 'rep:semscript:export',       '#', '1', '2018-03-01', '1', '2018-03-01', '');
