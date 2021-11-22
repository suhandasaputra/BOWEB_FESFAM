<%-- 
    Document   : header
    Author     : suhanda
--%>
<script>
window.history.forward("index.jsp");
</script>

<script>
    $(document).ready(function () {
    var mySession = '<%=session.getAttribute("userlevel")%>';
    if(mySession == 'administrator'){
        $.getJSON('BackendUserServlet?action=Listagentnotifadministrator', {}, function (data) {
        var useryabes = [];
        var useryabessum = data.length;
        var useryabessum1 = "You have " + data.length + " notification";
        for (var i = 0; i < data.length; i++) {
            useryabes += '<li><a><div class="pull-left"><div class="person"><i class="fa fa-user fa-2x"></i></div></div><h4>' +
                    data[i].username
                    + '<small><i class="fa fa-clock-o"></i>' + data[i].activitastime + '</small></h4><p>' +
                    data[i].activitas + '</p></a></li>';
        }
        $("ul#useryabes").html(useryabes);
        $("span#useryabessum").html(useryabessum);
        $("li#useryabessum1").html(useryabessum1);
    });
    }else if(mySession == 'admin'){
        $.getJSON('BackendUserServlet?action=Listagentnotif', {}, function (data) {
        var useryabes = [];
        var useryabessum = data.length;
        var useryabessum1 = "You have " + data.length + " notification";
        for (var i = 0; i < data.length; i++) {
            useryabes += '<li><a><div class="pull-left"><div class="person"><i class="fa fa-user fa-2x"></i></div></div><h4>' +
                    data[i].username
                    + '<small><i class="fa fa-clock-o"></i>' + data[i].activitastime + '</small></h4><p>' +
                    data[i].activitas + '</p></a></li>';
        }
        $("ul#useryabes").html(useryabes);
        $("span#useryabessum").html(useryabessum);
        $("li#useryabessum1").html(useryabessum1);
    });
    }else if(mySession == 'cs'){
        $.getJSON('BackendUserServlet?action=Listagentnotif', {}, function (data) {
        var useryabes = [];
        var useryabessum = data.length;
        var useryabessum1 = "You have " + data.length + " notification";
        for (var i = 0; i < data.length; i++) {
            useryabes += '<li><a><div class="pull-left"><div class="person"><i class="fa fa-user fa-2x"></i></div></div><h4>' +
                    data[i].username
                    + '<small><i class="fa fa-clock-o"></i>' + data[i].activitastime + '</small></h4><p>' +
                    data[i].activitas + '</p></a></li>';
        }
        $("ul#useryabes").html(useryabes);
        $("span#useryabessum").html(useryabessum);
        $("li#useryabessum1").html(useryabessum1);
    });
    }
});
</script>





<!--Untuk edit css header-->

<!-- Main Header -->
<header class="main-header">
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/Home" class="logo">
    <!--<a href="add_user.jsp" class="logo">-->
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>FES FAM</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>FES FAM</b></span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>
                        <span class="label label-success" id="useryabessum"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header" id="useryabessum1"></li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu" id="useryabes">
                            </ul>
                        </li>
                        <li class="footer"><a href="<%=request.getContextPath()%>/ListLog">See All notification</a></li>
                    </ul>
                </li>
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="hidden-xs">Logout</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- The user image in the menu -->
                        <li class="user-header">
                        </li>
                        <li class="user-footer">
                            <form action="LogoutServlet" method="post" class="pull-right">
                                <input class="btn btn-default btn-flat" type="submit" value="Logout">
                            </form>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>