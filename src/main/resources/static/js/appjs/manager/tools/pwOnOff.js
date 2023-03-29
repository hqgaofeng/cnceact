var prefix = "/manager/pwOnOff";

$(function() {
    getEquipment();
    checkNodes();
});

function checkPwOnOffIsNull(){
    var hostIP = document.getElementById("hostIP").value.trim();
    var slaveIP = document.getElementById("slaveIP").value.trim();
    var loop = document.getElementById("loop").value.trim();
    var temper = document.getElementById("temper").value.trim();
    var outlet = document.getElementById("outlet").value.trim();
    var onTime = document.getElementById("onTime").value.trim();
    var offTime = document.getElementById("offTime").value.trim();
    var devName = document.getElementById("devName").value.trim();

    var warning1 = document.getElementById("warning1");
    var warning2 = document.getElementById("warning2");
    var warning3 = document.getElementById("warning3");
    var warning4 = document.getElementById("warning4");
    var warning5 = document.getElementById("warning5");
    var warning6 = document.getElementById("warning6");
    var warning7 = document.getElementById("warning7");


    if (hostIP == '请输入设备IP') {
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";

        warning1.innerText = "请输入主设备IP";
        warning1.style.display = "block";
        return;
    } else if (slaveIP ==  '请输入设备IP，多个IP用逗号分割') {
        warning1.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";

        warning2.innerText = "请输入从设备IP，多个IP用逗号分割";
        warning2.style.display = "block";
        return;
    } else if (loop == '') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";

        warning3.innerText = "请输入循环次数";
        warning3.style.display = "block";
        return;
    } else if (temper == '请输入测试温度') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";

        warning4.innerText = "请输入测试温度";
        warning4.style.display = "block";
        return;
    } else if (outlet == '') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";

        warning5.innerText = "请选择测试开关";
        warning5.style.display = "block";
        return;
    } else if (onTime == '请输入上电时间'){
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning7.style.display = "none";

        warning6.innerText = "请输入上电时间";
        warning6.style.display = "block";
    } else if (offTime == '请输入下电时间') {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";

        warning7.innerText = "请输入下电时间";
        warning7.style.display = "block";
    } else {
        warning1.style.display = "none";
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warning5.style.display = "none";
        warning6.style.display = "none";
        warning7.style.display = "none";
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
                    $("#devName").append("<option value='"+data[0]+"'>"+data[0]+"</option>");
                    $('#devName').attr("disabled",true)
                }else{
                    $("#devName").append('<option value="请选择执行设备">请选择执行设备</option>');
                    $('#devName').attr("disabled",false)
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#devName").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                        }
                    }
                }
            }
        }
    });
}

function cancelTest(){
    var devName = $('#devName').val();
    $.ajax({
        url:"/manager/pwOnOff/cancel",
        type:'post',
        contentType:'application/json;charset=utf-8',
        data:{
            "devName":devName
        },
        async:false,
        success:function (data) {
            if(data){
                changeProgress();
                enbleButton();
            }
        },
        error:function () {
        }
    });
}

        function startTask() {
            $('#progress_bar').width(0 + '%'); //根据获得的进度改变进度条
            var y = document.getElementById("progress_value");
            y.innerHTML = 0 + '%';
            var timestamp = Date.parse(new Date());
            $.ajax({
                method: 'get', // 服务器数据的请求方式 get or post
                url: "/manager/progreBar/selecTask", // 服务器数据的加载地址
                async: false,
                datatype: 'json',
                data: {
                    "toolId": 12
                },
                success: function (data) {
                    if (data) {
                        $("#progress").css('display', 'block');
                        disableButton();
                        var sitv = setInterval(function () {
                            var prog_url = "/manager/progreBar/load?toolId=12&timestamp=" + timestamp;  //获取进度的请求url
                            $.getJSON(prog_url, function (res) {
                                $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                                var y = document.getElementById("progress_value");
                                y.innerHTML = res + '%';

                            });
                        }, 10000);
                        var model;
                        var radios = document.getElementsByName("selectModel");
                        for (var i = 0; i < radios.length; i++) {
                            if (radios[i].checked == true) {
                                model = i + 1;
                            }
                        }
                        var hostIP = $("#hostIP").val();
                        var slaveIP = $("#slaveIP").val();
                        var temper = $("#temper").val();
                        var outlet = $("#outlet").val();
                        var onTime = $("#onTime").val();
                        var offTime = $("#offTime").val();
                        var loop = $("#loop").val();
                        var devName = $("#devName").val();
                        var params = '{' +
                            '"model":"' + model + '",' +
                            '"hostIP":"' + hostIP + '",' +
                            '"slaveIP":"' + slaveIP + '",' +
                            '"temper":"' + temper + '",' +
                            '"outlet":"' + outlet + '",' +
                            '"onTime":"' + onTime + '",' +
                            '"offTime":"' + offTime + '",' +
                            '"loop":"' + loop + '",' +
                            '"devName":"' + devName + '"' +
                            '}';
                        $.ajax({
                            url: "/manager/pwOnOff/beginTask",
                            type: 'post',
                            dataType: "text",
                            processData: false,
                            contentType: 'application/json;charset=utf-8',
                            data: params,
                            success: function (data) {
                                if (data) {
                                    clearInterval(sitv);
                                    $('#progress_bar').width('100%');
                                    var y = document.getElementById("progress_value");
                                    y.innerHTML = "100% 任务已完成";
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

function changeProgress() {
    var hh1 = document.getElementById("progress");
    if (hh1.style.display == "" || hh1.style.display == "none") {
        hh1.style.display = "block"
    } else {
        hh1.style.display = "none"
    }
}

        function check() {
            var checkbox = document.getElementById("ball3");//选中checkbox的id；
            var checkbox1 = document.getElementById("ball1");//选中checkbox的id；
            var checkbox2 = document.getElementById("ball2");//选中checkbox的id；
            if (checkbox.checked == true && checkbox1.checked == true && checkbox2.checked == true) {//按钮已选中
                document.getElementById("confirm").removeAttribute("disabled");//移除disabled
            } else {
                document.getElementById("confirm").disabled = true;
            }
        }

        $('#myModal').on('show.bs.modal', function () {
            document.getElementById("confirm").disabled = true;
            unCheck();
        })

        function unCheck() {
            var checkbox = document.getElementById("ball1");//选中checkbox的id；
            var checkbox1 = document.getElementById("ball2");//选中checkbox的id；
            var checkbox2 = document.getElementById("ball3");//选中checkbox的id；
            checkbox.checked = false;
            checkbox1.checked = false;
            checkbox2.checked = false;
        }

        function checkNodes() {
            $.ajax({
                method: 'get', // 服务器数据的请求方式 get or post
                url: "/manager/progreBar/checkNodes", // 服务器数据的加载地址
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data) {
                        $("#myModal9").modal({
                            show: false
                        });
                    } else {
                        $('.modal-backdrop').remove();
                        $("#myModal9").modal({
                            show: true
                        });
                    }
                }
            });
        }