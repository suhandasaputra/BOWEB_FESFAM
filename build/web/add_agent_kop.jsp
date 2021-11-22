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
        $.getJSON('CorporationServlet?action=option_cuid', {}, function (data) {
            var options = '<option value="">Nama Koperasi</option>';
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

        <%--<%@include file='sidebar_add_agent.jsp' %>--%>
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
                    <a href="AddAgent" class="btn btn-default button-collection"
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
                                <h3 class="box-title">Tambah User Badan Koperasi</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="AgentKopServlet?action=addagent" name="frmAddAgentyabes">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Cu id</label>
                                            <div class="col-sm-10">
                                                <select id="cu_id" class="form-control select2" style="width: 100%;" name="cu_id" required>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">No Anggota</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" maxlength="25" name="no_ba" placeholder="example : 0.01.00001" required>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Email</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="email" maxlength="40" name="agent_id" placeholder="example : user@mail.com" required>
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
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Scan Form Pendftaran</label>
                                            <div class="col-sm-10">
                                                <input type="file" id="img" name="img" onchange="readURL(this);"/>
                                                <img id="blah" src="#" alt="your image" />
                                                <script>
                                                    function readURL(input) {
                                                        if (input.files && input.files[0]) {
                                                            var reader = new FileReader();
                                                            reader.onload = function (e) {
                                                                $('#blah')
                                                                        .attr('src', e.target.result)
                                                                        .width(250)
                                                                        .height(200);
                                                            };
                                                            reader.readAsDataURL(input.files[0]);
                                                        }
                                                    }
                                                </script>
                                                <br/>
                                                <input class="form-control" type="text" readonly name="img_ktp" id="img_ktp"/>
                                                <script>
                                                    if (window.File && window.FileReader && window.FileList && window.Blob) {
                                                        document.getElementById('img').addEventListener('change', handleFileSelect, false);
                                                    } else {
                                                        alert('The File APIs are not fully supported in this browser.');
                                                    }
                                                    function handleFileSelect(evt) {
                                                        var f = evt.target.files[0]; // FileList object
                                                        var reader = new FileReader();
                                                        // Closure to capture the file information.
                                                        reader.onload = (function (theFile) {
                                                            return function (e) {
                                                                var binaryData = e.target.result;
                                                                //Converting Binary Data to base 64
                                                                var base64String = window.btoa(binaryData);
                                                                //showing file converted to base64
                                                                document.getElementById('img_ktp').value = base64String;
                                                                alert('sukses convert gambar');
                                                            };
                                                        })(f);
                                                        // Read in the image file as a data URL.
                                                        reader.readAsBinaryString(f);
                                                    }
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Logo Koperasi</label>
                                            <div class="col-sm-10">
                                                <input type="file" id="img_logo" name="img_logo" onchange="readURL1(this);"/>
                                                <img id="blah1" src="#" alt="your image" />
                                                <script>
                                                    function readURL1(input) {
                                                        if (input.files && input.files[0]) {
                                                            var reader = new FileReader();
                                                            reader.onload = function (e) {
                                                                $('#blah1')
                                                                        .attr('src', e.target.result)
                                                                        .width(250)
                                                                        .height(200);
                                                            };
                                                            reader.readAsDataURL(input.files[0]);
                                                        }
                                                    }
                                                </script>
                                                <br/>
                                                <input class="form-control" type="text" readonly name="img_profile" id="img_profile"/>
                                                <script>
                                                    if (window.File && window.FileReader && window.FileList && window.Blob) {
                                                        document.getElementById('img_logo').addEventListener('change', handleFileSelect, false);
                                                    } else {
                                                        alert('The File APIs are not fully supported in this browser.');
                                                    }
                                                    function handleFileSelect(evt) {
                                                        var f = evt.target.files[0]; // FileList object
                                                        var reader = new FileReader();
                                                        // Closure to capture the file information.
                                                        reader.onload = (function (theFile) {
                                                            return function (e) {
                                                                var binaryData = e.target.result;
                                                                //Converting Binary Data to base 64
                                                                var base64String = window.btoa(binaryData);
                                                                //showing file converted to base64
                                                                document.getElementById('img_profile').value = base64String;
                                                                alert('sukses convert gambar');
                                                            };
                                                        })(f);
                                                        // Read in the image file as a data URL.
                                                        reader.readAsBinaryString(f);
                                                    }
                                                </script>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">Tambah</button>
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


