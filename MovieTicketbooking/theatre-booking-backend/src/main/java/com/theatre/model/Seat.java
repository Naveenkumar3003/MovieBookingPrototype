package com.theatre.model;

public class Seat {
    private int seatId;
    private int showId;
    private int rowNo;
    private int colNo;
    private String status; // AVAILABLE, BOOKED
    
    public Seat() {}
    
    public Seat(int seatId, int showId, int rowNo, int colNo, String status) {
        this.seatId = seatId;
        this.showId = showId;
        this.rowNo = rowNo;
        this.colNo = colNo;
        this.status = status;
    }
    
    // Getters and Setters
    public int getSeatId() { return seatId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }
    
    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }
    
    public int getRowNo() { return rowNo; }
    public void setRowNo(int rowNo) { this.rowNo = rowNo; }
    
    public int getColNo() { return colNo; }
    public void setColNo(int colNo) { this.colNo = colNo; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getSeatLabel() {
        return (char)('A' + rowNo - 1) + String.valueOf(colNo);
    }
}
