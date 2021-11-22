<%-- 
    Document   : monitoringtransaksi
    Created on : May 26, 2016, 11:45:00 AM
    Author     : suhanda
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%@include file= 'defaultextend.jsp' %>
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
//        $('#balDay').maskMoney();
//        $('#balMonth').maskMoney();

        //form submit
        $('#frmAddAgentyabes').submit(function () {
            var balDay = $('#balDay').maskMoney('unmasked')[0];
            $('#balDay').val(balDay);
            var balMonth = $('#balMonth').maskMoney('unmasked')[0];
            $('#balMonth').val(balMonth);
        }
        );
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
                    Manajemen User
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="update_agent.jsp">Edit User</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Edit User</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="AgentServlet?action=updateagent" name="frmAddAgentyabes" id="frmAddAgentyabes">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nomor Telepon</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="agent_id" readonly="" value="<c:out value="${agentyabes.agent_id}" />" required/>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Nama User</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="agent_name" value="<c:out value="${agentyabes.agent_name}" />" required/>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID User</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" name="email" value="<c:out value="${agentyabes.email}" />" required/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <div class="modal modal-info fade" id="modal-info" style="display: none;">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">×</span></button>
                                                    <h4 class="modal-title">Konfirmasi</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Apakah anda yakin melakukan perubahan ini...?</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Tidak</button>
                                                    <button type="submit" class="btn btn-outline">YA</button>
                                                </div>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>

                                    <!--<button type="submit" value="Submit" class="btn btn-block">Simpan Perubahan</button>-->
                                    <button type="button" value="Submit" class="btn btn-block"  data-toggle="modal" data-target="#modal-info">Simpan Perubahan</button>
                                    <!--                                    <button type="button" class="btn btn-block" data-toggle="modal" data-target="#modal-success">
                                                                            Launch Default Modal
                                                                        </button>-->
                                </div>


                            </form>
                            <!--                            <div class="box-footer">
                                                            <form method="POST" action="AgentServlet?action=resetpassword">
                                                                <input type="hidden" name="agent_id" value="<c:out value="${agentyabes.agent_id}" />">
                                                                <button type="submit" value="Submit" class="btn btn-block ">Reset Password</button></form>
                                                        </div> -->
                            <div class="box-footer">
                                <form method="POST" action="AgentServlet?action=resetpin">
                                    <div class="modal modal-danger fade" id="modal-danger" style="display: none;">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">×</span></button>
                                                    <h4 class="modal-title">Konfirmasi</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Apakah anda yakin akan melakukan reset pin...?</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Tidak</button>
                                                    <button type="submit" class="btn btn-outline">YA</button>
                                                </div>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>
                                    <input type="hidden" name="agent_id" value="<c:out value="${agentyabes.agent_id}" />">
                                    <!--<button type="button" value="Submit" class="btn btn-block"  data-toggle="modal" data-target="#modal-info">Simpan Perubahan</button>-->
                                    <button type="button" value="Submit" class="btn btn-block" data-toggle="modal" data-target="#modal-danger">Reset Pin</button>
                                </form>
                            </div>
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


