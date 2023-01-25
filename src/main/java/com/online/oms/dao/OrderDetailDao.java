package com.online.oms.dao;

import com.online.oms.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Long> {

}
