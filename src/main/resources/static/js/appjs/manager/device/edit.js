$().ready(function() {
	validateRule();
	// $("#signupForm").validate();
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
		url : "/manager/device/update",
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
            computerName : {
				required : true
			},
            computerAlias : {
				required : true
			},
            computerType : {
				required : true
			},
            status : {
				required : true
			},
            optSystem : {
				required : true
			},
            ip : {
                required : true
            },
            loginName : {
                required : true
            },
            attrArea : {
                required : true
            },
            siteNum : {
                required : true
            },
            userName : {
                required : true
            },
            belonger : {
                required : true
            },
            description : {
                required : true
            },
			agree : "required"
		},
		messages : {

            computerName : {
				required : icon + "请输入机器名称"
			},
            computerAlias : {
				required : icon + "请输入机器别名"
			},
            computerType : {
				required : icon + "请选择机器类型"
			},
            status : {
				required : icon + "请选择当前状态"
			},
            optSystem : {
                required : icon + "请输入操作系统信息"
            },
            ip : {
                required : icon + "请输入机器IP"
            },
            loginName : {
                required : icon + "请输入登录账号"
            },
            attrArea : {
                required : icon + "请选择机器所在区域"
            },
            siteNum : {
                required : icon + "请输入机器位置编号"
            },
            userName : {
                required : icon + "请输入用户工号"
            },
            belonger : {
                required : icon + "请输入机器所有者"
            },
            description : {
                required : icon + "请输入机器描述"
            }
		}
	})
}

//关闭
function closeSelf() {
    var index = parent.layer.getFrameIndex(window.name);// 获取窗口索引
    parent.layer.close(index);
}