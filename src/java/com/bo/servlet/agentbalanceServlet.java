/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

//import com.mbo.dao.MonitoringPpob;
import com.boa.model.agentbalance;
import com.boa.util.DatabaseProcess;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 *
 * @author syukur
 */
public class agentbalanceServlet extends HttpServlet {
    
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(agentbalanceServlet.class);

    private static final long serialVersionUID = 1L;

    public agentbalanceServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        ArrayList<Monitoringppobs> ppob = new ArrayList<Monitoringppobs>();
//        ppob = DatabaseProcess.getAllPpobs();
        try {
            ArrayList<agentbalance> ppob = new ArrayList<agentbalance>();
            DatabaseProcess dp = new DatabaseProcess();
            ppob = dp.getAgentBalance();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(ppob);

            JsonArray jsonArray = element.getAsJsonArray();
            log.info(jsonArray);
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
