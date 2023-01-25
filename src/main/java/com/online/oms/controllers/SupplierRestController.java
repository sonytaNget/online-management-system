package com.online.oms.controllers;


import com.online.oms.entities.SupplierEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierRestController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierEntity> createSupplier(@RequestBody SupplierEntity supplierEntity) {

        supplierService.addSupplier(supplierEntity);
        return new ResponseEntity<>(supplierEntity, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierEntity> getSupplierById(@PathVariable Long id) {

        SupplierEntity supplierEntity;

        try {
            supplierEntity = supplierService.getSupplier(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(supplierEntity, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SupplierEntity>> getAllSuppliers() {

        List<SupplierEntity> supplierEntityList;

        try {
            supplierEntityList = supplierService.getAllSuppliers();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(supplierEntityList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplierById(@PathVariable Long id) {

        boolean isDelete =  supplierService.deleteSupplierById(id);

        if (isDelete) {
            return new ResponseEntity<>("UserEntity is successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No user found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSupplierById(@RequestBody SupplierEntity supplierEntity, @PathVariable Long id) {

        try {
            supplierService.updateSupplierById(id, supplierEntity);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("SupplierEntity is successfully updated", HttpStatus.CREATED);
    }

}
