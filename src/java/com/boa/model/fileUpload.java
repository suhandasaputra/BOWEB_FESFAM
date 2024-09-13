///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.boa.model;
//
//import java.io.File;
//import org.apache.commons.fileupload.FileItemStream;
//import java.io.*;
//
///**
// *
// * @author USER
// */
//public class fileUpload {
// 
// 
// 
// 
//    
//    public static boolean processFile(FileItemStream item) {
//        try {
//            //localhost
////            File f = new File("C:\\xampp\\htdocs\\backofficecuso_ppob\\image\\ads\\");
//            //dev
////            File f = new File("/var/www/matajaristore/upload/image/ads/");
//            //prod
//            File f = new File("/var/www/html/image/ads/");
//            if (!f.exists()) {
//                f.mkdir();
//            }
//            File savedFile = new File(f.getAbsolutePath() + File.separator + item.getName());
//            FileOutputStream fos = new FileOutputStream(savedFile);
//            InputStream is = item.openStream();
//            int x = 0;
//            byte[] b = new byte[1024];
//            while ((x = is.read(b)) != -1) {
//                fos.write(b, 0, x);
//            }
//            fos.flush();
//            fos.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//}
