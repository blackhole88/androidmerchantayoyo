package com.ayoyo.merchant.json.mlm;

import java.util.List;

public class MlmKomisiResponse {
    public String code;
    public String message;
    public MlmKomisiTipe data;

    class MlmKomisiTipe{
        public List<Standard> standard;
        public List<Detail> detail;
    }

    class Standard{
        public String type;
        public String total_komisi;
    }

    class Detail{
        public String fullnama;
        public String type;
        public String komisi;
    }
}
