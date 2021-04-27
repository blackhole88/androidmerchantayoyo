package com.ayoyo.merchant.json.linkaja;

import java.util.List;

public class HistoryDisbursementResponse {
    public String code;
    public String message;
    public List<History> data;

    public class History{
        public String transid;
        public String transdatetime;
        public String linkaja_number;
        public String amount;
        public String status1;
        public String status2;
        public String transactionstatus;
    }
}
