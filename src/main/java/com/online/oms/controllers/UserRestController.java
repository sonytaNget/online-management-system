package com.online.oms.controllers;

import com.online.oms.authentication.JwtUtils;
import com.online.oms.entities.UserEntity;
import com.online.oms.exceptions.BadEmailException;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity) {

        try {
            userService.addUser(userEntity);
        } catch (BadEmailException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("UserEntity is successfully added" ,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserEntity userEntity, @PathVariable Long id) {

        try {
            userService.updateUser(userEntity, id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("UserEntity is successfully updated", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserByID(@PathVariable Long id) {

        UserEntity userEntityWithID;

        try {
            userEntityWithID = userService.getUserByID(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userEntityWithID, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UserEntity>> getAllUsers() {

        List<UserEntity> userEntityList;

        try {
            userEntityList = userService.getAllUsers();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userEntityList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {

        boolean isDelete =  userService.deleteUserByID(id);

        if (isDelete) {
            return new ResponseEntity<>("SupplierEntity is successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No user found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserEntity userEntity) {

        try {
            userService.signUpUser(userEntity.getEmail(), userEntity.getPassword(), userEntity.getPhoneNumber());
        } catch (BadEmailException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("userEntity is successfully added" ,HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity userEntity) {

        boolean isLogin;

        try {
            isLogin = userService.login(userEntity.getEmail(), userEntity.getPassword());
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }

        if (isLogin) {
            String token = JwtUtils.generateJwtToken(userEntity);
            return new ResponseEntity<>(token ,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect email or password" ,HttpStatus.NOT_FOUND);
        }
    }
}
