<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%@include file='defaultextend.jsp' %>

<body class="hold-transition skin-blue-light sidebar-mini">
<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%> 
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%--<%@include file='sidebar_document_agent.jsp' %>--%>
                <%@include file='sidebar_left.jsp' %>

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    Agent Management
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <a href="document_create_agent.jsp" class="btn btn-default button-collection"
                       tabindex="0" aria-controls="transaction" href="#">
                        <span>REFRESH</span>
                    </a>
                </ol>
            </section >
            <div class="content">
                <div class="row">
                    <div class="content">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="box">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">Verifikasi User</h3>
                                    </div>
                                    <div class="box-body">
                                        <!--                                        <div class="col-md-12">-->
                                        <ul class="timeline">
                                            <!--  Menambah mapping agent -->
<!--                                            <li class="time-label">
                                                <span class="bg-yellow">
                                                    Menambah agent
                                                </span>
                                            </li>-->
                                            <li>
                                                <i class="fa fa-users bg-blue"></i>
                                                <div class="timeline-item">
                                                    <div class="timeline-body">
                                                        <a class="thumbnail fancybox" rel="ligthbox" href="image/add_agent.png">
                                                            <img class="img-responsive" alt="" src="image/add_agent.png" />
                                                        </a>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <i class="fa fa-circle bg-aqua"></i>
                                                <div class="timeline-item">
                                                    <h3 class="timeline-header">Field User Id</h3>
                                                    <div class="timeline-body">
                                                        field agent id adalah id untuk agent . 
                                                        Untuk pengisian agent id bisa berupa gabungan nama agent dengan nomor . 

                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                        <!--                                        </div>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.content-wrapper -->

        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
    <script src="js/jQuery-2.2.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/app.min.js"></script>
    <script src="fancy/jquery.fancybox.js"></script>
    <script src="js/custom.js"></script>
</body>
