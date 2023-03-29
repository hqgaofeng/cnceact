var prefix = "/sys/require"
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
						offset : params.offset
					};
				},
				columns : [
					{
						checkbox : true
					},
					{
						field : 'id',
						title : '序号',
                        visible: false
					},
					{
						field : 'type',
						title : '需求类型',
                        formatter : function(value, row, index) {
                            if (value == '1') {
                                return '新增';
                            } else if (value == '2') {
                                return '优化';
                            }
                        }
					},
					{
						field : 'status',
						title : '需求状态',
                        formatter : function(value, row, index) {
                            if (value == '0') {
                                return '<span class="label label-info">待受理</span>';
                            } else if (value == '1') {
                                return '<span class="label label-warning">未受理</span>';
                            } else if (value == '2') {
                                return '<span class="label label-primary">已受理</span>';
                            } else if (value == '3') {
                                return '<span class="label label-danger">已拒绝</span>';
                            }
                        }
					},
					{
						field : 'describe',
						title : '需求描述'
					},
                    {
                        field : 'sponsor',
                        title : '发起人'
                    },
                    {
                        field : 'remark',
                        title : '受理意见'
                    },
                    {
                        field : 'expectTime',
                        title : '期望交付日期'
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
		title : '新增需求',
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
			url : "/sys/require/remove",
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
		title : '更新需求',
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
