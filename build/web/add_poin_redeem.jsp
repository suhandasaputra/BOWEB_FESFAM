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
    $.getJSON('HystoriPoinServlet?action=optiontrancodename', {}, function (data) {
        var options = '<option value="">-- pilih item --</option>';
        for (var i = 0; i < data.length; i++) {
            options += '<option value="' + data[i].trancodeid + '">' + data[i].itemname + '</option>';
        }
        $("select#productname").html(options);
    });

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
                    Poin Management
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="add_transaction.jsp" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Add Poin Redeem</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="HystoriPoinServlet?action=addpoinredeem" name="frmAddTransaction">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Redeem Kode</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="redeem_itemid" placeholder="example : 111111" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Item Name</label>
                                            <div class="col-sm-10">
                                                <select id="productname" class="form-control select2" style="width: 100%;" name="itemname" required>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Poin</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="price" placeholder="example : 111111" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Stock</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="stock" placeholder="example : 111111" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Start Date</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="start_date" type="date" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">End Date</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="end_date" type="date" required>
                                            </div>
                                        </div>
<!--                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Start Date</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="start_date" placeholder="example : 111111">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">End Date</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="end_date" placeholder="example : 111111">
                                            </div>
                                        </div>                                        -->
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">add</button>
                                </div>
                            </form>
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


