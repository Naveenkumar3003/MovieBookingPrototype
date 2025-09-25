package com.theatre.model;

public class Movie {
    private int movieId;
    private String title;
    private int duration;
    
    public Movie() {}
    
    public Movie(int movieId, String title, int duration) {
        this.movieId = movieId;
        this.title = title;
        this.duration = duration;
    }
    
    // Getters and Setters
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
}