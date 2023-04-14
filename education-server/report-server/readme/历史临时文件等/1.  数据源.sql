CREATE TABLE rep_datasource (
	pk_datasource BIGINT NOT NULL COMMENT '表id',
	code varchar(100) NOT NULL COMMENT '数据源的编码',
	name varchar(100) NOT NULL COMMENT '数据源的名字',
	ip varchar(300) NULL COMMENT 'ip地址',
	port varchar(5) NULL COMMENT '端口',
	dbuser varchar(100) NULL COMMENT '数据库认证用户',
	dbpass varchar(100) NULL COMMENT '认证密码',
	dbname varchar(100) NULL COMMENT '数据库名字',
	authdbname varchar(100) NULL COMMENT '认证数据库名-如果数据库需要的话',
	otherparmas varchar(100) NULL COMMENT '其他数据库参数-使用url参数语法',
    queryclass varchar(100) NULL  COMMENT '查询实现类',
	def1 varchar(100) NULL,
	def2 varchar(100) NULL,
	def3 varchar(100) NULL,
	def4 varchar(100) NULL,
	def5 varchar(100) NULL,
	org_id BIGINT NULL COMMENT '组织id',
	create_by BIGINT NULL COMMENT '创建用户',
	create_time DATETIME DEFAULT CURRENT_TIMESTAMP  NULL COMMENT '创建时间',
	update_by BIGINT NULL COMMENT '修改人',
	update_time DATETIME NULL COMMENT '修改时间',
	version INT DEFAULT 0 NULL COMMENT '版本号-乐观锁',
	description varchar(255) NULL,
	dr SMALLINT DEFAULT 0 NULL COMMENT '删除标志',
	statue TINYINT DEFAULT 0 NULL COMMENT '状态',
	needauth SMALLINT DEFAULT 0 NULL COMMENT '是否需要认证，0不需要，1需要',
	CONSTRAINT frp_datasource_pk PRIMARY KEY (pk_datasource)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='数据源列表';

-- 菜单 SQL
insert into sys_menu (menu_id,menu_name, parent_id, order_num, path
, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196920923578310656,'数据源', '4196923328759996416', '1', 'datasource',
 'rep/datasource/index', 1, 'C', '0', 'rep:datasource:list', '#', '1', '2018-03-01', '1', '2018-03-01', '数据源菜单');

-- 按钮父菜单ID
SELECT @parentId := 4196920923578310656;

-- 按钮 SQL
insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196920923578310657,'数据源查询', @parentId, '1',  '#', '', 1,  'F', '0', 'rep:datasource:query',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196920923578310658,'数据源新增', @parentId, '2',  '#', '', 1,  'F', '0', 'rep:datasource:add',          '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196920923578310659,'数据源修改', @parentId, '3',  '#', '', 1,  'F', '0', 'rep:datasource:edit',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196920923578310660,'数据源删除', @parentId, '4',  '#', '', 1,  'F', '0', 'rep:datasource:remove',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196920923578310661,'数据源导出', @parentId, '5',  '#', '', 1,  'F', '0', 'rep:datasource:export',       '#', '1', '2018-03-01', '1', '2018-03-01', '');
