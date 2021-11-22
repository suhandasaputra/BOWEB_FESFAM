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
public class UpgradeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

        if (action.equalsIgnoreCase("optionagent")) {
            req.put(FieldParameter.procCode, ProcessingCode.optionagentnew);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listConnection));
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

        if (action.equalsIgnoreCase("check")) {
            req.put(FieldParameter.procCode, ProcessingCode.upgrade);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.agent_id, request.getParameter("agent_id"));
            req.put(FieldParameter.nama_koperasi, request.getParameter("nama_koperasi"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                session.setAttribute("agent_id", resp.get(RuleNameParameter.agent_id).toString());
                session.setAttribute("agent_name", resp.get(FieldParameter.agent_name).toString());
                session.setAttribute("userlevel2", resp.get(FieldParameter.userlevel).toString());
                session.setAttribute("reference_id", resp.get(FieldParameter.reference_id).toString());
                session.setAttribute("nama_koperasi", resp.get(FieldParameter.nama_koperasi).toString());

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Upgrade");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i> " + resp.get(RuleNameParameter.resp_desc) + " \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Upgrade");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> " + resp.get(RuleNameParameter.resp_desc) + " \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Upgrade");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> " + resp.get(RuleNameParameter.resp_desc) + " \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("GoToMember")) {
            req.put(FieldParameter.procCode, ProcessingCode.GoToMember);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.agent_id_user, request.getParameter("agent_id_user"));
            req.put(FieldParameter.agent_id, request.getParameter("agent_id"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                session.setAttribute("agent_id_user", resp.get(FieldParameter.agent_id_user).toString());
                session.setAttribute("agent_name", resp.get(FieldParameter.agent_name).toString());
                session.setAttribute("reference_id", resp.get(FieldParameter.agent_id).toString());

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Member");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i> " + resp.get(RuleNameParameter.resp_desc) + " \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Member");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> " + resp.get(RuleNameParameter.resp_desc) + " \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Member");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> " + resp.get(RuleNameParameter.resp_desc) + " \n"
                        + "</div>");
                rd.include(request, response);
            }
        }
    }
}
