package com.czyl.framework.aspectj;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.czyl.common.constant.Constants;
import com.czyl.common.constant.HttpStatus;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataPermission;
import com.czyl.framework.feign.AuthServerClient;
import com.czyl.framework.plugin.DataContextHolder;
import com.czyl.framework.security.LoginUser;
import com.czyl.framework.web.domain.BaseEntity;
import com.czyl.project.system.domain.DataPermissionEntity;
import com.czyl.project.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据过滤处理
 *
 * @author hhhcccggg
 * 数据权限过滤大概流程如下:
 * 1.通过权限资源+作用域+授权主体生成权限资源
 * 2.切入@DataPermission注解标识的controller查询方法
 * 3.通过作用域+授权主体查询该controller所包含的数据权限
 * 4.查询标识的controller方法的查询结果实体对应的数据库表元数据信息
 * 5.遍历数据权限+元数据信息 根据对应关系 拼接sql
 */
@Aspect
@Component
@Slf4j
public class DataPermissionAspect {

    @Autowired
    private AuthServerClient authServerClient;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.czyl.framework.aspectj.lang.annotation.DataPermission)")
    public void dataScopePointCut() {
    }

    @Around("dataScopePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取当前的用户
        LoginUser loginUser = AppContextUtils.getLoginUser();
        Object[] args = point.getArgs();
        if (loginUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!loginUser.getUser().isAdmin()) {
                if (args.length < 0 || !(args[0] instanceof BaseEntity)) {
                    throw new CustomException("查询失败,查询参数有误");
                }
                dataScopeFilter(point, loginUser.getUser(), (BaseEntity) args[0]);
            }
        }
        //注意此处需要将返回值return，否则目标对象实际返回值会被置为null
        return point.proceed(args);
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切入点
     * @param user      当前登录用户
     */
    public void dataScopeFilter(ProceedingJoinPoint joinPoint, SysUser user, BaseEntity arg) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DataPermission annotation = method.getAnnotation(DataPermission.class);
        AjaxResult permissionResult = authServerClient.getDataPermission(annotation.scopeCode(), annotation.subjectType());
        if (permissionResult.getCode() != HttpStatus.SUCCESS) {
            log.error("查询auth服务:/system/metaData接口失败" + permissionResult.getMsg());
            throw new CustomException("查询失败");
        }
        AjaxResult scopeResource = authServerClient.getScopeResource();
        if (scopeResource.getCode() != HttpStatus.SUCCESS) {
            log.error("查询auth服务:/system/dataPermission/getScopeResource" + scopeResource.getMsg());
            throw new CustomException("查询失败");
        }
        JSONObject scopeResourceData = JSON.parseObject(JSON.toJSONString(scopeResource.getData()));
        List<Long> roleIds = Arrays.asList(user.getRoleIds());

        List<DataPermissionEntity> dataPermissionList = (List) ((List) permissionResult.getData())
                .stream()
                .map(o -> BeanUtil.mapToBean((Map<?, ?>) o, DataPermissionEntity.class, true))
                .filter(o -> Constants.DataPermissionSubject.ROLE.getValue().equalsIgnoreCase(((DataPermissionEntity) o).getSubjectType())
                        && roleIds.contains(((DataPermissionEntity) o).getSubjectId())).collect(Collectors.toList());

        if (CollUtil.isNotEmpty(dataPermissionList)) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> columns = new LinkedList();
            for (int n = 0; n < dataPermissionList.size(); n++) {
                DataPermissionEntity entity = dataPermissionList.get(n);

                //虚拟资源，控制字段的显隐
                if (entity.getVirtualResource()) {
                    columns.add(StringUtils.toCamelCase(entity.getColumnName()));

                } else {
                    JSONObject o = scopeResourceData.getJSONObject(entity.getScopeCode() + "@" + entity.getResourceCode() + "@" + entity.getColumnName());
                    String permissionSql = "";
                    if (o != null) {
                        permissionSql = o.getString("permissionSql");
                    }
                    if (Constants.RuleType.无权限.getValue().intValue() == entity.getRule().intValue()) {
                        if (StrUtil.isNotEmpty(permissionSql)) {
                            String replace = permissionSql.replace("#{sql}", "is null ");
                            stringBuilder.append(" ( ");
                            stringBuilder.append(replace);
                            stringBuilder.append(" ) ");
                            stringBuilder.append(" and ");

//                            if ("policy".equals(entity.getScopeCode())) {
//                                stringBuilder.append(" or ");
//                            } else {
//                                stringBuilder.append(" and ");
//                            }

                        } else {
                            stringBuilder.append(" ( ");
                            stringBuilder.append(StrUtil.isNotEmpty(annotation.tableAlias()) ? annotation.tableAlias() + "." : "");
                            stringBuilder.append(entity.getColumnName());
                            stringBuilder.append(" is  null ");
                            stringBuilder.append(" or ");
                            stringBuilder.append(StrUtil.isNotEmpty(annotation.tableAlias()) ? annotation.tableAlias() + "." : "");
                            stringBuilder.append(entity.getColumnName());
                            stringBuilder.append(" = 0 ");
                            stringBuilder.append(" ) ");
                            stringBuilder.append(" and ");

//                            if ("policy".equals(entity.getScopeCode())) {
//                                stringBuilder.append(" or ");
//                            } else {
//                                stringBuilder.append(" and ");
//                            }
                        }

                    } else if (Constants.RuleType.全权限.getValue().intValue() == entity.getRule().intValue()) {

                    } else {
                        if (StrUtil.isNotEmpty(permissionSql)) {
                            String replace = permissionSql.replace("#{sql}", "in ( " + entity.getPermissionData() + " ) ");
                            stringBuilder.append(" ( ");
                            stringBuilder.append(replace);
                            stringBuilder.append(" ) ");
                            stringBuilder.append(" and ");
//                            if ("policy".equals(entity.getScopeCode())) {
//                                stringBuilder.append(" or ");
//                            } else {
//                                stringBuilder.append(" and ");
//                            }
                        } else {
                            stringBuilder.append(" ( ");
                            stringBuilder.append(StrUtil.isNotEmpty(annotation.tableAlias()) ? annotation.tableAlias() + "." : "");
                            stringBuilder.append(entity.getColumnName());
                            stringBuilder.append(" ");
                            stringBuilder.append(entity.getRefSql());
                            stringBuilder.append(" ) ");
                            stringBuilder.append(" and ");

//                            if ("policy".equals(entity.getScopeCode())) {
//                                stringBuilder.append(" or ");
//                            } else {
//                                stringBuilder.append(" and ");
//                            }
                        }

                    }


                }

            }
            String sql = stringBuilder.toString();
            if (StrUtil.isNotEmpty(sql)) {
                if (sql.endsWith(" or ")) {
                    sql = sql.substring(0, sql.lastIndexOf("or"));
                } else if (sql.endsWith(" and ")) {
                    sql = sql.substring(0, sql.lastIndexOf("and"));
                }
                BeanUtil.setFieldValue(arg, "scope", sql);
                DataContextHolder.setDataColumn(columns);
            }

        }

    }
}
