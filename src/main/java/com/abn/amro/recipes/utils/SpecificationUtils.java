package com.abn.amro.recipes.utils;

import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.criteria.Filter;
import com.abn.amro.recipes.model.criteria.StringFilter;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {


    public static Specification<Recipe> buildSpecification(Filter filter, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(root.get(fieldName), filter.getEquals());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(root.get(fieldName), filter.getNotEquals());
        }
        return null;
    }

    public static Specification<Recipe> buildStringSpecification(StringFilter filter, String fieldName) {
        Specification<Recipe> specification = buildSpecification(filter, fieldName);
        if (specification == null && filter.getContains() != null) {
            return (root, query, builder) -> builder.like(builder.upper(root.get(fieldName)), wrapLikeQuery(filter.getContains()));
        }
        return specification;
    }

    public static Specification<Recipe> buildReferenceSpecification(StringFilter filter, String referenceName, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(root.get(referenceName).get(fieldName), filter.getEquals());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(root.get(referenceName).get(fieldName), filter.getNotEquals());
        } else if (filter.getContains() != null) {
            return (root, query, builder) -> builder.like(root.get(referenceName).get(fieldName), wrapLikeQuery(filter.getContains()));
        }
        return null;
    }

    private static String wrapLikeQuery(String txt) {
        return "%" + txt + '%';
    }

}
