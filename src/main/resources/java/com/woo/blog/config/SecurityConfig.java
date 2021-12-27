package com.woo.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.woo.blog.config.auth.PrincipalDetailService;

//빈 등록: 스프링컨테이너에서 객체를 관리할수있게 하는것 //아래3개는 세트 
@Configuration //빈등록(ioc관리)
@EnableWebSecurity//시큐리티 필터가 등록이 된다 그설정은 여기에서
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을 하면 권한 및 인증을 미리체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService PrincipalDetailService;
	
	
	@Bean 
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean //ioc가됨 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기를하는데
	// 해당 패스뭐도가 뭘로 해쉬가 되어서 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 db에있는 해쉬랑 비교할 수 있음.
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(PrincipalDetailService).passwordEncoder(encodePWD());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf 토큰 비활성화 (테스트시걸어두는게좋음)
			.authorizeRequests()	//어떤리퀘스트이들어오면
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**") //auth 로들어가
				.permitAll()	
				.anyRequest()	//"/","/auth/**","/js/**","/css/**","/image/**"이게아닌 다른모든요청은
				.authenticated()	//인증이되야댐
		.and()
			.formLogin()
			.loginPage("/auth/loginForm") //인증이필요한 로그인폼으로이동
			.loginProcessingUrl("/auth/loginProc")
			.defaultSuccessUrl("/"); //정상적으로대면
			//.failureUrl("fail")--실패하면 이동   
			//	스프링 시큐리티가 해당주소로 요청오는 로그인을 가로채서 대신 로그인한다
			
	}
}
