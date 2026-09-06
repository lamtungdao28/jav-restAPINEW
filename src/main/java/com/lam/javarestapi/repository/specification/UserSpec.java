package com.lam.javarestapi.repository.specification;

import com.lam.javarestapi.model.User;
import com.lam.javarestapi.util.Gender;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("fistName"), "%" + firstName + "%");
    }

    public static Specification<User> notEquaGender(Gender gender) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("gender"), gender);
    }
}
