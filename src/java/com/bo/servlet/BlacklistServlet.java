///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bo.servlet;
//
//import com.boa.model.Blacklist;
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.reflect.TypeToken;
//import com.boa.util.DatabaseProcess;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// *
// * @author syukur
// */
//public class BlacklistServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//    private static String UPDATE = "update_blacklist.jsp";
//
//    DatabaseProcess dp = new DatabaseProcess();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String forward = "";
//        String action = request.getParameter("action");
//        if (action.equalsIgnoreCase("listblacklist")) {
//            ArrayList<Blacklist> alllist = new ArrayList<Blacklist>();
//            alllist = dp.getAllBlacklist();
//            Gson gson = new Gson();
//            JsonElement element = gson.toJsonTree(alllist, new TypeToken<List<Blacklist>>() {
//            }.getType());
//            JsonArray jsonArray = element.getAsJsonArray();
//            response.setContentType("application/json");
//            response.getWriter().print(jsonArray);
//        } else if (action.equalsIgnoreCase("listblacklistcs")) {
//            ArrayList<Blacklist> alllist = new ArrayList<Blacklist>();
//            alllist = dp.getAllBlacklist();
//            Gson gson = new Gson();
//            JsonElement element = gson.toJsonTree(alllist, new TypeToken<List<Blacklist>>() {
//            }.getType());
//            JsonArray jsonArray = element.getAsJsonArray();
//            response.setContentType("application/json");
//            response.getWriter().print(jsonArray);
//        }
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String forward = "";
//        String action = request.getParameter("action");
//        if (action.equalsIgnoreCase("addblacklist")) {
//            Blacklist blacklist = new Blacklist();
//            blacklist.setNama(request.getParameter("nama"));
//            blacklist.setAlias(request.getParameter("alias"));
//            blacklist.setTempat_tanggal_lahir(request.getParameter("tempat_tanggal_lahir"));
//            blacklist.setAlamat(request.getParameter("alamat"));
//            blacklist.setCatagory(request.getParameter("catagory"));
//            blacklist.setKeterangan(request.getParameter("keterangan"));
//            blacklist.setId(request.getParameter("id"));
//            blacklist.setKtp(request.getParameter("ktp"));
//            String status = dp.addBlackList(blacklist);
//            if (status.equalsIgnoreCase("0000")) {
//                HttpSession session = request.getSession(true);
////            session.setMaxInactiveInterval(60);
//                String username = session.getAttribute("userlogin").toString();
//                String activitas = "menambahkan blacklist " + request.getParameter("nama") + " " + request.getParameter("id");
//                dp.userYabes(username, activitas);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_blacklist.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-success status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else if (status.equalsIgnoreCase("0002")) {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_black_list.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_black_list.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
//                        + "</div>");
//                rd.include(request, response);
//            }
//        } else if (action.equalsIgnoreCase("update")) {
//            forward = UPDATE;
//            String id = request.getParameter("id");
//            Blacklist blacklist = dp.getBlacklistById(id);
//            request.setAttribute("blacklist", blacklist);
//            RequestDispatcher view = request.getRequestDispatcher(forward);
//            view.forward(request, response);
//        } else if (action.equalsIgnoreCase("saveupdate")) {
//            System.out.println("wertyuufghbvbn");
//            Blacklist blacklist = new Blacklist();
//            blacklist.setNama(request.getParameter("nama"));
//            blacklist.setAlias(request.getParameter("alias"));
//            blacklist.setId(request.getParameter("id"));
//            blacklist.setKtp(request.getParameter("ktp"));
//            blacklist.setCatagory(request.getParameter("catagory"));
//            blacklist.setTempat_tanggal_lahir(request.getParameter("tempat_tanggal_lahir"));
//            blacklist.setAlamat(request.getParameter("alamat"));
//            blacklist.setKeterangan(request.getParameter("keterangan"));
//
//            String status = dp.updateBlacklist(blacklist);
//            if (status.equals("0000")) {
//                HttpSession session = request.getSession(true);
//                String username = session.getAttribute("userlogin").toString();
//                String activitas = "mengupdate blacklist " + request.getParameter("nama") + " " + request.getParameter("id");
//                dp.userYabes(username, activitas);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_blacklist.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-success status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_connection_biller.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
//                        + "</div>");
//                rd.include(request, response);
//            }
//        } else if (action.equalsIgnoreCase("delete")) {
//            System.out.println("ini delete");
//            String id = request.getParameter("id");
//            String status = dp.deleteBlacklist(id);
//            if (status.equals("0000")) {
//                HttpSession session = request.getSession(true);
//                String username = session.getAttribute("userlogin").toString();
//                String activitas = "menghapus blacklist " + request.getParameter("id");
//                dp.userYabes(username, activitas);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_blacklist.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-success status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-check\"></i>Success\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_blacklist.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i>Failed\n"
//                        + "</div>");
//                rd.include(request, response);
//            }
//        }
//    }
//}
