var prefix = "/manager/cameraTools"

$(function() {
    getEquipment();
    checkNodes();
});

function checkCameraIsNull(){
    var path = document.getElementById("path").value.trim();
    var folderName = document.getElementById("folderName").value.trim();
    var projectName = document.getElementById("projectName").value.trim();
    var cameraType = document.getElementById("cameraType").value.trim();

    var warningDiv = document.getElementById("warning");
    var warning2 = document.getElementById("warning2");
    var warning3 = document.getElementById("warning3");
    var warning4 = document.getElementById("warning4");

    var pathRegex = /^[a-zA-Z]:\\(.+\\)*[^\\/:*?"<>|]*$/;
    // var reg = new RegExp('[\\\\/:*?\"<>|]');
    var reg = new RegExp('^[^？\\ * | “ < > : /]{1,256}$');
    var reg = new RegExp('^[^?v \\ * | "" < > : /]{1,256}$');

    if (projectName == '请选择项目') {
        warning3.innerText = "请选择项目";
        warning3.style.display = "block";
        return;
    } else if (cameraType == '请选择类型' ){
        warning3.style.display = "none";
        warning4.innerText = "请选择类型";
        warning4.style.display = "block";
        return;
    } else if (path == '') {
        warning3.style.display = "none";
        warning4.style.display = "none";

        warningDiv.innerText = "导出路径不能为空";
        warningDiv.style.display = "block";
        return;
    } else if (!pathRegex.test(path)){
        warning3.style.display = "none";
        warning4.style.display = "none";

        warningDiv.innerText = "请输入合法的导出路径";
        warningDiv.style.display = "block";
        return;
    } else if (folderName == "请输入文件夹" || folderName == ""){
        warning3.style.display = "none";
        warning4.style.display = "none";
        warningDiv.style.display = "none";

        warning2.innerText = "文件夹名不能为空";
        warning2.style.display = "block";
        return;
    } else if (!reg.test(folderName)){
        warning3.style.display = "none";
        warning4.style.display = "none";
        warningDiv.style.display = "none";

        warning2.innerText = "请输入0-9、A-Z、a-z、中文,但不能有：？\\ * | “ < > : /　 等";
        warning2.style.display = "block";
        return;
    } else {
        warning2.style.display = "none";
        warning3.style.display = "none";
        warning4.style.display = "none";
        warningDiv.style.display = "none";

        $('#myModal').modal('show')
    }
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
              "toolId": 1
          },
          success: function (data) {
              if (data) {
                  $("#progress").css('display', 'block');
                  disableButton();
                  var sitv = setInterval(function () {
                      var prog_url =  "/manager/progreBar/load?toolId=1&timestamp="+timestamp;  //获取进度的请求url
                      $.getJSON(prog_url, function (res) {
                          $('#progress_bar').width(res + '%'); //根据获得的进度改变进度条
                          var y=document.getElementById("progress_value");
                          y.innerHTML= res + '%';
                      });
                  }, 10000);

                    var obj = document.getElementById("projectName");
                    var index = obj.selectedIndex;
                    var projectName = obj.options[index].value;

                    var obj1 = document.getElementById("cameraType");
                    var index = obj1.selectedIndex;
                    var cameraType = obj1.options[index].value;

                    var path=  document.getElementById("path").value;
                    var folderName=  document.getElementById("folderName").value;

                    var equipName=$('#selectEquip').val();

                    $.ajax({
                        url:"/manager/cameraTools/start",
                        type:'post',
                        data:{"projectName":projectName,
                               "cameraType":cameraType,
                               "path":path,
                               "folderName":folderName,
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
function clickCancelButton(){
	var equipName=$('#selectEquip').val();
	if (equipName=="请选择执行设备"){
	    layer.msg("请选择执行设备");
        return;
	}
    $.ajax({
       url:"/manager/cameraTools/cancel",
       type:'post',
//       contentType:'application/json;charset=utf-8',
       data:{
        "equipName"  :equipName
        },
       async:false,
       success:function (data) {
           if(data==true){
               $("#progress").css('display', 'none');
               $('#progress_bar').width( '0%'); //根据获得的进度改变进度条
               var y=document.getElementById("progress_value");
               y.innerHTML= '0%';
               enbleButton();
           }
       },
    });

}

function getEquipment(deptId) {
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

function Change1() {
        var hh1 = document.getElementById("progress");
        if (hh1.style.display == "" || hh1.style.display == "none") {
            hh1.style.display = "block"
        } else {
            hh1.style.display = "none"
        }
    }


function unCheck() {
    var checkbox = document.getElementById("ball3");//选中checkbox的id；
    var checkbox1 = document.getElementById("ball1");//选中checkbox的id；
    var checkbox2 = document.getElementById("ball2");//选中checkbox的id；
    checkbox.checked=false;
    checkbox1.checked=false;
    checkbox2.checked=false;
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
