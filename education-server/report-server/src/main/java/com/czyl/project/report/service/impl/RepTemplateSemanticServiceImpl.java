package com.czyl.project.report.service.impl;

import java.util.List;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.AppContextUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.czyl.project.report.mapper.RepTemplateSemanticMapper;
import com.czyl.project.report.domain.RepTemplateSemantic;
import com.czyl.project.report.service.IRepTemplateSemanticService;

/**
 * 报表模板_语义模型关联Service业务层处理
 * 
 * @author tanghx
 * @date 2020-04-08
 */
@Service
public class RepTemplateSemanticServiceImpl implements IRepTemplateSemanticService 
{
    @Autowired
    private RepTemplateSemanticMapper repTemplateSemanticMapper;

    /**
     * 查询报表模板_语义模型关联
     * 
     * @param pkTemplateSemantic 报表模板_语义模型关联ID
     * @return 报表模板_语义模型关联
     */
    @Override
    public RepTemplateSemantic selectRepTemplateSemanticById(Long pkTemplateSemantic)
    {
        return repTemplateSemanticMapper.selectRepTemplateSemanticById(pkTemplateSemantic);
    }

    /**
     * 查询报表模板_语义模型关联列表
     * 
     * @param repTemplateSemantic 报表模板_语义模型关联
     * @return 报表模板_语义模型关联
     */
    @Override
    public List<RepTemplateSemantic> selectRepTemplateSemanticList(RepTemplateSemantic repTemplateSemantic)
    {
        return repTemplateSemanticMapper.selectRepTemplateSemanticList(repTemplateSemantic);
    }

    /**
     * 新增报表模板_语义模型关联
     * 
     * @param repTemplateSemantic 报表模板_语义模型关联
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRepTemplateSemantic(RepTemplateSemantic repTemplateSemantic)
    {
		if(repTemplateSemantic.getCreateBy() == null || repTemplateSemantic.getCreateBy() == 0) {
			repTemplateSemantic.setCreateBy(AppContextUtils.getUserId());
		}
        repTemplateSemantic.setCreateTime(DateUtils.getNowDate());
        return repTemplateSemanticMapper.insertRepTemplateSemantic(repTemplateSemantic);
    }

    /**
     * 修改报表模板_语义模型关联
     * 
     * @param repTemplateSemantic 报表模板_语义模型关联
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRepTemplateSemantic(RepTemplateSemantic repTemplateSemantic)
    {
        return repTemplateSemanticMapper.updateRepTemplateSemantic(repTemplateSemantic);
    }

    /**
     * 批量删除报表模板_语义模型关联
     * 
     * @param pkTemplateSemantics 需要删除的报表模板_语义模型关联ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRepTemplateSemanticByIds(Long[] pkTemplateSemantics)
    {
        return repTemplateSemanticMapper.deleteRepTemplateSemanticByIds(pkTemplateSemantics);
    }

    /**
     * 删除报表模板_语义模型关联信息
     * 
     * @param pkTemplateSemantic 报表模板_语义模型关联ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRepTemplateSemanticById(Long pkTemplateSemantic)
    {
        return repTemplateSemanticMapper.deleteRepTemplateSemanticById(pkTemplateSemantic);
    }
}
