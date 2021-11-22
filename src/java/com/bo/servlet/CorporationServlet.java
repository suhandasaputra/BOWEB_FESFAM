/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.function.MessageProcess;
import com.boa.function.SHA256Enc;
import com.boa.function.SendHttpProcess;
import com.boa.function.impl.MessageProcessImpl;
import com.boa.parameter.FieldParameter;
import com.boa.parameter.ProcessingCode;
import com.boa.parameter.RuleNameParameter;
import com.boa.model.Corporation;
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
public class CorporationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String UPDATE = "update_corp.jsp";
        private static String UPDATEKOP = "update_koperasi.jsp";


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
        if (action.equalsIgnoreCase("ListCorp")) {
            req.put(FieldParameter.procCode, ProcessingCode.listCorp);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            System.out.println("ini response agent nya : "+resp);
            response.getWriter().print(resp.get(FieldParameter.listCorp));
        } else if (action.equalsIgnoreCase("option_cuid")) {
            req.put(FieldParameter.procCode, ProcessingCode.option_cuid);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.list_cuid));
        } else if (action.equalsIgnoreCase("ListKop")) {
            req.put(FieldParameter.procCode, ProcessingCode.listKop);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.list_kop));
        } else if (action.equalsIgnoreCase("hystori_trf_corp")) {
            req.put(FieldParameter.procCode, ProcessingCode.hystori_trf_corp);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.list_trf_corp));
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
        if (action.equalsIgnoreCase("addCorp")) {
            req.put(FieldParameter.procCode, ProcessingCode.addCorp);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.cu_id, request.getParameter("cu_id"));
            req.put(FieldParameter.phonenumber, request.getParameter("phonenumber"));
            req.put(FieldParameter.no_anggota, request.getParameter("no_anggota"));
            
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_corp.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_corp.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Corp Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_corp.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            req.put(FieldParameter.procCode, ProcessingCode.getCorpByCuidAndPhonenumber);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.cu_id, request.getParameter("cu_id"));
            req.put(FieldParameter.phonenumber, request.getParameter("phonenumber"));
            req.put(FieldParameter.no_anggota, request.getParameter("no_anggota"));


            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);

            Corporation corp = new Corporation();
            corp.setCu_id(resp.get(FieldParameter.cu_id).toString());
            corp.setPhonenumber(resp.get(FieldParameter.phonenumber).toString());
            corp.setNo_anggota(resp.get(FieldParameter.no_anggota).toString());

            request.setAttribute("corp", corp);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("updateCorp")) {
            req.put(FieldParameter.procCode, ProcessingCode.updateCorp);
            req.put(FieldParameter.userlogin, session.getAttribute(FieldParameter.userlogin));
            req.put(FieldParameter.cu_id, request.getParameter(FieldParameter.cu_id));
            req.put(FieldParameter.phonenumber, request.getParameter(FieldParameter.phonenumber));
            req.put(FieldParameter.no_anggota, request.getParameter(FieldParameter.no_anggota));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_corp.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_corp.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            req.put(FieldParameter.procCode, ProcessingCode.deleteCorp);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.cu_id, request.getParameter("cu_id"));
            req.put(FieldParameter.phonenumber, request.getParameter("phonenumber"));
            req.put(FieldParameter.no_anggota, request.getParameter("no_anggota"));


            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_corp.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_corp.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("addKop")) {
            req.put(FieldParameter.procCode, ProcessingCode.addKop);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.cu_id, request.getParameter("cu_id"));
            req.put(FieldParameter.nama_koperasi, request.getParameter("nama_koperasi"));
            req.put(FieldParameter.password_corp, SHA256Enc.encryptProc(request.getParameter("password_corp")));

            
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_koperasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_koperasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Corp Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_koperasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("updateKop")) {
            forward = UPDATEKOP;
            req.put(FieldParameter.procCode, ProcessingCode.getKopByCuid);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.cu_id, request.getParameter("cu_id"));
            req.put(FieldParameter.nama_koperasi, request.getParameter("nama_koperasi"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);

            Corporation corp = new Corporation();
            corp.setCu_id(resp.get(FieldParameter.cu_id).toString());
            corp.setNama_koperasi(resp.get(FieldParameter.nama_koperasi).toString());

            request.setAttribute("corp", corp);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("updateKoppp")) {
            req.put(FieldParameter.procCode, ProcessingCode.updateKop);
            req.put(FieldParameter.userlogin, session.getAttribute(FieldParameter.userlogin));
            req.put(FieldParameter.cu_id, request.getParameter(FieldParameter.cu_id));
            req.put(FieldParameter.nama_koperasi, request.getParameter(FieldParameter.nama_koperasi));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_koperasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_koperasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("deleteKop")) {
            req.put(FieldParameter.procCode, ProcessingCode.deleteKop);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.cu_id, request.getParameter("cu_id"));
            req.put(FieldParameter.nama_koperasi, request.getParameter("nama_koperasi"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_koperasi.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_koperasi.jsp");
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
