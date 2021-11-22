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
//        $('#hargaBeli').maskMoney();
//        $('#feeBeli').maskMoney();

        //form submit
//        $('#frmUpdateBillerproduct').submit(function () {
//            var hargaBeli = $('#hargaBeli').maskMoney('unmasked')[0];
//            $('#hargaBeli').val(hargaBeli);
//            var feeBeli = $('#feeBeli').maskMoney('unmasked')[0];
//            $('#feeBeli').val(feeBeli);
//        });
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
                    <li><a href="update_product_biller.jsp">Update Biller Product</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Update Biller Product</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="BillerproductServlet?action=updateproductbiller" name="frmUpdateBillerproduct" id="frmUpdateBillerproduct">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama Biller</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" readonly="" name="billername" id="billername" value="<c:out value="${pbiller.billername}"/>" required/>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Kode Produk QMA</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" readonly="" name="tcbiller" id="billercode" value="<c:out value="${pbiller.tcbiller}"/>" required/>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama Produk</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" readonly="" name="trancodename" id="trancodename" value="<c:out value="${pbiller.trancodename}"/>" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Tipe Transaksi</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" style="width: 100%;" name="tctype" id="transactiontype">
                                                    <option value="280000" <c:if test="${pbiller.tctype == 280000}"><c:out value="selected"/></c:if>>Bill Payment</option>
                                                    <option value="290000" <c:if test="${pbiller.tctype == 290000}"><c:out value="selected"/></c:if>>Prepaid Reload</option>
                                                    </select>
                                                    <!--<input class="form-control" readonly="" name="tctype" id="transactiontype" value="<c:out value="${pbiller.tctype}" />"/>-->
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" title="hanya untuk prabayar, Set 0 untuk pascabayar">(*)Harga Beli Dari QMA</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="hargabeli" id="hargaBeli" value="<c:out value="${pbiller.hargabeli}" />" required/>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" title="isinya sama dengan Fee QMA yang di backofficeH2h, hanya untuk pascabayar, set 0 untuk prabayar">(*)Fee QMA</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="feebeli" id="feeBeli" value="<c:out value="${pbiller.feebeli}" />" required/>
                                            </div>
                                        </div>
<!--                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" title="Poin Taip Transaksi">(*)Poin</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="poin" id="poin" value="<c:out value="${pbiller.poin}" />" required/>
                                            </div>
                                        </div> -->
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


