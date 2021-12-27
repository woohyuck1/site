<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.id == principal.user.id}">
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br /> <br />
	<div>
		글 번호 : <span id="id"><i>${board.id} </i></span> 작성자 : <span><i>${board.user.username} </i></span> <br />
	</div>
	<div>
		<label for="title">:(</label>
		<h3>${board.title}</h3>
	</div>
	<div>
		<label for="content">:)</label>
		<div>${board.content}</div>
	</div>
	<div class="card">
	<form>
	<input type = "hidden" id = "userId" value="${principal.user.id}"/>
	<input type = "hidden" id = "boardId" value="${board.id}"/>
		<div class="card-body">
			<textarea id = "reply-content" class="form-control" rows="1"></textarea>
		</div>
		<div>
			<button type="button" id = "btn-reply-save" class="btn btn-primary">등록</button>
		</div>
		</form>
	</div>
	<br>
	<br />
	<div class="card">
		<div class="card-header">야홍</div>
		<ul id="reply-box" class="list-group">
			<c:forEach var="reply" items="${board.replys}">
			
			<li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
				<div>${reply.content}</div>
				
				<div class="panel panel-default">
						<div class="panel-body">
				
					<table class="table table-bordered table-hover">
					<tr>
						<td>${fName}</td>
						<td><a href="javascript:getfile('${fName}')"><span class="glyphicon glyphicon-file"></span></a></td>
					</tr>
					</table>
				</div>
				</div>
				
				<div class="d-flex">
					<div class="font-italic">작성자:${reply.user.username}&nbsp;</div>
					<button onClick = "index.replyDelete(${board.id},${reply.id})" class="badge">삭제</button>
				</div>
			</li>
		</c:forEach>
		</ul>
	</div>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>