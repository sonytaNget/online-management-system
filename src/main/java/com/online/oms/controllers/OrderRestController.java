package com.online.oms.controllers;

import com.online.oms.entities.OrderEntity;
import com.online.oms.exceptions.ResourceNotFoundException;
import com.online.oms.services.imp.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderEntity createOrder(@RequestBody OrderEntity orderEntity) {

        orderService.makeOrder(orderEntity);
        return orderEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) throws ResourceNotFoundException {

        boolean isDelete = orderService.deleteOrderById(id);

        if (isDelete) {
            return new ResponseEntity<>("OrderEntity is successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {

        OrderEntity orderEntity;

        try {
            orderEntity = orderService.getOneOrderById(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orderEntity, HttpStatus.OK);

    }

    @GetMapping()
    public ResponseEntity<List<OrderEntity>> getAllOrder() {

        List<OrderEntity> orderEntityList;

        try {
            orderEntityList = orderService.getAllOrder();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<OrderEntity>> getAllOrdersByCustomerId(@PathVariable Long id) {

        List<OrderEntity> orderEntityList = orderService.getAllOrderHistoryByCustomerId(id);;

        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}/search")
    public ResponseEntity<List<OrderEntity>> getAllOrdersByCustomerIdAndDate(@PathVariable Long id, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<OrderEntity> orderEntityList = orderService.getAllOrderHistoryByCustomerIdAndDate(id, date);
        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderEntity>> getAllOrdersByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<OrderEntity> orderEntityList = orderService.getAllOrdersByDate(date);
        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }

    @GetMapping("/search-by-date-range")
    public ResponseEntity<List<OrderEntity>> getAllOrdersBetweenDates(@RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                      @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        List<OrderEntity> orderEntityList = orderService.getAllOrdersBetweenDates(endDate.plusDays(1), startDate.minusDays(1));
        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrderStatusById(@PathVariable Long id, @RequestBody String status) throws ResourceNotFoundException {

        OrderEntity orderEntity = orderService.updateOrderStatusById(id, status);
        return new ResponseEntity<>(orderEntity, HttpStatus.OK);
    }

    @GetMapping("/search-by")
    public ResponseEntity<List<OrderEntity>> getAllOrdersByStatus(@RequestParam("status") String status) {

        List<OrderEntity> orderEntityList = orderService.getAllOrdersByStatus(status);
        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }

    @GetMapping("/search-by-status-and-date")
    public ResponseEntity<List<OrderEntity>> getAllOrdersByStatusAndOrderDate(@RequestParam("status") String status,
                                                                              @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate orderDate) {

        List<OrderEntity> orderEntityList = orderService.getAllOrdersByStatusAndOrderDate(status, orderDate);
        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }

    @GetMapping("/search-by-status-and-between")
    public ResponseEntity<List<OrderEntity>> getAllOrdersByStatusAndBetweenDates(@RequestParam String status,
                                                                                 @RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                                 @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        List<OrderEntity> orderEntityList = orderService.getAllOrdersByStatusAndBetweenDates(status,
                endDate.plusDays(1),
                startDate.minusDays(1));
        return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
    }
}
