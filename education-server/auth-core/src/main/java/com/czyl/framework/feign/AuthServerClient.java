package com.czyl.framework.feign;

import com.czyl.common.constant.CacheNameConstants;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.login.LoginSuper;
import com.czyl.framework.feign.fallback.AuthServerClientFallbackFactory;
import com.czyl.project.integrate.domain.SysAppreg;
import com.czyl.project.system.domain.SysBilltplItem;
import com.czyl.project.system.domain.SysDictData;
import com.czyl.project.system.domain.SysUserPermission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * auth-server的Feign
 *
 * @author tanghx
 */
@FeignClient(value = "auth-server", fallback = AuthServerClientFallbackFactory.class)
public interface AuthServerClient {

    /**
     * 字典类型+字典值返回字典显示名称
     *
     * @param dictType
     * @param dictValue
     * @return
     */
    @Cacheable(cacheNames = CacheNameConstants.DICT_LABEL, key = "#p0+#p1")
    @RequestMapping(method = RequestMethod.GET, value = "/system/dict/data/dictLabel/{dictType}/{dictValue}", consumes = "application/json")
    public AjaxResult dictLabel(@PathVariable String dictType, @PathVariable String dictValue);

    /**
     * 字典类型+多个字典值返回字典显示名称
     *
     * @param dictType
     * @param dictValues
     * @return
     */
    @Cacheable(cacheNames = CacheNameConstants.DICT_LABEL, key = "#p0+#p1")
    @RequestMapping(method = RequestMethod.GET, value = "/system/dict/data/dictLabels/info", consumes = "application/json")
    public List<SysDictData> dictLabels(@RequestParam(value = "dictType") String dictType, @RequestParam(value = "dictValues") List<String> dictValues);

    /**
     * 应用
     *
     * @param code
     * @return
     */
    @LoginSuper
    @Cacheable(cacheNames = CacheNameConstants.APP_REG, key = "#p0")
    @RequestMapping(method = RequestMethod.GET, value = "/integrate/appreg/getAppReg/{code}", consumes = "application/json")
    public SysAppreg getAppReg(@PathVariable String code);


    /**
     * 单据模板
     *
     * @param billtplcode
     * @return
     */
    @LoginSuper
    @Cacheable(cacheNames = CacheNameConstants.BILL_ITEM, key = "#p0")
    @RequestMapping(method = RequestMethod.GET, value = "/system/billtpl/billitems", consumes = "application/json")
    public List<SysBilltplItem> getBillItems(@RequestParam(value = "billtplcode", required = false) String billtplcode);

    /**
     * 业务参数value
     *
     * @param key
     * @return
     */
    @LoginSuper
    @Cacheable(cacheNames = CacheNameConstants.BIZ_CONFIG, key = "#p0")
    @RequestMapping(method = RequestMethod.GET, value = "/system/bizconfig/value", consumes = "application/json")
    public String getBizConfigValue(@RequestParam(value = "key") String key);


    /**
     * 用户角色
     *
     * @param userId
     * @return
     */
    @LoginSuper
    //@Cacheable(cacheNames = CacheNameConstants.USER_ROLE,key= "#p0")
    @RequestMapping(method = RequestMethod.GET, value = "/system/user/permision/{userId}", consumes = "application/json")
    public List<String> getUserRole(@PathVariable Long userId);


    /**
     * 用户数据权限
     *
     * @param userId
     * @return
     */
    @LoginSuper
    //@Cacheable(cacheNames = CacheNameConstants.ROLE_PERMISION,key= "#p0")
    @RequestMapping(method = RequestMethod.GET, value = "/system/permission/getUserPermision", consumes = "application/json")
    public List<SysUserPermission> getUserPermision(@RequestParam(value = "userId") Long userId);

    /**
     * 角色数据权限
     *
     * @param entity
     * @return
     */
    @LoginSuper
    //@Cacheable(cacheNames = CacheNameConstants.ROLE_PERMISION,key= "#p0")
    @RequestMapping(method = RequestMethod.POST, value = "/system/permission/import")
    public AjaxResult importData(@RequestBody SysUserPermission entity);


    @LoginSuper
//    @Cacheable(cacheNames = CacheNameConstants.DATA_PERMISSION)
    @RequestMapping(method = RequestMethod.GET, value = "/system/dataPermission", consumes = "application/json")
    AjaxResult getDataPermission(@RequestParam("scopeCode") String scopeCode,
                                 @RequestParam("subjectType") String subjectType);


    @LoginSuper
    @GetMapping("/system/role/optionselect")
    AjaxResult getRole();

    @LoginSuper
    @GetMapping(value = "/system/dataPermission/getScopeResource", consumes = "application/json")
    AjaxResult getScopeResource();
}
