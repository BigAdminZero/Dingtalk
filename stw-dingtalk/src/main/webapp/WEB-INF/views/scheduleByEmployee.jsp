<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width: 100%">
	<head>
		<meta charset="UTF-8">
		<title>按个人排班</title>
		<link rel="stylesheet" href="static/css/calendar.css">
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrapValidator.css" />
		<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrapValidator.js" ></script>
	</head>
	<body style="width: 100%">
		<div class="col-xs-12" style="margin-top: 20px">
		    <form class="form-inline">
			  <div class="form-group col-xs-offset-1">
			    <label for="exampleInputName2">部门：</label>
			    <select id="department" class="form-control" style="margin-right: 50px;" onchange="getemployee()">

		            <option>==请选择==</option>
		            <c:forEach var="department" items="${DepartmentList}">
		            	<option value="${department.departmentId}">${department.departmentName}</option>
		            </c:forEach>
		        </select>
		        
		        <label for="exampleInputEmail2">员工名：</label>
			    <select id="employee" class="form-control" style="margin-right: 100px;"  onchange="alertdate()">

		            <option>==请选择==</option>
		            
		
		        </select>
		        <a href="#" onclick="updateTable()" class="btn btn-primary">保存</a>
			  </div>
			  
			  <div class='calendar' id='calendar' style="width: 100%"></div>
			  
			    
			  
			  
			</form>
		
		        
    	</div>
	</body>
	
	<script type="text/javascript">
		//初始化下拉框的值 实现部门和员工的级联
		function getemployee(){
			
			var departmentId=$("#department").val();
			
			var departmentName=$("#department option:checked").text();
			
			$("#employee").html("<option>==请选择==</option>")
			
			$.ajax({
				url:"getEmployeeByDepartmentId",
				type:"post",
				async : false,
				data:{"departmentId":departmentId},
				success:function(msg){
					if(msg!=null){
						for(var i=0;i<msg.length;i++){
							//alert(msg[i].employeeId);
							$("#employee").append("<option value='"+msg[i].employeeId+"'>"+msg[i].employeeName+"</option>"); 
						}
					}
				}
			});
			
		}
		
		
	</script>
	<script type="text/javascript" src="static/js/calendar.js"></script>
</html>
