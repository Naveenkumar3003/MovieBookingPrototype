package com.theatre.model;

import java.util.List;

public class BookingRequest {
    private int showId;
    private String userName;
    private List<Integer> seatIds;
    
    public BookingRequest() {}
    
    // Getters and Setters
    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public List<Integer> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Integer> seatIds) { this.seatIds = seatIds; }
}