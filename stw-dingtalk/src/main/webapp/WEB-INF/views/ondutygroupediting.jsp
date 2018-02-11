<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height: 100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<link href="static/css/bootstrap-multiselect.css" rel="stylesheet" />
<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap-multiselect.js"></script>

<title>值班组编辑</title>
<style type="text/css">
button {
	background-color: rgba(255, 255, 255, 0)
}
</style>
</head>

<body style="height: 100%">
	<div class="modal fade" id="myModalUpdate" tabindex="-1" role="dialog"
		style="height: 100%">
		<div class="modal-dialog" role="document" style="height: 80%">
			<form action="updateOndutyGroup" method="post" class="modal-content"
				style="height: 85%">
				<div class="modal-content" style="height: 100%">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">编辑值班人员</h4>
					</div>
					<div class="modal-body" style="height: 70%">
					<label class="col-sm-12 control-label" id="ondutyGroupName" style="color: blue;size: 40px"></label>
					<input type="hidden" name="ondutyGroupId" id="ondutyGroupId">
						<label class="col-sm-6 control-label">值班人员</label>
						<div class="col-sm-6">
						
							<select id="employeeList" multiple="multiple" name="employeeList"></select>
						</div>
					</div>
					<div class="modal-footer" style="height: 10%">
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

	<div class="modal fade" id="myModalNew" tabindex="-1" role="dialog" style="height: 100%">
		<div class="modal-dialog" role="document" style="height: 80%">
		<form action="addOndutyGroup" method="post" class="modal-content" style="height: 100%">
			<div class="modal-content" style="height: 100%">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增值班组</h4>
				</div>
				<div class="modal-body" style="height: 75%">
				<div id="groupNameDiv"  class="form-group">
		<label class="control-label" id="groupNameLabel">值班组名<span style='display:none;color: red;' id="toolong"> -值班组名不能超过十个字符</span><span style='display:none;color: red;' id="null"> -值班组名不能为空</span></label>
			<input class="form-control" name="groupName" id="groupName" type="text" placeholder="请输入值班组名..." onblur="ifready()">
		</div>
		<div id="groupNameDiv"  class="form-group">
		<label class="control-label">值班部门</label>
						<div class="col-sm-12">
						
							<select id="departmentList" name="departmentList" onchange="putEmployeeByDepartmentList(this.value)"></select>
						</div>
		</div>
		<div id="groupNameDiv"  class="form-group">
		<label class="control-label" id="groupNameLabel">值班组成员</label>
						<div class="col-sm-12">
						
							<select id="employeeByDepartmentList" multiple="multiple" name="employeeByDepartmentList"></select>
						</div>
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

	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<td></td>
				<td>值班组名</td>
				<td>部门id</td>
				<td>值班人员</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="ondutyGroup" items="${ondutyGroupList}">
				<tr>
					<td id="${ondutyGroup.ondutyGroupId}"><input type="checkbox" value="${ondutyGroup.ondutyGroupId}"></td>
					<td>${ondutyGroup.ondutyGroupName}</td>
					<td>${ondutyGroup.departmentId}</td>
					<td><div>${ondutyGroup.employeeName}</div>
						<button type="button" class="glyphicon glyphicon-list"
							data-toggle="modal" data-target="#myModalUpdate"
							style="float: right; border: none"
							onclick="getEmployeeList(this)"></button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<button class="btn btn-primary" style="float: right;"
		data-toggle="modal" data-target="#myModalNew" onclick="getDepartmentAndEmployeeList()">新增值班组</button>
	<button class="btn btn-danger" style="float: right;" onclick="deleteOndutyGroup()">值班组删除</button>
</body>
<script type="text/javascript">
	var departmentId;
	var ondutyGroupId;
	var ondutyGroupName;
	function getEmployeeList(thisbutton) {
		//console.log($($(thisbutton).parent().parent().children().get(3)).children().get(0));
		departmentId = $(thisbutton).parent().parent().children().get(2).innerHTML;
		ondutyGroupId = $(thisbutton).parent().parent().children().get(0).id;
		ondutyGroupName = $(thisbutton).parent().parent().children().get(1).innerHTML
		var nameList = $($(thisbutton).parent().parent().children().get(3))
				.children().get(0).innerHTML;
		initMultiselect(nameList);
	}
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
	function initMultiselect(nameList) {
		$("#ondutyGroupName").text(ondutyGroupName);
		$("#ondutyGroupId").val(ondutyGroupId);
		$.ajax({
					method : 'post',
					url : "${pageContext.request.contextPath}/getDepartmentListByDepartmentId",
					dataType : 'json',
					data : {
						"departmentId" : departmentId
					},
					success : function(jsondata) {
						var nameArr = nameList.split("；");
						//console.log(nameArr);
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
								var temp = 0;
								for (var n = 0; n < nameArr.length - 1; n++) {
									if (nameArr[n] == jsondata[j].employeeName) {
										temp = 1;
									} 
								}
								if (temp == 1) {
									group['children'].push({
										selected : true,
										name : jsondata[j].employeeName,
										label : ''
												+ jsondata[j].employeeName,
										value : jsondata[j].employeeId
									});
								} else {
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
						putEmployeeList(data);
					},
					error : function(data) {
						alert("修改模块获取部门成员列表错误");
					}
				});

	}
	function putEmployeeList(data) {
		$('#employeeList').multiselect({
			includeSelectAllOption: true,
			enableClickableOptGroups: true,
			maxHeight : 200
		});
		$('#employeeList').multiselect('dataprovider', data);
	}
function getDepartmentAndEmployeeList(){
	$.ajax({
		method : 'post',
		url : "${pageContext.request.contextPath}/getDepartmentListAjax",
		dataType : 'json',
		success : function(jsondata) {
			var data = [];
			if(jsondata.length != 0){
			for(var i = 0;i<jsondata.length;i++){
				data.push({
					name : jsondata[i].departmentName,
					label : ''
							+ jsondata[i].departmentName,
					value : jsondata[i].departmentId
				});
			}
			}
			putDepartmentList(data);
			putEmployeeByDepartmentList($("#departmentList").find("option:selected").val());
		},
		error : function(data) {
			alert("获取部门列表错误");
		}
	});
	}
	function putDepartmentList(data) {
		$('#departmentList').multiselect({
			maxHeight : 200
		});
		$('#departmentList').multiselect('dataprovider', data);
	}
	function putEmployeeByDepartmentList(dId) {
		$.ajax({
			method : 'post',
			url : "${pageContext.request.contextPath}/getDepartmentListByDepartmentId",
			dataType : 'json',
			data : {
				"departmentId" : dId
			},
			success : function(jsondata) {
				var data = [];
					for (var j = 0; j < jsondata.length; j++) {
							data.push({
								name : jsondata[j].employeeName,
								label : ''
										+ jsondata[j].employeeName,
								value : jsondata[j].employeeId
							});
						
					}
					$('#employeeByDepartmentList').multiselect({
						includeSelectAllOption: true,
						maxHeight : 200
					});
					$('#employeeByDepartmentList').multiselect('dataprovider', data);
			},
			error : function(data) {
				alert("新增模块获取部门成员列表错误");
			}
		});
		
	}
	
	function ifready(){
		if ($("#groupName").val().length>10) {
			$("#null").fadeOut();
			$("#inputResult").remove();
			$("#inputError2Status").remove();
			$("#inputGroupSuccess1Status").remove();
			$("#groupNameDiv").attr("class",'form-group has-error has-feedback')
			$("#groupName").attr("aria-describedby",'inputError2Status');
			$("#groupNameDiv").append("<span id='inputResult' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span><span id='inputError2Status' class='sr-only'>(error)</span>");
			$("#toolong").fadeIn();
		}else if($("#groupName").val().length==0){
			$("#toolong").fadeOut();
			$("#inputResult").remove();
			$("#inputError2Status").remove();
			$("#inputGroupSuccess1Status").remove();
			$("#groupNameDiv").attr("class",'form-group has-error has-feedback')
			$("#groupName").attr("aria-describedby",'inputError2Status');
			$("#groupNameDiv").append("<span id='inputResult' class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span><span id='inputError2Status' class='sr-only'>(error)</span>");
			$("#null").fadeIn();
		}else{
			$("#toolong").fadeOut();
			$("#inputResult").remove();
			$("#inputError2Status").remove();
			$("#inputGroupSuccess1Status").remove();
			$("#groupNameDiv").attr("class",'form-group has-success has-feedback')
			$("#groupName").attr("aria-describedby",'inputGroupSuccess1Status');
			$("#groupNameDiv").append("<span id='inputResult' class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span><span id='inputGroupSuccess1Status' class='sr-only'>(success)</span>");
			$("#null").fadeOut();
		}
	}
	
	function deleteOndutyGroup(){
		var ondutyGroupId = $('input[type=checkbox]:checked').map(function(){return this.value}).get().join(',');
		$.ajax({
			method : 'post',
			url : "${pageContext.request.contextPath}/deleteOndutyGroup",
			data : {
				"ondutyGroupIdString" : ondutyGroupId
			},
			success : function(jsondata) {
				window.location.href = "${pageContext.request.contextPath}/showOndutyGroup";
			},
			error : function(data) {
				alert("删除失败");
			}
		});
	}
</script>
</html>