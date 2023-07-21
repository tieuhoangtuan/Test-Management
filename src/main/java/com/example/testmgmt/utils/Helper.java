package com.example.testmgmt.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static String getSiteURL(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    public static List<String> getFieldName(Class<?> entityClass) {

        List<String> filedNames = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            filedNames.add(field.getName());
        }
        return filedNames;
    }

    public static String getLocalDay() {
        LocalDate localDate = LocalDate.now();
        String dateString = localDate.toString();
        return dateString;
    }
}
