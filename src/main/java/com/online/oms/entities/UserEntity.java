package com.online.oms.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "avatar")
    private String avatar;

    @OneToMany(cascade=CascadeType.ALL)
    private List<AddressEntity> billingAddressEntityEntities;

    @OneToMany(cascade= CascadeType.ALL)
    private List<AddressEntity> shippingAddressEntityEntities;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<AddressEntity> getBillingAddress() {
        return billingAddressEntityEntities;
    }

    public void setBillingAddress(List<AddressEntity> billingAddressEntityEntities) {
        this.billingAddressEntityEntities = billingAddressEntityEntities;
    }

    public List<AddressEntity> getShippingAddress() {
        return shippingAddressEntityEntities;
    }

    public void setShippingAddress(List<AddressEntity> shippingAddressEntityEntities) {
        this.shippingAddressEntityEntities = shippingAddressEntityEntities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
