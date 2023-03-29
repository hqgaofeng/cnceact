var prefix = "/module/manpower";
$(function() {
	load();
});


function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/module/manpower/list", // 服务器数据的加载地址
				editable: true,
				// showToggle: true,
				// showColumns: true,
				clickEdit: true,
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
				columns : [
					{
						field: 'meId',
						title: 'id',
						visible: false,
					},
					{
						field : 'modName',
						title : '模块'
					},
					{
						field : 'staffName',
						title : '姓名',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" >' +
								'<i class="fa fa-edit " id ="fa-edit" ></i></a> ' +
								'<a class = "s">'+ row.staffName +'</a> ';
							return e;
						}
					},
					{
						field : 'proName',
						title : '项目',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" >' +
								'<i class="fa fa-edit " id ="fa-edit" ></i></a> ' +
								'<a class = "s">'+ row.proName +'</a> ';
							return e;
						}
					},
					{
						field : 'totalManpower',
						title : '总计'
					},
					{
						field : 'actualManpower', // 列字段名
						title : '实际人力'
					},
					{
						field : 'nonInput', //
						title : '未投入'
					},
					{
						field : 'changeManpower', // 列字段名
						title : '变动人力',
					},
					{
						field : 'planManpower', // 列字段名
						title : '计划人力',
					},
					{
						field : 'remark', // 列字段名
						title : '备注',
					}],

				// 单点击事件
				onClickCell: function(field, value, row, $element) {
					if (field == "staffName") {
						let tdValue = $element.html();
						edit(row.meId,field,row.staffName,row.proName,row.remark)
					}
					if (field == "proName") {
						let tdValue = $element.html();
						editProName(row.meId,field,row.staffName,row.proName,row.remark)
					}

				},

				// 双点击事件
				/**
				 * @param {点击列的 field 名称} field
				 * @param {点击列的 value 值} value
				 * @param {点击列的整行数据} row
				 * @param {td 元素} $element
				 */
				onDblClickCell: function(field, value, row, $element) {
					// 保存
					// var myMap = {};
					let myMap=new Map();
					// 行索引
					// myMap.index = index;
					// 数据 id
					myMap.meid = row.meId;
					// 保存列名称
					myMap.operation = field;


					myMap.userName = row.staffName;
					myMap.proName = row.proName;
					myMap.remark = row.remark;





					if (field == "changeManpower") {
						$element.attr('contenteditable', true);
						$element.blur(function() {
							let index = $element.parent().data('index');
							// 修改后的新值
							let tdValue = $element.html();


							var result = checkCellInput(tdValue);
							if (result == null) {
								// 数据校验
								if (tdValue > 1 || tdValue < 0) {
									// alert("输入的数据有误，请输入0-1以内的数字！")
									parent.layer.msg("输入的数据有误，请输入0-1以内的数字！", {});
									$('#exampleTable').bootstrapTable('refresh');
								} else {
									if(tdValue > row.planManpower) {
										parent.layer.msg("变动人力不能大于计划人力！", {});
										$('#exampleTable').bootstrapTable('refresh');
									} else {
										myMap.newValue = tdValue;

										// 未投入 = 变动人力
										myMap.uncommitted = tdValue;
										myMap.set('uncommitted', tdValue)

										// 实际人力 = 计划 -变动
										var actualManpower = (row.planManpower * 1 - tdValue).toFixed(2);
										myMap.actualManpower = actualManpower;
										myMap.set('actualManpower', actualManpower)

										//  总计 = 实际 + 变动
										var total = (actualManpower * 1 + tdValue * 1).toFixed(2)
										myMap.total = total;
										myMap.set('total', total)

										console.log(myMap)
										saveData(myMap);
									}
								}
							} else{
								parent.layer.msg("输入不正确！\n" + result);
							}


						})
					}

					if (field == "planManpower") {
						// 保存列名称
						//myMap.name = field;

						$element.attr('contenteditable', true);
						$element.blur(function() {
							let index = $element.parent().data('index');
							// 修改后的新值
							let tdValue = $element.html();

							var result = checkCellInput(tdValue);
							if (result == null) {
								if (tdValue > 1 || tdValue < 0) {
									// alert("输入的数据有误，请输入0-1以内的数字！")
									parent.layer.msg("输入的数据有误，请输入0-1以内的数字！", {});
									$('#exampleTable').bootstrapTable('refresh');
								} else {
									if (tdValue > 1) {
										// alert("输入的数据有误，请确保总计人力小于1！")
										parent.layer.msg("输入的数据有误，请确保总计人力小于1！", {});
										$('#exampleTable').bootstrapTable('refresh');
									} else {
										// 计划人力
										myMap.newValue = tdValue;

										// 实际人力 = 计划 - 变动
										var actualManpower = (tdValue * 1 - row.changeManpower * 1).toFixed(2);
										myMap.actualManpower = actualManpower
										myMap.set('actualManpower', actualManpower);

										// 总计 = 实际 + 变动
										var total = (actualManpower * 1 + row.changeManpower * 1).toFixed(2)
										myMap.total = total;
										myMap.set('total', total);

										// 未投入
										var uncommitted = row.changeManpower;
										myMap.uncommitted = uncommitted;
										myMap.set('uncommitted', uncommitted);


										console.log(myMap)
										saveData(myMap);
									}
								}
							} else{
								parent.layer.msg("输入不正确！\n" + result);
							}

						})
					}

					if (field == "remark") {
						// 保存列名称
						//myMap.name = field;
						$element.attr('contenteditable', true);
						$element.blur(function() {
							let index = $element.parent().data('index');
							// 修改后的新值
							let tdValue = $element.html();

							myMap.newValue = tdValue;
							console.log(myMap)
							saveData(myMap);
						})
					}
				}

			});
	function saveData(myMap) {
		console.log("ok, MyMap++++" + myMap);
		$.ajax({
			type: "post",
			contentType : 'application/json;charset=utf-8',
			url: prefix +"/saveStaffName",
			data : JSON.stringify(myMap),
			success: function (data) {
				if (data.code == 0) {
					$('#exampleTable').bootstrapTable('refresh');
					parent.layer.msg("保存成功", {});
				} else {
					parent.layer.msg("保存失败", {});
				}
			},
			error: function () {
				alert('请求失败');
			}
		});

		// $('#exampleTable').bootstrapTable('refresh');
		// var pageNumber = $('#exampleTable').bootstrapTable('getOptions').pageNumber;
		// var pageSize = $('#exampleTable').bootstrapTable('getOptions').pageSize;
		// queryData(pageNumber, pageSize);
	}
}
function checkCellInput(tdValue) {
	var total_len = tdValue.toString().length;
	if (total_len <= 0) return null; // EMPTY is valid.
	if (isNaN(tdValue)) return "请输入数字";
	if (tdValue < 0) return "请输入正数";
	var len = tdValue.toString().split('.').pop().length;
	if (len > 2) return "最多输入两位小数";
	return null;
}

function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function getName() {
	$('#exampleTable').bootstrapTable('refresh');
}

function edit(id, field, staffName,proName,remark) {
	layer.open({
		type : 2,
		title : '选择人员',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/staffInfo/' + id,// iframe的url
		//eancel也可以换为end函数
		end: function(index, layero){
			var sessionData = sessionStorage.getItem('selectValue');//取出数据
			if (sessionData != staffName) {
				var myMap = {};
				myMap.meid = id
				myMap.operation = field;
				myMap.newValue = sessionData;
				myMap.userName = staffName;
				myMap.proName = proName;
				myMap.remark = remark;
				console.log("修改用户名称： meId=" + id + ",操作=" + field + ",修改的值为=" + sessionStorage.getItem('selectValue') +
					",用户=" + staffName + ",项目名称=" + proName + ",备注=" + remark);
				saveStaffName(myMap)
			}
		}
	});
}

function editProName(id, field, staffName,proName,remark) {
	layer.open({
		type : 2,
		title : '选择项目名称',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/projectInfo',// iframe的url
		//eancel也可以换为end函数
		end: function(index, layero){
			var sessionData = sessionStorage.getItem('selectPName');//取出数据
			// console.log("sessionData是："+sessionData )
			console.log("我是父页面获取到的数据:"+sessionData)
			if (sessionData != proName) {
				var myMap = {};
				myMap.meid = id
				myMap.operation = field;
				myMap.newValue = sessionData;
				myMap.userName = staffName;
				myMap.proName = proName;
				myMap.remark = remark;
				console.log("修改项目名称： meId=" + id + ",操作=" + field + ",修改的值为=" + sessionStorage.getItem('selectPName') +
					",用户=" + staffName + ",项目名称=" + proName + ",备注=" + remark);
				saveStaffName(myMap)
			}

		}
	});
}

// 向后台更改名字
function saveStaffName(myMap) {
	$.ajax({
		type: "post",
		contentType: 'application/json;charset=utf-8',
		url: prefix + "/saveStaffName",
		data: JSON.stringify(myMap),
		success: function (data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功", {});
				$('#exampleTable').bootstrapTable('refresh');
			} else {
				parent.layer.msg("保存失败", {});
			}
		},
		error: function () {
			alert('请求失败');
		}
	})
}



