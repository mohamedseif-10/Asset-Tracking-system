-- Create the database
CREATE DATABASE Asset_Tracking_System;
USE Asset_Tracking_System;

-- Create users table
CREATE TABLE users (
    u_id INT PRIMARY KEY AUTO_INCREMENT,
    u_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role CHAR(10) NOT NULL CHECK (role IN ('manager', 'staff'))
);

-- Create assets table
CREATE TABLE assets (
    as_id INT PRIMARY KEY AUTO_INCREMENT,
    as_name VARCHAR(100) NOT NULL,
    status VARCHAR(18) CHECK (status IN ('In use', 'Available', 'Under maintenance')), -- asset availability status
    description TEXT,
    add_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- date added (automatically set)
    update_date DATETIME NULL ON UPDATE CURRENT_TIMESTAMP, -- Null as default, Current time when update it
    user_id INT, -- null if not in use
    FOREIGN KEY (user_id) REFERENCES users(u_id) ON DELETE SET NULL
);

-- Index for better performance when filtering by user (Where)
CREATE INDEX idx_user_id ON assets(user_id);

-- Create asset_history table
CREATE TABLE asset_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    asset_id INT NOT NULL,
    start_date DATETIME,
    end_date DATETIME,
    status VARCHAR(50) NOT NULL CHECK (status IN ('Assigned', 'Returned', 'Repaired')),
    FOREIGN KEY (user_id) REFERENCES users(u_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(as_id) ON DELETE CASCADE
);
