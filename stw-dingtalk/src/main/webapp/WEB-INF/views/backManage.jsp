<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
		<style type="text/css">
			body {
    width: 100%;
    height: 100%;
    margin: 0;
    overflow: hidden;
    background-color: #FFFFFF;
    font-family: "Microsoft YaHei", sans-serif;
 }
 .navbar-collapse{
    padding-left: 5px;
    padding-right: 5px;
 }
 .nav>li{
     text-align: center;
 }
 .nav>li>a{
     color:#444;
     margin: 0 5px;
 }
 .nav-pills>li.active>a, .nav-pills>li.active>a:focus, .nav-pills>li.active>a:hover{
     background-color: #222222;
 }
 .dropdown-menu{
     min-width: 200px;
     margin-left: 40px;
     background-color: #E3E3E3;
 }
 .dropdown-menu>li>a{
     padding: 10px 15px;
 }
 .pageSidebar{
    width:20%;
    height:100%;
    padding-bottom: 30px;
    overflow: auto;
    background-color: #e3e3e3;
 }
 .pageContent{
     height: 100%;
     width:80%;
     min-width: 768px;
     float:right;
     top: 0;
     right: 0;
     z-index: 3;
     padding-bottom: 30px;
     position: absolute;
 }
 .pageContainer{
     bottom: 0;
     left:0;
     right: 0;
     top: 53px;
     overflow: auto;
     position: absolute;
     width: 100%;
 }
 .footer {
     width: 100%;
     height: 30px;
     line-height: 30px;
     margin-top: 0;
     left: 0;
     right: 0;
     bottom: 0;
     position: absolute;
     z-index: 10;
     background-color:#DFDFDF;
 }
		</style>
		<script type="text/javascript">
		$(function(){
			$(".nav li").click(function() {
				
    				$(".active").removeClass('active');
    				$(this).addClass("active");
			});
			
		});
			
		</script>
		
	</head>
	<body>
		<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" title="logoTitle" href="#">考勤后台管理系统</a>
       </div>
       <div class="collapse navbar-collapse">
           <ul class="nav navbar-nav navbar-right">
               <li role="presentation">
                   <a href="${pageContext.request.contextPath}/dutygroup">当前用户：<span class="badge">${nowUser.userName}</span></a>
               </li>
               <li>
                   <a href="${pageContext.request.contextPath}/user/logout">
                         <span class="glyphicon glyphicon-lock"></span>退出登录</a>
                </li>
            </ul>
       </div>
    </div>      
</nav>
<!-- 中间主体内容部分 -->
<div class="pageContainer">
     <!-- 左侧导航栏 -->
     <div class="pageSidebar">
         <ul class="nav nav-stacked nav-pills">
             <li role="presentation">
                 <a href="${pageContext.request.contextPath}/showAuthorityPage" target="mainFrame" >权限管理</a>
             </li>
             <li class="dropdown">
                 <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">组织管理<span class="caret"></span>
                 </a>
                 
                 <ul class="dropdown-menu">
                     <li>
                         <a href="${pageContext.request.contextPath}/showDepartmentManager" target="mainFrame">部门管理</a>
                     </li>
                     <li>
                         <a href="${pageContext.request.contextPath}/showEmployeeManager" target="mainFrame">员工管理</a>
                     </li>
                     <li>
                         <a href="${pageContext.request.contextPath}/showEmpNumberEditing" target="mainFrame">员工编号管理</a>
                     </li>
                 </ul>
             </li>
             <li role="presentation">
                 <a href="${pageContext.request.contextPath}/showAnalysisPage" target="mainFrame">考勤统计管理</a>
             </li>
             
             <li class="dropdown">
                 <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">排班管理<span class="caret"></span>
                 </a>
                 
                 <ul class="dropdown-menu">
                     <li>
                         <a href="${pageContext.request.contextPath}/showScheduleTemplatePage" target="mainFrame">排班模板编辑</a>
                     </li>
                     <li>
                         <a href="${pageContext.request.contextPath}/showSchedulePage" target="mainFrame">排班</a>
                     </li>
                     <li>
                         <a href="${pageContext.request.contextPath}/showApplyPage" target="mainFrame">请假出差申请</a>
                     </li>
                 </ul>
             </li>
             
         </ul>
     </div>
     <!-- 正文内容部分 -->
     <div class="pageContent">
         <iframe src="" id="mainFrame" name="mainFrame" frameborder="0" width="100%"  height="100%" frameBorder="0"></iframe>
     </div>
 </div>
 <!-- 底部页脚部分 -->
 <div class="footer">
     <p class="text-center">
         
     </p>
 </div>
		
	</body>
</html>
