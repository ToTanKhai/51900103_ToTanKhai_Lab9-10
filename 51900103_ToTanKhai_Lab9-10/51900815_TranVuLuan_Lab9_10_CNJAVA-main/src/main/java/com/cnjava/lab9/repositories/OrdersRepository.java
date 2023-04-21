package com.cnjava.lab9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnjava.lab9.models.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{

}
