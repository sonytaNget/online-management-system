package com.online.oms.services;

import com.online.oms.dao.ProductDao;
import com.online.oms.entities.ProductEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductEntityServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Test
    public void testAddProduct() {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Milk");
        productEntity.setQty(5);

        productService.addProduct(productEntity);
        verify(productDao).save(productEntity);
    }

    @Test
    public void testDeleteProduct() {

        Long id = 1L;

        productService.deleteProductById(id);
        verify(productDao).deleteById(id);
    }

    @Test
    public void testDeleteProductFalse() {

        doThrow(EmptyResultDataAccessException.class).when(productDao).deleteById(anyLong());
        boolean isDelete = productService.deleteProductById(1L);
        assertThat(isDelete).isFalse();
    }

    @Test
    public void testGetOneProduct() throws ResourceNotFoundException {

        Long id = 1L;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setQty(5);

        ProductEntity productEntityWithId = new ProductEntity();
        productEntityWithId.setQty(5);
        productEntityWithId.setName("Dove");
        productEntityWithId.setProductDetail("Detail");
        productEntityWithId.setImage("https://dove.jpg");
        productEntityWithId.setDiscountPercentage(30.0);
        productEntityWithId.setPrice(100);
        productEntityWithId.setType("beauty");

        Optional<ProductEntity> productOptional = Optional.of(productEntityWithId);

        when(productDao.findById(anyLong())).thenReturn(productOptional);
        ProductEntity result = productService.getProductById(id);
        assertThat(result.getProductDetail()).isEqualTo(productEntityWithId.getProductDetail());
        assertThat(result.getImage()).isEqualTo(productEntityWithId.getImage());
        assertThat(result.getName()).isEqualTo(productEntityWithId.getName());
        assertThat(result.getQty()).isEqualTo(productEntityWithId.getQty());
        assertThat(result.getDiscountPercentage()).isEqualTo(productEntityWithId.getDiscountPercentage());
        assertThat(result.getPrice()).isEqualTo(productEntityWithId.getPrice());
        assertThat(result.getType()).isEqualTo(productEntityWithId.getType());
    }

    @Test
    public void testGetProductNotFound() {

        when(productDao.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productService.getProductById(1L);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not found");
        }

    }

    @Test
    public void testGetAllProducts() throws ResourceNotFoundException {

        List<ProductEntity> productEntityList = new ArrayList<>();

        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setType("Beauty");

        ProductEntity productEntity2 = new ProductEntity();
        productEntity1.setType("Personal Care");

        ProductEntity productEntity3 = new ProductEntity();
        productEntity1.setType("Health");

        productEntityList.add(productEntity1);
        productEntityList.add(productEntity2);
        productEntityList.add(productEntity3);

        when(productDao.findAll()).thenReturn(productEntityList);
        List<ProductEntity> result = productService.getAllProducts();
        assertThat(result).isEqualTo(productEntityList);
    }

    @Test
    public void testGetAllProductsEmptyList() throws EmptyResultDataAccessException, ResourceNotFoundException {

        List<ProductEntity> productEntityList = new ArrayList<>();
        when(productDao.findAll()).thenReturn(productEntityList);
        try {
            productService.getAllProducts();
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No product in database");
        }
    }

    @Test
    public void testUpdateProductById() throws ResourceNotFoundException {

        Long id = 1L;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(id);
        ProductEntity productEntityWithExistID = new ProductEntity();

        when(productDao.findById(anyLong())).thenReturn(Optional.of(productEntityWithExistID));

        productService.updateById(productEntity, id);
        verify(productDao).save(productEntity);
    }

    @Test
    public void testUpdateProductByIdNotMatch() {

        Long id = 1L;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(5L);

        ProductEntity newProductEntity = new ProductEntity();
        Optional productOptional = Optional.of(newProductEntity);

        when(productDao.findById(anyLong())).thenReturn(productOptional);

        try {
            productService.updateById(productEntity, id);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not match");
        }
    }

    @Test
    public void testUpdateProductByIdNotExist() {

        Long id = 1L;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(id);

        when(productDao.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productService.updateById(productEntity, id);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not exist");
        }
    }
}
