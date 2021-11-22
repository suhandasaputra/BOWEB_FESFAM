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
        $('#hargaJual').maskMoney();
        $('#feeJual').maskMoney();
        $('#sa_commission').maskMoney();
        //form submit
        $('#frmUpdateMapping').submit(function () {
            var hargaJual = $('#hargaJual').maskMoney('unmasked')[0];
            $('#hargaJual').val(hargaJual);
            var feeJual = $('#feeJual').maskMoney('unmasked')[0];
            $('#feeJual').val(feeJual);
            var sa_commission = $('#sa_commission').maskMoney('unmasked')[0];
            $('#sa_commission').val(sa_commission);
        });
    });
</script>
<body class="hold-transition skin-blue-light sidebar-mini">
    <%
        session.getMaxInactiveInterval();
        if (session.getAttribute("userlevel") == null) {
            response.sendRedirect("index.jsp");
        }
    %>
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
                    <li><a href="update_mapping_agent.jsp">Update Mapping User</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Update Produk Untuk User Payment</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="MappingagentServlet?action=updateproductuser" name="frmUpdateMapping" id="frmUpdateMapping">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Kode Produk(QMA & PPOB)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" readonly="" name="trancodeid" id="trancodeid" value="<c:out value="${am.trancodeid}" />">
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama Produk</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" readonly="" name="trancodename" id="trancodename" value="<c:out value="${am.trancodename}" />">
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" title="pascabayar = Total Biaya Admin">(*)Fee Jual/Biaya Admin(Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" required name="feejual" id="hargajual" value="<c:out value="${am.feejual}" />">
                                            </div>
                                        </div>                                      
                                        <div class="form-group">
                                            <label for="BuyingFee" class="col-sm-2 control-label">Fee PPOB(Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" required name="ppob_profit" id="ppob_profit" value="<c:out value="${am.ppob_profit}" />">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="Profit" class="col-sm-2 control-label">Fee Leader</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" required name="ref_profit" id="ref_profit" value="<c:out value="${am.ref_profit}" />">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="Profit" class="col-sm-2 control-label">Poin(Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" required name="poin" id="poin" value="<c:out value="${am.poin}" />">
                                            </div>
                                        </div>
                                        <br><br>
                                        <div class="form-group">
                                            <label for="Profit" class="col-sm-2 control-label" title="pascabayar = Total Biaya Admin">Fee Jual/Biaya Admin(Non Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" required name="feejual_noncorp" id="feejual_noncorp" value="<c:out value="${am.feejual_noncorp}" />">
                                            </div>
                                        </div>    
                                        <div class="form-group">
                                            <label for="Profit" class="col-sm-2 control-label">Fee PPOB (Non Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" required name="ppob_profit_noncorp" id="ppob_profit_noncorp" value="<c:out value="${am.ppob_profit_noncorp}" />">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="Profit" class="col-sm-2 control-label">Poin(Non Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" required name="poin_noncorp" id="poin_noncorp" value="<c:out value="${am.poin_noncorp}" />">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-10">
                                                <input class="form-control" type="hidden" required name="hargajual" id="hargajual" value="<c:out value="${am.hargajual}" />">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-10">
                                                <input class="form-control" type="hidden" required name="hargajual_noncorp" id="hargajual_noncorp" value="<c:out value="${am.hargajual_noncorp}" />">
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