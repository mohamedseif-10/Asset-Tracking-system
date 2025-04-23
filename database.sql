CREATE DATABASE Asset_Tracking_System;
USE Asset_Tracking_System;


CREATE TABLE users (
    u_id INT PRIMARY KEY AUTO_INCREMENT,
    u_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role CHAR(10) NOT NULL CHECK (role IN ('manager', 'staff'))
    );


CREATE TABLE assets (
    as_id INT PRIMARY KEY AUTO_INCREMENT,
    as_name VARCHAR(100) NOT NULL,
    type VARCHAR(255) NOT NULL,
    status VARCHAR(50), -- the status of asset (in use, available, under maintenance, ...etc)
    description TEXT, 
    add_date DATETIME DEFAULT NOW(), -- date and time the asset was added if you don't say it will add the current
    user_id INT, -- if the asset in use add user_id to know who use it
    FOREIGN KEY (user_id) REFERENCES users(u_id) ON DELETE SET NULL
);


CREATE TABLE asset_history (
    user_id INT,
    asset_id INT NOT NULL,
    date DATETIME DEFAULT NOW(),
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(u_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(as_id) ON DELETE CASCADE
);

