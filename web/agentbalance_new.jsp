<%-- 
    Document   : monitoringtransaksi
    Author     : suhanda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%@include file='defaultextend.jsp' %>
<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%>
<script>
    $(document).ready(function () {
        var mySession = '<%=session.getAttribute("userlevel")%>';
        $('#transaction thead tr.filters th').each(function () {
            var title = $(this).text();
            if ($(this).hasClass("input-filter")) {
                $(this).html('<input name ="' + $.trim(title).replace(/ /g, '') + '" type="text" class = "form-control" placeholder="Search ' + $.trim(title) + '" />');
            } else if ($(this).hasClass("date-filter")) {
                $(this).html('<div class="input-prepend input-group"><span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span><input type="text" style="width: 200px" name="' + $.trim(title).replace(/ /g, '') + '"  placeholder="Search ' + $.trim(title) + '" class="form-control daterange"/></div>');
            }
        });
        var table = $('#transaction').DataTable({
            dom: '<"top"Blf>rt<"bottom"ip><"a">',
            "ajax": {
                "url": "/BOWEB_FESFAM/agentbalanceServlet",
                "dataSrc": ""
            },
            "columns": [
                {"data": "agent_id"},
                    {"data": "agent_name"},
                    {"data": "phonenumber"},
                    {"data": "app_id"},
                    {"data": "curr_bal",
                        render: function (data) {
                            var rx = /(\d+)(\d{3})/;
                            return String(data).replace(/^\d+/, function (w) {
                                while (rx.test(w)) {
                                    w = w.replace(rx, '$1,$2');
                                }
                                return w;
                            });
                        }
                    },
                    
                    {"data": "poin_bal",
                        render: function (data) {
                            var rx = /(\d+)(\d{3})/;
                            return String(data).replace(/^\d+/, function (w) {
                                while (rx.test(w)) {
                                    w = w.replace(rx, '$1,$2');
                                }
                                return w;
                            });
                        }
                    },
                    
                    {"data": "max_curr_bal",
                        render: function (data) {
                            var rx = /(\d+)(\d{3})/;
                            return String(data).replace(/^\d+/, function (w) {
                                while (rx.test(w)) {
                                    w = w.replace(rx, '$1,$2');
                                }
                                return w;
                            });
                        }
                    }
            ],
            buttons: [
                {
                    extend: 'collection',
                    text: 'Export',
                    buttons:
                            [
                                {
                                    extend: "copyHtml5",
                                    title: "List saldo user",
                                    exportOptions: {columns: ':visible:not()'}, //last column has the action types detail/edit/delete
                                    footer: true
                                },
                                {
                                    extend: "csvHtml5",
                                    title: "List saldo user",
                                    exportOptions: {columns: ':visible:not()'},
                                    footer: true
                                },
                                {
                                    extend: "excelHtml5",
                                    title: "List saldo user",
                                    exportOptions: {columns: ':visible:not()'},
                                    footer: true
                                },
                                {
                                    extend: "pdfHtml5",
                                    title: "List saldo user",
                                    exportOptions: {columns: ':visible:not()'},
                                    footer: true
                                },
                                {
                                    extend: "print",
                                    exportOptions: {columns: ':visible:not()'},
                                    footer: true
                                }

                            ]
                }
            ],
            responsive: true,
            "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
            orderCellsTop: true,
            scrollX: true,
            colReorder: true,
            language: {
                searchPlaceholder: 'Search all columns',
                lengthMenu: '<div class="input-group"><span class="input-group-addon"><span class="glyphicon glyphicon-menu-hamburger"></span></span>_MENU_</div>',
                processing: "<img src='image/loading.gif'>"
            },
            processing: true,
            "initComplete": function (settings, json) {
                //  $("#mytable_processing").css("visibility", "hidden");
                $('#transaction').fadeIn();
            },
            "order": [[3, "desc"]],
            "footerCallback": function (tfoot, data, start, end, display) {
                var info = $('#transaction').DataTable().page.info();
                $(tfoot).find('td').eq(0).html("Total Count: " + info.recordsDisplay);

                var api = this.api(), data;
                var intVal = function (i) {
                    return typeof i === 'string' ?
                            i * 1 :
                            typeof i === 'number' ?
                            i : 0;
                };
                
                total = api
                        .column(3)
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                // Total over this page
                pageTotal = api
                        .column(3, {page: 'current'})
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                $(api.column(0).footer()).html('Total User Balance : Rp ' + total + ' Total This Page Balance : Rp ' + pageTotal);
                
            }
        });
        new $.fn.dataTable.Buttons(table, {
            buttons: [
                {
                    extend: 'colvis',
                    text: 'Column visibility'

                },
            ]
        });
        //add button to top
        table.buttons(0, null).container().prependTo(
                table.table().container()
                );
        //remove class from search filter
        ($("#transaction_filter input").removeClass("input-sm"));
        //instantiate datepicker and choose your format of the dates
        $('.daterange').daterangepicker({
            ranges: {
                "Today": [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '7 last days': [moment().subtract(6, 'days'), moment()],
                '30 last days': [moment().subtract(29, 'days'), moment()],
                'This month': [moment().startOf('month'), moment().endOf('month')],
                'Last month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                'Blank date': [moment("0001-01-01"), moment("0001-01-01")]
            }
            ,
            autoUpdateInput: false,
            opens: "left",
            locale: {
                cancelLabel: 'Clear',
                format: 'DD-MMM-YYYY'
            }
        });
        var startDate;
        var endDate;
        var dataIdx;        //current data column to work with
        $("#transaction_wrapper thead").on("mousedown", "th", function (event) {
            var visIdx = $(this).parent().children().index($(this));
            dataIdx = table.column.index('fromVisible', visIdx);
        });
        // Function for converting a dd/mmm/yyyy date value into a numeric string for comparison (example 01-Dec-2010 becomes 20101201
        function parseDateValue(rawDate) {

            var d = moment(rawDate, "DD-MMM-YYYY").format('DD-MM-YYYY');
            var dateArray = d.split("-");
            var parsedDate = dateArray[2] + dateArray[1] + dateArray[0];
            return parsedDate;
        }
        //filter on daterange
        $(".daterange").on('apply.daterangepicker', function (ev, picker) {
            ev.preventDefault();
            //if blank date option was selected
            if ((picker.startDate.format('DD-MMM-YYYY') == "01-Jan-0001") && (picker.endDate.format('DD-MMM-YYYY')) == "01-Jan-0001") {
                $(this).val('Blank');


                val = "^$";

                table.column(dataIdx)
                        .search(val, true, false, true)
                        .draw();

            } else {
                //set field value
                $(this).val(picker.startDate.format('DD-MMM-YYYY') + ' to ' + picker.endDate.format('DD-MMM-YYYY'));

                //run date filter
                startDate = picker.startDate.format('DD-MMM-YYYY');
                endDate = picker.endDate.format('DD-MMM-YYYY');

                var dateStart = parseDateValue(startDate);
                var dateEnd = parseDateValue(endDate);

                var filteredData = table
                        .column(dataIdx)
                        .data()
                        .filter(function (value, index) {

                            var evalDate = value === "" ? 0 : parseDateValue(value);
                            if ((isNaN(dateStart) && isNaN(dateEnd)) || (evalDate >= dateStart && evalDate <= dateEnd)) {

                                return true;
                            }
                            return false;
                        });


                var val = "";
                for (var count = 0; count < filteredData.length; count++) {

                    val += filteredData[count] + "|";
                }

                val = val.slice(0, -1);


                table.column(dataIdx)
                        .search(val ? "^" + val + "$" : "^" + "-" + "$", true, false, true)
                        .draw();
            }
        });
        $(".daterange").on('cancel.daterangepicker', function (ev, picker) {
            ev.preventDefault();
            $(this).val('');
            table.column(dataIdx)
                    .search("")
                    .draw();

        });
        //hide unnecessary columns
        var column = table.columns($('.HideColumn'));
        // Toggle the visibility
        column.visible(!column.visible());
        // Apply the search
        $.each($('.input-filter', table.table().header()), function () {
            var column = table.column($(this).index());
            //onsole.log(column);
            $('input', this).on('keyup change', function () {
                if (column.search() !== this.value) {
                    column
                            .search(this.value)
                            .draw();
                }
            });
        });

        function Timer() {
            var date = new Date();
            var day = date.getDate();
            var month = date.getMonth() + 1;
            var year = date.getFullYear();
            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            time = day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds;
            var lblTime = document.getElementById("time");
            lblTime.innerHTML = time;
        }
        setInterval(Timer, 1000);
    });
</script>
<body class="hold-transition skin-blue-light sidebar-mini">
    <div class="wrapper">
        <%
            if (session.getAttribute("userlevel").toString().equals("administrator")) {
        %>
        <%@include file='sidebar_left.jsp' %>
        <%
        } else if (session.getAttribute("userlevel").toString().equals("admin")) {
        %>
        <%@include file='sidebar_left_admin.jsp' %>
        <%
        } else if (session.getAttribute("userlevel").toString().equals("cs")) {
        %>
        <%@include file='sidebar_left_cs.jsp' %>
        <%
            }
        %>
        <%@include file='header.jsp' %>

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    Top Up User Management
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="<%=request.getContextPath()%>/Home"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="<%=request.getContextPath()%>/AgentBalance" class="btn btn-default button-collection"
                       tabindex="0" aria-controls="transaction" href="#">
                        <span>REFRESH</span>
                    </a>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title">Info Balance User</h3>
                                <div class="col-md-12">
                                    <table id="transaction" class="table display table-striped table-bordered responsive-utilities jambo_table" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th><strong>User ID</strong></th>
                                                <th><strong>User Name</strong></th>  
                                                <th><strong>Email</strong></th>
                                                <th><strong>Level</strong></th>
                                                <th><strong>User Balance</strong></th>
                                                <th><strong>Poin Balance</strong></th>
                                                <th><strong>User Limit</strong></th>                                      
                                            </tr>
                                            <tr class="filters">
                                                <th class="input-filter">User ID</th>
                                                <th class="input-filter">User Name</th>
                                                <th class="input-filter">Email</th>
                                                <th class="input-filter">Level</th>
                                                <th class="input-filter">User Balance</th>
                                                <th class="input-filter">Poin Balance</th>
                                                <th class="input-filter">User Limit</th>
                                            </tr>
                                        </thead>
                                        <tfoot>
                                            <tr>
                                                <td ></td>
                                            </tr>

                                        </tfoot>
                                    </table>
                                </div>
                            </div>                         
                        </div>
                    </div>
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


