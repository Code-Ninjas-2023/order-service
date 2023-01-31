package com.codeninjas.orderservice.controller;

import com.codeninjas.orderservice.domain.Order;
import com.codeninjas.orderservice.exception.OrderServiceException;
import com.codeninjas.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrder();
    }

    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable String id) throws OrderServiceException {
        return orderService.getOrderById(id);

    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteById(@PathVariable String id) throws OrderServiceException {
        orderService.deleteOrder(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateById(@PathVariable String id, @Valid @RequestBody Order order) throws OrderServiceException {
        orderService.updateOrder(id, order);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}