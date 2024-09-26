package com.zesn.orderservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class Order {

    private int id;
    private String name;
    private int quantity;
    private long price;
}
