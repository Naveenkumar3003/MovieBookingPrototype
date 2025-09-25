package com.theatre.model;

import java.time.LocalDateTime;

public class Show {
    private int showId;
    private int movieId;
    private String movieTitle;
    private LocalDateTime showTime;
    
    public Show() {}
    
    public Show(int showId, int movieId, String movieTitle, LocalDateTime showTime) {
        this.showId = showId;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.showTime = showTime;
    }
    
    // Getters and Setters
    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }
    
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    
    public LocalDateTime getShowTime() { return showTime; }
    public void setShowTime(LocalDateTime showTime) { this.showTime = showTime; }
}
