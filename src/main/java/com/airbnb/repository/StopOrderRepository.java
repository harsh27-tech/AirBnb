package com.airbnb.repository;

import com.airbnb.entity.StopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopOrderRepository extends JpaRepository<StopOrder, Long> {
}