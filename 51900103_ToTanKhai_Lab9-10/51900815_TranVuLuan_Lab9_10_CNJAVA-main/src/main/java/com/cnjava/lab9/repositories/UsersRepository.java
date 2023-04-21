package com.cnjava.lab9.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnjava.lab9.models.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{
	Optional<Users> findByEmail(String email);
}
