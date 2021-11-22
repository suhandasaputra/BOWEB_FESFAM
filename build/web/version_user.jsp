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
        var mySession = '<%=session.getAttribute("userlevel")%>';
        var table = $('#tableagent').DataTable({
            dom: 'Bfrtip',
            "ajax": {
                "url": "/BOWEB_FESFAM/VersionServlet?action=ListVersionUser",
                "dataSrc": ""
            },
            "columns": [
                {data: "versioncode"},
                {data: "versionname"},
                {data: "versioncode",
                    render: function (data) {
                        if(mySession == 'administrator'){
                            return '<form action="VersionServlet" method="post">'
                                + '<input type="hidden" name="action" value="updateuser">'
                                + '<input type="hidden" name="versioncode" value="' + data + '">'
                                + '<button type="submit" name="submit" value="submit" class="btn btn-primary" padding-left="19px" padding-right="19px">Edit</button></form>'
                        }else if(mySession == 'admin'){
                            return '<form action="VersionServlet" method="post">'
                                + '<input type="hidden" name="action" value="updateuser">'
                                + '<input type="hidden" name="versioncode" value="' + data + '">'
                                + '<button type="submit" name="submit" value="submit" class="btn btn-primary" padding-left="19px" padding-right="19px">Edit</button></form>'
                        }else if(mySession == 'cs'){
                            return '-'
                        }
                    }
                }
            ],
            buttons: [
            ],
            responsive: true,
            orderCellsTop: true,
            scrollX: true,
            colReorder: true,
            language: {
                processing: "<img src='image/loading.gif'>"
            },
            processing: true
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
                    Manajemen Version
                    <small id="time"></small>
                </h1>
<!--                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="list_corp.jsp" class="btn btn-default button-collection"
                       tabindex="0" aria-controls="transaction" href="#">
                        <span>REFRESH</span>
                    </a>
                </ol>-->
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title">User Version</h3>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <table id="tableagent" class="table display table-striped table-bordered responsive-utilities jambo_table" cellspacing="0" width="100%">
                                            <thead>                                            
                                                <tr>
                                                    <th>version Code</th>
                                                    <th>version Name</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <td></td>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>                         
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


