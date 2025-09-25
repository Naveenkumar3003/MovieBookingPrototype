CREATE DATABASE IF NOT EXISTS theatre_booking;
USE theatre_booking;

-- Movies Table
CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration INT NOT NULL
);

-- Shows Table
CREATE TABLE shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    show_time TIMESTAMP NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

-- Seats Table
CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    show_id INT NOT NULL,
    row_no INT NOT NULL,
    col_no INT NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Bookings Table
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    show_id INT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    seat_numbers TEXT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Sample Data
INSERT INTO movies (title, duration) VALUES
('Avengers: Endgame', 180),
('Spider-Man: No Way Home', 148),
('The Batman', 176),
('Top Gun: Maverick', 130);

INSERT INTO shows (movie_id, show_time) VALUES
(1, '2024-01-15 10:00:00'),
(1, '2024-01-15 14:00:00'),
(1, '2024-01-15 18:00:00'),
(2, '2024-01-15 11:00:00'),
(2, '2024-01-15 15:00:00'),
(3, '2024-01-15 12:00:00'),
(3, '2024-01-15 16:00:00'),
(4, '2024-01-15 13:00:00'),
(4, '2024-01-15 17:00:00');

-- Generate seats for all shows (10 rows x 12 columns = 120 seats per show)
DELIMITER //
CREATE PROCEDURE GenerateSeats()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE show_id INT;
    DECLARE row_num INT;
    DECLARE col_num INT;
    DECLARE show_cursor CURSOR FOR SELECT s.show_id FROM shows s;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN show_cursor;
    
    show_loop: LOOP
        FETCH show_cursor INTO show_id;
        IF done THEN
            LEAVE show_loop;
        END IF;
        
        SET row_num = 1;
        WHILE row_num <= 10 DO
            SET col_num = 1;
            WHILE col_num <= 12 DO
                INSERT INTO seats (show_id, row_no, col_no, status) 
                VALUES (show_id, row_num, col_num, 'AVAILABLE');
                SET col_num = col_num + 1;
            END WHILE;
            SET row_num = row_num + 1;
        END WHILE;
    END LOOP;
    
    CLOSE show_cursor;
END //
DELIMITER ;

CALL GenerateSeats();
DROP PROCEDURE GenerateSeats;