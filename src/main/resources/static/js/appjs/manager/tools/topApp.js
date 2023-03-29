var prefix = "/manager/topAppTools"
$(function() {
    load();
    loadSelectType();
    getEquipment();
    $("#upload").on("click",function(){
        $("#myModal5").modal("show");
    });
   checkNodes();
});

function load() {

	$('#exampleTable').bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/appInfo", // 服务器数据的加载地址
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
                queryParams : function(params) {
                   return {
                       // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                       limit : params.limit,
                       offset : params.offset,
                       type : $('#selectType').val(),
                       appField : $('#selectField').val()
                   };
                },
			    //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
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
						field : 'appPackage',
						title : '应用包名'
					},
					{
						field : 'name',
						title : '应用名称'
					},
					{
						field : 'type',
						title: '应用类别'

					},
                    {
                        field : 'appField',
                        title : '细分领域'
                    },
                    {
                        field : 'appRank',
                        title : '排名'
                    }
					]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function batchInstall() {
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
            "toolId": 8
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url ="/manager/progreBar/load?toolId=8&timestamp="+timestamp; //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
                var equipName=$('#selectEquip').val();
                if (equipName=="请选择执行设备"){

                    return;
                };
                if (rows.length == 0) {

                    return;
                };
                var names = new Array();
                // 遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function(i, row) {
                    names[i] = row['name'];
                });
                    $.ajax({
                        type : 'POST',
                        data : {
                            "names" :names,
                            "equipName":equipName
                        },
                        url : prefix + '/batchInstall',
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

function batchUninstall() {
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
            "toolId": 8
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=8&timestamp="+timestamp;//获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);
                var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
                var equipName=$('#selectEquip').val();
                if (equipName=="请选择执行设备"){

                    return;
                };
                        var appPackages = new Array();
                        // 遍历所有选择的行数据，取每条数据对应的ID
                        $.each(rows, function(i, row) {
                            appPackages[i] = row['appPackage'];
                        });
                    $.ajax({
                        type : 'POST',
                        data : {
                            "appPackages" :appPackages,
                            "equipName"   :equipName
                        },
                        url : prefix + '/batchUninstall',
                        success: function (data) {
                            if (data == "SUCCESS") {
                                clearInterval(sitv);
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

function loadSelectType(deptId){
   $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url : prefix + "/appType", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            success :function(data) {
             if(data){
              for(i=0;i<data.length;i++){
                  if (data[i]!=null){
                    $("#selectType").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                  }
              }

             }

            }
    });

}



function fieldChange(deptId) {
       document.all.selectField.length = 0; //清空select
       var appType = $('#selectType').val();
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url : prefix + "/appField", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
            "appType":appType
            },
            success :function(data) {
                if(data){
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#selectField").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                        }
                   }
                }
            }
       });
}


function getEquipment(deptId) {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/equipName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
                 "id":"8"
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


function disableButton() {
    var button1 = document.getElementById('unInstall');
    button1.disabled = true;
    var button2 = document.getElementById('Install');
    button2.disabled = true;
    var button3 = document.getElementById('select');
    button3.disabled = true;
    var button4 = document.getElementById('selectReport');
    button4.disabled = true;
    var button5 = document.getElementById('selectLog');
    button5.disabled = true;
    var button6 = document.getElementById('upload');
    button6.disabled = true;

}

function enbleButton() {
    var button1 = document.getElementById('unInstall');
    button1.disabled = false;
    var button2 = document.getElementById('Install');
    button2.disabled = false;
    var button3 = document.getElementById('select');
    button3.disabled = false;
    var button4 = document.getElementById('selectReport');
    button4.disabled = false;
    var button5 = document.getElementById('selectLog');
    button5.disabled = false;
    var button6 = document.getElementById('upload');
    button6.disabled = false;
}

function check_1(){
    var checkbox = document.getElementById("ball1_1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_1");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm_1").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm_1").disabled=true;
    }
}

function unCheck_1() {
    var checkbox = document.getElementById("ball1_1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_1");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal_1').on('show.bs.modal', function() {
    document.getElementById("confirm_1").disabled=true;
    unCheck_1();
})


function check_2(){
    var checkbox = document.getElementById("ball1_2");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_2");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm_2").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm_2").disabled=true;
    }
}

function unCheck_2() {
    var checkbox = document.getElementById("ball1_2");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_2");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal_2').on('show.bs.modal', function() {
    document.getElementById("confirm_2").disabled=true;
    unCheck_2();
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
