/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.parameter.StaticParameter;
import com.boa.function.MessageProcess;
import com.boa.function.SendHttpProcess;
import com.boa.function.impl.MessageProcessImpl;
import com.boa.parameter.FieldParameter;
import com.boa.parameter.ProcessingCode;
import com.boa.parameter.RuleNameParameter;
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
public class AgentlimitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String UPDATE = "update_limit.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String forward = "";
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        SendHttpProcess http = new SendHttpProcess();
        MessageProcess mp = (MessageProcess) new MessageProcessImpl();
        HashMap req = new HashMap();

        if (action.equalsIgnoreCase("Listlimit")) {
            req.put(FieldParameter.procCode, ProcessingCode.listAgentLimitBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            String reqMsg = mp.encryptMessage(req);
            String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            HashMap resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listAgentLimit));
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            req.put(FieldParameter.procCode, ProcessingCode.getAgentLimitByAgentidBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agentId"));
            String reqMsg = mp.encryptMessage(req);
            String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            HashMap resp = mp.decryptMessage(responseWeb);
            request.setAttribute("agentlimit", resp.get(FieldParameter.agentLimit));
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
//        else if(action.equalsIgnoreCase("ListLimitcs")){
//            req.put(FieldParameter.procCode, ProcessingCode.listAgentLimitBackend);
//            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
//            String reqMsg = mp.encryptMessage(req);
//            String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
//            HashMap resp = mp.decryptMessage(responseWeb);
//            response.getWriter().print(resp.get(FieldParameter.listAgentLimit));
//        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        SendHttpProcess http = new SendHttpProcess();
        String action = request.getParameter("action");
        MessageProcess mp = (MessageProcess) new MessageProcessImpl();
        HttpSession session = request.getSession(true);
        String reqMsg = "";
        String responseWeb = "";
        HashMap req = new HashMap();
        HashMap resp = new HashMap();
        if (action.equalsIgnoreCase("updatelimit")) {
            req.put(FieldParameter.userlogin, session.getAttribute(FieldParameter.userlogin));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            req.put(RuleNameParameter.max_bal_day, request.getParameter("max_bal_day"));
            req.put(RuleNameParameter.max_bal_month, request.getParameter("max_bal_month"));
            req.put(RuleNameParameter.max_item_day, request.getParameter("max_item_day"));
            req.put(RuleNameParameter.max_item_month, request.getParameter("max_item_month"));
            req.put(RuleNameParameter.max_curr_bal, request.getParameter("max_curr_bal"));
            req.put(FieldParameter.procCode, ProcessingCode.updateAgentLimit);

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
//            System.out.println("ini adalah responnya :"+resp);
            if (status.equals("0000")) {
                session = request.getSession(true);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_limit.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_limit.jsp");
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
