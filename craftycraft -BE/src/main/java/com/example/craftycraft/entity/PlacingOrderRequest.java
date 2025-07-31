package com.example.craftycraft.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="ORDER_DETAILS")
@Entity
@Data
public class PlacingOrderRequest {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String mailId;
    private Long quantity;
    private Long price;//price for each product
    private Long totalAmount;//price for total product
    private String Discount;
    private Long finalAmount;//price after discount
}
