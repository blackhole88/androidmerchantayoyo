package com.ayoyo.merchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ayoyo.merchant.models.KategoriItemModel;
import com.ayoyo.merchant.models.KategoriItemNonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class KategoriResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("dataactive")
    @Expose
    private List<KategoriItemModel> data = new ArrayList<>();

    @SerializedName("datanonactive")
    @Expose
    private List<KategoriItemNonModel> datanon = new ArrayList<>();

    @SerializedName("totalitemactive")
    @Expose
    private String totalitemactive;

    @SerializedName("totalitemnonactive")
    @Expose
    private String totalitemnonactive;

    @SerializedName("totalitempromo")
    @Expose
    private String totalitempromo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KategoriItemModel> getData() {
        return data;
    }

    public void setData(List<KategoriItemModel> data) {
        this.data = data;
    }

    public List<KategoriItemNonModel> getDatanon() {
        return datanon;
    }

    public void setDatanon(List<KategoriItemNonModel> datanon) {
        this.datanon = datanon;
    }

    public String getTotalitemactive() {
        return totalitemactive;
    }

    public void setTotalitemactive(String totalitemactive) {
        this.totalitemactive = totalitemactive;
    }

    public String getTotalitemnonactive() {
        return totalitemnonactive;
    }

    public void setTotalitemnonactive(String totalitemnonactive) {
        this.totalitemnonactive = totalitemnonactive;
    }

    public String getTotalitempromo() {
        return totalitempromo;
    }

    public void setTotalitempromo(String totalitempromo) {
        this.totalitempromo = totalitempromo;
    }
}
