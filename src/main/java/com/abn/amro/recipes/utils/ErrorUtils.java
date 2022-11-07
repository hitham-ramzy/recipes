package com.abn.amro.recipes.utils;

import static com.abn.amro.recipes.utils.ErrorConstant.NAME_ALREADY_EXIST;

public class ErrorUtils {

    public static void generateError(ErrorConstant constant) {
        throw new RuntimeException(constant.name());
    }

}
