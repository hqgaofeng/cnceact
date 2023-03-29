$(function() {
    getEquipment();
     checkNodes();

});
var value = 0;
var time = 20;

function clickStartTask(){
    $('#progress_bar').width(0 + '%'); //根据获得的进度改变进度条
    var y=document.getElementById("progress_value");
    y.innerHTML= 0 + '%';
    var timestamp = Date.parse(new Date());
    $.ajax({
        method: 'get', // 服务器数据的请求方式 get or post
        url: "/manager/progreBar/selecTask", // 服务器数据的加载地址
        async: false,
        datatype: 'json',
        data: {
            "toolId": 9
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=9&timestamp="+timestamp;  //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var projectName = document.getElementById("projectName");
                var index = projectName.selectedIndex;
                var projectName = projectName.options[index].value;

                var cameraLens = document.getElementById("cameraLens");
                var index = cameraLens.selectedIndex;
                var cameraLens = cameraLens.options[index].value;

                var testScene = document.getElementById("testScene").value;

                var path = document.getElementById("path").value;

                var name = document.getElementById("name").value;

                var number = document.getElementById("number").value;

                var equipName=$('#selectEquip').val();


                $.ajax({
                    url:"/manager/flare/start",
                    type:"post",
                    data:{
                        "projectName":projectName,
                        "cameraLens":cameraLens,
                        "testScene":testScene,
                        "path":path,
                        "name":name,
                        "number":number,
                        "equipName":equipName
                    },
                    success: function(data){
                        if (data=="SUCCESS"){
                            clearInterval(sitv);
                            $('#progress_bar').width('100%');
                            var y=document.getElementById("progress_value");
                            y.innerHTML= "100% 任务已完成";
                            enbleButton();
                        }
                    }
                });
            } else {
                alert("已有任务在运行，请稍后再发起任务")
            }
        }
    })
}
function clickCancelBtn(){
	var equipName=$('#selectEquip').val();
    $.ajax({
       url:"/manager/camera3ATools/cancel",
       type:'post',
       contentType:'application/json;charset=utf-8',
       data:{
            "equipName":equipName
       },
       async:false,
       success:function (data) {
           if(data==true){
               Change1();
               enbleButton();
           }
       },
       error:function () {
       }
    });

}


function getEquipment(deptId) {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/equipName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
                 "id":"2"
            },
            success :function(data) {
                if(data){
                    if (data.length==1){
                        $("#selectEquip").append("<option value='"+data[0]+"'>"+data[0]+"</option>");
                        $('#selectEquip').attr("disabled",true)
                    }else{
                        $("#selectEquip").append('<option value="请选择执行设备">请选择执行设备</option>');
                        $('#selectEquip').attr("disabled",false)
                        for(i=0;i<data.length;i++){
                            if (data[i]!=null){
                                $("#selectEquip").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                            }
                        }
                    }

                }
            }
       });
}

function check(){
    var checkbox = document.getElementById("ball3");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball2");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm").disabled=true;
    }
}

function disableButton() {
      var button = document.getElementById('start');
      button.disabled = true;
}

function enbleButton() {
      var button = document.getElementById('start');
      button.disabled = false;
}

function unCheck() {
    var checkbox = document.getElementById("ball3");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball2");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

function Change1() {
    var hh1 = document.getElementById("progress");
    if (hh1.style.display == "" || hh1.style.display == "none") {
        hh1.style.display = "block"
    } else {
        hh1.style.display = "none"
    }
}
$('#myModal').on('show.bs.modal', function () {
    document.getElementById("confirm").disabled=true;
    unCheck();
})

function checkNodes() {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/progreBar/checkNodes", // 服务器数据的加载地址
            async :true,
            datatype :'json',
            success :function(data) {
                    if (data){
                         $("#myModal9").modal({
                                show: false
                         });
                    }else{
                         $('.modal-backdrop').remove();
                         $("#myModal9").modal({
                                show: true
                         });
                    }
                }
       });
}
function checkFlare() {
    let projectName = document.getElementById("projectName").value.trim();
    let cameraLens = document.getElementById("cameraLens").value.trim();
    let testScene = document.getElementById("testScene").value.trim();
    let path = document.getElementById("path").value.trim();
    let name = document.getElementById("name").value.trim();
    let number = document.getElementById("number").value.trim();

    const warningDiv1 = document.getElementById("warning1");
    const warningDiv2 = document.getElementById("warning2");
    const warningDiv3 = document.getElementById("warning3");
    const warningDiv4 = document.getElementById("warning4");
    const warningDiv5 = document.getElementById("warning5");
    const warningDiv6 = document.getElementById("warning6");

    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (projectName == "请选择测试项目") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv1.innerText = "请选择测试项目";
        warningDiv1.style.display = "block";
        return;
    } else if (cameraLens == "请选择测试镜头") {

        warningDiv1.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";

        warningDiv2.innerText = "请选择测试镜头";
        warningDiv2.style.display = "block";
        return;
    }else if (testScene == "请选择测试场景") {
        warningDiv2.style.display = "none";
        warningDiv1.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";

        warningDiv3.innerText = "请选择测试场景";
        warningDiv3.style.display = "block";
        return;
    }else if (!pathRegex.test(path) || path == '') {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv1.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv4.innerText = "请输入合法路径";
        warningDiv4.style.display = "block";
        return;
    }else if (name == "请输入表格名称") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv1.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv5.innerText = "请输入表格名称";
        warningDiv5.style.display = "block";
        return;
    }else if (number == "请输入样机编号") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv1.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.innerText = "请输入样机编号";
        warningDiv6.style.display = "block";
    }else {
        warningDiv1.style.display = "none";
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        $('#myModal').modal('show');
    }

}