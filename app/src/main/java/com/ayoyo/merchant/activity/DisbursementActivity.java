package com.ayoyo.merchant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.item.DisbursementItem;
import com.ayoyo.merchant.json.linkaja.ListTransaksiLinkajaRequest;
import com.ayoyo.merchant.json.linkaja.ListTransaksiLinkajaResponse;
import com.ayoyo.merchant.models.User;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisbursementActivity extends AppCompatActivity {
    @BindView(R.id.rvBelumDitarik)
    RecyclerView rvBelumDitarik;
    @BindView(R.id.rvAllTransaksi)
    RecyclerView rvAllTransaksi;
    @BindView(R.id.rlprogress)
    RelativeLayout loading;
    DisbursementItem adapterBelumDitarik;
    DisbursementItem adapterAll;
    @BindView(R.id.back_btn)
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);
        ButterKnife.bind(this);


        backButton.setOnClickListener(v -> onBackPressed());
        getDataBelumDitarik();
        getDataAll();
    }

    public void getDataBelumDitarik(){
        loading.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        ListTransaksiLinkajaRequest request = new ListTransaksiLinkajaRequest();
        request.id_user = loginUser.getId();
        request.tipe = "notdis";
        service.getLinkAjaTransaksi(request).enqueue(new Callback<ListTransaksiLinkajaResponse>() {
            @Override
            public void onResponse(Call<ListTransaksiLinkajaResponse> call, Response<ListTransaksiLinkajaResponse> response) {
                loading.setVisibility(View.GONE);
                if(response.body()!=null && response.body().data.size()>0) {
                    adapterBelumDitarik = new DisbursementItem(DisbursementActivity.this, response.body().data);
                    rvBelumDitarik.setLayoutManager(new LinearLayoutManager(DisbursementActivity.this));
                    rvBelumDitarik.setAdapter(adapterBelumDitarik);
                    adapterBelumDitarik.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListTransaksiLinkajaResponse> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(DisbursementActivity.this, "Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getDataAll(){
        loading.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        ListTransaksiLinkajaRequest request = new ListTransaksiLinkajaRequest();
        request.id_user = loginUser.getId();
        request.tipe = "dis";
        service.getLinkAjaTransaksi(request).enqueue(new Callback<ListTransaksiLinkajaResponse>() {
            @Override
            public void onResponse(Call<ListTransaksiLinkajaResponse> call, Response<ListTransaksiLinkajaResponse> response) {
                loading.setVisibility(View.GONE);
                if(response.body()!=null && response.body().data.size()>0) {
                    adapterAll = new DisbursementItem(DisbursementActivity.this, response.body().data);
                    rvAllTransaksi.setLayoutManager(new LinearLayoutManager(DisbursementActivity.this));
                    rvAllTransaksi.setAdapter(adapterAll);
                    adapterAll.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListTransaksiLinkajaResponse> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(DisbursementActivity.this, "Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show();
            }
        });
    }
}
