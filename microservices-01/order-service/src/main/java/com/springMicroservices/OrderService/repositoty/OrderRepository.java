package com.springMicroservices.OrderService.repositoty;

import com.springMicroservices.OrderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
