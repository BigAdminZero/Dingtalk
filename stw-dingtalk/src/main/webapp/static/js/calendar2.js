/**
 * 
 *//**
 * 
 */
	var dateobj = prev();
	var departmentId=0;
	/*var jsonstr="";
	var jsonarray = eval('('+jsonstr+')');*/
			
	function prev(){
	    var _date = new Date();    // 默认为当前系统时间
	    return {
	      getDate : function(){
	        return _date;
	      },
	      setDate : function(date) {
	        _date = date;
	      }
	    };
	  }
	
	function alertdate(){
		
		$("#calendar").empty();
		departmentId=$("#department").val();
		//alert(departmentId);
		var employeeName=$("#employee option:checked").text();
		
		renderhtml();    //对日历div中增加表格元素
		showCalendarData();  //在表格中显示日期
		bindEvent();   //对表格上方的上月下月绑定事件
		jsonstr="[{'startTime':'00:00','endTime':'00:00','ifOnduty':0,'typeOnduty':0,'cordate':20180103,'employeeId':0,'scheduleTableId':0}]";
		jsonarray = eval('('+jsonstr+')')
	}
	
	
	function renderhtml(){
		var calendar=document.getElementById("calendar");
		var titlebox=document.createElement("div");  //在div中设置标题区  显示当前月份和上月下月的控制
		var bodybox=document.createElement("div");   //主体部分 用于存放日历表格
		
		titlebox.className="calendar-title-box";   //给标题box绑定属性   并向div中添加元素
		titlebox.innerHTML="<span id='prevMonth' class='prev-month'></span>"+
		"<span class='calendar-title' id='calendarTitle'></span>"+
		"<span class='next-month' id='nextMonth'></span>";
		
		 calendar.appendChild(titlebox);  //将标题box设为日历容器的子节点
		 bodybox.className='calendar-body-box';   //表格部分绑定属性
		 var headhtml="<tr>"+"<th>日</th>"+"<th>一</th>"+
		 "<th>二</th>"+"<th>三</th>"+"<th>四</th>"+
		 "<th>五</th>"+"<th>六</th>"+"</tr>";
		 var bodyhtml="";
		 
		 for(var i=0;i<6;i++){		//一周7天 字符串拼接放到bodyhtml中
		 	bodyhtml+="<tr>"+"<td></td>"+"<td></td>"+"<td></td>"+
		 	"<td></td>"+"<td></td>"+"<td></td>"+"<td></td>"+"</tr>";
		 }
		 bodybox.innerHTML="<table id='calendarTable' class='table table-bordered text-center'>"+
		 headhtml+bodyhtml+"</table>";  //设置表格box的内容
		 
		 calendar.appendChild(bodybox);
		 
	}
	
	
	
	function showCalendarData(){
		//alert(employee);
		var year=dateobj.getDate().getFullYear();   //dateobj为object对象 通过getDate得到日期 再得到年
		var month=dateobj.getDate().getMonth()+1;
		var datestr=getdatestr(dateobj.getDate());  //通过函数gatdatestr将dateobj转换成字符串格式
		
		//设置表格顶部的年月信息
		var calendarTitle=document.getElementById("calendarTitle");
		var titleStr=datestr.substr(0,4)+"年"+datestr.substr(4,2)+"月";  //通过substr截取字符串  得到年的数字和月份数字
		calendarTitle.innerText=titleStr;   //将拼接后的****年**月显示在上方
		
		//设置表格中的日期数据
		var table=document.getElementById("calendarTable");
		var tds=table.getElementsByTagName("td");
		var firstday=new Date(year,month-1,1);  //当前月的第一天
		//alert(firstday.getDay());
		
		
		$.ajax({
			url:"getDepartmentOnDutyByDepartmentId",
			data:{"departmentId":departmentId},
			dataType:"json",
			type:"post",
			async:false,
			success:function(table){
				for(var i=0;i<tds.length;i++){
					var thisday=new Date(year,month-1,i+1-firstday.getDay());  //getDay得到第一天是周几 从而显示第一行上个月的数据
					var thisdayStr=getdatestr(thisday);
						
						if(table.length==0){
							var tag="<span style='font-size:16px; font-wight:700'>"+thisday.getDate()+"</span>"+
							"<br/><span class='glyphicon glyphicon-plus-sign' data-toggle='modal' data-target='#myModalNew' onclick='chooseType("+thisdayStr+")' style='color:green' ></span>";
						}else{
							var tag="<span style='font-size:16px; font-wight:700'>"+thisday.getDate()+"</span>";
							for(var j=0;j<table.length;j++){
								
								if(dateformat(thisday)==jsonDateFormat(table[j].date)){
									var type="";
									if(table[j].typeOnduty=='1'){
										type="工作日值班";
									}else{
										type="周末值班";
									}
									tag+="<br/><input type='hidden' value='"+thisdayStr+"'><span>"+table[j].employeeName+"</span>&nbsp&nbsp&nbsp<span>"+type+"</span>&nbsp&nbsp<span class='glyphicon glyphicon-remove-circle' style='color:red' id='"+thisdayStr+table[j].employeeId+"' onclick='del(this.id)'></span>";
									
								}
							}
							tag+="<br/><span class='glyphicon glyphicon-plus-sign' data-toggle='modal' data-target='#myModalNew' onclick='chooseType("+thisdayStr+")' style='color:green' ></span>";
						}
						
						
					
					tds[i].innerHTML=tag;
					tds[i].setAttribute("data",thisdayStr);
					
					
					if(thisdayStr.substr(0,6)==getdatestr(firstday).substr(0,6)){
						tds[i].className="currentMonth";  //当前月设置属性
					}else{
						tds[i].className="otherMonth";		//其他月的日历设置属性
						$(tds[i]).empty();
						//$(tds[i]).find("input,radio,select").attr("disabled","disabled");
					}
					
					if(thisdayStr==getdatestr(new Date())){
						tds[i].classList.add("currentDay");
						//tds[i].className="currentDay";   //当天设置属性
					}
				}
			},error:function(){
				alert(1111);
			}
		});
		
		

		
		
		/*$("input[name='startTime']").change(recordmessage);
		$("input[name='endTime']").change(recordmessage);
		$(":radio").click(radioclick);
		$(":radio").click(recordmessage);
		$("select[name='typeOnduty']").change(recordmessage);*/
		
		//alert(dateobj.getdate().getDay());
		
	}
	
	
	function bindEvent(){
		var prevMonth=document.getElementById("prevMonth");
		var nextMonth=document.getElementById("nextMonth");
		addEvent(prevMonth,'click',toPrevMonth); 	//绑定点击事件
		addEvent(nextMonth,'click',toNextMonth);
		//addEvent($(":radio"),'click',toChooseSch);
	}
	
	function getdatestr(date){
		var year=date.getFullYear();
		var month=date.getMonth()+1;  //月份从0开始
		var day=date.getDate();
		month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
		day=(day>9)?(""+day):("0"+day);
		
		return year+month+day;
	}
	
	function getdatestr2(date){
		var year=date.getFullYear();
		var month=date.getMonth()+1;  //月份从0开始
		var day=date.getDate();
		month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
		day=(day>9)?(""+day):("0"+day);
		
		return year + '-' + month + '-' + day;
	}
	
	function dateformat(date){
		var year=date.getFullYear();
		var month=date.getMonth()+1;  //月份从0开始
		var day=date.getDate();
		month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
		day=(day>9)?(""+day):("0"+day);
		
		return year + '-' + month + '-' + day;  
	}
	
	function jsonDateFormat(jsonDate) {//json日期格式转换为正常格式
		
		var year = jsonDate.year+1900;
		var month = jsonDate.month+1;
		var day = jsonDate.date;
		month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
		day=(day>9)?(""+day):("0"+day);
		return year + "-" + month + "-" + day;
		
	}
	function jsonDateFormat2(jsonDate) {//json日期格式转换为正常格式
		
		var year = jsonDate.year+1900;
		var month = jsonDate.month+1;
		var day = jsonDate.date;
		month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
		day=(day>9)?(""+day):("0"+day);
		return year+month+day;
		
	}
	
	function jsonTimeFormat(jsonTime){
		
		var hour=jsonTime.hours;
		var minute=jsonTime.minutes;
		hour=(hour>9)?(""+hour):("0"+hour);
		minute=(minute>9)?(""+minute):("0"+minute);
		
		return hour+":"+minute;
	}
	
	function addEvent(dom,eType,func){
		if(dom.addEventListener){
			dom.addEventListener(eType,function(e){
				func(e);
			});
		}else if(dom.attachEvent){
			dom.attachEvent('on'+eType,function(e){
				func(e);
			});
		}else{
			dom['on'+eType]=function(e){
				func(e);
			}
		}
	}
	
	function toPrevMonth(){
		var date=dateobj.getDate();
		dateobj.setDate(new Date(date.getFullYear(),date.getMonth()-1,1));
		showCalendarData();
		//$(":radio").click(radioclick);
		//alertdate();
	}
	
	function toNextMonth(){
		var date=dateobj.getDate();
		dateobj.setDate(new Date(date.getFullYear(),date.getMonth()+1),1)
		showCalendarData();
		//$(":radio").click(radioclick);
		//alertdate();
	}
	
	function radioclick(){
		var parentCode=$(this).parent();
		if($(this).val()=='true'){
			if(parentCode.children("select").length==0){
				parentCode.children("label").show();
				parentCode.append("<select name='typeOnduty'><option value='2'>周末值班</option>"+
									"<option value='1'>工作日值班</option></select>");
			}
		}else{
			
			parentCode.children("select").remove();
			parentCode.children("label").hide();
			
		}
		$("select[name='typeOnduty']").change(recordmessage);
	}
	
	function recordmessage(){
		
		var parentCode=this.parentNode;
	
		var startTime=$(parentCode.getElementsByTagName("input")[0]).val();
		var endTime=$(parentCode.getElementsByTagName("input")[1]).val();
		var scheduleTableId=$(parentCode.getElementsByTagName("input")[2]).val();
		var typeOnduty=$(parentCode.getElementsByTagName("select")[0]).val();
		var cordate=$(parentCode).attr("data");
		var ifOnduty=2;
		var flag=0;
		var index="";
		if(typeOnduty!=null){
			ifOnduty=1;
		}else{
			typeOnduty=1;
		}
		
		//alert(startTime+"|"+endTime+"|"+employeeId+"|"+cordate+"|"+typeOnduty+"|"+scheduleTableId);
		
		var arr  =
	     {
		         "startTime" : startTime,
		         "endTime" : endTime,
		         "ifOnduty": ifOnduty,
		         "typeOnduty": typeOnduty,
		         "cordate": cordate,
		         "employeeId": employeeId,
		         "scheduleTableId": scheduleTableId
		     }
		
		for(var i=0;i<jsonarray.length;i++){
			if(jsonarray[i].scheduleTableId==scheduleTableId){
				flag=true;
				index=i;
			}
		}
		
		if(flag==true){
			jsonarray[index].startTime=startTime;
			jsonarray[index].endTime=endTime;
			jsonarray[index].ifOnduty=ifOnduty;
			jsonarray[index].typeOnduty=typeOnduty;
			jsonarray[index].cordate=cordate;
			jsonarray[index].employeeId=employeeId;
		}else{
			jsonarray.push(arr);
		}
	}
	
	function updateTable(){
		
		var jsonstring=JSON.stringify(jsonarray);
		
		$.ajax({
			url:"updateTableByEmployeeId",
			type:"post",
			data:{"jsonarray":jsonstring},
			success:function(msg){
				
			}
		});
		
	}

var date;

	function chooseType(a){
		date = a;
		putEmployeeList3();
		var data = [];
				data.push({
					label : '按值班组添加',
					value : 1
				});
				data.push({
					label : '按员工添加',
					value : 2
				});
		$('#type').multiselect({
			maxHeight : 200,
		});
		$('#type').multiselect('dataprovider', data);
		var type = 1;
		getDepartmentAndEmployeeList(type);
	}
	function changeType(value){
		getDepartmentAndEmployeeList(value);
	}
	function getDepartmentAndEmployeeList(type) {

		$.ajax({
			method : 'post',
			url : "getEmployeeByOnDutyAndAll",
			dataType : 'json',
			data : {
				"departmentId" : departmentId
			},
			success : function(jsondata) {
				var data = [];
				if (type==1) {
					for (var j = 0; j < jsondata.length-1; j++) {
						data.push({
							label : ''
									+ jsondata[j].groupName,
							value : jsondata[j].groupId
						});
					}
					$('#OnDutyEmployeeByDepartmentList').removeAttr("multiple");
					putEmployeeList1(data);
				}else{
						var group = {
								label : '' + jsondata[jsondata.length-1].groupName,
								children : []
							};
						var member = jsondata[jsondata.length-1].member;
						for (var j = 0; j < member.length; j++) {
							group['children'].push({
								name : member[j].employeeName,
								label : ''
										+ member[j].employeeName,
								value : member[j].employeeId
							});	
						}
						data.push(group);
						$('#OnDutyEmployeeByDepartmentList').attr("multiple","multiple");
						putEmployeeList2(data);

					
				}
				$("#date").val(date);
				$("#departmentId").val(departmentId);
				$("#datespan").html(date);
			},
			error : function(data) {
				alert("新增模块获取部门成员列表错误");
			}
		});
		
	}
	function putEmployeeList1(data) {
		$('#OnDutyEmployeeByDepartmentList').multiselect({
			maxHeight : 200,
		});
		$('#OnDutyEmployeeByDepartmentList').multiselect('dataprovider', data);
	}
	function putEmployeeList3() {
		var data = [];
		data.push({
			label : '工作日值班',
			value : 1
		});
		data.push({
			label : '周末值班',
			value : 2
		});
		$('#ondutyType').multiselect({
			maxHeight : 200,
		});
		$('#ondutyType').multiselect('dataprovider', data);
	}
	function putEmployeeList2(data) {
		$('#OnDutyEmployeeByDepartmentList').multiselect({
			enableClickableOptGroups: true,
			includeSelectAllOption: true,
			maxHeight : 200,
		});
		$('#OnDutyEmployeeByDepartmentList').multiselect('dataprovider', data);
	}
  function del(id){
	  var choosedate = $("#"+id).parent().children().get(2).value;
	  var employeeID = id.substring(8,id.length);
	  $.ajax({
			method : 'post',
			url : "updateByDepartmentOnDuty",
			data : {
				"date" : choosedate,
				"employeeId" : employeeID
			},
			success : function() {
				alertdate();
			},
			error : function(data) {
				alert("新增模块获取部门成员列表错误");
			}
		});
		
  }
  
  function defMonth(str){
	  
	  var defmon=parseInt(str)+1;
	  if(defmon==13){
		  return 1;
	  }
	  
	  return defmon;
  }
  
  function updateByAjax(){
	  
	  var typee=$('#type').val();
	  
	  var OnDutyEmployeeByDepartmentList=$('#OnDutyEmployeeByDepartmentList').val();
	  
	  var departmentIdd=$("#departmentId").val();
	  
	  var datee=$("#date").val();
	  
	  var ondutyTypee=$("#ondutyType").val();
	  
	  $.ajax({
		  url:"OnDutyEmployeeByDepartmentList",
		  type:"post",
		  data:{"type":typee,"OnDutyEmployeeByDepartmentList":OnDutyEmployeeByDepartmentList,
			  "departmentId":departmentIdd,"date":datee,"ondutyType":ondutyTypee},
		  success:function(msg){
			  showCalendarData();
			  $('#myModalNew').modal('hide');
		  },
		  error:function(){
			  alert("ajax错误");
		  }
	  });
	  
	  
	  
  }
		