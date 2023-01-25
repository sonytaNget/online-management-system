package com.online.oms.services;

import com.online.oms.entities.ProductEntity;
import com.online.oms.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IProductService {

    ProductEntity addProduct(ProductEntity productEntity);

    boolean deleteProductById(Long id) ;
    
    ProductEntity getProductById(Long id) throws ResourceNotFoundException;

    List<ProductEntity> getAllProducts() throws ResourceNotFoundException;

    ProductEntity updateById(ProductEntity productEntity, Long id) throws ResourceNotFoundException;
}
