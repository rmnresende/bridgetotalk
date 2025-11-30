package com.renanresende.bridgetotalk.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "app.constraints")
@PropertySource(value = "classpath:constraints-messages.yml", factory = YamlPropertySourceFactory.class)
public class ConstraintMessagesProperties {

    private Map<String, String> messages;

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    @PostConstruct
    public void test() {
        System.out.println("Loaded: " + messages);
    }
}
