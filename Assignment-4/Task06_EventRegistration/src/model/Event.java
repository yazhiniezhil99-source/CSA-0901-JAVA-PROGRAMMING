package model;

import java.time.LocalDate;

public class Event {

    private int eventId;
    private String eventName;
    private LocalDate eventDate;
    private String venue;
    private int capacity;
    private int registeredCount;

    public Event(int eventId, String eventName,
                 LocalDate eventDate, String venue,
                 int capacity, int registeredCount) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.capacity = capacity;
        this.registeredCount = registeredCount;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    public int getAvailableSeats() {
        return capacity - registeredCount;
    }

    @Override
    public String toString() {
        return eventName + " - " + eventDate
                + " (" + getAvailableSeats() + " seats available)";
    }
}