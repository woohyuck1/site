package com.woo.blog.controller;

import java.net.SocketPermission;
import java.net.http.HttpHeaders;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.jni.User;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woo.blog.config.auth.PrincipalDetail;
import com.woo.blog.model.KakaoProfile;
import com.woo.blog.model.OAuthToken;
import com.woo.blog.service.UserService;

//인증이 안된사용자들이 출입할수있는경로 /auth
//그냥주소가 /면 index.jsp 허용
//static이하에있는 js /css /image/ 허용

@Controller
public class UserController {

	@GetMapping("/boardWrite")
	public String test() {
		return "/boardWrite";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/delete")
	public String delete() {
		return "user/delete";
	}
	
	@SuppressWarnings("unused")
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // 데이터리턴해주는 컨트롤러함수
	
	// post 방식으로 key=value 데이터를 요청
	RestTemplate rt = new RestTemplate();

	// httpheader 오브젝트 생성
	org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
	headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

	// httpbody 오브젝트 생성
	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	params.add("grant_type","authorization_code");
	params.add("client_id","27ee4261212ee053d91a908daf633a80");
	params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
	params.add("code",code);

	// httpheader,httpbody 하나의 오브젝트에 담기
	HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

	// http 요청하기 - post방식- 그리고 response 변수의 응답 받음
	ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
			kakaoTokenRequest, String.class);

	// Gson,Json Simple,ObjectMapper 등등 담을수있는 라이브러리가있따
	ObjectMapper objectMapper = new ObjectMapper();
	OAuthToken oAuthToken = null;
	try{
		oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
	}catch(JsonMappingException e){
	e.printStackTrace();
	}catch(JsonProcessingException e){
	e.printStackTrace();
	}

	System.out.println(oAuthToken.getAccess_token());

	
	RestTemplate rt2 = new RestTemplate();
	
	org.springframework.http.HttpHeaders headers2 = new org.springframework.http.HttpHeaders();
	headers2.add("Authorization","Bearer "+oAuthToken.getAccess_token());
	headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

	HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

	ResponseEntity<String> response2 = rt2.exchange(
			"https://kapi.kakao.com/v2/user/me", 
			HttpMethod.POST,
			kakaoProfileRequest2,
			String.class
	);
	System.out.println(response2.getBody());
	
	ObjectMapper objectMapper2 = new ObjectMapper();
	KakaoProfile kakaoProfile = null;
	try{
		kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
	}catch(JsonMappingException e)
	{
		e.printStackTrace();
	}catch(JsonProcessingException e)
	{
		e.printStackTrace();
	}
	
	
	/*System.out.println(kakaoProfile.getId());
	System.out.println(kakaoProfile.getKakao_account().getEmail());
	
	System.out.println(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
	System.out.println(kakaoProfile.getKakao_account().getEmail());
	UUID garbagePassword= UUID.randomUUID();
	System.out.println(garbagePassword);
		*/
	return  response2.getBody();
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

}
