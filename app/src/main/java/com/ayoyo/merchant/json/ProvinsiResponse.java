package com.ayoyo.merchant.json;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProvinsiResponse {
    @Expose
    @SerializedName("code")
    public String code;

    @Expose
    @SerializedName("message")
    public String mesage;

    @Expose
    @SerializedName("data")
    public List<Provinsi> data = new ArrayList<>();

    public static class Provinsi{
        public String id;
        public String name;

        @NonNull
        @Override
        public String toString() {
            return this.name;
        }
    }
}




