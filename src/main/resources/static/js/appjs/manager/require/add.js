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
        url : "/manager/require/save",
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
            toolName: {
                required: true
            },
            type: {
                required: true
            },
            describe: {
                required: true
            },
            sponsor: {
                required: true
            },
            deptOpinion: {
                required: true
            },
            incomeAnalysis: {
                required: true
            },
            isApprove: {
                required: true
            },
            expectDueTime: {
                required: true
            },
            messages: {

                toolName: {
                    required: icon + "此项不能为空"
                },
                type: {
                    required: icon + "此项不能为空"
                },
                describe: {
                    required: icon + "此项不能为空"
                },
                sponsor: {
                    required: icon + "此项不能为空"
                },
                deptOpinion: {
                    required: icon + "此项不能为空"
                },
                incomeAnalysis: {
                    required: icon + "此项不能为空"
                },
                isApprove: {
                    required: icon + "此项不能为空"
                },
                expectDueTime: {
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



