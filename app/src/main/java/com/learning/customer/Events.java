package com.learning.customer;

import java.io.Serializable;

public class Events implements Serializable {

    private String name;
    private String id;
    private String organiser;
    private String startDate;
    private String endDate;
    private String startTime;
    private String contactInfo;
    private String venue;
    private String ticketPrice;
    private String status;

    public Events() {

    }

    public Events(String name, String id, String organiser, String startDate, String endDate, String startTime, String contactInfo, String venue, String status, String ticketPrice) {
        this.name = name;
        this.id = id;
        this.organiser = organiser;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.contactInfo = contactInfo;
        this.venue = venue;
        this.status = status;
        this.ticketPrice = ticketPrice;
    }

    public String getName() {
        return name;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
