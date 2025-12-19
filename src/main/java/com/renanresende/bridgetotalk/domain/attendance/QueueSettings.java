package com.renanresende.bridgetotalk.domain.attendance;

import java.time.Duration;
import java.time.LocalTime;

public class QueueSettings {

    private AutomatedMessage welcomeMessage;
    private AutomatedMessage offHoursMessage;
    private AutomatedMessage waitingMessage;

    private WeeklySchedule weeklySchedule;

    private Duration autoCloseAfterInactivity;
    private Duration maxWaitingTime;

    private QueueSettings(AutomatedMessage welcomeMessage,
                          AutomatedMessage offHoursMessage,
                          AutomatedMessage waitingMessage,
                          WeeklySchedule weeklySchedule) {

        this.welcomeMessage = welcomeMessage;
        this.offHoursMessage = offHoursMessage;
        this.waitingMessage = waitingMessage;
        this.weeklySchedule = weeklySchedule;
    }

    public static QueueSettings defaultSettings(AutomatedMessage welcomeMessage,
                                                AutomatedMessage offHoursMessage,
                                                AutomatedMessage waitingMessage,
                                                WeeklySchedule weeklySchedule) {
        return new QueueSettings(
                welcomeMessage,
                offHoursMessage,
                offHoursMessage,
                WeeklySchedule.standardBusinessHours(LocalTime.of(9, 0),
                                                     LocalTime.of(18, 0)
                )
        );
    }

    public boolean isOpenAt(QueueDateTime dateTime) {
        return weeklySchedule.isOpenAt(dateTime);
    }

    public AutomatedMessage welcomeMessage() {
        return welcomeMessage;
    }

    public AutomatedMessage offHoursMessage() {
        return offHoursMessage;
    }
}
