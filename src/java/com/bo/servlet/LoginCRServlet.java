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
import com.boa.model.Userlogin;
import com.boa.util.DatabaseProcess;
import com.boa.function.SHA256Enc;
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
public class LoginCRServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
//    private LoginProcess dao;
//    private UserYabes dao1;
    private static String INSERT = "add_user.jsp";

    public LoginCRServlet() {
//        dao = new LoginProcess();
//        dao1 = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("listuser")) {
            ArrayList<Userlogin> alluser = new ArrayList<Userlogin>();
            DatabaseProcess dp = new DatabaseProcess();
            alluser = dp.getAlluser();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(alluser, new TypeToken<List<Userlogin>>() {
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
        DatabaseProcess dp = new DatabaseProcess();
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(request.getParameter("userName"));
        userlogin.setUserpass(SHA256Enc.encryptProc(request.getParameter("userPass")));
        userlogin.setUserlogin(request.getParameter("userLogin"));
        String status = dp.addUser(userlogin);
        if (status.equals("Sukses menambahkan user ppob")) {
            HttpSession session = request.getSession(true);
//            session.setMaxInactiveInterval(60);
            String username = session.getAttribute("userlogin").toString();
            String activitas = "menambahkan user login " + request.getParameter("userLogin");
            dp.userYabes(username, activitas);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_user.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_user.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        }
    }
}
