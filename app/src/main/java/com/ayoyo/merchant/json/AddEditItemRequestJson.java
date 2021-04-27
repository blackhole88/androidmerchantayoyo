package com.ayoyo.merchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class AddEditItemRequestJson {

    @SerializedName("no_telepon")
    @Expose
    private String notelepon;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("idmerchant")
    @Expose
    private String idmerchant;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("harga")
    @Expose
    private String harga;

    @SerializedName("harga_promo")
    @Expose
    private String hargapromo;

    @SerializedName("kategori")
    @Expose
    private String kategori;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("foto")
    @Expose
    private String foto;

    @SerializedName("foto_lama")
    @Expose
    private String fotolama;

    @SerializedName("status_promo")
    @Expose
    private String status;


    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdmerchant() {
        return idmerchant;
    }

    public void setIdmerchant(String idmerchant) {
        this.idmerchant = idmerchant;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getHargapromo() {
        return hargapromo;
    }

    public void setHargapromo(String hargapromo) {
        this.hargapromo = hargapromo;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotolama() {
        return fotolama;
    }

    public void setFotolama(String fotolama) {
        this.fotolama = fotolama;
    }
}
