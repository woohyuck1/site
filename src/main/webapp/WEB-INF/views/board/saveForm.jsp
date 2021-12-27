<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   

<div class="container">


		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter title" id="title">
		</div>
		 
		 <form id="fileForm" enctype="multipart/form-data">
    <span class="file" id="file">
        <input type="file" id="testUploadFile" name="file" >
    </span>
</form>
 
		 
		<div class="form-group">
			<textarea class="form-control summernote " rows="5" id="content"></textarea>
		</div>
	
	
			
		<button id="btn-save"  class="btn btn-primary">끝</button>
		</div>
 <script>
      $('.summernote').summernote({
        //placeholder: '??', 기본내용
        tabsize: 2,
        height: 400
      });
    </script>
    <script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>