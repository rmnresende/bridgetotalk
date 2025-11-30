package com.renanresende.bridgetotalk.adapter.in.web.resolver;

import com.renanresende.bridgetotalk.config.ConstraintMessagesProperties;
import org.springframework.stereotype.Component;

@Component
public class ConstraintMessageResolver {

    private final ConstraintMessagesProperties properties;

    public ConstraintMessageResolver(ConstraintMessagesProperties properties) {
        this.properties = properties;
    }

    public String resolve(String databaseMessage) {

        if (databaseMessage == null) {
            return "Data conflict occurred";
        }

        var messages = properties.getMessages();

        return messages
                .entrySet()
                .stream()
                .filter(entry -> databaseMessage.contains(entry.getKey()))
                .map(entry -> entry.getValue())
                .findFirst()
                .orElse("Data conflict occurred");
    }
}
