$().ready(function() {
    validateRule();
});

$.validator.setDefaults({
    submitHandler : function() {
        save();
    }
});

function save() {
    $.ajax({
        cache : true,
        type : "POST",
        url : "/system/manager/save",
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
            name: {
                required: true
            },
            status: {
                required: true
            },
            useDept: {
                required: true
            },
            tUseArea: {
                required: true
            },
            tDescribe: {
                required: true
            },
            dueTime: {
                required: true
            },
            messages: {

                name: {
                    required: icon + "此项不能为空"
                },
                status: {
                    required: icon + "此项不能为空"
                },
                useDept: {
                    required: icon + "此项不能为空"
                },
                tUseArea: {
                    required: icon + "此项不能为空"
                },
                tDescribe: {
                    required: icon + "此项不能为空"
                },
                dueTime: {
                    required: icon + "此项不能为空"
                }
            }
        }
    })
}

//关闭
function closeSelf() {
    var index = parent.layer.getFrameIndex(window.name);// 获取窗口索引
    parent.layer.close(index);
    window.parent.location.reload();
}



