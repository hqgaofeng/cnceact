$(function() {
    getEquipment();
});

function getEquipment() {
    $.ajax({
        method : 'get', // 服务器数据的请求方式 get or post
        url :"/manager/equipName", // 服务器数据的加载地址
        async :false,
        datatype :'json',
        data:{
            "id":"11"
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


$(function() {
    $( "#myModal1" ).draggable();
});

$(function() {
    $( "#instructionDiv" ).draggable();
});

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
function fold(){
    var str = document.getElementById("fold1");
    var divlist = document.getElementById("fold");
    var tr = $("tr");
    if (str.innerHTML==="展 开"){
        for (var i = 2; i < tr.length; i++) {
            tr[i].style.display="table-row";
        }
        //divlist.style.display="block";
        str.innerHTML="隐 藏";
    }else {
        for (var i = 2; i < tr.length; i++) {
            tr[i].style.display="none";
        }
        //divlist.style.display="none";
        str.innerHTML="展 开";
    }
/*    if (str.innerHTML==="展 开"){
        divlist.style.display="block";
        str.innerHTML="隐 藏";
    }else {
        divlist.style.display="none";
        str.innerHTML="展 开";
    }*/
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
             "toolId": 11
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
                var myMap={};
                var data=[];
                var projectName=$("#projectName").val();
                var projectStage=$("#projectStage").val();
                var projectScheme=$("#projectScheme").val();

                var engineer=$("#engineer").val();
                var equipment=$("#equipment").val();
                var testTime=$("#testTime").val();

                var PEP=$("#PEP").val();
                var lineLoss2=$("#lineLoss2").val();
                var lineLoss5=$("#lineLoss5").val();

               var selectCase=$("#selectCase").val();
               var selectEquip=$("#selectEquip").val();


                var check = $("input:checked")
                for (var i = 0; i < check.length-3; i++) {
                    var dataRows = {};
                    var a = "#select"+check[i].id+"Speed";
                    var b = "#select"+check[i].id+"Channel";
                    var c = "#"+check[i].id+"PD";
                    var d = "#"+check[i].id+"ZS";
                    var e = "#"+check[i].id+"ID";
                    var speed = $(a).val();
                    var channel = $(b).val();
                    var pd = $(c).text();
                    var zs = $(d).text();
                    var id = $(e).text();
                    dataRows.id=id;
                    dataRows.network=pd;
                    dataRows.band=zs;
                    dataRows.speed=speed;
                    dataRows.channel=channel;
                    data[i]=dataRows;
                }
                myMap.items=data;
                myMap.proName=projectName;
                myMap.testStage=projectStage;
                myMap.testPlan=projectScheme;
                myMap.engineer=engineer;
                myMap.device=equipment;
                myMap.beginTime=testTime;
                myMap.expPower=PEP;
                myMap.cbl4Loss=lineLoss2;
                myMap.cbl5Loss=lineLoss5;
                myMap.scene=selectCase;
                myMap.equipName=selectEquip;
                $.ajax(
                    {
                        method: 'post',
                        url:"/manager/wifiSignal/start",
                        contentType : 'application/json;charset=utf-8',
                        async: false,
                        datatype: 'json',
                        data:JSON.stringify(myMap),
                        success: function(data){
                            if (data == true){
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


function check2(){
    var projectName=document.getElementById("projectName").value.trim();
    var projectStage=document.getElementById("projectStage").value.trim();
    var projectScheme=document.getElementById("projectScheme").value.trim();
    var engineer=document.getElementById("engineer").value.trim();
    var equipment=document.getElementById("equipment").value.trim();
    var testTime=document.getElementById("testTime").value.trim();
    var PEP = document.getElementById("PEP").value.trim();
    var lineLose2 = document.getElementById("lineLose2").value.trim();
    var LineLose5 = document.getElementById("LineLose5").value.trim();
    var selectCase = document.getElementById("selectCase").value.trim();


    var warningDiv2 = document.getElementById("warning2");
    var warningDiv3 = document.getElementById("warning3");
    var warningDiv4 = document.getElementById("warning4");
    var warningDiv5 = document.getElementById("warning5");
    var warningDiv6 = document.getElementById("warning6");
    var warningDiv7 = document.getElementById("warning7");
    var warningDiv8 = document.getElementById("warning8");
    var warningDiv9 = document.getElementById("warning9");
    var warningDiv10 = document.getElementById("warning10");
    var warningDiv11 = document.getElementById("warning11");

    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    if (projectName=="请输入项目名称"){
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv2.innerText = "请输入项目名称";
        warningDiv2.style.display = "block";
        return;
    } else if (projectStage == "请输入项目阶段") {
        warningDiv2.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv3.innerText = "请输入项目阶段";
        warningDiv3.style.display = "block";
        return;
    }else if (projectScheme == "请输入项目方案") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv4.innerText = "请输入项目方案";
        warningDiv4.style.display = "block";
        return;
    }else if (engineer == "请输入工程师姓名") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv5.innerText = "请输入工程师姓名";
        warningDiv5.style.display = "block";
        return;
    }else if (equipment == "请输入测试设备") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv6.innerText = "请输入测试设备";
        warningDiv6.style.display = "block";
        return;
    }else if(testTime=="选择时间") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv7.innerText = "请选择时间";
        warningDiv7.style.display = "block";
        return;
    }else if (PEP == '请输入') {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv8.innerText = "请输入期望功率";
        warningDiv8.style.display = "block";
        return;
    }else if (lineLose2 == "请输入线损") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv9.innerText = "请输入2.4G线损";
        warningDiv9.style.display = "block";
        return;
    }else if (LineLose5 == "请输入线损") {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv11.style.display = "none";
        warningDiv10.innerText = "请输入5G线损";
        warningDiv10.style.display = "block";
        return;
    }else if (selectCase == '') {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.innerText = "请选择测试用例";
        warningDiv11.style.display = "block";
        return;
    } else {
        warningDiv2.style.display = "none";
        warningDiv3.style.display = "none";
        warningDiv4.style.display = "none";
        warningDiv5.style.display = "none";
        warningDiv6.style.display = "none";
        warningDiv7.style.display = "none";
        warningDiv8.style.display = "none";
        warningDiv9.style.display = "none";
        warningDiv10.style.display = "none";
        warningDiv11.style.display = "none";
        $('#myModal').modal('show');
    }
}



