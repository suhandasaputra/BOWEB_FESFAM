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
                    Biller Management
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="update_connection_biller.jsp">Update Connection Biller</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Update Connection Biller</h3>
                            </div> 
                            <form class="form-horizontal" method="POST" action="ConnectionbillerServlet?action=updateconnection" name="frmUpdateConnection">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Biller Code</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="billercode" readonly="" value="<c:out value="${cbiller.billercode}" />" required/>
                                            </div>
                                        </div>  
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Biller Name</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="billername" value="<c:out value="${cbiller.billername}" />" required/>
                                            </div>
                                        </div>                                        
                                                                              
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Package Name</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="packagename" value="<c:out value="${cbiller.packagename}" />" required/>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">URL Host</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="urlhost" value="<c:out value="${cbiller.urlhost}" />" required/>
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
        </div>
        <!-- /.content-wrapper -->

        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


