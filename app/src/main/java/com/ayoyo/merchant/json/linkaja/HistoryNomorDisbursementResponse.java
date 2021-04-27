package com.ayoyo.merchant.json.linkaja;

import java.util.List;

public class HistoryNomorDisbursementResponse {
    public String code;
    public String message;
    public List<Nomor> data;

    public class Nomor{
        public String nomor;
    }
}
