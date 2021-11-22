/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.model.Agentyabes;
import com.boa.model.Connectionbiller;
import com.boa.parameter.StaticParameter;
import com.boa.util.DatabaseProcess;
import com.boa.function.MessageProcess;
import com.boa.function.SHA256Enc;
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
public class ConnectionbillerServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
    private static String UPDATE = "update_connection_biller.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        SendHttpProcess http = new SendHttpProcess();
        MessageProcess mp = (MessageProcess) new MessageProcessImpl();
        HashMap req = new HashMap();

        if (action.equalsIgnoreCase("ListConnection")) {
            req.put(FieldParameter.procCode, ProcessingCode.listConnectionBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            String reqMsg = mp.encryptMessage(req);
            String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            HashMap resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listConnection));
        } else if (action.equalsIgnoreCase("ListConnectioncs")) {

            req.put(FieldParameter.procCode, ProcessingCode.listConnectionBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            String reqMsg = mp.encryptMessage(req);
            String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            HashMap resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listConnection));

        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
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
        if (action.equalsIgnoreCase("addconnectionbiller")) {
            req.put(FieldParameter.procCode, ProcessingCode.addConnectionBiller);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.billername, request.getParameter("billername"));
            req.put(FieldParameter.billercode, request.getParameter("billercode"));
            req.put(FieldParameter.packagename, request.getParameter("packagename"));
            req.put(FieldParameter.urlhost, request.getParameter("urlhost"));

            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/connection_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equals("0002")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_connection_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Agent Account. \n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_connection_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i> Failed, unknown error. \n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            req.put(FieldParameter.procCode, ProcessingCode.getConnectionByBillercode);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            req.put(FieldParameter.billercode, request.getParameter("billercode"));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);

            Connectionbiller cbiller = new Connectionbiller();
            cbiller.setBillername(resp.get(FieldParameter.billername).toString());
            cbiller.setBillercode(resp.get(FieldParameter.billercode).toString());
            cbiller.setPackagename(resp.get(FieldParameter.packagename).toString());
            cbiller.setUrlhost(resp.get(FieldParameter.urlhost).toString());

            request.setAttribute("cbiller", cbiller);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("updateconnection")) {
            req.put(FieldParameter.procCode, ProcessingCode.updateConnection);
            req.put(FieldParameter.userlogin, session.getAttribute(FieldParameter.userlogin));
            req.put(FieldParameter.billercode, request.getParameter(FieldParameter.billercode));
            req.put(FieldParameter.billername, request.getParameter(FieldParameter.billername));
            req.put(FieldParameter.packagename, request.getParameter(FieldParameter.packagename));
            req.put(FieldParameter.urlhost, request.getParameter(FieldParameter.urlhost));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/connection_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_connection_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            req.put(FieldParameter.procCode, ProcessingCode.deleteConnection);
            req.put(FieldParameter.userlogin, session.getAttribute(FieldParameter.userlogin));
            req.put(FieldParameter.billercode, request.getParameter(FieldParameter.billercode));
            reqMsg = mp.encryptMessage(req);
            responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            resp = mp.decryptMessage(responseWeb);
            String status = resp.get(RuleNameParameter.resp_code).toString();
            if (status.equals("0000")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/connection_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/connection_biller.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Failed.\n"
                        + "</div>");
                rd.include(request, response);
            }
        }
    }
}
