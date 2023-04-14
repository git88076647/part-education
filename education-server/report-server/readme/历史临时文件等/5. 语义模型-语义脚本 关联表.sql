CREATE TABLE rep_sem_script (
	pk_sem_script bigint(20) NOT NULL COMMENT '表id',
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
	pk_semantic BIGINT NOT NULL COMMENT '语义模型id',
	pk_semscript BIGINT NOT NULL COMMENT '语义脚本id',
	CONSTRAINT `PRIMARY` PRIMARY KEY (pk_sem_script)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='语义模型和语义脚本关联表';

