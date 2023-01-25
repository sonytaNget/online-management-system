package com.online.oms.entities;

public enum Status {

    PLACED("1"),
    IN_PROGRESS("2"),
    SHIPPED("3"),
    DELIVERED("4");
    String value;

    Status(String value) {
        this.value = value;
    }
}
