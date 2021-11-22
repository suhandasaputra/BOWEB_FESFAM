<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<%@include file='defaultextend.jsp' %>
<%@page import="javax.swing.filechooser.FileNameExtensionFilter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.*"%>
<%@ page import="java.util.*"%>
<%@ page import = "org.apache.commons.io.FileUtils" %>
<script>
    $(document).ready(function () {
        
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
        <%@include file='header.jsp' %>
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
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    Manajemen Warung QR
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="AddAgent" class="btn btn-default button-collection"
                       tabindex="0" aria-controls="transaction" href="#">
                        <span>Clear Data</span>
                    </a>
                </ol>
            </section>
            <!-- Main content -->
            <section class="content">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">Tambah Qr</h3>
                    </div>
                    <form class="form-horizontal" method="POST" action="QrServlet?action=add_qr" name="frmAddAgentyabes">
                        <div class="box-body">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Merchant ID</label>
                                    <div class="col-sm-10">
                                        <input class="form-control" type="text" maxlength="20" name="merchantid" placeholder="example : W000001" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Nama Merchant</label>
                                    <div class="col-sm-10">
                                        <input class="form-control" type="text" maxlength="50" name="merchantname" placeholder="example : Warung suka cita" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Biaya Admin</label>
                                    <div class="col-sm-10">
                                        <input class="form-control" id="biayaadmin" name="biayaadmin" type="number" placeholder="example : 3000" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Profit PPOB</label>
                                    <div class="col-sm-10">
                                        <input class="form-control" name="ppob_profit" type="number" placeholder="example : 2000" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Profit Koperasi</label>
                                    <div class="col-sm-10">
                                        <input class="form-control" name="ref_profit" type="number" placeholder="example : 1000" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Profit PPOB Non Koperasi</label>
                                    <div class="col-sm-10">
                                        <input class="form-control" name="ppob_profit_noncorp" type="number" placeholder="example : 3000" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="box-footer">
                            <button type="submit" value="Submit" class="btn btn-success pull-right">Tambah</button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


