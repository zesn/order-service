package com.zesn.karate;

import com.intuit.karate.junit5.Karate;
import com.zesn.orderservice.OrderServiceApplication;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.AbstractEnvironment;


public class KarateTestRunner {

    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:com/zesn/karate/SampleTest.feature");
    }
    @BeforeAll
    public static void setUpApp(){
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        SpringApplication.run(OrderServiceApplication.class, "this");
    }
}
