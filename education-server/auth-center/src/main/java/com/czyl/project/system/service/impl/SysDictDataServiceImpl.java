package com.czyl.project.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czyl.project.system.domain.SysDictData;
import com.czyl.project.system.mapper.SysDictDataMapper;
import com.czyl.project.system.service.ISysDictDataService;

/**
 * 字典 业务层处理
 * 
 * @author tanghx
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * 根据条件分页查询字典数据
	 * 
	 * @param dictData 字典数据信息
	 * @return 字典数据集合信息
	 */
	@Override
	public List<SysDictData> selectDictDataList(SysDictData dictData) {
		return dictDataMapper.selectDictDataList(dictData);
	}

	/**
	 * 根据字典类型查询字典数据
	 * 
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	@Override
	public List<SysDictData> selectDictDataByType(String dictType) {
		return dictDataMapper.selectDictDataByType(dictType);
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 * 
	 * @param dictType  字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 */
	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		return dictDataMapper.selectDictLabel(dictType, dictValue);
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 *
	 * @param dictType  字典类型
	 * @param dictValues 字典键值
	 * @return 字典标签
	 */
	@Override
	public List<SysDictData> selectDictLabels(String dictType, List<String> dictValues) {
		return dictDataMapper.selectDictLabels(dictType,dictValues);
	}

	/**
	 * 根据字典数据ID查询信息
	 * 
	 * @param dictCode 字典数据ID
	 * @return 字典数据
	 */
	@Override
	public SysDictData selectDictDataById(Long dictCode) {
		return dictDataMapper.selectDictDataById(dictCode);
	}

	/**
	 * 通过字典ID删除字典数据信息
	 * 
	 * @param dictCode 字典数据ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteDictDataById(Long dictCode) {
		return dictDataMapper.deleteDictDataById(dictCode);
	}

	/**
	 * 批量删除字典数据信息
	 * 
	 * @param dictCodes 需要删除的字典数据ID
	 * @return 结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteDictDataByIds(Long[] dictCodes) {
		return dictDataMapper.deleteDictDataByIds(dictCodes);
	}

	/**
	 * 新增保存字典数据信息
	 * 
	 * @param dictData 字典数据信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertDictData(SysDictData dictData) {
		return dictDataMapper.insertDictData(dictData);
	}

	/**
	 * 修改保存字典数据信息
	 * 
	 * @param dictData 字典数据信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateDictData(SysDictData dictData) {
		return dictDataMapper.updateDictData(dictData);
	}
}
