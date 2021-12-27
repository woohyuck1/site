package com.woo.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woo.blog.model.User;

//DAO 
//자동으로 bean 등록됨
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{
	//select * from user where nsername=1?;
	Optional<User> findByUsername(String username);
}



 

//jpa nameing 쿼리
	//Select * from user where userename = ?1 and password =?2;
//User findByUsernameAndPassword(String username , String password);

//@Query(value="Select * from user where userename = ?1 AND password =?2",nativeQuery = true)
//User login(String username,String password);