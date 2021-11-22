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
<script>
    $(document).ready(function () {
        $.getJSON('CorporationServlet?action=option_cuid', {}, function (data) {
            var options = '<option value="">Nama Koperasi</option>';
            for (var i = 0; i < data.length; i++) {
                options += '<option value="' + data[i].cu_id + '">' + data[i].nama_koperasi + '</option>';
            }
            $("select#cu_id").html(options);
        });
        
        $('#cu_id').change(function (event) {
            var cu_id = $("select#cu_id").val();
            $.getJSON('MappingagentServlet?action=optionagent', {
                cu_id: cu_id
            }, function (response) {
                var select = $('#agent_id');
                select.find('option').remove();
                $.each(response, function (index, data) {
                    $('<option>').val(data.agent_id).text(data.agent_id).appendTo(select);
                });
            });
        });
        
//        $.getJSON('MappingagentServlet?action=optionagent', {}, function (data) {
//            var options = '<option value="">Agent LKD</option>';
//            for (var i = 0; i < data.length; i++) {
//                options += '<option value="' + data[i].agent_id + '">' + data[i].agent_id + '</option>';
//            }
//            $("select#agent_id").html(options);
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
        //Date picker
        $('#datepicker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });

        //masking nominal
        $('#amount').maskMoney();

        //form submit
        $('#frmAddtpAgent').submit(function () {
            var amount = $('#amount').maskMoney('unmasked')[0];
            $('#amount').val(amount);
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
                    Debet Management
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="add_tpsaldo_agent.jsp" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Withdrawal to LKD</h3>
                            </div> 
                            <form class="form-horizontal" method="POST" action="TopupagentServlet?action=lkd" name="frmAddtpAgent" id="frmAddtpAgent">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID Koperasi</label>
                                            <div class="col-sm-10">
                                                <select id="cu_id" class="form-control select2" style="width: 100%;" name="cu_id" required>
                                                </select>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID User LKD</label>
                                            <div class="col-sm-10">
                                                <select id="agent_id" class="form-control select2" style="width: 100%;" name="agentId" required>
                                                </select>
                                            </div>
                                        </div>                                       
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Amount</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="amount" id="amount" placeholder="example : 1000000" required>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Bank Name</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" readonly name="bankName" placeholder="example : Bank" value="qma" required>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Reference Number</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="acctNo" placeholder="example : 123456789012" required>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Transfer Date</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="transferDate" type="text" id="datepicker" placeholder="example : DD-MM-YYYY" required>
                                            </div>
                                        </div>
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
        </div>
        <!-- /.content-wrapper -->

        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>

