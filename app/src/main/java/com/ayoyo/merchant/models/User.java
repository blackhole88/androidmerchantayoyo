package com.ayoyo.merchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class User extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("id_mitra")
    @Expose
    private String id;

    @SerializedName("nama_mitra")
    @Expose
    private String namamitra;

    @SerializedName("alamat_mitra")
    @Expose
    private String alamat_mitra;

    @SerializedName("email_mitra")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("telepon_mitra")
    @Expose
    private String noTelepon;

    @SerializedName("phone_mitra")
    @Expose
    private String phone;

    @SerializedName("country_code_mitra")
    @Expose
    private String countrycode;
///
    @SerializedName("id_merchant")
    @Expose
    private String id_merchant;

    @SerializedName("saldo")
    @Expose
    private long walletSaldo;

    @SerializedName("alamat_merchant")
    @Expose
    private String alamat_merchant;

    @SerializedName("latitude_merchant")
    @Expose
    private String latitude_merchant;

    @SerializedName("longitude_merchant")
    @Expose
    private String longitude_merchant;

    @SerializedName("jam_buka")
    @Expose
    private String jam_buka;

    @SerializedName("jam_tutup")
    @Expose
    private String jam_tutup;

    @SerializedName("foto_merchant")
    @Expose
    private String foto_merchant;

    @SerializedName("nama_merchant")
    @Expose
    private String namamerchant;

    @SerializedName("token_merchant")
    @Expose
    private String token_merchant;

    @SerializedName("status_merchant")
    @Expose
    private String status_merchant;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamamitra() {
        return namamitra;
    }

    public void setNamamitra(String namamitra) {
        this.namamitra = namamitra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat_mitra() {
        return alamat_mitra;
    }

    public void setAlamat_mitra(String alamat_mitra) {
        this.alamat_mitra = alamat_mitra;
    }

    public long getWalletSaldo() {
        return walletSaldo;
    }

    public void setWalletSaldo(long walletSaldo) {
        this.walletSaldo = walletSaldo;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getId_merchant() {
        return id_merchant;
    }

    public void setId_merchant(String id_merchant) {
        this.id_merchant = id_merchant;
    }

    public String getAlamat_merchant() {
        return alamat_merchant;
    }

    public void setAlamat_merchant(String alamat_merchant) {
        this.alamat_merchant = alamat_merchant;
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

    public String getNamamerchant() {
        return namamerchant;
    }

    public void setNamamerchant(String namamerchant) {
        this.namamerchant = namamerchant;
    }

    public String getToken_merchant() {
        return token_merchant;
    }

    public void setToken_merchant(String token_merchant) {
        this.token_merchant = token_merchant;
    }

    public String getStatus_merchant() {
        return status_merchant;
    }

    public void setStatus_merchant(String status_merchant) {
        this.status_merchant = status_merchant;
    }
}
