/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

//import com.boa.dao.LoginProcess;
import com.boa.parameter.StaticParameter;
import com.boa.function.MessageProcess;
import com.boa.function.SendHttpProcess;
import com.boa.function.impl.MessageProcessImpl;
import com.boa.parameter.FieldParameter;
import com.boa.parameter.ProcessingCode;
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
public class LoginServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LoginServlet.class);
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap req = new HashMap();
        MessageProcess mp = new MessageProcessImpl();
        SendHttpProcess http = new SendHttpProcess();
        String userlogin = request.getParameter("userlogin");
        req.put(FieldParameter.procCode, ProcessingCode.loginBackend);
        req.put(FieldParameter.userlogin, request.getParameter("userlogin"));
        req.put(FieldParameter.password, request.getParameter("userpass"));
        String reqMsg = mp.encryptMessage(req);
        String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
        HashMap resp = mp.decryptMessage(responseWeb);
        if (resp.get(FieldParameter.resp_code).toString().equals("0000")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userlogin", userlogin);
            session.setAttribute("userlevel", resp.get(FieldParameter.userlevel).toString());
            log.info("============== " + userlogin + " is logged in " + "==============");
            RequestDispatcher rd = request.getRequestDispatcher("/WelcomePage.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>Welcome " + userlogin + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            log.info("\n" + "Either user name or password is wrong." + "\n");
            rd.include(request, response);
        }
    }
}
