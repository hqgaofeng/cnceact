var prefix = "/manager/upgradeBlueTools"
$(function() {
    getEquipment();
    checkNodes();
});

function checkBlueIsNull(){
    var localPath = document.getElementById("localPath").value.trim();
    var remotePath = document.getElementById("remotePath").value.trim();

    var warningDiv = document.getElementById("warning");
    var warningDiv2 = document.getElementById("warning2");

    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (localPath == ''  ) {
        warningDiv.innerText = "本地本路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(localPath)){
        warningDiv.innerText = "请输入合法的本地本路径";
        warningDiv.style.display = "block";
        return;
    } else if (remotePath == ''){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "版本下载路径不能为空";
        warningDiv2.style.display = "block";
        return;
    }  else if (!pathRegex.test(remotePath)){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "请输入合法的版本下载路径";
        warningDiv2.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        warningDiv2.style.display = "none";
        $('#myModal_1').modal('show')
    }
}

function checkBlueIsNull1(){
    var localPath = document.getElementById("localPath").value.trim();
    var remotePath = document.getElementById("remotePath").value.trim();

    var warningDiv = document.getElementById("warning");
    var warningDiv2 = document.getElementById("warning2");

    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (localPath == ''  ) {
        warningDiv.innerText = "本地本路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(localPath)){
        warningDiv.innerText = "请输入合法的本地本路径";
        warningDiv.style.display = "block";
        return;
    } else if (remotePath == ''){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "版本下载路径不能为空";
        warningDiv2.style.display = "block";
        return;
    }  else if (!pathRegex.test(remotePath)){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "请输入合法的版本下载路径";
        warningDiv2.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        warningDiv2.style.display = "none";
        $('#myModal_2').modal('show')
    }
}

function checkBlueIsNull2(){
    var localPath = document.getElementById("localPath").value.trim();
    var remotePath = document.getElementById("remotePath").value.trim();

    var warningDiv = document.getElementById("warning");
    var warningDiv2 = document.getElementById("warning2");


    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (localPath == ''  ) {
        warningDiv.innerText = "本地本路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(localPath)){
        warningDiv.innerText = "请输入合法的本地本路径";
        warningDiv.style.display = "block";
        return;
    } else if (remotePath == ''){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "版本下载路径不能为空";
        warningDiv2.style.display = "block";
        return;
    }  else if (!pathRegex.test(remotePath)){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "请输入合法的版本下载路径";
        warningDiv2.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        warningDiv2.style.display = "none";
        $('#myModal_3').modal('show')
    }
}

function checkBlueIsNull3(){
    var localPath = document.getElementById("localPath").value.trim();
    var remotePath = document.getElementById("remotePath").value.trim();

    var warningDiv = document.getElementById("warning");
    var warningDiv2 = document.getElementById("warning2");


    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (localPath == ''  ) {
        warningDiv.innerText = "本地本路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(localPath)){
        warningDiv.innerText = "请输入合法的本地本路径";
        warningDiv.style.display = "block";
        return;
    } else if (remotePath == ''){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "版本下载路径不能为空";
        warningDiv2.style.display = "block";
        return;
    }  else if (!pathRegex.test(remotePath)){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "请输入合法的版本下载路径";
        warningDiv2.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        warningDiv2.style.display = "none";
        $('#myModal_4').modal('show')
    }
}

function checkBlueIsNull4(){
    var localPath = document.getElementById("localPath").value.trim();
    var remotePath = document.getElementById("remotePath").value.trim();

    var warningDiv = document.getElementById("warning");
    var warningDiv2 = document.getElementById("warning2");


    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (localPath == ''  ) {
        warningDiv.innerText = "本地本路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(localPath)){
        warningDiv.innerText = "请输入合法的本地本路径";
        warningDiv.style.display = "block";
        return;
    } else if (remotePath == ''){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "版本下载路径不能为空";
        warningDiv2.style.display = "block";
        return;
    }  else if (!pathRegex.test(remotePath)){
        warningDiv.style.display = "none";
        warningDiv2.innerText = "请输入合法的版本下载路径";
        warningDiv2.style.display = "block";
        return;
    } else {
        warningDiv.style.display = "none";
        warningDiv2.style.display = "none";
        $('#myModal_5').modal('show')
    }
}

function downLoad(){
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
            "toolId":3
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=3&timestamp="+timestamp; //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var localPath=  document.getElementById("localPath").value;
                var remotePath=  document.getElementById("remotePath").value;
                var equipName=$('#selectEquip').val();
                var packType="";
                var obj1 = document.getElementsByName("packType");
                for(var j=0; j<obj1.length; j ++){
                    if(obj1[j].checked){
                        packType=obj1[j].value;
                    }
                }

                $.ajax({
                    url:"/manager/upgradeBlueTools/downLoad",
                    type:'post',
                    data:{"localPath":localPath,
                           "remotePath":remotePath,
                            "packType":packType,
                             "equipName":equipName},
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

function onlyUpgrade(){
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
            "toolId": 3
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=3&timestamp="+timestamp;  //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var localPath=  document.getElementById("localPath").value;
                var equipName=$('#selectEquip').val();
                if (equipName=="请选择执行设备"){
                    layer.msg("请选择执行设备");
                    return;
                }
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
                    url:"/manager/upgradeBlueTools/onlyUpgradeBlue",
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

function upgradeAndRoot(){
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
            "toolId":3
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=3&timestamp="+timestamp; //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var localPath=  document.getElementById("localPath").value;
                var equipName=$('#selectEquip').val();
                if (equipName=="请选择执行设备"){
                    layer.msg("请选择执行设备");
                    return;
                }
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
                    url:"/manager/upgradeBlueTools/upgradeAndRootBlue",
                    type:'post',
                    data:{
                        "localPath":localPath,
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
            "toolId": 3
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=3&timestamp=\"+timestamp; "; //获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var equipName=$('#selectEquip').val();

                $.ajax({
                    url:"/manager/upgradeBlueTools/skipWizard",
                    type:'post',
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

function fullProcess(){
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
            "toolId": 3
        },
        success: function (data) {
            if (data) {
                $("#progress").css('display', 'block');
                disableButton();
                var sitv = setInterval(function () {
                    var prog_url = "/manager/progreBar/load?toolId=3&timestamp="+timestamp;//获取进度的请求url
                    $.getJSON(prog_url, function (res) {
                        $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                        var y=document.getElementById("progress_value");
                        y.innerHTML= res + '%';
                    });
                }, 10000);

                var localPath=  document.getElementById("localPath").value;
                var remotePath=  document.getElementById("remotePath").value;
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
                    url:"/manager/upgradeBlueTools/fullProcessBlue",
                    type:'post',
                    data:{
                        "localPath":localPath,
                        "remotePath":remotePath,
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

function getEquipment(deptId) {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/equipName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
                 "id":"4"
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

function disableButton() {
    var button1 = document.getElementById('onlyUpgrade');
    button1.disabled = true;
    var button2 = document.getElementById('upgradeAndRoot');
    button2.disabled = true;
    var button3 = document.getElementById('skipWizard');
    button3.disabled = true;
    var button4 = document.getElementById('downLoad');
    button4.disabled = true;
    var button5 = document.getElementById('fullProcess');
    button5.disabled = true;

}

function enbleButton() {
    var button1 = document.getElementById('onlyUpgrade');
    button1.disabled = false;
    var button2 = document.getElementById('upgradeAndRoot');
    button2.disabled = false;
    var button3 = document.getElementById('skipWizard');
    button3.disabled = false;
    var button4 = document.getElementById('downLoad');
    button4.disabled = false;
    var button5 = document.getElementById('fullProcess');
    button5.disabled = false;
}

function check_1(){
    var checkbox = document.getElementById("ball1_1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_1");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm_1").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm_1").disabled=true;
    }
}

function unCheck_1() {
    var checkbox = document.getElementById("ball1_1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_1");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal_1').on('show.bs.modal', function() {
    document.getElementById("confirm_1").disabled=true;
    unCheck_1();
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

$('#myModal_2').on('show.bs.modal', function() {
    document.getElementById("confirm_2").disabled=true;
    unCheck_2();
})



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
    var checkbox1 = document.getElementById("ball2_3");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_3");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal_3').on('show.bs.modal',function(){
    document.getElementById("confirm_3").disabled=true;
    unCheck_3();
})



function check_4(){
    var checkbox = document.getElementById("ball1_4");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_4");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_4");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm_4").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm_4").disabled=true;
    }
}

function unCheck_4() {
    var checkbox = document.getElementById("ball1_4");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_4");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_4");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal_4').on('show.bs.modal',function() {
    document.getElementById("confirm_4").disabled=true;
    unCheck_4();
})


function check_5(){
    var checkbox = document.getElementById("ball1_5");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_5");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_5");//选中checkbox的id；
    if(checkbox.checked==true && checkbox1.checked==true && checkbox2.checked==true){//按钮已选中
        document.getElementById("confirm_5").removeAttribute("disabled");//移除disabled
    }else{
        document.getElementById("confirm_5").disabled=true;
    }
}

function unCheck_5() {
    var checkbox = document.getElementById("ball1_5");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2_5");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3_5");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
}

$('#myModal_5').on('show.bs.modal', function() {
    document.getElementById("confirm_5").disabled=true;
    unCheck_5();
})

function checkNodes() {
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/progreBar/checkNodes", // 服务器数据的加载地址
            async :true,
            datatype :'json',
            success :function(data) {
                    if (data){
                         $("#myModal11").modal({
                                show: false
                         });
                    }else{
                         $('.modal-backdrop').remove();
                         $("#myModal11").modal({
                                show: true
                         });
                    }
                }
       });
}