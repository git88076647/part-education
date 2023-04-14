package com.czyl.common.utils;

import com.czyl.project.domain.bo.TreeOptionBo;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 树形数据 BO 构建器 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/13 0013 16:10
 * @project
 * @Version
 */
public abstract class TreeBuilderUtil {
    /**
     * 把一个对象集合 组装成 前端可识别的 树形集合对象  <br/>
     * https://element.eleme.cn/#/zh-CN/component/cascader  <br/>
     *
     * @param vos          VO集合
     * @param idName       id列名 的字段名
     * @param fatherIdName 父级id列名 的字段名
     * @param labelName    前端 显示列名 的字段名
     * @param valueName    前端 值列名 的字段名
     * @return
     */
    public static List<TreeOptionBo> build(List<?> vos, String idName, String fatherIdName, String labelName
            , String valueName) throws NoSuchFieldException {
        if (CollectionUtil.isEmpty(vos)) {
            return Collections.emptyList();
        }
        List<TreeOptionBo> bo = new LinkedList<>();
        Field idField = ObjectUtil.getClassFieldByAll(vos.get(0).getClass(), idName);
        Field fatherIdField = ObjectUtil.getClassFieldByAll(vos.get(0).getClass(), fatherIdName);
        Field labelField = ObjectUtil.getClassFieldByAll(vos.get(0).getClass(), labelName);
        Field valueField = ObjectUtil.getClassFieldByAll(vos.get(0).getClass(), valueName);

        //第一，我先 构建 id 映射表
        HashMap<Object, Object> groupByIdMap = new HashMap<>(vos.size());
        Object idValue;
        Object fatherIdValue;
        Object labelValue;
        Object valueValue;
        for (Object vo : vos) {
            idValue = ObjectUtil.getFiledValue(vo, idField);
            groupByIdMap.put(idValue, vo);
        }

        TreeOptionBo node;
        LinkedList<TreeOptionBo> chnodes;
        //开始构建
        for (Object vo : vos) {
            fatherIdValue = ObjectUtil.getFiledValue(vo, fatherIdField);
            if (groupByIdMap.containsKey(fatherIdValue)) {
                //不是第一层
                continue;
            }

            node = new TreeOptionBo();
            node.setLabel(String.valueOf(ObjectUtil.getFiledValue(vo, labelField)));
            node.setValue(String.valueOf(ObjectUtil.getFiledValue(vo, valueField)));
            node.setChildren(getChildren(vos
                    , idField, fatherIdField
                    , labelField
                    , valueField
                    , ObjectUtil.getFiledValue(vo, idField)));
            bo.add(node);
        }

        return bo;
    }

    /**
     * 拿到孩子 <br/>
     *
     * @param vos           VO集合
     * @param idField       id列名 的字段
     * @param fatherIdField 父级id列名 的字段
     * @param labelField    前端 显示列名 的字段
     * @param valueField    前端 值列名 的字段
     * @param fatherValue   父级的值
     * @return
     */
    private static List<TreeOptionBo> getChildren(List<?> vos, Field idField
            , Field fatherIdField, Field labelField
            , Field valueField, Object fatherValue) throws NoSuchFieldException {

        List<TreeOptionBo> bo = new LinkedList<>();
        TreeOptionBo node;
        Object fid;
        boolean is;
        for (Object vo : vos) {
            fid = ObjectUtil.getFiledValue(vo, fatherIdField);
            is = fatherValue == fid;

            if (!is && null != fatherValue
                    && null != fid) {
                is = fatherValue.equals(fid);
            }

            if (!is) {
                //不是他孩子
                continue;
            }

            node = new TreeOptionBo();
            node.setLabel(String.valueOf(ObjectUtil.getFiledValue(vo, labelField)));
            node.setValue(String.valueOf(ObjectUtil.getFiledValue(vo, valueField)));
            node.setChildren(getChildren(vos
                    , idField, fatherIdField
                    , labelField
                    , valueField
                    , ObjectUtil.getFiledValue(vo, idField)));
            bo.add(node);
        }

        return bo;
    }
}
