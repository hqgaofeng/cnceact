var prefix = "/manager/device"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get',
				url : prefix + "/list",
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true,
				dataType : "json",
				pagination : true,
				singleSelect : false,
				pageSize : 10,
				pageNumber : 1,
				showColumns : false,
				sidePagination : "server",
				queryParams : function(params) {
					return {
						limit : params.limit,
						offset : params.offset,
                        belonger : $('#searchName').val()
					};
				},
				columns : [
					{
						checkbox : true
					},
                    {
                        field : 'deviceId',
                        title : '序号',
                        visible: false
                    },
					{
						field : 'computerName',
						title : '机器名称'
					},
					{
						field : 'computerAlias',
						title : '机器别名'
					},
					{
						field : 'computerType',
						title : '机器类型',
                        formatter : function(value, row, index) {
                            if (value == '1') {
                                return '台式机';
                            } else if (value == '2') {
                                return '笔记本';
                            }
                        }
					},
                    {
                        field : 'status',
                        title : '当前状态',
                        formatter : function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-primary">正常</span>';
                            } else if (value == '2') {
                                return '<span class="label label-danger">禁用</span>';
                            }
                        }
                    },
                    {
                        field : 'optSystem',
                        title : '操作系统'
                    },
                    {
                        field : 'ip',
                        title : '机器IP'
                    },
                    {
                        field : 'loginName',
                        title : '登录账号'
                    },
                    {
                        field : 'attrArea',
                        title : '所在区域',
                        formatter : function(value, row, index) {
                            if (value == 'F') {
                                return '自用区';
                            } else if (value == 'B') {
                                return '蓝区';
                            }
                        }
                    },
                    {
                        field : 'siteNum',
                        title : '位置编号'
                    },
                    {
                        field : 'userName',
                        title : '用户工号'
                    },
                    {
                        field : 'belonger',
                        title : '所有者'
                    },
                    {
                        field : 'description',
                        title : '机器描述'
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
                                + row.id
                                + '\')"><i class="fa fa-edit "></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            return e + d;
                        }
					} ]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '新增设备',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/add'
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/manager/device/remove",
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
		title : '更新设备信息',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id
	});
}

function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections');
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
			ids[i] = row['id'];
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
