var prefix = "/manager/upgradeTools"
$(function() {
    getEquipment();
    checkNodes();
});
function checkIsNull(){
    var path = document.getElementById("localPath").value.trim();
    var warningDiv = document.getElementById("warning");


    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (path == '') {
        warningDiv.innerText = "导出路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(path)){
        warningDiv.innerText = "请输入合法的导出路径";
        warningDiv.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        $('#myModal').modal('show')
    }
}

function checkIsNull1(){
    var path = document.getElementById("localPath").value.trim();
    var warningDiv = document.getElementById("warning");

    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (path == '') {
        warningDiv.innerText = "导出路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(path)){
        warningDiv.innerText = "请输入合法的导出路径";
        warningDiv.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        $('#myModal_2').modal('show')
    }
}

function checkIsNull2(){
    var path = document.getElementById("localPath").value.trim();
    var warningDiv = document.getElementById("warning");

    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (path == '') {
        warningDiv.innerText = "导出路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(path)){
        warningDiv.innerText = "请输入合法的导出路径";
        warningDiv.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        $('#myModal_3').modal('show')
    }
}
function onlyUpgrade( deptId){
    // checkIsNull();
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
            "toolId": 4
        },
        // beforeSend: function(){
        //     let path = document.getElementById("path").value.trim();
        //     let selectEquip = document.getElementById("selectEquip").value.trim();
        //     if (path == null) {
        //         alert("请输入导出路径！")
        //         console.log("错了！")
        //         ajax.abort();
        //     }
        //     if(selectEquip == "请选择执行设备") {
        //         alert("请选择执行设备!")
        //         console.log("错了！")
        //         ajax.abort();
        //     }
        // },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=4&timestamp="+timestamp; //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var localPath=  document.getElementById("localPath").value;
                var equipName=$('#selectEquip').val();

                var platformType="";
                var obj = document.getElementsByName("platformType");
                for(var i=0; i<obj.length; i ++){
                    if(obj[i].checked){
                        platformType=obj[i].value;
                    }
                }
                var packType="";
                var obj1 = document.getElementsByName("packType");
                for(var j=0; j<obj1.length; j ++){
                    if(obj1[j].checked){
                        packType=obj1[j].value;
                    }
                }

                $.ajax({
                    url:prefix+"/onlyUpgrade",
                    type:'post',
                    datatype :'json',
                    data:{
                        "localPath":localPath,
                        "platformType":platformType,
                        "packType":packType,
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
        },

    })
}

function upgradeAndRoot(deptId){
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
            "toolId": 4
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=4&timestamp="+timestamp;//获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var localPath=  document.getElementById("localPath").value;
                var equipName=$('#selectEquip').val();

                var platformType="";
                var obj = document.getElementsByName("platformType");
                for(var i=0; i<obj.length; i ++){
                    if(obj[i].checked){
                        platformType=obj[i].value;
                    }
                }

                var packType="";
                var obj1 = document.getElementsByName("packType");
                for(var j=0; j<obj1.length; j ++){
                    if(obj1[j].checked){
                        packType=obj1[j].value;
                    }
                }
                $.ajax({
                    url:"/manager/upgradeTools/upgradeAndRoot",
                    type:'post',
                    data:{"localPath":localPath,
                           "platformType":platformType,
                           "packType":packType,
                           "equipName":equipName
                    },
                    success: function(data){
                        if (data=="SUCCESS"){
                            clearInterval(sitv);
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

function skipWizard(){
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
            "toolId": 4
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=4&timestamp="+timestamp; //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var equipName=$('#selectEquip').val();
                $.ajax({
                    url:"/manager/upgradeTools/skipWizard",
                    contentType:'application/json;charset=utf-8',
                    data:{
                        "equipName":equipName
                    },
                    success: function(data){
                        if (data=="SUCCESS"){
                            clearInterval(sitv);
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

function getEquipment(deptId) {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/equipName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
                 "id":"5"
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
    var checkbox = document.getElementById("ball1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm").disabled=true;
    }
}

function unCheck() {
    var checkbox = document.getElementById("ball1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}


$('#myModal').on('show.bs.modal', function () {
    document.getElementById("confirm").disabled=true;
    unCheck();
})

$('#mymodel').on('hide.bs.modal', function () {
    onlyUpgrade();
})


function check_2(){
    var checkbox = document.getElementById("ball1_2");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_2");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm_2").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm_2").disabled=true;
    }
}

function unCheck_2() {
    var checkbox = document.getElementById("ball1_2");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_2");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}


$('#myModal_2').on('show.bs.modal', function () {
    document.getElementById("confirm_2").disabled=true;
    unCheck_2();
})


function disableButton() {
    var button1 = document.getElementById('onlyUpgrade');
    button1.disabled = true;
    var button2 = document.getElementById('upgradeAndRoot');
    button2.disabled = true;
    var button3 = document.getElementById('skipWizard');
    button3.disabled = true;
}

function enbleButton() {
    var button1 = document.getElementById('onlyUpgrade');
    button1.disabled = false;
    var button2 = document.getElementById('upgradeAndRoot');
    button2.disabled = false;
    var button3 = document.getElementById('skipWizard');
    button3.disabled = false;
}

function check_3(){
    var checkbox = document.getElementById("ball1_3");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_3");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_3");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm_3").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm_3").disabled=true;
    }
}

function unCheck_3() {
    var checkbox = document.getElementById("ball1_3");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_3");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal_3').on('show.bs.modal', function () {
    document.getElementById("confirm_3").disabled=true;
    unCheck_3();
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