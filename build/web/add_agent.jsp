<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<%@include file='defaultextend.jsp' %>



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

    });
</script> 
<body class="hold-transition skin-blue-light sidebar-mini">
    <div class="wrapper">
        <%@include file='header.jsp' %>

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
        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    Manajemen User
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="AddAgentKop" class="btn btn-default button-collection"
                       tabindex="0" aria-controls="transaction" href="#">
                        <span>Clear Data</span>
                    </a>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title">Tambah User</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="AgentKopServlet?action=addagentkop" name="frmAddAgentyabes">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID User</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="email" maxlength="100" name="agent_id" placeholder="example : user@mail.com" required>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama User</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="50" name="agent_name" placeholder="example : username" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nomor Telepon</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="number" maxlength="13" name="phonenumber" placeholder="example : phonenumber" required>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="button" value="Submit" class="btn btn-success pull-right" data-toggle="modal" data-target="#modal-default">Tambah</button>
                                </div>
<!--                                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-default">
                                    Launch Default Modal
                                </button>-->


                                <div class="modal fade" id="modal-default" style="display: none;">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">×</span></button>
                                                <h4 class="modal-title">Konfirmasi</h4>
                                            </div>
                                            <div class="modal-body">
                                                <p>Apakah anda yakin untuk menambahkan user?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Tidak</button>
                                                <button type="submit" class="btn btn-primary">YA</button>
                                            </div>
                                        </div>
                                        <!-- /.modal-content -->
                                    </div>
                                    <!-- /.modal-dialog -->
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


