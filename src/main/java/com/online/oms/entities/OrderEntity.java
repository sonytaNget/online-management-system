package com.online.oms.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Product_Order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "shipping_address_id")
    private Long shippingAddressId;

    @Column(name = "billing_address_id")
    private Long billingAddressId;

    @OneToMany(mappedBy="orderDetailId", cascade = CascadeType.PERSIST)
    private List<OrderDetail> orderDetail;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDateTime;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private LocalDate orderDate;

    private Status status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Long billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDate) {
        this.createDateTime = createDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
