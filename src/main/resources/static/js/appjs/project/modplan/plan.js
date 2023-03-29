var prefix = "/module/plan";

$(function() {
    getModule();
    getProjectPlan();
    load();
    $(window).trigger("resize");
});

function getModule() {
    $.ajax({
        method : 'get', // 服务器数据的请求方式 get or post
        url :"/module/plan/getModule", // 服务器数据的加载地址
        async :false,
        datatype :'json',
        success :function(data) {
            $("#modName").empty();
            $("#modName").append('<option value="">请选择模块</option>');
            for (var i = 0; i < data.length; i++) {
                if (data[i] == "") continue;
                $("#modName").append('<option value='+data[i]+'>'+data[i]+'</option>');
            }
        }
    });
}

function getProjectPlan() {
    $.ajax({
        method : 'get',
        url :"/module/plan/getProjectPlan",
        async :false,
        datatype :'json',
        success :function(data) {
            $("#planName").empty();
            for (var i = 0; i < data.length; i++) {
                $("#planName").append('<option value='+data[i]+'>'+data[i]+'</option>');
            }
        }
    });
}

function load(pageIndex) {
    var modName = $("#modName").val();
    var planName = $("#planName").val();
    if(planName === ""){
        parent.layer.msg("请选择版本计划");
        return
    }
    $.ajax({
        type: "get",
        url: prefix +"/list",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:{
            modName: modName,
            planName: planName
        },
        success: function (json) {
            if (json.length==0) {
                parent.layer.msg("当前查询无数据");
            }
            // 加载接口数据
            $('#exampleTable').bootstrapTable('destroy').bootstrapTable({
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
                // 是否开启冻结列
                fixedColumns: true,
                // 需要冻结的列数
                fixedNumber: 4,
                // 行内编辑
                editable:true,
                columns: getDataColumns(json),
                showFooter: true
            });
            // 隐藏ID列
            $('#exampleTable').bootstrapTable('hideColumn', 'mId');
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
                title: "模块",
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
                title: "模块人力(人天)",
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
                title: "计划开始时间",
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
                title: "计划完成时间",
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
                if (field !== "id" || field !== "1" || field !== "2") {
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
    var modName = $("#modName").val();
    var planName = $("#planName").val();
    if (planName === "") {
        parent.layer.msg("请选择版本计划");
        return;
    }
    $.ajax({
        type: "get",
        url: prefix +"/list",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:{
            modName:modName,
            planName:planName
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
                // 是否开启冻结列
                fixedColumns: true,
                // 需要冻结的列数
                fixedNumber: 4,
                // 行内编辑
                editable:true,
                columns: getDataColumns(json),
                showFooter: true,
                onDblClickCell: function(field, value, row, $element) {
                    if (!isEditableField(field)) return;
                    $element.attr('contenteditable', true);
                    $element.blur(function() {
                        var index = $element.parent().data('index');
                        var mId = row.mId;
                        var tdValue = $element.html();
                        $(window).keydown( function(e) {
                            var key = window.event?e.keyCode:e.which;
                            if (key.toString() === "13") {
                                return false;
                            }
                        });
                        var result = checkCellInput(tdValue);
                        if (result == null) {
                            verifyData(index, field, tdValue, mId);
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
            $('#exampleTable').bootstrapTable('hideColumn', 'mId');
        },
        error: function () {
            // location.reload();
        }
    });
}

function checkCellInput(tdValue) {
    var total_len = tdValue.toString().length;
    if (total_len <= 0) return null;
    if (isNaN(tdValue)) return "请输入数字";
    if (tdValue < 0) return "请输入正数";
    var len = tdValue.toString().split('.').pop().length;
    if (len > 2) return "最多输入两位小数";
    return null;
}

function isEditableField(field) {
    if (field === "1" || field === "模块") return false;
    if (field === "2" || field === "模块人力(人天)") return false;
    if (field === "3" || field === "计划开始时间") return false;
    if (field === "4" || field === "计划完成时间") return false;
    return true;
}

// 行内编辑保存
function verifyData(index, field, value, mId) {
    $('#exampleTable').bootstrapTable('updateCell', {
        index: index,       // 行索引
        field: field,       // 列名
        value: value        // cell值
    });
    datacells = $("#exampleTable").find("tr[data-index="+index+"]").find("td");

    var colList = [];
    for (var i = 4; i < datacells.length; i++) {
        var str = datacells[i].innerText;
        if (str !=="" || str !=null) {
            colList.push(Number(str))
        }
    }
    var sum = eval(colList.join('+'));
    var modPower = parseFloat(sum).toFixed(2);
    var planName = $("#planName").val();
    var myMap = {};
    myMap.id = index;
    myMap.planName = planName;
    datacells[1].innerHTML = modPower;
    myMap.modPower = datacells[1].innerHTML;
    myMap.name = field;
    myMap.value = value;
    myMap.mId = mId;
    saveData(myMap);
}

function saveData(myMap) {
    $.ajax({
        type: "post",
        contentType : 'application/json;charset=utf-8',
        url: prefix +"/saveData",
        data : JSON.stringify(myMap),
        success: function (data) {
            if (data.code === 0) {
                parent.layer.msg("保存成功", {});
            } else {
                parent.layer.msg("保存失败", {});
            }
        },
        error: function () {
            alert('请求失败');
        }
    });
    var pageNumber = $('#exampleTable').bootstrapTable('getOptions').pageNumber;
    var pageSize = $('#exampleTable').bootstrapTable('getOptions').pageSize;
    queryData(pageNumber, pageSize);
}