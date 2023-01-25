package com.online.oms.services;

import com.online.oms.dao.OrderDetailDao;
import com.online.oms.entities.OrderDetail;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.OrderDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderEntityDetailServiceTest {

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Mock
    private OrderDetailDao orderDetailDao;

    @Test
    public void testCreateOrderDetail() {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId(1L);
        orderDetail.setQty(10);
        orderDetail.setPrice(90L);

        orderDetailService.makeOrderDetail(orderDetail);
        verify(orderDetailDao).save(orderDetail);
    }

    @Test
    public void testDeleteOrderDetail() {

        orderDetailService.deleteById(1L);
        verify(orderDetailDao).deleteById(1L);
    }

    @Test
    public void testDeleteOrderDetailNotFound() {

        doThrow(EmptyResultDataAccessException.class).when(orderDetailDao).deleteById(anyLong());
        boolean isDelete = orderDetailService.deleteById(1L);
        assertThat(isDelete).isFalse();

    }
    
    @Test
    public void testGetOneOrderDetail() throws ResourceNotFoundException {

        Long id = 1L;
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(id);

        when(orderDetailDao.findById(id)).thenReturn(Optional.of(orderDetail));
        OrderDetail result = orderDetailService.getOrderDetail(id);
        assertThat(result.getOrderDetailId()).isEqualTo(orderDetail.getOrderDetailId());
    }

    @Test
    public void testGetOneOrderDetailNotFound() {

        when(orderDetailDao.findById(anyLong())).thenReturn(Optional.empty());

        try {
            orderDetailService.getOrderDetail(1L);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not found");
        }
    }

    @Test
    public void testGetAllOrderDetail() throws ResourceNotFoundException {

        OrderDetail orderDetail = new OrderDetail();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        when(orderDetailDao.findAll()).thenReturn(orderDetailList);
        List<OrderDetail> orderDetailEntities = orderDetailService.getAllOrderDetail();
        assertThat(orderDetailEntities).isEqualTo(orderDetailList);

    }

    @Test
    public void testGetAllOrderDetailEmptyList() {

        List<OrderDetail> orderDetailEmptyList = new ArrayList<>();
        when(orderDetailDao.findAll()).thenReturn(orderDetailEmptyList);

        try {
            orderDetailService.getAllOrderDetail();
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No order detail in database");
        }
    }

    @Test
    public void updateOrderDetailById() throws ResourceNotFoundException {

        Long id = 1L;

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(id);

        when(orderDetailDao.findById(id)).thenReturn(Optional.of(orderDetail));

        orderDetailService.updateOrderDetailById(orderDetail, id);
        verify(orderDetailDao).save(orderDetail);
    }

    @Test
    public void updateOrderDetailByIdNotMatch() {

        Long id = 1L;

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(5L);

        when(orderDetailDao.findById(id)).thenReturn(Optional.of(orderDetail));

        try {
            orderDetailService.updateOrderDetailById(orderDetail, id);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not match");
        }
    }

    @Test
    public void updateOrderDetailByIdNotFound() {

        when(orderDetailDao.findById(anyLong())).thenReturn(Optional.empty());
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(1L);

        try {
            orderDetailService.updateOrderDetailById(orderDetail, 1L);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not found");
        }
    }

}
