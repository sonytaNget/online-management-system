package com.online.oms.services;

import com.online.oms.entities.UserEntity;
import com.online.oms.exceptions.BadEmailException;
import com.online.oms.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUserService{

    UserEntity addUser(UserEntity userEntity) throws BadEmailException;
    UserEntity updateUser(UserEntity userEntity, Long id) throws ResourceNotFoundException;

    UserEntity getUserByID(Long id) throws ResourceNotFoundException;

    List<UserEntity> getAllUsers() throws ResourceNotFoundException;

    boolean deleteUserByID(Long id) throws ResourceNotFoundException;

    UserEntity signUpUser(String email, String password, String phoneNumber) throws BadEmailException;

    boolean login(String email, String password) throws ResourceNotFoundException;
}
