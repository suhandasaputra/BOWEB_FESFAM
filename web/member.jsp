<%-- 
    Document   : member
    Author     : suhanda
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<%@include file='defaultextend.jsp'%>
<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%>
<script>
    $(document).ready(function () {
        $('#billermsg thead tr.filters th').each(function () {
            var title = $(this).text();
            if ($(this).hasClass("input-filter")) {
                $(this).html('<input name ="' + $.trim(title).replace(/ /g, '') + '" type="text" class = "form-control" placeholder="Search ' + $.trim(title) + '" />');
            } else if ($(this).hasClass("date-filter")) {
                $(this).html('<div class="input-prepend input-group"><span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span><input type="text" style="width: 200px" name="' + $.trim(title).replace(/ /g, '') + '"  placeholder="Search ' + $.trim(title) + '" class="form-control daterange"/></div>');
            }
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

    $(document).ready(function () {
        $.getJSON('UpgradeServlet?action=optionagent', {}, function (data) {
            var options = '<option value="">Agent Name</option>';
            for (var i = 0; i < data.length; i++) {
                options += '<option value="' + data[i].agent_id + '">' + data[i].agent_name + '</option>';
            }
            $("select#agent_id").html(options);
        });
    });

</script>

<body class="hold-transition skin-blue-light sidebar-mini">
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%@include file='sidebar_left.jsp' %>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    User Manajemen
                    <div align="right">
                        <medium id="time"></medium>
                    </div>
                </h1>
            </section >
            <div class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h1><b>Register User To Member</b></h1>                                
                                <form action="UpgradeServlet?action=GoToMember" method="POST">
                                    <fieldset>
                                        <div class="col-md-12">  
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Input No Hape User</label>
                                                <div class="col-sm-6">
                                                    <input class="form-control" name="agent_id_user" id="agent_id_user" required>
                                                </div>
                                            </div>
                                        </div>
                                        <br><br>
                                        <div class="col-md-12">  
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Pilih Leader</label>
                                                <div class="col-sm-6">
                                                    <select id="agent_id" class="form-control select2" style="width: 100%;" name="agent_id">
                                                    </select>
                                                </div>
                                            </div>
                                        </div> 
                                        <br><br>
                                        <div align="center" class="col-sm-10">
                                            <input class="btn btn-bitbucket" type="submit" value="SUBMIT"/>
                                        </div>
                                    </fieldset>
                                </form>
                                <br>
                                <table id="ppobtable" class="display table table-bordered table-striped" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th><Strong>USER ID</strong></th>
                                            <th><Strong>USER NAME</Strong></th>
                                            <th><Strong>REFERENCE ID</Strong></th>

                                        </tr>
                                    <td>${sessionScope.agent_id_user}</td>
                                    <td>${sessionScope.agent_name}</td>
                                    <td>${sessionScope.reference_id}</td>
                                    </thead>
                                </table>
                                <%
                                    session.removeAttribute("agent_id_user");
                                    session.removeAttribute("agent_name");
                                    session.removeAttribute("reference_id");
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
