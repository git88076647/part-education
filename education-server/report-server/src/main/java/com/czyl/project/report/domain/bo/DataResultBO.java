package com.czyl.project.report.domain.bo;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 最终 查询出来的  数据的 封装类 <br/>
 * 用于和前台的JSON交互<br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/1 0001 14:03
 * @project
 * @Version
 */
@Data
public class DataResultBO extends HashMap {
    /**
     * 数据
     */
    private List<Map<String, Object>> datas;
    /**
     * 分页信息
     */
    private Page<?> pageInfo;
}
