package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.model.dto.OrderDTO;
import com.mdv.appstore.model.dto.OrderHistoryDTO;
import com.mdv.appstore.model.dto.OrderItemDTO;
import com.mdv.appstore.model.request.OrderCreateRequest;
import com.mdv.appstore.model.request.OrderHistoryRequest;
import com.mdv.appstore.model.request.OrderItemRequest;

@Mapper
public interface OrderMapper {

    void insertOrder(@Param("orderCreateRequest") OrderCreateRequest orderCreateRequest);

    void insertOrderDetail(@Param("orderItemRequest") OrderItemRequest orderItemRequest);

    void insertOrderHistory(@Param("orderHistoryRequest") OrderHistoryRequest orderHistoryRequest);

    OrderDTO findById(@Param("id") Long id);

    List<OrderDTO> findAll();

    List<OrderDTO> findAllByUserId(@Param("userId") Long userId);

    OrderItemDTO findOrderItem(Long id);

    List<OrderItemDTO> findOrderItems(@Param("orderId") Long orderId);

    List<OrderHistoryDTO> findOrderHistories(@Param("orderId") Long orderId);

    void updateOrderStatus(@Param("id") Long id, @Param("status") String status);
}
