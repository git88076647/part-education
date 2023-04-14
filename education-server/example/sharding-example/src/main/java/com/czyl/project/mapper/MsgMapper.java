package com.czyl.project.mapper;

import java.util.List;

import com.czyl.project.domain.Msg;

public interface MsgMapper {

	public Msg selectById(Long id);

	public List<Msg> selectList(Msg msg);

	int insertBatch(List<Msg> list);

}
