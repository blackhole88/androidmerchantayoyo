package com.ayoyo.merchant.json;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KecamatanResponse {
    @Expose
    @SerializedName("code")
    public String code;
    @Expose
    @SerializedName("message")
    public String mesage;

    @Expose
    @SerializedName("data")
    public List<Kecamatan> data = new ArrayList<>();

    public static class Kecamatan{
        public String id;
        public String kota_id;
        public String nama;

        @NonNull
        @Override
        public String toString() {
            return nama;
        }
    }

}


