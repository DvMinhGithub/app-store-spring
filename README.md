# App Store Project

## Giới thiệu
Dự án **App Store** là một ứng dụng quản lý cửa hàng trực tuyến, bao gồm các chức năng quản lý sản phẩm, đơn hàng, người dùng, và các hoạt động liên quan đến kho hàng. Dự án được xây dựng bằng **Spring Boot** và sử dụng **MySQL** làm cơ sở dữ liệu.

## Công nghệ sử dụng
- **Spring Boot 3.4.1**: Framework chính để xây dựng ứng dụng.
- **MySQL**: Cơ sở dữ liệu quan hệ để lưu trữ dữ liệu.
- **MyBatis**: ORM (Object-Relational Mapping) để kết nối và thao tác với cơ sở dữ liệu.
- **Lombok**: Giúp giảm boilerplate code trong Java.
- **Spotless**: Công cụ để định dạng code tự động.

## Cấu trúc dự án
Dự án được tổ chức theo cấu trúc chuẩn của Spring Boot, bao gồm các package chính:
- `com.mdv`: Package gốc của dự án.
- `controller`: Chứa các lớp điều khiển (Controller) để xử lý các request HTTP.
- `service`: Chứa các lớp dịch vụ (Service) để xử lý logic nghiệp vụ.
- `repository`: Chứa các lớp truy vấn dữ liệu (Repository) để tương tác với cơ sở dữ liệu.
- `model`: Chứa các lớp đối tượng (Entity) đại diện cho các bảng trong cơ sở dữ liệu.
- `dto`: Chứa các lớp DTO (Data Transfer Object) để truyền dữ liệu giữa các lớp.
- `config`: Chứa các lớp cấu hình (Configuration) cho ứng dụng.

## Cơ sở dữ liệu
Cơ sở dữ liệu của ứng dụng bao gồm các bảng sau:

### Bảng `brand`
- `id`: Khóa chính, tự động tăng.
- `name`: Tên thương hiệu, duy nhất.
- `created_at`: Thời gian tạo bản ghi.
- `updated_at`: Thời gian cập nhật bản ghi.
- `is_deleted`: Trạng thái xóa (0: chưa xóa, 1: đã xóa).

### Bảng `product`
- `id`: Khóa chính, tự động tăng.
- `name`: Tên sản phẩm.
- `price`: Giá sản phẩm.
- `image_url`: URL hình ảnh sản phẩm.
- `sold`: Số lượng đã bán.
- `view`: Số lượt xem.
- `brand_id`: Khóa ngoại tham chiếu đến bảng `brand`.
- `created_at`: Thời gian tạo bản ghi.
- `updated_at`: Thời gian cập nhật bản ghi.
- `is_deleted`: Trạng thái xóa (0: chưa xóa, 1: đã xóa).

### Bảng `product_attribute`
- `id`: Khóa chính, tự động tăng.
- `attribute_name`: Tên thuộc tính.
- `attribute_value`: Giá trị thuộc tính.
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.

### Bảng `category`
- `id`: Khóa chính, tự động tăng.
- `name`: Tên danh mục, duy nhất.
- `created_at`: Thời gian tạo bản ghi.
- `updated_at`: Thời gian cập nhật bản ghi.
- `is_deleted`: Trạng thái xóa (0: chưa xóa, 1: đã xóa).

### Bảng `product_category`
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.
- `category_id`: Khóa ngoại tham chiếu đến bảng `category`.

### Bảng `user`
- `id`: Khóa chính, tự động tăng.
- `address`: Địa chỉ người dùng.
- `avatar`: URL avatar người dùng.
- `dob`: Ngày sinh người dùng.
- `email`: Email người dùng, duy nhất.
- `name`: Tên người dùng.
- `password`: Mật khẩu người dùng.
- `phone`: Số điện thoại người dùng, duy nhất.
- `created_at`: Thời gian tạo bản ghi.
- `updated_at`: Thời gian cập nhật bản ghi.

### Bảng `cart_item`
- `id`: Khóa chính, tự động tăng.
- `quantity`: Số lượng sản phẩm trong giỏ hàng.
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.
- `user_id`: Khóa ngoại tham chiếu đến bảng `user`.

### Bảng `voucher`
- `id`: Khóa chính, tự động tăng.
- `code`: Mã voucher, duy nhất.
- `condition_value`: Giá trị điều kiện để sử dụng voucher.
- `discount_price`: Giá trị giảm giá.
- `end_time`: Thời gian kết thúc voucher.
- `start_time`: Thời gian bắt đầu voucher.
- `total_quantity`: Tổng số lượng voucher.
- `used_quantity`: Số lượng voucher đã sử dụng.
- `created_at`: Thời gian tạo bản ghi.
- `updated_at`: Thời gian cập nhật bản ghi.
- `is_active`: Trạng thái kích hoạt (0: không kích hoạt, 1: kích hoạt).

### Bảng `order`
- `id`: Khóa chính, tự động tăng.
- `order_code`: Mã đơn hàng, duy nhất.
- `address`: Địa chỉ giao hàng.
- `created_at`: Thời gian tạo đơn hàng.
- `phone`: Số điện thoại người nhận.
- `status`: Trạng thái đơn hàng (CANCEL, SUCCESS, PENDING, RETURN).
- `total_price`: Tổng giá trị đơn hàng.
- `voucher_code`: Mã voucher sử dụng (nếu có).
- `user_id`: Khóa ngoại tham chiếu đến bảng `user`.

### Bảng `order_line`
- `id`: Khóa chính, tự động tăng.
- `price_at_order_time`: Giá sản phẩm tại thời điểm đặt hàng.
- `quantity`: Số lượng sản phẩm.
- `order_id`: Khóa ngoại tham chiếu đến bảng `order`.
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.

### Bảng `supplier`
- `id`: Khóa chính, tự động tăng.
- `name`: Tên nhà cung cấp.
- `phone`: Số điện thoại nhà cung cấp.
- `address`: Địa chỉ nhà cung cấp.
- `is_deleted`: Trạng thái xóa (0: chưa xóa, 1: đã xóa).
- `created_at`: Thời gian tạo bản ghi.
- `updated_at`: Thời gian cập nhật bản ghi.

### Bảng `inventory`
- `id`: Khóa chính, tự động tăng.
- `import_price`: Giá nhập hàng.
- `quantity`: Số lượng hàng tồn kho.
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.
- `created_at`: Thời gian tạo bản ghi.
- `supplier_id`: Khóa ngoại tham chiếu đến bảng `supplier`.
- `batch_code`: Mã lô hàng, duy nhất.

### Bảng `role`
- `id`: Khóa chính, tự động tăng.
- `name`: Tên vai trò (CUSTOMER, EMPLOYEE, ADMIN).

### Bảng `user_role`
- `user_id`: Khóa ngoại tham chiếu đến bảng `user`.
- `role_id`: Khóa ngoại tham chiếu đến bảng `role`.

### Bảng `review`
- `id`: Khóa chính, tự động tăng.
- `user_id`: Khóa ngoại tham chiếu đến bảng `user`.
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.
- `rating`: Đánh giá (từ 1 đến 5).
- `review_date`: Ngày đánh giá.
- `comment`: Bình luận đánh giá.

### Bảng `product_promotion`
- `id`: Khóa chính, tự động tăng.
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.
- `end_time`: Thời gian kết thúc khuyến mãi.
- `start_time`: Thời gian bắt đầu khuyến mãi.
- `discount_price`: Giá giảm giá.
- `is_active`: Trạng thái kích hoạt (0: không kích hoạt, 1: kích hoạt).

### Bảng `daily_revenue`
- `id`: Khóa chính, tự động tăng.
- `date`: Ngày thống kê doanh thu.
- `total_revenue`: Tổng doanh thu trong ngày.

### Bảng `order_history`
- `id`: Khóa chính, tự động tăng.
- `order_id`: Khóa ngoại tham chiếu đến bảng `order`.
- `status`: Trạng thái đơn hàng (CANCEL, SUCCESS, PENDING, RETURN).
- `changed_at`: Thời gian thay đổi trạng thái.
- `changed_by`: Người thay đổi trạng thái (tham chiếu đến bảng `user`).

### Bảng `payment`
- `id`: Khóa chính, tự động tăng.
- `order_id`: Khóa ngoại tham chiếu đến bảng `order`.
- `method`: Phương thức thanh toán (CASH, CREDIT_CARD, PAYPAL).
- `amount`: Số tiền thanh toán.
- `status`: Trạng thái thanh toán (PENDING, SUCCESS, FAILED).
- `created_at`: Thời gian tạo bản ghi.

### Bảng `inventory_transaction`
- `id`: Khóa chính, tự động tăng.
- `product_id`: Khóa ngoại tham chiếu đến bảng `product`.
- `quantity`: Số lượng hàng.
- `type`: Loại giao dịch (IMPORT, EXPORT).
- `transaction_date`: Thời gian giao dịch.
- `supplier_id`: Khóa ngoại tham chiếu đến bảng `supplier`.

## Cài đặt và chạy ứng dụng

### Yêu cầu hệ thống

-   Java 21
-   MySQL
-   Gradle

### Các bước cài đặt

1.  **Clone dự án**:

    ```bash
    git clone [https://github.com/DvMinhGithub/app-store-spring.git](https://github.com/DvMinhGithub/app-store-spring.git)
    cd app-store
    ```

2.  **Cấu hình cơ sở dữ liệu**:

    -   Tạo một database mới trong MySQL với tên `app_store`.
    -   Cập nhật thông tin kết nối cơ sở dữ liệu trong file `src/main/resources/application.yml`:

        ```yaml
        spring:
          datasource:
            url: jdbc:mysql://localhost:3306/app_store
            username: your-username
            password: your-password
        ```

3.  **Xây dựng ứng dụng bằng Gradle**:

    ```bash
    ./gradlew build
    ```

4.  **Chạy ứng dụng**:

    ```bash
    ./gradlew bootRun
    ```

5.  **Truy cập ứng dụng**:

    -   Mở trình duyệt và truy cập `http://localhost:8080`.

### Cấu hình bổ sung

-   **Profile**:

    -   Nếu bạn muốn chạy ứng dụng với một profile cụ thể (ví dụ: `dev`, `prod`), bạn có thể thêm tham số `-Dspring.profiles.active` khi chạy ứng dụng:

        ```bash
        ./gradlew bootRun -Dspring.profiles.active=dev
        ```

-   **Cổng**:

    -   Để thay đổi cổng mặc định (8080), hãy cập nhật thuộc tính `server.port` trong `application.yml`:

        ```yaml
        server:
          port: 8081
        ```

### Các lệnh Gradle thông dụng

-   `./gradlew build`: Xây dựng ứng dụng.
-   `./gradlew bootRun`: Chạy ứng dụng.
-   `./gradlew test`: Chạy các unit test.
-   `./gradlew clean`: Xóa thư mục build.

### Lưu ý

-   Đảm bảo rằng MySQL server đang chạy trước khi khởi động ứng dụng.
-   Nếu bạn gặp lỗi kết nối cơ sở dữ liệu, hãy kiểm tra lại thông tin kết nối trong `application.yml`.
-   Nếu bạn có bất kỳ vấn đề nào, vui lòng kiểm tra phần Issues trên GitHub hoặc liên hệ với người quản lý dự án.