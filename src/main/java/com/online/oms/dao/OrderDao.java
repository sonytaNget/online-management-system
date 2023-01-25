package com.online.oms.dao;

import com.online.oms.entities.OrderEntity;
import com.online.oms.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends JpaRepository <OrderEntity, Long> {

    List<OrderEntity> getAllByUserId(Long id);

    List<OrderEntity> getAllByUserIdAndOrderDate(Long id, LocalDate date);

    List<OrderEntity> getAllByOrderDate(LocalDate date);

    List<OrderEntity> getAllByOrderDateLessThanAndOrderDateGreaterThan(LocalDate endDate, LocalDate startDate);

    List<OrderEntity> getAllByStatus(Status statusEntity);

    List<OrderEntity> getAllByStatusAndOrderDate(Status statusEntity, LocalDate orderDate);

    List<OrderEntity> getAllByStatusAndOrderDateLessThanAndOrderDateGreaterThan(Status statusEntity, LocalDate endDate, LocalDate startDate);
}
