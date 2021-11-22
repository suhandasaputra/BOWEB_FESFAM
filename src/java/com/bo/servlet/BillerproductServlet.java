/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.model.Billerproduct;
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
public class BillerproductServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;
    private static String INSERT = "add_product_biller.jsp";
    private static String UPDATE = "update_product_biller.jsp";

    public BillerproductServlet() {
    }

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

        if (action.equalsIgnoreCase("optionbiller")) {
            req.put(FieldParameter.procCode, ProcessingCode.optionbiller);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listConnection));
        } else if (action.equalsIgnoreCase("optionproductbiller")) {
            String transactiontype = request.getParameter("transactiontype");
            req.put(FieldParameter.procCode, ProcessingCode.optionproductbiller);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.transactiontype, transactiontype);
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listProductBiller));
        } else if (action.equalsIgnoreCase("listbillerproduct")) {
            req.put(FieldParameter.procCode, ProcessingCode.listBillerProduct);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listProductBiller));
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
        if (action.equalsIgnoreCase("addproductbiller")) {
            req.put(FieldParameter.procCode, ProcessingCode.addProductBiller);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.billercode, request.getParameter("billercode"));
            req.put(FieldParameter.transactiontype, request.getParameter("transactiontype"));
            req.put(FieldParameter.trancodeid, request.getParameter("trancodeid"));
            req.put(FieldParameter.tcbiller, request.getParameter("tcbiller"));
            req.put(FieldParameter.hargabeli, request.getParameter("hargabeli"));
            req.put(FieldParameter.feebeli, request.getParameter("feebeli"));
//                        req.put(FieldParameter.poin, request.getParameter("poin"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_biller_product.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_product_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate ID. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_product_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            req.put(FieldParameter.procCode, ProcessingCode.getProductbillerBybillercodeAndtcbiller);
            req.put(FieldParameter.tcbiller, request.getParameter("tcbiller"));
            req.put(FieldParameter.billercode, request.getParameter("billercode"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            Billerproduct pbiller = new Billerproduct();
            
            pbiller.setBillername(resp.get(FieldParameter.billername).toString());
            pbiller.setTcbiller(resp.get(FieldParameter.tcbiller).toString());
            pbiller.setTrancodename(resp.get(FieldParameter.trancodename).toString());
            pbiller.setHargabeli(resp.get(FieldParameter.hargabeli).toString());
            pbiller.setFeebeli(resp.get(FieldParameter.feebeli).toString());
            pbiller.setTctype(resp.get(FieldParameter.tctype).toString());
//                        pbiller.setPoin(resp.get(FieldParameter.poin).toString());

            
            request.setAttribute("pbiller", pbiller);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("updateproductbiller")) {
            
            req.put(FieldParameter.procCode, ProcessingCode.updateProductBiller);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.billername, request.getParameter(FieldParameter.billername));
            req.put(FieldParameter.tcbiller, request.getParameter(FieldParameter.tcbiller));
            req.put(FieldParameter.trancodename, request.getParameter(FieldParameter.trancodename));
            req.put(FieldParameter.tctype, request.getParameter(FieldParameter.tctype));
            req.put(FieldParameter.hargabeli, request.getParameter(FieldParameter.hargabeli));
            req.put(FieldParameter.feebeli, request.getParameter(FieldParameter.feebeli));
//                        req.put(FieldParameter.poin, request.getParameter(FieldParameter.poin));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_biller_product.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_product_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            req.put(FieldParameter.procCode, ProcessingCode.deleteProductBiller);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.tcbiller, request.getParameter("tcbiller"));
            req.put(FieldParameter.billercode, request.getParameter("billercode"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_biller_product.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success.\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_biller_product.jsp");
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
