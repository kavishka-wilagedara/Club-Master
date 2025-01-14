package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Event;

import java.util.Date;
import java.util.List;

public interface EventService {

    Event createEvent(Event event, String clubId, String clubAdminId);
    List<Event> getAllEvents();
    Event updateEventById(String clubAdminId, String eventId, Event event);
    void deleteEventById(String eventId);
    List<Event> getAllEventsByClubId(String clubId);
    List<Event> getAllOngoingEventsByClubId(String clubId);
    List<Event> getAllUpcomingEventsByClubId(String clubId);
    List<Event> getAllPastEventsByClubId(String clubId);
    Event getEventById(String eventId);
    List<Event> getAllOngoingEvents();
    List<Event> getAllUpcomingEvents();
    List<Event> getAllPastEvents();
}