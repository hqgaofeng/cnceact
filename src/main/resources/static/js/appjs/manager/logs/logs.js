var prefix = "/manager/logs"
$(function() {
    load();
    getToolName();
});

function load(deptId) {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/logsInfo", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				pageList  :[10,25,50,100,200,500],
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						startTime:$('#date1').val(),
						endTime:$('#date2').val(),
						toolName:$('#selectToolName').val()

					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					{
						checkbox : true
					},
//					{
//						title : '序号',
//                        formatter : function(value, row, index) {
//                                return value=index+1
//                        }
//					},
					{
						field : 'toolName',
						title : '工具名称'
					},
					{
						field : 'userName',
						title : '用户名'
					},
					/*{
						field : 'logPath',
						title : "日志路径"
					},*/
                    {
                        field : 'createTime',
                        title : '创建时间'
                    },
                     {
                        field: 'operator',
                        title: '操作',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            // var sid_code = base64encode(row.sid + '');   //  sid 加密处理
                            // alert(sid_code);
                            return  '<button id="downLoad" type="button" class="btn  btn-primary" >'+
                                 '<i aria-hidden="true"></i>下载</button>'
                        },
                         events: {
                          'click #downLoad': function (e, value, row, index) {
                             download(row.id);
                          }
                        },
                    },
					]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function getToolName(){
       $.ajax({
            method : 'get', // 服务器数据的请求方式 get or post
            url :"/manager/tools/toolName", // 服务器数据的加载地址
            async :false,
            datatype :'json',
            data:{
            },
            success :function(data) {
                if(data){
                    for(i=0;i<data.length;i++){
                        if (data[i]!=null){
                            $("#selectToolName").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
                        }
                   }
                }
            }
       });
}


function download(id){
        // 构造XMLHttpRequest对象
        var xmlRequest = new XMLHttpRequest();
        // 发送get请求
        xmlRequest.open("GET", prefix+"/download?id="+id, true);
        // 设置响应类型
        xmlRequest.responseType = "blob";
        // 发送请求
        xmlRequest.send([]);

        // 请求获得响应之后,触发下面的回调函数
        xmlRequest.onload = function(oEvent) {
            // 当时满足下面的状态码的时候,视为下载成功
            if ((xmlRequest.status >= 200 && xmlRequest.status < 300) || xmlRequest.status === 304) {

                // 从xmlRequest对象中获取响应的内容
                var content = xmlRequest.response;

                /*
                	从xmlRequest的响应头中获取文件名字符串
                	因为我们将文件以流的形式返回前端,返回的文件没有文件名
                	因此在后端处理的时候,我们将文件名放到响应头中
                	然后在前端从响应头中获取文件名
				*/
                var dispositionStr = xmlRequest.getResponseHeader('content-disposition');
                if (dispositionStr == null || dispositionStr === "") {
                    alert("下载失败!");
                    return;
                }

				// 获取文件名
                let dispositionArr = dispositionStr.split(";");
                // 我们的文件名可能含有汉字,因此在后端进行了UTF-8编码处理,此处进行解码
                let fileName = decodeURIComponent(dispositionArr[1]);

                // 利用response的响应内容构造一个Blob对象(通过Blob对象进行下载)
                var blob = new Blob([content]);

                // 创建一个隐藏的用来下载文件的a标签
                var elink = document.createElement('a');
                elink.download = fileName;
                elink.style.display = 'none';

                /*
                	将blob文件对象转换为内存中对象,并将生成的对象赋给隐藏的a标签
                	bolb对象会暂时存储在客户端的内存中,
                	使用URL.createObjectURL()方法可以创建指向内存文件对象的临时url
                	使用createObjectURL可以节省性能并更快速,只不过需要在不使用的情况下手动释放内存
                	FileReader.readAsDataURL返回文件的base64字符串，比blob url消耗更多内存，但是在不用的时候会自动从内存中清除（通过垃圾回收机制）
				*/
                const src = URL.createObjectURL(blob);
                elink.href = src;
                document.body.appendChild(elink);

                // 模拟点击下载事件,进行下载
                elink.click();

                // 点击之后,移除我们定义的隐藏a标签
                document.body.removeChild(elink);

                // 移除文件对象
                URL.revokeObjectURL(src)
            }
        }
}