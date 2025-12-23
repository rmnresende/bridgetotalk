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
                          WeeklySchedule weeklySchedule,
                          Duration autoCloseAfterInactivity,
                          Duration maxWaitingTime) {

        this.welcomeMessage = welcomeMessage;
        this.offHoursMessage = offHoursMessage;
        this.waitingMessage = waitingMessage;
        this.weeklySchedule = weeklySchedule;
        this.autoCloseAfterInactivity = autoCloseAfterInactivity;
        this.maxWaitingTime = maxWaitingTime;
    }

    /**
     * Creates and initializes a new instance of {@code QueueSettings} with default settings.
     * The default settings include:
     * - Empty and disabled {@code welcomeMessage}, {@code offHoursMessage}, and {@code waitingMessage}.
     * - A weekly schedule set to standard business hours (Monday to Friday, 9:00 AM to 6:00 PM).
     *
     * @return A new instance of {@code QueueSettings} configured with default settings.
     */
    public static QueueSettings initializeWithDefaultSettings() {

       var welcomeMessage = AutomatedMessage.disabled();
       var offHoursMessage = AutomatedMessage.disabled();
       var waitingMessage = AutomatedMessage.disabled();
       var weeklySchedule = WeeklySchedule.standardBusinessHours(LocalTime.of(9, 0),
                                                                 LocalTime.of(18, 0)
       );

        return new QueueSettings(
                welcomeMessage,
                offHoursMessage,
                waitingMessage,
                weeklySchedule,
                null,
                null
        );
    }

    public static QueueSettings rehydrate(AutomatedMessage welcomeMessage,
                                          AutomatedMessage offHoursMessage,
                                          AutomatedMessage waitingMessage,
                                          WeeklySchedule weeklySchedule,
                                          Duration autoCloseAfterInactivity,
                                          Duration maxWaitingTime) {

        return new QueueSettings(welcomeMessage, offHoursMessage, waitingMessage, weeklySchedule, autoCloseAfterInactivity, maxWaitingTime);
    }

    public boolean isOpenAt(QueueDateTime dateTime) {
        return weeklySchedule.isOpenAt(dateTime);
    }

    public AutomatedMessage getWelcomeMessage() {
        return welcomeMessage;
    }

    public AutomatedMessage getOffHoursMessage() {
        return offHoursMessage;
    }

    public AutomatedMessage getWaitingMessage() {
        return waitingMessage;
    }

    public WeeklySchedule getWeeklySchedule(){
        return weeklySchedule;
    }

    public Duration getAutoCloseAfterInactivity() {
        return autoCloseAfterInactivity;
    }

    public Duration getMaxWaitingTime() {
        return maxWaitingTime;
    }
}
