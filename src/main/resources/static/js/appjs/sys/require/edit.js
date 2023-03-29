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
		url : "/sys/require/update",
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
            type : {
				required : true
			},
            status : {
				required : true
			},
            describe : {
				required : true
			},
            remark : {
				required : true
			},
            expectTime : {
				required : true
			},
			agree : "required"
		},
		messages : {

            type : {
				required : icon + "请选择需求类型"
			},
            status : {
				required : icon + "请选择需求状态"
			},
            describe : {
				required : icon + "请输入需求描述"
			},
            remark : {
				required : icon + "请填写受理意见"
			},
            expectTime : icon + "请选择交付时间"
		}
	})
}

//关闭
function closeSelf() {
    var index = parent.layer.getFrameIndex(window.name);// 获取窗口索引
    parent.layer.close(index);
}