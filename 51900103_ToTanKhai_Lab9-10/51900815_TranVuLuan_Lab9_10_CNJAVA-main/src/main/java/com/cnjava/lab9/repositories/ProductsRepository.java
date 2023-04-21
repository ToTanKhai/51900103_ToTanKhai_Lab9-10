package com.cnjava.lab9.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnjava.lab9.models.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer>{
	List<Products> findByName(String name);
}
