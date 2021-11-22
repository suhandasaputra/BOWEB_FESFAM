<%-- 
    Document   : monitoringtransaksi
    Author     : suhanda
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

        $.getJSON('BillerproductServlet?action=optionbiller', {}, function (data) {
            var options = '<option value="">Biller Name</option>';
            for (var i = 0; i < data.length; i++) {
                options += '<option value="' + data[i].billercode + '">' + data[i].billername + '</option>';
            }
            $("select#billercode").html(options);
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
                    Manajemen Produk
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="update_transaction.jsp">Update Produk</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Update Produk</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="TransactionServlet?action=updateproduk" name="frmUpdateTransaction">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID Produk PPOB</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="trancodeid" readonly="" value="<c:out value="${trancode.trancodeid}" />" required/>
                                            </div>
                                        </div>  
                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">Tipe Transaksi</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" style="width: 100%;" name="tctype">
                                                    <option value="280000" <c:if test="${trancode.tctype == 280000}"><c:out value="selected"/></c:if>>Bill Payment</option>
                                                    <option value="290000" <c:if test="${trancode.tctype == 290000}"><c:out value="selected"/></c:if>>Prepaid Reload</option>
                                                    </select>
                                                </div>
                                            </div>                                      
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Nama Produk</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" name="trancodename" value="<c:out value="${trancode.trancodename}" />" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Biller</label>
                                            <div class="col-sm-10">
                                                <select id="billercode" class="form-control select2" style="width: 100%;" name="billercode" required>
                                                </select>
                                            </div>
                                        </div>
                                            
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Provider</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" name="provider" value="<c:out value="${trancode.provider}" />" required/>
                                            </div>
                                        </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Category</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" name="category" value="<c:out value="${trancode.category}" />" required/>
                                            </div>
                                        </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Detail Produk</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" name="detailproduct" value="<c:out value="${trancode.detailproduct}" />" required/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">Update</button>
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


