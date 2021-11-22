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
public class Blacklist {

    private String nama;
    private String alias;
    private String tempat_tanggal_lahir;
    private String alamat;
    private String keterangan;
    private String id;
    private String ktp;
    private String catagory;
    private String createdate;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTempat_tanggal_lahir() {
        return tempat_tanggal_lahir;
    }

    public void setTempat_tanggal_lahir(String tempat_tanggal_lahir) {
        this.tempat_tanggal_lahir = tempat_tanggal_lahir;
    }

    public String getAlamat() {
        return alamat;
    }
    
    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

}
