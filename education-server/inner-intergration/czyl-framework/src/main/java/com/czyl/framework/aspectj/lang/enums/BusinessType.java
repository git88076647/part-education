package com.czyl.framework.aspectj.lang.enums;

/**
 * <pre>
 * 业务操作类型
 * 
 * <h1>如果要新增 枚举，不可修改枚举的顺序</h1>
 * </pre>
 * @author tanghx
 */
public enum BusinessType
{
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,
    
    /**
     * 清空数据
     */
    CLEAN,
    
    /**
     * 文件上传
     */
    UPLOAD,
    
    /**
     * 文件下载
     */
    DOWNLOAD,
    /**
     * 上线
     */
    UPLINE,
    /**
     * 下线
     */
    DOWNLINE,
    
    /**
     * 启动
     */
    STARTUP,
    /**
     * 停机
     */
    SHUTDOWN,
    /**
     * 重启
     */
    RELOAD,
    /**
     * 重新注册
     */
    REFRESH,
    /**
     * 执行
     */
    RUN,
    /**
     * 冻结
     */
    FROZEN,
    /**
     * 解冻
     */
    THAW
}
