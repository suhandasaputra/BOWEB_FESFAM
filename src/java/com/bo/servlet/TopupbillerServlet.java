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
//import com.boa.dao.TopupBiller;
//import com.boa.dao.UserYabes;
import com.boa.model.Topupbiller;
import com.boa.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
public class TopupbillerServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private TopupBiller dao;
//    private UserYabes dao1;
    private static String INSERT = "add_tpsaldo_biller.jsp";

    public TopupbillerServlet() {
//        dao = new TopupBiller();
//        dao1 = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("Listtopupbiller")) {
            ArrayList<Topupbiller> alltopupbiller = new ArrayList<Topupbiller>();
            alltopupbiller = dp.getAllTopupbiller();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(alltopupbiller, new TypeToken<List<Topupbiller>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("Listtopupbillercs")) {

            ArrayList<Topupbiller> alltopupbiller = new ArrayList<Topupbiller>();
            alltopupbiller = dp.getAllTopupbiller();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(alltopupbiller, new TypeToken<List<Topupbiller>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Topupbiller topupbiller = new Topupbiller();
        topupbiller.setBillercode(request.getParameter("billerCode"));
        topupbiller.setAmount(request.getParameter("amount"));
        topupbiller.setBank_name(request.getParameter("bankName"));
        topupbiller.setAcct_no(request.getParameter("acctNo"));
        topupbiller.setTransfer_date(request.getParameter("transferDate"));
        HttpSession session = request.getSession(true);
        String username = session.getAttribute("userlogin").toString();
        String status = dp.addTopupBiller(topupbiller, username);
        if (status.equals("Success Topup Biller")) {
//            HttpSession session = request.getSession(true);
//            session.setMaxInactiveInterval(60);
//            String username = session.getAttribute("username").toString();
            String activitas = "menambahkan topup biller " + request.getParameter("billerCode");
            dp.userYabes(username, activitas);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_tpsaldo_biller.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_tpsaldo_biller.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        }
    }
}
