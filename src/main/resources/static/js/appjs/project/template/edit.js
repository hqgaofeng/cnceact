var prefix = "/project/template";
$(function() {
    getProjects();
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
		url : "/project/template/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.code === 0) {
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
            pLevel: {
                required: true
            },
            effectMonth: {
                required: true
            },
            messages: {
                pLevel: {
                    required: icon + "请选择级别"
                },
                effectMonth: {
                    required: icon + "请设置月份"
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
function getProjects() {
    $.ajax({
        method : 'get',
        url :prefix+"/loadProject",
        async :false,
        datatype :'json',
        success :function(data) {
            if(data){
                if (data.length===1){
                    $("#pName").append("<option value='"+data[0]+"'>"+data[0]+"</option>");
                    $('#pName').attr("disabled",true)
                }else{
                    $("#pName").append('<option value="">请选择项目</option>');
                    $('#pName').attr("disabled",false)
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#pName").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                        }
                    }
                }
            }
        }
    });
}