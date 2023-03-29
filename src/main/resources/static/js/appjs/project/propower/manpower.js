var prefix = "/project/manpower";
$(function() {
    load();
    loadTotalManPower();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				singleSelect : false, // 设置为true将禁止多选
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						limit : params.limit,
						offset : params.offset,
                        planDate: $('#planDate').val(),
                        proName : $('#proName').val()
					};
				},
				columns : [
					{
						field : 'peId', // 列字段名
						title : '序号',
						visible : false
					},
                    {
                        field : 'proName',
                        title : '项目'
                    },
					{
						field : 'modName',
						title : '模块'
					},
                    {
                        field : 'planDate',
                        title : '日期'
                    },
                    {
                        field : 'totalPower',
                        title : '人力需求（人天）'
                    },
                    {
                        field : 'planManpower',
                        title : '人力安排'
                    },
                    {
                        field : 'createTime',
                        title : '创建时间'
                    }]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}


function loadTotalManPower() {
    $.ajax({
        method : 'get',
        url : prefix +"/loadTotalPower",
        async :false,
        datatype :'json',
        success :function(data) {
            if (data === null) {
                $('#totalManpower').html();
            } else {
                $('#totalManpower').html(data);
            }
        }
    });
}