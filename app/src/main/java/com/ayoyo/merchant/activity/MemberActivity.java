package com.ayoyo.merchant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.item.MlmItem;
import com.ayoyo.merchant.json.mlm.MlmDownline;
import com.ayoyo.merchant.json.mlm.MlmDownlineResponse;
import com.ayoyo.merchant.json.mlm.MlmKomisiTransaksi;
import com.ayoyo.merchant.json.mlm.MlmRequest;
import com.ayoyo.merchant.json.mlm.MlmTipeResponse;
import com.ayoyo.merchant.models.User;
import com.ayoyo.merchant.utils.Utility;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;
//import com.paypal.android.sdk.payments.PaymentMethodActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberActivity extends AppCompatActivity {
    @BindView(R.id.rv_downline)
    RecyclerView rvDownline;
    @BindView(R.id.tvMlmSaldo)
    TextView tvMlmSaldo;
    @BindView(R.id.clFirstTime)
    ConstraintLayout clFirst;
    @BindView(R.id.clCommission)
    ConstraintLayout clCommission;
    @BindView(R.id.tvIsiUlang)
    TextView tvIsiUlang;
    @BindView(R.id.btnRiwayat)
    Button btnRiwayat;
    @BindView(R.id.btnRegisterPremium)
    Button btnRegPremium;
    String saldo;
    @BindView(R.id.tvHargaMember)
    TextView tvHargaMember;
    Long premiumPrice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);
        getPremiumPrice();
        btnRiwayat.setOnClickListener(v -> {
            Intent intent = new Intent(MemberActivity.this, RiwayatPenarikanActivity.class);
            intent.putExtra("saldo", saldo);
            startActivity(intent);
        });

        tvIsiUlang.setOnClickListener(v -> {
            Intent intent = new Intent(MemberActivity.this, WithdrawActivity.class);
            intent.putExtra("type","topup");
            startActivity(intent);
        });

//        isPremiumUser(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void isPremiumUser(boolean isPremium){
        if(isPremium) {
            clCommission.setVisibility(View.VISIBLE);
            clFirst.setVisibility(View.GONE);
            showCommission();
        }
        else {
            clFirst.setVisibility(View.VISIBLE);
            clCommission.setVisibility(View.GONE);
            showFirstTime();
        }
    }

    public void showFirstTime(){
        btnRegPremium.setOnClickListener( v -> {
            Intent intent = new Intent(MemberActivity.this, PaymentMethodActivity.class);
            intent.putExtra("saldo", saldo);
            intent.putExtra("premiumprice", premiumPrice);
            startActivity(intent);
        });
    }

    public void showCommission(){

    }

    public void getPremiumPrice(){
        MerchantService userService = ServiceGenerator.createService(MerchantService.class);
        userService.getMlmTipe().enqueue(new Callback<MlmTipeResponse>() {
            @Override
            public void onResponse(Call<MlmTipeResponse> call, Response<MlmTipeResponse> response) {
                if(response.isSuccessful() && response.body().data!=null) {
                    premiumPrice = Long.parseLong(response.body().data.get(1).harga);
                    Utility.currencyTXT(tvHargaMember, premiumPrice+"", MemberActivity.this);

                    tvHargaMember.setText(tvHargaMember.getText()+"/tahun");
                }

            }

            @Override
            public void onFailure(Call<MlmTipeResponse> call, Throwable t) {

            }
        });


    }

    public void getData(){
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        MlmRequest mlmRequest = new MlmRequest();
        mlmRequest.id_pelanggan = userLogin.getId();

        MerchantService userService = ServiceGenerator.createService(MerchantService.class);
        userService.getMlmDownline(mlmRequest).enqueue(new Callback<MlmDownlineResponse>() {
            @Override
            public void onResponse(Call<MlmDownlineResponse> call, Response<MlmDownlineResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().message.equals("found")){
                        if(response.body().data.size()>0 && response.body().data.get(0).type.equals("Premium")){
                            isPremiumUser(true);

                            MlmItem mlmItem = new MlmItem(MemberActivity.this, response.body().downline);
                            rvDownline.setLayoutManager(new LinearLayoutManager(MemberActivity.this));
                            rvDownline.setAdapter(mlmItem);
                            userService.getMlmKomisiTransaksi(mlmRequest).enqueue(new Callback<MlmKomisiTransaksi>() {
                                @Override
                                public void onResponse(Call<MlmKomisiTransaksi> call, Response<MlmKomisiTransaksi> response) {
                                    for (MlmKomisiTransaksi.KomisiTransaksi komisi : response.body().transaksi){
                                        MlmItem.dataList.add(new MlmDownline(komisi.id, komisi.id_pelanggan, "", komisi.get_komisi, "", komisi.level, komisi.tanggal, "Komisi Order", ""));
                                    }
                                    mlmItem.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<MlmKomisiTransaksi> call, Throwable t) {

                                }
                            });

                        }
                        else{
                            isPremiumUser(false);
                            showFirstTime();


                        }
//                        runOnUiThread(() -> {
//                            Utility.currencyTXT(tvMlmSaldo, response.body().total_saldo, MemberActivity.this);
////                            tvMlmSaldo.setText(response.body().total_saldo);
//                        });
                        saldo = response.body().total_saldo;
                        Utility.currencyTXT(tvMlmSaldo, response.body().total_saldo, MemberActivity.this);




                    }
                }

            }

            @Override
            public void onFailure(Call<MlmDownlineResponse> call, Throwable t) {

            }
        });
    }
}
