package com.abn.amro.recipes.utils;

public enum ErrorConstant {

    NAME_ALREADY_EXIST("Name Already Exist"),

    WRONG_RECIPE_TYPE_ID("Wrong recipe type Id"),

    WRONG_INGREDIENT_ID("Wrong Ingredient Id");

    private String message;

    ErrorConstant(String message) {
        this.message = message;
    }


}
