/**
 * 
 */
	var dateobj = prev();
	var employeeId=0;
	var jsonstr="";
	var jsonarray = eval('('+jsonstr+')');
			
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
		employeeId=$("#employee").val();
		var employeeName=$("#employee option:checked").text();
		
		renderhtml();    //对日历div中增加表格元素
		showCalendarData(employeeId);  //在表格中显示日期
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
	
	
	
	function showCalendarData(employeeId){
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
			url:"getScheduleTableByEmployeeId",
			data:{"employeeId":employeeId},
			dataType:"json",
			type:"post",
			async:false,
			success:function(table){
				for(var i=0;i<tds.length;i++){
					var thisday=new Date(year,month-1,i+1-firstday.getDay());  //getDay得到第一天是周几 从而显示第一行上个月的数据
					var thisdayStr=getdatestr(thisday);
					tds[i].innerHTML="";
					for(var j=0;j<table.length;j++){
						
						if(dateformat(thisday)==jsonDateFormat(table[j].date)){
							
							var tag="<span style='font-size:16px; font-wight:700'>"+thisday.getDate()+"</span>";
							var startTime=jsonTimeFormat(table[j].startTime);
							var endTime=jsonTimeFormat(table[j].endTime);
							
								
								if(table[j].ifOnduty==1){
									//值班的情况
									
									if(table[j].typeOnduty==1){
										//工作日值班
										tag+="<br/>"+startTime+"~"+(table[j].endTime.hours+3)+":"+table[j].endTime.minutes;
									}else{
										//周末值班
										tag+="<br/>09:00~18:00";
									}
									
									
								}else{
									//不值班
									tag+="<br/>"+startTime+"~"+endTime;
								}
								
							}
							
							tds[i].innerHTML=tag;
							tds[i].setAttribute("data",thisdayStr);
							if(thisdayStr==getdatestr(new Date())){
								tds[i].className="currentDay";   //当天设置属性
							}else if(thisdayStr.substr(0,6)==getdatestr(firstday).substr(0,6)){
								tds[i].className="currentMonth";  //当前月设置属性
							}else{
								tds[i].className="otherMonth";		//其他月的日历设置属性
								$(tds[i]).empty();
								//$(tds[i]).find("input,radio,select").attr("disabled","disabled");
							}
						
					}
					
					
				}
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
	
	function dateformat(date){
		var year=date.getFullYear();
		var month=date.getMonth()+1;  //月份从0开始
		var day=date.getDate();
		month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
		day=(day>9)?(""+day):("0"+day);
		
		return year + '-' + month + '-' + day;  
	}
	
	function jsonTimeFormat(jsonTime){
		var hour=jsonTime.hours;
		var minute=jsonTime.minutes;
		hout=(hour>9)?(""+hour):("0"+hour);
		minute=(minute>9)?(""+minute):("0"+minute);
		
		return hour+':'+minute;
	}
	
	function jsonDateFormat(jsonDate) {//json日期格式转换为正常格式
		
		var year = jsonDate.year+1900;
		var month = jsonDate.month+1;
		var day = jsonDate.date;
		month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
		day=(day>9)?(""+day):("0"+day);
		return year + "-" + month + "-" + day;
		
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
		showCalendarData(employeeId);
		//$(":radio").click(radioclick);
		//alertdate();
	}
	
	function toNextMonth(){
		var date=dateobj.getDate();
		dateobj.setDate(new Date(date.getFullYear(),date.getMonth()+1),1)
		showCalendarData(employeeId);
		//$(":radio").click(radioclick);
		//alertdate();
	}
	
	
	
	/*function radioclick(){
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
		
	}*/
	