<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrapValidator.css" />
		<link rel="stylesheet" href="assets/css/jquery-ui.min.css" />
		<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrapValidator.js" ></script>
		<script type="text/javascript" src="assets/js/jquery-ui.min.js" ></script>
		<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table.js"></script>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap-table.css" />
<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table-export.js"></script>
<script type="text/javascript"
	src="assets/bootstrap/js/tableExport.js"></script>
<title>EmployeeManager</title>
</head>
<body onload="readyTable()">
<button type="button" onclick="updateEmployee()" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>同步员工列表</button>
<div class="col-xs-12" id="singlelast">
    	<table id="tablesinglelast">
				 <thead>
				 <tr>
				 <th data-field="employeeId" data-valign="middle" data-align="center">员工id</th>
				 <th data-field="employeeName" data-valign="middle" data-align="center">员工姓名</th>
				 <th data-field="departmentName" data-valign="middle" data-align="center">所属部门</th>
				 </tr>
				 </thead>
				 </table>
    	</div>
</body>

<script type="text/javascript">
function readyTable(){
	 $('#tablesinglelast').bootstrapTable({//bootstraptable 插件
		 pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [20,50], 
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc", 
		 showExport: true,                     //是否显示导出
           exportDataType: "all", 
           search:true,
		}); 
	 getEmployee();
}
function updateEmployee(){
	$.ajax({
		url:"updateEmployee",
		type:"post",
		success:function(data){
			getEmployee();
		},
		error:function(){
			alert("同步员工信息失败");
		}
	});
}
function getEmployee(){
	$.ajax({
		url:"getEmployee",
		type:"post",
		dataType : 'json',
		success:function(data){
			console.log(data);
			$('#tablesinglelast').bootstrapTable('load', data);
		},
		error:function(){
			alert("获取员工信息失败");
		}
	});
}
</script>
</html>