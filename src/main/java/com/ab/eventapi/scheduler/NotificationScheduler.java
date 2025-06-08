package com.ab.eventapi.scheduler;

import com.ab.eventapi.service.EventNotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private final EventNotificationService notificationService;

    public NotificationScheduler(EventNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 60000)
    public void runScheduledNotificationCheck() {
        notificationService.checkAndNotifyUpcomingEvents();
    }
}