var prefix ="/manager/cameraStressTools"

$(function() {
    getEquipment();
    checkNodes();
});

function checkCameraStressIsNull(){

    var cameraFeatures = document.getElementById("cameraFeatures").value.trim();
    var cameraLens = document.getElementById("cameraLens").value.trim();
    var cameraMode = document.getElementById("cameraMode").value.trim();
    var cameraResolution = document.getElementById("cameraResolution").value.trim();
    var testNum = document.getElementById("testNum").value.trim();

    var warning1 = document.getElementById("warning1");
    var warning2 = document.getElementById("warning2");
    var warning3 = document.getElementById("warning3");
    var warning4 = document.getElementById("warning4");
    var warning5 = document.getElementById("warning5");

    if (cameraFeatures=='请选择功能') {
        warning1.innerText = "请选择功能";
        warning1.style.display = "block";
        return;
    } else if (cameraLens ==='请选测试镜头'){
        warning1.style.display = "none";

        warning2.innerText = "请选测试镜头";
        warning2.style.display = "block";
        return;
    } else if (cameraMode == ''){
        warning1.style.display = "none";
        warning2.style.display = "none";

        warning3.innerText = "请选择相机模式";
        warning3.style.display = "block";
        return;
    } else if (cameraResolution == ''){
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";

        warning4.innerText = "请选择分辨率";
        warning4.style.display = "block";
        return;
    } else if (testNum == '请选择测试次数'){
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";

        warning5.innerText = "请选择测试次数";
        warning5.style.display = "block";
        return;
    }  else {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        $('#myModal').modal('show')
    }
}

function getEquipment() {
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


getModelName=function (){
       modelName2=["录像","双景录像","延时摄影"]
       modelName1 = ["专业","大光圈","超级微距","全景","高像素","拍照","人像","水印","夜景"]
       var cameraFeatures=$('#cameraFeatures').val();
       var cameraLens=$('#cameraLens').val();
       var modelSort=""
       if (cameraFeatures=="拍照"){
            modelSort="01"
            $("#cameraMode").empty();
            for(i=0;i<modelName1.length;i++){
                $("#cameraMode").append("<option style='width:95%'value='"+modelName1[i]+"'>"+modelName1[i]+"</option>");
           }

       }
       if (cameraFeatures=="录像"){
            modelSort="02"
            $("#cameraMode").empty();
            for(i=0;i<modelName2.length;i++){
                $("#cameraMode").append("<option style='width:95%'value='"+modelName2[i]+"'>"+modelName2[i]+"</option>");
           }
       }
        $('#cameraMode').selectpicker('refresh');
//       $.ajax({
//            type: 'get', // 服务器数据的请求方式 get or post
//            url : prefix + "/modelName", // 服务器数据的加载地址
//            async :true,
//            datatype :'json',
//            data:{
//                 "modelSort":modelSort
//            },
//            success :function(data) {
////                initlist=data
//                 if(data){
//                     $("#cameraMode").empty();
//                    for(i=0;i<data.length;i++){
//                        if (data[i]!=null){
//                        $("#cameraMode").append("<option style='width:95%'value='"+data[i]+"'>"+data[i]+"</option>");
//                        }
//                   }
//                }
//                $('#cameraMode').selectpicker('refresh');
//            }
//       });
}

function getResoName(){
       resoName1=["4:3(推荐)","1:1","全屏"]
       resoName2=["20:9 1080p","16:9 1080p(推荐)","16:9 720p"]
       var Select = document.getElementById("cameraResolution");
       Select.options.length = 0;
       var cameraFeatures=$('#cameraFeatures').val();
       var modelSort=""
       if (cameraFeatures=="拍照"){
             $("#cameraResolution").empty();
             $("#cameraResolution").append($('<option value="">请选择分辨率</option>'));
            for(i=0;i<resoName1.length;i++){
                $("#cameraResolution").append("<option value='"+resoName1[i]+"'>"+resoName1[i]+"</option>");
            }
       }
       if (cameraFeatures=="录像"){
            $("#cameraResolution").empty();
            $("#cameraResolution").append($('<option value="">请选择分辨率</option>'));
            for(i=0;i<resoName2.length;i++){
                $("#cameraResolution").append("<option value='"+resoName2[i]+"'>"+resoName2[i]+"</option>");
            }

       }
//       $.ajax({
//            method : 'get', // 服务器数据的请求方式 get or post
//            url : prefix + "/resoName", // 服务器数据的加载地址
//            async :false,
//            datatype :'json',
//            data:{
//                 "modelSort":modelSort
//            },
//            success :function(data) {
//                if(data){
//                     $("#cameraResolution").empty();
//                     $("#cameraResolution").append($('<option value="">请选择分辨率</option>'));
//                    for(i=0;i<data.length;i++){
//                        if (data[i]!=null){
//                            $("#cameraResolution").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
//                        }
//                   }
//                }
//            }
//       });
        $('#cameraResolution').selectpicker('refresh');
}

function clickStartButton(){
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
            "toolId": 7
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url =  "/manager/progreBar/load?toolId=7&timestamp="+timestamp;  //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var cameraFeatures=$('#cameraFeatures').val();
                var cameraLens=$('#cameraLens').val();
                var cameraResolution=$('#cameraResolution').val();
                var testNum=$('#testNum').val();
                var equipName=$('#selectEquip').val();
                var cameraMode = $('#cameraMode').val();
                var  params='{'+
                            '"cameraFeatures":"'+ cameraFeatures+'",'+
                            '"cameraLens":"'+ cameraLens+'",'+
                            '"cameraResolution":"'+ cameraResolution+'",'+
                            '"testNum":"'+ testNum+'",'+
                            '"equipName":"'+ equipName+'",'+
                            '"cameraMode":"'+ cameraMode+'"'+
                             '}';
                $.ajax({
                    type : 'post', // 服务器数据的请求方式 get or post
                    url : prefix + "/startTask", // 服务器数据的加载地址
                    dataType: "text",
                    processData : false,
                    contentType : 'application/json;charset=utf-8',
                    data:params,
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


function chooseCameraLens(){
       modelName3=["录像","双景录像","延时录像"];
       modelName1 = ["拍照","人像","水印","夜景"];
       modelName2 = ["专业","大光圈","超级微距","全景","高像素","拍照","人像","水印","夜景"];
       var cameraLens=$('#cameraLens').val();
        var cameraFeatures=$('#cameraFeatures').val();
       if (cameraFeatures=="拍照"){
            if (cameraLens == "前置"){
               $("#cameraMode").empty();
               $("#cameraMode").append($('<option value="">请选相机模式</option>'));
               for(i=0;i<modelName1.length;i++){
                   $("#cameraMode").append("<option style='width:95%'value='"+modelName1[i]+"'>"+modelName1[i]+"</option>");
                   enbleButton();
               }
            }
            if (cameraLens == "后置"){
                $("#cameraMode").empty();
                for(i=0;i<modelName2.length;i++){
                    $("#cameraMode").append("<option style='width:95%'value='"+modelName2[i]+"'>"+modelName2[i]+"</option>");
                    enbleButton();
               }
            }
            if (cameraLens == "景深"){
                  $("#cameraMode").empty();
                  disableButton();
            }
            if (cameraLens == "微距"){
                  $("#cameraMode").empty();
                  disableButton();
            }
       }
       if (cameraFeatures=="录像"){
             $("#cameraMode").empty();
             $("#cameraMode").append($('<option value="">请选相机模式</option>'));
             for(i=0;i<modelName3.length;i++){
                   $("#cameraMode").append("<option style='width:95%'value='"+modelName3[i]+"'>"+modelName3[i]+"</option>");
                    enbleButton();
            }
       }
       $('#cameraMode').selectpicker('refresh');
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

function showlogin(){
    cache: false //关闭AJAX相应的缓存
    $('#tip').show();
}
function closelogin(){
    $('#tip').hide();
}