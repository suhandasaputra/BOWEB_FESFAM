<%-- 
    Document   : monitoringtransaksi
    Author     : suhanda
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">



<%@page import="javax.swing.filechooser.FileNameExtensionFilter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.*"%>
<%@ page import="java.util.*"%>
<%@ page import = "org.apache.commons.io.FileUtils" %>
<%@include file= 'defaultextend.jsp' %>
<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%> 
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
        //masking nominal
//        $('#balDay').maskMoney();
//        $('#balMonth').maskMoney();

        //form submit
        $('#frmAddAgentyabes').submit(function () {
            var balDay = $('#balDay').maskMoney('unmasked')[0];
            $('#balDay').val(balDay);
            var balMonth = $('#balMonth').maskMoney('unmasked')[0];
            $('#balMonth').val(balMonth);
        }
        );
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
                    Manajemen Warung QR
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="update_corp.jsp">Update QR</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Update QR</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="QrServlet?action=updateqr" name="frmAddAgentyabes" id="frmAddAgentyabes">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID Merchant</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="merchantid" readonly="" value="<c:out value="${qr.merchantid}" />" required/>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Merchant Name</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="merchantname" readonly="" value="<c:out value="${qr.merchantname}" />" required/>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Biaya Admin</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="biayaadmin" value="<c:out value="${qr.biayaadmin}" />" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Profit PPOB</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="ppob_profit" step="1" value="<c:out value="${qr.ppob_profit}" />" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Profit Koperasi</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="ref_profit" value="<c:out value="${qr.ref_profit}" />" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Profit PPOB non Koperasi</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="ppob_profit_noncorp" value="<c:out value="${qr.ppob_profit_noncorp}" />" required/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-block">Save Update</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


