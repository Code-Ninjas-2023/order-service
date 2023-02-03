package com.codeninjas.orderservice.service;

import com.codeninjas.orderservice.domain.Order;
import com.codeninjas.orderservice.exception.OrderServiceException;
import com.codeninjas.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public Order addOrder(Order Order) {

        return orderRepository.save(Order);
    }

    @Override
    public Order updateOrder(String id, Order order) throws OrderServiceException {
        Order newOrder = orderRepository.findById(id).orElseThrow(() -> new OrderServiceException("Order not found"));
        newOrder.setOrderId(id);
        newOrder.setOrderStatus(order.getOrderStatus());
        newOrder.setAddressId(order.getAddressId());
        newOrder.setQuantity(order.getQuantity());
        orderRepository.save(newOrder);
        return newOrder;
    }

    @Override
    public void deleteOrder(String id) throws OrderServiceException {
        orderRepository.findById(id).orElseThrow(() -> new OrderServiceException("Order not found"));
        orderRepository.deleteById(id);

    }

    @Override
    public Order getOrderById(String id) throws OrderServiceException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderServiceException("Order not found"));
    }
}
