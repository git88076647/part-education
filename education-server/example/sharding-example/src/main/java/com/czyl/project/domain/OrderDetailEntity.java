package com.czyl.project.domain;

import java.util.Date;

import com.czyl.framework.web.domain.BaseEntity;

import lombok.Data;

@Data
public class OrderDetailEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long order_id;
	private Long material_id;
	private Date billdate;
	
	

}
