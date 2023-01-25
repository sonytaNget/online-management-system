package com.online.oms.services;

import com.online.oms.entities.OrderEntity;
import com.online.oms.exceptions.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IOrderService {

    OrderEntity makeOrder (OrderEntity orderEntity);

    boolean deleteOrderById (Long id) throws ResourceNotFoundException;

    OrderEntity getOneOrderById(Long id) throws ResourceNotFoundException;

    List<OrderEntity> getAllOrder() throws ResourceNotFoundException;

    OrderEntity updateOrderById (OrderEntity orderEntity, Long id) throws ResourceNotFoundException;

    List<OrderEntity> getAllOrderHistoryByCustomerId(Long id);

    List<OrderEntity> getAllOrderHistoryByCustomerIdAndDate(Long id, LocalDate date);

    List<OrderEntity> getAllOrdersByDate(LocalDate date);

    List<OrderEntity> getAllOrdersBetweenDates(LocalDate endDate, LocalDate startDate);

    OrderEntity updateOrderStatusById(Long id, String status) throws ResourceNotFoundException;

    List<OrderEntity> getAllOrdersByStatus(String status);

    List<OrderEntity> getAllOrdersByStatusAndOrderDate(String status, LocalDate orderDate);

    List<OrderEntity> getAllOrdersByStatusAndBetweenDates(String status, LocalDate endDate, LocalDate startDate);
}
