package com.online.oms.services.imp;

import com.online.oms.dao.UserDao;
import com.online.oms.entities.AddressEntity;
import com.online.oms.entities.UserEntity;
import com.online.oms.exceptions.BadEmailException;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressService addressService;

    @Override
    public UserEntity addUser(UserEntity userEntity) throws BadEmailException {

        UserEntity userEntityWithSameEmail = userDao.findUserByEmail(userEntity.getEmail());

        if (userEntityWithSameEmail != null) {
            throw new BadEmailException("Email already exists");
        }

        UserEntity newUserEntity = userDao.save(userEntity);

        for (AddressEntity billingAddressEntityEntity : newUserEntity.getBillingAddress()) {
            billingAddressEntityEntity.setUserId(userEntity.getUserId());
            addressService.createAddress(billingAddressEntityEntity);
        }

        for (AddressEntity shippingAddressEntityEntity : newUserEntity.getShippingAddress()) {
            shippingAddressEntityEntity.setUserId(userEntity.getUserId());
            addressService.createAddress(shippingAddressEntityEntity);
        }

        return newUserEntity;
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity, Long id) throws ResourceNotFoundException {

        UserEntity userEntityWithID = userDao.findUserByUserId(userEntity.getUserId());

        if (id != userEntity.getUserId()) {
            throw new ResourceNotFoundException("ID not match");
        } else if (userEntityWithID == null) {
            throw new ResourceNotFoundException("Wrong UserEntity ID");
        }
        return userDao.save(userEntity);
    }

    @Override
    public UserEntity getUserByID(Long id) throws ResourceNotFoundException {

        Optional result = userDao.findById(id);

        if (result.isPresent()) {
            return (UserEntity) result.get();
        } else {
            throw new ResourceNotFoundException("ID not found");
        }
    }

    @Override
    public List<UserEntity> getAllUsers() throws ResourceNotFoundException {

        List<UserEntity> userEntityList = userDao.findAll();

        if (userEntityList.isEmpty()) {
            throw new ResourceNotFoundException("No UserEntity in Database");
        }
        return userEntityList;
    }

    @Override
    public boolean deleteUserByID(Long id) {

        try {
            userDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
    }

    @Override
    public UserEntity signUpUser(String email, String password, String phoneNumber) throws BadEmailException {

        UserEntity userEntityWithSameEmail = userDao.findUserByEmail(email);

        if (userEntityWithSameEmail != null) {
            throw new BadEmailException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setPassword(password);
        userEntity.setEmail(email);

        userDao.save(userEntity);

        return userEntity;
    }

    @Override
    public boolean login(String email, String password) {

        UserEntity userEntity = userDao.findUserByEmailAndAndPassword(email, password);

        if (userEntity == null) {
            return false;
        }

        return true;
    }

}
