<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>肾泰网员工考勤管理系统首页</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
        <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Oleo+Script:400,700'>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/loading.css">
        <script src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.min.js"></script>
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->


</head>
<body style="width: 100%;height: 100%">
		<div class="header">
            <div class="container">
                <div class="row">
                    <div class="logo span12">
                        <h1>肾泰网员工考勤管理系统</h1>
                    </div>
                    <!-- <div class="links span">
                        <a class="home" href="" rel="tooltip" data-placement="bottom" data-original-title="Home"></a>
                        <a class="blog" href="" rel="tooltip" data-placement="bottom" data-original-title="Blog"></a>
                    </div> -->
                </div>
            </div>
        </div>

        <div class="register-container container">
            <div class="row">
                <div class="iphone span5">
                </div>
                <div class="register span6">
                    <form>
                        <h2>登录到 <span class="red"><strong>AMS</strong></span></h2>
                        <label for="firstname">用户名</label>
                        <input type="text" id="firstname" placeholder="enter your name...">
                        <label for="password">密码</label>
                        <input type="password" id="password" placeholder="enter password...">
                        <button type="button" id="loginbtn">登录</button>
                        ${message}
                    </form>
                </div>
            </div>
        </div>
        <div id="loading" style="display: none;">
			<div id="loading-center">
				<div id="loading-center-absolute">
					<div class="object" id="object_one"></div>
					<div class="object" id="object_two" style="left:20px;"></div>
					<div class="object" id="object_three" style="left:40px;"></div>
					<div class="object" id="object_four" style="left:60px;"></div>
					<div class="object" id="object_five" style="left:80px;"></div>
				</div>
				<div id="content">
			      	
			      	每月首次登陆时间较长，<br />
			      	请勿关闭浏览器，耐心等待谢谢
			        
			    </div>
			</div>
		</div>
		<div id="error" style="display: none;">
			<div id="error-center">
				<div id="error-content">
			      	
			      	用户名或密码错误
			        
			    </div>
			</div>
		</div>
        <!-- Javascript -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery-1.8.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.backstretch.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>

</body>
<script type="text/javascript">

$("#loginbtn").click(function(){  
	
	
    var userName=$("#firstname").val();
    var password=$("#password").val();
    
    $.ajax({
    	url:"${pageContext.request.contextPath}/user/login",
    	type:"post",
    	async:false,
    	data:{"userName":userName,"password":password},
    	success:function(msg){
    		//后台没问题 ajax执行成功
    		
    		if(msg==1){
    			$("#loading").css('display','block');
    		    $("#loginbtn").attr('disabled',true); //设置变灰按钮  
    		    window.location.href="${pageContext.request.contextPath}/user/addALLSchedule";
    		}else{
    			$("#error").css('display','block');
    			setTimeout(function(){
    			    $("#error").hide();
    			},2000);
    		}
    		
    	},
    	error:function(){
    		alert("登陆发生错误");
    	}
    });
       
});

</script>
</html>