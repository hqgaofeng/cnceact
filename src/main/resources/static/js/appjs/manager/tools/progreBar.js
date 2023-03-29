
var prefix = "/manager/progreBar"


function selectTask(toolId) {
    $.ajax({
        method : 'get', // 服务器数据的请求方式 get or post
        url :prefix+"/selecTask", // 服务器数据的加载地址
        async :false,
        datatype :'json',
        data:{
            "toolId":toolId
        },
        success :function(data) {
          return data;
        }
    });
}


function getProgress(toolId) {
    $.ajax({
        method : 'get', // 服务器数据的请求方式 get or post
        url :prefix+"/load", // 服务器数据的加载地址
        async :false,
        datatype :'json',
        data:{
            "toolId":toolId
        },
        success :function(data) {
            return data;
        }
    });
}
