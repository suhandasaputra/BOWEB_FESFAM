<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <ul class="sidebar-menu">
            <li class="treeview">
                <a href="#"><i class="fa fa-user-md"></i> <span>Manajemen User</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListAgent"><i class="fa fa-users"></i>List User</a></li>
                    <li><a href="<%=request.getContextPath()%>/ListLimit"><i class="fa fa-user-times"></i>List Limit</a></li>
                    <li><a href="<%=request.getContextPath()%>/ListVa"><i class="fa fa-bank"></i>List Virtual Account</a></li>
                    <li><a href="<%=request.getContextPath()%>/ListVerifikasi"><i class="fa fa-user-secret"></i>List Verifikasi</a></li>
                    <li><a href="<%=request.getContextPath()%>/CheckOtp"><i class="fa fa-key"></i>Check OTP</a></li>
                    <li><a href="<%=request.getContextPath()%>/Member"><i class="fa fa-level-up"></i>Go to Member</a></li>
                    <li><a href="<%=request.getContextPath()%>/Upgrade"><i class="fa fa-level-up"></i>Upgrade To Leader</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-balance-scale"></i> <span>Balance Management</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListTopupSaldoAgent">List User Topup</a></li>
                    <li><a href="<%=request.getContextPath()%>/AgentBalance">Saldo User</a></li>
                    <li><a href="<%=request.getContextPath()%>/PengembalianDana">Pengembalian Dana</a></li>
                    <li><a href="<%=request.getContextPath()%>/TimeoutSukses">Timeout Sukses</a></li>
                    <li><a href="<%=request.getContextPath()%>/TimeoutGagal">Timeout Gagal</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="glyphicon glyphicon-piggy-bank"></i><span>PPOB Balance</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListTopupSaldoBiller">List PPOB Topup</a></li>
                    <li><a href="<%=request.getContextPath()%>/BillerBalance">Saldo PPOB</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddTopupSaldoBiller">Topup Saldo PPOB</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-play-circle-o"></i><span>Manajemen Poin</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/HystoriPoin">History Poin</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-product-hunt"></i> <span>Produk PPOB</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListProductCode">List Produk</a></li>
                    <li><a href="<%=request.getContextPath()%>/ActivationTrx">Ketersediaan Produk</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddProductCode">Tambah Produk</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-product-hunt"></i> <span>Produk Biller</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListBillerProduct">List Produk Biller</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddProductBiller">Tambah Produk Biller</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-product-hunt"></i> <span>Produk User</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListProductUser">List Produk User</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddProductUser">Tambah Produk Prepaid</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddProductUserPayment">Tambah Produk Payment</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-adn"></i> <span>Manajemen Ads</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListAds">List Ads</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddAds">Tambah Ads</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-connectdevelop"></i> <span>Manajemen Koneksi</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListConnectionBiller">List Koneksi</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddConnectionBiller">Tambah Koneksi</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-user"></i><span>Manajemen Login</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListUser">List User Login</a></li>
                    <li><a href="<%=request.getContextPath()%>/ListLog">Log / User Activity</a></li>
                    <li><a href="<%=request.getContextPath()%>/AddUser">Create User Login</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-vimeo-square"></i><span>Version</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/UserVersion">User App Version</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href=""><i class="fa fa-file"></i><span>Report</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/ListTodayTransaction">Today Transaction </a></li>
                    <li><a href="<%=request.getContextPath()%>/List7dayTransaction">Last 7 Day Transaction </a></li>
                    <li><a href="<%=request.getContextPath()%>/ListThisMonthTransaction">This Month Transaction </a></li>
                    <li><a href="<%=request.getContextPath()%>/ListTransaction">All Transaction </a></li>
                </ul>
            </li>
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
