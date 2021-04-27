package com.ayoyo.merchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class EditMerchantRequestJson {

    @SerializedName("no_telepon")
    @Expose
    private String notelepon;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("latitude")
    @Expose
    private String latitude_merchant;

    @SerializedName("longitude")
    @Expose
    private String longitude_merchant;

    @SerializedName("jam_buka")
    @Expose
    private String jam_buka;

    @SerializedName("jam_tutup")
    @Expose
    private String jam_tutup;

    @SerializedName("foto")
    @Expose
    private String foto_merchant;

    @SerializedName("foto_lama")
    @Expose
    private String foto_lama;

    @SerializedName("nama")
    @Expose
    private String namamerchant;



    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude_merchant() {
        return latitude_merchant;
    }

    public void setLatitude_merchant(String latitude_merchant) {
        this.latitude_merchant = latitude_merchant;
    }

    public String getLongitude_merchant() {
        return longitude_merchant;
    }

    public void setLongitude_merchant(String longitude_merchant) {
        this.longitude_merchant = longitude_merchant;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public void setJam_buka(String jam_buka) {
        this.jam_buka = jam_buka;
    }

    public String getJam_tutup() {
        return jam_tutup;
    }

    public void setJam_tutup(String jam_tutup) {
        this.jam_tutup = jam_tutup;
    }

    public String getFoto_merchant() {
        return foto_merchant;
    }

    public void setFoto_merchant(String foto_merchant) {
        this.foto_merchant = foto_merchant;
    }

    public String getFoto_lama() {
        return foto_lama;
    }

    public void setFoto_lama(String foto_lama) {
        this.foto_lama = foto_lama;
    }

    public String getNamamerchant() {
        return namamerchant;
    }

    public void setNamamerchant(String namamerchant) {
        this.namamerchant = namamerchant;
    }


}
