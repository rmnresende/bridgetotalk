package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.CreateQueueDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.ResponseQueueDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.UpdateQueueDto;
import com.renanresende.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.renanresende.bridgetotalk.domain.attendance.Queue;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QueueDtoMapper {

    CreateQueueCommand toCreateCommand(CreateQueueDto dto);

    UpdateQueueCommand toUpdateCommand(UUID id, UpdateQueueDto dto);

    ResponseQueueDto toResponseDto(Queue queue);
}
