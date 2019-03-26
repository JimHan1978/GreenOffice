(function($) {
	
	function getIntervalMonth(startDate,endDate){
        var startMonth = startDate.getMonth();
        var endMonth = endDate.getMonth();
        var intervalMonth = (startDate.getFullYear()*12+startMonth) - (endDate.getFullYear()*12+endMonth);
	}


	
	function loadBillData(userno,date) {
		//var url = "http://" + serverHost + ":8180/office/weatherBase.json";
		var url = "../data/report.json";
		$.get(url,{userno:userno,date:date},function(data) {
			if (data.success) {
				var result = data.result;
				var detail = result.detail;
				//更新首页
				var months = getIntervalMonth(new Date(result.joinDate),new Date());
				$("#name").text(detail.name);
				
				$("#totalMonth").text(months);
				
				var joinDate = result.joinDate.split("-");
				$("#joinDate").text(joinDate[0] + "年"+joinDate[1] +"月"+joinDate[2] +"日");
				
				//第二页数据
				$("#income_month").text(detail.bysrhj);
				
				var historyData = result.historyData;
				var xData=[],yData=[];
				for(var i=0;i<historyData.length;i++){
					xData[i]=historyData[i].ym.substr(5).replace(/\b(0+)/gi,"")+"月";
					yData[i]=historyData[i].bysrhj;
				}
				if(historyData.length==1){
					$("#accrual").hide();
				}else{
					$("#accrual").text(detail.bysrhj-yData[historyData.length-2]);
					$("#accrual").show();
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
				$("#rank_cash").text("10%");
				$("#rank_other").text("30%");
				
				//第五页
				//if()
				
				/*
				$("#cond_img").attr("src",
						"img/" + result.now.cond_code + ".png");*/
			}
		});
	}
	
	loadBillData("sunhao","2019-02");
	
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('container'));

	//var data1 = [3010, 5052, 7200,9610, 10852, 12200];
	
	function updateIncomeChart(xData,yData) {
		$("#container").css({"width":'320px',"height":"240px"});  
		
		/*option = {
			color: ['#00FF00'],
			tooltip: {
				trigger: 'axis',
				axisPointer: { // 坐标轴指示器，坐标轴触发有效
					type: 'line' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			 grid: {
				left: '1%',
				right: '1%',
				bottom: '1%',
				containLabel: true
			},
			xAxis: [{
				type: 'category',
				data: xData.join(","),
				// 此系列自己的调色盘。
				//color: ['#dd6b66','#759aa0','#e69d87','#8dc1a9','#ea7e53','#eedd78','#73a373','#73b9bc','#7289ab', '#91ca8c','#f49f42'],
				axisTick: {
					alignWithLabel: true
				},
				//设置x轴文本颜色
				axisLabel: {
					show: true,
					textStyle: {
						color: '#29444E'                    //x轴文本颜色
					}
				}
			}],
			
			
			yAxis: [{
				type: 'value',
				interval:20
			}],
			series: [{
				name: '月收入',
				type: 'bar',
				barWidth: '60%',
				data: data,
				itemStyle: {
					normal: {
						 color:'#29444E'     //柱状图颜色tongyi
					},
				}
			}]
		};*/
		var option = {
			color: ['#447283'],
            title: {
                text: '2019年月收入',
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

})(jQuery);