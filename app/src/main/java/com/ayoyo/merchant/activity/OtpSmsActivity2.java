package com.ayoyo.merchant.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.json.CekOtpRequest;
import com.ayoyo.merchant.json.GetOtpRequest;
import com.ayoyo.merchant.json.GetOtpResponse;
import com.ayoyo.merchant.json.LoginRequestJson;
import com.ayoyo.merchant.json.LoginResponseJson;
import com.ayoyo.merchant.json.RegisterRequestJson;
import com.ayoyo.merchant.json.RegisterResponseJson;
import com.ayoyo.merchant.models.User;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpSmsActivity2 extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    private String phoneVerificationId;
    String phoneNumber;
    boolean verify;
    private static String idOtp;
    private String TAG = "OTPSMSACTIVITY";
    @BindView(R.id.numone)
    EditText numOne;
    @BindView(R.id.numtwo)
    EditText numTwo;
    @BindView(R.id.numthree)
    EditText numThree;
    @BindView(R.id.numfour)
    EditText numFour;
    @BindView(R.id.numfive)
    EditText numFive;
    @BindView(R.id.numsix)
    EditText numSix;
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;

    @BindView(R.id.button)
    Button btnResend;

    boolean isRegister;
    RegisterRequestJson registerRequestJson;
    int fcmTimer = 120000;
    CountDownTimer timerplay = new CountDownTimer(fcmTimer, 1000) {

        @SuppressLint("SetTextI18n")
        public void onTick(long millisUntilFinished) {
            btnResend.setText("Kirim Ulang SMS OTP (" + millisUntilFinished /1000+")");
        }


        public void onFinish() {

            btnResend.setText("Kirim Ulang SMS OTP");
            btnResend.setEnabled(true);

        }
    }.start();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_sms);

        ButterKnife.bind(this);
        btnResend.setEnabled(false);
        timerplay.start();

        idOtp = "";

        registerRequestJson = getIntent().getParcelableExtra("data");
        if(registerRequestJson ==null) isRegister = false;
        else isRegister = true;

        fbAuth = FirebaseAuth.getInstance();
        codenumber();
        phoneNumber = getIntent().getStringExtra("phone");
        Log.d("phonenumber", phoneNumber);

        btnResend.setOnClickListener(v ->{
            v.setEnabled(false);
            timerplay.start();
            resendCode();
            Toast.makeText(OtpSmsActivity2.this, "Kode verifikasi sudah dikirim", Toast.LENGTH_SHORT).show();
//            resendCode();
        } );
//
       /*
        if(isRegister)  doRegister();
        else doLogin(phoneNumber);
        */

        getOtp();
    }

    public void doLogin(String phoneNumber){
        timerplay.cancel();
        try {
            String token = FirebaseInstanceId.getInstance().getToken();

            LoginRequestJson requestJson = new LoginRequestJson();
            phoneNumber = phoneNumber.replace("+", "");
            requestJson.setNotelepon(phoneNumber);
            requestJson.setRegId(token);
            MerchantService service = ServiceGenerator.createService(MerchantService.class);
            service.login(requestJson).enqueue(new Callback<LoginResponseJson>() {
                @Override
                public void onResponse(Call<LoginResponseJson> call, Response<LoginResponseJson> response) {
                    progresshide();
                    if (response.isSuccessful()) {
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                            User user = response.body().getData().get(0);
                            saveUser(user);
                            Intent intent = new Intent(OtpSmsActivity2.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
//                            Toast.makeText(OtpSmsActivity.this, "Pendaftaran Berhasil", Toast.LENGTH_LONG).show();

                        } else {
                            notif("Proses Login Gagal");
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseJson> call, Throwable t) {
                    progresshide();
                    notif("Proses Login Gagal, Cek Koneksi Internet");
                }
            });
        }
        catch (Exception e){
            progresshide();
            e.printStackTrace();
        }

    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(this).setLoginUser(user);
    }

    public void Send_Number_tofirebase(String phoneNumber) {
        Log.d(TAG, "Send_Number_tofirebase");
        setUpVerificatonCallbacks();
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,
//                120,
//                TimeUnit.SECONDS,
//                this,
//                verificationCallbacks);
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phoneNumber)
                .setTimeout((long)fcmTimer, TimeUnit.MILLISECONDS)
                .setActivity(this)
                .setCallbacks(verificationCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void setUpVerificatonCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progresshide();
                Log.d("respon", e.toString());

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    notif("Silahkan coba beberapa saat lagi!");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    notif("Silahkan coba beberapa saat lagi");
                }
                else{
                    notif("Verifikasi Gagal!");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                resendToken = token;
//                sendTo.setText("Send to ( " + phoneNumber + " )");
                progresshide();
//                viewFlipper.setInAnimation(RegisterActivity.this, R.anim.from_right);
//                viewFlipper.setOutAnimation(RegisterActivity.this, R.anim.to_left);
//                viewFlipper.setDisplayedChild(1);

            }
        };
    }

    public void getOtp(){
        progressshow();
        GetOtpRequest request = new GetOtpRequest();
        request.no_hp = phoneNumber;
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        service.getOtpWa(request).enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {
                progresshide();
                idOtp = response.body().data;
            }

            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {
                progresshide();
                onBackPressed();
                notif("Gagal mengirimkan OTP, silahkan coba kembali setelah beberapa saat");
            }
        });
    }

    public void verifyOtp(){
        CekOtpRequest request = new CekOtpRequest();
        request.id = idOtp;
        request.no_hp = phoneNumber;
        request.kode = "" + numOne.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() + numFour.getText().toString() + numFive.getText().toString() + numSix.getText().toString();

        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        service.cekOtp(request).enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().message.equals("success")) {
                        if(isRegister){
                            doRegister();
                        }
                        else doLogin(phoneNumber);
                    } else {
                        progresshide();
                        notif(response.body().message);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {
                onBackPressed();
                notif("Periksa koneksi internet Anda");
            }
        });
    }




    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        try {
            fbAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                if(isRegister) doRegister();
                                else doLogin(phoneNumber);

                            } else {
                                if (task.getException() instanceof
                                        FirebaseAuthInvalidCredentialsException) {

//                                    Toast.makeText(OtpSmsActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    progresshide();
                                    notif("Kode OTP salah!");
                                }
                            }
                        }
                    });
        }
        catch (Exception e){
//            Toast.makeText(this, "211"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void doRegister(){
        timerplay.cancel();
        MerchantService service = ServiceGenerator.createService(MerchantService.class, registerRequestJson.getEmail(), registerRequestJson.getNo_telepon());
        if(RegisterActivity2.imageByteArray!=null) registerRequestJson.setFoto(Base64.encodeToString(RegisterActivity2.imageByteArray, Base64.DEFAULT));
        if(RegisterActivity2.imageByteArrayktp!=null)registerRequestJson.setFoto_ktp(Base64.encodeToString(RegisterActivity2.imageByteArrayktp, Base64.DEFAULT));
        service.register(registerRequestJson).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponseJson> call, @NonNull Response<RegisterResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {

//                        Intent intent = new Intent(OtpSmsActivity.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
                        Intent intent = new Intent(OtpSmsActivity2.this, RegisterResultActivity.class);
                        startActivity(intent);
                        finish();

//                        doLogin(registerRequestJson.getNoTelepon());
//                        Toast.makeText(OtpSmsActivity.this, response.body().getData().get(0), Toast.LENGTH_SHORT).show();

                    } else {
                        notif(response.body().getMessage());
                    }
                } else {
                    notif("error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("Terjadi Kesalahan!");
            }
        });
    }

    public void notif(String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OtpSmsActivity2.this, text, Toast.LENGTH_LONG).show();

            }
        });
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);

    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);

    }

    public void resendCode() {

        getOtp();
    }

    public void codenumber() {

        numOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numOne.getText().toString().length() == 0) {
                    numTwo.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numTwo.getText().toString().length() == 0) {
                    numThree.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numThree.getText().toString().length() == 0) {
                    numFour.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numFour.getText().toString().length() == 0) {
                    numFive.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numFive.getText().toString().length() == 0) {
                    numSix.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numSix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyOtp();
            }
        });

        numSix.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(numSix.getText().toString().length()==0)
                        numFive.requestFocus();
                }
                return false;
            }
        });
        numFive.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(numFive.getText().toString().length()==0)
                        numFour.requestFocus();
                }
                return false;
            }
        });
        numFour.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(numFour.getText().toString().length()==0)
                        numThree.requestFocus();
                }
                return false;
            }
        });
        numThree.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(numThree.getText().toString().length()==0)
                        numTwo.requestFocus();
                }
                return false;
            }
        });
        numTwo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(numTwo.getText().toString().length()==0)
                        numOne.requestFocus();
                }
                return false;
            }
        });
    }


}