package com.woo.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.woo.blog.model.RoleType;
import com.woo.blog.model.User;
import com.woo.blog.repository.UserRepository;
//html파일이라니라 data를 리턴해주는 controller = restcontroller
@RestController
public class DummyControllerTest {
	
	//save함수는 id를 전달하지않으면 insert하고
	//id에대한 데이터가있으면 update 없으면 insertㅎㄴ다
	//email,password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
		userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
		return "실패";
		
	}
		return "성공";
	}
	@Transactional //함수종료시 자동으로 commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {//json 데이터를 요청 -> java obj로 변환해서 반환
		System.out.println("id:"+id);
		System.out.println("passwd:"+requestUser.getPassword());
		System.out.println("email"+requestUser.getEmail());
	
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("실패");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(requestUser);
		return null;
	}
	
	@Autowired //의존성 주입
	private UserRepository userRepository;
	
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건의 데이터를 리턴받아보기
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2,sort="id",direction = Sort.Direction.DESC)Pageable pageable){
	Page<User> pagingUser = userRepository.findAll(pageable);
	
	List<User> users = 	pagingUser.getContent();
	return pagingUser;
	}
	
	//{id}주소로 파라미터 전달받을수있음
	//http://localhost:8000/blog/dummy/user/3
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4을 찾으면 내가 데이터베이스에서 못찾아오게되면 user가 null 
		//그러면 return null 이됨 프로그램에 문제생김
		//optional 로 너의 user 객체를 감싸서 가져옴 null인지아닌지판단해서 return
		
		//람다식 
		//User user = userRepository.findById(id).orElseThrow(()->{
		// return new IllegalArgumentException("없음");
		// });
		 User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		@Override
		public IllegalArgumentException get() {
		return new IllegalArgumentException("해당 없어용 .id:"+id);
		 }
		
		
		 });
		 //요청:웹브라우저
		 //user 객체 = 자바오브젝트
		 //변환 (웹브라우자가 이해할수있는데티어 ->json (gson 라이브러리)
		 //스프링부터 = messageconverter라는 애가 응답시에 자동작동
		 // 만약에 자바 오브젝트를 리턴하게되면 messageconverter 가 javckson 라이브러리 호출해서
		 //user 오브젝트를json 으로 변환해서 브라우저에거 던진다.
		 return user;
}
	
	//http://localhost:8000/blog/dummy/join
	//hppt의 body에 username.password.email 데이터를가지고요청
	@PostMapping("/dummy/join")
	public String join(User user) {//key=value (약속된규칙)
	System.out.println("id:" + user.getId());
	System.out.println("username:"+user.getUsername());
	System.out.println("password:"+user.getPassword());
	System.out.println("email:"+user.getEmail());
	System.out.println("role:"+user.getRole());
	System.out.println("createdate:"+ user.getCreateDate());
	
	user.setRole(RoleType.USER);
	userRepository.save(user);
	return "성공";
}
	}
