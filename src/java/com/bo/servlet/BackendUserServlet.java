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
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class BackendUserServlet extends HttpServlet {

//    DatabaseProcess dp = new DatabaseProcess();
    private static final long serialVersionUID = 1L;
//    private UserYabes dao;

    public BackendUserServlet() {
//        dao = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        MessageProcess mp = new MessageProcessImpl();
        HttpSession session = request.getSession(true);
        SendHttpProcess http = new SendHttpProcess();

        HashMap req = new HashMap();
        if (action.equalsIgnoreCase("Listagentnotif")) {
            req.put(FieldParameter.procCode, ProcessingCode.listNotifUserBackend);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            String reqMsg = mp.encryptMessage(req);
            String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            HashMap resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listAgent));

        } else if (action.equalsIgnoreCase("Listagentnotifadministrator")) {
            req.put(FieldParameter.procCode, ProcessingCode.listNotifUserBackendadministrator);
            req.put(FieldParameter.userlogin, session.getAttribute("userlogin"));
            String reqMsg = mp.encryptMessage(req);
            String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
            HashMap resp = mp.decryptMessage(responseWeb);
            response.getWriter().print(resp.get(FieldParameter.listAgent));

        }
    }
}
