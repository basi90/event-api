package com.ab.eventapi.service.notifier;

import com.ab.eventapi.model.Event;

public interface Notifier {
    void notify(Event event);
}
