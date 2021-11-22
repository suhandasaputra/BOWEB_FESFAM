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
public class AgentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String DORMAN = "dorman.jsp";
    private static String UPDATE = "update_agent.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProcess mp = (MessageProcess) new MessageProcessImpl();
        HttpSession session = request.getSession(true);
        SendHttpProcess http = new SendHttpProcess();
        HashMap req = new HashMap();
        HashMap resp = new HashMap();
        String reqMsg = "";
        String forward = "";
        String responseWeb = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("Listagent")) {
            req.put(FieldParameter.procCode, ProcessingCode.listAgentBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listAgent));
        } else if (action.equalsIgnoreCase("verifikasi")) {
            req.put(FieldParameter.procCode, ProcessingCode.verifikasi);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listAgent));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProcess mp = (MessageProcess) new MessageProcessImpl();
        HttpSession session = request.getSession(true);
        String forward = "";
        String reqMsg = "";
        String responseWeb = "";
        String action = request.getParameter("action");
        SendHttpProcess http = new SendHttpProcess();
        HashMap req = new HashMap();
        HashMap resp = new HashMap();
        if (action.equalsIgnoreCase("addagent")) {
            req.put(FieldParameter.procCode, ProcessingCode.addAgentBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            req.put(RuleNameParameter.agent_name, request.getParameter("agent_name"));
            req.put(FieldParameter.phonenumber, request.getParameter("phonenumber"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Agent Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;

            System.out.println("update agent servlet : ");
            System.out.println("show data : " + request.getParameter("agent_id"));
            req.put(FieldParameter.procCode, ProcessingCode.getAgentByAgentidBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);

            System.out.println("ini Resp : " + resp);
            request.setAttribute("agentyabes", resp);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("updateagent")) {
            req.put(FieldParameter.procCode, ProcessingCode.updateAgentBackend);
            req.put(FieldParameter.userlogin, session.getAttribute(FieldParameter.userlogin));
            req.put(RuleNameParameter.agent_id, request.getParameter(RuleNameParameter.agent_id));
            req.put(RuleNameParameter.agent_name, request.getParameter(RuleNameParameter.agent_name));
//            req.put(FieldParameter.address, request.getParameter(FieldParameter.address));
            req.put(FieldParameter.email, request.getParameter(FieldParameter.email));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            req.put(FieldParameter.procCode, ProcessingCode.deleteAgentBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
//        } else if (action.equalsIgnoreCase("resetpassword")) {
//            req.put(FieldParameter.procCode, ProcessingCode.resetPassAgentBackend);
//            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
//            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
//            reqMsg = mp.encryptMessage(req);
//            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
//            resp = mp.decryptMessage(responseWeb);
//            String status = resp.get(RuleNameParameter.resp_code).toString();
//            if (status.equals("0000")) {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-success status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
//                        + "</div>");
//                rd.include(request, response);
//            }
        } else if (action.equalsIgnoreCase("resetpin")) {
            req.put(FieldParameter.procCode, ProcessingCode.resetPinAgentBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
//        } else if (action.equalsIgnoreCase("dorman")) {
//            forward = DORMAN;
//            req.put(FieldParameter.procCode, ProcessingCode.requestOTPDorman);
//            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
//            req.put(FieldParameter.hpNumber, request.getParameter("agent_phone"));
//
//            reqMsg = mp.encryptMessage(req);
//            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
//            resp = mp.decryptMessage(responseWeb);
//            Agentyabes agentyabes = new Agentyabes();
//            agentyabes.setAgent_phone(resp.get(FieldParameter.hpNumber).toString());
//            agentyabes.setAgent_name(resp.get(FieldParameter.name).toString());
//            agentyabes.setAgent_id(resp.get(FieldParameter.username).toString());
//            agentyabes.setBalance(resp.get(FieldParameter.balance).toString());
//
//            request.setAttribute("agentyabes", agentyabes);
//            RequestDispatcher view = request.getRequestDispatcher(forward);
//            view.forward(request, response);
//        } else if (action.equalsIgnoreCase("konfirmasidorman")) {
//            req.put(FieldParameter.procCode, ProcessingCode.confirmOTPDorman);
//            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
//            req.put(FieldParameter.hpNumber, request.getParameter("phonenumber"));
//            req.put(FieldParameter.password, request.getParameter("otp"));
//
//            System.out.println("ini adalah req nya coty : " + req);
//            reqMsg = mp.encryptMessage(req);
//            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
//            resp = mp.decryptMessage(responseWeb);
//            System.out.println("ini adalah respon nya coy : " +resp);
//            String status = resp.get(RuleNameParameter.resp_code).toString();
//            if (status.equals("0000")) {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-success status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
//                        + "</div>");
//                rd.include(request, response);
//            }
        }
    }
}
