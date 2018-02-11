<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!--此属性为文档兼容模式声明，表示如果在IE浏览器下则使用最新的标准渲染当前文档-->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--视口:在移动浏览器中，当页面宽度超出设备，浏览器内部虚拟的一个页面容器，将页面容器缩放到设备这么大，然后展示-->
		<!-- 上面三个必须先导入-->
		<title>hello</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<script src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
		<!--jquery必须先导入-->
		<title></title>
	</head>
	<body style="height: 100%">
		<div class="tabbable" style="height: 100%"> <!-- Only required for left/right tabs -->
  <ul class="nav nav-tabs">
    <li><a href="${pageContext.request.contextPath}/showAnalysis" target="content">排班结果</a></li>
    <li><a href="${pageContext.request.contextPath}/attendanceRecord" target="content">考勤记录</a></li>
    <li><a href="${pageContext.request.contextPath}/attendanceResult" target="content">考勤结果</a></li>
  </ul>
  
 
  <div class="tab-content" style="height: 92%">
    <div class="tab-pane active" id="tab1" style="height: 100%">
	<iframe src="" name="content" id="operate" frameborder="0"
					 width="100%" height="100%"></iframe>
	</div>
  </div>
</div>
	</body>

</html>

