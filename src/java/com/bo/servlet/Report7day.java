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

/**
 *
 * @author syukur
 */
public class Report7day extends HttpServlet {

//    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private ReportYabes dao;
    private static String UPDATE = "update_limit.jsp";

    public Report7day() {
//        dao = new ReportYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SendHttpProcess http = new SendHttpProcess();
        HashMap req = new HashMap();
        MessageProcess mp = new MessageProcessImpl();

        req.put(FieldParameter.procCode, ProcessingCode.list7TransactionBackend);
        req.put(FieldParameter.userlogin, request.getParameter("userlogin"));
        String reqMsg = mp.encryptMessage(req);

        String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
        HashMap resp = mp.decryptMessage(responseWeb);
        response.getWriter().print(resp.get(FieldParameter.listTrx));
    }
}
