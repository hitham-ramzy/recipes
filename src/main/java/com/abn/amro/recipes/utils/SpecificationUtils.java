package com.abn.amro.recipes.utils;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.criteria.Filter;
import com.abn.amro.recipes.model.criteria.StringFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

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
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(builder.upper(root.get(fieldName)), filter.getEquals().toUpperCase());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(builder.upper(root.get(fieldName)), filter.getNotEquals().toUpperCase());
        } else if (filter.getContains() != null) {
            return (root, query, builder) -> builder.like(builder.upper(root.get(fieldName)), wrapLikeQuery(filter.getContains()));
        }
        return null;
    }

    public static Specification<Recipe> buildReferenceSpecification(Filter filter, String referenceName, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(root.get(referenceName).get(fieldName), filter.getEquals());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(root.get(referenceName).get(fieldName), filter.getNotEquals());
        }
        return null;
    }

    public static Specification<Recipe> buildStringReferenceSpecification(StringFilter filter, String referenceName, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(builder.upper(root.get(referenceName).get(fieldName)), filter.getEquals());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(builder.upper(root.get(referenceName).get(fieldName)), filter.getNotEquals());
        } else if (filter.getContains() != null) {
            return (root, query, builder) -> builder.like(builder.upper(root.get(referenceName).get(fieldName)), wrapLikeQuery(filter.getContains()));
        }
        return null;
    }

    public static Specification<Recipe> buildDeepReferenceSpecification(Filter filter, String firstReferenceName, String secondReferenceName, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> {
                Join<Recipe, Ingredient> join = root.join(firstReferenceName).join(secondReferenceName);
                return builder.equal(join.get(fieldName), filter.getEquals());
            };
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> {
                Join<Recipe, Ingredient> join = root.join(firstReferenceName).join(secondReferenceName);
                return builder.notEqual(join.get(fieldName), filter.getNotEquals());
            };
        }
        return null;
    }

    public static Specification<Recipe> buildStringDeepReferenceSpecification(StringFilter filter, String firstReferenceName, String secondReferenceName, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> {
                Join<Recipe, Ingredient> join = root.join(firstReferenceName).join(secondReferenceName);
                return builder.equal(builder.upper(join.get(fieldName)), filter.getEquals());
            };
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> {
                Join<Recipe, Ingredient> join = root.join(firstReferenceName).join(secondReferenceName);
                return builder.notEqual(builder.upper(join.get(fieldName)), filter.getNotEquals());
            };
        } else if (filter.getContains() != null) {
            return (root, query, builder) -> {
                Join<Recipe, Ingredient> join = root.join(firstReferenceName).join(secondReferenceName);
                return builder.like(builder.upper(join.get(fieldName)), wrapLikeQuery(filter.getContains()));
            };
        }
        return null;
    }

    private static String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

}
