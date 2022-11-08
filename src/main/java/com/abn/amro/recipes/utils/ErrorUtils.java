package com.abn.amro.recipes.utils;

import com.abn.amro.recipes.exception.InvalidInputException;
import com.abn.amro.recipes.exception.NotFoundException;

public class ErrorUtils {

    public static void generateInvalidInputError(ErrorEnum constant) {
        throw new InvalidInputException(constant.getMessage());
    }

    public static void generateNotFoundError() {
        throw new NotFoundException();
    }

}
