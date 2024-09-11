package com.zesn.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDao orderDao;

    @Test
    public void testHealthCheck() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am healthy"));
    }

    @Test
    public void testFetchOrders() throws Exception {
        // Mock data
        Order order1 = new Order(1, "Laptop", 10, 1200L);
        Order order2 = new Order(2, "Phone", 5, 800L);
        Order order3 = new Order(3, "Monitor",7, 300L);

        List<Order> orders = Arrays.asList(order1, order2, order3);

        // Mock the behavior of orderDao.getOrders()
        given(orderDao.getOrders()).willReturn(orders);

        // Test the /orders endpoint
        ResultActions result = mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(3)))  // Order by price: Monitor
                .andExpect(jsonPath("$[0].name", is("Monitor")))
                .andExpect(jsonPath("$[0].quantity", is(7)))
                .andExpect(jsonPath("$[0].price", is(300)))
                .andExpect(jsonPath("$[1].id", is(2)))  // Order by price: Phone
                .andExpect(jsonPath("$[1].name", is("Phone")))
                .andExpect(jsonPath("$[1].quantity", is(5)))
                .andExpect(jsonPath("$[1].price", is(800)))
                .andExpect(jsonPath("$[2].id", is(1)))  // Order by price: Laptop
                .andExpect(jsonPath("$[2].name", is("Laptop")))
                .andExpect(jsonPath("$[2].quantity", is(10)))
                .andExpect(jsonPath("$[2].price", is(1200)));
    }
}

