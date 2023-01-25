package com.online.oms.entities;

import jakarta.persistence.*;

@Entity(name = "OrderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "price")
    private Long price;
    @Column(name = "qty")
    private int qty;

    @Column(name = "order_id")
    private Long orderId;


    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
