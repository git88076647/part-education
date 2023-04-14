package com.czyl.framework.aspectj;

import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.aspectj.lang.annotation.DataScope;
import com.czyl.framework.security.LoginUser;
import com.czyl.framework.security.service.TokenService;
import com.czyl.framework.web.domain.BaseEntity;
import com.czyl.project.system.domain.SysRole;
import com.czyl.project.system.domain.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据过滤处理
 * 
 * @author tanghx
 */
@Aspect
@Component
public class DataScopeAspect {
	/**
	 * 全部部门数据权限
	 */
	public static final String DATA_SCOPE_DEPT_ALL = "1";

	/**
	 * 自定部门数据权限
	 */
	public static final String DATA_SCOPE_CUSTOM = "2";

	/**
	 * 部门数据权限
	 */
	public static final String DATA_SCOPE_DEPT = "3";

	/**
	 * 部门及以下数据权限
	 */
	public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

	/**
	 * 仅本人数据权限
	 */
	public static final String DATA_SCOPE_SELF = "5";

	/**
	 * 主部门
	 */
	public static final String DATA_SCOPE_DEPT_MAIN = "6";
	
	/** 配置织入点*/
	@Pointcut("@annotation(com.czyl.framework.aspectj.lang.annotation.DataScope)")
	public void dataScopePointCut() {
	}

	@Before("dataScopePointCut()")
	public void doBefore(JoinPoint point) throws Throwable {
		handleDataScope(point);
	}

	protected void handleDataScope(final JoinPoint joinPoint) {
		// 获得注解
		DataScope controllerDataScope = getAnnotationLog(joinPoint);
		if (controllerDataScope == null) {
			return;
		}
		// 获取当前的用户
		LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
		SysUser currentUser = loginUser.getUser();
		if (currentUser != null) {
			// 如果是超级管理员，则不过滤数据
			if (!currentUser.isAdmin()) {
				dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias(), controllerDataScope.orgAlias());
			}
		}
	}

	/**
	 * 数据范围过滤
	 * @param joinPoint 切入点
	 * @param user 当前登录用户
	 * @param deptAlias 部门别名
	 * @param userAlias 用户别名
	 * @param orgAlias 组织别名
	 */
	public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias, String orgAlias) {
		StringBuilder sqlString = new StringBuilder();

		// 部门权限
		Set<String> dataScopeSet = new HashSet<String>();
		for (SysRole role : user.getRoles()) {
			dataScopeSet.add(role.getDataScope());
			if (DATA_SCOPE_CUSTOM.equals(role.getDataScope()) && StringUtils.isNotBlank(deptAlias)) {
				sqlString.append(MessageFormat.format(" or {0}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {1} ) ", deptAlias, role.getRoleId().toString()));
			}
		}
		if (dataScopeSet.contains(DATA_SCOPE_DEPT_ALL)) {
			sqlString = new StringBuilder();
		} else {
			if( user.getPsnId() != null && user.getPsnId() > 0 && StringUtils.isNotBlank(deptAlias)) {
				if (dataScopeSet.contains(DATA_SCOPE_DEPT)) {
					// TODO 主部门及辅部门 数据权限过滤
					sqlString.append(MessageFormat.format(" or exists (select 1 from sys_psndoc p where p.psn_id={0} and (p.dept_id={1}.dept_id or p.dept_id1={2}.dept_id or p.dept_id2={3}.dept_id or p.dept_id3={4}.dept_id))",user.getPsnId().toString(),deptAlias,deptAlias,deptAlias,deptAlias));
				}
				if (dataScopeSet.contains(DATA_SCOPE_DEPT_AND_CHILD)) {
					// TODO 主部门及子部门数据权限过滤
					sqlString.append(MessageFormat.format(" or exists (select 1 from sys_psndoc p where p.psn_id={0} and FIND_IN_SET({1}.dept_id,queryDeptChildList(p.dept_id)) > 0 )",user.getPsnId().toString(),deptAlias));
				}
				if(dataScopeSet.contains(DATA_SCOPE_DEPT_MAIN) && !dataScopeSet.contains(DATA_SCOPE_DEPT_AND_CHILD) && !dataScopeSet.contains(DATA_SCOPE_DEPT)) {
					//主部门
					sqlString.append(MessageFormat.format(" or exists (select 1 from sys_psndoc p where p.psn_id={0} and p.dept_id={1}.dept_id)",user.getPsnId().toString(),deptAlias));
				}
			}
			// 仅制单人有权限
			if (dataScopeSet.contains(DATA_SCOPE_SELF)) {
				if (StringUtils.isNotBlank(userAlias)) {
					sqlString.append(MessageFormat.format(" or {0}.create_by = {1} ", userAlias, user.getUserId().toString()));
				}
			}
		}

		// 组织权限,
		if (StringUtils.isNotBlank(orgAlias)) {
			Long orgId = AppContextUtils.getOrgId();
			Long userId = AppContextUtils.getUserId();
			sqlString.append(MessageFormat.format(" or ({0}.org_id = {1} or exists (SELECT 1 FROM sys_user_role ur join sys_role_org ro on ro.role_id = ur.role_id where ur.user_id = {2} and ro.org_id= {3}.org_id )) ", orgAlias, orgId.toString(), userId.toString(), orgAlias));
		}

		if (StringUtils.isNotBlank(sqlString)) {
			BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[0];
			if (" or ".equals(sqlString.substring(0, 4))) {
				baseEntity.setDataScope(" and (" + sqlString.substring(4) + ")");
			} else {
				baseEntity.setDataScope(" and (" + sqlString + ")");
			}
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private DataScope getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(DataScope.class);
		}
		return null;
	}
}
