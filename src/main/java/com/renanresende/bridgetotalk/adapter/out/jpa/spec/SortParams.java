package com.renanresende.bridgetotalk.adapter.out.jpa.spec;

import org.springframework.data.domain.Sort;

import java.util.Set;

public class SortParams {

    private static final Set<String> ALLOWED_FIELDS_TO_SORT_AGENTS = Set.of(
            "name",
            "email",
            "createdAt"
    );

    public static String validateFieldToSortAgent(String sortBy) {
        if (sortBy == null || sortBy.isBlank() || !ALLOWED_FIELDS_TO_SORT_AGENTS.contains(sortBy))
            return "name";

        return sortBy;
    }

    public static Sort.Direction validateDirection(String direction) {
        if (direction == null || direction.isBlank()) return Sort.Direction.ASC;

        return switch (direction.toLowerCase()) {
            case "asc" -> Sort.Direction.ASC;
            case "desc" -> Sort.Direction.DESC;
            default -> Sort.Direction.ASC;
        };
    }
}
