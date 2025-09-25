package com.theatre.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.theatre.dao.ShowDAO;
import com.theatre.model.Show;
import com.theatre.model.Seat;
import com.theatre.model.BookingRequest;

@RestController
@CrossOrigin(origins = "*")
public class TheatreController {
    
    @Autowired
    private ShowDAO showDAO;
    
    @GetMapping("/shows")
    public List<Show> getAllShows() {
        return showDAO.getAllShows();
    }
    
    @GetMapping("/shows/{id}/seats")
    public List<Seat> getSeatsByShowId(@PathVariable int id) {
        return showDAO.getSeatsByShowId(id);
    }
    
    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookSeats(@RequestBody BookingRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        boolean success = showDAO.bookSeats(request.getShowId(), request.getUserName(), request.getSeatIds());
        
        if (success) {
            response.put("success", true);
            response.put("message", "Booking confirmed successfully!");
            response.put("totalPrice", request.getSeatIds().size() * 150.0);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Booking failed. Some seats may already be booked.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
