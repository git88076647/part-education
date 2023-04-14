package com.czyl.common.utils;

import com.czyl.common.exception.CustomException;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 对象工具类，提供复制 或者 注解值获取等<br/>
 * <br/>
 * <br/>
 *
 * @author air tangsu Email:209308343@qq.com
 * @date 2020/3/11 0011 13:43
 * @project
 * @Version
 */
public abstract class ObjectUtil {
	private static final String PATTERN_DATE = "yyyy-MM-dd";
	private static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 返回一个 扔掉了 静态字段的 集合
	 *
	 * @param fs
	 * @return
	 */
	public static List<Field> dropStatic(List<Field> fs) {
		if (fs == null) {
			return Collections.emptyList();
		}

		ArrayList<Field> re = new ArrayList<>(fs.size());
		for (Field f : fs) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}

			re.add(f);
		}

		return re;
	}

	/**
	 * 返回一个 扔掉了 final字段的 集合
	 *
	 * @param fs
	 * @return
	 */
	public static List<Field> dropFinal(List<Field> fs) {
		if (fs == null) {
			return Collections.emptyList();
		}

		ArrayList<Field> re = new ArrayList<>(fs.size());
		for (Field f : fs) {
			if (Modifier.isFinal(f.getModifiers())) {
				continue;
			}

			re.add(f);
		}

		return re;
	}

	/**
	 * 返回一个 扔掉了 final并且static 字段的 集合
	 *
	 * @param fs
	 * @return
	 */
	public static List<Field> dropFinalAndStatic(List<Field> fs) {
		if (fs == null) {
			return Collections.emptyList();
		}

		ArrayList<Field> re = new ArrayList<>(fs.size());
		for (Field f : fs) {
			if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
				continue;
			}

			re.add(f);
		}

		return re;
	}

	/**
	 * 返回一个 扔掉了 final或static字段的 集合
	 *
	 * @param fs
	 * @return
	 */
	public static List<Field> dropStaticFinal(List<Field> fs) {
		return dropStatic(dropFinal(fs));
	}

	/**
	 * 获得这个类所有的 定义的字段，包括父类 接口等
	 *
	 * @param c
	 * @return
	 */
	public static List<Field> getAllFields(Class c) {
		if (c == null) {
			return Collections.emptyList();
		}
		LinkedList<Field> fs = new LinkedList<>();
		Class nc = c;
		Field[] declaredFields;
		Class[] interfaces;
		for (; nc != null && nc != Object.class; nc = nc.getSuperclass()) {
			declaredFields = nc.getDeclaredFields();
			if (declaredFields != null) {
				for (Field declaredField : declaredFields) {
					fs.add(declaredField);
				}
			}

			interfaces = nc.getInterfaces();
			if (interfaces == null || interfaces.length < 1) {
				continue;
			}

			for (Class anInterface : interfaces) {
				for (Class nnc = anInterface; nnc != null && nnc != Object.class; nnc = nnc.getSuperclass()) {
					declaredFields = nnc.getDeclaredFields();
					if (declaredFields != null) {
						for (Field declaredField : declaredFields) {
							fs.add(declaredField);
						}
					}
				}
			}
		}

		return fs;
	}

	/**
	 * 获得这个类所有的 定义的字段，包括父类， 不包括接口
	 *
	 * @param c
	 * @return
	 */
	public static List<Field> getAllClassFields(Class c) {
		if (c == null) {
			return Collections.emptyList();
		}
		LinkedList<Field> fs = new LinkedList<>();
		Class nc = c;
		Field[] declaredFields;
		for (; nc != null && nc != Object.class; nc = nc.getSuperclass()) {
			declaredFields = nc.getDeclaredFields();
			if (declaredFields != null) {
				for (Field declaredField : declaredFields) {
					fs.add(declaredField);
				}
			}
		}

		return fs;
	}

	/**
	 * 获取 类的字段（本类自己定义的！）
	 *
	 * @param c         class
	 * @param fieldName 字段名 大小写敏感
	 * @return
	 * @throws NoSuchFieldException
	 */
	@Nullable
	public static Field getClassField(@Nullable Class c, String fieldName) throws NoSuchFieldException {
		if (null == c) {
			return null;
		}

		return c.getDeclaredField(fieldName);
	}

	/**
	 * 获取 类的字段（包含父类 接口等！）
	 *
	 * @param c         class
	 * @param fieldName 字段名 大小写敏感
	 * @return
	 * @throws NoSuchFieldException
	 */
	@Nullable
	public static Field getClassFieldByAll(@Nullable Class c, String fieldName) throws NoSuchFieldException {
		if (null == c) {
			return null;
		}

		List<Field> allFields = getAllFields(c);

		for (Field f : allFields) {
			if (fieldName.equals(f.getName())) {
				return f;
			}
		}

		return null;
	}

	/**
	 * 获取 entity VO的 @Id 标识的主键字段的值
	 *
	 * @param vo
	 * @return
	 * @see org.springframework.data.annotation.Id
	 */
	@Nullable
	public static Object getEntityVOIdValue(@Nullable Object vo) {
		if (null == vo) {
			return null;
		}

		Field idFiled = getFirstMatchAnnotationField(vo.getClass(), Id.class);
		if (null == idFiled) {
			return null;
		}
		return getFiledValue(vo, idFiled);
	}

	/**
	 * 搜索一个 类的定义类里 所有定义的字段(对象类自己的) 注解了指定注解的字段列表
	 *
	 * @param clz             类
	 * @param annotationClass 注解类
	 * @return
	 */
	@NonNull
	public static List<Field> getMatchAnnotationFields(@Nullable Class<?> clz, Class<? extends Annotation> annotationClass) {
		return getMatchAnnotationFields(clz, Collections.singletonList(annotationClass));
	}

	/**
	 * 搜索一个 类的定义类里 所有定义的字段(对象类自己的) 注解了任何指定注解的字段列表
	 *
	 * @param clz            类
	 * @param annotationList 注解类们
	 * @return
	 */
	@NonNull
	public static List<Field> getMatchAnnotationFields(@Nullable Class<?> clz, List<Class<? extends Annotation>> annotationList) {
		final LinkedList<Field> matchList = new LinkedList<>();
		if (null == clz) {
			return matchList;
		}

		List<Field> allClassFields = getAllClassFields(clz);
		for (Field dbField : allClassFields) {
			if (annotationList.stream().anyMatch(dbField::isAnnotationPresent)) {
				matchList.add(dbField);
			}
		}

		return matchList;
	}

	/**
	 * 搜索一个 类的定义类里 所有定义的字段(对象类自己的) 没有注解指定注解的字段列表
	 *
	 * @param clz           类
	 * @param annotationClz 注解类
	 * @return
	 */
	@NonNull
	public static List<Field> getNoMatchAnnotationFields(@Nullable Class<?> clz, Class<? extends Annotation> annotationClz) {
		return getNoMatchAnnotationFields(clz, Collections.singletonList(annotationClz));
	}

	/**
	 * 搜索一个 类的定义类里 所有定义的字段(对象类自己的) 没有注解任何一个指定注解的字段列表
	 *
	 * @param clz            类
	 * @param annotationList 注解类们
	 * @return
	 */
	@NonNull
	public static List<Field> getNoMatchAnnotationFields(@Nullable Class<?> clz, List<Class<? extends Annotation>> annotationList) {
		final LinkedList<Field> matchList = new LinkedList<>();
		if (null == clz) {
			return matchList;
		}

		List<Field> allClassFields = getAllClassFields(clz);
		for (Field dbField : allClassFields) {
			if (annotationList.stream().noneMatch(dbField::isAnnotationPresent)) {
				matchList.add(dbField);
			}
		}

		return matchList;
	}

	/**
	 * 搜索一个 类的定义类里 所有定义的字段(对象类自己的) 注解了指定注解的字段第一个符合的
	 *
	 * @param clz             类
	 * @param annotationClass 注解类
	 * @return
	 */
	@Nullable
	public static Field getFirstMatchAnnotationField(@Nullable Class<?> clz, Class<? extends Annotation> annotationClass) {
		if (null == clz) {
			return null;
		}

		List<Field> allClassFields = getAllClassFields(clz);
		for (Field field : allClassFields) {
			if (field.isAnnotationPresent(annotationClass)) {
				return field;
			}
		}

		return null;
	}

	/**
	 * 获取对象里所有 非静态字段值不是null的(包含非public字段)
	 *
	 * @param obj 对象
	 * @return
	 */
	@NonNull
	public static List<Field> getValueNotNullFields(@Nullable Object obj) {
		LinkedList<Field> matchList = new LinkedList<>();
		if (null == obj) {
			return matchList;
		}

		List<Field> declaredFields = getAllClassFields(obj.getClass());
		if (CollectionUtil.isEmpty(declaredFields)) {
			return matchList;
		}

		try {
			for (Field field : declaredFields) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}

				if (null != field.get(obj)) {
					matchList.add(field);
				}
			}
		} catch (IllegalAccessException e) {
			// 基本不可能出错，所以这里不关注是否错误抛出问题
			return matchList;
		}

		return matchList;
	}

	/**
	 * 获取对象里所有 非静态字段值不是null的(包含非public字段) 且字段注解了 自定注解的
	 *
	 * @param obj             对象
	 * @param annotationClass 注解
	 * @return
	 */
	@NonNull
	public static List<Field> getValueNotNullFieldsWithAnnotation(@Nullable Object obj, Class<? extends Annotation> annotationClass) {
		return getValueNotNullFieldsWithAnnotation(obj, Collections.singletonList(annotationClass));
	}

	/**
	 * 获取对象里所有 非静态字段值不是null的(包含非public字段) 且字段注解了任何一个指定注解的
	 *
	 * @param obj            对象
	 * @param annotationList 注解们
	 * @return
	 */
	@NonNull
	public static List<Field> getValueNotNullFieldsWithAnnotation(@Nullable Object obj, List<Class<? extends Annotation>> annotationList) {
		LinkedList<Field> matchList = new LinkedList<>();

		List<Field> nonullFields = getValueNotNullFields(obj);

		if (nonullFields.isEmpty()) {
			return matchList;
		}

		for (Field field : nonullFields) {
			if (annotationList.stream().anyMatch(field::isAnnotationPresent)) {
				matchList.add(field);
			}
		}

		return matchList;
	}

	/**
	 * 获取对象里所有 非静态字段值不是null的(包含非public字段) 且字段没有注解指定注解的
	 *
	 * @param obj             对象
	 * @param annotationClass 注解
	 * @return
	 */
	@NonNull
	public static List<Field> getValueNotNullFieldsWithNoAnnotation(@Nullable Object obj, Class<? extends Annotation> annotationClass) {
		return getValueNotNullFieldsWithNoAnnotation(obj, Collections.singletonList(annotationClass));
	}

	/**
	 * 获取对象里所有 非静态字段值不是null的(包含非public字段) 且字段没有注解任何一个指定注解的
	 *
	 * @param obj            对象
	 * @param annotationList 注解们
	 * @return
	 */
	@NonNull
	public static List<Field> getValueNotNullFieldsWithNoAnnotation(@Nullable Object obj, List<Class<? extends Annotation>> annotationList) {
		LinkedList<Field> matchList = new LinkedList<>();

		List<Field> nonullFields = getValueNotNullFields(obj);

		if (nonullFields.isEmpty()) {
			return matchList;
		}

		for (Field field : nonullFields) {
			if (annotationList.stream().anyMatch(field::isAnnotationPresent)) {
				continue;
			}

			matchList.add(field);
		}

		return matchList;
	}

	/**
	 * 把一个对象里 他自身定义的字段（非静态） String类型的 如果是 ''空字符串 设置成null
	 *
	 * @param vo   对象
	 * @param trim 空字符串是否匹配trim后
	 */
	public static void setStringFiledNullIfEmpty(Object vo, boolean trim) {
		if (vo == null) {
			return;
		}

		List<Field> declaredFields = getAllClassFields(vo.getClass());
		if (CollectionUtil.isEmpty(declaredFields)) {
			return;
		}

		String value;
		for (Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			if (field.getType() != String.class) {
				continue;
			}

			value = (String) getFiledValue(vo, field);
			if (null == value) {
				continue;
			}

			if (trim) {
				value = value.trim();
			}

			if (value.isEmpty()) {
				setFiledValue(vo, field, null);
			}
		}
	}

	/**
	 * 获取一个对象字段的值，不抛出异常 发生异常返回null
	 *
	 * @param vo    对象
	 * @param field 字段
	 * @return
	 */
	public static Object getFiledValue(Object vo, Field field) {
		if (null == vo) {
			return null;
		}
		if (null == field) {
			return null;
		}
		field.setAccessible(true);
		try {
			return field.get(vo);
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	/**
	 * 设置一个对象字段的值，不抛出异常, 会尝试自动转型 <><br/>
	 * 除非业务可以容忍设置失败，否则请不要调用此方法 而是调用会报异常的另一个方法 setObjFiledValueAuto
	 *
	 * @param vo    对象
	 * @param field 字段
	 * @param value 要设置的值
	 * @return
	 */
	public static void setFiledValue(Object vo, Field field, Object value) {
		if (null == vo) {
			return;
		}
		if (null == field) {
			return;
		}

		setObjFiledValueAuto(vo, field.getName(), value);
	}

	/**
	 * 根据字段名 获得 类里的字段
	 *
	 * @param voClz
	 * @param classFieldName
	 * @return 如果没有返回null
	 */
	public static Field getClassFieldByName(Class voClz, String classFieldName) {
		try {
			return voClz.getField(classFieldName);
		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	/**
	 * 设置一个bean的字段的值 ,自动 判断类型 <br/>
	 * <br/>
	 *
	 * @param name
	 * @param value
	 * @throws CustomException
	 * @author air Email:209308343@qq.com
	 * @date 2019年12月6日08:40:57
	 */
	public static void setObjFiledValueAuto(Object vo, String name, Object value) throws CustomException {
		if (vo == null) {
			return;
		}

		if (StringUtils.isEmpty(name)) {
			return;
		}

		Object v = null;
		try {
			Class<?> c = vo.getClass();

			Method[] ms = c.getMethods();
			Method m = null;
			for (Method method : ms) {
				if (method.getName().equalsIgnoreCase("set" + name)) {
					m = method;
				}
			}
			Class<?> type;
			Field field = null;
			if (null == m) {
				field = c.getField(name);
				field.setAccessible(true);
				type = field.getType();
			} else {
				type = m.getParameterTypes()[0];
			}
			// 自动转换值的类型
			if (null == value || value.getClass() == type) {
				// 如果是null或者类型一致，直接设置
				if (m == null) {
					field.set(vo, value);
				} else {
					m.invoke(vo, value);
				}

				return;
			}

			String valueStr = value.toString();
			if (type.equals(String.class)) {
				v = valueStr;
			} else if (type.equals(Date.class)) {
				v = new SimpleDateFormat(PATTERN_DATE_TIME).parse(valueStr);
			} else if (type.equals(LocalDate.class)) {
				v = LocalDate.parse(valueStr, DateTimeFormatter.ofPattern(PATTERN_DATE));
			} else if (type.equals(LocalDateTime.class)) {
				v = LocalDateTime.parse(valueStr, DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
			} else if (type.equals(BigDecimal.class)) {
				v = new BigDecimal(valueStr);
			} else if (type.equals(Integer.class) || type.equals(int.class)) {
				v = Integer.valueOf(valueStr);
			} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
				v = "Y".equals(valueStr) || "true".equals(valueStr) ? Boolean.TRUE : Boolean.FALSE;
			} else if (type.equals(Double.class) || type.equals(double.class)) {
				v = Double.valueOf(valueStr);
			} else if (type.equals(char.class)) {
				v = valueStr.charAt(0);
			} else if (type.equals(Byte.class) || type.equals(byte.class)) {
				v = Byte.valueOf(valueStr);
			} else if (type.equals(Float.class) || type.equals(float.class)) {
				v = Float.valueOf(valueStr);
			}

			if (v == null) {
				// 不支持的 类型翻译
				throw new CustomException("不支持的类型翻译!");
			}

			if (m == null) {
				field.set(vo, v);
			} else {
				m.invoke(vo, v);
			}
		} catch (Exception e) {
			String err = "Class=" + vo.getClass().getName() + ",属性名=" + name + ",不能赋值为:" + "值Class=" + (v == null ? "null" : v.getClass().getName()) + ",值内容=" + v + ",ERROR=" + e.toString();
			throw new CustomException(err);
		}
	}

}
