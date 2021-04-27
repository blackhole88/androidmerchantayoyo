package com.ayoyo.merchant.utils.api.service;

import com.ayoyo.merchant.json.ActivekatRequestJson;
import com.ayoyo.merchant.json.AddEditItemRequestJson;
import com.ayoyo.merchant.json.AddEditKategoriRequestJson;
import com.ayoyo.merchant.json.BankResponseJson;
import com.ayoyo.merchant.json.CekOtpRequest;
import com.ayoyo.merchant.json.ChangePassRequestJson;
import com.ayoyo.merchant.json.DetailRequestJson;
import com.ayoyo.merchant.json.DetailTransResponseJson;
import com.ayoyo.merchant.json.EditMerchantRequestJson;
import com.ayoyo.merchant.json.EditProfileRequestJson;
import com.ayoyo.merchant.json.GetFiturResponseJson;
import com.ayoyo.merchant.json.GetOnRequestJson;
import com.ayoyo.merchant.json.GetOtpRequest;
import com.ayoyo.merchant.json.GetOtpResponse;
import com.ayoyo.merchant.json.HistoryRequestJson;
import com.ayoyo.merchant.json.HistoryResponseJson;
import com.ayoyo.merchant.json.HomeRequestJson;
import com.ayoyo.merchant.json.HomeResponseJson;
import com.ayoyo.merchant.json.ItemRequestJson;
import com.ayoyo.merchant.json.ItemResponseJson;
import com.ayoyo.merchant.json.KategoriRequestJson;
import com.ayoyo.merchant.json.KategoriResponseJson;
import com.ayoyo.merchant.json.KecamatanRequest;
import com.ayoyo.merchant.json.KecamatanResponse;
import com.ayoyo.merchant.json.KotaRequest;
import com.ayoyo.merchant.json.KotaResponse;
import com.ayoyo.merchant.json.LoginRequestJson;
import com.ayoyo.merchant.json.LoginResponseJson;
import com.ayoyo.merchant.json.PrivacyRequestJson;
import com.ayoyo.merchant.json.PrivacyResponseJson;
import com.ayoyo.merchant.json.ProvinsiResponse;
import com.ayoyo.merchant.json.RegisterRequestJson;
import com.ayoyo.merchant.json.RegisterResponseJson;
import com.ayoyo.merchant.json.ResponseJson;
import com.ayoyo.merchant.json.TopupRequestJson;
import com.ayoyo.merchant.json.TopupResponseJson;
import com.ayoyo.merchant.json.WalletRequestJson;
import com.ayoyo.merchant.json.WalletResponseJson;
import com.ayoyo.merchant.json.WithdrawRequestJson;
import com.ayoyo.merchant.json.linkaja.LinkajaDisbursementRequest;
import com.ayoyo.merchant.json.linkaja.LinkajaDisbursementResponse;
import com.ayoyo.merchant.json.linkaja.ListTransaksiLinkajaRequest;
import com.ayoyo.merchant.json.linkaja.ListTransaksiLinkajaResponse;
import com.ayoyo.merchant.json.mlm.MlmDownlineResponse;
import com.ayoyo.merchant.json.mlm.MlmKomisiTransaksi;
import com.ayoyo.merchant.json.mlm.MlmRequest;
import com.ayoyo.merchant.json.mlm.MlmTipeResponse;
import com.ayoyo.merchant.json.mlm.MlmUpdateStatusUserRequest;
import com.ayoyo.merchant.json.mlm.MlmUpdateStatusUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public interface MerchantService {
    @POST("linkaja/list_transaksi_with_linkaja")
    Call<ListTransaksiLinkajaResponse> getLinkAjaTransaksi(@Body ListTransaksiLinkajaRequest request);

    @POST("linkaja/disbursement")
    Call<LinkajaDisbursementResponse> postDisbursement(@Body LinkajaDisbursementRequest request);

    @POST("pelanggan/mlm_status_user_update")
    Call<MlmUpdateStatusUserResponse> updateStatusUser(@Body MlmUpdateStatusUserRequest request);

    @POST("pelanggan/mlm_tipe_user")
    Call<MlmTipeResponse> getMlmTipe();

    @POST("pelanggan/mlm_downline2")
    Call<MlmDownlineResponse> getMlmDownline(@Body MlmRequest mlmRequest);

    @POST("pelanggan/mlm_komisi_transaksi")
    Call<MlmKomisiTransaksi> getMlmKomisiTransaksi(@Body MlmRequest mlmRequest);

    @GET("merchant/kategorimerchant")
    Call<GetFiturResponseJson> getFitur();

    @POST("otp/generate_otp_wa")
    Call<GetOtpResponse> getOtpWa(@Body GetOtpRequest request);

    @POST("otp/cek_otp")
    Call<GetOtpResponse> cekOtp(@Body CekOtpRequest request);

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("merchant/kategorimerchantbyfitur")
    Call<GetFiturResponseJson> getKategori(@Body HistoryRequestJson param);

    @POST("merchant/onoff")
    Call<ResponseJson> turnon(@Body GetOnRequestJson param);

    @POST("merchant/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("merchant/provinsi")
    Call<ProvinsiResponse> getProvinsi();

    @POST("merchant/kota")
    Call<KotaResponse> getKota(@Body KotaRequest kotaRequest);

    @POST("merchant/kecamatan")
    Call<KecamatanResponse> getKecamatan(@Body KecamatanRequest kecamatanRequest);

    @Headers("Accept-Encoding:identity")
    @POST("merchant/register_merchant")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @POST("merchant/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("merchant/edit_profile")
    Call<LoginResponseJson> editprofile(@Body EditProfileRequestJson param);

    @POST("merchant/edit_merchant")
    Call<LoginResponseJson> editmerchant(@Body EditMerchantRequestJson param);

    @POST("merchant/home")
    Call<HomeResponseJson> home(@Body HomeRequestJson param);

    @POST("merchant/history")
    Call<HistoryResponseJson> history(@Body HistoryRequestJson param);

    @POST("merchant/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("merchant/kategori")
    Call<KategoriResponseJson> kategori(@Body KategoriRequestJson param);

    @POST("merchant/item")
    Call<ItemResponseJson> itemlist(@Body ItemRequestJson param);

    @POST("merchant/active_kategori")
    Call<ResponseJson> activekategori(@Body ActivekatRequestJson param);

    @POST("merchant/active_item")
    Call<ResponseJson> activeitem(@Body ActivekatRequestJson param);

    @POST("merchant/add_kategori")
    Call<ResponseJson> addkategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/edit_kategori")
    Call<ResponseJson> editkategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/delete_kategori")
    Call<ResponseJson> deletekategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/add_item")
    Call<ResponseJson> additem(@Body AddEditItemRequestJson param);

    @POST("merchant/edit_item")
    Call<ResponseJson> edititem(@Body AddEditItemRequestJson param);

    @POST("merchant/delete_item")
    Call<ResponseJson> deleteitem(@Body AddEditItemRequestJson param);

    @POST("pelanggan/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("merchant/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("merchant/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("merchant/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

}
