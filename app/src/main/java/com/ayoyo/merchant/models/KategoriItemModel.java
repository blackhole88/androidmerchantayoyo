package com.ayoyo.merchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class KategoriItemModel extends RealmObject implements Serializable{

    @Expose
    @SerializedName("id_kategori_item")
    private String id_kategori_item;

    @Expose
    @SerializedName("nama_kategori_item")
    private String nama_kategori_item;

    @Expose
    @SerializedName("status_kategori")
    private String status_kategori;

    @Expose
    @SerializedName("total_item")
    private String total_item;



    public String getId_kategori_item() {
        return id_kategori_item;
    }

    public void setId_kategori_item(String id_kategori_item) {
        this.id_kategori_item = id_kategori_item;
    }

    public String getNama_kategori_item() {
        return nama_kategori_item;
    }

    public void setNama_kategori_item(String nama_kategori_item) {
        this.nama_kategori_item = nama_kategori_item;
    }

    public String getStatus_kategori() {
        return status_kategori;
    }

    public void setStatus_kategori(String status_kategori) {
        this.status_kategori = status_kategori;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_itemoken(String total_item) {
        this.total_item = total_item;
    }

}
