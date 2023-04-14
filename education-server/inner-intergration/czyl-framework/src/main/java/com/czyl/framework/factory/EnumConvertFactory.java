package com.czyl.framework.factory;

import com.czyl.common.utils.StringUtils;
import com.czyl.framework.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class EnumConvertFactory implements ConverterFactory<String, IEnum> {
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToIEum<>(targetType);
    }

    @SuppressWarnings("all")
    private static class StringToIEum<T extends IEnum> implements Converter<String, T> {
        private Class<T> targerType;

        public StringToIEum(Class<T> targerType) {
            this.targerType = targerType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            return (T) EnumConvertFactory.getIEnum(this.targerType, source);
        }
    }

    public static <T extends IEnum> Object getIEnum(Class<T> targerType, String source) {
        for (T enumObj : targerType.getEnumConstants()) {
            if (source.equals(String.valueOf(enumObj.getId()))) {
                return enumObj;
            }
        }
        return null;
    }

}
