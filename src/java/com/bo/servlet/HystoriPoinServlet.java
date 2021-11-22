/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.model.Poin;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
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
public class HystoriPoinServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
    private static String UPDATE = "update_poin_redeem.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("listpoin")) {
            ArrayList<Poin> allpoin = new ArrayList<Poin>();
            allpoin = dp.getAllPoin();
            //indikasi berubah sort
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(allpoin, new TypeToken<List<Poin>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("listpoinredeem")) {
            ArrayList<Poin> allpoinredeem = new ArrayList<Poin>();
            allpoinredeem = dp.getAllPoinRedeem();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(allpoinredeem, new TypeToken<List<Poin>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("listpoinredeemcs")) {
            ArrayList<Poin> allpoinredeem = new ArrayList<Poin>();
            allpoinredeem = dp.getAllPoinRedeem();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(allpoinredeem, new TypeToken<List<Poin>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("delete")) {
            String redeem_itemid = request.getParameter("redeem_itemid");
            String status = dp.deletePoinRedeem(redeem_itemid);
            if (status.equals("0000")) {
                HttpSession session = request.getSession(true);
                String username = session.getAttribute("userlogin").toString();
                String activitas = "menghapus Poin Redeem " + request.getParameter("redeem_itemid");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_poin_redeem.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_poin_redeem.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            String redeem_itemid = request.getParameter("redeem_itemid");
            Poin poin = dp.getPoinByRedeem_itemid(redeem_itemid);
            request.setAttribute("poin", poin);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("optiontrancodename")) {
            ArrayList<Poin> allpoin = new ArrayList<Poin>();
            allpoin = dp.getAllproductName();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(allpoin, new TypeToken<List<Poin>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("updatepoin")) {

            Poin poin = new Poin();
            poin.setRedeem_itemid(request.getParameter("redeem_itemid"));
            poin.setItemname(request.getParameter("itemname"));
            poin.setPrice(request.getParameter("price"));
            poin.setStart_date(request.getParameter("start_date"));
            poin.setEnd_date(request.getParameter("end_date"));
//            poin.setTrancodeid(request.getParameter("trancodeid"));

            String status = dp.updateRedeemPoin(poin);
            if (status.equals("0000")) {
                HttpSession session = request.getSession(true);
                String username = session.getAttribute("userlogin").toString();
                String activitas = "mengupdate redeem poin " + request.getParameter("redeem_itemid");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_poin_redeem.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_poin_redeem.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("addpoinredeem")) {
            Poin poin = new Poin();
            poin.setRedeem_itemid(request.getParameter("redeem_itemid"));
            poin.setItemname(request.getParameter("itemname"));
            poin.setPrice(request.getParameter("price"));
            poin.setStock(request.getParameter("stock"));
            poin.setStart_date(request.getParameter("start_date"));
            poin.setEnd_date(request.getParameter("end_date"));
            poin.setOtherparam("INTERNAL");
            poin.setTrancodeid(request.getParameter("itemname"));

//            poin.setRequested(request.getParameter("requested"));
//            poin.setApproved(request.getParameter("approved"));
            String status = dp.addPoinRedeem(poin);
            if (status.equalsIgnoreCase("0000")) {
                HttpSession session = request.getSession(true);
                String username = session.getAttribute("userlogin").toString();
                String activitas = "menambahkan poin redeem " + request.getParameter("redeem_itemid");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_poin_redeem.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equalsIgnoreCase("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_poin_redeem.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_poin_redeem.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
                        + "</div>");
                rd.include(request, response);
            }
        }
    }
}
