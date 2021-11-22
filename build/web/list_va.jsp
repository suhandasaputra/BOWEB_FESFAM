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
        $('#tableagent thead tr.filters th').each(function () {
            var title = $(this).text();
            if ($(this).hasClass("input-filter")) {
                $(this).html('<input name ="' + $.trim(title).replace(/ /g, '') + '" type="text" class = "form-control" placeholder="Search ' + $.trim(title) + '" />');
            } else if ($(this).hasClass("date-filter")) {
                $(this).html('<div class="input-prepend input-group">\n\
    <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>\n\
<input type="text" style="width: 200px" name="' + $.trim(title).replace(/ /g, '') + '"  placeholder="Search ' + $.trim(title) + '" class="form-control daterange"/></div>');
            }
        });
        var table = $('#tableagent').DataTable({
            dom: 'Bfrtip',
            "ajax": {
                "url": "/BOWEB_FESFAM/VaServlet",
                "dataSrc": ""
            },
            "columns": [
                {data: "agent_id"},
                {data: "phonenumber"},
                {data: "bank"},
                {data: "no_va"},
                {data: "aktivasi"},
                {data: "trx_id"},
                {data: "expired"}
            ],
            buttons: [
                {
                    extend: 'collection',
                    text: 'Export',
                    buttons:
                            [
                                {
                                    extend: "copyHtml5",
                                    title: "List Virtual Account",
                                    exportOptions: {columns: ':visible'}, //last column has the action types detail/edit/delete
                                    footer: true
                                },
                                {
                                    extend: "csvHtml5",
                                    title: "List Virtual Account",
                                    exportOptions: {columns: ':visible'},
                                    footer: true
                                },
                                {
                                    extend: "excelHtml5",
                                    title: "List Virtual Account",
                                    exportOptions: {columns: ':visible'},
                                    footer: true
                                },
                                {
                                    extend: "pdfHtml5",
                                    title: "List Virtual Account",
                                    exportOptions: {columns: ':visible'},
                                    footer: true
                                },
                                {
                                    extend: "print",
                                    exportOptions: {columns: ':visible'},
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
                $('#tableagent').fadeIn();
            },
            "order": [[4, "desc"]],
            "footerCallback": function (tfoot, data, start, end, display) {
                var info = $('#tableagent').DataTable().page.info();
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
        ($("#tableagent_filter input").removeClass("input-sm"));
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
        var dataIdx; //current data column to work with
        $("#tableagent_wrapper thead").on("mousedown", "th", function (event) {
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

        // Get the modal
        var modal = document.getElementById('myModal');

// Get the image and insert it inside the modal - use its "alt" text as a caption
        var img = document.getElementById('myImg');
        var modalImg = document.getElementById("img01");
        var captionText = document.getElementById("caption");
        img.onclick = function () {
            modal.style.display = "block";
            modalImg.src = this.src;
            captionText.innerHTML = this.alt;
        }

// Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
        }

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
                    Manajemen User
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="list_corp.jsp" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Daftar VA</h3>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <table id="tableagent" class="table display table-striped table-bordered responsive-utilities jambo_table" cellspacing="0" width="100%">
                                            <thead>                                            
                                                <tr>
                                                    <th>agent_id</th>
                                                    <th>nama</th>
                                                    <th>bank</th>
                                                    <th>no_va</th>
                                                    <th>tanggal aktivasi</th>
                                                    <th>trx id</th>
                                                    <th>expired</th>
                                                </tr>
                                                <tr class="filters">
                                                    <th class="input-filter">agent_id</th>
                                                    <th class="input-filter">nama</th>                                      
                                                    <th class="input-filter">bank</th>
                                                    <th class="input-filter">no_va</th>
                                                    <th class="date-filter">tanggal aktivasi</th>
                                                    <th class="input-filter">trx id</th>
                                                    <th class="date-filter">expired</th>

                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <td></td>
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


