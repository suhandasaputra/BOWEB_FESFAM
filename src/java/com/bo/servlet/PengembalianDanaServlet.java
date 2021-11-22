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
 * @author suhanda
 */
public class PengembalianDanaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
//    private static String DORMAN = "dorman.jsp";
//    private static String UPDATE = "update_agent.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (action.equalsIgnoreCase("pengembalian")) {
            req.put(FieldParameter.procCode, ProcessingCode.pengembalian);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.rrn, request.getParameter("rrn"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction_ppob.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/pengembalian_dana.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Agent Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/pengembalian_dana.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("timeoutsukses")) {
            req.put(FieldParameter.procCode, ProcessingCode.timeoutsukses);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.rrn, request.getParameter("rrn"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction_ppob.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/pengembalian_timeout.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Agent Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/pengembalian_timeout.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        }  else if (action.equalsIgnoreCase("timeoutgagal")) {
            req.put(FieldParameter.procCode, ProcessingCode.timeoutgagal);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.rrn, request.getParameter("rrn"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_transaction_ppob.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/pengembalian_timeout_gagal.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Agent Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/pengembalian_timeout_gagal.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        }
    }
}
