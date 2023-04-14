package com.czyl.project.domain;

import java.util.Date;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.common.annotation.table.Version;
import com.czyl.framework.web.domain.BaseEntity;

import lombok.Data;

@Data
@Table("t_order")
public class OrderEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id("id")
	private Long id;
	private Date billdate;
	private String vnote;

	@Version
	private Long version;

}
