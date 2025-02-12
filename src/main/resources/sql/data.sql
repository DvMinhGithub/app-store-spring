INSERT IGNORE INTO `role` (`name`)
VALUES
    ('CUSTOMER'),
    ('EMPLOYEE'),
    ('ADMIN');
-- Password: 123456
INSERT IGNORE INTO `user` (`name`, `email`, `password`, `phone`, `address`, `avatar`, `dob`, `created_at`, `updated_at`)
VALUES
    ('John Doe', 'user1@example.com', '$2a$10$4ptoazYi85Xp89SmSs3a0eo1h7KfOhU1/f.aFqgNsRc629OSZm1oS', '1234567890', '123 Main St', 'https://example.com/avatar1.jpg', '1990-01-01', NOW(), NOW()),
    ('Jane Doe', 'user2@example.com', '$2a$10$4ptoazYi85Xp89SmSs3a0eo1h7KfOhU1/f.aFqgNsRc629OSZm1oS', '0987654321', '456 Elm St', 'https://example.com/avatar2.jpg', '1995-05-05', NOW(), NOW());

INSERT IGNORE INTO `user_role` (`user_id`, `role_id`)
VALUES
    (1, 1),
    (2, 1);

INSERT IGNORE INTO `brand` (`name`, `created_at`, `updated_at`, `is_deleted`)
VALUES
    ('Apple', NOW(), NOW(), 0),
    ('Samsung', NOW(), NOW(), 0),
    ('Xiaomi', NOW(), NOW(), 0);

INSERT IGNORE INTO `category` (`name`, `created_at`, `updated_at`, `is_deleted`)
VALUES
    ('Smartphone', NOW(), NOW(), 0),
    ('Tablet', NOW(), NOW(), 0);

INSERT IGNORE INTO `product` (`name`, `price`, `image_url`, `sold`, `view`, `brand_id`, `created_at`, `updated_at`, `is_deleted`)
VALUES
    ('iPhone 14', 999.99, 'https://example.com/iphone14.jpg', 100, 500, 1, NOW(), NOW(), 0),
    ('Galaxy S22', 899.99, 'https://example.com/galaxys22.jpg', 80, 400, 2, NOW(), NOW(), 0),
    ('Redmi Note 11', 299.99, 'https://example.com/redminote11.jpg', 200, 1000, 3, NOW(), NOW(), 0);

INSERT IGNORE INTO `product_category` (`product_id`, `category_id`)
VALUES
    (1, 1),
    (2, 1),
    (3, 1);

INSERT IGNORE INTO `product_promotion` (`product_id`, `discount_price`, `start_time`, `end_time`, `is_active`)
VALUES
    (1, 100.0, '2023-01-01', '2023-12-31', 1),
    (2, 50.0, '2023-01-01', '2023-11-30', 1);

INSERT IGNORE INTO `cart_item` (`user_id`, `product_id`, `quantity`)
VALUES
    (1, 1, 1),
    (2, 2, 1);

INSERT IGNORE INTO `supplier` (`name`, `phone`, `address`, `is_deleted`, `created_at`, `updated_at`)
VALUES
    ('Supplier A', '1111111111', '123 Supplier St', 0, NOW(), NOW()),
    ('Supplier B', '2222222222', '456 Supplier St', 0, NOW(), NOW());

INSERT IGNORE INTO `inventory` (`import_price`, `quantity`, `product_id`, `supplier_id`, `batch_code`, `created_at`)
VALUES
    (800.0, 100, 1, 1, 'BATCH001', NOW()),
    (700.0, 150, 2, 2, 'BATCH002', NOW());

INSERT IGNORE INTO `inventory_transaction` (`product_id`, `quantity`, `type`, `transaction_date`, `supplier_id`)
VALUES
    (1, 100, 'IMPORT', NOW(), 1),
    (2, 150, 'IMPORT', NOW(), 2);

INSERT IGNORE INTO `voucher` (`code`, `discount_price`, `condition_value`, `total_quantity`, `used_quantity`, `start_time`, `end_time`, `is_active`, `created_at`, `updated_at`)
VALUES
    ('SALE10', 10.0, 100.0, 100, 0, '2023-01-01', '2023-12-31', 1, NOW(), NOW()),
    ('FREESHIP', 5.0, 50.0, 200, 0, '2023-01-01', '2023-11-30', 1, NOW(), NOW());

INSERT IGNORE INTO `order` (`user_id`, `voucher_code`, `order_code`, `status`, `total_price`, `address`, `phone`, `created_at`)
VALUES
    (1, NULL, 'ORDER001', 'PENDING', 999.99, '123 Main St', '1234567890', NOW()),
    (2, 'SALE10', 'ORDER002', 'SUCCESS', 899.99, '456 Elm St', '0987654321', NOW());

INSERT IGNORE INTO `order_item` (`order_id`, `product_id`, `quantity`, `price_at_order_time`)
VALUES
    (1, 1, 1, 999.99),
    (2, 2, 1, 899.99);

INSERT IGNORE INTO `payment` (`order_id`, `method`, `amount`, `status`, `created_at`)
VALUES
    (1, 'CREDIT_CARD', 999.99, 'PENDING', NOW()),
    (2, 'PAYPAL', 899.99, 'SUCCESS', NOW());

INSERT IGNORE INTO `order_history` (`order_id`, `status`, `changed_at`, `changed_by`)
VALUES
    (1, 'PENDING', NOW(), 1),
    (2, 'SUCCESS', NOW(), 2);

INSERT IGNORE INTO `review` (`user_id`, `product_id`, `rating`, `review_date`)
VALUES
    (1, 1, 5, NOW()),
    (2, 2, 4, NOW());

INSERT IGNORE INTO `daily_revenue` (`date`, `total_revenue`)
VALUES
    ('2023-10-01', 1899.98),
    ('2023-10-02', 1500.00);