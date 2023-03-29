$(function() {
    getEquipment();
    checkNodes();
});
var value = 0;
var time = 20;

function clickStartCamera3ATools(){
    var projectName = document.getElementById("projectName");
    var index = projectName.selectedIndex;
    var projectName = projectName.options[index].value;

    var cameraLens = document.getElementById("cameraLens");
    var index = cameraLens.selectedIndex;
    var cameraLens = cameraLens.options[index].value;

    var cameraMode = document.getElementById("cameraMode");
    var index = cameraMode.selectedIndex;
    var cameraMode = cameraMode.options[index].value;

    var cameraTime = document.getElementById("cameraTime");
    var index = cameraTime.selectedIndex;
    var cameraTime = cameraTime.options[index].value;

    var cameraTouch = document.getElementById("cameraTouch");
    var index = cameraTouch.selectedIndex;
    var cameraTouch = cameraTouch.options[index].value;

    var cameraNum = document.getElementById("cameraNum");
    var index = cameraNum.selectedIndex;
    var cameraNum = cameraNum.options[index].value;

    var equipName=$('#selectEquip').val();

    var value=0
        $.ajax({
            url:"/manager/camera3ATools/start",
           type:"post",
           data:{
                "projectName":projectName,
                "cameraLens":cameraLens,
                "cameraMode":cameraMode,
                "cameraTime":cameraTime,
                "cameraTouch":cameraTouch,
                "equipName":equipName
                },
           success: function(result){

            }
        });
}
function clickCancelcamera3ATools(){
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

function unCheck() {
    var checkbox = document.getElementById("ball1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball3");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball2");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
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
$('#myModal').on('show.bs.modal', function () {
    document.getElementById("confirm").disabled=true;
    unCheck();
})

function Change1() {
    var hh1 = document.getElementById("progress");
    if (hh1.style.display == "" || hh1.style.display == "none") {
        hh1.style.display = "block"
    } else {
        hh1.style.display = "none"
    }
}

function fn() {
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
            "toolId": 2
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=2&timestamp="+timestamp; //获取进度的请求url
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

                var cameraMode = document.getElementById("cameraMode");
                var index = cameraMode.selectedIndex;
                var cameraMode = cameraMode.options[index].value;

                var cameraTime = document.getElementById("cameraTime");
                var index = cameraTime.selectedIndex;
                var cameraTime = cameraTime.options[index].value;

                var cameraTouch = document.getElementById("cameraTouch");
                var index = cameraTouch.selectedIndex;
                var cameraTouch = cameraTouch.options[index].value;

                var equipName=$('#selectEquip').val();
                params ='{'+
                    '"projectName":"'+projectName+'",'+
                    '"cameraLens":"'+cameraLens+'",'+
                    '"cameraMode":"'+cameraMode+'",'+
                    '"cameraTime":"'+cameraTime+'",'+
                    '"cameraTouch":"'+cameraTouch+'",'+
                    '"equipName":"'+equipName+'"'+
                '}';
                $.ajax({
                    url:"/manager/camera3ATools/start",
                    type:"post",
                    dataType: "text",
                    processData : false,
                    contentType : 'application/json;charset=utf-8',
                    data:params,
                    success: function(data){
                        if (data=="SUCCESS"){
                            clearInterval(sitv);
                            var y=document.getElementById("progress_value");
                             $('#progress_bar').width('100%');
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

function selectTask(toolId) {
    $.ajax({
        method : 'get', // 服务器数据的请求方式 get or post
        url :"/manager/progreBar/selecTask", // 服务器数据的加载地址
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
        method: 'get', // 服务器数据的请求方式 get or post
        url: "/manager/progreBar/load", // 服务器数据的加载地址
        async: false,
        datatype: 'json',
        data: {
            "toolId": toolId
        },
        success: function (data) {
            return data;
        }
    });
}


getcameraTouch=function () {
    var cameraMode =$("#cameraMode").val();
    if (cameraMode =="单模式拍摄(20张)"){
         $("#cameraTouch").empty();
         $("#cameraTouch").append("<option style='width:95%'value='TouchAF'>TouchAF</option>");
    }
    if (cameraMode =="双模式拍摄(40张)"){
        $("#cameraTouch").empty();
        $("#cameraTouch").append("<option style='width:95%'value='UnTouchAF'>UnTouchAF</option>");
   }
}


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

function checkInfo() {
    let projectName = document.getElementById("projectName").value.trim();
    let cameraLens = document.getElementById("cameraLens").value.trim();
    let cameraMode = document.getElementById("cameraMode").value.trim();
    let cameraTime = document.getElementById("cameraTime").value.trim();
    let cameraTouch = document.getElementById("cameraTouch").value.trim();

    const warningDiv1 = document.getElementById("warning1");
    const warningDiv2 = document.getElementById("warning2");
    const warningDiv3 = document.getElementById("warning3");
    const warningDiv4 = document.getElementById("warning4");
    const warningDiv5 = document.getElementById("warning5");


    if (projectName == "null") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv1.innerText = "请选择测试项目";
        warningDiv1.style.display = "block";
        return;
    } else if (cameraLens == "null") {
        warningDiv1.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv2.innerText = "请选择测试镜头";
        warningDiv2.style.display = "block";
        return;
    }else if (cameraMode == "null") {
        warningDiv1.style.display = "none";
        warningDiv2.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv3.innerText = "请选择相机模式";
        warningDiv3.style.display = "block";
        return;
    }else if (cameraTime == "null") {
        warningDiv1.style.display = "none";
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv4.innerText = "请选择间隔时间";
        warningDiv4.style.display = "block";
        return;
    }else if (cameraTouch == "null") {
        warningDiv1.style.display = "none";
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.innerText = "请选择对焦模式";
        warningDiv5.style.display = "block";
        return;
    }else {
        warningDiv1.style.display = "none";
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        $('#myModal').modal('show');
    }
    }
