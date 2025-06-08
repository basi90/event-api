package com.ab.eventapi.service.notifier;

import com.ab.eventapi.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerNotifier implements Notifier {

    private static final Logger logger = LoggerFactory.getLogger(LoggerNotifier.class);

    @Override
    public void notify(Event event) {
        logger.warn("Notification: Event '{}' is starting at {}", event.getTitle(), event.getStartsAt());
    }
}
