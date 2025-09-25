package com.theatre.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.theatre.config.DatabaseConfig;
import com.theatre.model.Show;
import com.theatre.model.Seat;
import com.theatre.model.Booking;

@Repository
public class ShowDAO {
    
    @Autowired
    private DatabaseConfig dbConfig;
    
    private static final double SEAT_PRICE = 150.0;
    
    public List<Show> getAllShows() {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT s.show_id, s.movie_id, m.title, s.show_time " +
                     "FROM shows s JOIN movies m ON s.movie_id = m.movie_id " +
                     "ORDER BY s.show_time";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Show show = new Show();
                show.setShowId(rs.getInt("show_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setMovieTitle(rs.getString("title"));
                show.setShowTime(rs.getTimestamp("show_time").toLocalDateTime());
                shows.add(show);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return shows;
    }
    
    public List<Seat> getSeatsByShowId(int showId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE show_id = ? ORDER BY row_no, col_no";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, showId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setShowId(rs.getInt("show_id"));
                seat.setRowNo(rs.getInt("row_no"));
                seat.setColNo(rs.getInt("col_no"));
                seat.setStatus(rs.getString("status"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return seats;
    }
    
    public boolean bookSeats(int showId, String userName, List<Integer> seatIds) {
        Connection conn = null;
        try {
            conn = dbConfig.getConnection();
            conn.setAutoCommit(false);
            
            // Check if all seats are available
            String checkSql = "SELECT COUNT(*) FROM seats WHERE seat_id IN (" +
                             String.join(",", seatIds.stream().map(String::valueOf).toArray(String[]::new)) +
                             ") AND status = 'AVAILABLE'";
            
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            
            if (rs.getInt(1) != seatIds.size()) {
                conn.rollback();
                return false; // Some seats are not available
            }
            
            // Update seat status
            String updateSql = "UPDATE seats SET status = 'BOOKED' WHERE seat_id IN (" +
                              String.join(",", seatIds.stream().map(String::valueOf).toArray(String[]::new)) + ")";
            
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.executeUpdate();
            
            // Get seat numbers for booking record
            String seatSql = "SELECT row_no, col_no FROM seats WHERE seat_id IN (" +
                           String.join(",", seatIds.stream().map(String::valueOf).toArray(String[]::new)) + ")";
            
            PreparedStatement seatStmt = conn.prepareStatement(seatSql);
            ResultSet seatRs = seatStmt.executeQuery();
            
            List<String> seatLabels = new ArrayList<>();
            while (seatRs.next()) {
                int row = seatRs.getInt("row_no");
                int col = seatRs.getInt("col_no");
                seatLabels.add((char)('A' + row - 1) + String.valueOf(col));
            }
            
            // Insert booking record
            String bookingSql = "INSERT INTO bookings (show_id, user_name, seat_numbers, total_price) VALUES (?, ?, ?, ?)";
            PreparedStatement bookingStmt = conn.prepareStatement(bookingSql);
            bookingStmt.setInt(1, showId);
            bookingStmt.setString(2, userName);
            bookingStmt.setString(3, String.join(",", seatLabels));
            bookingStmt.setDouble(4, seatIds.size() * SEAT_PRICE);
            bookingStmt.executeUpdate();
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
