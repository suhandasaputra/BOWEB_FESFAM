<%-- 
    Document   : monitoring_tempmsg
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
</script>

<body class="hold-transition skin-blue-light sidebar-mini">
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%@include file='sidebar_left.jsp' %>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    Balance Manajemen
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
                                <h1><b> Timeout Gagal</b></h1>
                                pengembalian Timeout ini dilakukan dengan syarat :<br> 
                                1. status di biller gagal<br>
                                2. RC di ppob 0068<br>
                                3. saldo user sudah terpotong<br>
                                <!--4. user belum mendapatkan poin-->
                                <br>
                                <br>
                                Setelah Admin melakukan proses Timeout_Gagal maka akan :<br>
                                1. RC pada menu report ppob akan berubah menjadi 0001<br>
                                2. saldo user akan dikembalikan sesuai nominal yang terpotong<br>
                                3. akan ada record reversal untuk transaksi tersebut pada menu report<br>
                                4. histori poin transaksi tersebut akan terhapus
                                5. Poin akan dikembalikan seperti semula
                                <br>Input Reference Number(RRN)
                                <form action="PengembalianDanaServlet?action=timeoutgagal" method="POST">
                                    <fieldset>
                                        <div class="form-group">
                                            <input class="content-header" name="rrn" type="text" placeholder="RRN" required=""><br>
                                        </div>
                                        <div align="left">
                                            <input class="btn btn-bitbucket" type="submit" value="SUBMIT"/>
                                            <input class="btn btn-bitbucket" type="reset" value="RESET"/>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--<%@include file='footer.jsp' %>--%>
        </div>
</body>

