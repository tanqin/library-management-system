package com.bjpowernode.util;


import java.lang.reflect.Field;

public class BeanUtil {
    /**
     * 对象属性值的拷贝
     *
     * @param origin
     * @param dest
     */
    public static void populate(Object origin, Object dest) {
        try {
            // 判断两个对象是否属于同一类型
            if (origin.getClass() != dest.getClass()) {
                throw new RuntimeException("两个对象不属于同一类型");
            }
            Class<?> clazz = origin.getClass();
            // 获取 clazz 类中所有成员变量 Field 对象组数
            Field[] declaredFields = clazz.getDeclaredFields();

            // 遍历
            for (Field f : declaredFields) {
                if ("serialVersionUID".equals(f.getName())) {
                    continue;
                }
                // 打破封锁
                f.setAccessible(true);
                // 从 dest 中找到对应属性值，并赋值到 origin 中的对应属性中
                f.set(origin, f.get(dest));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
