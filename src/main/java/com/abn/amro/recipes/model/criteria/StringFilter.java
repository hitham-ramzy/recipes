package com.abn.amro.recipes.model.criteria;

import lombok.Data;

@Data
public class StringFilter extends Filter<String> {
    private String contains;
}