$(function(){
    load();
    setEndTime();
    setStartTime();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'post', // 服务器数据的请求方式 get or post
				url : "/project/log/logsInfo", // 服务器数据的加载地址
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				pageList  :[10,25,50,100,200],
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						startTime:$('#startTime').val(),
						endTime:$('#endTime').val(),
					};
				},
				columns : [
					{
						field : 'userName',
						title : '操作人',
						width : 200
					},
                    {
                        field : 'userDept',
                        title : '部门',
                        width : 200

                    },
					{
						field : 'editInfo',
						title : '操作明细'
					},
                    {
	                        field : 'createTime',
                        title : '操作时间',
                        width : 200
                    }
					]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}


function setStartTime() {
	var D = new Date();
	var y = D.getFullYear();
	var m = D.getMonth() + 1;
	var d = D.getDate() - 2; // 获取前天日期

	// 检查是否跨月
	if (d < 1) {
		m -= 1; // 月份减1
		if (m < 1) {
			y -= 1; // 年份减1
			m = 12; // 月份设置为12月
		}
		var lastMonth = new Date(y, m, 0); // 获取上个月份的Date对象
		d = lastMonth.getDate() + d; // 获取上个月份的天数加上前天的天数
	}

	var nowMonth = y + "-" + m + "-" + d;
	document.getElementById("startTime").value = nowMonth;
}



function setEndTime(){
	var D = new Date();
	var y = D.getFullYear();
	var m = D.getMonth()+1;
	var d = D.getDate();
	var nowMonth = y + "-" + m + "-" + d;
	document.getElementById("endTime").value = nowMonth;
}
