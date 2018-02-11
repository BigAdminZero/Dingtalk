<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>排班模板编辑</title>
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrapValidator.css" />
		<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrapValidator.js" ></script>
		
		<style>
	    	.table tbody tr td {
	    		
			  vertical-align: middle;
			 
			}
			.table thead tr th {
	    		
			  text-align: center;
			 
			}
	    </style>
	</head>
	<body>
		
		<div class="col-xs-10 col-xs-offset-1" style="margin-top: 50px">
		    <div class="panel panel-default">
		
		        <div class="panel-heading">
		
		            <h3 class="panel-title">排班模板编辑</h3>
		
		
		        </div>
		
			        <div class="panel-body">
			
			                <div class="table-responsive">
			                    <table class="table table-striped table-bordered table-hover text-center">
			
			                        <thead>
			
			                        <tr>
			                            <th></th><th>组名</th><th>上班时间</th>
			                            <th>下班时间</th><th>星期</th><th>操作</th>
			                        </tr>
			
			                        </thead>
			
			                        <tbody>
									<c:forEach var="ScheduleGroup" items="${ScheduleGroupList}">
									
										<tr id="${ScheduleGroup.scheduleGroupId}">
			
				                            <td>
				                            	<div class="checkbox">
				                            		<label><input type="checkbox" name="checkbox" value="${ScheduleGroup.scheduleGroupId}" /></label>
										        </div>
				                            </td>
				                            <td>${ScheduleGroup.scheduleGroupName}</td>
											<td><fmt:formatDate value="${ScheduleGroup.startTime}" pattern="HH:mm"/></td>
				                            <td><fmt:formatDate value="${ScheduleGroup.endTime}" pattern="HH:mm"/></td>
				                            <td>${ScheduleGroup.week}</td>
				                            <td>
				                            	<a href="scheduleTemplateEditing?scheduleGroupId=${ScheduleGroup.scheduleGroupId}" class="btn btn-primary">修改</a>
				                            	<button class="btn btn-danger" onclick="del(${ScheduleGroup.scheduleGroupId})">删除</button>
				                            </td>
				
				                        </tr>
									
									</c:forEach>
									
			                        </tbody>
			
			
			                    </table>
							
			            	</div>
			            	
			            	<div class="col-lg-offset-5">
			            		
			            		<a href="scheduleTemplateEditing?scheduleGroupId=0" class="btn btn-primary">新增</a>
			            		<button class="btn btn-primary" onclick="delmulti()">批量删除</button>
			            		
			            	</div>
			
			        </div>
				</div>
			</div>
    	</div>
    	<div class="modal fade" id="delcfmModel">  
		  <div class="modal-dialog">  
		    <div class="modal-content message_align">  
		      <div class="modal-header">  
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>  
		        <h4 class="modal-title">提示信息</h4>  
		      </div>  
		      <div class="modal-body">  
		        <p>您确认要删除吗？</p>  
		      </div>  
		      <div class="modal-footer">  
		         <input type="hidden" id="url"/>  
		         <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>  
		         <a id="cfgdel" class="btn btn-success" data-dismiss="modal">确定</a>  
		      </div>  
		    </div>
		  </div> 
		</div>
		<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
		    <div class="modal-dialog" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		                <h4 class="modal-title">提示</h4>
		            </div>
		            <div class="modal-body">
		                <p id="content">个人信息只能逐个修改</p>
		            </div>
		            <div class="modal-footer">
		                <button onclick="closeModal()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div>
		    </div>
		</div>
	</body>
	
	<script>
		
		function del(id){
			
			$('#delcfmModel').modal();
			
			$("#cfgdel").click(function(){
				
				$.ajax({
					url:"deleteScheduleGroup",
					type:"post",
					dataType:"json",
					data:{"scheduleGroupId":id},
					success:function(msg){
						$("#"+id).remove();
					}
				});
			});
			
		}
		
		function delmulti(){
			var scheduleGroupId=[];
			$("input[name='checkbox']:checked").each(function(){
				scheduleGroupId.push($(this).val());
			});
			
			if(scheduleGroupId.length==0){
				$("#myModal").css('display','block');
				$("#myModal").removeClass('modal fade');
				$("#myModal").addClass("modal fade in");
				$("#content").text("请选择一个用户");
			}else{
				
				$.ajax({
					url:"deleteScheduleGroup",
					type:"post",
					dataType:"json",
					async : false,
			        cache : false,
			        traditional: true,
					data:{"scheduleGroupId":scheduleGroupId},
					success:function(msg){
						for(var i=0;i<scheduleGroupId.length;i++){
							var id=scheduleGroupId[i];
							$("#"+id).remove();
						}
					}
				});
			}
		}
		
		function closeModal(){
		$("#myModal").css('display','none');
		}
	</script>
</html>
