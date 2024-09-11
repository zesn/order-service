package com.zesn.karate;

import com.intuit.karate.junit5.Karate;
import com.zesn.orderservice.OrderServiceApplication;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;


public class KarateTestRunner {

    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:com/zesn/karate/SampleTest.feature");
    }
    @BeforeAll
    public static void setUpApp(){
        SpringApplication.run(OrderServiceApplication.class, "this");
    }
}
