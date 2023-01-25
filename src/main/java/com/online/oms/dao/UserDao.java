package com.online.oms.dao;

import com.online.oms.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findUserByEmail (String email);
    UserEntity findUserByUserId (Long id);

    UserEntity findUserByEmailAndAndPassword(String email, String password);
}

