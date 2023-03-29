var prefix = "/manager/machine"

$(function() {
    checkNodes();
    getDevice();
});

function getDevice() {
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
                    $("#selectLabelName").append("<option value='"+data[0]+"'>"+data[0]+"</option>");
                    $('#selectLabelName').attr("disabled",true)
                }else{
                    $("#selectLabelName").append('<option value="请选择执行设备">请选择执行设备</option>');
                    $('#selectLabelName').attr("disabled",false)
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#selectLabelName").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                        }
                    }
                }
            }
        }
    });
}

//数组合并并去重
arrayDuplicateRemoval=function (arr1,arr2){
    var _arr = new Array();
    for(var i=0;i<arr1.length;i++){
        _arr.push(arr1[i]);
    }
    for(var i=0;i<arr2.length;i++){
        var flag = true;
        for(var j=0;j<arr1.length;j++){
            if(arr2[i]==arr1[j]){
                flag=false;
                break;
            }
        }
        if(flag){
            _arr.push(arr2[i]);
        }
    }
    return _arr;
}

//步进信道
getTestStep=function () {

    var LTEChannel = ['10', '20', '30', '40', '50'];
    var WCDMAChannel = ['1', '2', '3', '4', '5'];
    var GSMChannel = ['1', '2', '3', '4', '5'];
    var CDMAChannel = ['1', '2', '3', '4', '5'];
    var netState = $('#netState').val();

    if (netState.length === 1) {
        if (netState == "GSM") {
            $("#testStep").empty();
            for (var k = 0; k < GSMChannel.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + GSMChannel[k] + "'>" + GSMChannel[k] + "</option>");
            }
        }
        if (netState == "CDMA") {
            $("#testStep").empty();
            for (var k = 0; k < CDMAChannel.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + CDMAChannel[k] + "'>" + CDMAChannel[k] + "</option>");
            }
        }
        if (netState == "WCDMA") {
            $("#testStep").empty();
            for (var k = 0; k < WCDMAChannel.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + WCDMAChannel[k] + "'>" + WCDMAChannel[k] + "</option>");
            }
        }
        if (netState == "LTE") {
            $("#testStep").empty();
            for (var k = 0; k < LTEChannel.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + LTEChannel[k] + "'>" + LTEChannel[k] + "</option>");
            }
        }
    }

    if (netState.length === 2) {
        if (netState.includes("GSM" && "CDMA")) {
            var newArray = arrayDuplicateRemoval(GSMChannel,CDMAChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "WCDMA")) {
            var newArray = arrayDuplicateRemoval(GSMChannel,WCDMAChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");

            }
        }
        if (netState.includes("GSM" && "LTE")) {
            var newArray = arrayDuplicateRemoval(GSMChannel,LTEChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "WCDMA")) {
            var newArray = arrayDuplicateRemoval(CDMAChannel,WCDMAChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "LTE")) {
            var newArray = arrayDuplicateRemoval(CDMAChannel,LTEChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("WCDMA" && "LTE")) {
            var newArray = arrayDuplicateRemoval(WCDMAChannel,LTEChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    if (netState.length === 3) {
        if (netState.includes("GSM" && "CDMA" && "WCDMA")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMChannel,CDMAChannel);
            newArray = arrayDuplicateRemoval(newArray,WCDMAChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "CDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMChannel,CDMAChannel);
            newArray = arrayDuplicateRemoval(newArray,LTEChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMChannel,WCDMAChannel);
            newArray = arrayDuplicateRemoval(newArray,LTEChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(CDMAChannel,WCDMAChannel);
            newArray = arrayDuplicateRemoval(newArray,LTEChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    if (netState.length === 4) {
        if (netState.includes("GSM" && "CDMA" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMChannel,CDMAChannel);
            newArray = arrayDuplicateRemoval(newArray,WCDMAChannel);
            newArray = arrayDuplicateRemoval(newArray,LTEChannel);
            $("#testStep").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#testStep").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    $('#testStep').selectpicker('refresh');
}

//测试场景
getScenario=function () {
    var SC_CDMA = ['CDMA_音乐播放冲突', 'CDMA_动态壁纸冲突', 'CDMA_马达冲突', 'CDMA_触屏冲突', '灭屏'];
    var SC_WCDMA = ['WCDMA_音乐冲突', 'WCDMA_动态壁纸冲突', 'WCDMA_马达冲突', 'WCDMA_触屏冲突', '灭屏'];
    var SC_GSM = ['GSM_音乐播放冲突', 'GSM_动态壁纸冲突', 'GSM_马达冲突', 'GSM_触屏冲突', '灭屏'];
    var SC_LTE = ['LTE_动态壁纸冲突', 'LTE_马达冲突', 'LTE_前置camera冲突-主摄', 'LTE_音乐播放冲突', 'LTE_后置camera冲突-主摄', 'LTE_游戏场景冲突', 'LTE_触屏冲突',
        'LTE_后置camera冲突-广角', 'LTE_后置camera冲突-景深', 'LTE_后置camera冲突-微距', 'LTE_后置camera冲突-长焦', 'LTE_前置camera冲突-广角',
        'LTE_数字耳机冲突', 'LTE_有线耳机冲突', '灭屏'];
    var netState = $('#netState').val();

    if (netState.length === 1) {
        if (netState == "GSM") {
            $("#scenario").empty();
            for (var k = 0; k < SC_GSM.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + SC_GSM[k] + "'>" + SC_GSM[k] + "</option>");
            }
        }
        if (netState == "CDMA") {
            $("#scenario").empty();
            for (var k = 0; k < SC_CDMA.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + SC_CDMA[k] + "'>" + SC_CDMA[k] + "</option>");
            }
        }
        if (netState == "WCDMA") {
            $("#scenario").empty();
            for (var k = 0; k < SC_WCDMA.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + SC_WCDMA[k] + "'>" + SC_WCDMA[k] + "</option>");
            }
        }
        if (netState == "LTE") {
            $("#scenario").empty();
            for (var k = 0; k < SC_LTE.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + SC_LTE[k] + "'>" + SC_LTE[k] + "</option>");
            }
        }
    }

    if (netState.length === 2) {
        if (netState.includes("GSM" && "CDMA")) {
            var newArray = arrayDuplicateRemoval(SC_GSM,SC_CDMA);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "WCDMA")) {
            var newArray = arrayDuplicateRemoval(SC_GSM,SC_WCDMA);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");

            }
        }
        if (netState.includes("GSM" && "LTE")) {
            var newArray = arrayDuplicateRemoval(SC_GSM,SC_LTE);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "WCDMA")) {
            var newArray = arrayDuplicateRemoval(SC_CDMA,SC_WCDMA);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "LTE")) {
            var newArray = arrayDuplicateRemoval(SC_CDMA,SC_LTE);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("WCDMA" && "LTE")) {
            var newArray = arrayDuplicateRemoval(SC_WCDMA,SC_LTE);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    if (netState.length === 3) {
        if (netState.includes("GSM" && "CDMA" && "WCDMA")) {
            var newArray;
            newArray = arrayDuplicateRemoval(SC_GSM,SC_CDMA);
            newArray = arrayDuplicateRemoval(newArray,SC_WCDMA);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "CDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(SC_GSM,SC_CDMA);
            newArray = arrayDuplicateRemoval(newArray,SC_LTE);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(SC_GSM,SC_WCDMA);
            newArray = arrayDuplicateRemoval(newArray,SC_LTE);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(SC_CDMA,SC_WCDMA);
            newArray = arrayDuplicateRemoval(newArray,SC_LTE);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    if (netState.length === 4) {
        if (netState.includes("GSM" && "CDMA" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(SC_GSM,SC_CDMA);
            newArray = arrayDuplicateRemoval(newArray,SC_WCDMA);
            newArray = arrayDuplicateRemoval(newArray,SC_LTE);
            $("#scenario").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#scenario").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    $('#scenario').selectpicker('refresh');
}

//测试频段
getSpectrum=function () {
    var GSMBand = ['850', '900'];
    var CDMABand = ['BC0', 'BC1', 'BC2', 'BC3', 'BC4', 'BC5', 'BC6', 'BC7', 'BC8', 'BC9', 'BC10', 'BC11', 'BC12', 'BC13', 'BC14', 'BC15'];
    var WCDMABand = ['Band1', 'Band2', 'Band3', 'Band4', 'Band5', 'Band6', 'Band7', 'Band8', 'Band9', 'Band10', 'Band11', 'Band12', 'Band13', 'Band14', 'Band15', 'Band16', 'Band17', 'Band18', 'Band19'];
    var LTEBand = ['Band1', 'Band2', 'Band3', 'Band4', 'Band5', 'Band6', 'Band7', 'Band8', 'Band9', 'Band10', 'Band11', 'Band12', 'Band13', 'Band14', 'Band15', 'Band16', 'Band17', 'Band18', 'Band19', 'Band20', 'Band21', 'Band22', 'Band23', 'Band24', 'Band25', 'Band26', 'Band27', 'Band28', 'Band29', 'Band30', 'Band31', 'Band32', 'Band33', 'Band34', 'Band35', 'Band36', 'Band37', 'Band38', 'Band39', 'Band40', 'Band41', 'Band42', 'Band43', 'Band44', 'Band45', 'Band46', 'Band47', 'Band48', 'Band49', 'Band50', 'Band51', 'Band52', 'Band53', 'Band54', 'Band55', 'Band56', 'Band57', 'Band58', 'Band59', 'Band60', 'Band61', 'Band62', 'Band63', 'Band64', 'Band65', 'Band66', 'Band67', 'Band68', 'Band69', 'Band70', 'Band71', 'Band72', 'Band73', 'Band74', 'Band75', 'Band76'];
    var netState = $('#netState').val();

    if (netState.length === 1) {
        if (netState == "GSM") {
            $("#spectrum").empty();
            for (var k = 0; k < GSMBand.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + GSMBand[k] + "'>" + GSMBand[k] + "</option>");
            }
        }
        if (netState == "CDMA") {
            $("#spectrum").empty();
            for (var k = 0; k < CDMABand.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + CDMABand[k] + "'>" + CDMABand[k] + "</option>");
            }
        }
        if (netState == "WCDMA") {
            $("#spectrum").empty();
            for (var k = 0; k < WCDMABand.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + WCDMABand[k] + "'>" + WCDMABand[k] + "</option>");
            }
        }
        if (netState == "LTE") {
            $("#spectrum").empty();
            for (var k = 0; k < LTEBand.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + LTEBand[k] + "'>" + LTEBand[k] + "</option>");
            }
        }
    }

    if (netState.length === 2) {
        if (netState.includes("GSM" && "CDMA")) {
            var newArray = arrayDuplicateRemoval(GSMBand,CDMABand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "WCDMA")) {
            var newArray = arrayDuplicateRemoval(GSMBand,WCDMABand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");

            }
        }
        if (netState.includes("GSM" && "LTE")) {
            var newArray = arrayDuplicateRemoval(GSMBand,LTEBand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "WCDMA")) {
            var newArray = arrayDuplicateRemoval(CDMABand,WCDMABand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "LTE")) {
            var newArray = arrayDuplicateRemoval(CDMABand,LTEBand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("WCDMA" && "LTE")) {
            var newArray = arrayDuplicateRemoval(WCDMABand,LTEBand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    if (netState.length === 3) {
        if (netState.includes("GSM" && "CDMA" && "WCDMA")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMBand,CDMABand);
            newArray = arrayDuplicateRemoval(newArray,WCDMABand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "CDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMBand,CDMABand);
            newArray = arrayDuplicateRemoval(newArray,LTEBand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("GSM" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMBand,WCDMABand);
            newArray = arrayDuplicateRemoval(newArray,LTEBand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
        if (netState.includes("CDMA" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(CDMABand,WCDMABand);
            newArray = arrayDuplicateRemoval(newArray,LTEBand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    if (netState.length === 4) {
        if (netState.includes("GSM" && "CDMA" && "WCDMA" && "LTE")) {
            var newArray;
            newArray = arrayDuplicateRemoval(GSMBand,CDMABand);
            newArray = arrayDuplicateRemoval(newArray,WCDMABand);
            newArray = arrayDuplicateRemoval(newArray,LTEBand);
            $("#spectrum").empty();
            for (var k = 0; k < newArray.length; k++) {
                $("#spectrum").append("<option style='width:95%'value='" + newArray[k] + "'>" + newArray[k] + "</option>");
            }
        }
    }

    $('#spectrum').selectpicker('refresh');
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
                var projectPlan = $("#projectPlan").val();
                var engineer = $("#engineer").val();
                var device = $("#device").val();
                var testTime = $("#testTime").val();
                var lineLoss = $("#lineLoss").val();
                var netState = $("#netState").val();
                var testStep = $("#testStep").val();
                var spectrum = $("#spectrum").val();
                var scenario = $("#scenario").val();
                var bandwidth = $("#bandwidth").val();
                var beginChannel = $("#beginChannel").val();
                var endChannel = $("#endChannel").val();
                var selectLabelName = $("#selectLabelName").val();
                     var params = '{' +
                         '"projectName":"' + projectName + '",' +
                         '"projectStage":"' + projectStage + '",' +
                         '"projectPlan":"' + projectPlan + '",' +
                         '"engineer":"' + engineer + '",' +
                         '"device":"' + device + '",' +
                         '"testTime":"' + testTime + '",' +
                         '"netState":"' + netState + '",' +
                         '"testStep":"' + testStep + '",' +
                         '"lineLoss":"' + lineLoss + '",' +
                         '"scenario":"' + scenario + '",' +
                         '"spectrum":"' + spectrum + '",' +
                         '"bandwidth":"' + bandwidth + '",' +
                         '"beginChannel":"' + beginChannel + '",' +
                         '"endChannel":"' + endChannel + '",' +
                         '"selectLabelName":"' + selectLabelName + '"' +
                         '}';
                $.ajax({
                    url:"/manager/machine/start",
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