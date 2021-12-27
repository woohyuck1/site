package com.woo.blog.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	//@Column(nullable = true, length = 200)
	//private String file;
	
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 사용 <html>태그가 섞여서디자인됨.
	
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) //many = board , user = one 한명의 유저는 여려개의 개시글을쓸수있다
	@JoinColumn(name="userId")
	private User user; //db는 오브젝트를 저장할수었다 FK,자바는오브젝트를 저장할수있다. 
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //mappedby 연관관계의 주인이아니다 (fk가아님) db에컬럼을만들지마
	@JsonIgnoreProperties({"board"})													//remove 보드개시글을지울떄 댓글들도 다날려버리기~ 
	@OrderBy("id desc")
	private List<Reply> replys; //join문을통해 값을 얻기위해 사용
	
	@CreationTimestamp
	private LocalDateTime createDate;
}
