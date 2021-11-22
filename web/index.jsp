<%--<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<jsp:forward page="Welcome.do"/>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet" href="css/login.css">

<body>
    <div class="container">
        <div class="row vertical-offset-100">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default login">
                    <div class="panel-heading">                            
                        <div class="row-fluid user-row">
                            <i class="fa fa-users fa-3x"></i> 
                        </div>
                        <h3 class="panel-title user-row">FES FAM</h3> 
                    </div>
                    <div class="panel-body">
                        <div class="form-group" align="center">
                            <!--<H2>FESFAM</H2>-->
                            <hr>
                        </div>
                        <form action="LoginServlet" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="User Login" name="userlogin" type="text" required="required"/>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="User Password" name="userpass" type="password" required="required">
                                </div>
                                <input class="btn btn-lg btn-success btn-block" type="submit" value="Login"/>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
<footer class="footer">
<!--    <div class="container">
        <p class="text-muted">Place sticky footer content here.</p>
    </div>-->
    <div class="pull-right hidden-xs">
        
        <!--<p class="text-muted">Version 2.0 </p>-->
    </div>
    <!-- Default to the left -->
    <!--<p class="text-muted">Copyright &copy; 2018 PT. QUANTUM MULTI AKSES . All rights reserved.</p>-->
</footer>

<script src="js/jQuery-2.2.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
