package com.online.oms.services;

import com.online.oms.entities.AddressEntity;
import com.online.oms.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IAddressService {

    AddressEntity createAddress(AddressEntity addressEntity);

    AddressEntity getAddress(Long id) throws ResourceNotFoundException;

    List<AddressEntity> getAllAddress() throws ResourceNotFoundException;

    boolean deleteById(Long id);

    AddressEntity updateAddressById(AddressEntity addressEntity, Long id) throws ResourceNotFoundException;
}
