package com.czyl.framework.web.domain;

import java.util.HashMap;

/**
 * 基础VO
 * @author tanghx
 *
 */
public class SuperVO extends HashMap<String,Object> {

	private static final long serialVersionUID = 1L;

	public String[] getAttrs() {
		return this.keySet().toArray(new String[0]);
	}
	
	public Object getAttr(String attr) {
		return this.get(attr);
	}
	
	public SuperVO setAttr(String attr,Object value) {
		this.put(attr,value);
		return this;
	}
	
	
}
