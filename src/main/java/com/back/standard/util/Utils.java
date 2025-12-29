package com.back.standard.util;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
public class Utils {

    public static class reflection {
        public static void setField(Object obj, String fieldName, Object value) {
            try {
                var field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
