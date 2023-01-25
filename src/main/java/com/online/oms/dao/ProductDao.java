package com.online.oms.dao;

import com.online.oms.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<ProductEntity, Long> {

}
