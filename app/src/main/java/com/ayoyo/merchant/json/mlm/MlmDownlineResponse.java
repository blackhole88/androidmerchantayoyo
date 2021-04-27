package com.ayoyo.merchant.json.mlm;

import java.util.List;

public class MlmDownlineResponse {
    public String code;
    public String message;
    public String wallet_saldo;
    public String total_saldo;
    public List<MlmDetail> data;
    public List<MlmDownline> downline;

}
