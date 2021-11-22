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
<style>
    #myImg:hover {opacity: 0.7;}

    /* The Modal (background) */
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        padding-top: 100px; /* Location of the box */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
    }

    /* Caption of Modal Image */
    #caption {
        margin: auto;
        display: block;
        width: 80%;
        max-width: 700px;
        text-align: center;
        color: #ccc;
        padding: 10px 0;
        height: 150px;
    }

    @-webkit-keyframes zoom {
        from {-webkit-transform:scale(0)} 
        to {-webkit-transform:scale(1)}
    }

    @keyframes zoom {
        from {transform:scale(0)} 
        to {transform:scale(1)}
    }

    /* The Close Button */
    .close {
        position: absolute;
        top: 50px;
        right: 35px;
        color: #f1f1f1;
        font-size: 40px;
        font-weight: bold;
        transition: 0.3s;
    }

    .close:hover,
    .close:focus {
        color: #bbb;
        text-decoration: none;
        cursor: pointer;
    }
    #reload {
        position: absolute;
        bottom: 30px;
        right: 550px;
        color: black;
        font-size: 40px;
        font-weight: bold;
        transition: 0.3s;
        opacity: 1;
        display: block;
        transition: .5s ease;
        backface-visibility: hidden;
    }
    .container:hover #reload {
        opacity: 0.3;
    }

    .north {
        margin: auto;
        display: block;
        width: 80%;
        max-width: 700px;
        transform:rotate(0deg);
        -ms-transform:rotate(0deg); /* IE 9 */
        -webkit-transform:rotate(0deg); /* Safari and Chrome */
    }

    .north, #caption {  
        -webkit-animation-name: zoom;
        -webkit-animation-duration: 0.6s;
        animation-name: zoom;
        animation-duration: 0.6s;
    }



    .west {
        margin: auto;
        display: block;
        width: 80%;
        max-width: 700px;

        transform:rotate(90deg);
        -ms-transform:rotate(90deg); /* IE 9 */
        -webkit-transform:rotate(90deg); /* Safari and Chrome */
    }
    .south {
        margin: auto;
        display: block;
        width: 80%;
        max-width: 700px;

        transform:rotate(180deg);
        -ms-transform:rotate(180deg); /* IE 9 */
        -webkit-transform:rotate(180deg); /* Safari and Chrome */

    }
    .east {
        margin: auto;
        display: block;
        width: 80%;
        max-width: 700px;

        transform:rotate(270deg);
        -ms-transform:rotate(270deg); /* IE 9 */
        -webkit-transform:rotate(270deg); /* Safari and Chrome */
    }
</style>

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
                    <a href="<%=request.getContextPath()%>/ListVerifikasi" class="btn btn-default button-collection">
                        <span>BACK</span>
                    </a>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-success">
                            <div class="box-header with-border">
                                <h3 class="box-title">Verifikasi User</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="VerifikasiServlet?action=verifikasi" name="frmAddAgentyabes" id="frmAddAgentyabes">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">User ID</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="agent_id" readonly="" value="<c:out value="${verifi.agent_id}" />" required/>
                                            </div>
                                        </div>
<!--                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Biodata dukcapil</label>
                                            <div class="col-sm-10">
                                                <textarea class="form-control" id="biodata_dukcapil" name="biodata_dukcapil" style="height: 200px; width: 470px; text-align: left" readonly="" required>
                                                    <%--<c:out value="${verifi.biodata_dukcapil}" />--%>
                                                </textarea>
                                            </div>
                                        </div>-->
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Gambar Ktp</label>
                                            <div class="col-sm-10">
                                                <img id="img_ktp" src="${verifi.img_ktp}">
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Gambar Wajah + KTP</label>
                                            <div class="col-sm-10">
                                                <img id="img_profile" src="${verifi.img_profile}">
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Gambar Selfi</label>
                                            <div class="col-sm-10">
                                                <img id="img_self" src="${verifi.img_self}">
                                            </div>
                                        </div>
                                        <div id="myModal" class="modal">
                                            <span class="close">&times;</span>
                                            <!--                                            <img class="modal-content" id="img01">
                                                                                        <div id="caption"></div>-->
                                            <img class="north" id="img01">
                                            <div class="container">
                                                <img id="reload" src="image/reload.png">
                                            </div>
                                            <div id="caption"></div>
                                        </div>
                                        <script>
                                            var modal = document.getElementById('myModal');
                                            var img1 = document.getElementById('img_ktp');
                                            var img2 = document.getElementById('img_profile');
                                            var img3 = document.getElementById('img_self');

                                            var modalImg = document.getElementById("img01");
                                            var captionText = document.getElementById("caption");
                                            img1.onclick = function () {
                                                modal.style.display = "block";
                                                modalImg.src = this.src;
                                                captionText.innerHTML = this.alt;
                                            }
                                            img2.onclick = function () {
                                                modal.style.display = "block";
                                                modalImg.src = this.src;
                                                captionText.innerHTML = this.alt;
                                            }
                                            img3.onclick = function () {
                                                modal.style.display = "block";
                                                modalImg.src = this.src;
                                                captionText.innerHTML = this.alt;
                                            }

                                            $('#reload').click(function () {
                                                var img = $('#img01');
                                                if (img.hasClass('north')) {
                                                    img.attr('class', 'west');
                                                } else if (img.hasClass('west')) {
                                                    img.attr('class', 'south');
                                                } else if (img.hasClass('south')) {
                                                    img.attr('class', 'east');
                                                } else if (img.hasClass('east')) {
                                                    img.attr('class', 'north');
                                                }
                                            });

                                            var span = document.getElementsByClassName("close")[0];
                                            span.onclick = function () {
                                                modal.style.display = "none";
                                            }
                                        </script>
                                    </div>
                                </div>
                            </form>
                            <div class="box-footer">
                                <form method="POST" action="VerifikasiServlet?action=verifikasi">
                                    <input type="hidden" name="agent_id" value="<c:out value="${verifi.agent_id}" />">
                                    <button type="submit" value="Submit" class="btn btn-bitbucket" style="width: 200px; margin-left: 40%;">Accept</button></form>
                            </div> 
                            <div class="box-footer">
                                <form method="POST" action="VerifikasiServlet?action=reject">
                                    <input type="hidden" name="agent_id" value="<c:out value="${verifi.agent_id}" />">
                                    <button type="submit" value="Submit" class="btn alert-danger" style="width: 200px; margin-left: 40%;">Reject</button>
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


