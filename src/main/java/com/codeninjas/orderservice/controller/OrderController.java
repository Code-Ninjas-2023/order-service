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
import java.util.Objects;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> response = orderService.getAllOrders();
        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order response = orderService.addOrder(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) throws OrderServiceException {
        Order response = orderService.getOrderById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

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