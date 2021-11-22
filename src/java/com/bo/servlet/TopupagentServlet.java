/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
//import com.boa.dao.TopupAgent;
//import com.boa.dao.UserYabes;
import com.boa.model.Topupagent;
import com.boa.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class TopupagentServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private TopupAgent dao;
//    private UserYabes dao1;
    private static String INSERT = "add_tpsaldo_biller.jsp";
//        private static String INSERT = "add_tpsaldo_agent.jsp";

    public TopupagentServlet() {
//        dao = new TopupAgent();
//        dao1 = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("Listtopupagent")) {
            ArrayList<Topupagent> alltopupagent = new ArrayList<Topupagent>();
            try {
                alltopupagent = dp.getAllTopupagent();
            } catch (ParseException ex) {
                Logger.getLogger(TopupagentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            //indikasi berubah sort
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(alltopupagent, new TypeToken<List<Topupagent>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();

//            for (int i = 0; i < jsonArray.size(); i++) {
//                System.out.println("JsonArray : " + jsonArray.get(i));
//            }
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("Listtopupagentcs")) {
            ArrayList<Topupagent> alltopupagent = new ArrayList<Topupagent>();
            try {
                alltopupagent = dp.getAllTopupagent();
            } catch (ParseException ex) {
                Logger.getLogger(TopupagentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            //indikasi berubah sort
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(alltopupagent, new TypeToken<List<Topupagent>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();

//            for (int i = 0; i < jsonArray.size(); i++) {
//                System.out.println("JsonArray : " + jsonArray.get(i));
//            }
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("update")) {
            String agentid = request.getParameter("agentId");
            String topupid = request.getParameter("topupId");

            HttpSession session = request.getSession(true);
            String username = session.getAttribute("userlogin").toString();

            if (dp.updateTopupagent(agentid, topupid, username)) {
                String activitas = "mengapprove topup agent " + agentid;
                dp.userYabes(username, activitas);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_tpsaldo_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>update data success\n"
                        + "</div>");
                agentid = "";
                topupid = "";

//                rd.include(request, response);
                response.sendRedirect("list_tpsaldo_agent.jsp");
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_tpsaldo_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>please correct your input\n"
                        + "</div>");
                rd.include(request, response);
            }

        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("lkd")) {
            Topupagent topagent = new Topupagent();
            topagent.setCu_id(request.getParameter("cu_id"));
            topagent.setAgent_id(request.getParameter("agentId"));
            topagent.setAmount(request.getParameter("amount"));
            topagent.setBank_name(request.getParameter("bankName"));
            topagent.setAcct_no(request.getParameter("acctNo"));
            topagent.setTransfer_date(request.getParameter("transferDate"));
            HttpSession session = request.getSession(true);
            String username = session.getAttribute("userlogin").toString();
            String status = dp.addTopupagent(topagent, username);
            if (status.equals("Success , Waiting Approve")) {

                String activitas = "menambahkan topup lkd " + request.getParameter("agentId") + " " + request.getParameter("amount");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_tpsaldo_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");

                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_tpsaldo_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("debet")) {
            Topupagent topagent = new Topupagent();
            topagent.setCu_id(request.getParameter("cu_id"));
            topagent.setAmount(request.getParameter("amount"));
            topagent.setBank_name(request.getParameter("bankName"));
            topagent.setAcct_no(request.getParameter("acctNo"));
            topagent.setRrn(request.getParameter("rrn"));

            topagent.setTransfer_date(request.getParameter("transferDate"));
            HttpSession session = request.getSession(true);
            String username = session.getAttribute("userlogin").toString();
            String status = dp.debetagent(topagent, username);
            if (status.equals("0000")) {

                String activitas = "mendebet koperasi " + request.getParameter("cu_id") + " " + request.getParameter("amount");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction_ppob.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");

                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction_ppob.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        }
    }
}
