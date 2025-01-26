CREATE DATABASE IF NOT EXISTS `app_store`;
USE `app_store`;

CREATE TABLE IF NOT EXISTS `brand`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL UNIQUE,
    `created_at`  datetime(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`  datetime(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `is_deleted`  bit(1)       NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
);

CREATE TABLE IF NOT EXISTS `product`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `is_deleted`    bit(1)       NOT NULL DEFAULT 0,
    `name`          varchar(255) NOT NULL,
    `price`         DECIMAL(10, 2) DEFAULT 0,
    `image_url`     varchar(255) DEFAULT NULL,
    `total_quantity`int          NOT NULL DEFAULT 0,
    `sold`          int          NOT NULL DEFAULT 0,
    `view`          int          NOT NULL DEFAULT 0,
    `brand_id`      bigint       DEFAULT NULL,
    `created_at`    datetime(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`    datetime(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_brand_id` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
);

CREATE TABLE IF NOT EXISTS `product_attribute`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `attribute_name`  varchar(255) DEFAULT NULL,
    `attribute_value` varchar(255) DEFAULT NULL,
    `product_id`      bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_attribute_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
);

CREATE TABLE IF NOT EXISTS `category`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL UNIQUE,
    `created_at`  datetime(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`  datetime(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `is_deleted`  bit(1)       NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
);

CREATE TABLE IF NOT EXISTS `product_category`
(
    `product_id`  bigint NOT NULL,
    `category_id` bigint NOT NULL,
    PRIMARY KEY (`product_id`, `category_id`),
    CONSTRAINT `fk_product_category_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_product_category_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);

CREATE TABLE IF NOT EXISTS `user`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `address`      varchar(255) DEFAULT NULL,
    `avatar`       varchar(255) DEFAULT NULL,
    `dob`          date         DEFAULT NULL,
    `email`        varchar(255) NOT NULL UNIQUE,
    `name`         varchar(255) NOT NULL,
    `password`     varchar(255) NOT NULL,
    `phone`        varchar(255) NOT NULL UNIQUE,
    `created_at`   datetime(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`   datetime(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
);

CREATE TABLE IF NOT EXISTS `cart_item`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `quantity`   int    NOT NULL DEFAULT 0,
    `product_id` bigint NOT NULL,
    `user_id`    bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_cart_item_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_cart_item_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `voucher`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `code`              varchar(255) NOT NULL UNIQUE,
    `condition_value`   DECIMAL(10, 2) NOT NULL DEFAULT 0,
    `discount_price`    DECIMAL(10, 2) NOT NULL DEFAULT 0,
    `end_time`          date         NOT NULL,
    `start_time`        date         NOT NULL,
    `total_quantity`    int          NOT NULL DEFAULT 0,
    `used_quantity`     int          NOT NULL DEFAULT 0,
    `created_at`        datetime(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`        datetime(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `is_active`         bit(1)       NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
);

CREATE TABLE IF NOT EXISTS `order`
(
    `id`           bigint                              NOT NULL AUTO_INCREMENT,
    `order_code`   varchar(255)                        NOT NULL UNIQUE,
    `address`      varchar(255)                        NOT NULL,
    `created_at`   datetime(6)                         DEFAULT CURRENT_TIMESTAMP(6),
    `phone`        varchar(255)                        NOT NULL,
    `status`       enum ('CANCEL','SUCCESS','PENDING','RETURN') NOT NULL,
    `total_price`  DECIMAL(10, 2)                      NOT NULL,
    `voucher_code` varchar(255) DEFAULT NULL,
    `user_id`      bigint                              NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_order_voucher_code` FOREIGN KEY (`voucher_code`) REFERENCES `voucher` (`code`),
);

CREATE TABLE IF NOT EXISTS `order_line`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `price_at_order_time` DECIMAL(10, 2) NOT NULL,
    `quantity`            int    NOT NULL,
    `order_id`            bigint NOT NULL,
    `product_id`          bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_line_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_order_line_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
);

CREATE TABLE IF NOT EXISTS `supplier`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) NOT NULL,
    `phone`        varchar(255) NOT NULL,
    `address`      varchar(255) DEFAULT NULL,
    `is_deleted`   bit(1)       NOT NULL DEFAULT 0,
    `created_at`   datetime(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`   datetime(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `inventory`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `import_price` DECIMAL(10, 2) NOT NULL DEFAULT 0,
    `quantity`     int    NOT NULL DEFAULT 0,
    `product_id`   bigint NOT NULL,
    `created_at`   datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
    `supplier_id`  bigint NOT NULL,
    `batch_code`   varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_inventory_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_inventory_supplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
);

CREATE TABLE IF NOT EXISTS `inventory_transaction`
(
    `id`                bigint                              NOT NULL AUTO_INCREMENT,
    `product_id`        bigint                              NOT NULL,
    `quantity`          int                                 NOT NULL,
    `type`              enum ('IMPORT','EXPORT')            NOT NULL,
    `transaction_date`  datetime(6)                         DEFAULT CURRENT_TIMESTAMP(6),
    `supplier_id`       bigint                              NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_inventory_transaction_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_inventory_transaction_supplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
);

CREATE TABLE IF NOT EXISTS `role`
(
    `id`   bigint NOT NULL AUTO_INCREMENT,
    `name` enum ('CUSTOMER','EMPLOYEE','ADMIN') DEFAULT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE IF NOT EXISTS `review`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `user_id`    bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `rating`     int    DEFAULT NULL CHECK (`rating` BETWEEN 1 AND 5),
    `review_date` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
    `comment`    text   DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_review_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_review_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `product_promotion`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `product_id`     bigint NOT NULL,
    `end_time`       date    DEFAULT NULL,
    `start_time`     date    DEFAULT NULL,
    `discount_price` DECIMAL(10, 2) NOT NULL DEFAULT 0,
    `is_active`      bit(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_promotion_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `daily_revenue`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `date`          date   NOT NULL UNIQUE,
    `total_revenue` DECIMAL(10, 2) DEFAULT 0,
    PRIMARY KEY (`id`),
);

CREATE TABLE IF NOT EXISTS `order_history`
(
    `id`          bigint                              NOT NULL AUTO_INCREMENT,
    `order_id`    bigint                              NOT NULL,
    `status`      enum ('CANCEL','SUCCESS','PENDING','RETURN') NOT NULL,
    `changed_at`  datetime(6)                         DEFAULT CURRENT_TIMESTAMP(6),
    `changed_by`  bigint                              NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_history_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
    CONSTRAINT `fk_order_history_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `payment`
(
    `id`           bigint                              NOT NULL AUTO_INCREMENT,
    `order_id`     bigint                              NOT NULL,
    `method`       enum ('CASH','CREDIT_CARD','PAYPAL') NOT NULL,
    `amount`       DECIMAL(10, 2)                      NOT NULL,
    `status`       enum ('PENDING','SUCCESS','FAILED') NOT NULL,
    `created_at`   datetime(6)                         DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_payment_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
);
