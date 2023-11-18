package com.cst438.domain;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
	  
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(@Param("userId") int userId);
}
