package net.helygamex.persistence.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Transcoder {

    // Remove dash to insert in the database
    public static String encode(String uuid) {
        uuid = uuid.replace("-","");
        return uuid;
    }

    // Put lower case and add dash for uuid
    public static String decode(String uuid) {
        // Regexp to format uuid
        uuid = uuid.toLowerCase();
        uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
        return uuid;
    }

    public static HashMap<String, Boolean> getHashMapPerm(Object permissions) {
        HashMap<String, Boolean> result = new HashMap<>();
        try {
            // Iterate class fields
            for (Field field : permissions.getClass().getDeclaredFields()) {
                //Check if annotations present
                if (field.isAnnotationPresent(Perm.class)) {
                    // Make the private field accessible
                    field.setAccessible(true);
                    // Add to HashMap with correct value
                    result.put(field.getAnnotation(Perm.class).value(), field.getBoolean(permissions));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Set the annotations values
    public static void setAnnotationValue(Object permissions, String key, Boolean value) {
        try {
            //Iterate class fields
            for (Field field : permissions.getClass().getDeclaredFields()) {
                // Make the private field accessible
                field.setAccessible(true);
                // Check if annotations present and equal the key
                if (field.isAnnotationPresent(Perm.class) && field.getAnnotation(Perm.class).value().equals(key)) {
                    field.setBoolean(permissions, value);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
