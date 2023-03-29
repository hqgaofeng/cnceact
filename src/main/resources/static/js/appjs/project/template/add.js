var prefix = "/project/template"

$().ready(function() {
    validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

$('input:radio[name="region"]').change(function (){
    if($("#region1").is(":checked")){
        getSyncProject("XA");
    }
    if($("#region2").is(":checked")){
        getSyncProject("BJ");
    }
});

function save() {
    console.log($('#signupForm').serialize());
	$.ajax({
		cache : true,
		type : "POST",
		url : "/project/template/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				parent.getProjectMonth();
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
            region: {
                required: true
            },
            effectMonth: {
                required: true
            },
            messages: {

                pName: {
                    required: icon + "请选择项目"
                },
                region: {
                    required: icon + "请选择地域"
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
function getSyncProject(region) {
    $.ajax({
        method : 'get',
        url :prefix+"/getSyncProject",
        async :false,
        datatype :'json',
        data:{
            region:region
        },
        success :function(data) {
            if(data){
                if (data.length===0){
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