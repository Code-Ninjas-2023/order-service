package com.codeninjas.orderservice.service;

import com.codeninjas.orderservice.domain.Order;
import com.codeninjas.orderservice.exception.OrderServiceException;
import com.codeninjas.orderservice.repository.OrderRepository;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;


    @Before
    public void setUp() {
        order = new Order();
        order.setOrderId("5677");
        order.setOrderStatus("Delivered");
        order.setAddressId("1765 seattle");
        order.setQuantity(6);
        order.setUserId("7");
        order.setProductId("567");
    }

    @Test
    public void getAllOrder() {
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);
        orderService.getAllOrders();
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void addOrder() {
        when(orderRepository.save(any())).thenReturn(order);
        Order response = orderService.addOrder(order);
        verify(orderRepository, times(1)).save(any());
        assertEquals(response, order);
    }


@Test
public void givenUpdateOrderIsSuccessful() throws OrderServiceException {
        Order newOrder = new Order();
        Mockito.when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));
    newOrder.setOrderId("5677");
    newOrder.setOrderStatus("Delivered");
    newOrder.setAddressId("1765 seattle");
    newOrder.setQuantity(6);
    newOrder.setUserId("7");
    newOrder.setProductId("567");
        Order response = orderService.updateOrder("5677", newOrder);
    assertEquals(response, newOrder);
    }

    @Test
    public void givenUpdateOrderThrowsException() throws OrderServiceException {
        Mockito.when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> {
            orderService.updateOrder(any(), order);
        });

    }

    @Test
    public void givenDeleteOrderIsSuccessful() throws OrderServiceException {
        String orderId = "1123";
        Mockito.when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));
        orderService.deleteOrder(orderId);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void getOrderByIdIsSuccessful() throws OrderServiceException {
        String OrderId = "5677";
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(order));
        orderService.getOrderById(OrderId);
        verify(orderRepository, times(1)).findById(OrderId);
    }

    @Test
    public void getOrderByIdThrowsException() throws OrderServiceException {
        when(orderRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> {
            orderService.getOrderById(anyString());
        });
    }
}