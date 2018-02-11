/**
 * attendanceResult.jsp所用的js
 * 获取考勤结果
 */

$(function() {
	$('#date').datetimepicker({
	    format: 'yyyy-mm',
	    autoclose : true,
	    startView : 3,
	    minView : 3
	});
  });

function ifnull(){
	if($("#department").val()=="==请选择=="){
		alert("请选择需要查询的部门");
		return false;
	}
	if($("#date").val()==""){
		alert("请选择需要查询的时间");
		return false;
	}
	var date = $("#date").val();
	var year=parseInt(date.split("-")[0]);
	var month=parseInt(date.split("-")[1]);
	var daycount=getLastDay(year, month); //得到选中月份的最大天数
	
	$("#singlelast").empty();
	$("#singlelast").append("<table id='tablesinglelast'></table>")
	var appendPos=$("#tablesinglelast");
	//$("#tablesinglelast").bootstrapTable('destroy');

	appendPos.append("<thead><tr><th data-field='empNumber' data-valign='middle' rowspan='2' data-align='center'>员工编号</th>" +
			"<th data-field='employeeName' rowspan='2' id='employeeName' data-valign='middle' data-align='center'>员工姓名</th>" +
			"<th data-field='exceptionTime' data-valign='middle' rowspan='2' data-align='center'>打卡异常次数</th>" +
			"<th data-field='lateTime' data-valign='middle' rowspan='2' data-align='center'>迟到打卡次数</th>" +
			"<th data-field='earlyTime' data-valign='middle' rowspan='2' data-align='center'>早退打卡次数</th>" +
			"<th data-field='noDutyTime' data-valign='middle' rowspan='2' data-align='center'>缺卡次数</th>" + 
			"<th data-field='workTime' data-valign='middle' rowspan='2' data-align='center'>出勤天数</th>" + 
			"<th data-field='weekWork' data-valign='middle' rowspan='2' data-align='center'>周末加班天数</th></tr><tr></tr></thead>");
	
	
	for(var i=1;i<=daycount;i++){
		var j=i+1;
		var appendPos2=$("#tablesinglelast").children().eq(0).children().eq(0).children().eq(j-1);
		appendPos2.after("<th data-valign='middle' colspan='1' data-formatter='displaycolor' data-align='center'>"+i+"</th>");

	}
	for(var i=1;i<=daycount;i++){
		var appendPos3=$("#tablesinglelast").children().eq(0).children().eq(1);
		var month2=(month>9)?(""+month):("0"+month);
		var day=(i>9)?(""+i):("0"+i);
		var heredate = year+"-"+month2+"-"+day;
		var weekday = new Date(heredate).getDay();
		 text = "";
        switch (weekday) {
            case 0:
                text = "日";
                break;
            case 1:
                text = "一";
                break;
            case 2:
                text = "二";
                break;
            case 3:
                text = "三";
                break;
            case 4:
                text = "四";
                break;
            case 5:
                text = "五";
                break;
            case 6:
                text = "六";
                break;
        }
		appendPos3.append("<th data-formatter='displaycolor' data-align='center' data-field='"+year+"-"+month2+"-"+day+"'>"+text+"</th>");

	}
	
	readyTable();
	getAttendanceResult();
}
function getAttendanceResult(){
	var department = $("#department").val();
	var date = $("#date").val();
	$.ajax({
		url:"getAttendanceResult",
		type:"post",
		dataType : 'json',
		data:{
			"departmentId":department,
			"date":date,
			},
		success:function(data){
			console.log(data);
			$('#tablesinglelast').bootstrapTable('load', data);
		},
		error:function(){
			alert("获取考勤结果失败");
		}
	});
}
function readyTable(){
	 $('#tablesinglelast').bootstrapTable({//bootstraptable 插件
		 pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20 ,30], 
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: 'asc', 
		 showExport: true,                     //是否显示导出
           exportDataType: 'all', 
           search:true,
          detailView: true,//父子表
           onExpandRow: function (index, row, $detail) {
               oInitInitSubTable(index, row, $detail);
           }
		}); 
}
function displaycolor(value,row,index) {  
	var a = "";  
    if(value == "√") {  
        var a = '<span style="color:#00ff00">'+value+'</span>';  
    }else if(value == "×"){  
        var a = '<span style="color:#FF0000">'+value+'</span>';  
    }else if(value == "未排班"){  
        var a = '<span style="color:#0000ff">未排班</span>';  
    }else{  
        var a = '<span>休息</span>';  
    }  
    return a;  
    }  
function oInitInitSubTable(index, row, $detail) {
    var cur_table = $detail.html('<table></table>').find('table');
    $(cur_table).bootstrapTable({
        pageSize: 5,
        pageList: [5, 10],
        columns: [{
            checkbox: true
        }, {
            field: 'workDate',
            title: '异常打卡日期'
        }, {
            field: 'exception',
            title: '异常类型',
        }, {
            field: 'attendanceStart',
            title: '上班打卡时间'
        }, {
            field: 'attendanceEnd',
            title: '下班打卡时间'
        }, {
            field: 'start',
            title: '排班上班时间'
        },{
            field: 'end',
            title: '排班下班时间'
        },{
            field: 'worktime',
            title: '工作时长'
        },],
        //无线循环取子表，直到子表里面没有记录
        /*onExpandRow: function (index, row, $Subdetail) {
            oInit.InitSubTable(index, row, $Subdetail);
        }*/
    });
    $(cur_table).bootstrapTable('load', row.recordResult);
};

function getLastDay(year,month){   
	 var new_year = year;  //取当前的年份   
	 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）   
	 if(month>12)      //如果当前大于12月，则年份转到下一年   
	 {   
	 new_month -=12;    //月份减   
	 new_year++;      //年份增   
	 }   
	 var new_date = new Date(new_year,new_month,1);        //取当年当月中的第一天   
	 return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期   
}

