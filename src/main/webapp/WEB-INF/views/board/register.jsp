<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ include file="../includes/header.jsp"%>
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
                	<form role="form" action="/board/register" method="post">
                		<div class="form-group">
                			<label>Title</label>
                			<input class="form-control" name="title">
                		</div>
                		<div class="form-group">
                			<label>Text area</label>
                			<textarea class="form-control" name="content" style="height:184px;resize: none;"></textarea>
                		</div>
                		<div class="form-group">
                			<label>Writer</label>
                			<input class="form-control" name="writer" readonly="readonly" value="<sec:authentication property='principal.username'/>">
                		</div>
                		<button type="submit" class="btn btn-default">Submit</button>
                		<button type="reset" class="btn btn-default">Reset</button>
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