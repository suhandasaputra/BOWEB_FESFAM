<%-- 
    Document   : monitoringtransaksi
    Author     : suhanda
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">



<%@page import="javax.swing.filechooser.FileNameExtensionFilter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.*"%>
<%@ page import="java.util.*"%>
<%@ page import = "org.apache.commons.io.FileUtils" %>
<%@include file= 'defaultextend.jsp' %>
<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("userlevel").toString().equals(null)) {
        response.sendRedirect("index.jsp");
    }
%> 
<script>
    $(document).ready(function () {

        $.getJSON('CorporationServlet?action=option_cuid', {}, function (data) {
            var options = '<option value="<c:out value="${ads.spectator}" />"><c:out value="${ads.nama_koperasi}" /></option>';
            for (var i = 0; i < data.length; i++) {
                options += '<option value="' + data[i].cu_id + '">' + data[i].nama_koperasi + '</option>';
            }
            $("select#cu_id").html(options);
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
                    Manajemen Ads
                    <small id="time"></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="update_corp.jsp">Update Ads</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Update Ads</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="AdsServlet?action=updateAds" name="frmAddAgentyabes" id="frmAddAgentyabes">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ID Ads</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="ads_id" readonly="" value="<c:out value="${ads.ads_id}" />" required/>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Banner</label>
                                            <div class="col-sm-10">
                                                <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#modal-image" >Pilih Banner</button><br>
                                                <table id="myTable" class="table  table-striped table-bordered table-hover">
                                                    <tbody id="logo_bank" >
                                                    <input type="hidden" name="url" id="url" value="<c:out value="${ads.url}" />">
                                                    <td><img name="url" id="url" src="<c:out value="${ads.url}" />" required width="200px" height="100px"/></td>
                                                    
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Tanggal Mulai</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="datetime-local" name="start_date" step="1" value="<c:out value="${ads.start_date}" />" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Tanggal Akhir</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="datetime-local" name="end_date" step="1" value="<c:out value="${ads.end_date}" />" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Koperasi</label>
                                            <div class="col-sm-10">
                                                <select id="cu_id" class="form-control select2" style="width: 100%;" name="cu_id">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-block">Save Update</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="modal-image" data-modal-index="1">
                    <div class="modal-dialog" style="width:900px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Pilih Banner</h4>
                            </div>
                            <div class="modal-body">
                                <div class="row"> 
                                    <div class="row">
                                        <%
//                                            local
//                                                File file = new File("C:\\xampp\\htdocs\\backofficecuso_ppob\\image\\ads\\");
//                                                dev
//                                                File file = new File("/var/www/matajaristore/upload/image/ads/");
//                                                prod
                                                File file = new File("/var/www/html/image/ads/");
                                            String[] fileNames = file.list();
                                            File[] fileObjects = file.listFiles();
                                        %>
                                        <ul>
                                            <%                                                    for (int i = 0; i < fileObjects.length; i++) {
                                                    if (!fileObjects[i].isDirectory()) {
                                            %>
                                            <!--<li>-->
                                                <div class="pts-row"><div class="pts-col-sm-3 pts-col-xs-6 pts-text-center">
                                                        <div class="gallery_product col-lg-2  filter hdpe"><img height="100" width="100" src="image/ads/<%= fileNames[i]%>">
                                                            <br><br>
                                                            <div class="pts-row">
                                                                <input type="radio" name="path" id="paht"  value="<%= fileNames[i]%>" /><p><%= fileNames[i]%></p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            <!--</li>-->
                                            <%
                                                    }
                                                }
                                            %>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary" data-dismiss="modal" id="addToTable" >Submit</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div>
                <script type="text/javascript">
                    $('#addToTable').click(function () {
                                                 $("#logo_bank").empty();

                        $('table tbody').empty();
//                        
                        
                        var path = $('#paht:checked').val();
                        $('table tbody').append(' <input type="hidden"  name="url" id="url" value=' + path + '>');
                        //local
//                        $('table tbody').append(' <img height="100" width="100" src=http://127.0.0.1/BOWEB_FESFAM/image/ads/' + path + '>');
                        //dev
//                        $('table tbody').append(' <img height="100" width="100" src=http://117.53.46.3/image/ads/' + path + '>');
                        //prod
                        $('table tbody').append(' <img height="100" width="100" src=http://202.73.25.93/image/ads/' + path + '>');
                
    });
                </script>                      
            </section>
        </div>
        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


