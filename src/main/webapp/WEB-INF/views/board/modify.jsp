<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ include file="../includes/header.jsp"%>

<script>
	$(document).ready(function(){
		
		var formObj = $("form");
		
		$('button').on("click",function(e){
			
			e.preventDefault();
			var operation = $(this).data("oper");
			
			if(operation==='remove'){
				formObj.attr("action","/board/remove");
			}else if(operation==='list'){
				formObj.attr("action","/board/list").attr("method","get");
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				var keywordTag = $("input[name='keyword']").clone();
				var typeTag = $("input[name='type']").clone();
				formObj.empty();
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				formObj.append(keywordTag);
				formObj.append(typeTag);
			}
			formObj.submit();
			
		});
	});
</script>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Board Register</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
                    Board Register
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                <form role="form" action="/board/modify" method="post">
	                	<div class="form-group">
	               			<label>Bno</label>
	               			<input class="form-control" name="bno" readonly="readonly" value='<c:out value="${board.bno}"/>'>
	               		</div>
	               		<div class="form-group">
	               			<label>Title</label>
	               			<input class="form-control" name="title" value='<c:out value="${board.title}"/>'>
	               		</div>
	               		<div class="form-group">
	               			<label>Content</label>
	               			<textarea class="form-control" name="content" style="height:184px;resize: none;"><c:out value="${board.content}"/></textarea>
	               		</div>
	               		<div class="form-group">
	               			<label>Writer</label>
	               			<input class="form-control" name="writer" readonly="readonly" value='<c:out value="${board.writer}"/>'>
	               		</div>
	               		
               			<sec:authentication property="principal" var="pinfo"/>
               			<sec:authorize access="isAuthenticated()">
	               			<c:if test="${pinfo.username eq board.writer }">
			               		<button type="submit" data-oper="modify" class="btn btn-default">Modify</button>
			               		<button type="submit" data-oper="remove" class="btn btn-danger">Remove</button>
	               			</c:if>
               			</sec:authorize>
	               		<button type="submit" data-oper="list" class="btn btn-info">List</button>
	               		
	               		<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum}"/>'> 
               			<input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
               			<input type="hidden" name="type" value="<c:out value='${cri.type }' />">
                        <input type="hidden" name="keyword" value="<c:out value='${cri.keyword }' />">
                		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }"/>
                		
               		</form>
                </div>
                <!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-6 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->