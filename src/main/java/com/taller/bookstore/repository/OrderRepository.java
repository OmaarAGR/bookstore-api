package com.taller.bookstore.repository;

import com.taller.bookstore.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"items", "items.book", "user"})
    List<Order> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"items", "items.book", "user"})
    List<Order> findAll();
}
