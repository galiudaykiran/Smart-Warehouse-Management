package com.smart_warehouse_management.Authentication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_warehouse_management.Authentication.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
	Optional<User> findByMobile(String mobile);    
	boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);

}