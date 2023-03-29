var prefix = "/project/powerDetail";
$(function() {
    load();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
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
                        proName : $('#proName').val()
					};
				},
				columns : [
					{
						field : 'pdId', // 列字段名
						title : '序号',
                        visible: false
					},
					{
						field : 'proName',
						title : '项目'
					},
                    {
                        field : 'written',
                        title : '接口人'
                    },
                    {
                        field : 'proPlan',
                        title : '版本目的'
                    },
                    {
                        field : 'workContent',
                        title : '工作内容'
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
