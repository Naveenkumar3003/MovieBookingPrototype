package com.theatre.model;

public class Booking {
    private int bookingId;
    private int showId;
    private String userName;
    private String seatNumbers;
    private double totalPrice;
    
    public Booking() {}
    
    public Booking(int bookingId, int showId, String userName, String seatNumbers, double totalPrice) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.userName = userName;
        this.seatNumbers = seatNumbers;
        this.totalPrice = totalPrice;
    }
    
    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    
    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(String seatNumbers) { this.seatNumbers = seatNumbers; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}