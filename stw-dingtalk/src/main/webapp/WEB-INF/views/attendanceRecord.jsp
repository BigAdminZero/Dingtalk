<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>考勤记录</title>
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
</head>
<body onload="readyTable()">
<div class="col-xs-12" style="margin-top: 20px">
		    <form class="form-inline">
			  <div class="form-group col-xs-12">
			    <label for="exampleInputName2">部门：</label>
			    <select id="department" class="form-control" style="margin-right: 50px;" onchange="getemployee()" name='departmentid'>
		            <option>==请选择==</option>
		            <c:forEach var="department" items="${department}">
		            	<option value="${department.departmentId}">${department.departmentName}</option>
		            </c:forEach>
		        </select>
		        <label for="exampleInputEmail2">员工名：</label>
			    <select id="employee" class="form-control" style="margin-right: 100px;" name='employeeId'>
		            <option>==请选择==</option>
		        </select>
		        </div>
		         <div class="form-group col-xs-12">
		        <label for="from">从：</label>
			    <input class="form-control" id="from" name='starttime'> 
			    <label for="to">到：</label>
			    <input class="form-control" id="to" name='endtime'> 
		        <a onclick="ifnull()" class="btn btn-primary">查询</a>
			  </div>
			
			</form>
		
		        
    	</div>
    	<div class="col-xs-12" id="singlelast">
    	<table id="tablesinglelast">
				 <thead>
				 <tr>
				 <th data-field="workDate" id="ws.ref_date" data-valign="middle" data-align="center">工作日日期</th>
				 <th data-field="OnDuty" data-valign="middle" data-align="center">上班打卡时间</th>
				 <th data-field="OffDuty" data-valign="middle" data-align="center">下班打卡时间</th>
				 <th data-field="workTime" data-valign="middle" data-align="center">工作时长</th>
				 </tr>
				 </thead>
				 </table>
    	</div>
</body>
<script type="text/javascript" src="static/js/attendanceRecord.js"></script>
<script type="text/javascript">
$(function() {
    $( "#from" ).datepicker({
    	dateFormat:"yy-mm-dd",
      changeMonth: true,
      onClose: function( selectedDate ) {
        $( "#to" ).datepicker( "option", "minDate", selectedDate );
      }
    });
    $( "#to" ).datepicker({
    	dateFormat:"yy-mm-dd",
      changeMonth: true,
      onClose: function( selectedDate ) {
        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
      }
    });
  });
	</script>
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

function ifnull(){
	if($("#employee").val()=="==请选择=="){
		alert("请选择需要查询的员工");
		return false;
	}
	if($("#from").val()==""){
		alert("请选择需要查询的起始时间");
		return false;
	}
	if($("#to").val()==""){
		alert("请选择需要查询的截止时间");
		return false;
	}
	getAttendanceRecord();
}
function readyTable(){
	 $('#tablesinglelast').bootstrapTable({//bootstraptable 插件
		 pageNumber:1,                       //初始化加载第一页，默认第一页
         pageSize: 10,                       //每页的记录行数（*）
         pageList: [10, 31], 
         pagination: true,                   //是否显示分页（*）
         sortable: true,                     //是否启用排序
         sortOrder: "asc", 
		 showExport: true,                     //是否显示导出
            exportDataType: "all", 
		}); 
}
</script>
</html>