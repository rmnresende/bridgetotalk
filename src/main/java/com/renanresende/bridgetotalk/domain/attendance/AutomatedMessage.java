package com.renanresende.bridgetotalk.domain.attendance;

public record AutomatedMessage(
        String content,
        boolean enabled
) {

    public static AutomatedMessage disabled() {
        return new AutomatedMessage(null, false);
    }

    public static AutomatedMessage enabled(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }
        return new AutomatedMessage(content, true);
    }
}
