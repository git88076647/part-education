package com.czyl.project.domain.bo;

import lombok.Data;
import java.util.List;

/**
 * 前端 树形 组件要求的数据格式 的 BO <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/13 0013 17:04
 * @project
 * @Version
 */
@Data
public class TreeOptionBo {
    private String label;
    private String value;
    private List<TreeOptionBo> children;
}
