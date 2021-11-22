<%-- 
    Document   : monitoringbiller
    Author     : suhanda
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        function datatable() {
            table = $("#ppobtable").dataTable({
                retrieve: true,
                "ajax": {
                    "url": "/BOWEB_FESFAM/agentbalanceServlet",
                    "dataSrc": ""
                },
                "columns": [
                    {"data": "agent_id"},
                    {"data": "agent_name"},
                    {"data": "phonenumber"},
//                    {"data": "no_ktp"},
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
                "order": [[1, "asc"]],
                "footerCallback": function (row, data, start, end, display) {
                var api = this.api(), data;
                var intVal = function (i) {
                    return typeof i === 'string' ?
                            i * 1 :
                            typeof i === 'number' ?
                            i : 0;
                            
                };
                total = api
                        .column(4)
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                // Total over this page
                pageTotal = api
                        .column(4, {page: 'current'})
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                $(api.column(0).footer()).html('Total User Balance : Rp ' + total + ' Total This Page Balance : Rp ' + pageTotal);
            }
            });
        }
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
        setInterval(datatable, 1000);
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
                    Balance Management
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <!--<li><a href="monitoringppob.jsp">Monitoring PPOB</a></li>-->
                    <a href="monitoringppob.jsp" class="btn btn-default button-collection"
                       tabindex="0" aria-controls="billermsg" href="#">
                        <span>REFRESH</span>
                    </a>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-info">
                            <div class="box-header with-border">
                                <h3 class="box-title">Info Balance User</h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <div class="col-md-12">
                                    <table id="ppobtable" class="display table table-bordered table-striped" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th><strong>User ID</strong></th>
                                                <th><strong>User Name</strong></th>  
                                                <th><strong>Phone Number</strong></th>
                                                <!--<th><strong>ID Card</strong></th>-->
                                                <th><strong>User Balance</strong></th>
                                                <th><strong>Poin Balance</strong></th>
                                                <th><strong>User Limit</strong></th>                                      

                                            </tr>
                                        </thead>
                                        <tfoot>
                                            <tr>
                                                <th></th>
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


