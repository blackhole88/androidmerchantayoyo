package com.ayoyo.merchant.json.mlm;

public class MlmDownline{
    public String id;
    public String id_pelanggan;
    public String referral_code;
    public String komisi;
    public String id_tipe;
    public String level;
    public String tanggal;
    public String fullnama;
    public String type;

    public MlmDownline(String id, String id_pelanggan, String referral_code, String komisi, String id_tipe, String level, String tanggal, String fullnama, String type) {
        this.id = id;
        this.id_pelanggan = id_pelanggan;
        this.referral_code = referral_code;
        this.komisi = komisi;
        this.id_tipe = id_tipe;
        this.level = level;
        this.tanggal = tanggal;
        this.fullnama = fullnama;
        this.type = type;
    }
}
