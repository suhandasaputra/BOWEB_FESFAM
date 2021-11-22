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
import com.boa.parameter.StaticParameter;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author suhanda
 */

public class VaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        SendHttpProcess http = new SendHttpProcess();
        HashMap req = new HashMap();
        MessageProcess mp = new MessageProcessImpl();

        req.put(FieldParameter.procCode, ProcessingCode.listVa);
        req.put(FieldParameter.userlogin, request.getParameter("userlogin"));
        String reqMsg = mp.encryptMessage(req);

        String responseWeb = http.sendHttpRequest(StaticParameter.urlBOServer, reqMsg);
        HashMap resp = mp.decryptMessage(responseWeb);
        response.getWriter().print(resp.get(FieldParameter.listVa));
    }

    
}
