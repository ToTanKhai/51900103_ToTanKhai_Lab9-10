package com.cnjava.lab9.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnjava.lab9.models.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	@Query(value = """
			select t from Token t inner join Users u\s
			on t.users.id = u.id\s
			where u.id = :id and (t.expired = false or t.revoked = false)\s
			""")
	List<Token> findAllValidTokenByUser(Integer id);

	Optional<Token> findByToken(String token);
}
