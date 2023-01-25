package com.online.oms.services.imp;

import com.online.oms.dao.OrderDao;
import com.online.oms.entities.OrderEntity;
import com.online.oms.entities.OrderDetail;
import com.online.oms.entities.Status;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public OrderEntity makeOrder(OrderEntity orderEntity) {

        OrderEntity saveOrderEntity = orderDao.save(orderEntity);
        LocalDateTime localDate = LocalDateTime.now();

        saveOrderEntity.setCreateDateTime(localDate);

        for (OrderDetail orderDetail : orderEntity.getOrderDetail()) {
            orderDetail.setOrderId(saveOrderEntity.getOrderId());
            orderDetailService.makeOrderDetail(orderDetail);
        }
        return saveOrderEntity;
    }

    @Override
    public boolean deleteOrderById(Long id) throws ResourceNotFoundException {

        try {
            orderDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }


    public OrderEntity getOneOrderById(Long id) throws ResourceNotFoundException {

        Optional order = orderDao.findById(id);

        if (order.isPresent()) {
            return (OrderEntity) order.get();
        } else {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Override
    public List<OrderEntity> getAllOrder() throws ResourceNotFoundException {

        List<OrderEntity> orderEntityList = orderDao.findAll();

        if (orderEntityList.isEmpty()) {
            throw new ResourceNotFoundException("No order found in Database");
        }
        return orderEntityList;
    }

    @Override
    public OrderEntity updateOrderById(OrderEntity orderEntity, Long id) throws ResourceNotFoundException {

        Long orderId = orderEntity.getOrderId();
        Optional orderIdFromDb = orderDao.findById(id);

        if (orderId != id) {
            throw new ResourceNotFoundException("Id not match");
        } else if (orderIdFromDb.isEmpty()) {
            throw new ResourceNotFoundException("Id not found");
        } else {
            orderDao.save(orderEntity);
        }
        return orderEntity;
    }

    @Override
    public List<OrderEntity> getAllOrderHistoryByCustomerId(Long id) {

        List<OrderEntity> orderEntityList = orderDao.getAllByUserId(id);
        return orderEntityList;
    }

    @Override
    public List<OrderEntity> getAllOrderHistoryByCustomerIdAndDate(Long id, LocalDate date) {

        List<OrderEntity> orderEntityList = orderDao.getAllByUserIdAndOrderDate(id, date);
        return orderEntityList;
    }

    @Override
    public List<OrderEntity> getAllOrdersByDate(LocalDate date) {

        List<OrderEntity> orderEntityList = orderDao.getAllByOrderDate(date);
        return orderEntityList;
    }

    @Override
    public List<OrderEntity> getAllOrdersBetweenDates(LocalDate endDate, LocalDate startDate) {

        List<OrderEntity> orderEntityList = orderDao.getAllByOrderDateLessThanAndOrderDateGreaterThan(endDate, startDate);
        return orderEntityList;
    }

    @Override
    public OrderEntity updateOrderStatusById(Long id, String status) throws ResourceNotFoundException {

        OrderEntity orderEntity =  getOneOrderById(id);
        Status statusEntityEnum = Status.PLACED;

        switch (status) {
            case "PLACED":
                statusEntityEnum = Status.PLACED;
                break;
            case "IN_PROGRESS":
                statusEntityEnum = Status.IN_PROGRESS;
                break;
            case "SHIPPED":
                statusEntityEnum = Status.SHIPPED;
                break;
            case "DELIVERED":
                statusEntityEnum = Status.DELIVERED;
                break;
        }
        orderEntity.setStatus(statusEntityEnum);
        orderDao.save(orderEntity);
        return orderEntity;
    }

    @Override
    public List<OrderEntity> getAllOrdersByStatus(String status) {

        Status statusEntityEnum = Status.PLACED;

        switch (status) {
            case "PLACED":
                statusEntityEnum = Status.PLACED;
                break;
            case "IN_PROGRESS":
                statusEntityEnum = Status.IN_PROGRESS;
                break;
            case "SHIPPED":
                statusEntityEnum = Status.SHIPPED;
                break;
            case "DELIVERED":
                statusEntityEnum = Status.DELIVERED;
                break;
        }

        List<OrderEntity> orderEntityList = orderDao.getAllByStatus(statusEntityEnum);
        return orderEntityList;
    }

    @Override
    public List<OrderEntity> getAllOrdersByStatusAndOrderDate(String status, LocalDate orderDate) {

        Status statusEntityEnum = Status.PLACED;

        switch (status) {
            case "PLACED":
                statusEntityEnum = Status.PLACED;
                break;
            case "IN_PROGRESS":
                statusEntityEnum = Status.IN_PROGRESS;
                break;
            case "SHIPPED":
                statusEntityEnum = Status.SHIPPED;
                break;
            case "DELIVERED":
                statusEntityEnum = Status.DELIVERED;
                break;
        }
        List<OrderEntity> orderEntityList = orderDao.getAllByStatusAndOrderDate(statusEntityEnum, orderDate);
        return orderEntityList;
    }

    @Override
    public List<OrderEntity> getAllOrdersByStatusAndBetweenDates(String status, LocalDate endDate, LocalDate startDate) {

        Status statusEntityEnum = Status.PLACED;

        switch (status) {
            case "PLACED":
                statusEntityEnum = Status.PLACED;
                break;
            case "IN_PROGRESS":
                statusEntityEnum = Status.IN_PROGRESS;
                break;
            case "SHIPPED":
                statusEntityEnum = Status.SHIPPED;
                break;
            case "DELIVERED":
                statusEntityEnum = Status.DELIVERED;
                break;
        }

        List<OrderEntity> orderEntityList = orderDao.getAllByStatusAndOrderDateLessThanAndOrderDateGreaterThan(statusEntityEnum, endDate, startDate);
        return orderEntityList;
    }
}
