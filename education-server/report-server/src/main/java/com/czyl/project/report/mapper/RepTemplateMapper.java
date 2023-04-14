package com.czyl.project.report.mapper;

import com.czyl.project.report.domain.RepTemplate;
import java.util.List;

/**
 * 报表模板Mapper接口
 * 
 * @author tanghx
 * @date 2020-04-08
 */
public interface RepTemplateMapper 
{
    /**
     * 查询报表模板
     * 
     * @param pkTemplate 报表模板ID
     * @return 报表模板
     */
    public RepTemplate selectRepTemplateById(Long pkTemplate);

    /**
     * 查询报表模板列表
     * 
     * @param repTemplate 报表模板
     * @return 报表模板集合
     */
    public List<RepTemplate> selectRepTemplateList(RepTemplate repTemplate);

    /**
     * 新增报表模板
     * 
     * @param repTemplate 报表模板
     * @return 结果
     */
    public int insertRepTemplate(RepTemplate repTemplate);

    /**
     * 修改报表模板
     * 
     * @param repTemplate 报表模板
     * @return 结果
     */
    public int updateRepTemplate(RepTemplate repTemplate);

    /**
     * 删除报表模板
     * 
     * @param pkTemplate 报表模板ID
     * @return 结果
     */
    public int deleteRepTemplateById(Long pkTemplate);

    /**
     * 批量删除报表模板
     * 
     * @param pkTemplates 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepTemplateByIds(Long[] pkTemplates);
}
