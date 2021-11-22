/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

//import com.boa.dao.LoginProcess;
//import com.boa.dao.UserYabes;
import com.boa.model.Userlogin;
import com.boa.util.DatabaseProcess;
import com.boa.function.SHA256Enc;
import java.io.IOException;
import java.io.PrintWriter;
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
public class LoginUDServlet extends HttpServlet {
DatabaseProcess dp = new DatabaseProcess();
    private static final long serialVersionUID = 1L;
//    private LoginProcess dao;
//    private UserYabes dao1;
    private static String INSERT = "add_user.jsp";
    private static String UPDATE = "update_user.jsp";

    public LoginUDServlet() {
//        dao = new LoginProcess();
//        dao1 = new UserYabes();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        

        if (action.equalsIgnoreCase("delete")) {
            String userLogin = request.getParameter("userLogin");
            String status = dp.deleteUserlogin(userLogin);
            if (status.equals("0000")) {
                HttpSession session = request.getSession(true);
//                session.setMaxInactiveInterval(60);
                String username = session.getAttribute("userlogin").toString();
                String activitas = "menghapus user login " + request.getParameter("userLogin");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_user.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>delete data success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_user.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>delete data failed\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            String userLogin = request.getParameter("userLogin");
            Userlogin userlogin = dp.getUserByUserlogin(userLogin);
            request.setAttribute("userlogin", userlogin);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(request.getParameter("username"));
        userlogin.setUserpass(SHA256Enc.encryptProc(request.getParameter("userpass")));
        userlogin.setUserlogin(request.getParameter("userlogin"));
        String status = dp.updateUserlogin(userlogin);
        if (status.equals("Sukses")) {
            HttpSession session = request.getSession(true);
            String username = session.getAttribute("userlogin").toString();
            String activitas = "mengupdate user login " + request.getParameter("userlogin");
            dp.userYabes(username, activitas);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_user.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_user.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        }
    }

}
