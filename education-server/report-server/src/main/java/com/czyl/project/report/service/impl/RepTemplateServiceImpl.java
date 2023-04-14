package com.czyl.project.report.service.impl;

import java.util.List;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.AppContextUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.czyl.project.report.mapper.RepTemplateMapper;
import com.czyl.project.report.domain.RepTemplate;
import com.czyl.project.report.service.IRepTemplateService;

/**
 * 报表模板Service业务层处理
 * 
 * @author tanghx
 * @date 2020-04-08
 */
@Service
public class RepTemplateServiceImpl implements IRepTemplateService 
{
    @Autowired
    private RepTemplateMapper repTemplateMapper;

    /**
     * 查询报表模板
     * 
     * @param pkTemplate 报表模板ID
     * @return 报表模板
     */
    @Override
    public RepTemplate selectRepTemplateById(Long pkTemplate)
    {
        return repTemplateMapper.selectRepTemplateById(pkTemplate);
    }

    /**
     * 查询报表模板列表
     * 
     * @param repTemplate 报表模板
     * @return 报表模板
     */
    @Override
    public List<RepTemplate> selectRepTemplateList(RepTemplate repTemplate)
    {
        return repTemplateMapper.selectRepTemplateList(repTemplate);
    }

    /**
     * 新增报表模板
     * 
     * @param repTemplate 报表模板
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRepTemplate(RepTemplate repTemplate)
    {
		if(repTemplate.getCreateBy() == null || repTemplate.getCreateBy() == 0) {
			repTemplate.setCreateBy(AppContextUtils.getUserId());
		}
        repTemplate.setCreateTime(DateUtils.getNowDate());
        return repTemplateMapper.insertRepTemplate(repTemplate);
    }

    /**
     * 修改报表模板
     * 
     * @param repTemplate 报表模板
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRepTemplate(RepTemplate repTemplate)
    {
        if(repTemplate.getUpdateBy() == null || repTemplate.getUpdateBy() == 0) {
			repTemplate.setUpdateBy(AppContextUtils.getUserId());
		}
        repTemplate.setUpdateTime(DateUtils.getNowDate());
        return repTemplateMapper.updateRepTemplate(repTemplate);
    }

    /**
     * 批量删除报表模板
     * 
     * @param pkTemplates 需要删除的报表模板ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRepTemplateByIds(Long[] pkTemplates)
    {
        return repTemplateMapper.deleteRepTemplateByIds(pkTemplates);
    }

    /**
     * 删除报表模板信息
     * 
     * @param pkTemplate 报表模板ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRepTemplateById(Long pkTemplate)
    {
        return repTemplateMapper.deleteRepTemplateById(pkTemplate);
    }
}
