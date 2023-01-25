package com.online.oms.services.imp;

import com.online.oms.dao.SupplierDao;
import com.online.oms.entities.SupplierEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements ISupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public SupplierEntity addSupplier(SupplierEntity supplierEntity) {

        supplierDao.save(supplierEntity);
        return supplierEntity;
    }

    @Override
    public SupplierEntity getSupplier(Long id) throws ResourceNotFoundException {

        Optional supplierWithId = supplierDao.findById(id);

        if (supplierWithId.isPresent()) {
            return (SupplierEntity) supplierWithId.get();
        } else {
            throw new ResourceNotFoundException("id not found");
        }
    }

    @Override
    public List<SupplierEntity> getAllSuppliers() throws ResourceNotFoundException {

        List<SupplierEntity> supplierEntityList = supplierDao.findAll();

        if (supplierEntityList.isEmpty()) {
            throw new ResourceNotFoundException("No supplier in Database");
        }

        return supplierEntityList;
    }

    @Override
    public boolean deleteSupplierById(Long id) {

        try {
            supplierDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
    }

    @Override
    public SupplierEntity updateSupplierById(Long id, SupplierEntity supplierEntity) throws ResourceNotFoundException {

        Optional supplierWithId = supplierDao.findById(id);

        if (supplierEntity.getSupplierId() != id) {
            throw new ResourceNotFoundException("Id not match");
        } else if (!supplierWithId.isPresent()){
            throw new ResourceNotFoundException("Id not exist");
        }
        supplierDao.save(supplierEntity);
        return supplierEntity;
    }

}
