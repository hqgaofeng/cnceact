$(function(){
    getToolName();
});


function save() {
    $.ajax({
        cache : true,
        type : "POST",
        url : "/manager/accpt/save",
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

//关闭
function closeSelf() {
    var index = parent.layer.getFrameIndex(window.name);// 获取窗口索引
    parent.layer.close(index);
    window.parent.location.reload();
}

function getToolName(){
        $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/tools/toolName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
            },
            success :function(data) {
                 $("#toolName").empty();
                if(data){
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#toolName").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                        }
                   }
                }
            }
       });
}



