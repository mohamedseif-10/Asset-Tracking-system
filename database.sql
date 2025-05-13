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
    status VARCHAR(18) CHECK (status IN ('IN_USE', 'AVAILABLE', 'UNDER_MAINTENANCE')), -- asset availability status
    description TEXT,
    add_date DATETIME DEFAULT NOW(), -- date added (automatically set)
    user_id INT, -- null if not in use
    FOREIGN KEY (user_id) REFERENCES users(u_id) ON DELETE SET NULL
);

-- Index for better performance when filtering by user (Where)
CREATE INDEX idx_user_id ON assets(user_id);
CREATE INDEX idx_asset_user ON asset_history(user_id);
CREATE INDEX idx_asset_id ON asset_history(asset_id);
CREATE INDEX idx_asset ON assets(as_id);

-- Create asset_history table
CREATE TABLE asset_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    asset_id INT NOT NULL,
    log_date DATETIME,
    status VARCHAR(18) NOT NULL CHECK (status IN ('ASSIGNED', 'UNASSIGNED', 'UNDER_MAINTENANCE', 'UPDATED', 'DELETED'))
);
