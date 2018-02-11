<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>排班模板编辑（新增/修改）</title>
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrapValidator.css" />
		<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.js" ></script>
		<script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrapValidator.js" ></script>
		
		<script>



        $(function(){



            $('form').bootstrapValidator({


                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields:{

                	scheduleGroupName:{

                        validators:{
                            notEmpty:{
                                message:"组名不能为空"
                            },
                            threshold :  6 , //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                            remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                                url: 'checksgroupName',//验证地址
                                message: '用户已存在',//提示消息
                                delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                                type: 'POST'//请求方式
                               
                            }
                        }
                    },
                    starttime:{

                        validators:{
                            notEmpty:{
                                message:"上班时间不能为空"
                            },
                            regexp:{

                                regexp:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,
                                message:"时间格式不对"

                            }
                        }
                    },
                    endtime:{

                        validators:{
                            notEmpty:{
                                message:"下班时间不能为空"
                            },
                            regexp:{

                                regexp:/^(20|21|22|23|[0-1]\d):[0-5]\d$/,
                                message:"时间格式不对"

                            }
                        }
                    }
                }
            });



        });

    </script>
	</head>
	<body>
		
		<div class="col-xs-4 col-xs-offset-4" style="margin-top: 50px">
			
			<c:if test="${ScheduleGroup==null}">
			<form action="add_schedulegroup" method="post">

		        <div class="form-group">
		            <label for="id">组名：</label>
		            <input type="text" name="scheduleGroupName" class="form-control" id="groupname" placeholder="请输入组名">
		        </div>
		
		
		        <div class="form-group">
		            <label for="id">上班时间：</label>
		            <input type="time" name="starttime" class="form-control"  placeholder="上班时间 格式：09:00">
		        </div>
		
		        <div class="form-group">
		            <label for="id">下班时间：</label>
		            <input type="time" name="endtime" class="form-control"  placeholder="下班时间 格式：21:00">
		        </div>
		
		        <div class="form-group">
		            <label for="id">星期：</label>
		            <div class="checkbox">
                		<label><input type="checkbox" name="weeks" value="一" checked="checked" />一</label>
                		<label><input type="checkbox" name="weeks" value="二" checked="checked" />二</label>
                		<label><input type="checkbox" name="weeks" value="三" checked="checked" />三</label>
                		<label><input type="checkbox" name="weeks" value="四" checked="checked" />四</label>
                		<label><input type="checkbox" name="weeks" value="五" checked="checked" />五</label>
                		<label><input type="checkbox" name="weeks" value="六" />六</label>
                		<label><input type="checkbox" name="weeks" value="日" />日</label>
                		
			        </div>
		        </div>
		
		        <button type="submit" class="btn btn-default">提交</button>
		        <a href="showScheduleTemplatePage" class="btn btn-default">返回</a>
		
		    </form>
		    </c:if>
		    
		    <c:if test="${ScheduleGroup!=null}">
			<form action="modify_schedulegroup?scheduleGroupId=${ScheduleGroup.scheduleGroupId}" method="post">

		        <div class="form-group">
		            <label for="id">组名：</label>
		            <input type="text" name="scheduleGroupName" class="form-control" id="groupname" value="${ScheduleGroup.scheduleGroupName}">
		        </div>
		
		
		        <div class="form-group">
		            <label for="id">上班时间：</label>
		            <input type="time" name="starttime" class="form-control" value="<fmt:formatDate value="${ScheduleGroup.startTime}" pattern="HH:mm"/>">
		        </div>
		
		        <div class="form-group">
		            <label for="id">下班时间：</label>
		            <input type="time" name="endtime" class="form-control" value="<fmt:formatDate value="${ScheduleGroup.endTime}" pattern="HH:mm"/>">
		        </div>
		
		        <div class="form-group">
		            <label for="id">星期：</label>
		            <div class="checkbox">
                		<label><input type="checkbox" name="weeks" value="一" />一</label>
                		<label><input type="checkbox" name="weeks" value="二" />二</label>
                		<label><input type="checkbox" name="weeks" value="三" />三</label>
                		<label><input type="checkbox" name="weeks" value="四" />四</label>
                		<label><input type="checkbox" name="weeks" value="五" />五</label>
                		<label><input type="checkbox" name="weeks" value="六" />六</label>
                		<label><input type="checkbox" name="weeks" value="日" />日</label>
                		
			        </div>
		        </div>
		
		        <button type="submit" class="btn btn-primary">提交</button>
		        <a href="showScheduleTemplatePage" class="btn btn-default">返回</a>
		
		    </form>
		    </c:if>
			
		</div>
		
	</body>
	<script type="text/javascript">
	
	$(function(){
		var week="${ScheduleGroup.week}";
		
		$("input[name='weeks']").each(function(){
			if(week.indexOf($(this).val(), 0)!=-1){
				$(this).attr("checked",'true')
			} 
			//alert(week.indexOf($(this).val(), 0))
		});
	});
	
	</script>
</html>
