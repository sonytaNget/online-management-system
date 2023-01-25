package com.online.oms.dao;

import com.online.oms.entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDao extends JpaRepository<SupplierEntity, Long> {
}
