package com.online.oms.controllers;

import com.online.oms.entities.ProductEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.models.request.ProductRequest;
import com.online.oms.models.response.ProductResponse;
import com.online.oms.services.imp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {


        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(productRequest.getProductId());
        productEntity.setProductDetail(productRequest.getProductDetail());
        productEntity.setName(productRequest.getName());
        productEntity.setImage(productRequest.getImage());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setQty(productRequest.getQty());
        productEntity.setDiscountPercentage(productRequest.getDiscountPercentage());
        productEntity.setType(productRequest.getType());

        ProductEntity productDB = productService.addProduct(productEntity);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(productDB.getProductId());
        productResponse.setProductDetail(productDB.getProductDetail());
        productResponse.setName(productDB.getName());
        productResponse.setImage(productDB.getImage());
        productResponse.setPrice(productDB.getPrice());
        productResponse.setQty(productDB.getQty());
        productResponse.setDiscountPercentage(productDB.getDiscountPercentage());
        productResponse.setType(productDB.getType());

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductByID(@PathVariable Long id) {

        boolean isDelete = productService.deleteProductById(id);

        if (isDelete) {
            return new ResponseEntity<>("The product has been successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ID not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> findProductById(@PathVariable Long id) {

        ProductEntity productEntity;

        try {
            productEntity = productService.getProductById(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productEntity, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductEntity>> findAllProducts() {

        List<ProductEntity> productEntityList;

        try {
            productEntityList = productService.getAllProducts();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productEntityList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductById(@RequestBody ProductEntity productEntity, @PathVariable Long id) {

        try {
            productService.updateById(productEntity, id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("ProductEntity is successfully updated", HttpStatus.CREATED);
    }
}
