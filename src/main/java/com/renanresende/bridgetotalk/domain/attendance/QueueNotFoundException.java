package com.renanresende.bridgetotalk.domain.attendance;

import com.renanresende.bridgetotalk.domain.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class QueueNotFoundException extends ResourceNotFoundException {

    public QueueNotFoundException(UUID id) {
        super("Queue not found with id: " + id + " in company");
    }
}
