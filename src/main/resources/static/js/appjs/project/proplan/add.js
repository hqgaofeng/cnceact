var prefix = "/project/plan";

$(function() {
    getProject();
});

$().ready(function() {
    validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
    console.log($('#signupForm').serialize());
	$.ajax({
		cache : true,
		type : "POST",
		url : "/project/plan/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            pName: {
                required: true
            },
            planName: {
                required: true
            },
            proPlan: {
                required: true
            },
            planStartTime: {
                required: true
            },
            planEndTime: {
                required: true
            },
            totalPower: {
                required: true
            },
            messages: {

                pName: {
                    required: icon + "请选择项目"
                },
                planName: {
                    required: icon + "请填入计划名称"
                },
                proPlan: {
                    required: icon + "请填入版本目的"
                },
                planStartTime: {
                    required: icon + "请选择计划开始时间"
                },
                planEndTime: {
                    required: icon + "请选择计划结束时间"
                },
                totalPower: {
                    required: icon + "请填入需求总人力"
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


// 获取项目
function getProject() {
    $.ajax({
        method : 'get',
        url : prefix + "/loadProject",
        async :false,
        datatype :'json',
        success :function(data) {
            if(data){
                if (data.length==1){
                    $("#select_code").empty();
                }else{
                    $("#select_code").empty();
                    $("#select_code").append('<option value="">请选择项目</option>');
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#select_code").append("<option value='"+data[i]+"' >"+data[i]+"</option>");
                        }
                    }
                }
            }
        }
    });
}
