package com.czyl.framework;

import com.czyl.common.constant.Constants;
import com.czyl.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hhhcccggg
 * @Date 2022/2/17 11:18
 * @Description 使用工厂模式根据规则生成SQLGenerator，后续如果规则复杂方便扩展
 **/
@Slf4j
@Component
public class SQLGeneratorFactory {

    private static final Map<Integer, SQLGenerator> GENERATOR = new HashMap<>();


    public static SQLGenerator getInstance(Integer operationType) {
        return GENERATOR.get(operationType);
    }


    @PostConstruct
    private void init() {
        //只支持根据主键id进行数据权限过滤
        log.info("------规则工厂初始化开始------");
        GENERATOR.put(Constants.RuleType.大于.getValue(),
                (permissionData) -> {
                    //当规则为大于小于等于时 permissionData 只能有一条
                    if (permissionData.split(",").length > 1) {
                        throw new CustomException("权限数据与规则不匹配");
                    }
                    if ("0".equals(permissionData)) {
                        return "";
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" > ");
                    stringBuilder.append(permissionData);
                    stringBuilder.append(" ");
                    return stringBuilder.toString();
                });
        GENERATOR.put(Constants.RuleType.小于.getValue(),
                (permissionData) -> {
                    if (permissionData.split(",").length > 1) {
                        throw new CustomException("权限数据与规则不匹配");
                    }
                    if ("0".equals(permissionData)) {
                        return "";
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" < ");
                    stringBuilder.append(permissionData);
                    stringBuilder.append(" ");
                    return stringBuilder.toString();
                });
        GENERATOR.put(Constants.RuleType.等于.getValue(),
                (permissionData) -> {
                    if (permissionData.split(",").length > 1) {
                        throw new CustomException("权限数据与规则不匹配");
                    }
                    if ("0".equals(permissionData)) {
                        return "";
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" = ");
                    stringBuilder.append(permissionData);
                    stringBuilder.append(" ");
                    return stringBuilder.toString();
                });
        GENERATOR.put(Constants.RuleType.包含.getValue(),
                (permissionData) -> {
                    if ("0".equals(permissionData)) {
                        return "";
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" in ( ");
                    stringBuilder.append(permissionData);
                    stringBuilder.append(" ) ");
                    return stringBuilder.toString();
                });
        GENERATOR.put(Constants.RuleType.无权限.getValue(),
                (permissionData) -> " = -1 ");

        GENERATOR.put(Constants.RuleType.全权限.getValue(),
                (permissionData) -> "");

        log.info("------规则工厂初始化结束------");
    }


}
