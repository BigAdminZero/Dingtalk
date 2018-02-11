<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/loading.css">
		<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
		<title></title>
	</head>
	<body>
<div class="container" >
    <div class="row">
    <div class="col-xs-12">
    <form class="form" >
        <div class="form-group">
            <label>部门名:</label>
            <select class="form-control" name="departmentId">
            <c:forEach items="${departments}" var="department">
            <option  value="${department.departmentId }" >${department.departmentName}</option>
            </c:forEach>
        	</select>
        </div>
   		 <label>排班模板:</label>  
        <select class="form-control" name="groupId">
         <c:forEach items="${scheduleGroups}" var="scheduleGroup">
            <option value="${scheduleGroup.scheduleGroupId}" >${scheduleGroup.scheduleGroupName}</option>
            </c:forEach>
        </select>
        <br/>
        <input class="btn btn-primary" id="savebtn"  type="button" value="保存"/>
     </form>
</div>

        </div>
</div>		
	
	<div id="modifing" style="display: none;">
		<div id="loading-center">
			<div id="loading-center-absolute">
				<div class="object" id="object_one"></div>
				<div class="object" id="object_two" style="left:20px;"></div>
				<div class="object" id="object_three" style="left:40px;"></div>
				<div class="object" id="object_four" style="left:60px;"></div>
				<div class="object" id="object_five" style="left:80px;"></div>
			</div>
			<div id="content">
		      	
		      	正在修改请稍后...
		        
		    </div>
		</div>
	</div>	
	</body>
	
	<script type="text/javascript">
	
	$("#savebtn").click(function(){
		
		$("#modifing").css('display','block');
	    $("#savebtn").attr('disabled',true);
	    var departmentId=$("select[name='departmentId']").val();
	    var groupId=$("select[name='groupId']").val();
	    
	    //alert(departmentId+"|"+groupId);
	    
	    $.ajax({
	    	url:"${pageContext.request.contextPath}/scheduleByDepartment",
	    	type:"post",
	    	data:{"SdeptId":departmentId,"SgroupId":groupId},
	    	success:function(msg){
	    		if(msg==1){
	    			$("#modifing").css('display','none');
	    			/* $("#content").text("保存成功");
	    			$("#loading-center-absolute").remove(); */
	    			$("#savebtn").attr('disabled',false);
	    		}
	    		
	    	}
	    });
	    
		
	});
	
	</script>
</html>
