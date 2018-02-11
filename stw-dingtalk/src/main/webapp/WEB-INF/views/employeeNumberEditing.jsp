<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%;width:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.js" ></script>
		<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table.js"></script>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap-table.css" />
<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
	src="assets/bootstrap/js/bootstrap-table-export.js"></script>
<script type="text/javascript"
	src="assets/bootstrap/js/tableExport.js"></script>
	<script type="text/javascript" src="static/js/bootstrap-multiselect.js"></script>
	<link href="static/css/bootstrap-multiselect.css" rel="stylesheet" />
<title>员工编号编辑</title>
</head>
<body onload="readyTable()" style="width: 100%">
	<div class="modal fade" id="myModalNew" tabindex="-1" role="dialog" style="height: 100%">
		<div class="modal-dialog" role="document" style="height: 80%">
		<form action="addEmpNumber" method="post" class="modal-content" style="height: 100%">
			<div class="modal-content" style="height: 100%">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增员工编号</h4>
				</div>
				<div class="modal-body" style="height: 75%">
				
					<div class="form-group">
						<label for="number" style="size: 40px">员工编号：</label>
						<input name="number" class="form-control" id="number">
					</div>
					<div class="form-group">
						<label for="employeeName">员工姓名：</label>
						<select id="noNumberEmployee" name="noNumberEmployee"></select>
					</div>
		
				</div>
				<div class="modal-footer" style="height: 15%">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</div>
			</form>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
<div>
	<ol class="breadcrumb">
        <li><a>组织管理</a></li>
        <li class="active">员工编号管理</li>
   </ol>
	<table id="tablesinglelast">
	</table>
	<button class="btn btn-primary" style="float: right;"
		data-toggle="modal" data-target="#myModalNew" onclick="getNoNumberEmployeeId()">新增员工编号</button>
	<button class="btn btn-danger" style="float: right;" onclick="deleteEmpNumber()">删除员工编号</button>
</div>
</body>

<script type="text/javascript">

var number;
var EmployeeName;
//数组去重
function removeRepeat(arr) {
	for (var i = 0; i < arr.length; i++)
		for (var j = i + 1; j < arr.length; j++)
			if (arr[i] === arr[j]) {
				arr.splice(j, 1);
				j--;
			}
	return arr.sort(function(a, b) {
		return a - b
	});
}
function getNoNumberEmployeeId(){
	 $.ajax({
			url:"getNoNumberEmployeeId",
			type:"post",
			dataType : "json",
			success:function(jsondata){
				console.log(jsondata);
				var data = [];
				var groupNum = 0;
				var departmentNameList = [];
				for (var i = 0; i < jsondata.length; i++) {
					departmentNameList.push(jsondata[i].departmentName);
				}
				departmentNameList = removeRepeat(departmentNameList);
				groupNum = departmentNameList.length;

				for (var i = 0; i < groupNum; i++) {
					var group = {
						label : '' + departmentNameList[i],
						children : []
					};

					for (var j = 0; j < jsondata.length; j++) {
						if(jsondata[j].departmentName==departmentNameList[i]){
							group['children'].push({
								name : jsondata[j].employeeName,
								label : ''
										+ jsondata[j].employeeName,
								value : jsondata[j].employeeId
							});
						}
					}

					data.push(group);
				}
				putNoNumberEmployee(data);
			},
			error:function(){
				alert("获取无编号员工失败");
			}
		});
}
function putNoNumberEmployee(data) {
	$('#noNumberEmployee').multiselect({
		maxHeight : 200
	});
	$('#noNumberEmployee').multiselect('dataprovider', data);
}

function getEmpNumber(thisbutton) {
	//console.log($($(thisbutton).parent().parent().children().get(3)).children().get(0));
	number = $(thisbutton).parent().parent().children().get(1).innerHTML;
	EmployeeName = $(thisbutton).parent().parent().children().get(2).innerHTML;
	
	$("#number").val(number);
	$("#employeeName").val(EmployeeName);
	
}

function deleteEmpNumber(){
	var EmpNumbers=[];
	var getSelectRows = $("#tablesinglelast").bootstrapTable('getSelections', function (row) {
        return row;
        });
	for(var i = 0; i < getSelectRows.length; i++){
		EmpNumbers.push(getSelectRows[i].empNumber);
	}
	if(EmpNumbers.length==0){
		layer.msg('请选择一个用户');
	}else{
		$.ajax({
			url:"deleteEmpNumber",
			type:"post",
	        traditional: true,
			data:{"EmpNumbers":EmpNumbers},
			success:function(msg){
				window.location.reload();
			}
		});
	}
}

function readyTable(){
	$("#tablesinglelast").empty();
	 $("#tablesinglelast").bootstrapTable({//bootstraptable 插件
		 pageNumber:1,                       //初始化加载第一页，默认第一页
       pageSize: 15,                       //每页的记录行数（*）
       pageList: [15,30], 
       pagination: true,                   //是否显示分页（*）
       sortable: true,                     //是否启用排序
       sortOrder: 'desc',    
       search:true,
          clickToSelect: true,
          columns: [{
              checkbox: true
          }, {
              field: 'empNumber',
              title: '员工编号'
          }, {
              field: 'name',
              title: '员工姓名',
          }],
		});
	 
	 $.ajax({
			url:"getEmployeeNumber",
			type:"post",
			dataType : "json",
			success:function(data){
				$('#tablesinglelast').bootstrapTable('load', data);
			},
			error:function(){
				alert("获取员工编号失败");
			}
		});
}
</script>

</html>