package com.online.oms.controllers;

import com.online.oms.entities.OrderDetail;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderdetails")
public class OrderDetailRestController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping
    public OrderDetail makeOrderDetail(@RequestBody OrderDetail orderDetail) {
        orderDetailService.makeOrderDetail(orderDetail);
        return orderDetail;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        boolean isDelete = orderDetailService.deleteById(id);

        if (isDelete) {
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Id not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOneOrderDetail(@PathVariable Long id) {

        OrderDetail orderDetail;

        try {
            orderDetail = orderDetailService.getOrderDetail(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

         return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDetail>> getAllOrderDetail() {

        List<OrderDetail> orderDetailList;

        try {
            orderDetailList = orderDetailService.getAllOrderDetail();

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orderDetailList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductDetailById(@RequestBody OrderDetail orderDetail, @PathVariable Long id) {

        try {
            orderDetailService.updateOrderDetailById(orderDetail,id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new  ResponseEntity<>("OrderDetail is successfully updated", HttpStatus.OK);
    }
}
