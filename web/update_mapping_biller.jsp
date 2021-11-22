<%-- 
    Document   : monitoringtransaksi
    Created on : May 26, 2016, 11:45:00 AM
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

        $('#transactiontype').change(function (event) {
            var transactiontype = $("select#transactiontype").val();
            $.get('BillerproductServlet?action=option', {
                transactionType: transactiontype
            }, function (response) {

                var select = $('#transactioncode');
                select.find('option').remove();
                $.each(response, function (index, data) {
                    $('<option>').val(data.trancodeid).text(data.trancodename).appendTo(select);
                });
            });
        });

        $.getJSON('MappingbillerServlet?action=optionbiller', {}, function (data) {
            var options = '<option value="">List Biller</option>';
            for (var i = 0; i < data.length; i++) {
                options += '<option value="' + data[i].billercode + '">' + data[i].billername + '</option>';
            }
            $("select#biller").html(options);
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
                    Biller Management
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="update_product_biller.jsp">Update Mapping Biller</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Update Mapping Biller</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="MappingbillerServlet" name="frmUpdateMapping">
                                <div class="box-body">
                                    <div class="col-md-12">                                       
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Transaction Code</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" value="<c:out value="${mappingbiller.trancodeid}" />" name="trancodeId">
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Transaction Name</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" value="<c:out value="${mappingbiller.trancodename}" />">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Transaction Type</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" value="<c:out value="${mappingbiller.tctype}" />">
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label for="Biller" class="col-sm-2 control-label">Biller</label>
                                            <div class="col-sm-10">
                                                <select id="biller" class="form-control select2" style="width: 100%;" name="billerCode">
                                                </select>
                                            </div>
                                        </div>                                                                               
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">update</button>
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


