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
public class Reportyabes {
    private String noref;
    private String custno;
    private String statusreply;
    private String billercode;
    private String requesttime;
    private String fromagent;
    private String proccode;
    private String productcode;
    private String profit;
    private String amount;
    private String rcinternal;
    private String prev_bal;

    public String getPrev_bal() {
        return prev_bal;
    }

    public void setPrev_bal(String prev_bal) {
        this.prev_bal = prev_bal;
    }
    private String curr_bal;

   

    public String getCurr_bal() {
        return curr_bal;
    }

    public void setCurr_bal(String curr_bal) {
        this.curr_bal = curr_bal;
    }

    

    public String getCustno() {
        return custno;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }
    private String status;
    private String billername;
    private String agentname;
    private String agentid;
    private String nameproduct;

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }
    /**
     * @return the noref
     */
    public String getNoref() {
        return noref;
    }

    /**
     * @param noref the noref to set
     */
    public void setNoref(String noref) {
        this.noref = noref;
    }

    /**
     * @return the statusreply
     */
    public String getStatusreply() {
        return statusreply;
    }

    /**
     * @param statusreply the statusreply to set
     */
    public void setStatusreply(String statusreply) {
        this.statusreply = statusreply;
    }

    /**
     * @return the billercode
     */
    public String getBillercode() {
        return billercode;
    }

    /**
     * @param billercode the billercode to set
     */
    public void setBillercode(String billercode) {
        this.billercode = billercode;
    }

    /**
     * @return the requesttime
     */
    public String getRequesttime() {
        return requesttime;
    }

    /**
     * @param requesttime the requesttime to set
     */
    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    /**
     * @return the fromagent
     */
    public String getFromagent() {
        return fromagent;
    }

    /**
     * @param fromagent the fromagent to set
     */
    public void setFromagent(String fromagent) {
        this.fromagent = fromagent;
    }

    /**
     * @return the proccode
     */
    public String getProccode() {
        return proccode;
    }

    /**
     * @param proccode the proccode to set
     */
    public void setProccode(String proccode) {
        this.proccode = proccode;
    }

    /**
     * @return the productcode
     */
    public String getProductcode() {
        return productcode;
    }

    /**
     * @param productcode the productcode to set
     */
    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    /**
     * @return the profit
     */
    public String getProfit() {
        return profit;
    }

    /**
     * @param profit the profit to set
     */
    public void setProfit(String profit) {
        this.profit = profit;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the rcinternal
     */
    public String getRcinternal() {
        return rcinternal;
    }

    /**
     * @param rcinternal the rcinternal to set
     */
    public void setRcinternal(String rcinternal) {
        this.rcinternal = rcinternal;
    }

    /**
     * @return the billername
     */
    public String getBillername() {
        return billername;
    }

    /**
     * @param billername the billername to set
     */
    public void setBillername(String billername) {
        this.billername = billername;
    }

    /**
     * @return the agentname
     */
    public String getAgentname() {
        return agentname;
    }

    /**
     * @param agentname the agentname to set
     */
    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    /**
     * @return the nameproduct
     */
    public String getNameproduct() {
        return nameproduct;
    }

    /**
     * @param nameproduct the nameproduct to set
     */
    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}