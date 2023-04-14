package com.czyl.framework;

/**
 * @author hhhcccggg
 * @Date 2022/2/17 11:01
 * @Description TODO
 **/
@FunctionalInterface
public interface SQLGenerator {


    /**
     * 权限sql拼接
     *
     * @param permissionData 权限数据
     * @return
     */
    String execute(String permissionData);
}
