/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.function.MessageProcess;
import com.boa.function.SendHttpProcess;
import com.boa.function.impl.MessageProcessImpl;
import com.boa.parameter.FieldParameter;
import com.boa.parameter.ProcessingCode;
import com.boa.parameter.RuleNameParameter;
import com.boa.model.Transactiontrancode;
import com.boa.parameter.StaticParameter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
public class TransactionServlet extends HttpServlet {

//    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private TransactionTrancode dao;
//    private UserYabes dao1;
    private static String INSERT = "add_transaction.jsp";

    public TransactionServlet() {
//        dao = new TransactionTrancode();
//        dao1 = new UserYabes();
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String forward = "";
//        String action = request.getParameter("action");
//
//        if (action.equalsIgnoreCase("listtransaction")) {
//            ArrayList<Transactiontrancode> alltransaction = new ArrayList<Transactiontrancode>();
//            alltransaction = dp.getAlltransactions();
//            Gson gson = new Gson();
//            JsonElement element = gson.toJsonTree(alltransaction, new TypeToken<List<Transactiontrancode>>() {
//            }.getType());
//
//            JsonArray jsonArray = element.getAsJsonArray();
//            response.setContentType("application/json");
//            response.getWriter().print(jsonArray);
//        } else if (action.equalsIgnoreCase("listtransactioncs")) {
//
//            ArrayList<Transactiontrancode> alltransaction = new ArrayList<Transactiontrancode>();
//            alltransaction = dp.getAlltransactions();
//            Gson gson = new Gson();
//            JsonElement element = gson.toJsonTree(alltransaction, new TypeToken<List<Transactiontrancode>>() {
//            }.getType());
//
//            JsonArray jsonArray = element.getAsJsonArray();
//            response.setContentType("application/json");
//            response.getWriter().print(jsonArray);
//
//        } else if (action.equalsIgnoreCase("insert")) {
//            forward = INSERT;
//            RequestDispatcher view = request.getRequestDispatcher(forward);
//            view.forward(request, response);
//        }
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Transactiontrancode transaction = new Transactiontrancode();
//        transaction.setTctype(request.getParameter("transactionType"));
//        transaction.setTrancodeid(request.getParameter("transactionId"));
//        transaction.setTrancodename(request.getParameter("transactionName"));
//        transaction.setBillercode(request.getParameter("billercode"));
//        String status = dp.addTransaction(transaction);
//        if (status.equalsIgnoreCase("Sukses menambahkan transaction")) {
//            HttpSession session = request.getSession(true);
////            session.setMaxInactiveInterval(60);
//            String username = session.getAttribute("userlogin").toString();
////                        String username = session.getAttribute("username").toString();
//
//            String activitas = "menambahkan transaction " + request.getParameter("transactionName");
//            dp.userYabes(username, activitas);
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction.jsp");
//            PrintWriter out = response.getWriter();
//            out.println("<div class=\"alert alert-success status-custom\">\n"
//                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                    + "     <i class=\"icon fa fa-check\"></i>Sukses menambahkan transaction\n"
//                    + "</div>");
//            rd.include(request, response);
//        } else if (status.equalsIgnoreCase("Maaf id transaction sudah ada")) {
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_transaction.jsp");
//            PrintWriter out = response.getWriter();
//            out.println("<div class=\"alert alert-danger status-custom\">\n"
//                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                    + "     <i class=\"icon fa fa-warning\"></i>Maaf id transaction sudah ada\n"
//                    + "</div>");
//            rd.include(request, response);
//        } else {
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_transaction.jsp");
//            PrintWriter out = response.getWriter();
//            out.println("<div class=\"alert alert-danger status-custom\">\n"
//                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                    + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
//                    + "</div>");
//            rd.include(request, response);
//        }
//    }
    

    private static String UPDATE = "update_transaction.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProcess mp = new MessageProcessImpl();
        HttpSession session = request.getSession(true);
        SendHttpProcess http = new SendHttpProcess();
        HashMap req = new HashMap();
        HashMap resp = new HashMap();
        String reqMsg = "";
        String forward = "";
        String responseWeb = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("listproduk")) {
            req.put(FieldParameter.procCode, ProcessingCode.listproduk);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listproduk));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProcess mp = new MessageProcessImpl();
        HttpSession session = request.getSession(true);
        String forward = "";
        String reqMsg = "";
        String responseWeb = "";
        String action = request.getParameter("action");
        SendHttpProcess http = new SendHttpProcess();
        HashMap req = new HashMap();
        HashMap resp = new HashMap();
        if (action.equalsIgnoreCase("addproduk")) {
            req.put(FieldParameter.procCode, ProcessingCode.addproduk);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            
            req.put(FieldParameter.tctype, request.getParameter("tctype"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            req.put(FieldParameter.trancodename, request.getParameter("trancodename"));
            req.put(FieldParameter.billercode, request.getParameter("billercode"));
            
            req.put(FieldParameter.provider, request.getParameter("provider"));
            req.put(FieldParameter.category, request.getParameter("category"));
            req.put(FieldParameter.detailproduct, request.getParameter("detailproduct"));
            

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Agent Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            req.put(FieldParameter.procCode, ProcessingCode.getProdukById);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);

            Transactiontrancode trancode = new Transactiontrancode();
            trancode.setTrancodeid(resp.get(FieldParameter.trancodeid).toString());
            trancode.setTrancodename(resp.get(FieldParameter.trancodename).toString());
            trancode.setBillercode(resp.get(FieldParameter.billercode).toString());
            trancode.setTctype(resp.get(FieldParameter.tctype).toString());
            trancode.setProvider(resp.get(FieldParameter.provider).toString());
            trancode.setCategory(resp.get(FieldParameter.category).toString());
            trancode.setDetailproduct(resp.get(FieldParameter.detailproduct).toString());


            request.setAttribute("trancode", trancode);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("updateproduk")) {
            req.put(FieldParameter.procCode, ProcessingCode.updateproduk);
            req.put(FieldParameter.userlogin, session.getAttribute(FieldParameter.userlogin));
            
            req.put(FieldParameter.trancodeid, request.getParameter(FieldParameter.trancodeid));
            req.put(FieldParameter.trancodename, request.getParameter(FieldParameter.trancodename));
            req.put(FieldParameter.billercode, request.getParameter(FieldParameter.billercode));
            req.put(FieldParameter.tctype, request.getParameter(FieldParameter.tctype));
            
            req.put(FieldParameter.provider, request.getParameter(FieldParameter.provider));
            req.put(FieldParameter.category, request.getParameter(FieldParameter.category));
            req.put(FieldParameter.detailproduct, request.getParameter(FieldParameter.detailproduct));

            
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            req.put(FieldParameter.procCode, ProcessingCode.deleteproduk);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("active")) {
            req.put(FieldParameter.procCode, ProcessingCode.activeproduct);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/activation_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/activation_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("deactive")) {
            req.put(FieldParameter.procCode, ProcessingCode.deactiveproduct);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/activation_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/activation_transaction.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        }
    }
}