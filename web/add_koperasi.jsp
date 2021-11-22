<%-- 
    Document   : monitoringtransaksi
    Author     : suhanda
--%>

<%@page import="java.util.UUID"%>
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

//        $.getJSON('CorporationServlet?action=option_cuid', {}, function (data) {
//            var options = '<option value="">Nama Koperasi</option>';
//            for (var i = 0; i < data.length; i++) {
//                options += '<option value="' + data[i].cu_id + '">' + data[i].nama_koperasi + '</option>';
//            }
//            $("select#cu_id").html(options);
//        });

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
                    Management Koperasi
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="add_koperasi.jsp" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Add Koperasi</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="CorporationServlet?action=addKop" name="frmAddUser">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID Koperasi</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="cu_id" placeholder="example : 0001" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama Koperasi</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="nama_koperasi" placeholder="example : koperasi satu" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Password</label>
                                            <div class="col-sm-10">
                                                <%
                                                    String key = UUID.randomUUID().toString();
                                                    System.out.println("ini key : " + key);
                                                    key.substring(0, 8);
                                                    session.setAttribute("uniq_generate", key.substring(0, 8));
                                                %>
                                                <input class="form-control" readonly id="password_corp" type="text" name="password_corp" value="${uniq_generate}" required>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">add</button>
                                </div>
                            </form>
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


