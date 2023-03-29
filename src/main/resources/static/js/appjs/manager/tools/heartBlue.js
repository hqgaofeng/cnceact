var prefix = "/manager/heartbeatAndPretestBlueTools"
$(function() {
	load();
	getEquipment();
    checkNodes();
});

function load(deptId) {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/heartbeatCaseInfo", // 服务器数据的加载地址
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
				pageList  :[10,25,50,100,200,500],
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
//				queryParams : function(params) {
//					return {
//						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
//						limit : params.limit,
//						offset : params.offset,
//						name : $('#searchName').val(),
//						deptId : deptId
//					};
//				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					{
						checkbox : true
					},
					{
						field : 'id', // 列字段名
						title : '序号',
						width:80
					},
					{
						field : 'name',
						title : '用例名称',
						width : 200
					},
					{
						field : 'type',
						title : '用例类型',
						width : 200,
                        formatter : function(value, row, index) {
                            if (value == 'H') {
                                return '<span>心跳测试</span>';
                            } else if (value == 'P') {
                                return '<span >预测试</span>';
                            }
                        }
					},
					{
						field : 'status',
						title : "用例状态",
                        formatter : function(value, row, index) {
                            if (value == '1') {
                                return '<span >正常</span>';
                            } else if (value == '2') {
                                return '<span >废弃</span>';
                            }
                        }
					},
                    {
                        field : 'createTime',
                        title : '创建时间'
                    },
                    {
                        field : 'updateTime',
                        title : '更新时间'
                    },
					]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function startTest() {
    $('#progress_bar').width(0 + '%'); //根据获得的进度改变进度条
    var y=document.getElementById("progress_value");
    y.innerHTML= 0 + '%';
    var timestamp = Date.parse(new Date());
    $.ajax({
        method: 'get', // 服务器数据的请求方式 get or post
        url: "/manager/progreBar/selecTask", // 服务器数据的加载地址
        async: false,
        datatype: 'json',
        data: {
            "toolId": 5
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=5&timestamp="+timestamp; //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
                var equipName=$('#selectEquip').val();
                if (rows.length == 0) {
                    layer.msg("请选择执行的用例");
                    return;
                }
                   var names = new Array();
                    // 遍历所有选择的行数据，取每条数据对应的ID
                    $.each(rows, function (i, row) {
                        names[i] = row['name'];
                    });
                    var num = document.getElementById("input").value;
                    $.ajax({
                        type : 'get',
                        data : {
                            "names" :names,
                            "num"  : num,
                            "equipName":equipName
                        },
                        url : prefix + '/startTest',
                        success: function (data) {
                            if (data == "SUCCESS") {
                                clearInterval(sitv);
                                $('#progress_bar').width('100%');
                                var y=document.getElementById("progress_value");
                                y.innerHTML= "100% 任务已完成";
                                enbleButton();
                            }
                        }
                    });
            } else {
                alert("已有任务在运行，请稍后再发起任务")
            }
        }
    })
}
function cancelTest() {
       var equipName=$('#selectEquip').val();
		if (equipName=="请选择执行设备"){
    	    layer.msg("请选择执行设备");
            return;
    	}
        layer.confirm("确认要取消本次任务吗?", {
    		btn : [ '确定', '取消' ]
    	// 按钮
    	}, function() {
		var flag = true;
		$.ajax({
			type : 'POST',
			data : {
				 "flag" :flag,
				 "equipName":equipName
			},
			url : prefix + '/cancelTest',
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

function getEquipment(deptId) {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/equipName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
                 "id":"5"
            },
            success :function(data) {
                if(data){
                    if (data.length==1){
                        $("#selectEquip").append("<option value='"+data[0]+"'>"+data[0]+"</option>");
                        $('#selectEquip').attr("disabled",true)
                    }else{
                        $("#selectEquip").append('<option value="请选择执行设备">请选择执行设备</option>');
                        $('#selectEquip').attr("disabled",false)
                        for(i=0;i<data.length;i++){
                            if (data[i]!=null){
                                $("#selectEquip").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                            }
                        }
                    }
                }
            }
       });
}
function check(){
    var checkbox = document.getElementById("ball3");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball2");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm").disabled=true;
    }
}



function disableButton() {
    var button = document.getElementById('start');
    button.disabled = true;
    var button1 = document.getElementById('selectReport');
    button1.disabled = true;
    var button2 = document.getElementById('selectLog');
    button2.disabled = true;

}

function enbleButton() {
    var button = document.getElementById('start');
    button.disabled = false;
    var button1 = document.getElementById('selectReport');
    button1.disabled = false;
    var button2 = document.getElementById('selectLog');
    button2.disabled = false;
}

function Change1() {
    modal.style.display = "none";
    var hh1 = document.getElementById("title-p1");
    if (hh1.style.display == "" || hh1.style.display == "none") {
        hh1.style.display = "block"
    } else {
        hh1.style.display = "none"
    }
}


function unCheck() {
    var checkbox = document.getElementById("ball3");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball2");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal').on('show.bs.modal', function () {
    document.getElementById("confirm").disabled=true;
    unCheck();
})

function checkNodes() {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/progreBar/checkNodes", // 服务器数据的加载地址
            async :true,
            datatype :'json',
            success :function(data) {
                    if (data){
                         $("#myModal9").modal({
                                show: false
                         });
                    }else{
                         $('.modal-backdrop').remove();
                         $("#myModal9").modal({
                                show: true
                         });
                    }
                }
       });
}