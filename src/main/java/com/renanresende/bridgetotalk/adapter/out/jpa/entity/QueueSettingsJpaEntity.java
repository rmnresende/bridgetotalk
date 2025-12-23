package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.adapter.out.jpa.converter.WeeklyScheduleConverter;
import com.renanresende.bridgetotalk.domain.attendance.WeeklySchedule;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Duration;
import java.util.UUID;

@Entity
@Table(name = "queue_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueSettingsJpaEntity {

    @Id
    @Column(name = "queue_id")
    private UUID queueId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "queue_id")
    private QueueJpaEntity queue;

    // Welcome message
    private String welcomeMessage;
    private boolean welcomeEnabled;

    // Off-hours message
    private String offHoursMessage;
    private boolean offHoursEnabled;

    // Waiting message
    private String waitingMessage;
    private boolean waitingEnabled;

    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = WeeklyScheduleConverter.class)
    @Column(name = "weekly_schedule", columnDefinition = "jsonb", nullable = false)
    private WeeklySchedule weeklySchedule;

    @Column(name = "auto_close_after_inactivity", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration autoCloseAfterInactivity;

    @Column(name = "max_waiting_time", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration maxWaitingTime;

}
