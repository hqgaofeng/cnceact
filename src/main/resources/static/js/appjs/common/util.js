//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

function loadDeviceNames(type){
    $(".selectpicker").selectpicker({
        noneSelectedText : '请选择'
    });
    getDeviceNames(type);
    //初始化刷新数据
    $(window).on('load', function() {
        $('.selectpicker').selectpicker('refresh');
    });
}
function getDeviceNames(type){
    $.ajax({
        url : "/device/device/getDevices?deviceType="+type,
        type : 'GET',
        async : false,
        datatype : 'json',
        success : function(data) {
            if(data){
                var devicenames =[];
                for(var i=0,len=data.length;i<len;i++){
                    var devicedata = data[i];
                    devicenames.push('<option value="'+devicedata.deviceNo+'">'+devicedata.deviceName+'</option>')
                }
                $("#deviceNo").html(devicenames.join(' '));
                document.getElementById("deviceNo").options.selectedIndex = 0;
            }
        },
        error : function() {
            alert('查询设备名称出错');
        }
    });
}

function setDefaultDate(){
    //昨天的时间
    var day1 = new Date();
    day1.setTime(day1.getTime()-24*60*60*1000);
    var month0 = day1.getMonth() + 1;
    var day0 = day1.getDate();
    var mins = day1.getMinutes();
    var seconds = day1.getSeconds();
    if (month0 < 10) {
        month0 = "0" + month0;
    }
    if (day0 < 10) {
        day0 = "0" + day0;
    }
    if(mins < 10){
        mins = "0" + mins;
    }
    if(seconds < 10){
        seconds = "0" + seconds;
    }
    var yesterDate = day1.getFullYear()+"-" + month0 + "-" + day0 + " "
        + day1.getHours() + ":" + mins + ":" + seconds;
    //今天的时间
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var currentdate = year + '-' + month + '-' + day + " " + date.getHours() + ":" + date.getMinutes()
        + ":" + date.getSeconds();
    //时间段查询-文本框赋值 开始时间
    $("#startTime").val(yesterDate);
    //时间段查询-文本框赋值 结束时间
    $("#endTime").val(currentdate);
}
function exportExcel(action) {
    var form = $("<form>");
    form.attr('style', 'display:none');
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action',action);

    var input1 = $('<input>');
    input1.attr('type', 'hidden');
    input1.attr('name', 'deviceName');
    input1.attr('value',$('#searchName').val());

    var input2 = $('<input>');
    input2.attr('type', 'hidden');
    input2.attr('name', 'devType');
    input2.attr('value',$("#type").val());
    var input3 = $('<input>');
    input3.attr('type', 'hidden');
    input3.attr('name', 'attrId');
    input3.attr('value',getAttrId());

    $('body').append(form);
    form.append(input1);
    form.append(input2);
    form.append(input3);
    /*JSON.stringify($.serializeObject($('#searchForm')))*/

    form.submit();
    form.remove();
}