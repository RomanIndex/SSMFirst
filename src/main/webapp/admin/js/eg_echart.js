//指定图标的配置和数据
var option = {
    title:{
        text:'ECharts 数据统计'
    },
    tooltip:{},
    legend:{
        data:['用户来源']
    },
    xAxis:{
        data:["Android","IOS","PC","Ohter"]
    },
    yAxis:{

    },
    series:[{
        name:'访问量',
        //type:'line',//折线图
        type:'bar',//柱状图
        data:[500,200,360,100]
    }]
};

//饼状图参数配置
/*var option = {
    title:{
        text:'ECharts 数据统计'
    },            
    series:[{
        name:'访问量',
        type:'pie',
        radius:'60%',
        data:[
            {value:500,name:'Android'},
            {value:200,name:'IOS'},
            {value:360,name:'PC'},
            {value:100,name:'Ohter'}
        ]
    }]
};*/

//初始化echarts实例
//var myChart = echarts.init(document.getElementById('chartmain'));
var myChart = echarts.init($('#chartmain').get(0));

//使用制定的配置项和数据显示图表
myChart.setOption(option);