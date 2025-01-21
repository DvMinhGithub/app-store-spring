-- Chèn dữ liệu vào bảng brand
INSERT IGNORE INTO `brand` (`name`, `created_at`, `updated_at`, `is_deleted`)
VALUES
  ('Apple', NOW(), NOW(), 0),
  ('Samsung', NOW(), NOW(), 0),
  ('Xiaomi', NOW(), NOW(), 0);

-- Chèn dữ liệu vào bảng product
INSERT IGNORE INTO `product` (`name`, `price`, `image_url`, `sold`, `view`, `brand_id`, `created_at`, `updated_at`, `is_deleted`)
VALUES
  ('iPhone 14', 999.99, 'https://example.com/iphone14.jpg', 100, 500, 1, NOW(), NOW(), 0),
  ('Galaxy S22', 899.99, 'https://example.com/galaxys22.jpg', 80, 400, 2, NOW(), NOW(), 0),
  ('Redmi Note 11', 299.99, 'https://example.com/redminote11.jpg', 200, 1000, 3, NOW(), NOW(), 0);

-- Chèn dữ liệu vào bảng user
INSERT IGNORE INTO `user` (`address`, `avatar`, `dob`, `email`, `name`, `password`, `phone`, `created_at`, `updated_at`)
VALUES
  ('123 Main St', 'https://example.com/avatar1.jpg', '1990-01-01', 'user1@example.com', 'John Doe', 'password123', '1234567890', NOW(), NOW()),
  ('456 Elm St', 'https://example.com/avatar2.jpg', '1995-05-05', 'user2@example.com', 'Jane Doe', 'password456', '0987654321', NOW(), NOW());

-- Chèn dữ liệu vào bảng voucher
INSERT IGNORE INTO `voucher` (`code`, `condition_value`, `discount_price`, `end_time`, `start_time`, `total_quantity`, `used_quantity`, `created_at`, `updated_at`, `is_active`)
VALUES
  ('SALE10', 100.0, 10.0, '2023-12-31', '2023-01-01', 100, 0, NOW(), NOW(), 1),
  ('FREESHIP', 50.0, 5.0, '2023-11-30', '2023-01-01', 200, 0, NOW(), NOW(), 1);

-- Chèn dữ liệu vào bảng order
INSERT IGNORE INTO `order` (`order_code`, `address`, `phone`, `status`, `total_price`, `voucher_code`, `user_id`, `created_at`)
VALUES
  ('ORDER001', '123 Main St', '1234567890', 'PENDING', 999.99, NULL, 1, NOW()),
  ('ORDER002', '456 Elm St', '0987654321', 'SUCCESS', 899.99, 'SALE10', 2, NOW());

-- Chèn dữ liệu vào bảng order_line
INSERT IGNORE INTO `order_line` (`price_at_order_time`, `quantity`, `order_id`, `product_id`)
VALUES
  (999.99, 1, 1, 1),
  (899.99, 1, 2, 2);

-- Chèn dữ liệu vào bảng payment
INSERT IGNORE INTO `payment` (`order_id`, `method`, `amount`, `status`, `created_at`)
VALUES
  (1, 'CREDIT_CARD', 999.99, 'PENDING', NOW()),
  (2, 'PAYPAL', 899.99, 'SUCCESS', NOW());

-- Chèn dữ liệu vào bảng order_history
INSERT IGNORE INTO `order_history` (`order_id`, `status`, `changed_at`, `changed_by`)
VALUES
  (1, 'PENDING', NOW(), 1),
  (2, 'SUCCESS', NOW(), 2);

-- Chèn dữ liệu vào bảng review
INSERT IGNORE INTO `review` (`user_id`, `product_id`, `rating`, `review_date`)
VALUES
  (1, 1, 5, NOW()),
  (2, 2, 4, NOW());

-- Chèn dữ liệu vào bảng role
INSERT IGNORE INTO `role` (`name`)
VALUES
  ('CUSTOMER'),
  ('EMPLOYEE'),
  ('ADMIN');

-- Chèn dữ liệu vào bảng user_role
INSERT IGNORE INTO `user_role` (`user_id`, `role_id`)
VALUES
  (1, 1),
  (2, 1);

-- Chèn dữ liệu vào bảng category
INSERT IGNORE INTO `category` (`name`, `created_at`, `updated_at`, `is_deleted`)
VALUES
  ('Smartphone', NOW(), NOW(), 0),
  ('Tablet', NOW(), NOW(), 0);

-- Chèn dữ liệu vào bảng product_category
INSERT IGNORE INTO `product_category` (`product_id`, `category_id`)
VALUES
  (1, 1),
  (2, 1),
  (3, 1);

-- Chèn dữ liệu vào bảng supplier
INSERT IGNORE INTO `supplier` (`name`, `phone`, `address`, `is_deleted`, `created_at`, `updated_at`)
VALUES
  ('Supplier A', '1111111111', '123 Supplier St', 0, NOW(), NOW()),
  ('Supplier B', '2222222222', '456 Supplier St', 0, NOW(), NOW());

-- Chèn dữ liệu vào bảng inventory
INSERT IGNORE INTO `inventory` (`import_price`, `quantity`, `product_id`, `supplier_id`, `batch_code`, `created_at`)
VALUES
  (800.0, 100, 1, 1, 'BATCH001', NOW()),
  (700.0, 150, 2, 2, 'BATCH002', NOW());

-- Chèn dữ liệu vào bảng inventory_transaction
INSERT IGNORE INTO `inventory_transaction` (`product_id`, `quantity`, `type`, `transaction_date`, `supplier_id`)
VALUES
  (1, 100, 'IMPORT', NOW(), 1),
  (2, 150, 'IMPORT', NOW(), 2);

-- Chèn dữ liệu vào bảng daily_revenue
INSERT IGNORE INTO `daily_revenue` (`date`, `total_revenue`)
VALUES
  ('2023-10-01', 1899.98),
  ('2023-10-02', 1500.00);