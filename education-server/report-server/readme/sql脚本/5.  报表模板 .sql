CREATE TABLE rep_template
(
    pk_template bigint(20)                                              NOT NULL COMMENT '表id',
    code        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
    name        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
    formatjson  json  NULL COMMENT '格式设计json',
    formathtml  message_text   NULL COMMENT '格式生成html',
    create_by   bigint(20)                                              NULL COMMENT '创建用户',
    create_time datetime    DEFAULT CURRENT_TIMESTAMP                   NULL COMMENT '创建时间',
    update_by   bigint(20)                                              NULL COMMENT '修改人',
    update_time datetime                                                NULL COMMENT '修改时间',
    version     int(11)     DEFAULT 0                                   NULL COMMENT '版本号-乐观锁',
    description varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
    dr          smallint(6) DEFAULT 0                                   NULL COMMENT '删除标志',
    statue      tinyint(4)  DEFAULT 0                                   NULL COMMENT '状态',
    org_id      bigint(20)                                              NULL COMMENT '组织id',
    CONSTRAINT primary_rep_template PRIMARY KEY (pk_template)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci
    COMMENT ='报表模板';



-- 菜单 SQL
insert into sys_menu (menu_id,menu_name, parent_id, order_num, path
, component, is_frame, menu_type, visible, perms, icon, create_by
, create_time, update_by, update_time, remark)
values(4196921061017265130,'报表模板', '4196923328759996416', '6', 'template'
, 'rep/template/index', 1, 'C', '0', 'rep:template:list', '#', '1'
, '2018-03-01', '1', '2018-03-01', '报表模板菜单');

-- 按钮父菜单ID
SELECT @parentId := 4196921061017265130;

-- 按钮 SQL
insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017265150,'报表模板查询', @parentId, '1',  '#', '', 1,  'F', '0', 'rep:template:query',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017265151,'报表模板新增', @parentId, '2',  '#', '', 1,  'F', '0', 'rep:template:add',          '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017265152,'报表模板修改', @parentId, '3',  '#', '', 1,  'F', '0', 'rep:template:edit',         '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017265153,'报表模板删除', @parentId, '4',  '#', '', 1,  'F', '0', 'rep:template:remove',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

insert into sys_menu  (menu_id,menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4196921061017265154,'报表模板导出', @parentId, '5',  '#', '', 1,  'F', '0', 'rep:template:export',       '#', '1', '2018-03-01', '1', '2018-03-01', '');

