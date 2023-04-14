package com.czyl.framework.json;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.czyl.framework.plugin.DataContextHolder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@JacksonStdImpl
public class BaseEntitySerializer extends JsonSerializer implements ContextualSerializer {
    private Class<?> type;

    public BaseEntitySerializer() {
    }

    public static final BaseEntitySerializer instance = new BaseEntitySerializer();

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            List<String> columns = DataContextHolder.getDataColumn();
            Field[] fields = value.getClass().getDeclaredFields();
            if (CollUtil.isNotEmpty(columns)) {
                try {
                    for (String s : columns) {
                        if (ReflectUtil.hasField(value.getClass(), s)) {
                            BeanUtil.setFieldValue(value, s, null);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            gen.writeStartObject();
            for (Field field : fields) {
                Object fieldValue = ReflectUtil.getFieldValue(value, field.getName());
                if (fieldValue == null) {
                    gen.writeObjectField(field.getName(), null);
                } else {
                    JsonSerialize jsonSerializeClass = field.getAnnotation(JsonSerialize.class);
                    if (jsonSerializeClass != null && jsonSerializeClass.using() == LongZeroToNullSerializer.class
                            && fieldValue instanceof Long && ((Long) fieldValue).compareTo(0L) == 0) {
                        gen.writeObjectField(field.getName(), null);

                    } else {
                        gen.writeObjectField(field.getName(), fieldValue);
                    }
                }

            }
            gen.writeEndObject();
        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return new BaseEntitySerializer();
    }
}
