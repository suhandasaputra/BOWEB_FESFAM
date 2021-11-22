<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    Produk PPOB
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
                                <h3 class="box-title">Tambah Produk</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="TransactionServlet?action=addproduk" name="frmAddTransaction">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">Tipe Produk</label>
                                            <div class="col-sm-10">
                                                <select class="form-control select2" style="width: 100%;" name="tctype">
                                                    <option value="280000">Bill Payment</option>
                                                    <option value="290000">Prepaid Reload</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID Produk PPOB</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="trancodeid" placeholder="example : 111111" required>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama Produk</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="trancodename" placeholder="example : Pulsa xl 10k" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama Biller</label>
                                            <div class="col-sm-10">
                                                <select id="billercode" class="form-control select2" style="width: 100%;" name="billercode" required>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Provider</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="provider" placeholder="example : telkomsel" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Kategori</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="category" placeholder="example : PULSA" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Detail Produk</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="detailproduct" placeholder="example : paket data 10GB, (5GB 4G, 3GB 3G, 2GB videoMax)" required>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">Tambah</button>
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


