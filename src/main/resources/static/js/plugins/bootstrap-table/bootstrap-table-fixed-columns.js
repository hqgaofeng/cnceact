/**
 * @author zhixin wen <wenzhixin2010@gmail.com>
 * @version: v1.0.1
 * @author Paulluo <paulluo0739@163.com>
 * @version: v1.0.2 完善冻结后排序、列宽和合并行
 */

(function ($) {
    'use strict';

    $.extend($.fn.bootstrapTable.defaults, {
        fixedColumns: false,
        fixedNumber: 1
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _init = BootstrapTable.prototype.init,
        _initHeader = BootstrapTable.prototype.initHeader,
        _initBody = BootstrapTable.prototype.initBody,
        _resetView = BootstrapTable.prototype.resetView,
        _onSort = BootstrapTable.prototype.onSort, // 排序时需增加对图标的同步更新
        _mergeCells = BootstrapTable.prototype.mergeCells;// 扩展合并表格方法

    BootstrapTable.prototype.init = function () {
        _init.apply(this, Array.prototype.slice.apply(arguments));

        var that = this;
        // 数据加载完成时需要对列宽度同步适配
        this.$el.on('load-success.bs.table', function () {
            that.onLoadSuccess();
        });
        //  显示/隐藏字段时需要对列宽度同步适配
        this.$el.on('column-switch.bs.table', function () {
            that.onColumnSwitch();
        });
    };

    BootstrapTable.prototype.initFixedColumns = function () {
        this.$fixedHeader = $([
            '<div class="fixed-table-header-columns">',
            '<table>',
            '<thead></thead>',
            '</table>',
            '</div>'].join(''));

        this.timeoutHeaderColumns_ = 0;
        this.$fixedHeader.find('table').attr('class', this.$el.attr('class'));
        this.$fixedHeaderColumns = this.$fixedHeader.find('thead');
        this.$tableHeader.before(this.$fixedHeader);

        this.$fixedBody = $([
            '<div class="fixed-table-body-columns">',
            '<table>',
            '<tbody></tbody>',
            '</table>',
            '</div>'].join(''));

        this.timeoutBodyColumns_ = 0;
        this.$fixedBody.find('table').attr('class', this.$el.attr('class'));
        this.$fixedBodyColumns = this.$fixedBody.find('tbody');
        this.$tableBody.before(this.$fixedBody);
    };

    BootstrapTable.prototype.initHeader = function () {
        _initHeader.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns) {
            return;
        }

        this.initFixedColumns();

        var that = this, $trs = this.$header.find('tr').clone(true);
        $trs.each(function () {
            $(this).find('th:gt(' + (that.options.fixedNumber-1) + ')').remove();
            //$(this).find('th:gt(' + that.options.fixedNumber + ')').remove();
        });
        this.$fixedHeaderColumns.html('').append($trs);

        var that = this;
        this.$el.on('load-success.bs.table', function(data) {
            that.onLoadSuccess(data);
        });
    };

    BootstrapTable.prototype.initBody = function () {
        _initBody.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns) {
            return;
        }

        var that = this,
            rowspan = 0;

        this.$fixedBodyColumns.html('');
        this.$body.find('> tr[data-index]').each(function () {
            var $tr = $(this).clone(),
                $tds = $tr.find('td');

            $tr.html('');
            var end = that.options.fixedNumber;
            if (rowspan > 0) {
                --end;
                --rowspan;
            }
            for (var i = 0; i < end; i++) {

                $tr.append($tds.eq(i).clone());
            }
            that.$fixedBodyColumns.append($tr);

            if ($tds.eq(0).attr('rowspan')){
                rowspan = $tds.eq(0).attr('rowspan') - 1;
            }
        });
    };

    BootstrapTable.prototype.resetView = function () {
        _resetView.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns) {
            return;
        }

        clearTimeout(this.timeoutHeaderColumns_);
        this.timeoutHeaderColumns_ = setTimeout($.proxy(this.fitHeaderColumns, this), this.$el.is(':hidden') ? 100 : 0);

        clearTimeout(this.timeoutBodyColumns_);
        this.timeoutBodyColumns_ = setTimeout($.proxy(this.fitBodyColumns, this), this.$el.is(':hidden') ? 100 : 0);
    };

    BootstrapTable.prototype.fitHeaderColumns = function () {
        var that = this,
            visibleFields = this.getVisibleFields(),
            headerWidth = 0;

        this.$body.find('tr:first-child:not(.no-records-found) > *').each(function (i) {
            var $this = $(this),
                index = i;

            if (i >= that.options.fixedNumber) {
                return false;
            }

            if (that.options.detailView && !that.options.cardView) {
                index = i - 1;
            }

            that.$fixedHeader.find('th[data-field="' + visibleFields[index] + '"]')
                .find('.fht-cell').width($this.innerWidth());
            headerWidth += $this.outerWidth();
        });
        this.$fixedHeader.width(headerWidth + 1).show();
    };

    BootstrapTable.prototype.fitBodyColumns = function () {
        var that = this,
            top = -(parseInt(this.$el.css('margin-top')) - 2),
            // the fixed height should reduce the scorll-x height
            height = this.$tableBody.height() - 19;

        if (!this.$body.find('> tr[data-index]').length) {
            this.$fixedBody.hide();
            return;
        }

        if (!this.options.height) {
            top = this.$fixedHeader.height();
            height = height - top;
        }

        this.$fixedBody.css({
            width: this.$fixedHeader.width(),
            height: height,
            top: top
        }).show();

        this.$body.find('> tr').each(function (i) {
            that.$fixedBody.find('tr:eq(' + i + ')').height($(this).height() - 1);
        });


        // events
        this.$tableBody.on('scroll', function () {
            that.$fixedBody.find('table').css('top', -$(this).scrollTop());
        });
        this.$body.find('> tr[data-index]').off('hover').hover(function () {
            var index = $(this).data('index');
            that.$fixedBody.find('tr[data-index="' + index + '"]').addClass('hover');
        }, function () {
            var index = $(this).data('index');
            that.$fixedBody.find('tr[data-index="' + index + '"]').removeClass('hover');
        });
        this.$fixedBody.find('tr[data-index]').off('hover').hover(function () {
            var index = $(this).data('index');
            that.$body.find('tr[data-index="' + index + '"]').addClass('hover');
        }, function () {
            var index = $(this).data('index');
            that.$body.find('> tr[data-index="' + index + '"]').removeClass('hover');
        });
    };


    BootstrapTable.prototype.mergeCells = function (options) {
        _mergeCells.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns) {
            return;
        }
        // copy base table implements，change $body to $fixBodyColumns
        var row = options.index,
            col = $.inArray(options.field, this.getVisibleFields()),
            rowspan = options.rowspan || 1,
            colspan = options.colspan || 1,
            i, j,
            $tr = this.$fixedBodyColumns.find('>tr'),
            $td;

        if (this.options.detailView && !this.options.cardView) {
            col += 1;
        }

        $td = $tr.eq(row).find('>td').eq(col);

        if (row < 0 || col < 0 || row >= this.data.length) {
            return;
        }

        for (i = row; i < row + rowspan; i++) {
            for (j = col; j < col + colspan; j++) {
                $tr.eq(i).find('>td').eq(j).hide();
            }
        }
        $td.attr('rowspan', rowspan).attr('colspan', colspan).show();
    };

    BootstrapTable.prototype.onSort = function () {

        _onSort.apply(this, Array.prototype.slice.apply(arguments));
        if (!this.options.fixedColumns) {
            return;
        }
        var name = this.options.sortName;
        var order = this.options.sortOrder;
        // 解决冻结列排序图标同步变化效果
        $(".fixed-table-header-columns").find("th[data-field]").find("div.sortable").removeClass("asc desc")
        $(".fixed-table-header-columns").find("th[data-field=" + name + "]").find("div.sortable").removeClass("asc desc").addClass(order);
    };

    // 显示/隐藏字段时需要对列宽度同步适配
    BootstrapTable.prototype.onColumnSwitch = function () {
        if (!this.options.fixedColumns) {
            return;
        }
        var columnBody = $(".fixed-table-body-columns");
        var columnHead = $(".fixed-table-header-columns");
        var len = $(".fixed-table-body-columns").length;
        for (var i = 0; i < len - 1; i++) {
            columnBody.eq(i).remove();
            columnHead.eq(i).remove();
        }
        fitFixColumnWidth();
    };

    // 数据加载完成时需要对列宽度同步适配
    BootstrapTable.prototype.onLoadSuccess = function () {
        if (!this.options.fixedColumns) {
            return;
        }
        fitFixColumnWidth();
    }

    // 对冻结列宽度适配
    function fitFixColumnWidth() {
        setTimeout(function() {
            var fixColumnTds = $(".fixed-table-body-columns tr:first-child td");
            var fixNum = fixColumnTds.length;
            var tableBody = $(".fixed-table-body tbody tr:first-child td");
            for (var i = 0; i < fixNum; i++) {
                fixColumnTds.eq(i).width(tableBody.eq(i).width());
            }
        }, 0);
    }

})(jQuery);