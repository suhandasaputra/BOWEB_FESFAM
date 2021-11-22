/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.function.MessageProcess;
import com.boa.function.SendHttpProcess;
import com.boa.function.impl.MessageProcessImpl;
//import com.boa.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.boa.parameter.FieldParameter;
import com.boa.parameter.ProcessingCode;
import com.boa.parameter.StaticParameter;
import com.boa.parameter.RuleNameParameter;
import com.boa.model.am_trancode;

/**
 *
 * @author syukur
 */
public class MappingagentServlet extends HttpServlet {

//    DatabaseProcess dp = new DatabaseProcess();
    private static final long serialVersionUID = 1L;

    private static String UPDATE_PREPAID = "update_mapping_agent.jsp";
    private static String UPDATE_PAYMENT = "update_mapping_agent_payment.jsp";

    public MappingagentServlet() {
    }

    @Override
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

        if (action.equalsIgnoreCase("listuserproduk")) {
            req.put(FieldParameter.procCode, ProcessingCode.listuserproduk);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listprodukuser));
        } else if (action.equalsIgnoreCase("optionagent")) {
            req.put(FieldParameter.procCode, ProcessingCode.optionagent);
            req.put(FieldParameter.cu_id, request.getParameter("cu_id"));
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listConnection));

        }
    }

    @Override
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
        if (action.equalsIgnoreCase("adduserprodukprepaid")) {
            req.put(FieldParameter.procCode, ProcessingCode.adduserprodukprepaid);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
//            req.put(FieldParameter.feejual, request.getParameter("feejual"));
            req.put(FieldParameter.hargajual, request.getParameter("hargajual"));
            req.put(FieldParameter.ppob_profit, request.getParameter("ppob_profit"));
            req.put(FieldParameter.ref_profit, request.getParameter("ref_profit"));
            req.put(FieldParameter.poin, request.getParameter("poin"));
            req.put(FieldParameter.hargajual_noncorp, request.getParameter("hargajual_noncorp"));
            req.put(FieldParameter.ppob_profit_noncorp, request.getParameter("ppob_profit_noncorp"));
            req.put(FieldParameter.poin_noncorp, request.getParameter("poin_noncorp"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate ID. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("adduserprodukpayment")) {
            req.put(FieldParameter.procCode, ProcessingCode.adduserprodukpayment);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            req.put(FieldParameter.feejual, request.getParameter("feejual"));
            req.put(FieldParameter.ppob_profit, request.getParameter("ppob_profit"));
            req.put(FieldParameter.ref_profit, request.getParameter("ref_profit"));
            req.put(FieldParameter.poin, request.getParameter("poin"));
            req.put(FieldParameter.feejual_noncorp, request.getParameter("feejual_noncorp"));
            req.put(FieldParameter.ppob_profit_noncorp, request.getParameter("ppob_profit_noncorp"));
            req.put(FieldParameter.poin_noncorp, request.getParameter("poin_noncorp"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_mapping_agent_payment.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate ID. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_mapping_agent_payment.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            req.put(FieldParameter.procCode, ProcessingCode.getAmTrancodeByTrancodeid);
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            am_trancode am = new am_trancode();

            am.setTrancodeid(resp.get(FieldParameter.trancodeid).toString());
            am.setTrancodename(resp.get(FieldParameter.trancodename).toString());
            am.setFeejual(resp.get(FieldParameter.feejual).toString());
            am.setHargajual(resp.get(FieldParameter.hargajual).toString());
            am.setPpob_profit(resp.get(FieldParameter.ppob_profit).toString());
            am.setRef_profit(resp.get(FieldParameter.ref_profit).toString());
            am.setPoin(resp.get(FieldParameter.poin).toString());
            am.setHargajual_noncorp(resp.get(FieldParameter.hargajual_noncorp).toString());
            am.setPpob_profit_noncorp(resp.get(FieldParameter.ppob_profit_noncorp).toString());
            am.setFeejual_noncorp(resp.get(FieldParameter.feejual_noncorp).toString());
            am.setPoin_noncorp(resp.get(FieldParameter.poin_noncorp).toString());

            if (resp.get(FieldParameter.tctype).equals("290000")) {
                forward = UPDATE_PREPAID;
            } else if (resp.get(FieldParameter.tctype).equals("280000")) {
                forward = UPDATE_PAYMENT;
            }

            request.setAttribute("am", am);

            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("updateproductuser")) {
            req.put(FieldParameter.procCode, ProcessingCode.updateuserproduk);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter(FieldParameter.trancodeid));
            req.put(FieldParameter.feejual, request.getParameter(FieldParameter.feejual));
            req.put(FieldParameter.hargajual, request.getParameter(FieldParameter.hargajual));
            req.put(FieldParameter.ppob_profit, request.getParameter(FieldParameter.ppob_profit));
            req.put(FieldParameter.ref_profit, request.getParameter(FieldParameter.ref_profit));
            req.put(FieldParameter.poin, request.getParameter(FieldParameter.poin));
            req.put(FieldParameter.hargajual_noncorp, request.getParameter(FieldParameter.hargajual_noncorp));
            req.put(FieldParameter.ppob_profit_noncorp, request.getParameter(FieldParameter.ppob_profit_noncorp));
            req.put(FieldParameter.feejual_noncorp, request.getParameter(FieldParameter.feejual_noncorp));
            req.put(FieldParameter.poin_noncorp, request.getParameter(FieldParameter.poin_noncorp));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            req.put(FieldParameter.procCode, ProcessingCode.deleteuserproduk);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_mapping_agent.jsp");
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
