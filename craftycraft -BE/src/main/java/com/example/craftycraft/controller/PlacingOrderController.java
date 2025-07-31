package com.example.craftycraft.controller;

import com.example.craftycraft.entity.PlacingOrderRequest;
import com.example.craftycraft.service.PlacingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api/PlacingOrder")
public class PlacingOrderController {
    @Autowired
    private PlacingOrderService placingorderservice;
    @PostMapping
    public ResponseEntity<String> PlacingOrder(@RequestBody PlacingOrderRequest placingorderrequest){
        placingorderservice.PlacingOrder(placingorderrequest);
        return ResponseEntity.ok("Ordered Confirmed");
    }
}

