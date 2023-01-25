package com.online.oms.services;

import com.online.oms.dao.OrderDao;
import com.online.oms.entities.OrderEntity;
import com.online.oms.entities.OrderDetail;
import com.online.oms.entities.Status;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.OrderDetailService;
import com.online.oms.services.imp.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderEntityServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDao orderDao;


    @Mock
    private OrderDetailService orderDetailService;

    @Test
    public void testAddOrder() {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(1L);
        OrderDetail myOrderDetail = new OrderDetail();
        List<OrderDetail> myOrderDetailList = new ArrayList<>();
        myOrderDetailList.add(myOrderDetail);
        orderEntity.setOrderDetail(myOrderDetailList);

        OrderEntity newOrderEntity = new OrderEntity();
        OrderDetail orderDetail = new OrderDetail();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        newOrderEntity.setOrderDetail(orderDetailList);

        when(orderDao.save(orderEntity)).thenReturn(newOrderEntity);
        when(orderDetailService.makeOrderDetail(myOrderDetail)).thenReturn(orderDetail);

        OrderEntity result = orderService.makeOrder(orderEntity);
        verify(orderDao).save(orderEntity);

        assertThat(result).isEqualTo(newOrderEntity);
        
    }

    @Test
    public void deleteOrderById() throws ResourceNotFoundException {

        boolean isDelete = orderService.deleteOrderById(1L);
        assertThat(isDelete).isTrue();

    }

    @Test
    public void deleteOrderByIdNotFound() throws ResourceNotFoundException {

        doThrow(EmptyResultDataAccessException.class).when(orderDao).deleteById(1L);
        boolean isDelete = orderService.deleteOrderById(1L);
        assertThat(isDelete).isFalse();

    }

    @Test
    public void getOneOrderById() throws ResourceNotFoundException {

        Long id = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);

        when(orderDao.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        OrderEntity result = orderService.getOneOrderById(id);
        assertThat(result.getOrderId()).isEqualTo(orderEntity.getOrderId());
    }


    @Test
    public void getOneOrderByIdNotFound() {

        when(orderDao.findById(anyLong())).thenReturn(Optional.empty());

        try {
            orderService.getOneOrderById(1L);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not found");
        }
    }

    @Test
    public void getAllOrder() throws ResourceNotFoundException {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(1L);
        List<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(orderEntity);

        when(orderDao.findAll()).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrder();
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void getAllOrderEmpty() {

        List<OrderEntity> emptyList = new ArrayList<>();

        when(orderDao.findAll()).thenReturn(emptyList);

        try {
            orderService.getAllOrder();
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No order found in Database");
        }
    }


    @Test
    public void updateOrderById() throws ResourceNotFoundException {

        Long id = 1L;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);

        when(orderDao.findById(id)).thenReturn(Optional.of(orderEntity));

        orderService.updateOrderById(orderEntity, id);
        verify(orderDao).save(orderEntity);
    }

    @Test
    public void updateOrderDetailByIdNotMatch() {

        Long id = 1L;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(5L);

        when(orderDao.findById(id)).thenReturn(Optional.of(orderEntity));

        try {
            orderService.updateOrderById(orderEntity, id);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not match");
        }
    }

    @Test
    public void updateOrderDetailByIdNotFound() {

        when(orderDao.findById(anyLong())).thenReturn(Optional.empty());
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(1L);

        try {
            orderService.updateOrderById(orderEntity, 1L);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not found");
        }
    }

    @Test
    public void testGetAllOrderHistoryByCustomerId() {

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        when(orderDao.getAllByUserId(anyLong())).thenReturn(orderEntityList);
        List<OrderEntity> resultList = orderService.getAllOrderHistoryByCustomerId(1L);
        assertThat(resultList).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrderHistoryByCustomerIdAndDate() {

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate localDate = LocalDate.of(2023, 01, 12);

        when(orderDao.getAllByUserIdAndOrderDate(1L, localDate)).thenReturn(orderEntityList);
        List<OrderEntity> resultList = orderService.getAllOrderHistoryByCustomerIdAndDate(1L, localDate);
        assertThat(resultList).isEqualTo(orderEntityList);
    }
    
    @Test
    public void testGetAllOrdersByDate() {

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate localDate = LocalDate.of(2023, 01, 12);

        when(orderDao.getAllByOrderDate(localDate)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByDate(localDate);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersBetweenDates() {
        
        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);
        
        LocalDate startDate = LocalDate.of(2022, 01, 1);
        LocalDate endDate = LocalDate.of(2023, 01, 12);
        
        when(orderDao.getAllByOrderDateLessThanAndOrderDateGreaterThan(endDate, startDate)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersBetweenDates(endDate, startDate);
        assertThat(result).isEqualTo(orderEntityList);
        
    }

    @Test
    public void testGetAllOrdersByStatusPLACED(){

        Status statusEntity = Status.PLACED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        when(orderDao.getAllByStatus(statusEntity)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatus("PLACED");
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusSHIPPED(){

        Status statusEntity = Status.SHIPPED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        when(orderDao.getAllByStatus(statusEntity)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatus("SHIPPED");
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusIN_PROGRESS(){

        Status statusEntity = Status.IN_PROGRESS;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        when(orderDao.getAllByStatus(statusEntity)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatus("IN_PROGRESS");
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusDELIVERED() {

        Status statusEntity = Status.DELIVERED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        when(orderDao.getAllByStatus(statusEntity)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatus("DELIVERED");
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testUpdateOrderStatusByIdToPLACED() throws ResourceNotFoundException {

        // Check whether the id exist
        Long id = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);

        when(orderDao.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        OrderEntity result = orderService.getOneOrderById(id);
        assertThat(result.getOrderId()).isEqualTo(orderEntity.getOrderId());

        // Update status after finding the id
        orderEntity.setStatus(Status.PLACED);
        when(orderDao.save(orderEntity)).thenReturn(orderEntity);
        OrderEntity updatedOrderEntity = orderService.updateOrderStatusById(id, "PLACED");
        assertThat(orderEntity.getStatus()).isEqualTo(updatedOrderEntity.getStatus());
    }

    @Test
    public void testUpdateOrderStatusByIdToIN_PROGRESS() throws ResourceNotFoundException {

        // Check whether the id exist
        Long id = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);

        when(orderDao.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        OrderEntity result = orderService.getOneOrderById(id);
        assertThat(result.getOrderId()).isEqualTo(orderEntity.getOrderId());

        // Update status after finding the id
        orderEntity.setStatus(Status.IN_PROGRESS);
        when(orderDao.save(orderEntity)).thenReturn(orderEntity);
        OrderEntity updatedOrderEntity = orderService.updateOrderStatusById(id, "IN_PROGRESS");
        assertThat(orderEntity.getStatus()).isEqualTo(updatedOrderEntity.getStatus());
    }

    @Test
    public void testUpdateOrderStatusByIdToSHIPPED() throws ResourceNotFoundException {

        // Check whether the id exist
        Long id = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);

        when(orderDao.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        OrderEntity result = orderService.getOneOrderById(id);
        assertThat(result.getOrderId()).isEqualTo(orderEntity.getOrderId());

        // Update status after finding the id
        orderEntity.setStatus(Status.SHIPPED);
        when(orderDao.save(orderEntity)).thenReturn(orderEntity);
        OrderEntity updatedOrderEntity = orderService.updateOrderStatusById(id, "SHIPPED");
        assertThat(orderEntity.getStatus()).isEqualTo(updatedOrderEntity.getStatus());
    }

    @Test
    public void testUpdateOrderStatusByIdToDELIVERED() throws ResourceNotFoundException {

        // Check whether the id exist
        Long id = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);

        when(orderDao.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        OrderEntity result = orderService.getOneOrderById(id);
        assertThat(result.getOrderId()).isEqualTo(orderEntity.getOrderId());

        // Update status after finding the id
        orderEntity.setStatus(Status.DELIVERED);
        when(orderDao.save(orderEntity)).thenReturn(orderEntity);
        OrderEntity updatedOrderEntity = orderService.updateOrderStatusById(id, "DELIVERED");
        assertThat(orderEntity.getStatus()).isEqualTo(updatedOrderEntity.getStatus());
    }

    @Test
    public void testGetAllOrdersByStatusPLACEDAndOrderDate() {

        Status statusEntity = Status.PLACED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate date = LocalDate.of(2022, 01, 1);

        when(orderDao.getAllByStatusAndOrderDate(statusEntity, date)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndOrderDate("PLACED", date);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusSHIPPEDAndOrderDate() {

        Status statusEntity = Status.SHIPPED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate date = LocalDate.of(2022, 01, 1);

        when(orderDao.getAllByStatusAndOrderDate(statusEntity,date)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndOrderDate("SHIPPED", date);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusIN_PROGRESSAndOrderDate() {

        Status statusEntity = Status.IN_PROGRESS;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate date = LocalDate.of(2022, 01, 1);

        when(orderDao.getAllByStatusAndOrderDate(statusEntity,date)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndOrderDate("IN_PROGRESS", date);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusDELIVEREDAndOrderDate() {

        Status statusEntity = Status.DELIVERED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate date = LocalDate.of(2022, 01, 1);

        when(orderDao.getAllByStatusAndOrderDate(statusEntity,date)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndOrderDate("DELIVERED", date);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusDELIVEREDAndBetweenOrderDate() {

        Status statusEntity = Status.DELIVERED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate startDate = LocalDate.of(2022, 01, 1);
        LocalDate endDate = LocalDate.of(2023, 01, 12);

        when(orderDao.getAllByStatusAndOrderDateLessThanAndOrderDateGreaterThan(statusEntity, endDate, startDate)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndBetweenDates("DELIVERED", endDate, startDate);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusSHIPPEDAndBetweenOrderDate() {

        Status statusEntity = Status.SHIPPED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate startDate = LocalDate.of(2022, 01, 1);
        LocalDate endDate = LocalDate.of(2023, 01, 12);

        when(orderDao.getAllByStatusAndOrderDateLessThanAndOrderDateGreaterThan(statusEntity, endDate, startDate)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndBetweenDates("SHIPPED", endDate, startDate);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusIN_PROGRESSAndBetweenOrderDate() {

        Status statusEntity = Status.IN_PROGRESS;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate startDate = LocalDate.of(2022, 01, 1);
        LocalDate endDate = LocalDate.of(2023, 01, 12);

        when(orderDao.getAllByStatusAndOrderDateLessThanAndOrderDateGreaterThan(statusEntity, endDate, startDate)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndBetweenDates("IN_PROGRESS", endDate, startDate);
        assertThat(result).isEqualTo(orderEntityList);
    }

    @Test
    public void testGetAllOrdersByStatusPLACEDAndBetweenOrderDate() {

        Status statusEntity = Status.PLACED;

        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntityList.add(orderEntity);

        LocalDate startDate = LocalDate.of(2022, 01, 1);
        LocalDate endDate = LocalDate.of(2023, 01, 12);

        when(orderDao.getAllByStatusAndOrderDateLessThanAndOrderDateGreaterThan(statusEntity, endDate, startDate)).thenReturn(orderEntityList);
        List<OrderEntity> result = orderService.getAllOrdersByStatusAndBetweenDates("PLACED", endDate, startDate);
        assertThat(result).isEqualTo(orderEntityList);
    }
}
