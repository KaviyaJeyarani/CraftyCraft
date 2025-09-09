//package com.example.craftycraft.entity;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Table
//@Entity
//public class ProductsDetail {
//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    private Long id;
//    private String productName;
//    private Long amountPerProduct;
//    private Long quantity;
//    private String status;
//    private String imageUrl;
//
//    @ManyToOne
//    @JoinColumn(name="categoryId")
//    private ProdCategory prodCategory;
//
//    private String seller;//modify it later by sellerId
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//}
