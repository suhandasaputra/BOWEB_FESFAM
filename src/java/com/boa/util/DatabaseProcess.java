/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boa.util;

import com.boa.model.Agentlimit;
import com.boa.model.Agentyabes;
import com.boa.model.Billerproduct;
import com.boa.model.Blacklist;
import com.boa.model.Connectionbiller;
import com.boa.model.Crmagent;
import com.boa.model.Crmbiller;
import com.boa.model.Mappingagent;
import com.boa.model.Mappingbiller;
import com.boa.model.Poin;
import com.boa.model.Reportyabes;
import com.boa.model.Topupagent;
import com.boa.model.Topupbiller;
import com.boa.model.Transactiontrancode;
import com.boa.model.Userlogin;
import com.boa.model.Useryabes;
import com.boa.model.Verifikasi;
import com.boa.model.agentbalance;
import com.boa.model.billerbalance;
import com.bo.servlet.DatasourceEntry;
//import com.bo.singleton.ResponseCodeSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class DatabaseProcess {

    private static final Logger log = Logger.getLogger(DatabaseProcess.class);

    private void clearStatment(PreparedStatement stat) {
//        log.info("stat 2 : " + stat);
        if (stat != null) {
            try {
//                log.info("stat A");
                stat.clearBatch();
//                log.info("stat B");
                stat.clearParameters();
//                log.info("stat C");
                stat.close();
//                log.info("stat D");
                stat = null;
//                log.info("stat E");
            } catch (SQLException ex) {
//                log.error("clearStatment : " +ex.getMessage());
//                ex.printStackTrace();
            }
        }
    }

    private void clearDBConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
//                log.error("clearDBConnection : "+ex.getMessage());
            }
        }
    }

    private void clearResultset(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
//                log.error("clearResultset : "+ex.getMessage());
            }
        }
    }

    private void clearAllConnStatRS(Connection conn, PreparedStatement stat, ResultSet rs) {
        clearResultset(rs);
        clearStatment(stat);
        clearDBConnection(conn);
    }

    public ArrayList<agentbalance> getAgentBalance() throws SQLException {
        ArrayList<agentbalance> ppobList = new ArrayList<agentbalance>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from am_user");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                stat2 = conn.prepareStatement("select * from am_balance where agent_id='" + rs1.getString("agent_id") + "'");
                rs2 = stat2.executeQuery();
                while (rs2.next()) {
                    agentbalance ab = new agentbalance();
                    ab.setAgent_id(rs1.getString("agent_id"));
                    ab.setAgent_name(rs1.getString("agent_name"));
                    ab.setPhonenumber(rs1.getString("email"));
                    ab.setApp_id(rs1.getString("app_id"));
                    ab.setCurr_bal(rs2.getString("curr_bal"));
                    ab.setPoin_bal(rs2.getString("poin_bal"));
                    ab.setMax_curr_bal(rs2.getString("max_curr_bal"));
                    ppobList.add(ab);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {

            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return ppobList;
    }

    public ArrayList<Verifikasi> getAllVerifikasi() throws SQLException {
        ArrayList<Verifikasi> listverified = new ArrayList<Verifikasi>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from am_user");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                stat2 = conn.prepareStatement("select * from am_balance where agent_id='" + rs1.getString("agent_id") + "'");
                rs2 = stat2.executeQuery();
                while (rs2.next()) {
                    Verifikasi ab = new Verifikasi();
                    if (rs1.getString("agent_id") == null) {
                        ab.setAgent_id("-");
                    } else {
                        ab.setAgent_id(rs1.getString("agent_id"));
                    }

                    if (rs1.getString("agent_name") == null) {
                        ab.setAgent_name("-");
                    } else {
                        ab.setAgent_name(rs1.getString("agent_name"));
                    }

                    if (rs1.getString("phonenumber") == null) {
                        ab.setPhonenumber("-");
                    } else {
                        ab.setPhonenumber(rs1.getString("phonenumber"));
                    }

                    if (rs1.getString("no_ktp") == null) {
                        ab.setNo_ktp("-");
                    } else {
                        ab.setNo_ktp(rs1.getString("no_ktp"));
                    }

                    if (rs2.getString("curr_bal") == null) {
                        ab.setCurr_bal("-");
                    } else {
                        ab.setCurr_bal(rs2.getString("curr_bal"));
                    }

                    if (rs2.getString("max_curr_bal") == null) {
                        ab.setMax_curr_bal("-");
                    } else {
                        ab.setMax_curr_bal(rs2.getString("max_curr_bal"));
                    }

                    if (rs1.getString("verified") == null) {
                        ab.setVerified("-");
                    } else {
                        ab.setVerified(rs1.getString("verified"));
                    }

                    if (rs1.getString("img_ktp") == null) {
                        ab.setImg_ktp("-");
                    } else {
                        ab.setImg_ktp(rs1.getString("img_ktp"));
                    }

                    if (rs1.getString("img_profile") == null) {
                        ab.setImg_profile("-");
                    } else {
                        ab.setImg_profile(rs1.getString("Img_profile"));
                    }
                    listverified.add(ab);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return listverified;
    }

    public String getUserlevel(String userlogin, String userpass) {
        Connection conn = null;
        String userlevel = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM ppob_user WHERE userlogin=? and userpass=?");
            stat.setString(1, userlogin);
            stat.setString(2, userpass);
            rs = stat.executeQuery();

            if (rs.next()) {
                userlevel = rs.getString("username");
            }
        } catch (SQLException e) {
            return userlevel = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return userlevel;
    }

    public ArrayList<Userlogin> getAlluser() {
        ArrayList<Userlogin> userloginList = new ArrayList<Userlogin>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from ppob_user");
            rs = stat.executeQuery();
            while (rs.next()) {
                Userlogin userlogin = new Userlogin();
                userlogin.setUserlogin(rs.getString("userlogin"));
                userlogin.setUsername(rs.getString("username"));
                userloginList.add(userlogin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return userloginList;
    }

    public ArrayList<Blacklist> getAllBlacklist() {
        ArrayList<Blacklist> listblack = new ArrayList<Blacklist>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from blacklist");
            rs = stat.executeQuery();
            while (rs.next()) {
                Blacklist list = new Blacklist();
                list.setNama(rs.getString("nama"));
                list.setAlias(rs.getString("alias"));
                list.setTempat_tanggal_lahir(rs.getString("tempat_tanggal_lahir"));
                list.setAlamat(rs.getString("alamat"));
                list.setCatagory(rs.getString("catagory"));
                list.setKeterangan(rs.getString("keterangan"));
                list.setId(rs.getString("id"));
                list.setKtp(rs.getString("ktp"));
                list.setCreatedate(rs.getString("createdate"));

                listblack.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return listblack;
    }

    public String addUser(Userlogin Userlogin) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO ppob_user (userlogin,userpass,username) VALUES (?,?,?)");
            stat.setString(1, Userlogin.getUserlogin());
            stat.setString(2, Userlogin.getUserpass());
            stat.setString(3, Userlogin.getUsername());
            stat.executeUpdate();
            stat.clearParameters();

        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            return status = "gagal, user sudah ada";
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "Sukses menambahkan user ppob";
    }

    public boolean userYabes(String username, String activitas) {
        PreparedStatement stat = null;
        Connection conn = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO ppob_user_activity(username, activityname) VALUES (?, ?)");
            stat.setString(1, username);
            stat.setString(2, activitas);
            stat.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            clearStatment(stat);
            clearDBConnection(conn);
        }
        return true;
    }

    public ArrayList<billerbalance> getBillerBalance() throws SQLException {
        ArrayList<billerbalance> ppobList = new ArrayList<billerbalance>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from balance_us");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                billerbalance ab = new billerbalance();
                ab.setBillercode(rs1.getString("billercode"));
                ab.setCurr_bal(rs1.getString("curr_bal"));
                ppobList.add(ab);

            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat1, rs1);
        }
        return ppobList;
    }

    public String deleteUserlogin(String userlogin) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("DELETE FROM ppob_user WHERE userlogin=?");
            stat.setString(1, userlogin);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "0000";
    }

    public Userlogin getUserByUserlogin(String userLogin) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Userlogin userlogin = new Userlogin();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM ppob_user WHERE userlogin=?");
            stat.setString(1, userLogin);
            rs = stat.executeQuery();

            if (rs.next()) {
                userlogin.setUserlogin(rs.getString("userlogin"));
                userlogin.setUsername(rs.getString("username"));
                userlogin.setUserpass(rs.getString("userpass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return userlogin;
    }

    public String updateUserlogin(Userlogin Userlogin) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE ppob_user SET userlogin=?, userpass=?, username=? WHERE userlogin=?");
            stat.setString(1, Userlogin.getUserlogin());
            stat.setString(2, Userlogin.getUserpass());
            stat.setString(3, Userlogin.getUsername());
            stat.setString(4, Userlogin.getUserlogin());
            stat.executeUpdate();
            stat.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "Sukses";
    }

    public ArrayList<Useryabes> getAlluseryabes() {
        ArrayList<Useryabes> useryabesList = new ArrayList<Useryabes>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from ppob_user_activity order by activitytime desc limit 20");
            rs = stat.executeQuery();
            while (rs.next()) {
                Useryabes useryabes = new Useryabes();
                useryabes.setUsername(rs.getString("username"));
                useryabes.setActivitas(rs.getString("activityname"));
                useryabes.setActivitastime(rs.getString("activitytime"));
                useryabesList.add(useryabes);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return useryabesList;
    }

    public String addAgent(Agentyabes Agentyabes) {
        Connection conn = null;
        String status;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareStatement("select * from am_user where agent_id=?");
            stat.setString(1, Agentyabes.getAgent_id());
            rs = stat.executeQuery();
            status1 = rs.next();
            if (status1 == true) {
                return status = "0002";
            } else {
                stat = conn.prepareStatement("INSERT INTO am_user(agent_id, password, agent_name, address, phonenumber, agent_pin, userlevel) VALUES (?, ?, ?, ?, ?, ?, ?)");
                stat.setString(1, Agentyabes.getAgent_id());
                stat.setString(2, Agentyabes.getAgent_pass());
                stat.setString(3, Agentyabes.getAgent_name());
                stat.setString(4, Agentyabes.getAgent_address());
                stat.setString(5, Agentyabes.getAgent_phone());
                stat.setString(6, Agentyabes.getAgent_pin());
                stat.setInt(7, Agentyabes.getAgent_level());
                stat.executeUpdate();
//                stat.close();
                stat = conn.prepareStatement("INSERT INTO am_balance(agent_id) VALUES (?)");
                stat.setString(1, Agentyabes.getAgent_id());
                stat.executeUpdate();
//                stat.close();
                stat = conn.prepareStatement("INSERT INTO am_itemlimit(agent_id) VALUES (?)");
                stat.setString(1, Agentyabes.getAgent_id());
                stat.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "0000";
    }

    public String addPoinRedeem(Poin poin) {
        Connection conn = null;
        String status;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select trancodename from trancode where trancodeid = ?");
            stat1.setString(1, poin.getItemname());
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                boolean status1 = false;
                stat = conn.prepareStatement("select * from redeem_itemlist where redeem_itemid=?");
                stat.setString(1, poin.getRedeem_itemid());
                rs = stat.executeQuery();
                status1 = rs.next();
                if (status1 == true) {
                    return status = "0002";
                } else {
                    stat = conn.prepareStatement("INSERT INTO redeem_itemlist(redeem_itemid, itemname, price, stock, start_date, end_date, otherparam, trancodeid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    stat.setString(1, poin.getRedeem_itemid());
                    stat.setString(2, rs1.getString("trancodename"));
                    stat.setInt(3, Integer.valueOf(poin.getPrice()));
                    stat.setInt(4, Integer.valueOf(poin.getStock()));
                    Date start = requesttime1.parse(poin.getStart_date());
                    poin.setStart_date(requesttime3.format(start));
                    stat.setTimestamp(5, Timestamp.valueOf(poin.getStart_date()));
                    Date end = requesttime1.parse(poin.getEnd_date());
                    poin.setEnd_date(requesttime3.format(end));
                    stat.setTimestamp(6, Timestamp.valueOf(poin.getEnd_date()));
                    stat.setString(7, poin.getOtherparam());
                    stat.setString(8, poin.getItemname());
                    stat.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(DatabaseProcess.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat);
            clearResultset(rs);
            clearDBConnection(conn);
        }
        return status = "0000";
    }

    public ArrayList<Agentyabes> getAllAgentyabes() {
        ArrayList<Agentyabes> agentyabesList = new ArrayList<Agentyabes>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT a.agent_id, a.address, a.phonenumber, a.agent_name, b.curr_bal FROM am_user a inner join am_balance b on a.agent_id = b.agent_id");
            rs = stat.executeQuery();
            while (rs.next()) {
                Agentyabes agentyabes = new Agentyabes();
                if (rs.getString("agent_id") == null) {
                    agentyabes.setAgent_id("-");
                } else {
//                    agentyabes.setAgent_id(rs.getString("agent_id").replace(" ", "%20"));
                    agentyabes.setAgent_id(rs.getString("agent_id"));

                }
                if (rs.getString("address") == null) {
                    agentyabes.setAgent_address("-");
                } else {
                    agentyabes.setAgent_address(rs.getString("address"));
                }
                if (rs.getString("phonenumber") == null) {
                    agentyabes.setPhonenumber("-");
                } else {
                    agentyabes.setPhonenumber(rs.getString("phonenumber"));
                }
                if (rs.getString("agent_name") == null) {
                    agentyabes.setAgent_name("-");
                } else {
                    agentyabes.setAgent_name(rs.getString("agent_name"));
                }
                if (rs.getString("curr_bal") == null) {
                    agentyabes.setAmount("-");
                } else {
                    agentyabes.setAmount(rs.getString("curr_bal"));
                }
                agentyabesList.add(agentyabes);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return agentyabesList;
    }

    public Agentlimit getLimitByagentId(String agent_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Agentlimit agentlimit = new Agentlimit();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM am_balance WHERE agent_id=?");
            stat.setString(1, agent_id);
            rs = stat.executeQuery();
            if (rs.next()) {
                agentlimit.setAgent_id(rs.getString("agent_id"));
                agentlimit.setMax_bal_day(rs.getString("max_bal_day"));
                agentlimit.setMax_bal_month(rs.getString("max_bal_month"));
            }
            stat = conn.prepareStatement("SELECT * FROM am_itemlimit WHERE agent_id=?");
            stat.setString(1, agent_id);
            rs = stat.executeQuery();
            if (rs.next()) {
                agentlimit.setMax_item_day((String.valueOf(rs.getInt("max_item_day"))));
                agentlimit.setMax_item_month(String.valueOf(rs.getString("max_item_month")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return agentlimit;
    }

    public String updateLimit(Agentlimit Agentlimit) {
        Connection conn = null;
        String status;
        PreparedStatement stat = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE am_balance SET max_bal_day=?, max_bal_month=? WHERE agent_id=?");
            stat.setFloat(1, Float.parseFloat(Agentlimit.getMax_bal_day()));
            stat.setFloat(2, Float.parseFloat(Agentlimit.getMax_bal_month()));
            stat.setString(3, Agentlimit.getAgent_id());
            stat.executeUpdate();
            stat = conn.prepareStatement("UPDATE am_itemlimit SET  max_item_day=?, max_item_month=? WHERE agent_id=?");
            stat.setInt(1, Integer.parseInt(Agentlimit.getMax_item_day()));
            stat.setInt(2, Integer.parseInt(Agentlimit.getMax_item_month()));
            stat.setString(3, Agentlimit.getAgent_id());
            stat.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return status = ex.getMessage();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "Sukses update agent limit";
    }

    public ArrayList<Agentlimit> getAllLimit() {
        ArrayList<Agentlimit> agentlimitList = new ArrayList<Agentlimit>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM am_user");
            rs1 = stat.executeQuery();
            while (rs1.next()) {
                stat = conn.prepareStatement("SELECT * FROM am_balance WHERE agent_id='" + rs1.getString("agent_id") + "'");
                rs2 = stat.executeQuery();
                while (rs2.next()) {
                    stat = conn.prepareStatement("SELECT * FROM am_itemlimit WHERE agent_id='" + rs1.getString("agent_id") + "'");
                    rs3 = stat.executeQuery();
                    while (rs3.next()) {
                        Agentlimit agentlimit = new Agentlimit();
                        agentlimit.setAgent_id(rs1.getString("agent_id"));
                        agentlimit.setMax_bal_day(rs2.getString("max_bal_day"));
                        agentlimit.setMax_bal_month(rs2.getString("max_bal_month"));
                        agentlimit.setMax_item_day(rs3.getString("max_item_day"));
                        agentlimit.setMax_item_month(rs3.getString("max_item_month"));
                        agentlimitList.add(agentlimit);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
            clearResultset(rs1);
            clearResultset(rs2);
            clearResultset(rs3);
        }
        return agentlimitList;
    }

    public ArrayList<Poin> getAllPoin() {
        ArrayList<Poin> allpoin = new ArrayList<Poin>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM am_update_poin");
            rs = stat.executeQuery();
            while (rs.next()) {
                Poin poin = new Poin();
                poin.setAgent_id(rs.getString("agent_id"));

                Date trxtgl = requesttime3.parse(rs.getString("trx_date"));
                poin.setTrx_date(requesttime2.format(trxtgl));

                poin.setNoref(rs.getString("noref"));
                poin.setProduct_code(rs.getString("product_code"));
                poin.setPoin(rs.getString("poin"));
                poin.setBefore_poin(rs.getString("before_poin"));
                poin.setCurrent_poin(rs.getString("current_poin"));
                poin.setDescription(rs.getString("description"));
                allpoin.add(poin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(DatabaseProcess.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return allpoin;
    }

    public ArrayList<Poin> getAllPoinRedeem() {
        ArrayList<Poin> allpoinredeem = new ArrayList<Poin>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM redeem_itemlist");
            rs = stat.executeQuery();
            while (rs.next()) {
                Poin poin = new Poin();
                poin.setRedeem_itemid(rs.getString("redeem_itemid"));
                poin.setItemname(rs.getString("itemname"));
                poin.setTrancodeid(rs.getString("trancodeid"));
                poin.setPrice(rs.getString("price"));
                poin.setStock(rs.getString("stock"));

                Date startdate = requesttime3.parse(rs.getString("start_date"));
                poin.setStart_date(requesttime2.format(startdate));

                Date enddate = requesttime3.parse(rs.getString("end_date"));
                poin.setEnd_date(requesttime2.format(enddate));

                poin.setOtherparam(rs.getString("otherparam"));
                poin.setRequested(rs.getString("requested"));
                poin.setApproved(rs.getString("approved"));
                allpoinredeem.add(poin);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(DatabaseProcess.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return allpoinredeem;
    }

    public String updateTransaction(Transactiontrancode transactiontrancode) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update trancode set tctype=?, trancodeid=?, billercode=?, trancodename=? where trancodeid=?");
            stat.setString(1, transactiontrancode.getTctype());
            stat.setString(2, transactiontrancode.getTrancodeid());
            stat.setString(3, transactiontrancode.getBillercode());
            stat.setString(4, transactiontrancode.getTrancodename());
            stat.setString(5, transactiontrancode.getTrancodeid());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate transaction";
    }

    public ArrayList<Transactiontrancode> getAlltransactions() {
        ArrayList<Transactiontrancode> transactionList = new ArrayList<Transactiontrancode>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        try {
            Transactiontrancode transaction = new Transactiontrancode();
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from trancode");
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("select conname from socketconn where billercode = ?");
                stat1.setString(1, rs.getString("billercode"));
                rs1 = stat1.executeQuery();
                while (rs1.next()) {

                    transaction.setTrancodeid(rs.getString("trancodeid"));
                    transaction.setTrancodename(rs.getString("trancodename"));
                    transaction.setBillername(rs1.getString("conname"));
                    transaction.setTctype(rs.getString("tctype"));
                    transactionList.add(transaction);

                }

            }
//            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearAllConnStatRS(conn, stat1, rs1);

        }
        return transactionList;
    }

    public String deleteTransaction(String trancodeid) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("delete from trancode where trancodeid=?");
            stat.setString(1, trancodeid);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menghapus transaction";
    }

    public Transactiontrancode getTransactionByTrancodeid(String trancodeid) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Transactiontrancode transactiontrancode = new Transactiontrancode();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from trancode where trancodeid=?");
            stat.setString(1, trancodeid);
            rs = stat.executeQuery();

            if (rs.next()) {
                transactiontrancode.setTctype(rs.getString("tctype"));
                transactiontrancode.setTrancodeid(rs.getString("trancodeid"));
                transactiontrancode.setTrancodename(rs.getString("trancodename"));
                transactiontrancode.setBillercode(rs.getString("billercode"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            clearAllConnStatRS(conn, stat, rs);
        }
        return transactiontrancode;
    }

    public String addTransaction(Transactiontrancode transactiontrancode) {
        String status;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareStatement("select * from trancode where trancodeid=?");
            stat.setString(1, transactiontrancode.getTrancodeid());
            rs = stat.executeQuery();
            status1 = rs.next();
            if (status1 == true) {
                return status = "Maaf id transaction sudah ada";
            } else {
                stat = conn.prepareStatement("insert into trancode(tctype,trancodeid,billercode,trancodename) values (?,?,?,?)");
                stat.setString(1, transactiontrancode.getTctype());
                stat.setString(2, transactiontrancode.getTrancodeid());
                stat.setString(3, transactiontrancode.getBillercode());
                stat.setString(4, transactiontrancode.getTrancodename());
                stat.executeUpdate();
            }
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menambahkan transaction";
    }

    public String updateConnectionBiller(Connectionbiller Connectionbiller) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update socketconn set host=?, billercode=?, conname=?, packagename=? where billercode=?");
            stat.setString(1, Connectionbiller.getUrlhost());
            stat.setString(2, Connectionbiller.getBillercode());
            stat.setString(3, Connectionbiller.getBillername());
            stat.setString(4, Connectionbiller.getPackagename());
            stat.setString(5, Connectionbiller.getBillercode());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate connection biller";
    }

    public String updateBlacklist(Blacklist blacklist) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update blacklist set nama=?, alias=?, id=?, ktp=?, tempat_tanggal_lahir=?, catagory=?, alamat=?, keterangan=? where id=?");
            stat.setString(1, blacklist.getNama());
            stat.setString(2, blacklist.getAlias());
            stat.setString(3, blacklist.getId());
            stat.setString(4, blacklist.getKtp());
            stat.setString(5, blacklist.getTempat_tanggal_lahir());
            stat.setString(6, blacklist.getCatagory());
            stat.setString(7, blacklist.getAlamat());
            stat.setString(8, blacklist.getKeterangan());
            stat.setString(9, blacklist.getId());

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = "0002";
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "0000";
    }

    public String updateRedeemPoin(Poin poin) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;

        String status;

        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update redeem_itemlist set redeem_itemid=?, itemname=?, price=?, start_date=?, end_date=?, trancodeid=? where redeem_itemid=?");
            stat.setString(1, poin.getRedeem_itemid());
            stat.setString(2, poin.getItemname());
            stat.setInt(3, Integer.valueOf(poin.getPrice()));
            Date start = requesttime1.parse(poin.getStart_date());
            poin.setStart_date(requesttime3.format(start));
            stat.setTimestamp(4, Timestamp.valueOf(poin.getStart_date()));
            Date end = requesttime1.parse(poin.getEnd_date());
            poin.setEnd_date(requesttime3.format(end));
            stat.setTimestamp(5, Timestamp.valueOf(poin.getEnd_date()));
            try {
                stat1 = conn.prepareStatement("select trancodeid from trancode where trancodename = ?");
                stat1.setString(1, poin.getItemname());
                rs = stat1.executeQuery();
                while (rs.next()) {
                    stat.setString(6, rs.getString("trancodeid"));
                }
            } catch (Exception e) {
            }
            stat.setString(7, poin.getRedeem_itemid());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(DatabaseProcess.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            clearStatment(stat1);
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "0000";
    }

    public ArrayList<Connectionbiller> getAllConnectionbillers() {
        ArrayList<Connectionbiller> connectionList = new ArrayList<Connectionbiller>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from socketconn");
            rs = stat.executeQuery();
            while (rs.next()) {
                Connectionbiller connectionbiller = new Connectionbiller();
                connectionbiller.setUrlhost(rs.getString("host"));
//                connectionbiller.setBillercode(rs.getString("billercode").replace(" ", "%20"));
                connectionbiller.setBillercode(rs.getString("billercode"));
                connectionbiller.setBillername(rs.getString("conname"));
                connectionbiller.setPackagename(rs.getString("packagename"));
                connectionList.add(connectionbiller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return connectionList;
    }

    public String deleteConnectionBiller(String billercode) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("delete from socketconn where billercode=?");
            stat.setString(1, billercode);
            stat.executeUpdate();
            stat.clearParameters();
            stat.close();
            stat = null;
            stat = conn.prepareStatement("delete from balance_us where billercode=?");
            stat.setString(1, billercode);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "Sukses menghapus connection biller";
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
    public String deleteBlacklist(String id) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("delete from blacklist where id=?");
            stat.setString(1, id);
            stat.executeUpdate();
            stat.clearParameters();
//            stat.close();
//            stat = null;
//            stat = conn.prepareStatement("delete from balance_us where billercode=?");
//            stat.setString(1, billercode);
//            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = "0002";
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
//            clearStatment(stat1);
        }
        return status = "0000";
    }

    public String deletePoinRedeem(String redeem_itemid) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("delete from redeem_itemlist where redeem_itemid=?");
            stat.setString(1, redeem_itemid);
            stat.executeUpdate();
//            stat.clearParameters();
//            stat.close();
//            stat = null;
//            stat = conn.prepareStatement("delete from balance_us where billercode=?");
//            stat.setString(1, billercode);
//            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = "0002";
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "0000";
    }

    public Poin getPoinByRedeem_itemid(String redeem_itemid) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Poin poin = new Poin();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from redeem_itemlist where redeem_itemid=?");
            stat.setString(1, redeem_itemid);
            rs = stat.executeQuery();
            if (rs.next()) {
                poin.setRedeem_itemid(rs.getString("redeem_itemid"));
                poin.setItemname(rs.getString("itemname"));
                poin.setPrice(rs.getString("price"));
                poin.setStart_date(rs.getString("start_date"));
                poin.setEnd_date(rs.getString("end_date"));
                poin.setTrancodeid(rs.getString("trancodeid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return poin;
    }

    public Connectionbiller getConnectionByBillercode(String billercode) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Connectionbiller connectionbiller = new Connectionbiller();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from socketconn where billercode=?");
            stat.setString(1, billercode);
            rs = stat.executeQuery();

            if (rs.next()) {
                connectionbiller.setUrlhost(rs.getString("host"));
                connectionbiller.setBillercode(rs.getString("billercode"));
                connectionbiller.setBillername(rs.getString("conname"));
                connectionbiller.setPackagename(rs.getString("packagename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return connectionbiller;
    }

    public Blacklist getBlacklistById(String id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Blacklist blacklist = new Blacklist();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from blacklist where id=?");
            stat.setString(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                blacklist.setNama(rs.getString("nama"));
                blacklist.setAlias(rs.getString("alias"));
                blacklist.setId(rs.getString("id"));
                blacklist.setKtp(rs.getString("ktp"));
                blacklist.setTempat_tanggal_lahir(rs.getString("tempat_tanggal_lahir"));
                blacklist.setCatagory(rs.getString("catagory"));
                blacklist.setAlamat(rs.getString("alamat"));
                blacklist.setKeterangan(rs.getString("keterangan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return blacklist;
    }

    public String addConnectionBiller(Connectionbiller Connectionbiller) {
        String status;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareStatement("SELECT * FROM socketconn WHERE billercode=?");
            stat.setString(1, Connectionbiller.getBillercode());
            rs = stat.executeQuery();
            status1 = rs.next();
            stat.close();
            if (status1 == true) {
                return status = "Maaf billercode sudah ada";
            } else {
                stat = conn.prepareStatement("INSERT INTO socketconn(todirect, host, seq, billercode, conname, packagename, typeconn) VALUES ('web', ?, (select seq+1 from socketconn where todirect = 'web' order by seq desc fetch first 1 rows only), ?, ?, ?, 'web')");
                stat.setString(1, Connectionbiller.getUrlhost());
                stat.setString(2, Connectionbiller.getBillercode());
                stat.setString(3, Connectionbiller.getBillername());
                stat.setString(4, Connectionbiller.getPackagename());
                stat.executeUpdate();
                stat.clearParameters();
                stat.close();

                stat = conn.prepareStatement("INSERT INTO balance_us(billercode) VALUES (?)");
                stat.setString(1, Connectionbiller.getBillercode());
                stat.executeUpdate();
            }
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menambahkan connection";
    }

    public String addBillerProduct(Billerproduct Billerproduct) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareStatement("select * from trancode_biller where tcbiller=?");
            stat.setString(1, Billerproduct.getTcbiller());
            rs = stat.executeQuery();
            status1 = rs.next();
            stat.close();
            if (status1 == true) {
                return status = "Maaf biller code sudah ada";
            } else {
                stat = conn.prepareStatement("INSERT INTO trancode_biller(billercode, tcbiller, feebeli, hargabeli, trancodeid) VALUES (?, ?, ?, ?, ?)");
                stat.setString(1, Billerproduct.getBillercode());
                stat.setString(2, Billerproduct.getTcbiller());
                stat.setInt(3, Integer.valueOf(Billerproduct.getFeebeli()));
                stat.setInt(4, Integer.valueOf(Billerproduct.getHargabeli()));
                stat.setString(5, Billerproduct.getTrancodeid());
                stat.executeUpdate();
            }
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menambahkan biller product";
    }

    public String addBlackList(Blacklist blacklist) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareStatement("select id from blacklist where id=?");
            stat.setString(1, blacklist.getId());
            rs = stat.executeQuery();
            status1 = rs.next();
            stat.close();
            if (status1 == true) {
                return status = "0002";
            } else {
                stat = conn.prepareStatement("INSERT INTO blacklist(nama, alias, tempat_tanggal_lahir, alamat, catagory, keterangan, id, ktp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                stat.setString(1, blacklist.getNama());
                stat.setString(2, blacklist.getAlias());
                stat.setString(3, blacklist.getTempat_tanggal_lahir());
                stat.setString(4, blacklist.getAlamat());
                stat.setString(5, blacklist.getCatagory());
                stat.setString(6, blacklist.getKeterangan());
                stat.setString(7, blacklist.getId());
                stat.setString(8, blacklist.getKtp());
                stat.executeUpdate();
            }
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "0000";
    }

    public String updateBillerproduct(Billerproduct Billerproduct) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update trancode_biller set billercode=?, tcbiller=?, feebeli=?, hargabeli=?, trancodeid=? where billercode=? and tcbiller=?");
            stat.setString(1, Billerproduct.getBillercode());
            stat.setString(2, Billerproduct.getTcbiller());
            stat.setInt(3, Integer.valueOf(Billerproduct.getFeebeli()));
            stat.setInt(4, Integer.valueOf(Billerproduct.getHargabeli()));
            stat.setString(5, Billerproduct.getTrancodeid());
            stat.setString(6, Billerproduct.getBillercode());
            stat.setString(7, Billerproduct.getTcbiller());
            stat.executeUpdate();
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate biller product";
    }

    public ArrayList<Billerproduct> getAllBillerproduct() {
        ArrayList<Billerproduct> billerproductList = new ArrayList<Billerproduct>();
        Connection conn = null;
        PreparedStatement stat = null;
//        PreparedStatement stat2 = null;
//        PreparedStatement stat3 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from trancode_biller");
            rs1 = stat.executeQuery();
            while (rs1.next()) {
                stat = conn.prepareStatement("select * from trancode where trancodeid='" + rs1.getString("trancodeid") + "'");
                rs2 = stat.executeQuery();
                while (rs2.next()) {
//                    stat.close();
                    stat = conn.prepareStatement("select * from socketconn where billercode='" + rs1.getString("billercode") + "'");
                    rs3 = stat.executeQuery();
                    while (rs3.next()) {
                        Billerproduct billerproduct = new Billerproduct();
                        billerproduct.setBillercode(rs1.getString("billercode"));
                        billerproduct.setTcbiller(rs1.getString("tcbiller"));
                        billerproduct.setFeebeli(rs1.getString("feebeli") + ".00");
                        billerproduct.setHargabeli(rs1.getString("hargabeli") + ".00");
                        billerproduct.setTrancodename(rs2.getString("trancodename"));
                        int payment = 280000;
                        int prepaid = 290000;
                        int sendmoney = 180000;
                        int receivemoney = 100000;
                        if (rs2.getInt("tctype") == payment) {
                            billerproduct.setTctype("Bill Payment");
                        } else if (rs2.getInt("tctype") == prepaid) {
                            billerproduct.setTctype("Prepaid Reload");
                        } else if (rs2.getInt("tctype") == sendmoney) {
                            billerproduct.setTctype("Emoney");
                        } else if (rs2.getInt("tctype") == receivemoney) {
                            billerproduct.setTctype("Emoney");
                        }
                        billerproduct.setBillername(rs3.getString("conname"));
                        billerproductList.add(billerproduct);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
            clearResultset(rs1);
            clearResultset(rs2);
            clearResultset(rs3);
        }
        return billerproductList;
    }

    public ArrayList<Billerproduct> getAlltransaction(String transactionType) {
        ArrayList<Billerproduct> transactionList = new ArrayList<Billerproduct>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs2 = null;
        switch (transactionType) {
            case "Other":
                try {
                    conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
                    stat = conn.prepareStatement("select * from trancode "
                            + "where "
                            + "tctype NOT LIKE '280000' AND "
                            + "tctype NOT LIKE '290000'");
                    rs = stat.executeQuery();
                    while (rs.next()) {
                        Billerproduct biller = new Billerproduct();
                        biller.setTrancodeid(rs.getString("trancodeid"));
                        biller.setTrancodename(rs.getString("trancodename"));
                        transactionList.add(biller);
                    }
//                rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Bill Payment":
                try {
                    conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
                    stat = conn.prepareStatement("select * from trancode where tctype='280000'");
                    rs = stat.executeQuery();
                    while (rs.next()) {
                        Billerproduct biller = new Billerproduct();
                        biller.setTrancodeid(rs.getString("trancodeid"));
                        biller.setTrancodename(rs.getString("trancodename"));
                        transactionList.add(biller);
                    }
//                rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Prepaid Reload":
                try {
                    conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
                    stat1 = conn.prepareStatement("select * from trancode where tctype='290000'");
                    rs1 = stat1.executeQuery();
                    while (rs1.next()) {
                        Billerproduct biller = new Billerproduct();
                        biller.setTrancodeid(rs1.getString("trancodeid"));
                        biller.setTrancodename(rs1.getString("trancodename"));
                        transactionList.add(biller);
                    }
//                rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Ppob":
                try {
                    stat2 = conn.prepareStatement("select * from trancode where tctype='180000'");
                    rs2 = stat2.executeQuery();
                    while (rs2.next()) {
                        Billerproduct biller = new Billerproduct();
                        biller.setTrancodeid(rs.getString("trancodeid"));
                        biller.setTrancodename(rs.getString("trancodename"));
                        transactionList.add(biller);
                    }
//                rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Select Transaction Type":
                Billerproduct biller = new Billerproduct();
                biller.setTrancodeid("Select Transaction Name");
                biller.setTrancodename("Select Transaction Name");
                transactionList.add(biller);
                break;
        }
        clearStatment(stat1);
        clearResultset(rs1);
        clearStatment(stat2);
        clearResultset(rs2);
        clearDBConnection(conn);
        return transactionList;
    }

    public String deleteBillerproduct(String tcbiller, String billercode) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("delete from trancode_biller where tcbiller=? and billercode=?");
            stat.setString(1, tcbiller);
            stat.setString(2, billercode);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menghapus biller product";
    }

    public Billerproduct getBillerByTcbiller(String tcbiller, String billercode) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        Billerproduct billerproduct = new Billerproduct();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from trancode_biller where tcbiller=? and billercode=?");
            stat.setString(1, tcbiller);
            stat.setString(2, billercode);
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("select * from socketconn where billercode=?");
                stat1.setString(1, billercode);
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    stat2 = conn.prepareStatement("select * from trancode where trancodeid=?");
                    stat2.setString(1, rs.getString("trancodeid"));
                    rs2 = stat2.executeQuery();
                    while (rs2.next()) {
                        billerproduct.setTrancodename(rs2.getString("trancodename"));
                        billerproduct.setTctype(rs2.getString("tctype"));
                        if (rs2.getString("tctype").equals("280000")) {
                            billerproduct.setTctypename("Bill Payment");
                        } else if (rs2.getString("tctype").equals("290000")) {
                            billerproduct.setTctypename("Prepaid Reload");
                        }
//                        else if (rs2.getString("tctype").equals("180000")) {
//                            billerproduct.setTctypename("Ppob");
//                        }
                    }
                    billerproduct.setBillername(rs1.getString("conname"));
                }
                billerproduct.setBillercode(rs.getString("billercode"));
                billerproduct.setTcbiller(rs.getString("tcbiller"));
                billerproduct.setFeebeli(rs.getString("feebeli") + ".00");
                billerproduct.setHargabeli(rs.getString("hargabeli") + ".00");
                billerproduct.setTrancodeid(rs.getString("trancodeid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return billerproduct;
    }

    public ArrayList<Billerproduct> getAllbiller() {
        ArrayList<Billerproduct> billerList = new ArrayList<Billerproduct>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from socketconn");
            rs = stat.executeQuery();
            while (rs.next()) {
                Billerproduct biller = new Billerproduct();
                biller.setBillercode(rs.getString("billercode"));
                biller.setConname(rs.getString("conname"));
                billerList.add(biller);
            }
//            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return billerList;
    }

    public ArrayList<Billerproduct> getAllbillercode() {
        ArrayList<Billerproduct> billerList = new ArrayList<Billerproduct>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from socketconn");
            rs = stat.executeQuery();
            while (rs.next()) {
                Billerproduct biller = new Billerproduct();
                biller.setBillercode(rs.getString("billercode"));
                biller.setConname(rs.getString("conname"));
                billerList.add(biller);
            }
//            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return billerList;
    }

    public String addMappingAgent(Mappingagent Mappingagent) {
        String status;
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareStatement("select * from am_trancode where agent_id=? and trancodeid=?");
            stat.setString(1, Mappingagent.getAgent_id());
            stat.setString(2, Mappingagent.getTrancodeid());
            rs = stat.executeQuery();
            status1 = rs.next();
            if (status1 == true) {
                return status = "Maaf Agent id / trancode id sudah ada";
            } else {
                stat1 = conn.prepareStatement("INSERT INTO am_trancode(agent_id, trancodeid, feejual, hargajual,sa_commission) VALUES (?, ?, ?, ?, ?)");
                stat1.setString(1, Mappingagent.getAgent_id());
                stat1.setString(2, Mappingagent.getTrancodeid());
                stat1.setInt(3, Integer.valueOf(Mappingagent.getFeejual()));
                stat1.setInt(4, Integer.valueOf(Mappingagent.getHargajual()));
                stat1.setInt(5, Integer.valueOf(Mappingagent.getSa_commission()));
                stat1.executeUpdate();
            }
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearStatment(stat1);
        }
        return status = "Sukses menambahkan mapping agent";
    }

    public ArrayList<Mappingagent> getAllagent() {
        ArrayList<Mappingagent> agentList = new ArrayList<Mappingagent>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from am_user");
            rs = stat.executeQuery();
            while (rs.next()) {
                Mappingagent agent = new Mappingagent();
                agent.setAgent_id(rs.getString("agent_id"));
                if (rs.getString("agent_name") != null) {
                    agent.setAgentname(rs.getString("agent_name"));
                } else {
                    agent.setAgentname("-");
                }
                agentList.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);

        }
        return agentList;
    }

    public ArrayList<Poin> getAllproductName() {
        ArrayList<Poin> listpoin = new ArrayList<Poin>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from trancode");
            rs = stat.executeQuery();
            while (rs.next()) {
                Poin poin = new Poin();
                poin.setTrancodeid(rs.getString("trancodeid"));
                if (rs.getString("trancodename") != null) {
                    poin.setItemname(rs.getString("trancodename"));
                } else {
                    poin.setItemname("-");
                }
                listpoin.add(poin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);

        }
        return listpoin;
    }

    public String deleteMappingagent(String trancodeid, String agent_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("delete from am_trancode where trancodeid=? and agent_id=?");
            stat.setString(1, trancodeid);
            stat.setString(2, agent_id);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menghapus mapping agent";
    }

    public Mappingagent getMappingagentByTrancodeid(String trancodeid, String agent_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        Mappingagent mappingagent = new Mappingagent();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from am_trancode where trancodeid=? and agent_id=?");
            stat.setString(1, trancodeid);
            stat.setString(2, agent_id);
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("select * from am_user where agent_id=?");
                stat1.setString(1, agent_id);
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    stat2 = conn.prepareStatement("select * from trancode where trancodeid=?");
                    stat2.setString(1, trancodeid);
                    rs2 = stat2.executeQuery();
                    while (rs2.next()) {
                        mappingagent.setTrancodename(rs2.getString("trancodename"));
                        mappingagent.setTctype(rs2.getString("tctype"));
                        if (rs2.getString("tctype").equals("280000")) {
                            mappingagent.setTctypename("Bill Payment");
                        } else if (rs2.getString("tctype").equals("290000")) {
                            mappingagent.setTctypename("Prepaid Reload");
                        } else if (rs2.getString("tctype").equals("180000")) {
                            mappingagent.setTctypename("Ppob");
                        }
                    }
                    mappingagent.setAgentname(rs1.getString("agent_name"));
                }
                mappingagent.setAgent_id(rs.getString("agent_id"));
                mappingagent.setTrancodeid(rs.getString("trancodeid"));
                mappingagent.setFeejual(rs.getString("feejual") + ".00");
                mappingagent.setHargajual(rs.getString("hargajual") + ".00");
                mappingagent.setSa_commission(rs.getString("sa_commission") + ".00");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return mappingagent;
    }

    public String updateMappingagent(Mappingagent Mappingagent) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update am_trancode set agent_id=?, trancodeid=?, feejual=?, hargajual=?, sa_commission=? where agent_id=? and trancodeid=?");
            stat.setString(1, Mappingagent.getAgent_id());
            stat.setString(2, Mappingagent.getTrancodeid());
            stat.setInt(3, Integer.valueOf(Mappingagent.getFeejual()));
            stat.setInt(4, Integer.valueOf(Mappingagent.getHargajual()));
            stat.setInt(5, Integer.valueOf(Mappingagent.getSa_commission()));
            stat.setString(6, Mappingagent.getAgent_id());
            stat.setString(7, Mappingagent.getTrancodeid());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate mapping agent";
    }

    public ArrayList<Mappingagent> getAllMappingagent() {
        ArrayList<Mappingagent> mappingagentList = new ArrayList<Mappingagent>();
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from am_trancode");
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("select * from trancode where trancodeid='" + rs.getString("trancodeid") + "'");
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    stat2 = conn.prepareStatement("select * from am_user where agent_id='" + rs.getString("agent_id") + "'");
                    rs2 = stat2.executeQuery();
                    while (rs2.next()) {
                        Mappingagent mappingagent = new Mappingagent();
                        mappingagent.setAgent_id(rs.getString("agent_id"));
//                        System.out.println("agent_id : " + rs.getString("agent_id"));
                        mappingagent.setFeejual(rs.getString("feejual") + ".00");
//                        System.out.println("feejual : " + rs.getString("feejual"));
                        mappingagent.setHargajual(rs.getString("hargajual") + ".00");
//                        System.out.println("hargajual : " + rs.getString("hargajual"));
                        mappingagent.setTrancodeid(rs.getString("trancodeid"));
                        mappingagent.setSa_commission(rs.getString("sa_commission") + ".00");
                        mappingagent.setTrancodename(rs1.getString("trancodename"));
//                        System.out.println("trancodename : " + rs1.getString("trancodename"));
                        int payment = 280000;
                        int prepaid = 290000;
                        int sendmoney = 180000;
                        int receivemoney = 100000;
                        if (rs1.getInt("tctype") == payment) {
                            mappingagent.setTctype("Bill Payment");
                        } else if (rs1.getInt("tctype") == prepaid) {
                            mappingagent.setTctype("Prepaid Reload");
                        } else if (rs1.getInt("tctype") == sendmoney) {
                            mappingagent.setTctype("Send Money");
                        } else if (rs1.getInt("tctype") == receivemoney) {
                            mappingagent.setTctype("Receive Money");
                        }
                        mappingagent.setAgentname(rs2.getString("agent_name"));
                        mappingagentList.add(mappingagent);
                    }
                }
            }
//            rs1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return mappingagentList;
    }

    public ArrayList<Mappingbiller> getMappingbiller() {
        ArrayList<Mappingbiller> mappingbillerList = new ArrayList<Mappingbiller>();
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from trancode");
            rs = stat.executeQuery();
            while (rs.next()) {
                Mappingbiller mappingbiller = new Mappingbiller();
                mappingbiller.setTrancodeid(rs.getString("trancodeid"));
                mappingbiller.setTrancodename(rs.getString("trancodename"));
                if (rs.getString("billercode") == null) {
                    mappingbiller.setBillercode("-");
                } else {
                    mappingbiller.setBillercode(rs.getString("billercode"));
                }
                int payment = 280000;
                int prepaid = 290000;
                int sendmoney = 180000;
                int receivemoney = 100000;
                if (rs.getInt("tctype") == payment) {
                    mappingbiller.setTctype("Bill Payment");
                } else if (rs.getInt("tctype") == prepaid) {
                    mappingbiller.setTctype("Prepaid Reload");
                } else if (rs.getInt("tctype") == sendmoney) {
                    mappingbiller.setTctype("Send Money");
                } else if (rs.getInt("tctype") == receivemoney) {
                    mappingbiller.setTctype("Receive Money");
                }
                stat1 = conn.prepareStatement("select * from socketconn where billercode='" + rs.getString("billercode") + "'");
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    if (rs.getString("billercode") == null) {
                        mappingbiller.setBillername("-");
                    } else {
                        mappingbiller.setBillername(rs1.getString("conname"));
                    }
                }
                mappingbillerList.add(mappingbiller);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            clearStatment(stat1);
            clearResultset(rs1);
            clearDBConnection(conn);
        }
        return mappingbillerList;
    }

    public Mappingbiller getMappingbillerByTrancodeid(String trancodeid) {
        Mappingbiller mappingbiller = new Mappingbiller();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from trancode where trancodeid=?");
            stat.setString(1, trancodeid);
            rs = stat.executeQuery();
            while (rs.next()) {
                mappingbiller.setTrancodeid(rs.getString("trancodeid"));
                mappingbiller.setTrancodename(rs.getString("trancodename"));
                if (rs.getString("tctype").equals("280000")) {
                    mappingbiller.setTctype("Bill Payment");
                } else if (rs.getString("tctype").equals("290000")) {
                    mappingbiller.setTctype("Prepaid Reload");
                } else if (rs.getString("tctype").equals("180000")) {
                    mappingbiller.setTctype("Ppob");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return mappingbiller;
    }

    public ArrayList<Mappingbiller> getAllbillerMapping() {
        ArrayList<Mappingbiller> billerList = new ArrayList<Mappingbiller>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from socketconn");
            rs = stat.executeQuery();
            while (rs.next()) {
                Mappingbiller biller = new Mappingbiller();
                biller.setBillercode(rs.getString("billercode"));
                biller.setBillername(rs.getString("conname"));
                billerList.add(biller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return billerList;
    }

    public String updateMappingBiller(Mappingbiller mappingbiller) {
        String status;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update trancode set  billercode=? where trancodeid=?");
            stat.setString(1, mappingbiller.getBillercode());
            stat.setString(2, mappingbiller.getTrancodeid());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate mapping biller";
    }

    public ArrayList<Crmbiller> getAllCrmbiller() {
        ArrayList<Crmbiller> crmbillerList = new ArrayList<Crmbiller>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from biller_crm");
            rs = stat.executeQuery();
            while (rs.next()) {
                Crmbiller crmbiller = new Crmbiller();
                if (rs.getString("codecrm") == null) {
                    crmbiller.setCodecrm("-");
                } else {
                    crmbiller.setCodecrm(rs.getString("codecrm"));
                }
                if (rs.getString("billercode") == null) {
                    crmbiller.setBillercode("-");

                } else {
                    crmbiller.setBillercode(rs.getString("billercode"));
                }
                if (rs.getString("posisi") == null) {
                    crmbiller.setPosisi("-");
                } else {
                    crmbiller.setPosisi(rs.getString("posisi"));
                }
                if (rs.getString("nama") == null) {
                    crmbiller.setNama("-");
                } else {
                    crmbiller.setNama(rs.getString("nama"));
                }
                if (rs.getString("numberphone") == null) {
                    crmbiller.setNumberphone("-");

                } else {
                    crmbiller.setNumberphone(rs.getString("numberphone"));
                }
                if (rs.getString("email") == null) {
                    crmbiller.setEmail("-");
                } else {
                    crmbiller.setEmail(rs.getString("email"));
                }
                crmbillerList.add(crmbiller);
            }
//            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return crmbillerList;
    }

    public String addCrmBiller(Crmbiller Crmbiller) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;
        String uniqcode = Crmbiller.getBillercode().toString() + Crmbiller.getPosisi().toString();
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareCall("SELECT * FROM biller_crm where billercode=? and posisi=?");
            stat.setString(1, Crmbiller.getBillercode());
            stat.setString(2, Crmbiller.getPosisi());
            rs = stat.executeQuery();
            status1 = rs.next();
            if (status1 == true) {
                return status = "Maaf nama biller dan posisi sudah ada";
            } else {
                stat1 = conn.prepareStatement("INSERT INTO biller_crm (codecrm,billercode,posisi,nama,numberphone,email) VALUES (?,?,?,?,?,?)");
                stat1.setString(1, uniqcode);
                stat1.setString(2, Crmbiller.getBillercode());
                stat1.setString(3, Crmbiller.getPosisi());
                stat1.setString(4, Crmbiller.getNama());
                stat1.setString(5, Crmbiller.getNumberphone());
                stat1.setString(6, Crmbiller.getEmail());
                stat1.executeUpdate();
            }
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearStatment(stat1);
        }
        return status = "Sukses menambahkan crm biller";
    }

    public String deleteCrmbiller(String codecrm) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("DELETE FROM biller_crm WHERE codecrm=?");
            stat.setString(1, codecrm);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menghapus crm biller";
    }

    public Crmbiller getCrmbillerByCodecrm(String codecrm) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        Crmbiller crmbiller = new Crmbiller();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from biller_crm WHERE codecrm=?");
            stat.setString(1, codecrm);
            rs = stat.executeQuery();

            while (rs.next()) {
                stat1 = conn.prepareStatement("select * from socketconn where billercode=?");
                stat1.setString(1, rs.getString("billercode"));
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    crmbiller.setBillername(rs1.getString("conname"));
                    crmbiller.setBillercode(rs.getString("billercode"));
                    crmbiller.setPosisi(rs.getString("posisi"));
                    crmbiller.setNama(rs.getString("nama"));
                    crmbiller.setNumberphone(rs.getString("numberphone"));
                    crmbiller.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            clearStatment(stat1);
            clearResultset(rs1);
            clearDBConnection(conn);
        }
        return crmbiller;
    }

    public String updateCrmbiller(Crmbiller Crmbiller) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        String uniqcode = Crmbiller.getBillercode().toString() + Crmbiller.getPosisi().toString();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE biller_crm SET codecrm=?, billercode=?, posisi=?, nama=?, numberphone=?, email=? WHERE codecrm=?");
            stat.setString(1, uniqcode);
            stat.setString(2, Crmbiller.getBillercode());
            stat.setString(3, Crmbiller.getPosisi());
            stat.setString(4, Crmbiller.getNama());
            stat.setString(5, Crmbiller.getNumberphone());
            stat.setString(6, Crmbiller.getEmail());
            stat.setString(7, uniqcode);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate crm biller";
    }

    public String addCrmagent(Crmagent Crmagent) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;
        String uniqcode = Crmagent.getAgent_id().toString() + Crmagent.getPosisi().toString();
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareCall("SELECT * FROM agent_crm where agent_id=? and posisi=?");
            stat.setString(1, Crmagent.getAgent_id());
            stat.setString(2, Crmagent.getPosisi());
            rs = stat.executeQuery();
            status1 = rs.next();
            if (status1 == true) {
                return status = "Maaf nama agent dan posisi sudah ada";
            } else {
                stat1 = conn.prepareStatement("INSERT INTO agent_crm (codecrm,agent_id,posisi,nama,numberphone,email) VALUES (?,?,?,?,?,?)");
                stat1.setString(1, uniqcode);
                stat1.setString(2, Crmagent.getAgent_id());
                stat1.setString(3, Crmagent.getPosisi());
                stat1.setString(4, Crmagent.getNama());
                stat1.setString(5, Crmagent.getNumberphone());
                stat1.setString(6, Crmagent.getEmail());
                stat1.executeUpdate();
            }
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearStatment(stat1);
        }
        return status = "Sukses menambahkan crm agent";
    }

    public ArrayList<Crmagent> getAllCrmagent() {
        ArrayList<Crmagent> crmagentList = new ArrayList<Crmagent>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from am_crm");
            rs = stat.executeQuery();
            while (rs.next()) {
                Crmagent crmagent = new Crmagent();
                if (rs.getString("codecrm") == null) {
                    crmagent.setCodecrm("-");
                } else {
                    crmagent.setCodecrm(rs.getString("codecrm"));
                }
                if (rs.getString("agent_id") == null) {
                    crmagent.setAgent_id("-");

                } else {
                    crmagent.setAgent_id(rs.getString("agent_id"));
                }
                if (rs.getString("posisi") == null) {
                    crmagent.setPosisi("-");
                } else {
                    crmagent.setPosisi(rs.getString("posisi"));
                }
                if (rs.getString("nama") == null) {
                    crmagent.setNama("-");
                } else {
                    crmagent.setNama(rs.getString("nama"));
                }
                if (rs.getString("numberphone") == null) {
                    crmagent.setNumberphone("-");

                } else {
                    crmagent.setNumberphone(rs.getString("numberphone"));
                }
                if (rs.getString("email") == null) {
                    crmagent.setEmail("-");
                } else {
                    crmagent.setEmail(rs.getString("email"));
                }
                crmagentList.add(crmagent);
            }
//            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);

        }
        return crmagentList;
    }

    public String deleteCrmagent(String codecrm) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("DELETE FROM agent_crm WHERE codecrm=?");
            stat.setString(1, codecrm);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menghapus crm agent";
    }

    public Crmagent getCrmagentByCodecrm(String codecrm) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Crmagent crmagent = new Crmagent();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from agent_crm WHERE codecrm=?");
            stat.setString(1, codecrm);
            rs = stat.executeQuery();

            while (rs.next()) {
                stat1 = conn.prepareStatement("select * from am_user where agent_id=?");
                stat1.setString(1, rs.getString("agent_id"));
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    crmagent.setAgentname(rs1.getString("agent_name"));
                    crmagent.setAgent_id(rs.getString("agent_id"));
                    crmagent.setPosisi(rs.getString("posisi"));
                    crmagent.setNama(rs.getString("nama"));
                    crmagent.setNumberphone(rs.getString("numberphone"));
                    crmagent.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearStatment(stat1);
            clearResultset(rs1);
            clearDBConnection(conn);
        }
        return crmagent;
    }

    public String updateCrmbiller(Crmagent Crmagent) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        String uniqcode = Crmagent.getAgent_id().toString() + Crmagent.getPosisi().toString();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE agent_crm SET codecrm=?, agent_id=?, posisi=?, nama=?, numberphone=?, email=? WHERE codecrm=?");
            stat.setString(1, uniqcode);
            stat.setString(2, Crmagent.getAgent_id());
            stat.setString(3, Crmagent.getPosisi());
            stat.setString(4, Crmagent.getNama());
            stat.setString(5, Crmagent.getNumberphone());
            stat.setString(6, Crmagent.getEmail());
            stat.setString(7, uniqcode);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate crm agent";
    }

    public String addTopupBiller(Topupbiller Topupbiller, String username) {
        Connection conn = null;
        String status;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Integer saldoBefore = null;
        Integer newAmount = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM balance_us WHERE billercode=?");
            stat.setString(1, Topupbiller.getBillercode());
            rs = stat.executeQuery();
            while (rs.next()) {
                saldoBefore = Integer.parseInt(rs.getString("curr_bal"));
            }
            rs.close();
            stat.close();
            stat = conn.prepareStatement("UPDATE balance_us SET curr_bal=?  WHERE billercode=?");
            stat.setInt(1, Integer.parseInt(Topupbiller.getAmount()) + saldoBefore);
            stat.setString(2, Topupbiller.getBillercode());
            stat.executeUpdate();
            stat.close();
            stat = conn.prepareStatement("INSERT INTO biller_update_balance(billercode,amount,before_balance,current_balance,bank_name,acct_no,transfer_date) VALUES (?,?,?,?,?,?,?)");
            stat.setString(1, Topupbiller.getBillercode());
            stat.setInt(2, Integer.parseInt(Topupbiller.getAmount()));
            stat.setInt(3, saldoBefore);
            stat.setInt(4, Integer.parseInt(Topupbiller.getAmount()) + saldoBefore);
            stat.setString(5, Topupbiller.getBank_name());
            stat.setString(6, Topupbiller.getAcct_no());
            stat.setString(7, Topupbiller.getTransfer_date());
            stat.executeUpdate();

            log.info("");
            log.info("============== ADD TOPUP BILLER : " + Topupbiller.getBillercode() + ", amount :" + Topupbiller.getAmount() + ", Previous Balance :" + saldoBefore + ", Current Balance  :" + (Integer.parseInt(Topupbiller.getAmount()) + saldoBefore) + ", bank :" + Topupbiller.getBank_name() + ", acct number :" + Topupbiller.getAcct_no() + ", Transfer Date :" + Topupbiller.getTransfer_date() + " Create By :" + username + " =================");
            log.info("");

        } catch (SQLException ex) {
            ex.printStackTrace();
            return status = ex.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Success Topup Biller";
    }

    public ArrayList<Topupbiller> getAllTopupbiller() {
        ArrayList<Topupbiller> topupbillerList = new ArrayList<Topupbiller>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from biller_update_balance");
            rs = stat.executeQuery();
            while (rs.next()) {
                Topupbiller topupbiller = new Topupbiller();
                topupbiller.setBillercode(rs.getString("billercode"));
                topupbiller.setAmount(rs.getString("amount"));
                topupbiller.setBefore_balance(rs.getString("before_balance"));
                topupbiller.setCurrent_balance(rs.getString("current_balance"));
                topupbiller.setBank_name(rs.getString("bank_name"));
                topupbiller.setAcct_no(rs.getString("acct_no"));
                topupbiller.setTransfer_date(rs.getString("transfer_date"));
                topupbiller.setTopup_date(rs.getString("topup_date"));
                topupbillerList.add(topupbiller);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return topupbillerList;
    }

    public String addTopupagent(Topupagent Topupagent, String username) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs2 = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();

            stat = conn.prepareStatement("INSERT INTO am_update_balance(agent_id, amount, bank_name, acct_no, transfer_date, no_ref) VALUES (?, ?, ?, ?, ?, ?)");
            stat.setString(1, Topupagent.getAgent_id());
            stat.setInt(2, Integer.parseInt(Topupagent.getAmount()));
            stat.setString(3, Topupagent.getBank_name());
            stat.setString(4, Topupagent.getAcct_no());
            stat.setString(5, Topupagent.getTransfer_date());
            stat.setString(6, Topupagent.getAcct_no());
            stat.executeUpdate();
            stat = null;
            stat = conn.prepareStatement("INSERT INTO tempmsg(msgid, noref, billercode, proccode, transactionid, productcode, fromagent, amount, refer, responsecode, rcinternal, no_ba) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, Topupagent.getAcct_no() + "1");
            stat.setString(2, Topupagent.getAcct_no() + "1");
            stat.setString(3, Topupagent.getBank_name());
            stat.setString(4, "400009");
            stat.setString(5, "400009");
            stat.setString(6, "400009");
            stat.setString(7, Topupagent.getAgent_id());
            stat.setString(8, Topupagent.getAmount());

            stat1 = conn.prepareStatement("SELECT agent_id,reference_id from am_user where agent_id = ?");
            stat1.setString(1, Topupagent.getAgent_id());
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                stat.setString(9, rs1.getString("reference_id"));
                stat2 = conn.prepareStatement("SELECT cu_id, no_anggota from am_corp where cu_id = ?");
                stat2.setString(1, rs1.getString("reference_id"));
                rs2 = stat2.executeQuery();
                while (rs2.next()) {
                    stat.setString(12, rs2.getString("no_anggota"));
                }
            }
//            stat.setInt(10, 0);
//            stat.setInt(11, 0);
            stat.setString(10, "0000");
            stat.setString(11, "0000");
            stat.executeUpdate();

            stat = null;
            stat = conn.prepareStatement("INSERT INTO tempmsg(msgid, noref, billercode, proccode, transactionid, productcode, fromagent, amount, refer, responsecode, rcinternal, no_ba, custno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, Topupagent.getAcct_no());
            stat.setString(2, Topupagent.getAcct_no());
            stat.setString(3, Topupagent.getBank_name());
            stat.setString(4, "005025");
            stat.setString(5, "005025");
            stat.setString(6, "005025");
            stat.setString(7, Topupagent.getCu_id());
            stat.setString(8, "-" + Topupagent.getAmount());

            stat.setString(9, Topupagent.getCu_id());
            stat2 = conn.prepareStatement("SELECT cu_id, no_anggota, phonenumber from am_corp where cu_id = ?");
            stat2.setString(1, Topupagent.getCu_id());
            rs2 = stat2.executeQuery();
            while (rs2.next()) {
                stat.setString(12, rs2.getString("no_anggota"));
                stat.setString(13, rs2.getString("phonenumber"));
            }

//            stat.setInt(10, 0);
//            stat.setInt(11, 0);
            stat.setString(10, "0000");
            stat.setString(11, "0000");

            stat.executeUpdate();

            log.info("");
            log.info("============== ADD TOPUP AGENT : " + Topupagent.getAgent_id() + ", amount :" + Topupagent.getAmount() + ", bank :" + Topupagent.getBank_name() + ", acct number :" + Topupagent.getAcct_no() + ", Transfer Date :" + Topupagent.getTransfer_date() + " Create By :" + username + " =================");
            log.info("");
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
          
            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return status = "Success , Waiting Approve";
    }

    public String debetagent(Topupagent Topupagent, String username) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO tempmsg(msgid, noref, billercode, proccode, transactionid, productcode, amount, refer, prev_bal, curr_bal, responsecode, rcinternal, no_ba, fromagent, custno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, Topupagent.getRrn());
            stat.setString(2, Topupagent.getRrn());
            stat.setString(3, Topupagent.getBank_name());
            stat.setString(4, "005024");
            stat.setString(5, "005024");
            stat.setString(6, "005024");
            stat.setString(7, "-" + Topupagent.getAmount());
            stat.setString(8, Topupagent.getCu_id());
            stat.setInt(9, 0);
            stat.setInt(10, 0);
            stat.setString(11, "0000");
            stat.setString(12, "0000");
            stat1 = conn.prepareStatement("SELECT no_anggota, cu_id from am_corp where cu_id = ?");
            stat1.setString(1, Topupagent.getCu_id());
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                stat.setString(13, rs1.getString("no_anggota"));
            }
            stat.setString(14, Topupagent.getCu_id());
            stat.setString(15, Topupagent.getAcct_no());

            stat.executeUpdate();

            log.info("");
            log.info("============== DEBET KOPERASI : " + Topupagent.getAgent_id() + ", amount :" + Topupagent.getAmount() + ", bank :" + Topupagent.getBank_name() + ", acct number :" + Topupagent.getAcct_no() + ", Transfer Date :" + Topupagent.getTransfer_date() + " Create By :" + username + " =================");
            log.info("");
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
          
            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat);
            clearDBConnection(conn);
        }
        return status = "0000";
    }

    public boolean updateTopupagent(String agentid, String topupid, String username) {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        PreparedStatement stat3 = null;
        PreparedStatement stat4 = null;
        PreparedStatement stat5 = null;

        ResultSet rs3 = null;
        ResultSet rs2 = null;
        ResultSet rs1 = null;
        ResultSet rs = null;
        Integer saldoBefore = null;
        Integer newAmount = null;
        Integer currbal = null;
        try {

            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT process_exe FROM am_update_balance where topup_id=?");
            stat.setFloat(1, Float.parseFloat(topupid));
            rs = stat.executeQuery();
            while (rs.next()) {
                if (rs.getString("process_exe").equals("f")) {
                    stat1 = conn.prepareStatement("SELECT curr_bal FROM am_balance where agent_id=?");
                    stat1.setString(1, agentid);
                    rs1 = stat1.executeQuery();
                    while (rs1.next()) {
                        saldoBefore = rs1.getInt("curr_bal");
                    }
//                    rs.close();
//                    stat.close();

                    stat2 = conn.prepareStatement("SELECT amount FROM am_update_balance where topup_id=?");
                    stat2.setFloat(1, Float.parseFloat(topupid));
                    rs2 = stat2.executeQuery();
                    while (rs2.next()) {
                        newAmount = rs2.getInt("amount");
//                        currbal = rs2.getInt("current_balance");
                    }
//                    stat.close();
                    stat3 = conn.prepareStatement("UPDATE am_balance SET curr_bal=curr_bal+? WHERE agent_id=?");
                    stat3.setFloat(1, newAmount);
                    stat3.setString(2, agentid);
                    stat3.executeUpdate();
                    stat4 = conn.prepareStatement("UPDATE am_update_balance SET process_exe=true, update_date=current_timestamp, before_balance=?, current_balance=amount+? WHERE topup_id=?");
                    stat4.setFloat(1, saldoBefore);
                    stat4.setFloat(2, saldoBefore);
                    stat4.setInt(3, Integer.parseInt(topupid));
                    stat4.executeUpdate();

                    stat5 = conn.prepareStatement("SELECT curr_bal FROM am_balance where agent_id=?");
                    stat5.setString(1, agentid);
                    rs3 = stat5.executeQuery();
                    while (rs3.next()) {
                        currbal = rs3.getInt("curr_bal");
                    }
                    log.info("");
                    log.info("============== APPROVE TOPUP AGENT : " + agentid + ", last balance :" + saldoBefore + ", amount :" + newAmount + ", current balance :" + currbal + ", Approve By :" + username + " =================");
                    log.info("");
//                    System.out.println("topup agent :"+ agentid + ", last balance :"+saldoBefore+", amount :"+newAmount+", current balance :"+currbal+", Approve By :"+ username);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            clearStatment(stat4);
            clearStatment(stat5);
            clearStatment(stat3);
            clearResultset(rs3);
            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return true;
    }

    public boolean updateVerifikasi(String agentid) {
        System.out.println("masuk database nih");
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update am_user set verified=1 where agent_id=?");
            stat.setString(1, agentid);
            stat.executeUpdate();
            stat.close();
            stat = conn.prepareStatement("update am_balance set max_curr_bal = 10000000 where agent_id=?");
            stat.setString(1, agentid);
            stat.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return true;
    }

    public ArrayList<Topupagent> getAllTopupagent() throws ParseException {
        ArrayList<Topupagent> topupagentList = new ArrayList<Topupagent>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
//            conn = DatasourceEntryBackend.getInstance().getBackendDS().getConnection();
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT b.agent_name, a.agent_id, a.before_balance, a.current_balance, a.amount, a.bank_name, a.acct_no, a.virt_no, a.no_ref, a.transfer_date, a.process_exe, a.topup_date, a.topup_id FROM am_update_balance a inner join am_user b on a.agent_id = b.agent_id order by a.topup_date DESC");
            rs = stat.executeQuery();
            while (rs.next()) {
                Topupagent topupagent = new Topupagent();
                Date requesttime = requesttime1.parse(rs.getString("topup_date"));
                topupagent.setTopup_date(requesttime2.format(requesttime));
                topupagent.setAgent_name(rs.getString("agent_name"));
                topupagent.setAgent_id(rs.getString("agent_id"));
                topupagent.setAmount(rs.getString("amount"));
                if (rs.getString("before_balance") != null) {
                    topupagent.setBefore_balance(rs.getString("before_balance"));
                } else {
                    topupagent.setBefore_balance("-");
                }
                if (rs.getString("current_balance") != null) {
                    topupagent.setCurrent_balance(rs.getString("current_balance"));
                } else {
                    topupagent.setCurrent_balance("-");
                }
                topupagent.setBank_name(rs.getString("bank_name"));
                if (rs.getString("acct_no") != null) {
                    topupagent.setAcct_no(rs.getString("acct_no"));
                } else {
                    topupagent.setAcct_no("-");
                }
                if (rs.getString("transfer_date") != null) {
                    topupagent.setTransfer_date(rs.getString("transfer_date"));
                } else {
                    topupagent.setTransfer_date("-");
                }
                if (rs.getString("virt_no") != null) {
                    topupagent.setVirt_no(rs.getString("virt_no"));
                } else {
                    topupagent.setVirt_no("-");
                }
                if (rs.getString("no_ref") != null) {
                    topupagent.setNo_ref(rs.getString("no_ref"));
                } else {
                    topupagent.setNo_ref("-");
                }
                topupagent.setTopup_id(rs.getString("topup_id"));
                topupagent.setProcess_exe(rs.getString("process_exe"));
                topupagentList.add(topupagent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return topupagentList;
    }

    public ArrayList<Reportyabes> getAlltransactionyabes() throws ParseException {
        ArrayList<Reportyabes> listTransaction = new ArrayList<Reportyabes>();
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH);
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        PreparedStatement stat3 = null;
        PreparedStatement stat4 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from "
                    + "(select msgid, proccode, statusreply, requesttime, responsetime, noref, billercode, amount, productcode, fromagent, rcinternal, custno, hargajual, hargabeli, feejual, feebeli, prev_bal, curr_bal "
                    + "from tempmsg union all "
                    + "select msgid, proccode, statusreply, requesttime, responsetime, noref, billercode, amount, productcode, fromagent, rcinternal, custno, hargajual, hargabeli, feejual, feebeli, prev_bal, curr_bal "
                    + "from tempmsg_backup order by msgid) "
                    + "AS tempmsg where proccode='280000' OR proccode='290000'");
            rs1 = stat1.executeQuery();
//            ResultSet resultSet1 = stat1.executeQuery("select * from tempmsg where proccode='280000' OR proccode='290000'");
            while (rs1.next()) {
                Reportyabes reportyabes = new Reportyabes();
                stat2 = conn.prepareStatement("select * from am_user where agent_id='" + rs1.getString("fromagent") + "'");
                rs2 = stat2.executeQuery();
                while (rs2.next()) {
                    stat3 = conn.prepareStatement("select * from socketconn where billercode ='" + rs1.getString("billercode") + "'");
                    rs3 = stat3.executeQuery();
                    if (rs2.getString("agent_id") == null) {
                        reportyabes.setAgentid("-");
                    } else {
                        reportyabes.setAgentid(rs2.getString("agent_id"));
                    }
                    if (rs2.getString("agent_name") == null) {
                        reportyabes.setAgentname("-");
                    } else {
                        reportyabes.setAgentname(rs2.getString("agent_name"));
                    }
                    while (rs3.next()) {
                        stat4 = conn.prepareStatement("select * from trancode where trancodeid='" + rs1.getString("productcode") + "'");
                        rs4 = stat4.executeQuery();
                        if (rs3.getString("conname") == null) {
                            reportyabes.setBillername("-");
                        } else {
                            reportyabes.setBillername(rs3.getString("conname"));
                        }
                        while (rs4.next()) {
                            if (rs4.getString("trancodename") == null) {
                                reportyabes.setNameproduct("-");
                            } else {
                                reportyabes.setNameproduct(rs4.getString("trancodename"));
                            }
                        }
                    }
                }
                if (rs1.getString("noref") == null) {
                    reportyabes.setNoref("-");
                } else {
                    reportyabes.setNoref(rs1.getString("noref"));
                }
                if (rs1.getString("custno") == null) {
                    reportyabes.setCustno("-");
                } else {
                    reportyabes.setCustno(rs1.getString("custno"));
                }
                if (rs1.getString("requesttime") == null) {
                    reportyabes.setRequesttime("-");
                } else {
                    Date requesttime = requesttime1.parse(rs1.getString("requesttime"));
                    reportyabes.setRequesttime(requesttime2.format(requesttime));
                }
                if (rs1.getString("rcinternal") == null) {
                    reportyabes.setRcinternal("-");
                } else {
                    reportyabes.setRcinternal(rs1.getString("rcinternal"));
                }
                if (null == rs1.getString("rcinternal")) {
                    reportyabes.setStatus("-");
                } else switch (rs1.getString("rcinternal")) {
                    case "0000":
                        reportyabes.setStatus("success");
                        break;
                    case "0020":
                        reportyabes.setStatus("error");
                        break;
                    case "0068":
                        reportyabes.setStatus("timeout");
                        break;
                    default:
                        reportyabes.setStatus("error transaction");
                        break;
                }
                if (rs1.getString("statusreply") == null) {
                    reportyabes.setStatusreply("-");
                } else {
                    reportyabes.setStatusreply(rs1.getString("statusreply"));
                }
                if (rs1.getString("amount") == null) {
                    reportyabes.setAmount("-");
                } else {
                    reportyabes.setAmount(rs1.getString("amount"));
                }
                if (rs1.getString("prev_bal") == null) {
                    reportyabes.setPrev_bal("-");
                } else {
                    reportyabes.setPrev_bal(rs1.getString("prev_bal"));
                }
                if (rs1.getString("curr_bal") == null) {
                    reportyabes.setCurr_bal("-");
                } else {
                    reportyabes.setCurr_bal(rs1.getString("curr_bal"));
                }
                listTransaction.add(reportyabes);
            }
//            rs1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
clearStatment(stat4);
            clearResultset(rs4);
            clearStatment(stat3);
            clearResultset(rs3);
            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
            clearDBConnection(conn);
        }
        return listTransaction;
    }

    public String deleteAgent(String agent_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("DELETE FROM am_user WHERE agent_id=?");
            stat.setString(1, agent_id);
            stat.executeUpdate();
            System.out.println("coba bray   :" + agent_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menghapus agent";
    }

    public Agentyabes getAgentByAgentid(String agent_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Agentyabes agentyabes = new Agentyabes();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from am_user WHERE agent_id=?");
            stat.setString(1, agent_id);
            rs = stat.executeQuery();

            while (rs.next()) {
                agentyabes.setAgent_id(rs.getString("agent_id"));
                agentyabes.setAgent_name(rs.getString("agent_name"));
                agentyabes.setAgent_address(rs.getString("address"));
                agentyabes.setPhonenumber(rs.getString("phonenumber"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return agentyabes;
    }

    public String updateAgent(Agentyabes Agentyabes) {
        Connection conn = null;
        PreparedStatement stat = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE am_user SET agent_id=?, agent_name=?, address=?, phonenumber=?, password=?, agent_pin=? WHERE agent_id=?");
            stat.setString(1, Agentyabes.getAgent_id());
            stat.setString(2, Agentyabes.getAgent_name());
            stat.setString(3, Agentyabes.getAgent_address());
            stat.setString(4, Agentyabes.getAgent_phone());
            stat.setString(5, Agentyabes.getAgent_pass());
            stat.setString(6, Agentyabes.getAgent_pin());
            stat.setString(7, Agentyabes.getAgent_id());
            stat.executeUpdate();
            stat.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat);
        }
        return status = "Sukses mengupdate Agent";
    }

}
