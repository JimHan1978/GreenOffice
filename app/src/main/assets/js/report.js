	var serverHost="http://hyserver.hyetec.com:8180/office/"
	
	function getIntervalMonth(startDate,endDate){
        var startMonth = startDate.getMonth();
        var endMonth = endDate.getMonth();
        var intervalMonth = (endDate.getFullYear()*12+endMonth)- (startDate.getFullYear()*12+startMonth) ;
        return intervalMonth;
	}

	function getIntervalDays(startDate,endDate){
        var dateSpan = Math.abs(endDate-startDate);
        var intervalDays = Math.floor(dateSpan / (24 * 3600 * 1000));
        return intervalDays;
	}
	/**
	 * 设置账单数据接口
	 * @param son_str
	 */
	function setData(json,sex,joinDateStr,photo,userId) {
		var result;


		if(typeof(json)=='string'){
			result = JSON.parse(json);
		}else{
			result=json;
		}
        var detail = result.detail;

		//alert(detail.date);
		try{
		    loadBillLottery(userId,detail.date.replace('-',""));
		}catch(e){
		   var  txt =  e.message;
           alert(txt);
		}
       // alert(detail.date);
        var monthArr = detail.date.split("-");
        
        $("#title-ym").text(monthArr[0] + "年"+monthArr[1] +"月");
        //更新首页
        var intervalDays = getIntervalDays(new Date(joinDateStr),new Date());
        $("#name").text(detail.name);
        //$("#nameTitle").text(sex==117?"先生":"女士")
        $("#intervalDays").text(intervalDays);
        //var photo = 'upload/2019/05/16/e0407c31-e3ce-4ca7-9c69-ef2449123b02.jpg';
        $("#tips-1").css("background-image","url('"+photo+"')");

        var joinDate = joinDateStr.split("-");
        $("#joinDate").text(joinDate[0] + "年"+joinDate[1] +"月"+joinDate[2] +"日");
        //第二页数据
        $("#income_month").text(Math.round(detail.bysrhj));


        var historyData = result.historyData;
        var xData=[],yData=[],total=0,len = historyData.length;
        var start = len>6?len-6:0
        for(var i=start;i<historyData.length;i++){
            xData.push(historyData[i].ym.substr(5).replace(/\b(0+)/gi,"")+"月");
            yData.push(historyData[i].bysrhj);
        }

        for(var i=0;i<historyData.length;i++){
            total+=Number(historyData[i].bysrhj);
        }

        if(historyData.length==1){
            $("#accrualDiv").hide();
        }else{
        	var accrual = Math.round(detail.bysrhj-historyData[len-2].bysrhj);

        	
        	var atext = "";
        	if(accrual>=0){
        		atext="增加  "+Math.abs(accrual);
        	}else if(accrual<0){
        		atext="降低  "+Math.abs(accrual);
        	}
        	
            $("#accrual").text(atext);
            $("#accrualDiv").show();
        }
        //alert();
        //更新图表
        updateIncomeChart(xData,yData);
        myChart.resize();

        //第三页数据
        $("#salary").text(Math.round(detail.zhgzksr));
        
        if(detail.jxjc>0){
        	$("#award_perf").text(Math.round(detail.jxjc));
        }else if(detail.jxjc<0){
        	$("#cut_perf").text(-Math.round(detail.jxjc));
        }
        
        //第四页数据
        $("#housingund").text(Math.round(detail.gjjlmksr));
        $("#income_subsidy").text(Math.round(detail.gdbtksr));
        
        $("#income_cash").text(Math.round(detail.xjflsr));//福利
        $("#rank_cash").text(detail.beatXjflsrPercent);
        
        $("#income_bonus").text(Math.round(detail.jjsr));//奖励
        $("#rank_bonusIncomePercent").text(detail.beatBonusIncomePercent);
        
        $("#income_other").text(Math.round(Number(detail.attendanceYoyo)+Number(detail.activityYoyo)));//红包
        $("#income_rp_kq").text(detail.attendanceYoyo);
        $("#rank_other_kq").text(detail.attendanceYoyoPercent);
        $("#income_rp_hd").text(detail.activityYoyo);
        $("#rank_other_hd").text(detail.activityYoyoPercent);
		

        //近2个月出勤情况
        var isAllWork = true;
        
        var span_ic="<span class=\"icon_kq\"></span>";
        //病假
        if(detail.bjkk=='0'){
        	//<li class="animated bounceInDown"><span class="icon_kq"></span>您本月全勤,再接再励!</li>
        	//$("<li></li>").append($("<span></span>").addClass("icon_kq")).append("您本月全勤,再接再励!");
            //$("#sickleave").remove();
            $("#li-sickleave").remove();
        }else{
        	$("#cut_sick").text(Math.round(detail.bjkk));
            isAllWork = false;
        }
        //事假
        if(detail.sjkk=='0'){
            //$("#casualleave").remove();
            $("#li-casualleave").remove();
        }else{
        	$("#cut_leave").text(Math.round(detail.sjkk));
            isAllWork = false;
        }

        if(detail.cdkk=='0'){
            //$("#beLate").remove();
            $("#li-beLate").remove();
        }else{
        	 $("#cut_late").text(Math.round(detail.cdkk));
            isAllWork = false;
        }

        if(!isAllWork){
            $("#li-all-work").remove();
        }

        //近2个月绩效情况
        var perf = Math.sign(Number(detail.jxjc));
        switch (perf){
            case -1:
                $("#li-qualified").remove();
                $("#li-reward").remove();
                break;
            case 1:
                $("#li-qualified").remove();
                $("#li-dedcut").remove();
                break;
            default:
                $("#li-reward").remove();
                $("#li-dedcut").remove();
                break;
        }

        $("#cumulativeVal").text(Math.round(total));
        mui(".mui-slider").slider();
	}

	
//	function loadBillData(userno,date) {
//		//var url = "http://" + serverHost + ":8180/office/weatherBase.json";
//		var url = "http://127.0.0.1:8020/assets/data/report.json";
//		$.get(url,{userno:userno,date:date},function(dataStr){
//			setData(dataStr,117,'2019-02-01');
//		});
//	}
	function loadBillLottery(userId,date) {
    		var url = serverHost + "lottery/getBillLotteryInfo.json";
    		//var url = "http://127.0.0.1:8180/assets/data/report.json";

    	$.ajax({
        		  url : url,
        		  xhrFields: {
        		    withCredentials: true // 设置运行跨域操作
        		  },
        		  data : {userId:userId,actId:date},
        		  success : function(data) {
        		    if(data.success){
        				initLottery(userId,date,data.result.winAmount);
        			}
        		  },
        		  error: function (jqXHR, textStatus, errorThrown) {
                    /*弹出jqXHR对象的信息*/
//                    alert(jqXHR.responseText);
//                    alert(jqXHR.status);
//                    alert(jqXHR.readyState);
//                    alert(jqXHR.statusText);
//                    /*弹出其他两个参数的信息*/
//                    alert(textStatus);
//                    alert(errorThrown);
                }

    		});


    		/*$.get(url,{userId:userId,actId:date},function(data){
    			if(data.success){
    				initLottery(userId,actId,lotteryAmount);
    			}
    		});*/
    	}
	

	//var data1 = [3010, 5052, 7200,9610, 10852, 12200];
	
	function updateIncomeChart(xData,yData) {
		$("#container").css({"width":'18rem',"height":"16rem","float":"left","margin-top":"-4rem","margin-left":"-4rem"});  
		var now = new Date();
		/*var title = now.getFullYear()+"年1月收入";
		if(xData.length>1){
			title = now.getFullYear()+"年1月-"+xData[xData.length-1]+"收入";
		}*/
		/*for (i=0;i<yData.length;i++) {
			yData[i]=yData[i]/1000;
		}*/
		
		var option = {
			color: ['#87CDFA'],
            /*title: {
                text: title,
                  x: 'center',    
                textStyle:{
                	color: '#142C36',
					fontFamily: 'MFYueYuan',
					fontSize: 24,
					align: 'center'
                }
            },*/
            grid: {
				containLabel: false
			},
            tooltip: {
            	show: false
            },
            
            xAxis: {
                data: xData,
                show:false
            },
            yAxis: {
            	show:false,
				type: 'value',
				interval:20
			},
            series: [{
                name: '月收入',
                type: 'bar',
                data: yData,
				barWidth:12
            }]
        };
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);

	}
	//updateIncomeChart(data1);
	
	//app.title = '坐标轴刻度与标签对齐';

