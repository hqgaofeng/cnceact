var prefix = "/manager/wifi"

$(function() {
    getEquipment();
    checkNodes();
});

function checkWifiIsNull(){
    var projectName = document.getElementById("projectName").value.trim();
    var projectStage = document.getElementById("projectStage").value.trim();
    var projectScheme = document.getElementById("projectScheme").value.trim();
    var engineer = document.getElementById("engineer").value.trim();
    var equipment = document.getElementById("equipment").value.trim();
    var testTime = document.getElementById("testTime").value.trim();
    var levelValue = document.getElementById("levelValue").value.trim();
    var power = document.getElementById("power").value.trim();
    var lineLoss = document.getElementById("lineLoss").value.trim();
    var scenario = document.getElementById("scenario").value.trim();
    var internet = document.getElementById("internet").value.trim();
    var band = document.getElementById("band").value.trim();
    var channel = document.getElementById("channel").value.trim();

    var warning1 = document.getElementById("warning1");
    var warning2 = document.getElementById("warning2");
    var warning3 = document.getElementById("warning3");
    var warning4 = document.getElementById("warning4");
    var warning5 = document.getElementById("warning5");
    var warning6 = document.getElementById("warning6");
    var warning7 = document.getElementById("warning7");
    var warning8 = document.getElementById("warning8");
    var warning9 = document.getElementById("warning9");
    var warning10 = document.getElementById("warning10");
    var warning11 = document.getElementById("warning11");
    var warning12 = document.getElementById("warning12");
    var warning13 = document.getElementById("warning13");


    if (projectName == '请输入项目名称') {
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning1.innerText = "请输入项目名称";
        warning1.style.display = "block";
    } else if (projectStage ==  '请输入项目阶段') {
        warning1.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning2.innerText = "请输入项目阶段";
        warning2.style.display = "block";
        return;
    } else if (projectScheme == '请输入项目方案') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning3.innerText = "请输入项目方案";
        warning3.style.display = "block";
        return;
    } else if (engineer == '请输入工程师姓名') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning4.innerText = "请输入工程师姓名";
        warning4.style.display = "block";
        return;
    } else if (equipment == '请输入测试设备') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning5.innerText = "请输入测试设备";
        warning5.style.display = "block";
        return;
    } else if (testTime == '选择时间'){
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning6.innerText = "请选择时间";
        warning6.style.display = "block";
        return;
    } else if (levelValue == '请输入初始电平值') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning7.innerText = "请输入初始电平值";
        warning7.style.display = "block";
        return;
    } else if (power == 'Max Power') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning8.innerText = "Max Power";
        warning8.style.display = "block";
        return;
    } else  if (lineLoss == '请输入线损') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning9.innerText = "请输入线损";
        warning9.style.display = "block";
        return;
    } else if (scenario == '') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning10.innerText = "请选择测试场景";
        warning10.style.display = "block";
        return;
    } else if (internet == '请选择测试网络') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";

        warning11.innerText = "请选择测试网络";
        warning11.style.display = "block";
        return;
    } else if (band == '') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning13.style.display = "none";

        warning12.innerText = "请选择测试频段";
        warning12.style.display = "block";
        return;
    } else if (channel == '') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";

        warning13.innerText = "请选择测试信道";
        warning13.style.display = "block";
        return;
    } else {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
        warning8.style.display = "none";
        warning9.style.display = "none";
        warning10.style.display = "none";
        warning11.style.display = "none";
        warning12.style.display = "none";
        warning13.style.display = "none";
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
            "id":"1"
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



getchannel=function (){
    var twoChannel=['1','2','3','4','5','6','7','8','9','10','11','12','13'];
    var fiveChannel=['36','40','44','48','52','56','60','64','100','104','108','112','116','120','124','128','132','136','140','144','149','153','157','161','165'];

    var internet=$('#internet').val();
    if (internet=="2.4G"){
        $("#channel").empty();
        for (var k=0;k<twoChannel.length;k++){
            $("#channel").append("<option style='width:95%'value='"+twoChannel[k]+"'>"+twoChannel[k]+"</option>");
        }
    }
    if (internet=="5G") {
        $("#channel").empty();
        for (var k=0;k<fiveChannel.length;k++){
            $("#channel").append("<option style='width:95%'value='"+fiveChannel[k]+"'>"+fiveChannel[k]+"</option>");
        }
    }
    if (internet=="2.4G,5G"){
        $("#channel").empty();
        for (var k=0;k<fiveChannel.length;k++){
            $("#channel").append("<option style='width:95%'value='"+fiveChannel[k]+"'>"+fiveChannel[k]+"</option>");
        }
        for (var i=0;i<twoChannel.length;i++){
            $("#channel").append("<option style='width:95%'value='"+twoChannel[i]+"'>"+twoChannel[i]+"</option>");
        }

    }
    $('#channel').selectpicker('refresh');
}



getBand=function () {
    var twoBand=['11b','11g',"11n_2"];
    var fiveBand=['11n','11a','11ac',"11ax"];
    var internet=$('#internet').val();
    if (internet=="2.4G"){
        $("#band").empty();
        for (var k=0;k<twoBand.length;k++){
            $("#band").append("<option style='width:95%'value='"+twoBand[k]+"'>"+twoBand[k]+"</option>");
        }
    }
    if (internet=="5G") {
        $("#band").empty();
        for (var k=0;k<fiveBand.length;k++){
            $("#band").append("<option style='width:95%'value='"+fiveBand[k]+"'>"+fiveBand[k]+"</option>");
        }
    }
    if (internet=="2.4G,5G"){
        $("#band").empty();
        for (var k=0;k<fiveBand.length;k++){
            $("#band").append("<option style='width:95%'value='"+fiveBand[k]+"'>"+fiveBand[k]+"</option>");
        }
        for (var i=0;i<twoBand.length;i++){
            $("#band").append("<option style='width:95%'value='"+twoBand[i]+"'>"+twoBand[i]+"</option>");
        }

    }
    $('#band').selectpicker('refresh');
}

$(function() {
    $( "#myModal1" ).draggable();
});

$(function() {
    $( "#instructionDiv" ).draggable();
});


function startTask(){

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
                 "toolId": 10
             },
             success: function (data) {
                 if (data) {
                     $("#progress").css('display', 'block');
                     disableButton();
                     var sitv = setInterval(function () {
                         var prog_url =  "/manager/progreBar/load?toolId=10&timestamp="+timestamp;  //获取进度的请求url
                         $.getJSON(prog_url, function (res) {
                             $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                             var y=document.getElementById("progress_value");
                             y.innerHTML= res + '%';

                         });
                     }, 10000);

                var projectName = $("#projectName").val();
                var projectStage = $("#projectStage").val();
                var projectScheme = $("#projectScheme").val();
                var engineer = $("#engineer").val();
                var equipment = $("#equipment").val();
                var testTime = $("#testTime").val();
                var levelValue = $("#levelValue").val();
                var power = $("#power").val();
                var lineLoss = $("#lineLoss").val();
                var scenario = $("#scenario").val();
                var internet = $("#internet").val();
                var band = $("#band").val();
                var channel = $("#channel").val();
                var selectEquip = $("#selectEquip").val();
                var  params='{'+
                    '"projectName":"'+ projectName+'",'+
                    '"projectStage":"'+ projectStage+'",'+
                    '"projectScheme":"'+ projectScheme+'",'+
                    '"engineer":"'+ engineer+'",'+
                    '"equipment":"'+ equipment+'",'+
                    '"testTime":"'+ testTime+'",'+
                    '"levelValue":"'+ levelValue+'",'+
                    '"power":"'+ power+'",'+
                    '"lineLoss":"'+ lineLoss+'",'+
                    '"scenario":"'+ scenario+'",'+
                    '"internet":"'+ internet+'",'+
                    '"band":"'+ band+'",'+
                    '"channel":"'+ channel+'",'+
                    '"selectEquip":"'+ selectEquip+'"'+
                '}';
                $.ajax({
                    url:"/manager/wifi/start",
                    type:'post',
                    dataType: "text",
                    processData : false,
                    contentType : 'application/json;charset=utf-8',
                    data: params,
                    success: function(data){
                        if (data == "SUCCESS"){
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

function disableButton() {
    var button1 = document.getElementById('start');
    button1.disabled = true;
  }

function enbleButton() {
    var button1 = document.getElementById('start');
    button1.disabled = false;
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

$('#myModal').on('show.bs.modal', function() {
    document.getElementById("confirm").disabled=true;
    unCheck();
})

function unCheck() {
    var checkbox = document.getElementById("ball1");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball2");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball3");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
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