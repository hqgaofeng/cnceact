function checkPwd (e) {
    var pwdRegex = new RegExp('(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,40}');
    if (!pwdRegex.test($(e).val())) {
        layer.alert("您的密码复杂度太低(密码中必须包含大小写字母、数字、特殊字符,且长度在[8,40]之间)");
        $(e).val(null);
    }
}

$("#pwd_save").click(function () {
    if($("#modifyPwd").valid()){
        $.ajax({
            cache : true,
            type : "POST",
            url :"/sys/user/resetPwd",
            data : $('#modifyPwd').serialize(),
            async : false,
            error : function() {
                layer.msg("Connection error");
            },
            success : function(data) {
            	if($("#pwd_flag").val()=='1'){
            		//是单独页面(不是个人设置进入的)
                    if (data.code == 0) {
                        parent.location.href = '/index';
                    } else{
                        layer.msg(data.msg);
                    }
				}else{
                    if (data.code == 0) {
                        parent.layer.alert("修改密码成功");
                        $("#photo_info").click();
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            }
        });
    }
});