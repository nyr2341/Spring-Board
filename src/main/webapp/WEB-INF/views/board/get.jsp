<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ include file="../includes/header.jsp"%>

<script type="text/javascript" src="/resources/js/reply.js"></script>
<script>
	$(document).ready(function(){
		
		var bnoValue = '<c:out value="${board.bno}" />';
		var replyUL = $(".chat");
		
		showList(-1);
		
		function showList(page){
			replyService.getList({bno:bnoValue, page:page||1 }, function(replyCnt,list){
				if(page==-1){
					pageNum = Math.ceil(replyCnt/10.0);
					showList(pageNum);
					return;
				}
				
				var str="";
				if(list==null||list.length==0){
					replyUL.html("");
					return;
				}
				for(var i=0, len=list.length||0; i<len; i++){
					str+="<li class='left clearfix' data-rno='"+list[i].rno+"'><div>";
					str+="<div class='header'><strong>"+list[i].replyer+"</strong>"+
						 "<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					str+="<p>"+list[i].reply+"</p></div></li>"
				}
   				replyUL.html(str);
   				showReplyPage(replyCnt);
			});
		}
		
		var pageNum = -1;
		var replyPageFooter = $(".panel-footer");
		
		function showReplyPage(replyCnt){
			var endNum = Math.ceil(pageNum/ 10.0) * 10;
			var startNum = endNum - 9;
			var prev = startNum != 1;
			var next = false;
			
			if(endNum * 10 >= replyCnt){
				endNum = Math.ceil(replyCnt/10.0);
			}
			if(endNum * 10 < replyCnt){
				next = true;
			}
			
			var str = "<ul class='pagination pull-right'>";
			if(prev){
				str += "<li class='page-item'><a class='page-link' href='"+(startNum-1)+"'>Previous</a></li>";
			}
			for(var i=startNum; i<=endNum; i++){
				var active = pageNum == i? "active":"";
				str += "<li class='page-item "+active+"'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
			}
			if(next){
				str += "<li class='page-item "+active+"'><a class='page-link' href='"+(endNum+1)+"'>Next</a></li>";
			}
			str += "</ul>";
			replyPageFooter.html(str);
		}
		
		replyPageFooter.on("click","li a",function(e){
			e.preventDefault();
			var targetPageNum=$(this).attr("href");
			pageNum=targetPageNum;
			showList(pageNum);
		});
		
		var modal = $(".modal");
		var modalInputReply = modal.find("input[name='reply']");
		var modalInputReplyer = modal.find("input[name='replyer']");
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		var modalModifyBtn = $("#modalModifyBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
		var modalCloseBtn = $("#modalCloseBtn");
		
		var replyer = null;
		<sec:authorize access="isAuthenticated()">
			replyer = '<sec:authentication property="principal.username"/>';
		</sec:authorize>
		var csrfHeaderName = "${_csrf.headerName}";
		var csrfTokenValue="${_csrf.token}";
		
		$("#addReplyBtn").on("click",function(e){
			modal.find("input").val("");
			modal.find("input[name='replyer']").val(replyer).attr("readonly","readonly");;
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id!='modalCloseBtn']").hide();
			modalRegisterBtn.show();
			$(".modal").modal("show");
		});
		
		$(".chat").on("click","li",function(e){
			var rnoValue = $(this).data("rno");
			replyService.get(rnoValue, function(reply){
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer).attr("readonly","readonly");
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readonly");
				modal.data("rno",reply.rno);
				
				modal.find("button[id!='modalCloseBtn']").hide();
				modalModifyBtn.show();
				modalRemoveBtn.show();
				$(".modal").modal("show");
			});
		});
		
		$(document).ajaxSend(function(e,xhr,option){
			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		});
		
		modalRegisterBtn.on("click",function(e){
			var reply = {
					reply: modalInputReply.val(),
					replyer: modalInputReplyer.val(),
					bno: bnoValue
			};
			replyService.add(reply, function(result){
				alert(result);

				modal.find("input").val("");
				modal.modal("hide");
				
				showList(-1);
			});
		});
		
		modalModifyBtn.on("click",function(e){
			var originalReplyer = modalInputReplyer.val();
			var reply = {
					rno: modal.data("rno"),
					reply: modalInputReply.val(),
					replyer: originalReplyer
			};
			if(!replyer){
				alert('로그인후 수정이 가능합니다.');
				modal.modal("hide");
				return;
			}
			if(replyer!=originalReplyer){
				alert('자신이 작성한 댓글만 수정이 가능합니다.');
				modal.modal("hide");
				return;
			}
			replyService.update(reply, function(result){
				alert(result);
				modal.modal("hide");
				showList(pageNum);
			});
		});
		
		modalRemoveBtn.on("click",function(e){
			if(!replyer){
				alert('로그인후 삭제가 가능합니다.');
				modal.modal("hide");
				return;
			}
			var originalReplyer = modalInputReplyer.val();
			if(replyer!=originalReplyer){
				alert('자신이 작성한 댓글만 삭제가 가능합니다.');
				modal.modal("hide");
				return;
			}
			replyService.remove({bno:bnoValue, rno:modal.data("rno"), replyer:originalReplyer},function(result){
				alert(result);
				modal.modal("hide");
				showList(pageNum);
			});
		});
		
		modalCloseBtn.on("click",function(e){
			modal.modal("hide");
		});
	});
</script>
<script>
	$(document).ready(function(){
		var operForm=$("#operForm");
		$("button[data-oper='modify']").on("click",function(e){
			operForm.attr("action","/board/modify").submit();
		});
		$("button[data-oper='list']").on("click",function(e){
			operForm.find('#bno').remove();
			operForm.attr("action","/board/list").submit();
		});
	});
</script>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label> <input class="form-control" name="reply"
						value="New Reply">
				</div>
				<div class="form-group">
					<label>Replyer</label> <input class="form-control" name="replyer"
						value="New Replyer">
				</div>
				<div class="form-group">
					<label>Reply Date</label> <input class="form-control"
						name="replyDate" value="123124">
				</div>
			</div>
			<div class="modal-footer">
				<button id="modalModifyBtn" type="button" class="btn btn-warning">Modify</button>
				<button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
				<button id="modalRegisterBtn" type="button" class="btn btn-primary">Register</button>
				<button id="modalCloseBtn" type="button" class="btn btn-default">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

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
				<div class="panel-heading">Board Register</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="form-group">
						<label>Title</label> <input class="form-control" name="title"
							readonly="readonly" value='<c:out value="${board.title}"/>'>
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea class="form-control" name="content" readonly="readonly"
							style="height: 184px; resize: none;"><c:out
								value="${board.content}" /></textarea>
					</div>
					<div class="form-group">
						<label>Writer</label> <input class="form-control" name="writer"
							readonly="readonly" value='<c:out value="${board.writer}"/>'>
					</div>
					<sec:authentication property="principal" var="pinfo" />
					<sec:authorize access="isAuthenticated()">
						<c:if test="${pinfo.username eq board.writer }">
							<button data-oper="modify" class="btn btn-default">Modify</button>
						</c:if>
					</sec:authorize>
					<button data-oper="list" class="btn btn-info">List</button>

					<form id="operForm" action="/board/modify" method="get">
						<input type="hidden" name="bno" id="bno"
							value='<c:out value="${board.bno}"/>'> <input
							type="hidden" name="pageNum"
							value='<c:out value="${cri.pageNum}"/>'> <input
							type="hidden" name="amount"
							value='<c:out value="${cri.amount}"/>'> <input
							type="hidden" name="type" value="<c:out value='${cri.type }' />">
						<input type="hidden" name="keyword"
							value="<c:out value='${cri.keyword }' />">
					</form>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->

			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-comments fa-fw"></i> Reply
					<sec:authorize access="isAuthenticated()">
						<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">New
							Reply</button>
					</sec:authorize>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<ul class="chat">
					</ul>
				</div>
				<!-- /.panel-body -->
				<div class="panel-footer"></div>
				<!-- /.panel-footer -->
			</div>
			<!-- /.panel -->

		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->