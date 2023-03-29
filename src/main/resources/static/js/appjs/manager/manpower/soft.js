var prefix = "/manager/manPower"
$(function() {
    load();
    // getToolName();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/softInfo", // 服务器数据的加载地址
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				singleSelect : false, // 设置为true将禁止多选
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// pageList  :[10,25,50,100,200,500],
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				queryParams : function(params) {
					return {
						limit : params.limit,
						offset : params.offset,
						startTime : $('#date1').val(),
						endTime : $('#date2').val(),
						toolName : $('#toolName').val()
					};
				},
				columns : [
					{
						checkbox : true
					},
					{
						field : 'id',
						title : '序号'
					},
					{
						field : 'toolName',
						title : '工具名称'
					},
					{
						field : 'useDept',
						title : '工具使用部门'
					},
                    {
                        field : 'startUseTime',
                        title : '开始使用时间'
                    },
                    {
                        field : 'endUseTime',
                        title : '结束使用时间'
                    },
                    {
                        field : 'useTime',
                        title : "使用时长(H)"
                    },
                    {
                        field : 'manPower',
                        title : "节省人力(人/天)"
                    },
                    {
                        field : 'createTime',
                        title : '创建时间'
                    }
					]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function getToolName(){
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/tools/toolName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
            },
            success :function(data) {
                if(data){
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#toolName").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                        }
                   }
                }
            }
       });
}