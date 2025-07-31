package com.example.craftycraft.service;

import com.example.craftycraft.entity.PlacingOrderRequest;
import com.example.craftycraft.repository.PlacingOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlacingOrderService {
    @Autowired
    PlacingOrderRepository placingorderrepository;
    @Transactional
    public void PlacingOrder(PlacingOrderRequest placingorderrequest){
        placingorderrepository.save(placingorderrequest);
    }
}
