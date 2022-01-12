package com.woo.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woo.blog.model.User;


public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}
