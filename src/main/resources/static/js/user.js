let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function대신 >사용한이유는 this를 바인딩하기 위해서
			this.save();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});

	},
	save: function() {
		$	//alert("user의 save함수 호출");
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		//console.log(data);

		//ajax 통싱을 이용해서 3개으 데이터를 json으로 변경하여 insert요청
		//ajax호출시 default 가 비동기호출
		//ajax가 통신을 선공하고 서버가 json을 리턴해주면 자동으로 자바오브젝트로 변환
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),//json으로변경 ,http body 데이터
			contentType: "application/json; charset=utf-8",//body 데이터가 어떤타입인지
			dataType: "json" //요청을 서버로해서 응답이왔을때 기본적으로 모든건이 String (생긴게 json이라면)>자바스트립트 오브젝트로 변경해준다
		}).done(function(resp) {
			if(resp.status === 500){
				alert("실패");
			}else{
			alert("회원가입성공.");
			//console.log(resp);
			location.href = "/";
			}
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

	},
	update: function() {
		$
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
	
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
		}).done(function(resp) {
			alert("수정성공.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

	},

}

index.init();
