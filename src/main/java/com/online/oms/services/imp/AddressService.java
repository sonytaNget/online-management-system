package com.online.oms.services.imp;

import com.online.oms.dao.AddressDao;
import com.online.oms.entities.AddressEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public AddressEntity createAddress(AddressEntity addressEntity) {

        AddressEntity saveAddressEntityEntity = addressDao.save(addressEntity);

        return saveAddressEntityEntity;
    }

    @Override
    public AddressEntity getAddress(Long id) throws ResourceNotFoundException {

        Optional address = addressDao.findById(id);

        if (address.isPresent()) {
            return (AddressEntity) address.get();
        } else {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Override
    public List<AddressEntity> getAllAddress() throws ResourceNotFoundException {

        List<AddressEntity> addressEntityEntityList = addressDao.findAll();

        if (addressEntityEntityList.isEmpty()) {
            throw new ResourceNotFoundException("No address in database");
        }

        return addressEntityEntityList;
    }

    @Override
    public boolean deleteById(Long id) {

        try {
            addressDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public AddressEntity updateAddressById(AddressEntity addressEntity, Long id) throws ResourceNotFoundException {

        Long addressId = addressEntity.getAddressId();
        Optional addressIdFromDb = addressDao.findById(id);

        if (addressId != id) {
            throw new ResourceNotFoundException("Id not match");
        } else if (addressIdFromDb.isEmpty()) {
            throw new ResourceNotFoundException("Id not found");
        } else {
            addressDao.save(addressEntity);
        }
        return addressEntity;
    }
}
