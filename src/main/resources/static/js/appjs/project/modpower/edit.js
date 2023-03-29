var prefix = "/module/manpower";

$(function() {
    getProject();
    getStaff();
});

$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/module/manpower/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(data.msg);
			}

		}
	});

}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            modName: {
                required: true
            },
            staffName: {
                required: true
            },
            proName: {
                required: true
            },
            planDate: {
                required: true
            },
            planManpower: {
                required: true
            },
            actualManpower: {
                required: true
            },
            messages: {

                modName: {
                    required: icon + "请选择模块"
                },
                staffName: {
                    required: icon + "请选择人员"
                },
                proName: {
                    required: icon + "请选择项目"
                },
                planDate: {
                    required: icon + "请选择安排日期"
                },
                planManpower: {
                    required: icon + "请填入计划人力"
                },
                actualManpower: {
                    required: icon + "请填入实际人力"
                }
            }
        }
    })
}

//关闭
function closeSelf() {
    var index = parent.layer.getFrameIndex(window.name);// 获取窗口索引
    parent.layer.close(index);
}

// 获取项目列表
function getProject() {
    $.ajax({
        method : 'get',
        url : prefix + "/loadProject",
        async :false,
        datatype :'json',
        success :function(data) {
            if(data){
                if (data.length==1){
                    $("#select_project").empty();
                }else{
                    $("#select_project").empty();
                    $("#select_project").append('<option value="">请选择项目</option>');
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#select_project").append("<option value='"+data[i]+"' >"+data[i]+"</option>");
                        }
                    }
                }
            }
        }
    });
}

// 获取人员列表
function getStaff() {
    $.ajax({
        method : 'get',
        url : prefix + "/loadStaff",
        async :false,
        datatype :'json',
        success :function(data) {
            if(data){
                if (data.length==1){
                    $("#select_staff").empty();
                }else{
                    $("#select_staff").empty();
                    $("#select_staff").append('<option value="">请选择人员</option>');
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#select_staff").append("<option value='"+data[i]+"' >"+data[i]+"</option>");
                        }
                    }
                }
            }
        }
    });
}


