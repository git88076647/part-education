package com.czyl.framework.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.math.BigDecimal;

@JacksonStdImpl
public class LongZeroToNullSerializer extends JsonSerializer implements ContextualSerializer {
    private Class<?> type;

    public LongZeroToNullSerializer() {
    }

    public LongZeroToNullSerializer(final JavaType type) {
        this.type = type == null ? Object.class : type.getRawClass();
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value instanceof Short) {
            if (((Short) value).compareTo((short) 0) == 0) {
                gen.writeNull();
            } else {
                gen.writeNumber(((Short) value).shortValue());
            }
        }
        if (value instanceof Integer) {
            if (((Integer) value).intValue() == 0) {
                gen.writeNull();
            } else {
                gen.writeNumber(((Integer) value).intValue());
            }
        }
        if (value instanceof Float) {
            if (((Float) value).compareTo(0f) == 0) {
                gen.writeNull();
            } else {
                gen.writeNumber(((Float) value).floatValue());
            }
        }

        if (value instanceof Double) {
            if (((Double) value).compareTo(0D) == 0) {
                gen.writeNull();
            } else {
                gen.writeNumber(((Double) value).doubleValue());
            }
        }

        if (value instanceof Long) {
            if (((Long) value).compareTo(0L) == 0) {
                gen.writeNull();
            } else {
                gen.writeString(value.toString());
            }
        }
        if (value instanceof BigDecimal) {
            if (((BigDecimal) value).compareTo(BigDecimal.ZERO) == 0) {
                gen.writeNull();
            } else {
                gen.writeNumber((BigDecimal) value);
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return new LongZeroToNullSerializer(property.getType());
    }
}
 