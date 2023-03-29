var prefix = "/project/plan";

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
		url : "/project/plan/update",
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
