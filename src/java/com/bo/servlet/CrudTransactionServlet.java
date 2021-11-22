/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

//import com.boa.dao.TransactionTrancode;
//import com.boa.dao.UserYabes;
import com.boa.model.Billerproduct;
import com.boa.model.Transactiontrancode;
import com.boa.util.DatabaseProcess;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
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
public class CrudTransactionServlet extends HttpServlet {
    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private TransactionTrancode dao;
//    private UserYabes dao1;
    private static String INSERT = "add_transaction.jsp";
    private static String UPDATE = "update_transaction.jsp";

    public CrudTransactionServlet() {
//        dao = new TransactionTrancode();
//        dao1 = new UserYabes();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            String trancodeid = request.getParameter("transactionId");
            String status = dp.deleteTransaction(trancodeid);
            if (status.equals("Sukses menghapus transaction")) {
                HttpSession session = request.getSession(true);
//                session.setMaxInactiveInterval(60);
                String username = session.getAttribute("userlogin").toString();
                String activitas = "menghapus transaction " + request.getParameter("transactionId");
                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_user.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            String trancodeid = request.getParameter("transactionId");
            Transactiontrancode transactiontrancode = dp.getTransactionByTrancodeid(trancodeid);
            request.setAttribute("transactiontrancode", transactiontrancode);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }else if (action.equalsIgnoreCase("optionbiller")) {

            ArrayList<Billerproduct> allbillerproduct = new ArrayList<Billerproduct>();
            allbillerproduct = dp.getAllbillercode();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(allbillerproduct, new TypeToken<List<Billerproduct>>() {
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
        Transactiontrancode transaction = new Transactiontrancode();
        transaction.setTctype(request.getParameter("transactionType"));
        transaction.setTrancodeid(request.getParameter("transactionId"));
        transaction.setTrancodename(request.getParameter("transactionName"));
        transaction.setBillercode(request.getParameter("billercode"));
        String status = dp.updateTransaction(transaction);
        if (status.equals("Sukses mengupdate transaction")) {
            HttpSession session = request.getSession(true);
            String username = session.getAttribute("userlogin").toString();
            String activitas = "mengupdate transaction " + request.getParameter("transactionName");
            dp.userYabes(username, activitas);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_transaction.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                    + "</div>");
            rd.include(request, response);
        }
    }
}
