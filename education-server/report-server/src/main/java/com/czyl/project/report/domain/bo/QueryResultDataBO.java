package com.czyl.project.report.domain.bo;

import com.czyl.common.meta.DataResultMeta;
import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

/**
 * 语义模型 执行后 查询结果数据 前台交互 bo<br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/8 0008 14:46
 * @project
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResultDataBO {
    /**
     * 所有字段名
     */
    private List<String> allColumnNames;
    /**
     * 数据
     */
    private List<Map<String, Object>> datas;
    /**
     * 分页信息
     */
    private Page<?> pageInfo;
    /**
     * 结果结构描述
     */
    private DataResultMeta dataResultMeta;
}
