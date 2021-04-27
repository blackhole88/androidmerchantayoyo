package com.ayoyo.merchant.json.mlm;

import java.util.List;

public class MlmKomisiTransaksi {
    public String code;
    public String message;
    public List<KomisiTransaksi> transaksi;

    public class KomisiTransaksi{
        public String id;
        public String id_transaksi;
        public String id_pelanggan;
        public String level;
        public String get_komisi;
        public String tanggal;
    }
}
