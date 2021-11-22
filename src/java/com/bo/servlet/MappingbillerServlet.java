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
//import com.boa.dao.MappingBiller;
//import com.boa.dao.UserYabes;
import com.boa.model.Mappingbiller;
//import com.boa.util.DBUtil;
import com.boa.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class MappingbillerServlet extends HttpServlet {
    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private MappingBiller dao;
//    private UserYabes dao1;
    private static String UPDATE = "update_mapping_biller.jsp";

    public MappingbillerServlet() {
//        dao = new MappingBiller();
//        dao1 = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("listmappingbiller")) {
            ArrayList<Mappingbiller> allmappingbiller = new ArrayList<Mappingbiller>();
            allmappingbiller = dp.getMappingbiller();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(allmappingbiller, new TypeToken<List<Mappingbiller>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            String trancodeid = request.getParameter("trancodeId");
            Mappingbiller mappingbiller = dp.getMappingbillerByTrancodeid(trancodeid);
            request.setAttribute("mappingbiller", mappingbiller);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("optionbiller")) {
            ArrayList<Mappingbiller> allmappingagent = new ArrayList<Mappingbiller>();
            allmappingagent = dp.getAllbillerMapping();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(allmappingagent, new TypeToken<List<Mappingbiller>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Mappingbiller mappingbiller = new Mappingbiller();
        mappingbiller.setBillercode(request.getParameter("billerCode"));
        mappingbiller.setTrancodeid(request.getParameter("trancodeId"));
        String status = dp.updateMappingBiller(mappingbiller);
        if (status.equals("Sukses mengupdate mapping biller")) {
            HttpSession session = request.getSession(true);
//            session.setMaxInactiveInterval(60);
            String username = session.getAttribute("userlogin").toString();
            String activitas = "mengupdate mapping biller " + request.getParameter("trancodeId");
            dp.userYabes(username, activitas);            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_biller.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_mapping_biller.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        }
    }
}
