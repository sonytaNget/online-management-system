package com.online.oms.controllers;

import com.online.oms.entities.AddressEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    @PostMapping (consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressEntity createAddress (@RequestBody AddressEntity addressEntity) {

        // Set userId with current login userId
        addressEntity.setUserId(1L);
        addressService.createAddress(addressEntity);
        return addressEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressEntity> getOneAddressById (@PathVariable Long id) {

        AddressEntity addressEntity;
        try {
            addressEntity = addressService.getAddress(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(addressEntity, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<AddressEntity>> getAllAddress() {

        List<AddressEntity> addressEntityEntityList;

        try {
            addressEntityEntityList = addressService.getAllAddress();

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(addressEntityEntityList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        boolean isDelete = addressService.deleteById(id);

        if (isDelete) {
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Id not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAddressById(@RequestBody AddressEntity addressEntity, @PathVariable Long id) {

        try {
            addressService.updateAddressById(addressEntity,id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new  ResponseEntity<>("AddressEntity is successfully updated", HttpStatus.OK);
    }
}
