package com.ayoyo.merchant.json.linkaja;

import java.util.List;

public class ListTransaksiLinkajaResponse {
    public String code;
    public String message;
    public List<LinkAjaTransaksi> data;

    public class LinkAjaTransaksi{
        public String id;
        public String nama_pelanggan;
        public String waktu_order;
        public String biaya_akhir;
        public String biaya_potongkomisi;
        public String tip;
        public String total;
        public String status;
    }
}
