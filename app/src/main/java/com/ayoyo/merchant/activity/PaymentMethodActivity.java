package com.ayoyo.merchant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.item.PaymentItem;
import com.ayoyo.merchant.json.linkaja.EnableEddRequest;
import com.ayoyo.merchant.json.linkaja.EnableEddResponse;
import com.ayoyo.merchant.json.mlm.MlmUpdateStatusUserRequest;
import com.ayoyo.merchant.json.mlm.MlmUpdateStatusUserResponse;
import com.ayoyo.merchant.models.PaymentMethodModel;
import com.ayoyo.merchant.models.User;
import com.ayoyo.merchant.utils.Utility;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;
import com.ayoyo.merchant.utils.api.service.PaymentService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends AppCompatActivity {
    @BindView(R.id.rvPaymentMethod)
    RecyclerView rvPaymentMethod;
    @BindView(R.id.btnBayar)
    Button btnBayar;
    public static String namaMethod = "AyoDompet";
    private Long premiumPrice;
    private Bitmap imageSaldo;
    private boolean useSaldo = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        ButterKnife.bind(this);

        String saldo = getIntent().getStringExtra("saldo");
        premiumPrice = getIntent().getLongExtra("premiumprice", 0);

        Log.d("saldos", saldo);
        PaymentMethodModel model = new PaymentMethodModel();
        model.methodName = "AyoDompet";
        model.value = saldo;
        model.isActive = true;

        PaymentMethodModel model1 = new PaymentMethodModel();
        model1.methodName = "Tunai";
        model1.isActive = false;

        PaymentMethodModel model2 = new PaymentMethodModel();
        model2.methodName = "LinkAja";
        model2.isActive = false;
        model2.imageValue = imageSaldo;

        List<PaymentMethodModel> list = new ArrayList();
        list.add(model);
        list.add(model2);

        PaymentItem paymentItem = new PaymentItem(this, list, new PaymentItem.OnPaymentClickListener() {
            @Override
            public void onPaymentClick(PaymentMethodModel model) {
                Log.d("TEST","bayar pakai: " + model.methodName.toString());
                if(model.methodName.equals("AyoDompet")) useSaldo = true;
                else useSaldo = false;
            }
        });
        rvPaymentMethod.setLayoutManager(new LinearLayoutManager(this));
        rvPaymentMethod.setAdapter(paymentItem);

        btnBayar.setOnClickListener(v -> {
            User userLogin = BaseApp.getInstance(this).getLoginUser();
            MlmUpdateStatusUserRequest mlmRequest = new MlmUpdateStatusUserRequest();
            mlmRequest.id_pelanggan = userLogin.getId();
            mlmRequest.id_tipe = "2";
            if(useSaldo && namaMethod.equals("AyoDompet") && Utility.isSaldoEnough(premiumPrice, PaymentMethodActivity.this) ) {


                MerchantService userService = ServiceGenerator.createService(MerchantService.class);
                userService.updateStatusUser(mlmRequest).enqueue(new Callback<MlmUpdateStatusUserResponse>() {
                    @Override
                    public void onResponse(Call<MlmUpdateStatusUserResponse> call, Response<MlmUpdateStatusUserResponse> response) {
                        Toast.makeText(PaymentMethodActivity.this, "Pembayaran Berhasil, Akun Anda menjadi Premium", Toast.LENGTH_LONG).show();

                        finish();
                    }

                    @Override
                    public void onFailure(Call<MlmUpdateStatusUserResponse> call, Throwable t) {

                    }
                });
            }
            else if (!useSaldo){
                bayarPremiumLinkaja(mlmRequest);
            }
        });
    }


    private void bayarPremiumLinkaja(MlmUpdateStatusUserRequest request){
        PaymentService service = ServiceGenerator.createService(PaymentService.class);
        service.updateStatusUser(request).enqueue(new Callback<EnableEddResponse>() {
            @Override
            public void onResponse(Call<EnableEddResponse> call, Response<EnableEddResponse> response) {
//                showLoading(false);
                if(response.body()!=null) {
                    if (response.body().message.equals("failed") && response.body().data.contains("edd")) {
                        activateEdd();
                    } else {
                        Intent intent = new Intent(PaymentMethodActivity.this, WebcheckoutActivity.class);
                        intent.putExtra("url", response.body().data);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<EnableEddResponse> call, Throwable t) {
//                showLoading(false);
            }
        });
    }

    private void activateEdd(){
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        PaymentService service = ServiceGenerator.createService(PaymentService.class);
        EnableEddRequest request = new EnableEddRequest();
        request.id_user = loginUser.getId();
        request.no_hp = loginUser.getNoTelepon();
        service.enableEdd(request).enqueue(new Callback<EnableEddResponse>() {
            @Override
            public void onResponse(Call<EnableEddResponse> call, Response<EnableEddResponse> response) {
//                showLoading(false);
                Intent intent = new Intent(PaymentMethodActivity.this, WebcheckoutActivity.class);
                intent.putExtra("url", response.body().data);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<EnableEddResponse> call, Throwable t) {

            }
        });
    }
}