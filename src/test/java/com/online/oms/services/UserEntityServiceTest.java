package com.online.oms.services;

import com.online.oms.dao.UserDao;
import com.online.oms.entities.AddressEntity;
import com.online.oms.entities.UserEntity;
import com.online.oms.exceptions.BadEmailException;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.AddressService;
import com.online.oms.services.imp.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserEntityServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private AddressService addressService;

    @Test
    public void testAddUser() throws BadEmailException {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Lulu");

        AddressEntity addressEntity = new AddressEntity();
        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(addressEntity);
        userEntity.setBillingAddress(addressEntityList);
        userEntity.setShippingAddress(addressEntityList);


        when (userDao.findUserByEmail(anyString())).thenReturn(userEntity);

        when(userDao.save(userEntity)).thenReturn(userEntity);

        when(addressService.createAddress(addressEntity)).thenReturn(addressEntity);

        UserEntity resultUserEntity = userService.addUser(userEntity);
        assertThat(resultUserEntity).isEqualTo(userEntity);
    }

    @Test
    public void addUserWithSameEmail() {

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jiji@gmail.com");

        UserEntity userEntityWithSameEmail = new UserEntity();

        when(userDao.findUserByEmail(anyString())).thenReturn(userEntityWithSameEmail);

        try {
            userService.addUser(userEntity);
        } catch (BadEmailException e) {
            verify(userDao, never()).save(userEntity);
        }
    }

    @Test
    public void updateUser() throws ResourceNotFoundException {

        Long id = 1L;

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(id);

        UserEntity userEntityWithExistID = new UserEntity();

        when(userDao.findUserByUserId(anyLong())).thenReturn(userEntityWithExistID);

        userService.updateUser(userEntity, id);
        verify(userDao).save(userEntity);
    }

    @Test
    public void updateUserNoneExistID() {

        Long id = 1L;

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(id);

        when(userDao.findUserByUserId(anyLong())).thenReturn(null);

        try {
            userService.updateUser(userEntity, id);
        } catch (ResourceNotFoundException e) {
            verify(userDao, never()).save(userEntity);
        }
    }

    @Test
    public void updateUserNotMatchID() {

        Long id = 1L;

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(2L);

        UserEntity userEntityNotMatchID = new UserEntity();

        when(userDao.findUserByUserId(anyLong())).thenReturn(userEntityNotMatchID);

        try {
            userService.updateUser(userEntity, id);
        } catch (ResourceNotFoundException e) {
            verify(userDao, never()).save(userEntity);
        }
    }

    @Test
    public void getUserWithID() throws ResourceNotFoundException {

        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(id);

        UserEntity userEntityWithID = new UserEntity();
        userEntityWithID.setUserId(id);
        userEntityWithID.setFirstName("Mickey");
        userEntityWithID.setLastName("Mouse");
        userEntityWithID.setEmail("mickey@gmail.com");
        userEntityWithID.setAvatar("https://mouse.jpg");
        userEntityWithID.setPhoneNumber("0129310203");

        Optional<UserEntity> userOptional = Optional.of(userEntityWithID);

        when(userDao.findById(anyLong())).thenReturn(userOptional);

        UserEntity result = userService.getUserByID(id);
        assertThat(result.getUserId()).isEqualTo(userEntityWithID.getUserId());
        assertThat(result.getEmail()).isEqualTo(userEntityWithID.getEmail());
        assertThat(result.getPhoneNumber()).isEqualTo(userEntityWithID.getPhoneNumber());
        assertThat(result.getAvatar()).isEqualTo(userEntityWithID.getAvatar());
        assertThat(result.getFirstName()).isEqualTo(userEntityWithID.getFirstName());
        assertThat(result.getLastName()).isEqualTo(userEntityWithID.getLastName());

    }

    @Test
    public void getUserNoneExistID() {

        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(id);

        when(userDao.findById(anyLong())).thenReturn(Optional.empty());

        try {
            userService.getUserByID(id);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("ID not found");
        }
    }

    @Test
    public void getAllUsers() throws ResourceNotFoundException {

        UserEntity userEntity1 = new UserEntity();
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity1);

        when(userDao.findAll()).thenReturn(userEntityList);
        List<UserEntity> result =  userService.getAllUsers();
        assertThat(result).isEqualTo(userEntityList);
    }

    @Test
    public void getAllUsersWithEmptyList()  {

        List<UserEntity> userEntityList = new ArrayList<>();

        when(userDao.findAll()).thenReturn(userEntityList);
        try {
            userService.getAllUsers();
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No UserEntity in Database");
        }
    }

    @Test
    public void deleteUserByID(){

        boolean isDelete = userService.deleteUserByID(1L);
        assertThat(isDelete).isTrue();
    }

    @Test
    public void deleteUserByIDNotFound() {

        doThrow(EmptyResultDataAccessException.class).when(userDao).deleteById(anyLong());
        boolean isDelete = userService.deleteUserByID(1L);
        assertThat(isDelete).isFalse();
    }

    @Test
    public void testSignUpUser() throws BadEmailException {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Lulu");
        userEntity.setEmail("lili@gmail.com");
        userEntity.setPassword("lulu123");
        userEntity.setPhoneNumber("090976788");

        when(userDao.findUserByEmail(anyString())).thenReturn(null);
        when(userDao.save(userEntity)).thenReturn(userEntity);
        userService.signUpUser("loli@gmail.com", "popo09", "09864645");
    }

    @Test
    public void testSignUpUserWithExistingEmail() {

        UserEntity userEntity = new UserEntity();

        UserEntity userEntityWithExistEmail = new UserEntity();

        when(userDao.findUserByEmail(anyString())).thenReturn(userEntityWithExistEmail);
        when(userDao.save(userEntity)).thenReturn(userEntity);

        try {
            userService.signUpUser("loli@gmail.com", "popo09", "09864645");
        } catch (BadEmailException e) {
            assertThat(e.getMessage()).isEqualTo("Email already exists");
        }
    }

    @Test
    public void testLogin() throws ResourceNotFoundException {

        UserEntity userEntity = new UserEntity();

        when(userDao.findUserByEmailAndAndPassword(anyString(), anyString())).thenReturn(userEntity);
        boolean result = userService.login("kuku@gmail.com", "kiki090");
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testLoginNotFound() throws ResourceNotFoundException {

        when(userDao.findUserByEmailAndAndPassword(anyString(), anyString())).thenReturn(null);
        boolean result = userService.login("kuku@gmail.com", "kiki090");
        assertThat(result).isEqualTo(false);
    }

}
