package com.abn.amro.recipes.utils;

public enum ErrorConstant {

    NAME_ALREADY_EXIST("Name Already Exist"),

    RECIPE_TYPE_NOT_EXIST("Recipe Type is not exist"),

    RECIPE_TYPE_NAME_NOT_CHANGED("Recipe Type Name not changed"),

    RECIPE_TYPE_ALREADY_USED("Recipe Type already used"),

    INGREDIENT_NOT_EXIST("Ingredient is not exist"),

    INGREDIENT_NAME_NOT_CHANGED("Ingredient Name not changed"),

    INGREDIENT_ALREADY_USED("Ingredient already used"),

    GENERAL_ERROR("General Error");

    private String message;

    ErrorConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
