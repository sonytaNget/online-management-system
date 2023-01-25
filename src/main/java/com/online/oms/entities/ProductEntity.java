package com.online.oms.entities;

import jakarta.persistence.*;

@Entity(name = "Product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "qty")
    private int qty;
    @Column(name = "image")
    private String image;
    @Column(name = "name")
    private String name;
    @Column(name = "product_detail")
    private String productDetail;
    @Column(name = "price")
    private double price;
    @Column(name = "discount_percentage")
    private double discountPercentage;
    @Column(name = "type")
    private String type;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
