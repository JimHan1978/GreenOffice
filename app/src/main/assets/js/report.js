
	
	function getIntervalMonth(startDate,endDate){
        var startMonth = startDate.getMonth();
        var endMonth = endDate.getMonth();
        var intervalMonth = (endDate.getFullYear()*12+endMonth)-(startDate.getFullYear()*12+startMonth);
        return intervalMonth;
	}

	/**
	 * 设置账单数据接口
	 * @param son_str
	 */
	function setData(json,sex,joinDateStr) {
		var result;
		if(typeof(json)=='string'){
			result = JSON.parse(json);
		}else{
			result=json;
		}
        var detail = result.detail;
        //更新首页
        var months = getIntervalMonth(new Date(joinDateStr),new Date());
        $("#name").text(detail.name);
        $("#nameTitle").text(sex==117?"先生":"女士")
        $("#totalMonth").text(months);

        var joinDate = joinDateStr.split("-");
        $("#joinDate").text(joinDate[0] + "年"+joinDate[1] +"月"+joinDate[2] +"日");

        //第二页数据
        $("#income_month").text(detail.bysrhj);

        var historyData = result.historyData;
        var xData=[],yData=[],total=0;
        for(var i=0;i<historyData.length;i++){
            xData[i]=historyData[i].ym.substr(5).replace(/\b(0+)/gi,"")+"月";
            yData[i]=historyData[i].bysrhj;
            total+=Number(yData[i]);
        }
        if(historyData.length==1){
                    $("#accrualDiv").hide();
                }else{
                    $("#accrual").text(detail.bysrhj-yData[historyData.length-2]);
                    $("#accrualDiv").show();
                }

        //更新图表
        updateIncomeChart(xData,yData);
        myChart.resize();

        //第三页数据
        $("#salary").text(detail.zhgzksr);
        $("#cut_late").text(detail.cdkk);
        $("#cut_leave").text(detail.sjkk);
        $("#cut_sick").text(detail.bjkk);
        $("#cut_perf").text(detail.jxjc);
        $("#award_perf").text(detail.jxjc);


        //第四页数据
        $("#housingund").text(detail.gjjlmksr);
        $("#income_cash").text(detail.xjflsr);
        $("#income_subsidy").text(detail.gdbtksr);
        $("#income_bonus").text(detail.jjsr);
        $("#income_other").text(detail.qtjlsr);
        $("#rank_cash").text(detail.beatXjflsrPercent);
        $("#rank_other").text(detail.beatQtjlsrPercent);



        //近2个月出勤情况
        var isAllWork = true;
        //病假
        if(detail.bjkk=='0'){
            $("#sickleave").remove();
            $("#id-sickleave").remove();
        }else{
            isAllWork = false;
        }
        //事假
        if(detail.sjkk=='0'){
            $("#casualleave").remove();
            $("#id-casualleave").remove();
        }else{
            isAllWork = false;
        }

        if(detail.cdkk=='0'){
            $("#beLate").remove();
            $("#id-beLate").remove();
        }else{
            isAllWork = false;
        }

        if(!isAllWork){
            $("#all-work").remove();
            $("#id-all-work").remove();
        }

        //近2个月绩效情况
        var perf = Math.sign(Number(detail.jxjc));
        switch (perf){
            case -1:
                $("#qualified").remove();
                $("#id-qualified").remove();
                $("#reward").remove();
                $("#id-reward").remove();
                break;
            case 1:
                $("#qualified").remove();
                $("#id-qualified").remove();
                $("#dedcut").remove();
                $("#id-dedcut").remove();
                break;
            default:
                $("#reward").remove();
                $("#id-reward").remove();
                $("#dedcut").remove();
                $("#id-dedcut").remove();
                break;
        }

        $("#cumulativeVal").text(total);
        mui(".mui-slider").slider();
	}

	
	function loadBillData(userno,date) {
		//var url = "http://" + serverHost + ":8180/office/weatherBase.json";
		var url = "http://127.0.0.1:8020/MonthReport/data/report.json";
		$.get(url,{userno:userno,date:date},function(dataStr){
			setData(dataStr);
		});
	}
	
	

	//var data1 = [3010, 5052, 7200,9610, 10852, 12200];
	
	function updateIncomeChart(xData,yData) {
		$("#container").css({"width":'420px',"height":"240px"});  
		var year = new Date().getFullYear();

		var option = {
			color: ['#447283'],
            title: {
                text: year+'年月收入',
                x:'center',
                textStyle:{
                	color: '#142C36',
					fontFamily: 'MFYueYuan',
					fontSize: 24,
					align: 'center'
					/*lineHeight: ...,*/
					
                }
                
            },
            grid: {
				containLabel: false
			},
            tooltip: {},
            
            xAxis: {
                data: xData,
            },
            yAxis: {
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

