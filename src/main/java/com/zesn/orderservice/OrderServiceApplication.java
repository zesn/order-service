package com.zesn.orderservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Log4j2
@SpringBootApplication
@RestController
public class OrderServiceApplication {

    @Autowired
    private OrderDao orderDao;

    @GetMapping("/health")
    public String healthCheck() {
        log.info("Hello from healthCheck");
        return "I am healthy";
    }

    @GetMapping("/orders")
    public List<Order> fetchOrders() {
        log.info("Hello from orders");
        return orderDao.getOrders().stream().
                sorted(Comparator.comparing(Order::getPrice)).toList();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
