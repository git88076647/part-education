package com.czyl.project.report.mapper;

import com.czyl.project.report.domain.RepTemplateSemantic;
import java.util.List;

/**
 * 报表模板_语义模型关联Mapper接口
 * 
 * @author tanghx
 * @date 2020-04-08
 */
public interface RepTemplateSemanticMapper 
{
    /**
     * 查询报表模板_语义模型关联
     * 
     * @param pkTemplateSemantic 报表模板_语义模型关联ID
     * @return 报表模板_语义模型关联
     */
    public RepTemplateSemantic selectRepTemplateSemanticById(Long pkTemplateSemantic);

    /**
     * 查询报表模板_语义模型关联列表
     * 
     * @param repTemplateSemantic 报表模板_语义模型关联
     * @return 报表模板_语义模型关联集合
     */
    public List<RepTemplateSemantic> selectRepTemplateSemanticList(RepTemplateSemantic repTemplateSemantic);

    /**
     * 新增报表模板_语义模型关联
     * 
     * @param repTemplateSemantic 报表模板_语义模型关联
     * @return 结果
     */
    public int insertRepTemplateSemantic(RepTemplateSemantic repTemplateSemantic);

    /**
     * 修改报表模板_语义模型关联
     * 
     * @param repTemplateSemantic 报表模板_语义模型关联
     * @return 结果
     */
    public int updateRepTemplateSemantic(RepTemplateSemantic repTemplateSemantic);

    /**
     * 删除报表模板_语义模型关联
     * 
     * @param pkTemplateSemantic 报表模板_语义模型关联ID
     * @return 结果
     */
    public int deleteRepTemplateSemanticById(Long pkTemplateSemantic);

    /**
     * 批量删除报表模板_语义模型关联
     * 
     * @param pkTemplateSemantics 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepTemplateSemanticByIds(Long[] pkTemplateSemantics);
}
