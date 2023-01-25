package com.online.oms.services;

import com.online.oms.entities.SupplierEntity;
import com.online.oms.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ISupplierService {

    SupplierEntity addSupplier(SupplierEntity supplierEntity);

    SupplierEntity getSupplier(Long id) throws ResourceNotFoundException;

    List<SupplierEntity> getAllSuppliers() throws ResourceNotFoundException;

    boolean deleteSupplierById(Long id);

    SupplierEntity updateSupplierById(Long id, SupplierEntity supplierEntity) throws ResourceNotFoundException;
}
