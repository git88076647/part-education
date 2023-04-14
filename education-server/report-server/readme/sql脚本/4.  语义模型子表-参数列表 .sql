CREATE TABLE rep_semparmar (
	pk_semparmar bigint(20) NOT NULL COMMENT '表id',
	code varchar(100)  NOT NULL COMMENT '编码',
	name varchar(100)  NOT NULL COMMENT '名字',
	pk_semantic varchar(300)  NULL COMMENT '语义模型id',
	valuestr text  NULL COMMENT '参数文本，比如 如果是固定的字符参数啥的 就是值',
	create_by bigint(20) NULL COMMENT '创建用户',
	create_time datetime DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
	update_by bigint(20) NULL COMMENT '修改人',
	update_time datetime NULL COMMENT '修改时间',
	version int(11) DEFAULT 0 NULL COMMENT '版本号-乐观锁',
	description varchar(255)  NULL COMMENT '备注',
	dr smallint(6) DEFAULT 0 NULL COMMENT '删除标志',
	statue tinyint(4) DEFAULT 0 NULL COMMENT '状态',
	org_id bigint(20) NULL COMMENT '组织id',
	javatype varchar(100) NULL COMMENT '参数的java类型class全名',
	CONSTRAINT primary_rep_semparmar PRIMARY KEY (pk_semparmar)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='语义模型的参数列表';

-- 菜单 SQL
insert into sys_menu (menu_id,menu_name, parent_id, order_num, path, component
, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921232815955968,'语义模型参数', '4196923328759996416'
, '1', 'semparmar', 'rep/semparmar/index', 1,
 'C', '0', 'rep:semparmar:list', '#', '1', '2018-03-01', '1', '2018-03-01', '语义模型参数菜单');

-- 按钮父菜单ID
SELECT @parentId := 4196921232815955968;

-- 按钮 SQL
insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921232815955969,'语义模型参数查询', @parentId, '1',  '#', '', 1,  'F', '0', 'rep:semparmar:query',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921232815955970,'语义模型参数新增', @parentId, '2',  '#', '', 1,  'F', '0', 'rep:semparmar:add',          '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921232815955971,'语义模型参数修改', @parentId, '3',  '#', '', 1,  'F', '0', 'rep:semparmar:edit',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921232815955972,'语义模型参数删除', @parentId, '4',  '#', '', 1,  'F', '0', 'rep:semparmar:remove',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921232815955973,'语义模型参数导出', @parentId, '5',  '#', '', 1,  'F', '0', 'rep:semparmar:export',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

