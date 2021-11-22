<%-- 
    Document   : monitoringtransaksi
    Author     : suhanda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%@include file='defaultextend.jsp' %>
<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%>
<!--<script src="js/jquery.maskMoney.min.js"></script>-->

<script>
    $(document).ready(function () {
//        $('#transactiontype').click(function (event) {
        var transactiontype = 'Bill Payment';
        $.getJSON('BillerproductServlet?action=optionproductbiller', {
            transactiontype: transactiontype
        }, function (response) {
            var select = $('#trancodeid');
            select.find('option').remove();
            $.each(response, function (index, data) {
                $('<option>').val(data.trancodeid).text(data.trancodename).appendTo(select);
            });
        });
//        });



//        $.getJSON('MappingagentServlet?action=optionagent', {}, function (data) {
//            var options = '<option value="">Agent PPOB</option>';
//            for (var i = 0; i < data.length; i++) {
//                options += '<option value="' + data[i].agent_id + '">' + data[i].agentname + '</option>';
//            }
//            $("select#agent").html(options);
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

        //masking nominal
        $('#hargaJual').maskMoney();
        $('#feeJual').maskMoney();
        $('#sa_commission').maskMoney();

        //form submit
        $('#frmAddMappingagent').submit(function () {
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
                    Product User
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="add_mapping_agent.jsp" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Tambah Produk User Prepaid</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="MappingagentServlet?action=adduserprodukpayment" name="frmAddMappingagent" id="frmAddMappingagent">
                                <div class="box-body">
                                    <div class="col-md-12">                                       
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Pilih Produk</label>
                                            <div class="col-sm-10">
                                                <select id="trancodeid" class="form-control select2" style="width: 100%;" name="trancodeid" required>
                                                    <option>Pilih Produk</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" onfocus="">Fee Jual(Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="feejual" id="feejual" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">PPOB Profit(Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="ppob_profit" id="ppob_profit" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Fee Leader</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="ref_profit" id="ref_profit" required>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Poin(Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="poin" id="poin" required>
                                            </div>
                                        </div>
                                        <br><br>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" onfocus="">Fee Jual(Non Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="feejual_noncorp" id="feejual_noncorp" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">PPOB Profit(Non Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="ppob_profit_noncorp" id="ppob_profit_noncorp" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Poin(Non Member)</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" name="poin_noncorp" id="poin_noncorp" required>
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


