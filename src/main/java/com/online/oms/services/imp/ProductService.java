package com.online.oms.services.imp;

import com.online.oms.dao.ProductDao;
import com.online.oms.entities.ProductEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public ProductEntity addProduct(ProductEntity productEntity) {
        return productDao.save(productEntity);
    }

    @Override
    public boolean deleteProductById(Long id) {

        try {
            productDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public ProductEntity getProductById(Long id) throws ResourceNotFoundException {

        Optional isFound = productDao.findById(id);

        if (isFound.isPresent()) {
            return (ProductEntity) isFound.get();
        } else {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    public List<ProductEntity> getAllProducts() throws ResourceNotFoundException {

        List<ProductEntity> productEntityList = productDao.findAll();

        if (productEntityList.isEmpty()) {
            throw new ResourceNotFoundException("No product in database");
        }
        return productEntityList;
    }

    @Override
    public ProductEntity updateById(ProductEntity productEntity, Long id) throws ResourceNotFoundException {

        Optional productWithId = productDao.findById(id);

        if (productEntity.getProductId() != id) {
            throw new ResourceNotFoundException("Id not match");
        } else if (!productWithId.isPresent()){
            throw new ResourceNotFoundException("Id not exist");
        }
        productDao.save(productEntity);
        return productEntity;
    }


}
