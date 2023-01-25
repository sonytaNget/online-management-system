package com.online.oms.services;

import com.online.oms.dao.AddressDao;
import com.online.oms.entities.AddressEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.AddressService;
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
public class AddressEntityEntityServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressDao addressDao;

    @Test
    public void testCreateAddress() {

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setUserId(1L);

        addressService.createAddress(addressEntity);
        verify(addressDao).save(addressEntity);
    }

    @Test
    public void testGetAddressById() throws ResourceNotFoundException {

        Long id = 1L;
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setUserId(id);

        when(addressDao.findById(id)).thenReturn(Optional.of(addressEntity));
        AddressEntity result = addressService.getAddress(id);
        assertThat(result.getAddressId()).isEqualTo(addressEntity.getAddressId());
    }

    @Test
    public void testGetAddressByIdNotFound() {

        when(addressDao.findById(anyLong())).thenReturn(Optional.empty());

        try {
            addressService.getAddress(1L);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not found");
        }
    }

    @Test
    public void testGetAllAddress() throws ResourceNotFoundException {

        AddressEntity addressEntity = new AddressEntity();
        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(addressEntity);

        when(addressDao.findAll()).thenReturn(addressEntityList);
        List<AddressEntity> addressEntities = addressService.getAllAddress();
        assertThat(addressEntities).isEqualTo(addressEntityList);

    }

    @Test
    public void testGetAllAddressEmptyList() {

        List<AddressEntity> addressEntityEmptyList = new ArrayList<>();
        when(addressDao.findAll()).thenReturn(addressEntityEmptyList);

        try {
            addressService.getAllAddress();
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No address in database");
        }
    }

    @Test
    public void updateAddressById() throws ResourceNotFoundException {

        Long id = 1L;

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressId(id);

        when(addressDao.findById(id)).thenReturn(Optional.of(addressEntity));

        addressService.updateAddressById(addressEntity, id);
        verify(addressDao).save(addressEntity);
    }

    @Test
    public void updateAddressByIdNotMatch() {

        Long id = 1L;

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressId(5L);

        when(addressDao.findById(id)).thenReturn(Optional.of(addressEntity));

        try {
            addressService.updateAddressById(addressEntity, id);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not match");
        }
    }

    @Test
    public void updateAddressByIdNotFound() {

        when(addressDao.findById(anyLong())).thenReturn(Optional.empty());
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressId(1L);

        try {
            addressService.updateAddressById(addressEntity, 1L);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Id not found");
        }
    }

    @Test
    public void testDeleteAddress() {

        addressService.deleteById(1L);
        verify(addressDao).deleteById(1L);
    }

    @Test
    public void testDeleteAddressNotFound() {

        doThrow(EmptyResultDataAccessException.class).when(addressDao).deleteById(anyLong());
        boolean isDelete = addressService.deleteById(1L);
        assertThat(isDelete).isFalse();

    }
}
