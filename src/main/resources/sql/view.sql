USE `app_store`;

-- View hiển thị thông tin người dùng kèm vai trò
CREATE OR replace VIEW user_role_view
AS
  SELECT u.id,
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
  FROM   USER u
         left join user_role ur
                ON u.id = ur.user_id
         left join ROLE r
                ON ur.role_id = r.id; 