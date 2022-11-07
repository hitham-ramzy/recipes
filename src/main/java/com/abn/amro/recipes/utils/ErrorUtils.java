package com.abn.amro.recipes.utils;

public class ErrorUtils {

    public static final String NAME_FIELD_LENGTH = "Name field length should be between 1 and 100";
    public static final String NAME_FIELD_NOT_NULL = "Name field shouldn't be null";

    public static void generateError(ErrorConstant constant) {
        throw new RuntimeException(constant.getMessage());
    }

}
