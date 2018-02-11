<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>工作日安排</title>
	<link rel="stylesheet" href="assets/css/jquery-ui.min.css">
	<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.css">
	<script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
	<script type="text/javascript" src="assets/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="assets/bootstrap/js/bootstrap.js"></script>
</head>
<body>

	<div class="col-xs-12" style="margin-top: 20px" align="center">
		<form action="updateScheduleTableByWork" method="post" class="form-inline">
			<div class="form-group col-xs-offset-12">
			    <label for="startDate">开始日期:</label>
				<input class="form-control" type="text" name="startDate" id="startDate" size="10">
		        </div>
		        <div class="form-group col-xs-offset-12">
		        <label for="endDate">结束日期:</label>
				<input class="form-control" type="text" name="endDate" id="endDate" size="10">
				</div>
				<div class="form-group col-xs-offset-12">
		        <label for="startTime">上班时间:</label>
				<input class="form-control" type="time" name="startTime" id="startTime" size="10">
				</div>
				<div class="form-group col-xs-offset-12">
		        <label for="endTime">下班时间:</label>
				<input class="form-control" type="time" name="endTime" id="endTime" size="10">
		        
			</div>
			<button type="submit" class="btn btn-primary">更新</button>
		</form>
	</div>
</body>

<script type="text/javascript">

$("#startDate").datepicker({
	defaultDate: "+1w",
	changeMonth: true,
    changeYear: true,
    dateFormat:"yy-mm-dd",
    showAnim:"slideDown",
    onClose: function( selectedDate ) {
        $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
      }
});

$("#endDate").datepicker({
	defaultDate: "+1w",
	changeMonth: true,
    changeYear: true,
    dateFormat:"yy-mm-dd",
    onClose: function( selectedDate ) {
        $( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
      }
});



</script>
</html>