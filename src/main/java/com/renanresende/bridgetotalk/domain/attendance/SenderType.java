package com.renanresende.bridgetotalk.domain.attendance;

public enum SenderType {
    CUSTOMER("Customer"),
    AGENT("Agent"),
    SYSTEM("System"); // Para mensagens automáticas, como notificação de fechamento.


    SenderType(String customer) {

    }
}