package com.example.craftycraft.repository;

import com.example.craftycraft.entity.PlacingOrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacingOrderRepository extends JpaRepository<PlacingOrderRequest,Long> {
}
