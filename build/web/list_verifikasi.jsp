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
        var mySession = '<%=session.getAttribute("userlevel")%>';
        function datatable() {
            table = $("#ppobtable").dataTable({
                retrieve: true,
                "ajax": {
                    "url": "/BOWEB_FESFAM/VerifikasiServlet?action=Listverifikasi",
                    "dataSrc": ""
                },
                "columns": [
                    {"data": "agent_id"},
                    {"data": "agent_name"},
                    {"data": "phonenumber"},
                    {"data": "no_ktp"},
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
                    },
                    {data: "verified",
                        render: function (data) {
                            if (data == '1') {
                                return '<span class="label label-success">Verified</span>'
                            } else if (data == '2') {
                                return '<span class="label label-warning">Unverified</span>'
                            } else if (data == '0') {
                                return '<span class="label label-warning">Unverified</span>'
                            }
                        }
                    },
//                    {"data": "img_ktp",
//                        render: function (data) {
//                            if (data == '-') {
//                                return '-'
//                            } else {
//                                return '<img src="data:image/png;base64, ' + data + '" height="100px" width="100px">'
//                            }
//                        }
//                    },
//                    {"data": "img_self",
//                        render: function (data) {
//                            if (data == '-') {
//                                return '-'
//                            } else {
//                                return '<img src="data:image/png;base64, ' + data + '" height="100px" width="100px">'
//                            }
//                        }
//                    },
//                    {"data": "img_profile",
//                        render: function (data) {
//                            if (data == '-') {
//                                return '-'
//                            } else {
//                                return '<img src="data:image/png;base64, ' + data + '" height="100px" width="100px">'
//                            }
//                        }
//                    },
                    {data: {agent_id: "agent_id", img_ktp: "img_ktp", img_self: "img_self", img_profile: "img_profile", verified: "verified"},
                        render: function (data) {
                            if (mySession == 'administrator') {
                                if (data.img_ktp != '-', data.img_self != '-', data.img_profile != '-' && data.verified == "2") {
                                    return'<form action="VerifikasiServlet" method="post">'
                                            + '<input type="hidden" name="action" value="update">'
                                            + '<input type="hidden" name="agent_id" value="' + data.agent_id + '">'
                                            + '<button type="submit" name="submit" value="submit" class="btn btn-primary" padding-left="19px" padding-right="19px">Ready To Approve</button></form>'
//                                    return'<form action="VerifikasiServlet" method="post">'
//                                            + '<input type="hidden" name="action" value="verifikasi">'
//                                            + '<input type="hidden" name="agent_id" value="' + data.agent_id + '">'
//                                            + '<button type="submit" name="submit" value="submit" class="btn btn-primary" padding-left="19px" padding-right="19px">Ready to Approve</button></form>'
                                } else if (data.img_ktp != '-', data.img_self != '-', data.img_profile != '-' && data.verified == "1") {
                                    return '<form action="VerifikasiServlet" method="post">'
                                            + '<input type="hidden" name="action" value="approve">'
                                            + '<input type="hidden" name="agent_id" value="' + data.agent_id + '">'
                                            + '<button type="submit" name="submit" value="submit" class="btn btn-success" padding-left="19px" padding-right="19px">Approved</button></form>'
//                                    return '<a href=# class="btn btn-success">Approved</a>'
                                } else {
                                    return '<a href=# class="btn btn-danger">Not Ready Approve</a>'
                                }
                            } else if (mySession == 'admin') {
                                if (data.img_ktp != '-', data.img_self != '-', data.img_profile != '-' && data.verified == "2") {
//                                    return'<form action="VerifikasiServlet" method="post">'
//                                            + '<input type="hidden" name="action" value="verifikasi">'
//                                            + '<input type="hidden" name="agent_id" value="' + data.agent_id + '">'
//                                            + '<button type="submit" name="submit" value="submit" class="btn btn-primary" padding-left="19px" padding-right="19px">Ready to Approve</button></form>'
                                    return'<form action="VerifikasiServlet" method="post">'
                                            + '<input type="hidden" name="action" value="update">'
                                            + '<input type="hidden" name="agent_id" value="' + data.agent_id + '">'
                                            + '<button type="submit" name="submit" value="submit" class="btn btn-primary" padding-left="19px" padding-right="19px">Ready To Approve</button></form>'
//                                                        
                                } else if (data.img_ktp != '-', data.img_self != '-', data.img_profile != '-' && data.verified == "1") {
                                    return '<a href=# class="btn btn-success">Approved</a>'
                                } else {
                                    return '<a href=# class="btn btn-danger">Not Ready Approve</a>'
                                }
                            } else if (mySession == 'cs') {
                                return '-'
                            }
                        }
                    }
                ],
                "order": [[1, "asc"]],
                responsive: true,
                "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                orderCellsTop: true,
                scrollX: true,
                colReorder: true,
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
                    Manajemen User
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
                                <h3 class="box-title">Daftar Verifikasi User</h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <div class="col-md-12">
                                    <table id="ppobtable" class="display table table-bordered table-striped" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th><strong>Nomor Telepon</strong></th>
                                                <th><strong>Nama User</strong></th>  
                                                <th><strong>Email</strong></th>
                                                <th><strong>KTP</strong></th>
                                                <th><strong>Saldo User</strong></th>                                      
                                                <th><strong>Limit User</strong></th> 
                                                <th><strong>Status Verifikasi</strong></th>                                      
                                                <!--                                                <th><strong>Foto KTP</strong></th>
                                                                                                <th><strong>Foto Selfi</strong></th>
                                                                                                <th><strong>Foto Profil</strong></th>-->
                                                <th>Aksi</th>
                                        </thead>
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


