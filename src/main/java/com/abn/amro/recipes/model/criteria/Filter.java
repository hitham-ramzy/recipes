package com.abn.amro.recipes.model.criteria;

import lombok.Data;

@Data
public class Filter<TYPE> {
    private TYPE equals;
    private TYPE notEquals;
}