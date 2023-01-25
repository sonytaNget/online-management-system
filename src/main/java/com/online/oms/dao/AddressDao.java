package com.online.oms.dao;

import com.online.oms.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<AddressEntity, Long> {
}
