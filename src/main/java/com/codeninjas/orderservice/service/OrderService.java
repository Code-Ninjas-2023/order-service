package com.codeninjas.orderservice.service;


import com.codeninjas.orderservice.domain.Order;
import com.codeninjas.orderservice.exception.OrderServiceException;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order addOrder(Order Order);

    Order updateOrder (String id ,Order order) throws OrderServiceException;

    void deleteOrder (String id) throws OrderServiceException;

    Order getOrderById (String id) throws OrderServiceException;






}
