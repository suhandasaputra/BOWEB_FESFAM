/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

import com.boa.model.Agentyabes;
import com.boa.function.MessageProcess;
import com.boa.function.SHA256Enc;
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
public class LogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProcess mp = new MessageProcessImpl();
        HttpSession session = request.getSession(true);
        SendHttpProcess http = new SendHttpProcess();
        HashMap req = new HashMap();
        HashMap resp = new HashMap();
        String reqMsg = "";
        String responseWeb = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("Listlog")) {
            if (session.getAttribute("userlevel").toString().equals("administrator")) {
                req.put(FieldParameter.procCode, ProcessingCode.listLog);
                req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
                reqMsg = mp.encryptMessage(req);
                responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
                resp = mp.decryptMessage(responseWeb);
                response.getWriter().print(resp.get(FieldParameter.listLog));
            } else if (session.getAttribute("userlevel").toString().equals("admin")) {
                req.put(FieldParameter.procCode, ProcessingCode.listLogselfuser);
                req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
                reqMsg = mp.encryptMessage(req);
                responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
                resp = mp.decryptMessage(responseWeb);
                response.getWriter().print(resp.get(FieldParameter.listLog));
            } else if (session.getAttribute("userlevel").toString().equals("cs")) {
                req.put(FieldParameter.procCode, ProcessingCode.listLogselfuser);
                req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
                reqMsg = mp.encryptMessage(req);
                responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
                resp = mp.decryptMessage(responseWeb);
                response.getWriter().print(resp.get(FieldParameter.listLog));
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
