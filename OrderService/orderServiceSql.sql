CREATE DATABASE IF NOT EXISTS `order-service`;
USE `order-service`;

-- Create the orders table
DROP TABLE IF EXISTS `orders`;
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL
);

-- Create the t_order_line_items table
DROP TABLE IF EXISTS `order_line_items`;
CREATE TABLE order_line_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku_code VARCHAR(255),
    price DECIMAL(19, 2),
    quantity INT,
    order_id BIGINT, -- This column establishes the relationship with the orders table
    FOREIGN KEY (order_id) REFERENCES orders(order_id) -- This ensures referential integrity
);