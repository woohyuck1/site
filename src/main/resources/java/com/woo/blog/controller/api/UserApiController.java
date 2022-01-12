package com.woo.blog.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.woo.blog.config.auth.PrincipalDetail;
import com.woo.blog.dto.ResponseDto;
import com.woo.blog.model.RoleType;
import com.woo.blog.model.User;
import com.woo.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
//@Autowired
//	private HttpSession session; 이렇게해도된다

	// 회원가입 로직이실행되는부분
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		// 실제로 db에 insert를 하고 아래에서 return이 됨
		userService.membership(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/user/delete")
	public ResponseDto<Integer> delete(@RequestBody User user){

		userService.meberdel(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
 
    
	//회원 수정
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		userService.memberfix(user);
		//트랜잭션이 종료되기 떄문에 db에 값은 변경이됨
		//세션값은 변경안됨 직접 세션값 변경해야됨
		//세션등록
		org.springframework.security.core.Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}

