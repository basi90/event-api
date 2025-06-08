package com.ab.eventapi.service;

import com.ab.eventapi.model.Event;

public interface Notifier {
    void notify(Event event);
}
