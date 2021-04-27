package com.ayoyo.merchant.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ayoyo.merchant.BuildConfig;
import com.ayoyo.merchant.R;
import com.ayoyo.merchant.activity.ChangepassActivity;
import com.ayoyo.merchant.activity.EditmitraActivity;
import com.ayoyo.merchant.activity.EditstoreActivity;
import com.ayoyo.merchant.activity.LoginActivity;
import com.ayoyo.merchant.activity.MemberActivity;
import com.ayoyo.merchant.activity.PrivacyActivity;
import com.ayoyo.merchant.activity.SplashActivity;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.constants.Constants;
import com.ayoyo.merchant.json.mlm.MlmDownlineResponse;
import com.ayoyo.merchant.json.mlm.MlmRequest;
import com.ayoyo.merchant.models.User;
import com.ayoyo.merchant.utils.SettingPreference;
import com.ayoyo.merchant.utils.PicassoTrustAll;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;

import java.util.Objects;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class SettingsFragment extends Fragment {

    private Context context;
    private ImageView bannermerchant;
    private TextView merchantname, namamitra;
    private SettingPreference sp;
    Button btnPremium, btnCopy;
    LinearLayout llrefCode;
    private String saldo;
    TextView refCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getContext();

        bannermerchant = getView.findViewById(R.id.bannermerchant);
        merchantname = getView.findViewById(R.id.nama_merchant);
        namamitra = getView.findViewById(R.id.namamitra);
        llrefCode = getView.findViewById(R.id.ll_refcode);
        btnCopy = getView.findViewById(R.id.btnCopy);
        btnPremium = getView.findViewById(R.id.button8);
        refCode = getView.findViewById(R.id.refCode);

        LinearLayout editmitra = getView.findViewById(R.id.editmitra);
        LinearLayout editmerchant = getView.findViewById(R.id.editmerchant);

        LinearLayout aboutus = getView.findViewById(R.id.llaboutus);
        LinearLayout privacy = getView.findViewById(R.id.llprivacypolicy);
        LinearLayout shareapp = getView.findViewById(R.id.llshareapp);
        LinearLayout rateapp = getView.findViewById(R.id.llrateapp);
        LinearLayout logout = getView.findViewById(R.id.lllogout);
        LinearLayout llpassword = getView.findViewById(R.id.llpassword);
        sp = new SettingPreference(context);

        getData();


        btnPremium.setOnClickListener(v -> {
            Intent i = new Intent(context, MemberActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });


        btnCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", refCode.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(v.getContext(), "Kode referal telah disalin", Toast.LENGTH_LONG).show();

        });

        editmitra.setOnClickListener(v -> {
            Intent i = new Intent(context, EditmitraActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        });

        editmerchant.setOnClickListener(v -> {
            Intent i = new Intent(context, EditstoreActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        });

        privacy.setOnClickListener(v -> {
            Intent i = new Intent(context, PrivacyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutus();
            }
        });

        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = "Let me recommend you this application ";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));

            }
        });

        rateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + requireActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                }
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Objects.requireNonNull(getActivity()).getPackageName())));
                }
            }
        });

        llpassword.setOnClickListener(v -> {
            Intent i = new Intent(context, ChangepassActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDone();
            }
        });


        return getView;
    }

    private void clickDone() {
        new AlertDialog.Builder(requireActivity(), R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Realm realm = BaseApp.getInstance(context).getRealmInstance();
                        realm.beginTransaction();
                        realm.delete(User.class);
                        realm.commitTransaction();
                        removeNotif();
                        BaseApp.getInstance(context).setLoginUser(null);
//                        startActivity(new Intent(getContext(), IntroActivity.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        startActivity(new Intent(getContext(), SplashActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void aboutus() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_aboutus);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageView close = dialog.findViewById(R.id.bt_close);
        final LinearLayout email = dialog.findViewById(R.id.email);
        final LinearLayout phone = dialog.findViewById(R.id.phone);
        final LinearLayout website = dialog.findViewById(R.id.website);
        final WebView about = dialog.findViewById(R.id.aboutus);

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = sp.getSetting()[1];
        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/NeoSans_Pro_Regular.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        about.loadDataWithBaseURL(null, text, mimeType, encoding, null);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int REQUEST_PHONE_CALL = 1;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + (sp.getSetting()[3])));
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(callIntent);
                    }
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {(sp.getSetting()[2])};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "halo");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "email" + "\n");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (sp.getSetting()[4]);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onResume() {
        super.onResume();
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        merchantname.setText(loginUser.getNamamerchant());
        namamitra.setText(loginUser.getNamamitra());
        refCode.setText(loginUser.getId());
        PicassoTrustAll.getInstance(context)
                .load(Constants.IMAGESMERCHANT + loginUser.getFoto_merchant())
                .placeholder(R.drawable.image_placeholder)
                .resize(250, 250)
                .into(bannermerchant);



    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }


    public void getData(){
        //rlprogress.setVisibility(View.VISIBLE);
        User userLogin = BaseApp.getInstance(getContext()).getLoginUser();
        MlmRequest mlmRequest = new MlmRequest();
        mlmRequest.id_pelanggan = userLogin.getId();

        MerchantService userService = ServiceGenerator.createService(MerchantService.class);
        userService.getMlmDownline(mlmRequest).enqueue(new Callback<MlmDownlineResponse>() {
            @Override
            public void onResponse(Call<MlmDownlineResponse> call, Response<MlmDownlineResponse> response) {
                //rlprogress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().message.equals("found")){
                        if(response.body().data.size()>0 && response.body().data.get(0).type.equals("Premium")){

                            btnPremium.setText("Premium Member");
                            llrefCode.setVisibility(View.VISIBLE);
                        }
                        else{

                            llrefCode.setVisibility(View.GONE);


                        }
//                        runOnUiThread(() -> {
//                            Utility.currencyTXT(tvMlmSaldo, response.body().total_saldo, MemberActivity.this);
////                            tvMlmSaldo.setText(response.body().total_saldo);
//                        });
                        saldo = response.body().total_saldo;
//                        Utility.currencyTXT(tvMlmSaldo, response.body().total_saldo, MemberActivity.this);




                    }
                }

            }

            @Override
            public void onFailure(Call<MlmDownlineResponse> call, Throwable t) {

            }
        });
    }
}
