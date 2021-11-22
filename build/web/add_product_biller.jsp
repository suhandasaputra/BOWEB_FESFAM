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

        $('#transactiontype').change(function (event) {
            var transactiontype = $("select#transactiontype").val();
            $.getJSON('BillerproductServlet?action=optionproductbiller', {
                transactiontype: transactiontype
            }, function (response) {
                var select = $('#trancodeid');
                select.find('option').remove();
                $.each(response, function (index, data) {
                    $('<option>').val(data.trancodeid).text(data.trancodename).appendTo(select);
                });
            });
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

        //masking nominal
//        $('#hargaBeli').maskMoney();
//        $('#feeBeli').maskMoney();

        //form submit
//        $('#frmAddBillerproduct').submit(function () {
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
                    Produk Biller
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="add_product_biller.jsp" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Tambah Produk Biller</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="BillerproductServlet?action=addproductbiller" name="frmAddBillerproduct" id="frmAddBillerproduct">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama Biller</label>
                                            <div class="col-sm-10">
                                                <select id="billercode" class="form-control select2" style="width: 100%;" name="billercode" required>
                                                </select>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Tipe Transaksi</label>
                                            <div class="col-sm-10">
                                                <select id="transactiontype" class="form-control select2" style="width: 100%;" name="transactiontype" required>
                                                    <option>Pilih Tipe Transaksi</option>
                                                    <option value="Bill Payment">Bill Payment</option>
                                                    <option value="Prepaid Reload">Prepaid Reload</option>
                                                    <option value="Other">Other</option>
                                                </select>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Pilih Produk</label>
                                            <div class="col-sm-10">
                                                <select id="trancodeid" class="form-control select2" style="width: 100%;" name="trancodeid" required>
                                                    <option>Pilih Produk</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Kode Produk QMA</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="tcbiller" placeholder="example : 123" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" title="hanya untuk prabayar, Set 0 untuk pascabayar">(*)Harga Beli dari QMA</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="hargabeli" placeholder="example : 9500" id="hargaBeli" required>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" title="isinya sama dengan Fee QMA yang di backofficeH2h, hanya untuk pascabayar, set 0 untuk prabayar">(*)Fee QMA</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="feebeli" placeholder="example : 500" id="feeBeli" required>
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
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


