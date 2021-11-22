/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boa.function;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * author MATAJARI MITRA SOLUSI
 */
public class FunctionSupportMB {
    public static String getCurrentDateYYYYMMDDHHMMSS() {
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd;HHmmss");
        String s = formatter.format(calendar.getTime());
        return s;
    }
    
    public static String encryptOneWayDataSave(String data){
        try {
            MessageDigest sha256digester = MessageDigest.getInstance("SHA-256");
            byte[] digest = sha256digester.digest(data.getBytes("UTF-8"));
            String passwdEnc = new String(Hex.encodeHex(digest));
            return passwdEnc;
        } catch (NoSuchAlgorithmException ex) {
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
    
}