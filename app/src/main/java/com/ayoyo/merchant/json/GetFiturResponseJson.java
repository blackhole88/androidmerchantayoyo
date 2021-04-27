package com.ayoyo.merchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ayoyo.merchant.models.FiturModel;
import com.ayoyo.merchant.models.KategoriModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class GetFiturResponseJson {

    @Expose
    @SerializedName("fitur")
    private List<FiturModel> fitur = new ArrayList<>();

    @Expose
    @SerializedName("kategori")
    private List<KategoriModel> kategori = new ArrayList<>();

    public List<FiturModel> getData() {
        return fitur;
    }

    public void setData(List<FiturModel> fitur) {
        this.fitur = fitur;
    }

    public List<KategoriModel> getKategori() {
        return kategori;
    }

    public void setKategori(List<KategoriModel> kategori) {
        this.kategori = kategori;
    }



}
