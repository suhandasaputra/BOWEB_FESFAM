/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

//import com.boa.dao.BillerProduct;
//import com.boa.dao.UserYabes;
import com.boa.model.Billerproduct;
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
public class CrudBillerproductServlet extends HttpServlet {
    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private BillerProduct dao;
//    private UserYabes dao1;
    private static String INSERT = "add_product_biller.jsp";
    private static String UPDATE = "update_product_biller.jsp";

    public CrudBillerproductServlet() {
//        dao = new BillerProduct();
//        dao1 = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            String tcbiller = request.getParameter("tcBiller");
            String billercode = request.getParameter("billerCode");
            String status = dp.deleteBillerproduct(tcbiller, billercode);
            if (status.equals("Sukses menghapus biller product")) {
                HttpSession session = request.getSession(true);
//                session.setMaxInactiveInterval(60);
                String username = session.getAttribute("userlogin").toString();
                String activitas = "menghapus biller product " + request.getParameter("tcBiller");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_biller_product.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_biller_product.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            String tcbiller = request.getParameter("tcBiller");
            String billercode = request.getParameter("billerCode");
            Billerproduct billerproduct = dp.getBillerByTcbiller(tcbiller, billercode);
            request.setAttribute("billerproduct", billerproduct);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Billerproduct billerproduct = new Billerproduct();
        billerproduct.setBillercode(request.getParameter("billerCode"));
        billerproduct.setTcbiller(request.getParameter("tcBiller"));
        billerproduct.setFeebeli(request.getParameter("feeBeli"));
        billerproduct.setHargabeli(request.getParameter("hargaBeli"));
        billerproduct.setTrancodeid(request.getParameter("trancodeId"));
        String status = dp.updateBillerproduct(billerproduct);
        if (status.equals("Sukses mengupdate biller product")) {
            HttpSession session = request.getSession(true);
            String username = session.getAttribute("userlogin").toString();
            String activitas = "mengupdate biller product " + request.getParameter("tcBiller");
            dp.userYabes(username, activitas);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_biller_product.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_product_biller.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        }
    }
}
