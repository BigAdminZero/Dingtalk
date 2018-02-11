<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>权限管理</title>
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrapValidator.css" />
		<script type="text/javascript" src="static/js/jquery-1.10.2.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrapValidator.js" ></script>
		<script type="text/javascript" src="static/layer/layer.js" ></script>
		
		<style>
	    	.table tbody tr td {
	    		
			  vertical-align: middle;
			  text-align: center;
			 
			}
			.table thead tr th {
	    		
			  text-align: center;
			 
			}
			.table-responsive span{
				
				margin-right: 4px;
				
			}
	    </style>
	    
	    <script>
	    	
	    	$(function(){



            $('form').bootstrapValidator({


                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields:{

                    userName:{

                        validators:{
                            notEmpty:{
                                message:"账号不能为空"
                            }
                        }
                    },
                    password:{

                        validators:{
                            notEmpty:{
                                message:"密码不能为空"
                            },
                            identical:{
                                field:"pwdconfig",
                                message:"两次密码不一致"
                            }
                        }
                    },
                    pwdconfig:{

                        validators:{
                            notEmpty:{
                                message:"确认密码不能为空"
                            },
                            identical:{
                                field:"password",
                                message:"两次密码不一致"
                            }
                        }
                    },
                    departmentId:{
                    	validators:{
		               		notEmpty:{
		              		     message:'教师不能为空'
		              		 },
		         		    callback: {
		          		    message: '必须选择一个教师',
			          		    callback: function(value, validator) {
			          		
			             		    if (value == -1) {
			             			    return false;
			             			}else {
			             		        return true;
			             		    }
			          		    }
		         		 	}
                    	}
	                    	
                    	
                    }
                }
            });



        });
	    	
	    </script>
	</head>
	<body>
		<div class="row">
			<ol class="breadcrumb">
		         <li><a href="#">权限管理</a></li>
		        <li class="active">所有用户</li>
		   </ol>
			<div class="table-responsive col-xs-10 col-xs-offset-1">
				
				<div style="margin-bottom: 10px;">
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#adduserModal" style="border-radius: 0px;" >
					  <span class="glyphicon glyphicon-plus" ></span>新增
					</button>
					<button type="button" class="btn btn-danger" style="border-radius: 0px;" onclick="delUser()">
					  <span class="glyphicon glyphicon-remove" ></span>删除
					</button>
				</div>
	            <table class="table table-striped table-bordered table-hover">
	                <thead>
		                <tr>
		                	<th></th>
		                    <th>用户名</th><th>角色</th><th>创建人</th><th style="width: 270px;">操作</th>
		                </tr>
	                </thead>
	
	                <tbody>
	                	<c:forEach var="UserByCreater" items="${userlist}">
	                	
	                	<tr id="${UserByCreater.userId}">
		                    <td>
		                    	<div class="checkbox">
									<label><input type="checkbox" name="checkbox" value="${UserByCreater.userId}" /></label>
						        </div>
		                    </td>
		                    <td>${UserByCreater.userName}</td><td>${UserByCreater.authorityName}</td><td>${nowUser.userName}</td>
		                    <td>
		                    	<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modifyuserModal"
		                    		data-username="${UserByCreater.userName}" data-userid="${UserByCreater.userId}">密码修改</button>
		                    	<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modifyauthoModal"
		                    		data-username="${UserByCreater.userName}" data-userid="${UserByCreater.userId}">权限编辑</button>
		                    </td>
		                </tr>
		                </c:forEach>
	                </tbody>
	            </table>
	       </div>
        </div>
        
		
		<!--新增用户模态框-->
		<div class="modal fade" id="adduserModal" tabindex="-1" role="dialog" aria-labelledby="adduserModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="adduserModalLabel">新增用户</h4>
		      </div>
		      <div class="modal-body">
		        <form id="addUserForm" action="adduser" method="post">
		          <div class="form-group">
		            <label for="userName" class="control-label">用户名:</label>
		            <input type="text" class="form-control" id="userName" name="userName">
		          </div>
		          <div class="form-group">
		            <label for="password" class="control-label">密码:</label>
		            <input type="password" class="form-control" id="password" name="password">
		          </div>
		          <div class="form-group">
		            <label for="pwdconfig" class="control-label">确认密码:</label>
		            <input type="password" class="form-control" id="pwdconfig" name="pwdconfig">
		          </div>
		          <div class="form-group">
		            <label for="authorityName" class="control-label">角色:</label>
		            <select class="form-control" id="authorityName" name="authorityId" onchange="confAuthority()" >
		            	
		            </select>
		          </div>
		          
		          <div id="departmentControl" class="form-group" style="display:none">
			            <label for="department" class="control-label">所属部门:</label>
			            <select id="department" name="departmentId" class="form-control" disabled="disabled" style="margin-right: 50px;">
	
				            <option value="-1">==请选择==</option>
				            <option value="0">所有部门权限</option>
				            <c:forEach var="department" items="${DepartmentList}">
				            	<option value="${department.departmentId}">${department.departmentName}</option>
				            </c:forEach>
				        </select>
			      </div>
		          
		          <c:if test="${nowUser.authorityLevel!=1}">
		          	<c:if test="${nowUser.departmentId==0}">
			          	<div class="form-group">
				            <label for="department1" class="control-label">所属部门:</label>
				            <select id="department1" name="departmentId" class="form-control" style="margin-right: 50px;">

					            <option value="-1">==请选择==</option>
					            <option value="0">所有部门权限</option>
					            <c:forEach var="department" items="${DepartmentList}">
					            	<option value="${department.departmentId}">${department.departmentName}</option>
					            </c:forEach>
					        </select>
				        </div>
			        </c:if>
			        <c:if test="${nowUser.departmentId!=0}">
			        	<div class="form-group">
				            <label for="department1" class="control-label">所属部门:</label>
				            <select id="department1" name="departmentId" class="form-control" style="margin-right: 50px;">

					            <option value="-1">==请选择==</option>
					            <c:forEach var="department" items="${DepartmentList}">
					            	<c:if test="${nowUser.departmentId==department.departmentId}">
					            	<option value="${department.departmentId}">${department.departmentName}</option>
					            	</c:if>
					            </c:forEach>
					        </select>
				        </div>
			        
			        </c:if>
		          </c:if>
		          
		          <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			        <button type="submit" class="btn btn-primary">保存</button>
			      </div>
		        </form>
		      </div>
		      
		    </div>
		  </div>
		</div>
		
		<!--修改密码-->
		<div class="modal fade" id="modifyuserModal" tabindex="-1" role="dialog" aria-labelledby="modifyuserModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="modifyuserModalLabel">修改密码</h4>
		      </div>
		      <div class="modal-body">
		        <form action="modifyPwd" method="post">
		          <div class="form-group">
		            <label for="userName" class="control-label">用户名:</label>
		            <input type="hidden" class="form-control" id="userId" name="userId">
		            <input type="text" class="form-control" id="userName" name="userName">
		          </div>
		          <div class="form-group">
		            <label for="password" class="control-label">密码:</label>
		            <input type="password" class="form-control" id="password" name="password">
		          </div>
		          <div class="form-group">
		            <label for="pwdconfig" class="control-label">确认密码:</label>
		            <input type="password" class="form-control" id="pwdconfig" name="pwdconfig">
		          </div>
		          <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			        <button type="submit" class="btn btn-primary">保存</button>
			      </div>
		        </form>
		      </div>
		      
		    </div>
		  </div>
		</div>
       
        <!--权限编辑-->
		<div class="modal fade" id="modifyauthoModal" tabindex="-1" role="dialog" aria-labelledby="modifyauthoModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="modifyauthoModalLabel">权限编辑</h4>
		      </div>
		      <div class="modal-body">
		        <form action="editAuthority" method="post">
		          <div class="form-group">
		            <label for="userName" class="control-label">用户名:</label>
		            <input type="hidden" class="form-control" id="userId" name="userId">
		            <input type="text" class="form-control" id="userName" name="userName">
		          </div>
		          <div class="form-group">
		            <label for="authorityName" class="control-label">角色:</label>
		            <select class="form-control" id="authorityName2" name="authorityId" onchange="confAuthority2()" >
		            	
		            </select>
		          </div>
		          
		          <div id="departmentControl2" class="form-group" style="display:none">
			            <label for="department2" class="control-label">所属部门:</label>
			            <select id="department2" name="departmentId" class="form-control" disabled="disabled" style="margin-right: 50px;">
	
				            <option value="-1">==请选择==</option>
				            <option value="0">所有部门权限</option>
				            <c:forEach var="department" items="${DepartmentList}">
				            	<option value="${department.departmentId}">${department.departmentName}</option>
				            </c:forEach>
				        </select>
			        </div>
			      <c:if test="${nowUser.authorityLevel!=1}">
		          	<c:if test="${nowUser.departmentId==0}">
			          	<div class="form-group">
				            <label for="department3" class="control-label">所属部门:</label>
				            <select id="department3" name="departmentId" class="form-control" style="margin-right: 50px;">

					            <option value="-1">==请选择==</option>
					            <option value="0">所有部门权限</option>
					            <c:forEach var="department" items="${DepartmentList}">
					            	<option value="${department.departmentId}">${department.departmentName}</option>
					            </c:forEach>
					        </select>
				        </div>
			        </c:if>
			        <c:if test="${nowUser.departmentId!=0}">
			        	<div class="form-group">
				            <label for="department3" class="control-label">所属部门:</label>
				            <select id="department3" name="departmentId" class="form-control" style="margin-right: 50px;">

					            <option value="-1">==请选择==</option>
					            <c:forEach var="department" items="${DepartmentList}">
					            	<c:if test="${nowUser.departmentId==department.departmentId}">
					            	<option value="${department.departmentId}">${department.departmentName}</option>
					            	</c:if>
					            </c:forEach>
					        </select>
				        </div>
			        
			        </c:if>
		          </c:if>
		          
		          <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			        <button type="submit" class="btn btn-primary">保存</button>
			      </div>
		        </form>
		      </div>
		      
		    </div>
		  </div>
		</div>
        
	</body>
	<script>
	
		function delUser(){
			var nowUserPwd="${nowUser.password}"; //当前登录用户的密码
			layer.prompt({title: '请输入您的密码，并确认', formType: 1}, function(pass, index){
			  layer.close(index);
			  if(pass==nowUserPwd){
			  	//当前用户密码验证成功
			  	var chk_val=[];
			  	
			  	$("input[name='checkbox']:checked").each(function(){
			  		chk_val.push($(this).val());  //将选中的复选框放进一个数组中
			  	});
			  	
			  	if(chk_val.length==0){
			  		layer.msg('请选择一个用户');  //如果未选择用户点击删除的话
			  	}else{
			  		for(var i=0;i<chk_val.length;i++){
				  		$("#"+chk_val[i]).remove();
				  	}
			  	
			  		$.ajax({
			  			url:"deleteUser",
			  			type:"post",
			  			traditional:true,
			  			data:{"chk_vals":chk_val},
			  			success:function(){
			  				layer.msg('删除成功');
			  			}
			  		});
			  		
			  		
			  	}
			  	
			  }else{
				  //密码验证失败
			  	layer.msg('密码错误，请重新输入');
			  }
			  
			  
			});
		}
		$('#modifyuserModal').on('show.bs.modal', function (event) {
		 var a = $(event.relatedTarget) // relatedTarget 事件属性返回与事件的目标节点相关的节点。
		 var userName = a.context.dataset.username;
		 var userId=a.context.dataset.userid;
		 /*title = a.data('title'), description = a.data('description');*/
		 var modal = $(this)
		 modal.find('#userName').val(userName);
		 modal.find('#userId').val(userId);
		/* modal.find('#cm-modal-title').val(title);
		 modal.find('#cm-modal-content').val(description);*/
		});
		
		$('#modifyauthoModal').on('show.bs.modal', function (event) {
		 var a = $(event.relatedTarget) // relatedTarget 事件属性返回与事件的目标节点相关的节点。
		 var userName = a.context.dataset.username;
		 var userId=a.context.dataset.userid;
		 /*title = a.data('title'), description = a.data('description');*/
		 $("#departmentControl2").css('display','none');
			$("#department2").attr("disabled", true);
			
		 var modal = $(this);
			$.ajax({
				 url:"getAuthorityName",
				 type:"post",
				 success:function(aNames){
					 var nowUserId="${nowUser.authorityId}"; //当前登录用户的权限等级
					 modal.find('#authorityName2').find("option").remove();
					 for(var i=0;i<aNames.length;i++){
						 if(1==nowUserId){
							 modal.find('#authorityName2').append("<option value='"+aNames[i].authorityId+"'>"+aNames[i].authorityName+"</option>");
						 }else if(aNames[i].authorityId>=nowUserId){
							 modal.find('#authorityName2').append("<option value='"+aNames[i].authorityId+"'>"+aNames[i].authorityName+"</option>");
						 }
						 
					 }
				 }
			 });
		 
		 modal.find('#userName').val(userName);
		 modal.find('#userId').val(userId);
		/* modal.find('#cm-modal-title').val(title);
		 modal.find('#cm-modal-content').val(description);*/
		});
		
		$('#adduserModal').on('show.bs.modal', function (event) {
			$("#departmentControl").css('display','none');
			$("#department").attr("disabled", true);
			var modal = $(this);
			$.ajax({
				 url:"getAuthorityName",
				 type:"post",
				 success:function(aNames){
					 var nowUserId="${nowUser.authorityId}";
					 modal.find('#authorityName').find("option").remove();
					 for(var i=0;i<aNames.length;i++){
						 if(1==nowUserId){
							 modal.find('#authorityName').append("<option value='"+aNames[i].authorityId+"'>"+aNames[i].authorityName+"</option>");
						 }else if(aNames[i].authorityId>=nowUserId){
							 modal.find('#authorityName').append("<option value='"+aNames[i].authorityId+"'>"+aNames[i].authorityName+"</option>");
						 }
						 
					 }
				 }
			 });
			 /*title = a.data('title'), description = a.data('description');*/
			 
			 
			/* modal.find('#cm-modal-title').val(title);
			 modal.find('#cm-modal-content').val(description);*/
			});
		
	function confAuthority(){
		var authorityId=$("#authorityName").val();
		if(authorityId==2){
			$("#departmentControl").css('display','block');
			$("#department").attr("disabled", false); 
		}else{
			$("#departmentControl").css('display','none');
			$("#department").attr("disabled", true);
		}
	}
	
	function confAuthority2(){
		var authorityId=$("#authorityName2").val();
		if(authorityId==2){
			$("#departmentControl2").css('display','block');
			$("#department2").attr("disabled", false); 
		}else{
			$("#departmentControl2").css('display','none');
			$("#department2").attr("disabled", true);
		}
		
	}
		
	</script>
</html>
