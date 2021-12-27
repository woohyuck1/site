package com.woo.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// http컨트롤러 테스트

//사용자가 요청 -> 응당(html 파일)
// @controller

//사용자가 요청 -> 응답(data)
@RestController
public class httptest {
	
	
	private static final String TAG = "httptest:";
	
	//localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("woo").password("123").email("qwe@ma/c").build();
		System.out.println(TAG +"getter" +m.getId());
		m.setId(5000);
		System.out.println(TAG +"setter" +m.getId());
		return "lombok 성공!";
	}
	//인터넷 브라우저요청은 무적권 get만할수있다.
	//http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {//messageconverter(스프링부트) 라는친구가한다
		
		return "get 요청 :" +  m.getId()+ ","+m.getUsername()+","+ m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8080/http/post (insert)
	@PostMapping("/http/post") 
	public String postTest(@RequestBody Member m) { //messageconverter(스프링부트) 라는친구가한다
		return "post 요청 :" +  m.getId()+ ","+m.getUsername()+","+ m.getPassword()+","+m.getEmail();

	}
	
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 :" +  m.getId()+ ","+m.getUsername()+","+ m.getPassword()+","+m.getEmail();

	}
	
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
