package com.bjpowernode.util;


import java.lang.reflect.Field;

public class BeanUtil {
    /**
     * ��������ֵ�Ŀ���
     *
     * @param origin
     * @param dest
     */
    public static void populate(Object origin, Object dest) {
        try {
            // �ж����������Ƿ�����ͬһ����
            if (origin.getClass() != dest.getClass()) {
                throw new RuntimeException("������������ͬһ����");
            }
            Class<?> clazz = origin.getClass();
            // ��ȡ clazz �������г�Ա���� Field ��������
            Field[] declaredFields = clazz.getDeclaredFields();

            // ����
            for (Field f : declaredFields) {
                if ("serialVersionUID".equals(f.getName())) {
                    continue;
                }
                // ���Ʒ���
                f.setAccessible(true);
                // �� dest ���ҵ���Ӧ����ֵ������ֵ�� origin �еĶ�Ӧ������
                f.set(origin, f.get(dest));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
