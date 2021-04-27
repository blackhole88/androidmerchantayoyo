package com.ayoyo.merchant.json.mlm;

import java.util.List;

public class MlmTipeResponse {
    public String code;
    public String message;
    public List<MlmTipe> data;

    public class MlmTipe{
        public String id;
        public String type;
        public String harga;
    }
}
