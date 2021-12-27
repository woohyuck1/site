package com.woo.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.woo.blog.model.User;
import com.woo.blog.repository.UserRepository;

@Service //빈등록
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인요청을 가로챌 떄 username,password변수2개를 가로챔
	//password부분처리는 알아서 함
	//username이 db에있는지만 확인해서 리턴해주면됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당사용자없음:"+username);
				});
		return new PrincipalDetail(principal); //시큐리티의 세션에 유저 정보가 저장이됨
	}

}
