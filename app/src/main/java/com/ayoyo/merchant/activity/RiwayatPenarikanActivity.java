package com.ayoyo.merchant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.item.RiwayatPenarikanItem;
import com.ayoyo.merchant.json.WalletRequestJson;
import com.ayoyo.merchant.json.WalletResponseJson;
import com.ayoyo.merchant.models.User;
import com.ayoyo.merchant.models.WalletModel;
import com.ayoyo.merchant.utils.Utility;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPenarikanActivity extends AppCompatActivity {
    @BindView(R.id.rvPenarikan)
    RecyclerView rvPenarikan;
    @BindView(R.id.btnKembali)
    Button btnKembali;
    @BindView(R.id.tvDana)
    TextView tvDana;
    @BindView(R.id.etSaldo)
    EditText etSaldo;
    @BindView(R.id.tvTarikSaldo)
    TextView tvTarikSaldo;
    @BindView(R.id.tvKirimSaldo)
    TextView tvKirimSaldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_penarikan);
        ButterKnife.bind(this);

        String saldo = getIntent().getStringExtra("saldo");

        try {
            Utility.currencyTXT(tvDana, saldo, this);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        btnKembali.setOnClickListener(v -> finish());

        etSaldo.addTextChangedListener(Utility.currencyTWWithout00(etSaldo, this));

        getData();

        tvTarikSaldo.setOnClickListener(v -> {
            Intent i = new Intent(RiwayatPenarikanActivity.this, WithdrawActivity.class);
            i.putExtra("nominal", etSaldo.getText().toString());
            i.putExtra("type", "withdraw");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        tvKirimSaldo.setOnClickListener(v -> {
            Intent i = new Intent(RiwayatPenarikanActivity.this, WithdrawActivity.class);
            i.putExtra("nominal", etSaldo.getText().toString());
            i.putExtra("type", "topup");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

    public void getData(){
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        WalletRequestJson request = new WalletRequestJson();
        request.setId(loginUser.getId());
        MerchantService userService = ServiceGenerator.createService(
                MerchantService.class);
        userService.wallet(request).enqueue(new Callback<WalletResponseJson>() {
            @Override
            public void onResponse(Call<WalletResponseJson> call, Response<WalletResponseJson> response) {

                if(response.body().getData()!=null) {
                    List<WalletModel> list = new ArrayList<>();
                    WalletModel title = new WalletModel();
                    title.setTersedia("Tersedia");
                    title.setJumlah("Jumlah");
                    title.setWaktu("Waktu");
                    list.add(title);
                    for(WalletModel datas : response.body().getData()){
                        if(datas.getType().equals("withdraw")){
                            list.add(datas);

                        }
                    }

                    RiwayatPenarikanItem adapter = new RiwayatPenarikanItem(RiwayatPenarikanActivity.this, list);
                    rvPenarikan.setAdapter(adapter);
                    rvPenarikan.setLayoutManager(new LinearLayoutManager(RiwayatPenarikanActivity.this));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WalletResponseJson> call, Throwable t) {

            }
        });
    }
}