package com.ayoyo.merchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 12/01/2019.
 */

public class TransaksiMerchantModel implements Serializable {
    @Expose
    @SerializedName("id_trans_merchant")
    public String idtransmerchant;

    @Expose
    @SerializedName("id_transaksi")
    public String id_transaksi;

    @Expose
    @SerializedName("id_merchant")
    public String id_merchant;

    @Expose
    @SerializedName("total_biaya")
    public String total_biaya;

    @Expose
    @SerializedName("created")
    public String created;

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("quantity")
    public String quantity;

    @Expose
    @SerializedName("fullnama")
    public String fullnama;

    @Expose
    @SerializedName("id_pelanggan")
    public String idpelanggan;

    @Expose
    @SerializedName("id_driver")
    public String iddriver;

    public String getIdtransmerchant() {
        return idtransmerchant;
    }

    public void setIdtransmerchant(String idtransmerchant) {
        this.idtransmerchant = idtransmerchant;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_merchant() {
        return id_merchant;
    }

    public void setId_merchant(String id_merchant) {
        this.id_merchant = id_merchant;
    }

    public String getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(String total_biaya) {
        this.total_biaya = total_biaya;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFullnama() {
        return fullnama;
    }

    public void setFullnama(String fullnama) {
        this.fullnama = fullnama;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getIdpelanggan() {
        return idpelanggan;
    }

    public void setIdpelanggan(String idpelanggan) {
        this.idpelanggan = idpelanggan;
    }

    public String getIddriver() {
        return iddriver;
    }

    public void setIddriver(String iddriver) {
        this.iddriver = iddriver;
    }


}
