package com.online.oms.services;

import com.online.oms.entities.OrderDetail;
import com.online.oms.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IOrderDetailService {

    OrderDetail makeOrderDetail(OrderDetail orderDetail);

    boolean deleteById(Long id);

    OrderDetail getOrderDetail(Long id) throws ResourceNotFoundException;

    List<OrderDetail> getAllOrderDetail() throws ResourceNotFoundException;

    OrderDetail updateOrderDetailById(OrderDetail orderDetail, Long id) throws ResourceNotFoundException;
}
