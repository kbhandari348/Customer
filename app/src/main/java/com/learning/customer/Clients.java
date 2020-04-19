package com.learning.customer;

public class Clients {

    private String clientID;
    private String clientName;
    private String clientCollege;
    private String clientCollegeStudentID;
    private String clientEmail;
    private String clientPhoneNumber;
    private String eventIDj;
    private String userUID;

    public Clients() {
    }

    public Clients(String clientID, String clientName, String clientCollege, String clientCollegeStudentID, String clientEmail, String clientPhoneNumber, String eventIDj, String userUID) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.clientCollege = clientCollege;
        this.clientCollegeStudentID = clientCollegeStudentID;
        this.clientEmail = clientEmail;
        this.clientPhoneNumber = clientPhoneNumber;
        this.eventIDj=eventIDj;
        this.userUID = userUID;
    }

    public String getEventIDj() {
        return eventIDj;
    }

    public void setEventIDj(String eventIDj) {
        this.eventIDj = eventIDj;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCollege() {
        return clientCollege;
    }

    public void setClientCollege(String clientCollege) {
        this.clientCollege = clientCollege;
    }

    public String getClientCollegeStudentID() {
        return clientCollegeStudentID;
    }

    public void setClientCollegeStudentID(String clientCollegeStudentID) { this.clientCollegeStudentID = clientCollegeStudentID; }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) { this.clientPhoneNumber = clientPhoneNumber; }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
