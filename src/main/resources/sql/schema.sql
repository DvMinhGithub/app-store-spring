CREATE DATABASE IF NOT EXISTS `app_store`;
USE `app_store`;

CREATE TABLE IF NOT EXISTS `role` (
    `id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`  ENUM('CUSTOMER', 'EMPLOYEE', 'ADMIN') DEFAULT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(255) NOT NULL,
    `email`        VARCHAR(255) NOT NULL UNIQUE,
    `password`     VARCHAR(255) NOT NULL,
    `phone`        VARCHAR(255) NOT NULL UNIQUE,
    `address`      VARCHAR(255) DEFAULT NULL,
    `avatar`       VARCHAR(255) DEFAULT NULL,
    `dob`          DATE         DEFAULT NULL,
    `created_at`   DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`   DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_role` (
    `user_id`   BIGINT UNSIGNED NOT NULL,
    `role_id`   BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE IF NOT EXISTS `brand` (
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255)    NOT NULL UNIQUE,
    `created_at`  DATETIME(6)     DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`  DATETIME(6)     DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `is_deleted`  BIT(1)          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `supplier` (
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255) NOT NULL,
    `phone`       VARCHAR(255) NOT NULL,
    `address`     VARCHAR(255) DEFAULT NULL,
    `is_deleted`  BIT(1)       NOT NULL DEFAULT 0,
    `created_at`  DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`  DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `category` (
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255) NOT NULL UNIQUE,
    `created_at`  DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`  DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `is_deleted`  BIT(1)       NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `product` (
    `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `brand_id`        BIGINT UNSIGNED DEFAULT NULL,
    `name`            VARCHAR(255)    NOT NULL,
    `price`           DECIMAL(10, 2)  NOT NULL DEFAULT 0 CHECK (`price` >= 0),
    `image_url`       VARCHAR(255)    DEFAULT NULL,
    `total_quantity`  INT UNSIGNED    NOT NULL DEFAULT 0,
    `sold`            INT UNSIGNED    NOT NULL DEFAULT 0,
    `view`            INT UNSIGNED    NOT NULL DEFAULT 0,
    `is_deleted`      BIT(1)          NOT NULL DEFAULT 0,
    `created_at`      DATETIME(6)     DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`      DATETIME(6)     DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_brand_id` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
);

CREATE TABLE IF NOT EXISTS `product_attribute` (
    `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `product_id`      BIGINT UNSIGNED NOT NULL,
    `attribute_name`  VARCHAR(255)    DEFAULT NULL,
    `attribute_value` VARCHAR(255)    DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_attribute_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `product_category` (
    `product_id`  BIGINT UNSIGNED NOT NULL,
    `category_id` BIGINT UNSIGNED    NOT NULL,
    PRIMARY KEY (`product_id`, `category_id`),
    CONSTRAINT `fk_product_category_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_product_category_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);

CREATE TABLE IF NOT EXISTS `product_promotion` (
    `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `product_id`     BIGINT UNSIGNED NOT NULL,
    `discount_price` DECIMAL(10, 2) NOT NULL DEFAULT 0,
    `start_time`     DATE         DEFAULT NULL,
    `end_time`       DATE         DEFAULT NULL,
    `is_active`      BIT(1)       NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_promotion_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `review` (
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT UNSIGNED NOT NULL,
    `product_id`  BIGINT UNSIGNED NOT NULL,
    `rating`      INT UNSIGNED          DEFAULT NULL CHECK (`rating` BETWEEN 1 AND 5),
    `comment`     TEXT         DEFAULT NULL,
    `review_date` DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_review_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_review_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `cart_item` (
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT UNSIGNED NOT NULL,
    `product_id` BIGINT UNSIGNED NOT NULL,
    `quantity`   INT UNSIGNED    NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_cart_item_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_cart_item_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `inventory` (
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `product_id`    BIGINT UNSIGNED NOT NULL,
    `supplier_id`   BIGINT UNSIGNED NOT NULL,
    `batch_code`    VARCHAR(255)    NOT NULL UNIQUE,
    `import_price`  DECIMAL(10, 2)  NOT NULL DEFAULT 0 CHECK (`import_price` >= 0),
    `quantity`      INT UNSIGNED    NOT NULL DEFAULT 0 CHECK (`quantity` >= 0),
    `created_at`    DATETIME(6)     DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_inventory_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_inventory_supplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
);

CREATE TABLE IF NOT EXISTS `inventory_transaction` (
    `id`               BIGINT UNSIGNED          NOT NULL AUTO_INCREMENT,
    `product_id`       BIGINT UNSIGNED          NOT NULL,
    `supplier_id`      BIGINT                   DEFAULT NULL,
    `quantity`         INT UNSIGNED             NOT NULL,
    `type`             ENUM('IMPORT', 'EXPORT') NOT NULL,
    `transaction_date` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_inventory_transaction_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `voucher` (
    `id`              BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT,
    `code`            VARCHAR(255)      NOT NULL UNIQUE,
    `discount_price`  DECIMAL(10, 2)    NOT NULL DEFAULT 0,
    `condition_value` DECIMAL(10, 2)    NOT NULL DEFAULT 0,
    `total_quantity`  INT UNSIGNED      NOT NULL DEFAULT 0,
    `used_quantity`   INT UNSIGNED      NOT NULL DEFAULT 0,
    `start_time`      DATE              NOT NULL,
    `end_time`        DATE              NOT NULL,
    `is_active`       BIT(1)            NOT NULL DEFAULT 0,
    `created_at`      DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`      DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `order` (
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`       BIGINT UNSIGNED NULL,
    `voucher_code`  VARCHAR(255)    DEFAULT NULL,
    `order_code`    VARCHAR(255)    NULL UNIQUE,
    `status`        ENUM('CANCEL', 'SUCCESS', 'PENDING', 'RETURN') NULL,
    `total_price`   DECIMAL(10, 2)  NULL,
    `address`       VARCHAR(255)    NULL,
    `phone`         VARCHAR(255)    NULL,
    `checkout_url`  VARCHAR(255)    DEFAULT NULL,
    `created_at`    DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`    DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_order_voucher_code` FOREIGN KEY (`voucher_code`) REFERENCES `voucher` (`code`)
);

CREATE TABLE IF NOT EXISTS `order_item` (
    `id`                  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `order_id`            BIGINT UNSIGNED NOT NULL,
    `product_id`          BIGINT UNSIGNED NOT NULL,
    `quantity`            INT UNSIGNED    NOT NULL,
    `price_at_order_time` DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_item_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_order_item_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
);

CREATE TABLE IF NOT EXISTS `order_history` (
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `order_id`   BIGINT UNSIGNED NOT NULL,
    `changed_by` BIGINT UNSIGNED NOT NULL,
    `status`     ENUM('CANCEL', 'SUCCESS', 'PENDING', 'RETURN') NOT NULL,
    `changed_at` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_history_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
    CONSTRAINT `fk_order_history_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `payment` (
    `id`         BIGINT UNSIGNED                        NOT NULL AUTO_INCREMENT,
    `order_id`   BIGINT UNSIGNED                        NOT NULL,
    `method`     ENUM('CASH', 'CREDIT_CARD', 'PAYPAL') NOT NULL,
    `amount`     DECIMAL(10, 2)                         NOT NULL,
    `status`     ENUM('PENDING', 'SUCCESS', 'FAILED') NOT NULL,
    `created_at` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_payment_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
);

CREATE TABLE IF NOT EXISTS `daily_revenue` (
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `date`         DATE NOT NULL UNIQUE,
    `total_revenue` DECIMAL(10, 2) DEFAULT 0,
    `created_at`   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
);