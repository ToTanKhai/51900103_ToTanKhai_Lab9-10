package com.cnjava.lab9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnjava.lab9.models.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer>{

}
