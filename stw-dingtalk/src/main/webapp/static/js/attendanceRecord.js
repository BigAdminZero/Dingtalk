/**
 * attendanceRecord.jsp所用的js
 * 获取考勤记录
 */

function getAttendanceRecord(){
	var employeeId = $("#employee").val();
	var starttime = $("#from").val();
	var endtime = $("#to").val();
	
	$.ajax({
		url:"getAttendanceRecord",
		type:"post",
		dataType : 'json',
		data:{
			"starttime":starttime,
			"employeeId":employeeId,
			"endtime":endtime
			},
		success:function(data){
			$('#tablesinglelast').bootstrapTable('load', data.recordResult);
			/* $('#tablesinglelast').bootstrapTable({//bootstraptable 插件

				 showExport: true,                     //是否显示导出
		            exportDataType: "basic", 
				    
				    data: data.recordResult
				});*/
		},
		error:function(){
			alert("获取考勤记录失败");
		}
	});
}