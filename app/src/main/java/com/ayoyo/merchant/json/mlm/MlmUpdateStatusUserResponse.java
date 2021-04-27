package com.ayoyo.merchant.json.mlm;

public class MlmUpdateStatusUserResponse {
    public String code;
    public String message;
    public StatusData data;

    class StatusData{
        public String notifikasi;
        public boolean status;
    }


}
