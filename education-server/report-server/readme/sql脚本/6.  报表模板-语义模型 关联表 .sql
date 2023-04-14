CREATE TABLE rep_template_semantic
(
    pk_template_semantic bigint(20)                                              NOT NULL COMMENT '表id',
    pk_template          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报表模板',
    create_by            bigint(20)                                              NULL COMMENT '创建用户',
    create_time          datetime    DEFAULT CURRENT_TIMESTAMP                   NULL COMMENT '创建时间',
    version              int(11)     DEFAULT 0                                   NULL COMMENT '版本号-乐观锁',
    description          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
    dr                   smallint(6) DEFAULT 0                                   NULL COMMENT '删除标志',
    pk_semantic          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '语义模型id',
    CONSTRAINT primary_template_semantic PRIMARY KEY (pk_template_semantic)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci
    COMMENT ='报表模板_语义模型关联表';
ALTER TABLE rep_template_semantic
    ADD CONSTRAINT rep_template_semantic_un UNIQUE KEY (pk_template, pk_semantic);
