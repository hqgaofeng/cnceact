var prefix = "/manager/manPower"
$(function() {
    // getToolName();
});


function getToolName(){
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/tools/toolName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
            },
            success :function(data) {
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

