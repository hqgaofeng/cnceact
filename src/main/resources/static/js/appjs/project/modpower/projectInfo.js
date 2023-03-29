$(function() {
    getpName();
});

//关闭
function closeSelf() {
    var index = parent.layer.getFrameIndex(window.name);// 获取窗口索引
    parent.layer.close(index);
}

function confirm() {
    var myselect = document.getElementById("select_code");
    var index1 = myselect.selectedIndex;
    var selectPName = myselect.options[index1].value;
    // 将选中的数据名字传给模块每日人力页面
    sessionStorage.setItem('selectPName',selectPName);

    // 获取窗口索引
    var index = parent.layer.getFrameIndex(window.name);
    // 关闭窗口
    parent.layer.close(index);
}

// 后台查询所有人的姓名
function getpName() {
    $.ajax({
        method : 'get',
        url :"/module/manpower/pname",
        async :false,
        // 以json数据格式返回
        datatype :'json',
        success :function(res) {
            if(res){
                if (res.length==1){
                    $("#select_code").empty();
                }else{

                    $("#select_code").empty();

                    $("#select_code").append('<option value="">请选择项目</option>');

                    for(i=0;i<res.length;i++){
                        if (res[i]!=null){
                            $("#select_code").append("<option value='"+res[i]+"' >"+res[i]+"</option>");
                        }
                    }
                }
            }
        }
    });
}