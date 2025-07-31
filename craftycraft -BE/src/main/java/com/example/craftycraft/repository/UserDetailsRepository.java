package com.example.craftycraft.repository;

import com.example.craftycraft.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetail,Long> {
    Optional<UserDetail> findByMailId(String mailId);
}