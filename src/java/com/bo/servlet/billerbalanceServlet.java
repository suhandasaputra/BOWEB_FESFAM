/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo.servlet;

//import com.mbo.dao.MonitoringPpob;
import com.boa.model.billerbalance;
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
import com.boa.util.DatabaseProcess;
//import com.boa.util.DatabaseProcessH2H;
import java.util.List;

/**
 *
 * @author syukur
 */
public class billerbalanceServlet extends HttpServlet {
    
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(billerbalanceServlet.class);

    private static final long serialVersionUID = 1L;

    public billerbalanceServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        ArrayList<Monitoringppobs> ppob = new ArrayList<Monitoringppobs>();
//        ppob = DatabaseProcess.getAllPpobs();
        try {
            ArrayList<billerbalance> ppob = new ArrayList<billerbalance>();
            DatabaseProcess dp = new DatabaseProcess();
            ppob = dp.getBillerBalance();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(ppob, new TypeToken<List<billerbalance>>() {
            }.getType());

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
