package com.woo.blog.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.id.CompositeNestedGeneratedValueGenerator.GenerationContextLocator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM:java(다은언어) object -> 테이블로 매핑해주는기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
@Entity //User 클래스가 mysql에 테이블이 생성됨
//@DynamicInsert //insert할때널인 필드제외
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 디비의 넘버링전략을 따라간다
	private int id; //시퀀스,auto_increment
	
	@Column(nullable = false,length = 100,unique=true) //not null 길이20
	private String username; //아이디
	
	@Column(nullable = false,length =100)
	private String password; //비밀번호
	
	@Column(nullable = false,length =50)
	private String email; //메일 
	
	//@ColumnDefault("'user'")
	//db는 roletype이라는게없다
	@Enumerated(EnumType.STRING)//타입알려줌
	private RoleType role; //enum쓰는게좋음 //USER,ADMIN 타입강제
	
	private String oauth;
	
	@CreationTimestamp //시간이 자동으로 입력
	private LocalDateTime createDate;
}
