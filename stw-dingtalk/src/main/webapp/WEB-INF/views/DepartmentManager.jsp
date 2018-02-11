<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	
<title>DepartmentManager</title>
</head>
<body>
	<div class="row">
		<ol class="breadcrumb">
	        <li><a href="#">组织管理</a></li>
	        <li class="active">部门管理</li>
	   </ol>
		<div class="table-responsive col-xs-10 col-xs-offset-1">
			
			<div style="margin-bottom: 10px;">
				<button type="button" onclick="updateDepartment()" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>同步部门列表</button>
			</div>
           <table class="table table-striped table-bordered table-hover">
               <thead>
                <tr>
                	<th></th>
                    <th>部门id</th><th>部门名称</th>
                </tr>
               </thead>

               <tbody>
               	<c:forEach var="department" items="${departmentlist}">
               	
	               	<tr id="${department.departmentId}">
	                    <td></td>
	                    <td>${department.departmentId}</td>
	                    <td>${department.departmentName}</td>
	                </tr>
                </c:forEach>
               </tbody>
           </table>
	   </div>
   </div>



</body>
<script type="text/javascript">

function updateDepartment(){
	$.ajax({
		url:"updateDepartment",
		type:"post",
		success:function(msg){
			layer.msg('同步部门成功');
			setTimeout("window.location.reload()", 1000);
			
			
		},
		error:function(){
			layer.msg('部门同步发生错误');
		}
	});
}

</script>

</html>