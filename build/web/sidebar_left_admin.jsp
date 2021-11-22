<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <ul class="sidebar-menu">
            <li class="header"><h3>BACK OFFICE</h3></li>
            <!-- sidebar administrator -->
            <li class="treeview">
                <a href="#"><i class="fa fa-users"></i> <span>Manajemen User</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListAgent">List User</a></li>
                    <li><a href="<%=request.getContextPath()%>/ListLimit">List User Limit</a></li>
                    <li><a href="<%=request.getContextPath()%>/ListVerifikasi">List Verifikasi</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-building-o"></i> <span>Koneksi</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListConnectionBiller">List Koneksi</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-cog"></i> <span>Produk</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListProductCode">List Produk</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-building-o"></i> <span>Mapping Produk Biller</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListBillerProduct">List Produk Biller</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-building-o"></i> <span>Mapping Produk User</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListProductUser">List Produk User</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-usd"></i><span>Top Up Saldo User</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <!--<li><a href="<%=request.getContextPath()%>/AddTopupSaldoAgent">Add Top Up Saldo User</a></li>-->
                    <li><a href="<%=request.getContextPath()%>/ListTopupSaldoAgent">List Topup Saldo User</a></li>
                    <li><a href="<%=request.getContextPath()%>/AgentBalance">List Saldo</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-file"></i><span>Poin</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <!--<li><a href="<%=request.getContextPath()%>/ListPoinRedeem">List Poin Redeem</a></li>-->
                    <li><a href="<%=request.getContextPath()%>/HystoriPoin">History Poin</a></li>
                </ul>
            </li>
             <li class="treeview">
                <a href=""><i class="fa fa-file"></i><span>Report</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListTodayTransaction">Today Transaction </a></li>
                    <li><a href="<%=request.getContextPath()%>/List7dayTransaction">Last 7 Day Transaction </a></li>
                    <li><a href="<%=request.getContextPath()%>/ListThisMonthTransaction">This Month Transaction </a></li>
                    <!--<li><a href="<%=request.getContextPath()%>/ListTransaction">All Transaction </a></li>-->
                </ul>
            </li>

            <!--            <li class="treeview">
                            <a href="#"><i class="fa fa-users"></i> <span>Koperasi</span> <i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li><a href="<%=request.getContextPath()%>/ListKop">List Koperasi</a></li>
                                <li><a href="<%=request.getContextPath()%>/ListCorp">List Anggota Koperasi</a></li>
                            </ul>
                        </li>-->
            <li class="treeview">
                <a href="#"><i class="fa fa-users"></i> <span>Ads</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListAds">List Ads</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-user"></i><span>User Login</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListLog">List Log User</a></li>
                </ul>
            </li>
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
