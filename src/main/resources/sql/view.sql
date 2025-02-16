USE `app_store`;

CREATE OR REPLACE VIEW user_role_view
AS
SELECT
    u.id,
    u.address,
    u.avatar,
    u.dob,
    u.email,
    u.name,
    u.password,
    u.phone,
    u.created_at AS createdAt,
    u.updated_at AS updatedAt,
    r.id         AS r_id,
    r.name       AS r_name
FROM
    `user` u
LEFT JOIN
    user_role ur ON u.id = ur.user_id
LEFT JOIN
    `role` r ON ur.role_id = r.id;

CREATE OR REPLACE VIEW order_view
AS
SELECT
    o.id,
    o.user_id,
    o.voucher_code,
    o.order_code,
    o.status,
    o.total_price,
    o.address,
    o.phone,
    o.checkout_url,
    o.created_at,
    o.updated_at,
    oi.id              AS oi_id,
    oi.order_id        AS oi_order_id,
    oi.product_id      AS oi_product_id,
    oi.quantity        AS oi_quantity,
    oi.price_at_order_time AS oi_price_at_order_time
FROM
    `order` o
LEFT JOIN
    order_item oi ON o.id = oi.order_id;