var prefix = "/manager/require"

$(function() {
    load();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/requireInfo", // 服务器数据的加载地址
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
				pageList  :[10,25,50,100,200,500],
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						limit : params.limit,
						offset : params.offset,
                        toolName : $('#searchName').val()
					};
				},
				columns : [
					{
						checkbox : true
					},
					{
						field : 'id', // 列字段名
						title : '序号'
					},
					{
						field : 'toolName',
						title : '工具名称'
					},
					{
						field : 'type',
						title : '需求类型',
                        formatter : function(value, row, index) {
                            if (value == '1') {
                                return '<span>软测</span>';
                            } else if (value == '2') {
                                return '<span >硬测</span>';
                            }
                        }
					},
                    {
                        field : 'describe',
                        title : '需求描述'
                    },
                    {
                        field : 'sponsor',
                        title : '需求发起人'
                    },
                    {
                        field : 'deptOpinion',
                        title : '部门意见'
                    },
                    {
                        field : 'incomeAnalysis',
                        title : '收益分析'
                    },
                    {
                        field : 'isApprove',
                        title : '是否立项',
                        formatter : function(value, row, index) {
                            if (value == '0') {
                                return '<span>未立项</span>';
                            } else if (value == '1') {
                                return '<span >已立项</span>';
                            }
                        }
                    },
                    {
                        field : 'expectDueTime',
                        title : '期望交付日期'
                    },
                    {
                        field : 'actualDelTime',
                        title : '实际交付日期'
                    },
                    {
                        title : '操作',
                        field : 'id',
                        formatter : function(value, row, index) {
                            var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit "></i></a> ';
                            return e;
                        }
                    }]
			});
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    // iframe层
    layer.open({
        type : 2,
        title : '新增项目',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/add'
    });
}

function edit(id) {
    layer.open({
        type : 2,
        title : '需求编辑',
        maxmin : true,
        shadeClose : false,
        area : [ '800px', '520px' ],
        content : prefix + '/edit/' + id // iframe的url
    });
}