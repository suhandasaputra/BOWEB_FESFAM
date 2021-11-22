<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        $('#tabletransaction thead tr.filters th').each(function () {
            var title = $(this).text();
            if ($(this).hasClass("input-filter")) {
                $(this).html('<input name ="' + $.trim(title).replace(/ /g, '') + '" type="text" class = "form-control" placeholder="Search ' + $.trim(title) + '" />');
            } else if ($(this).hasClass("date-filter")) {
                $(this).html('<div class="input-prepend input-group"><span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span><input type="text" style="width: 200px" name="' + $.trim(title).replace(/ /g, '') + '"  placeholder="Search ' + $.trim(title) + '" class="form-control daterange"/></div>');
            }
        });
        var table = $('#tabletransaction').DataTable({
//            retrieve: true,
            dom: 'Bfrtip',
            "ajax": {
                "url": "/BOWEB_FESFAM/TransactionServlet?action=listproduk",
                "dataSrc": ""
            },
            "columns": [
                {data: "trancodeid",
                    render: function (data) {
                        var hidup = '<input type="checkbox" class="checkbox1">';
                                if (document.getElementsByClassName('checkbox1').checked == true) {
            console.log('true');

            } else {
                console.log('false');
            }   
                        return hidup + data;
             
                    }
                },
                {data: "trancodename"},
                {data: "available",
                    render: function (data) {
                        if (data == '1') {
                            return '<span class="label label-success">Aktif</span>';
                        } else if (data == '0') {
                            return '<span class="label label-warning">Non Aktif</span>';
                        }
                    }
                },
                {data: {trancodeid: "trancodeid", available: "available"},
                    render: function (data) {
                        if (mySession == 'administrator') {
                            if (data.available == 0) {
                                return '<form action="TransactionServlet" method="post">'
                                        + '<input type="hidden" name="action" value="active">'
                                        + '<input type="hidden" name="trancodeid" value="' + data.trancodeid + '">'
                                        + '<button type="submit" name="submit" value="submit" class="btn btn-success" padding-left="19px" padding-right="19px">Aktif</button></form>'
                            } else if (data.available == 1) {
                                return '<form action="TransactionServlet" method="post">'
                                        + '<input type="hidden" name="action" value="deactive">'
                                        + '<input type="hidden" name="trancodeid" value="' + data.trancodeid + '">'
                                        + '<button type="submit" name="submit" value="submit" class="btn btn-danger" padding-left="19px" padding-right="19px">Deaktif</button></form>'
                            }


                        } else if (mySession == 'admin') {
                            if (data.available == 0) {
                                return '<form action="TransactionServlet" method="post">'
                                        + '<input type="hidden" name="action" value="active">'
                                        + '<input type="hidden" name="trancodeid" value="' + data.trancodeid + '">'
                                        + '<button type="submit" name="submit" value="submit" class="btn btn-success" padding-left="19px" padding-right="19px">Aktif</button></form>'
                            } else if (data.available == 1) {
                                return '<form action="TransactionServlet" method="post">'
                                        + '<input type="hidden" name="action" value="deactive">'
                                        + '<input type="hidden" name="trancodeid" value="' + data.trancodeid + '">'
                                        + '<button type="submit" name="submit" value="submit" class="btn btn-danger" padding-left="19px" padding-right="19px">Deaktif</button></form>'
                            }
                        }
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
                                    title: "List transaction",
                                    exportOptions: {columns: ':visible:not(:last-child)'}, //last column has the action types detail/edit/delete
                                    footer: true
                                },
                                {
                                    extend: "csvHtml5",
                                    title: "List transaction",
                                    exportOptions: {columns: ':visible:not(:last-child)'},
                                    footer: true
                                },
                                {
                                    extend: "excelHtml5",
                                    title: "List transaction",
                                    exportOptions: {columns: ':visible:not(:last-child)'},
                                    footer: true
                                },
                                {
                                    extend: "pdfHtml5",
                                    title: "List transaction",
                                    exportOptions: {columns: ':visible:not(:last-child)'},
                                    footer: true
                                },
                                {
                                    extend: "print",
                                    exportOptions: {columns: ':visible:not(:last-child)'},
                                    footer: true
                                }

                            ]
                }
            ],
            responsive: true,
            "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
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
                $('#tabletransaction').fadeIn();
            },
            "footerCallback": function (tfoot, data, start, end, display) {
                var info = $('#tabletransaction').DataTable().page.info();
                $(tfoot).find('td').eq(0).html("Total Count: " + info.recordsDisplay);


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
        ($("#tabletransaction_filter input").removeClass("input-sm"));
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
                format: 'YYYY-MMM-DD'
            }
        });
        var startDate;
        var endDate;
        var dataIdx;  //current data column to work with
        $("#tabletransaction_wrapper thead").on("mousedown", "th", function (event) {
            var visIdx = $(this).parent().children().index($(this));
            dataIdx = table.column.index('fromVisible', visIdx);
        });
        // Function for converting a dd/mmm/yyyy date value into a numeric string for comparison (example 01-Dec-2010 becomes 20101201
        function parseDateValue(rawDate) {

            var d = moment(rawDate, "YYYY-MMM-DD").format('YYYY-MM-DD');
            var dateArray = d.split("-");
            var parsedDate = dateArray[2] + dateArray[1] + dateArray[0];
            return parsedDate;
        }
        //filter on daterange
        $(".daterange").on('apply.daterangepicker', function (ev, picker) {
            ev.preventDefault();
            //if blank date option was selected
            if ((picker.startDate.format('YYYY-MMM-DD') == "0001-Jan-01") && (picker.endDate.format('YYYY-MMM-DD')) == "0001-Jan-01") {
                $(this).val('Blank');


                val = "^$";

                table.column(dataIdx)
                        .search(val, true, false, true)
                        .draw();
            } else {
                //set field value
                $(this).val(picker.startDate.format('YYYY-MMM-DD') + ' to ' + picker.endDate.format('YYYY-MMM-DD'));
                //run date filter
                startDate = picker.startDate.format('YYYY-MMM-DD');
                endDate = picker.endDate.format('YYYY-MMM-DD');

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
                    Produk PPOB
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="list_transaction.jsp" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Daftar Produk</h3>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <table id="tabletransaction" class="table table-bordered table-striped" cellspacing="0" width="100%">
                                            <thead>                                            
                                                <tr>
                                                    <!--<th>Tipe Produk</th>-->
                                                    <th>ID Produk PPOB</th>
                                                    <th>Nama Produk</th>
                                                    <th>Status</th>
                                                    <th>Aksi</th>
                                                </tr>
                                                <tr class="filters">
                                                    <!--<th class="input-filter">Tipe Produk</th>-->
                                                    <th class="input-filter">ID Produk PPOB</th>
                                                    <th class="input-filter">Nama Produk</th>
                                                    <th class="input-filter">Status</th>
                                                    <th></th>
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
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


