package com.ayoyo.merchant.json;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KotaResponse {
    @Expose
    @SerializedName("code")
    public String code;
    @Expose
    @SerializedName("message")
    public String mesage;

    @Expose
    @SerializedName("data")
    public List<Kota> data = new ArrayList<Kota>();

    public static class Kota {
        public String id;
        public String province_id;
        public String nama;

        @NonNull
        @Override
        public String toString() {
            return nama;
        }
    }
}


