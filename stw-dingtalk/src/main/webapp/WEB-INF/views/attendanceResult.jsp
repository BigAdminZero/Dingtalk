<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>考勤结果</title>
<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrapValidator.css" />
		<link rel="stylesheet" href="assets/bootstrap/css/bootstrap-datetimepicker.min.css" />
		<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrapValidator.js" ></script>
		<script type="text/javascript" src="assets/bootstrap/js/bootstrap-datetimepicker.min.js" ></script>
		<script type="text/javascript" src="static/js/attendanceResult.js"></script>
		<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table.js"></script>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap-table.css" />
<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table-export.js"></script>
<script type="text/javascript"
	src="assets/bootstrap/js/tableExport.js"></script>
</head>
<body>
<div class="col-xs-12" style="margin-top: 20px">
		    <form class="form-inline">
			  <div class="form-group col-xs-12">
			    <label for="exampleInputName2">部门：</label>
			    <select id="department" class="form-control" style="margin-right: 50px;" name='departmentId'>
		            <option>==请选择==</option>
		            <option value="0">全部部门</option>
		            <c:forEach var="department" items="${department}">
		            	<option value="${department.departmentId}">${department.departmentName}</option>
		            </c:forEach>
		        </select>
		        </div>
		         <div class="form-group col-xs-12">
		        <label for="date">时间：</label>
			    <input class="form-control" id="date" name='date'> 
		        <a onclick="ifnull()" class="btn btn-primary">查询</a>
			  </div>
			
			</form>
		
		        
    	</div>
    	<div class="col-xs-12" id="singlelast">
    		
    	</div>
</body>



</html>