package com.ayoyo.merchant.utils.api.service;

import com.ayoyo.merchant.json.linkaja.BayarTransaksiRequest;
import com.ayoyo.merchant.json.linkaja.CompareSaldoRequest;
import com.ayoyo.merchant.json.linkaja.EnableEddRequest;
import com.ayoyo.merchant.json.linkaja.EnableEddResponse;
import com.ayoyo.merchant.json.linkaja.GetSaldoRequest;
import com.ayoyo.merchant.json.linkaja.IsiSaldoRequest;
import com.ayoyo.merchant.json.mlm.MlmUpdateStatusUserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentService {
    @POST("linkaja/do_enable_edd")
    Call<EnableEddResponse> enableEdd(@Body EnableEddRequest param);

    @POST("linkaja/bayar_transaksi")
    Call<EnableEddResponse> bayarTransaksi(@Body BayarTransaksiRequest param);



//    @POST("https://checkout.linkaja.id:8443/v1/payment")
//    @POST
//    Call<EnableEddResponse> directCheckout(@Url String url, @Body DirectCheckoutRequest request);

    @POST("linkaja/get_saldolinkaja")
    Call<EnableEddResponse> getSaldoLinkaja(@Body GetSaldoRequest saldoRequest);

    @POST("linkaja/compare_saldolinkaja")
    Call<EnableEddResponse> compareSaldo(@Body CompareSaldoRequest compareSaldoRequest);

    @POST("linkaja/isi_saldo")
    Call<EnableEddResponse> isiSaldo(@Body IsiSaldoRequest isiSaldoRequest);

    @POST("linkaja/update_status_user")
    Call<EnableEddResponse> updateStatusUser(@Body MlmUpdateStatusUserRequest isiSaldoRequest);
}

