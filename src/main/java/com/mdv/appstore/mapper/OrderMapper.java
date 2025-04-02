package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.OrderCreateRequest;
import com.mdv.appstore.dto.request.OrderHistoryRequest;
import com.mdv.appstore.dto.request.OrderItemRequest;
import com.mdv.appstore.dto.response.OrderHistoryResponse;
import com.mdv.appstore.dto.response.OrderItemResponse;
import com.mdv.appstore.dto.response.OrderResponse;

@Mapper
public interface OrderMapper {

    void insertOrder(@Param("orderCreateRequest") OrderCreateRequest orderCreateRequest);

    void insertOrderDetail(@Param("orderItemRequest") OrderItemRequest orderItemRequest);

    void insertOrderHistory(@Param("orderHistoryRequest") OrderHistoryRequest orderHistoryRequest);

    OrderResponse findById(@Param("id") Long id);

    List<OrderResponse> findAll();

    List<OrderResponse> findAllByUserId(@Param("userId") Long userId);

    OrderItemResponse findOrderItem(Long id);

    List<OrderItemResponse> findOrderItems(@Param("orderId") Long orderId);

    List<OrderHistoryResponse> findOrderHistories(@Param("orderId") Long orderId);

    Double getTotalRevenue(@Param("startDate") String startDate, @Param("endDate") String endDate);

    void updateOrderStatus(@Param("id") Long id, @Param("status") String status);
}
