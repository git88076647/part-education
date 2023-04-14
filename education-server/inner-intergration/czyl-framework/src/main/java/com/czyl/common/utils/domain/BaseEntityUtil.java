package com.czyl.common.utils.domain;

import com.czyl.common.utils.DateUtils;
import com.czyl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 提供对 BaseEntity 的公共字段 自动刷新 自动填充 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/9 0009 11:05
 * @project
 * @Version
 * @see com.czyl.framework.web.domain.BaseEntity
 */
public abstract class BaseEntityUtil {
    /**
     * 根据当前请求信息 填充比如 更新人 更新时间这种字段(新增时候确定的字段不处理)
     *
     * @param vo
     * @param userId 登录用户id
     * @param orgId  登录组织id 一般是用户所属的
     */
    public static void fill(BaseEntity vo, Long userId, Long orgId) {
        if (vo == null) {
            return ;
        }

        Date nowDate = DateUtils.getNowDate();
        if (vo.getUpdateTime() == null) {
            vo.setUpdateTime(nowDate);
        }

        if (vo.getUpdateBy() == null) {
            vo.setUpdateBy(userId);
        }
    }

    /**
     * 根据当前请求信息 填充比如 创建人 组织id 更新人 更新时间这种字段
     *
     * @param vo
     * @param userName 用户名字 昵称
     * @param userId   登录用户id
     * @param orgId    登录组织id 一般是用户所属的
     */
    public static void fillForce(BaseEntity vo, String userName, Long userId, Long orgId) {
        if (vo == null) {
            return ;
        }

        Date nowDate = DateUtils.getNowDate();
        if (vo.getCreateTime() == null) {
            vo.setUpdateTime(nowDate);
        }
        if (vo.getCreateByName() == null) {
            vo.setCreateByName(userName);
        }
        if (vo.getCreateBy() == null) {
            vo.setCreateBy(userId);
        }
        if (vo.getOrgId() == null) {
            vo.setOrgId(orgId);
        }
        if (vo.getVersion() == null) {
            vo.setVersion(0L);
        }

        fill(vo, userId, orgId);
    }
}
