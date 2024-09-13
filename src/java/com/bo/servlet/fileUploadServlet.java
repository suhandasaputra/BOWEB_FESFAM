///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bo.servlet;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import com.boa.model.fileUpload;
////import com.mea.model.user;
//import javax.servlet.RequestDispatcher;
//import org.apache.commons.fileupload.FileItemIterator;
//
////import java.io.*;
///**
// *
// * @author USER
// */
//@WebServlet(name = "fileUploadServlet", urlPatterns = {"/fileUploadServlet"})
//public class fileUploadServlet extends HttpServlet {
//    
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ServletFileUpload upload = new ServletFileUpload();
//        try {
//            FileItemIterator itr = upload.getItemIterator(request);
//            while (itr.hasNext()) {
//                FileItemStream item = itr.next();
//                if (fileUpload.processFile(item)){
//                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_ads.jsp");
//                    PrintWriter out = response.getWriter();
//                    out.println("<div class=\"alert alert-success status-custom\">\n"
//                            + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                            + "     <i class=\"icon fa fa-check\"></i>  Success  \n"
//                            + "</div>");
//                    rd.include(request, response);
//                } else {
//                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/add_ads.jsp");
//                    PrintWriter out = response.getWriter();
//                    out.println("<div class=\"alert alert-danger status-custom\">\n"
//                            + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                            + "     <i class=\"icon fa fa-warning\"></i> Failed, Duplicate Agent Account. \n"
//                            + "</div>");
//                    rd.include(request, response);
//                }
//            }
//        } catch (FileUploadException fue) {
//            fue.printStackTrace();
//        }
//    }
//}
