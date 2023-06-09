var prefix = "/project/baseline";
$(function() {
	load();
    initUpload();
    $("#upload").on("click",function(){
        $("#myModal").modal("show");
    });
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
                        baseline : $('#baseline').val()
					};
				},
				columns : [
					{
						checkbox : true
					},
					{
						field : 'bId', // 列字段名
						title : '序号',
						visible: false
					},
					{
						field : 'scope',
						title : '领域'
					},
                    {
                        field : 'module',
                        title : '模块'
                    },
                    {
                        field : 'baseline',
                        title : '基线'
                    },
                    {
                        field : 'createTime',
                        title : '创建时间'
                    },
					{
						title : '操作',
						field : 'id',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.bId
								+ '\')"><i class="fa fa-edit "></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.bId
								+ '\')"><i class="fa fa-remove"></i></a> ';
							return e + d;
						}
					}]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function initUpload(){
    $("#f_upload").fileinput({
        language: 'zh',
        uploadUrl: prefix + "/fileImport",
        allowedFileExtensions: ["xls", "xlsx"],
        uploadAsync: true,
        showPreview: false,
        showUpload: true,
        showRemove: false,
        showCancel:false,
        showCaption: false,
        browseClass: "btn btn-primary",
        maxFileSize: 0,
        minFileCount: 1,
        maxFileCount: 1,
        enctype: 'multipart/form-data',
        validateInitialCount:true
    }).on("fileuploaded", function(event, data) {
        console.log(data);
        load();
    });
}

function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '新增记录',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add'
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/project/baseline/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}
function edit(id) {
	layer.open({
		type : 2,
		title : '更新信息',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['bId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}
