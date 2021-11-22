/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.parameter.StaticParameter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.boa.util.DatabaseProcess;
import com.boa.function.MessageProcess;
import com.boa.function.SendHttpProcess;
import com.boa.function.impl.MessageProcessImpl;
import com.boa.parameter.FieldParameter;
import com.boa.parameter.ProcessingCode;
import com.boa.parameter.RuleNameParameter;
import com.boa.model.Verifikasi;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class VerifikasiServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(VerifikasiServlet.class);
    DatabaseProcess dp = new DatabaseProcess();
    private static final long serialVersionUID = 1L;
    private static String VERIFIKASI = "verifikasi.jsp";
    private static String VIEW = "view.jsp";

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
        if (action.equalsIgnoreCase("Listverifikasi")) {
            req.put(FieldParameter.procCode, ProcessingCode.listVerifikasi);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listAgent));
        } else if (action.equalsIgnoreCase("ListVerifikasics")) {
            req.put(FieldParameter.procCode, ProcessingCode.listVerifikasi);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listAgent));
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
        if (action.equalsIgnoreCase("verifikasi")) {
            req.put(FieldParameter.procCode, ProcessingCode.verifikasi);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_verifikasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_verifikasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = VERIFIKASI;
            req.put(FieldParameter.procCode, ProcessingCode.getPhotoByAgentid);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            Verifikasi verifi = new Verifikasi();
            verifi.setAgent_id(resp.get(RuleNameParameter.agent_id).toString());
            verifi.setImg_ktp(resp.get(FieldParameter.img_ktp).toString());
            verifi.setImg_self(resp.get(FieldParameter.img_profile).toString());
            verifi.setImg_profile(resp.get(FieldParameter.img_self).toString());
//            String abc = resp.get(FieldParameter.biodata_dukcapil).toString().replace("\\", "").replace("\"", "").replace("\\\"", "").replace("{", "\n").replace("}", "").replace(",", "\n");
//            verifi.setBiodata_dukcapil(abc);
            request.setAttribute("verifi", verifi);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("approve")) {
            forward = VIEW;
            req.put(FieldParameter.procCode, ProcessingCode.getPhotoByAgentid);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            Verifikasi verifi = new Verifikasi();
            verifi.setAgent_id(resp.get(RuleNameParameter.agent_id).toString());
            verifi.setImg_ktp(resp.get(FieldParameter.img_ktp).toString());
            verifi.setImg_self(resp.get(FieldParameter.img_profile).toString());
            verifi.setImg_profile(resp.get(FieldParameter.img_self).toString());
//            String abc = resp.get(FieldParameter.biodata_dukcapil).toString().replace("\\", "").replace("\"", "").replace("\\\"", "").replace("{", "\n").replace("}", "").replace(",", "\n");
//            verifi.setBiodata_dukcapil(abc);
            request.setAttribute("verifi", verifi);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("reject")) {
            req.put(FieldParameter.procCode, ProcessingCode.rejectVerifikasi);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(RuleNameParameter.agent_id, request.getParameter("agent_id"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);

            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_verifikasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_verifikasi.jsp");
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
