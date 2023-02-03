package com.codeninjas.orderservice.controller;

import com.codeninjas.orderservice.domain.Order;
import com.codeninjas.orderservice.service.OrderService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class OrderControllerTest {
    //    @Autowired
    @Qualifier
    MockMvc mock;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGetOrderById() throws Exception {
        Mockito.when(orderService.getOrderById("5431")).thenReturn(new Order("5431", "6754", "765", "seattle 2567", 5, "Delivered"));
        mock.perform(MockMvcRequestBuilders.get("/v1/orders/5431"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value("5431"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("6754"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("765"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressId").value("seattle 2567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value("Delivered"))

        ;
    }

    @Test
    public void testDeleteOrderById() throws Exception {
        mock.perform(MockMvcRequestBuilders.delete("/v1/orders/{id}", 1213))
                .andExpect(status().isOk());
        verify(orderService, times(1)).deleteOrder("1213");
    }

    @Test
    public void testAddOrder() throws Exception {
        Order order = new Order("5431", "6754", "765", "seattle 2567", 5, "Delivered");
        mock.perform(MockMvcRequestBuilders.post("/v1/orders")
                        .content(asJsonString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(orderService, times(1)).addOrder(order);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Order order = new Order("5431", "6754", "765", "seattle 2567", 5, "Delivered");
        mock.perform(MockMvcRequestBuilders.put("/v1/orders/{id}", 1213)
                        .content(asJsonString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(orderService, times(1)).updateOrder("1213", order);
    }


    @Test
    public void testGetAllOrders() throws Exception {

        List<Order> ordersList = new ArrayList<>();
        ordersList.add(new Order("5431", "6754", "765", "seattle 2567", 5, "Delivered"));
        ordersList.add(new Order("5432", "6755", "766", "lynwood 2567", 8, "pending"));
        Mockito.when(orderService.getAllOrders()).thenReturn(ordersList);
        mock.perform(MockMvcRequestBuilders.get("/v1/orders")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(ordersList.size()));
        verify(orderService, times(1)).getAllOrders();
    }
}