var prefix = "/manpower/manpower";

// "1", "姓名"; "2", "角色"; "3", "总计"; "4", "未投入"

$(function() {
    getEffectMonth();
    getDepartment();
    load();
    $(window).trigger("resize");
    // TableExport.init();
});

function getEffectMonth() {
    $.ajax({
        method : 'get', // 服务器数据的请求方式 get or post
        url :"/project/template/loadMonth", // 服务器数据的加载地址
        async :false,
        datatype :'json',
        success :function(data) {
            if (data.length == 0) {
                $("#month").append('<option value=""></option>');
                return;
            }
            for (var i = 0; i < data.length; i++) {
                if (data[i] == "") continue;
                $("#month").append('<option value='+data[i]+'>'+data[i]+'</option>');
            }
        }
    });
}

function getDepartment() {
    var region = $("#region").val();
    $.ajax({
        method : 'get',
        url :"/manpower/user/department",
        async :false,
        datatype :'json',
        data:{
            "region":region
        },
        success :function(data) {
            $("#department").empty();
            $("#department").append('<option value="">请选择一级部门</option>');
            for (var i = 0; i < data.length; i++) {
                $("#department").append('<option value='+data[i].department+'>'+data[i].department+'</option>');
            }
        }
    });
}

function getDomain() {
    var region = $("#region").val();
    var department = $("#department").val();
    $.ajax({
        method : 'get',
        url :"/manpower/user/domain",
        async :false,
        datatype :'json',
        data:{
            "region" : region,
            "department" : department
        },
        success :function(data) {
            if (data.length==0) {
                $("#domain").append('<option value=""></option>');
                return;
            }
            $("#domain").empty();
            $("#domain").append('<option value="">请选择二级部门</option>');
            for(var i = 0; i < data.length; i++) {
                console.log(i+"---"+data[i].domain)
                $("#domain").append('<option value='+data[i].domain+'>'+data[i].domain+'</option>');
            }

        }
    });
}

function load(pageIndex) {
    var region = $("#region").val();
    var department = "AQA部";
    var domain = "AQA";
    var d = new Date();
    var y = d.getFullYear();
    var m = d.getMonth()+1;
    var nowMonth = y + "-" + m;
    $.ajax({
        type: "get",
        url: prefix +"/list",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:{
            region:region,
            department:department,
            domain:domain,
            effectMonth:nowMonth
        },
        success: function (json) {
            if (json.length==0) {
                parent.layer.msg("当前查询无数据");
            }
            // 加载接口数据
            $('#exampleTable').bootstrapTable('destroy').bootstrapTable({
                columns: getDataColumns(json),
                data: json,
                cache: false,
                striped : true,
                sidePagination: "client",
                sortOrder: "desc",
                pagination: true,
                paginationPreText : '上一页',
                paginationNextText : '下一页',
                pageNumber : pageIndex || 1,
                pageSize : 10,
                pageList : [10,15,20,30,50,100,150,200],
                toolbar : '#exampleToolbar',
                maintainMeta:true,
                // stickyHeader: true,
                // height:680,
                // search: false,
                // 是否开启冻结列
                fixedColumns: true,
                // 需要冻结的列数
                fixedNumber: 4,
                // 行内编辑
                editable:true,
                showFooter: true,
                onPostBody:function () {
                    merge_footer();
                },
                showExport: phoneOrPc(),
                exportDataType: "all",//basic', 'all', 'selected'.
                exportTypes: ['excel', 'xlsx'],
                //exportButton: $('#btn_export'),//为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
                exportOptions: {
                    ignoreColumn: [0,1,2],//忽略某一列的索引,可以选择多个列
                    fileName: '人力导出数据',
                    worksheetName: 'Sheet1',
                    tableName: '数据列表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
                }
            });
            // 隐藏ID列
            $('#exampleTable').bootstrapTable('hideColumn', 'userId');
        },
        error: function () {
            // location.reload();
        }
    });
}

function getDataColumns(json) {
    var dataColumns = [];
    if (json.length == 0) return dataColumns;
    var jsonFirst = Object.keys(json[0]);
    for (var i = 0; i < jsonFirst.length; i++) {
        var property = jsonFirst[i];
        if (property === "1") {
            dataColumns.push({
                title: "姓名",
                field: property,
                sortable: true,
                edit: false,
                editable: false,
                align: "center",
                footerFormatter: function(rows) {
                    return '人力统计';
                }
            });
            continue;
        }
        if (property === "2") {
            dataColumns.push({
                title: "角色",
                field: property,
                sortable: true,
                edit: false,
                editable: false,
                align: "center",
                footerFormatter: function(rows) {
                    return '总和';
                }
            });
            continue;
        }
        if (property === "3") {
            dataColumns.push({
                title: "总计",
                field: property,
                sortable: true,
                edit: false,
                editable: false,
                align: "center",
                footerFormatter: function (rows) {
                    var field = this.field;
                    var count = 0;
                    var col_list = [];
                    for (var i in rows) {
                        var col_value = rows[i][field];
                        if(col_value != null && col_value !== ''){
                            col_list.push(Number(col_value))
                        }
                    }
                    var sum = eval(col_list.join('+'));
                    return parseFloat(sum).toFixed(2);
                }
            });
            continue;
        }
        if (property === "4") {
            dataColumns.push({
                title: "未投入",
                field: property,
                sortable: true,
                edit: false,
                editable: false,
                align: "center",
                footerFormatter: function (rows) {
                    var field = this.field;
                    var count = 0;
                    var col_list = [];
                    for (var i in rows) {
                        var col_value = rows[i][field];
                        if(col_value != null && col_value !== ''){
                            col_list.push(Number(col_value))
                        }
                    }
                    var sum = eval(col_list.join('+'));
                    return parseFloat(sum).toFixed(2);
                }
            });
            continue;
        }
        dataColumns.push({
            title: property,
            field: property,
            switchable: true,
            sortable: true,
            edit: false,
            editable: false,
            footerFormatter: function (rows) {
                var field = this.field;
                if (field != "id" || field != "1"/*"姓名"*/ || field != "2"/*"角色"*/) {
                    var field = this.field;
                    var count = 0;
                    var col_list = [];
                    for (var i in rows) {
                        var col_value = rows[i][field];
                        if(col_value != null && col_value !== ''){
                            col_list.push(Number(col_value))
                        }
                    }
                    var sum = eval(col_list.join('+'));
                    return parseFloat(sum).toFixed(2);
                }
            }
        });
    }
    return dataColumns;
}
function queryData(pageIndex, pageSize) {
    var month = $("#month").val();
    var region = $("#region").val();
    var department = $("#department").val();
    var domain = $("#domain").val();
    if (department === "0") {
        parent.layer.msg("请选择部门");
        return;
    }
    if (month === "0") {
        parent.layer.msg("请选择时间");
        return;
    }
    $.ajax({
        type: "get",
        url: prefix +"/list",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:{
            region:region,
            department:department,
            domain:domain,
            effectMonth:month
        },
        success: function (json) {
            if (json.length == 0) {
                parent.layer.msg("当前查询无数据");
                return;
            }
            $('#exampleTable').bootstrapTable('destroy').bootstrapTable({
                data: json,
                cache: false,
                striped: true,
                sidePagination: "client",
                sortOrder: "desc",
                pagination: true,
                paginationPreText : '上一页',
                paginationNextText : '下一页',
                pageNumber: pageIndex || 1,
                pageSize: pageSize || 10,
                pageList: [10,15,20,30,50,100,150,200],
                toolbar: '#exampleToolbar',
                // stickyHeader: true,
                // height:680,
                // search: false,
                // 是否开启冻结列
                fixedColumns: true,
                // 需要冻结的列数
                fixedNumber: 4,
                // 行内编辑
                editable:true,
                columns: getDataColumns(json),
                showFooter: true,
                onPostBody:function () {
                    merge_footer();
                },
                showExport: phoneOrPc(),
                exportDataType: "all",
                exportTypes: ['excel', 'xlsx'],
                exportOptions: {
                    ignoreColumn: [0,1,2],
                    fileName: '导出数据',
                    worksheetName: 'Sheet1',
                    tableName: '设备列表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
                },
                onDblClickCell: function(field, value, row, $element) {
                    if (!isEditableField(field)) return;
                    $element.attr('contenteditable', true);
                    $element.blur(function() {
                        var index = $element.parent().data('index');
                        var userId = row.userId;
                        var tdValue = $element.html();
                        $(window).keydown( function(e) {
                            var key = window.event?e.keyCode:e.which;
                            if (key.toString() == "13") {
                                return false;
                            }
                        });
                        var result = checkCellInput(tdValue);
                        if (result == null) {
                            verifyData(index, field, tdValue, userId);
                        } else {
                            parent.layer.msg("输入不正确！\n" + result);
                        }
                    })
                }
            }).bootstrapTable('refresh');

            $('#exampleTable').on('click-cell.bs.table', function (e, field, value, row, $element) {
                // alert(JSON.stringify(field));
            });
            // 隐藏ID列
            $('#exampleTable').bootstrapTable('hideColumn', 'userId');



        },
        error: function () {
            // location.reload();
        }
    });
}


function checkCellInput(tdValue) {
    var total_len = tdValue.toString().length;
    if (total_len <= 0) return null; // EMPTY is valid.
    if (isNaN(tdValue)) return "请输入数字";
    if (tdValue < 0) return "请输入正数";
    var len = tdValue.toString().split('.').pop().length;
    if (len > 2) return "最多输入两位小数";
    return null;
}

function isEditableField(field) {
    if (field == "1" || field == "姓名") return false;
    if (field == "2" || field == "角色") return false;
    if (field == "3" || field == "总计") return false;
    if (field == "4" || field == "未投入") return false;
    return true;
}

function phoneOrPc(){
    var sUserAgent = navigator.userAgent.toLowerCase();
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
    var bIsAndroid = sUserAgent.match(/android/i) == "android";
    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
        return false;
    } else {
        return true;
    }
}

function merge_footer() {
    // 获取table表中footer 并获取到这一行的所有列
    var footer_tbody = $('.fixed-table-footer table tbody');
    var footer_tr = footer_tbody.find('>tr');
    var footer_td = footer_tr.find('>td');
    var footer_td_1 = footer_td.eq(0);
    for (var i = 0; i < 2; i++) {
        footer_td.eq(i).hide();
    }
    footer_td_1.attr('colspan', 2).show();
}

// 行内编辑保存
function verifyData(index, field, value, userId) {
    $('#exampleTable').bootstrapTable('updateCell', {
        index: index,       // 行索引
        field: field,       // 列名
        value: value        // cell值
    });
    datacells = $("#exampleTable").find("tr[data-index="+index+"]").find("td");
    var colList = [];
    // var count=0;
    for (var i = 4; i < datacells.length; i++) {
        var str = datacells[i].innerText;
        if (str !=="" && str !== "-" && str !=null) {
            colList.push(Number(str))
        }
    }
    var sum = colList.reduce(function(a, b) {
        return a + b;
    }, 0);
    var total = parseFloat(sum).toFixed(2);
    if (total > 1 || total < 0) {
        parent.layer.msg("人力总计超出限定值，请仔细核对");
        return;
    }
    var cell = $(this).closest("td")

    var month = $("#month").val();
    var region = $("#region").val();
    var myMap = {};
    myMap.id = index;
    myMap.name = field;
    myMap.value = value;
    myMap.userId = userId;
    myMap.effectMonth = month;
    myMap.region = region;
    datacells[2].innerHTML = total;
    myMap.total = datacells[2].innerHTML;
    datacells[3].innerHTML = parseFloat(1-total).toFixed(2);
    myMap.noinput = datacells[3].innerHTML;

    $.ajax({
        type: "post",
        contentType : 'application/json;charset=utf-8',
        url: prefix +"/saveData",
        data : JSON.stringify(myMap),
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("保存成功", {})
                cell.text(myMap.value)
            } else {
                parent.layer.msg("保存失败", {});
            }
        },
        error: function () {
            alert('请求失败');
        }
    });
}
