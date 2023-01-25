package com.online.oms.services.imp;

import com.online.oms.dao.OrderDetailDao;
import com.online.oms.entities.OrderDetail;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements IOrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public OrderDetail makeOrderDetail(OrderDetail orderDetail) {

        return orderDetailDao.save(orderDetail);
    }

    @Override
    public boolean deleteById(Long id) {

        try {
            orderDetailDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws ResourceNotFoundException {

        Optional orderDetail = orderDetailDao.findById(id);

        if (!orderDetail.isPresent()) {
            throw new ResourceNotFoundException("Id not found");
        } else {
            return (OrderDetail) orderDetail.get();
        }
    }

    @Override
    public List<OrderDetail> getAllOrderDetail() throws ResourceNotFoundException {

        List<OrderDetail> orderDetailList = orderDetailDao.findAll();

        if (orderDetailList.isEmpty()) {
            throw new ResourceNotFoundException("No order detail in database");
        }

        return orderDetailList;
    }

    @Override
    public OrderDetail updateOrderDetailById(OrderDetail orderDetail, Long id) throws ResourceNotFoundException {

        Long orderDetailId = orderDetail.getOrderDetailId();
        Optional orderDetailIdFromDb = orderDetailDao.findById(id);

        if (orderDetailId != id) {
            throw new ResourceNotFoundException("Id not match");
        } else if (orderDetailIdFromDb.isEmpty()) {
            throw new ResourceNotFoundException("Id not found");
        } else {
            orderDetailDao.save(orderDetail);
        }
        return orderDetail;
    }
}
