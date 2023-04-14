package com.czyl.project.tool.gen.util;

import com.alibaba.fastjson.JSONObject;
import com.czyl.common.constant.GenConstants;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.uid.impl.DefaultUidGenerator;
import com.czyl.project.tool.gen.domain.GenTable;
import com.czyl.project.tool.gen.domain.GenTableColumn;
import com.czyl.project.tool.gen.domain.GenTableTpl;
import org.apache.velocity.VelocityContext;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 模版引擎工具
 *
 * @author tanghx
 */
public class VelocityUtils {
    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mybatis";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String packageName = genTable.getPackageName();
        String tplCategory = genTable.getTplCategory();
        String functionName = genTable.getFunctionName();
        DefaultUidGenerator uid = SpringUtils.getBean(DefaultUidGenerator.class);
        genTable.getColumns().parallelStream().forEach(o -> {
            //设置为参照类型
            if (GenConstants.TYPE_LONG.equals(o.getJavaType()) && !o.isPk() && !"version".equals(o.getJavaField()) && !o.isSuperColumn()) {
                o.setRefField(true);
            }
        });
        //单据模板编码
        AtomicInteger index = new AtomicInteger(0);
        List<GenTableTpl> billtpl = genTable.getColumns().stream().map(col -> {
            GenTableTpl bt = new GenTableTpl();
            bt.setId(uid.getUID());
            bt.setSortindex(index.addAndGet(5));
            if (GenConstants.TYPE_DATE.equals(col.getJavaType())) {
                bt.setType("date");
            } else if (GenConstants.HTML_DATETIME.equals(col.getJavaType())) {
                bt.setType("datetime");
            } else if (GenConstants.HTML_NUMBER.equals(col.getJavaType())) {
                bt.setType("number4");
            } else {
                bt.setType("text");
            }
            bt.setAlign("center");
            bt.setBilltplcode("tpl_" + genTable.getTableName().toLowerCase());
            bt.setLabel(col.getColumnComment());
            bt.setProp(col.getJavaField());
            bt.setShowCard(col.isEdit() ? 1 : 0);
            bt.setShowList(col.isList() ? 1 : 0);
            if (StringUtils.isNotBlank(col.getDictType())) {
                bt.setFormatter(col.getJavaField() + "Format");
            } else {
                bt.setFormatter("");
            }
            return bt;
        }).collect(Collectors.toList());
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("BusinessName", StringUtils.capitalize(genTable.getBusinessName()));
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable.getColumns()));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("billtpl", billtpl);
        velocityContext.put("table", genTable);

        velocityContext.put("menuIdPid", uid.getUID());
        velocityContext.put("menuId1", uid.getUID());
        velocityContext.put("menuId2", uid.getUID());
        velocityContext.put("menuId3", uid.getUID());
        velocityContext.put("menuId4", uid.getUID());
        velocityContext.put("menuId5", uid.getUID());
        velocityContext.put("gateway", genTable.getGateway());
        velocityContext.put("hasDr", false);
        velocityContext.put("hasCreate", false);
        velocityContext.put("hasUpdate", false);
        for (GenTableColumn column : genTable.getColumns()) {
            if ("dr".equalsIgnoreCase(column.getColumnName())) {
                velocityContext.put("hasDr", true);
            } else if ("create_by".equalsIgnoreCase(column.getColumnName())) {
                velocityContext.put("hasCreateBy", true);
            } else if ("update_by".equalsIgnoreCase(column.getColumnName())) {
                velocityContext.put("hasUpdateBy", true);
            }
        }
        if (GenConstants.TPL_TREE.equals(tplCategory)) {
            setTreeVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
        }
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        templates.add("vm/sql/sql.vm");
        templates.add("vm/sql/tpl.vm");
        templates.add("vm/js/api.js.vm");
        templates.add("vm/vue/index.vue.vm");
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTable genTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm")) {
            fileName = MessageFormat.format("{0}/domain/{1}.java", javaPath, className);
        } else if (template.contains("mapper.java.vm")) {
            fileName = MessageFormat.format("{0}/mapper/{1}Mapper.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = MessageFormat.format("{0}/service/I{1}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = MessageFormat.format("{0}/service/impl/{1}ServiceImpl.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = MessageFormat.format("{0}/controller/{1}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = MessageFormat.format("{0}/{1}Mapper.xml", mybatisPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = businessName + "Menu.sql";
        } else if (template.contains("tpl.vm")) {
            fileName = businessName + "billtpl.sql";
        } else if (template.contains("js.vm")) {
            fileName = MessageFormat.format("{0}/api/{1}/{2}.js", vuePath, moduleName, businessName);
        } else if (template.contains("vue.vm")) {
            fileName = MessageFormat.format("{0}/views/{1}/{2}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        String basePackage = StringUtils.substring(packageName, 0, lastIndex);
        return basePackage;
    }

    /**
     * 根据列类型获取导入包
     *
     * @param column 列集合
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(List<GenTableColumn> columns) {
        HashSet<String> importList = new HashSet<String>();
        for (GenTableColumn column : columns) {
            if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())) {
                importList.add("java.util.Date");
            } else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName) {
        return MessageFormat.format("{0}:{1}", moduleName, businessName);

    }

    /**
     * 获取树编码
     *
     * @param options 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
            return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_CODE));
        }
        return "";
    }

    /**
     * 获取树父编码
     *
     * @param options 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        return "";
    }

    /**
     * 获取树名称
     *
     * @param options 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_NAME));
        }
        return "";
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeName = paramsObj.getString(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isList()) {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName)) {
                    break;
                }
            }
        }
        return num;
    }
}