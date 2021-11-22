/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;


import com.boa.model.Connectionbiller;
import com.boa.util.DatabaseProcess;
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
public class CrudConnectionbillerServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();
    private static final long serialVersionUID = 1L;
    private static String INSERT = "add_connection_biller.jsp";
    private static String UPDATE = "update_connection_biller.jsp";

    public CrudConnectionbillerServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String forward = "";
//        String action = request.getParameter("action");
//
//        if (action.equalsIgnoreCase("delete")) {
//            String billercode = request.getParameter("billerCode");
//            String status = dp.deleteConnectionBiller(billercode);
//            if (status.equals("Sukses menghapus connection biller")) {
//                HttpSession session = request.getSession(true);
////                session.setMaxInactiveInterval(60);
//                String username = session.getAttribute("userlogin").toString();
//                String activitas = "menghapus connection biller " + request.getParameter("billerCode");
//                dp.userYabes(username, activitas);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/connection_biller.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-success status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/connection_biller.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
//                        + "</div>");
//                rd.include(request, response);
//            }
//        } else if (action.equalsIgnoreCase("update")) {
//            forward = UPDATE;
//            String billercode = request.getParameter("billerCode");
//            Connectionbiller connectionbiller = dp.getConnectionByBillercode(billercode);
//            request.setAttribute("connectionbiller", connectionbiller);
//            RequestDispatcher view = request.getRequestDispatcher(forward);
//            view.forward(request, response);
//        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Connectionbiller connectionbiller = new Connectionbiller();
//        connectionbiller.setBillername(request.getParameter("billerName"));
//        connectionbiller.setBillercode(request.getParameter("billerCode"));
//        connectionbiller.setPackagename(request.getParameter("packageName"));
//        connectionbiller.setUrlhost(request.getParameter("urlHost"));
//        String status = dp.updateConnectionBiller(connectionbiller);
//        if (status.equals("Sukses mengupdate connection biller")) {
//            HttpSession session = request.getSession(true);
//            String username = session.getAttribute("userlogin").toString();
//            String activitas = "mengupdate connection biller " + request.getParameter("billerName");
//            dp.userYabes(username, activitas);
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/connection_biller.jsp");
//            PrintWriter out = response.getWriter();
//            out.println("<div class=\"alert alert-success status-custom\">\n"
//                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
//                    + "</div>");
//            rd.include(request, response);
//        } else {
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_connection_biller.jsp");
//            PrintWriter out = response.getWriter();
//            out.println("<div class=\"alert alert-danger status-custom\">\n"
//                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
//                    + "</div>");
//            rd.include(request, response);
//        }






    }
}
