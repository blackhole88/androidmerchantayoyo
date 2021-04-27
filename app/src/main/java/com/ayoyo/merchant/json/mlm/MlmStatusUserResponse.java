package com.ayoyo.merchant.json.mlm;

import java.util.List;

public class MlmStatusUserResponse {
    public String code;
    public String message;
    public List<TypeAccount> data;

    public class TypeAccount{
        public String id;
        public String type;
        public String harga;
    }

}
