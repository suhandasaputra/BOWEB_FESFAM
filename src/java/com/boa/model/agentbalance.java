/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boa.model;

/**
 *
 * @author syukur
 */
public class agentbalance {
    private String agent_id;
    private String agent_name;
    private String phonenumber;
    private String no_ktp;
    private String max_curr_bal;
    private String poin_bal;
        private String app_id;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }


    public String getPoin_bal() {
        return poin_bal;
    }

    public void setPoin_bal(String poin_bal) {
        this.poin_bal = poin_bal;
    }


    public String getMax_curr_bal() {
        return max_curr_bal;
    }

    public void setMax_curr_bal(String max_curr_bal) {
        this.max_curr_bal = max_curr_bal;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }
    private String curr_bal;

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

   

    public String getCurr_bal() {
        return curr_bal;
    }

    public void setCurr_bal(String curr_bal) {
        this.curr_bal = curr_bal;
    }
    
    
}
