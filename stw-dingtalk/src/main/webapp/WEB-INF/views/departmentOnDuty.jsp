<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/calendar.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrapValidator.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="static/js/bootstrap-multiselect.js"></script>
<link href="static/css/bootstrap-multiselect.css" rel="stylesheet" />

<title>部门值班</title>
</head>
<body onload="ifHasDepartmentId()" style="width: 100%">
	<div class="modal fade" id="myModalNew" tabindex="-1" role="dialog"
		style="height: 100%">
		<div class="modal-dialog" role="document" style="height: 80%">
			<form class="modal-content"
				style="height: 100%" onsubmit="">
				<div class="modal-content" style="height: 100%">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">新增值班组</h4>
					</div>
					<div class="modal-body" style="height: 75%">
						<div id="groupNameDiv" class="form-group">
							<label class="control-label" id="groupNameLabel">日期</label>
							<div class="col-sm-12">
								<span id="datespan"></span>
								<input id="date" type="hidden" name="date">
							</div>
						</div>
						<div id="groupNameDiv" class="form-group">
								<input id="departmentId" type="hidden" name="departmentId">
						</div>
						<div id="groupNameDiv" class="form-group">
							<label class="control-label" id="groupNameLabel">值班成员</label>
							<div class="col-sm-12">
							<select id = "type" onchange="changeType(this.value)" name="type"></select>
								<select id="OnDutyEmployeeByDepartmentList"
									name="OnDutyEmployeeByDepartmentList"></select>
							</div>
						</div>
							<div id="groupNameDiv" class="form-group">
							<label class="control-label" id="groupNameLabel">值班类型</label>
							<div class="col-sm-12">
							<select id = "ondutyType" name="ondutyType"></select>
							</div>
						</div>
					</div>
					<div class="modal-footer" style="height: 15%">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" onclick="updateByAjax()" class="btn btn-primary">保存</button>
					</div>
				</div>
			</form>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<div class="col-xs-12" style="margin-top: 20px">
		<form class="form-inline">
			<div class="form-group col-xs-offset-1">
				<label for="exampleInputName2">部门：</label> <select id="department"
					class="form-control" style="margin-right: 50px;"
					onchange="alertdate()">

					<option>==请选择==</option>
					<c:forEach var="department" items="${DepartmentList}">
						<option value="${department.departmentId}" id="${department.departmentId}">${department.departmentName}</option>
					</c:forEach>

				</select> <a href="#" class="btn btn-primary">保存</a>
			</div>

			<div class='calendar' id='calendar' style="width: 100%"></div>
		</form>


	</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/calendar2.js"></script>
	<script type="text/javascript">
	function ifHasDepartmentId(){
		var dId="${departmentId}";
		if(dId != "null"){
			$("#"+dId).prop('selected',true);
			alertdate();
		}
	}
	</script>



</html>