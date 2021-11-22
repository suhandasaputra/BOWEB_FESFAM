<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            var month;
            switch (new Date().getMonth()) {
                case 0:
                    month = "January";
                    break;
                case 1:
                    month = "Februari";
                    break;
                case 2:
                    month = "March";
                    break;
                case 3:
                    month = "April";
                    break;
                case 4:
                    month = "May";
                    break;
                case 5:
                    month = "June";
                    break;
                case  6:
                    month = "July";
                    break;
                case  7:
                    month = "August";
                    break;
                case  8:
                    month = "September";
                    break;
                case  9:
                    month = "October";
                    break;
                case  10:
                    month = "November";
                    break;
                case  11:
                    month = "December";
            }
            var year = date.getFullYear();
            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            time = day + "-" + month + "-" + year + " " + hours + ":" + minutes + ":" + seconds;
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
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1><b>
                        AGENT MANAGEMENT
                        <div align="right">
                            <medium id="time"></medium>
                        </div>
                    </b>
                </h1>
            </section >
            <div class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h1><b>MASUKAN OTP</b></h1>
                                <form action="AgentServlet?action=konfirmasidorman" method="post">
                                    <fieldset>
                                        <div class="form-group">
                                            <input class="content-header" name="otp" type="text">
                                            <input class="content-header" name="phonenumber" type="text" value="${agentyabes.agent_phone}">
                                        </div>
                                        <div align="left">
                                            <input class="btn btn-bitbucket" type="submit" value="SUBMIT"/>
                                        </div>
                                    </fieldset>
                                </form>  
                                <br>
                                <table id="ppobtable" class="display table table-bordered table-striped" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th><Strong>USERID</strong></th>
                                            <th><Strong>USERNAME</Strong></th>
                                            <th><Strong>PHONE_NUMBER</Strong></th>
                                            <th><Strong>USER BALANCE</Strong></th>
                                        </tr>
                                    <td><c:out value="${agentyabes.agent_id}" /></td>
                                    <td><c:out value="${agentyabes.agent_name}" /></td>
                                    <td><c:out value="${agentyabes.agent_phone}" /></td>
                                    <td><c:out value="${agentyabes.balance}" /></td>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.content-wrapper -->
        <%@include file='footer.jsp' %>
        <%--<%@include file='sidebar_right.jsp' %>--%>
    </div>
</body>
