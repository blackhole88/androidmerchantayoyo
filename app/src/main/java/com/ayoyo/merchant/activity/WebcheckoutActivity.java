package com.ayoyo.merchant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ayoyo.merchant.R;

public class WebcheckoutActivity extends AppCompatActivity {
    WebView webview;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcheckout);

        String url = getIntent().getStringExtra("url");
        int source = getIntent().getIntExtra("source", 0);

        progress = findViewById(R.id.progress);
        webview = findViewById(R.id.webview);
//        webview.setWebChromeClient(new MyChromeClient());
        webview.setWebViewClient(new MyWebClient());
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);
//<body><form action='https:\/\/checkout.linkaja.id:8443\/v1\/payment' method='post'><input type='hidden' name='pgpToken' value='-----BEGIN Message-----\n\nwcFMAzkHiJj5T0ZnARAAyBIn4SNnLaJjLHipehUdhz+ghXFPXKmx0t8o5jR8gZMf\ntA6YSpk8nMI4Yf\/Qrw3jmA8VgUB1BvNkYjyDIBLOo1WlRUBbPze6GbWUwlPYthol\nDMt5EVe824Ufv0lUhtz5ak+jDqodOW9QtYxJGexeztK943gwUyn\/70\/UjgwcCj7x\ngayQaI2WpWAJwgHGqw3zUuxBCnsOOog9HrlfrKmEUkrFVd6VIKAOpOwo\/toB8OWB\n5dSEV0U4cZxlxD2M3TwZ7pW1xCojucpM5\/PT9OgVOWn5x+fE8qv8JnAxCieRHQ\/B\nDF7htTvUhZ80yGS27RY9NxRkZuDBCChAM5c5QzHyZBZCwXirEYMDA1G5NUA\/EaMk\nyCbkMt1RbN3EBE6bTvmpgkYmttdCxHIWmuh9FW81Zjw9brPq8JirGtIi9xKoHXTN\nv7mxHsb7J\/8eFlqfDzNbnxjw9\/2xOfNrvyKaFD9P2nT4KPN79hNy4MEf+OOYmJ35\nbswDjcB\/R+S\/jucMaI2SzjOlsym90WAVV8fncogsf8DNqONekYMEZcUh1hDXPK2p\nk5ksZYbPQnIAIKjltyh6V96NNhp4ETF2nPyMqKz5a8UCn4hBPGe3sk858LtoGYo2\nigWLm17OsCyInNwvbLScBs\/RMjPCVrLSK00nfEvfEpOGXI4BZGizrjfN2qY\/ZK3S\n6AGb9YCLDa0\/kBgWKZpvXI845wZCD0wIvEcQUqRslAnGcmgfG83+hPeqmZxDy2EX\n0E0R2xfnqdMhqMFkslMbAMdo5Y+5JOy9NafWaK2JobE4EWnP7w6LhQGFzHh0Xa3R\nTZPjX963zdHix4Ek\/O8kANMAtMWhFABUcfV1fTjRt7ZJWqb\/bGto2qlwzhskF8Cx\nyzwBBUQHTWtGVupg\/vnFj2e\/vJg4VFhT7dkGWhTXWRKTd0Zp+cU2ivfMOqrwu7+i\nxlZadkQm3wVI\/CrMaABHimbi\/VhQUY0ypMJ0P3rd5FhTy45yUjl5fxmQn30KWjm4\nHle+k\/QhVMOIzWITgaMb1Zfn6vFPXQUZD6R6D1XJSVmZd75LzaLXxVTwrLglkQIM\nfIdvcbrSd8NWDUUovx0MU9qraCzLsi4KUDYGVn5udU9BF4Snh3xiAP6MH3Yo4ABx\nlaFklMV89+0LImByxOXwxUZnKG7392q9ENqetqi\/pWiOsKlNMaUeHJlOTSmp6c8b\nSQ\/lsclTO60Ou345LDTFXjyRxOJ2FhYBXzxEtRIsJtu83+LkBNabNoO4JD2thr8P\nFpADzuIdx0H74MYA\n=GM7J\n-----END Message-----'><input type='hidden' name='refNum' value='f1f48c8056c0192d'><br\/><button type='submit' class='btn btn-success btn-block' name='submit2'>Continue to Linkaja<\/button><\/form><\/body>
//        url = "<body><form action='https://checkout.linkaja.id:8443/v1/payment' method='post'><input type='hidden' name='pgpToken' value='-----BEGIN Message-----\n\nwcFMAzkHiJj5T0ZnARAAF+FZfT4TDN/68dzsjTs5ClVFnpMiyysatT3VROPf7BHS\nUqnFTb5fuOdl5lsehazWEM3HXWca4RwdmAQKp1Cl1aefK3Ov3YebM7Bu1zJ6j3gM\n6fJS9bgIHkGRCeRKV8MYJJd/32VN1waE4dp8dF/CKVVQCUpyfmdefJJcJTZGZG9o\nxqy6k4Yu6kwp4hkNAu5sKT0Cw/ZKanCU2QE4p2cXQtfPGKxPJ7y0uxsoDUuSw5lA\nirB5FQJ2mBRFCWxmoE5+/dROPXrmL2raPs9NLR3cdSfjLfHVgK7gbS6y+7q1mlCt\nqgR9ZBrPn8wefdP1aDrKFgSrMigjvcXLUUDKHNr26KEGzwTFCeYBjLTzJc68Ilv6\nBkXnYSUUhgFwAh8ut133s5rKz+7QkcZ66h8vF7iKd6T3JcSZdZFEtyqVqwVxLe2i\nxXmUGlgNT0Z7oj434O8Wuyvz4Om9iN6QP/pdLTACry6AVUN8zhZ79KK5g4J4YRqJ\noawxA73uXECEHT9sW8iKelYtXHGCZerSonhfqdvPdM6wDhhtS2pyAJ50s7zknr59\nH0dxwx2ugMTy0N+gKl1fPEqRX3YHjaiFrOuxzvfsyMEc+n9R/5gm/D5g7vnnWW7K\nXMzLckW4N2t0KTFpGlJMEeHyYUtUE+VTM/Kza+dPzW7w4AjsnJeRAARcNFSyD4/S\n6AFkRE28oOWQOjKH4XDLOTYHHSI3MWkVQbzWI55fPpalnEjkF1ekXvwMFHxz+N2n\nP8sPjCsljG8+g8HnEUyHWp7EzMv9nFR+1u0kbNnABPiyQgp+344OJOHrbR3RHEiF\n0LQ29qPIAJioNt2nGo8/bvMPL69nxcf3dyYoVBDONozarR9+ApX4+S5ucdc/UUli\nojoeUEBw2e1k05IxlpwlGUIfLEjiEmFkM9cxGNB+8Q4yvvOKgZlryuqODAMdleJ5\nU2WKOYHoNtinw4+XGB4v0ASlfTXY39X/YiCo1HOetyVusGKObn5lGp8kxPbBVha+\n0Zd4yGOFNAvOf9PqnXXgLWTnszXrtcj5u6At0SWAdfdTGAQoO00ih6y63OzYwha1\nodreaftZ8hp4RegGN8DtwU6xXY2z+ht3HWGkFDMCFwJWgF6Ur97+jI2q62vxYwm/\nltDy4gtIQrOgyFQioLWCRDpKuOHw/HwZ23O8fPSyrZlzLtDH2SSQCagLCPhgTjK2\nQd/lHHN0uRDPlA4FTrB55gZcOM8r2NvI9int5y2UgmgI6+zkW2dUjtp72WM1yUCv\nHNlSU+Ic6rqF4YYrAA==\n=XoSQ\n-----END Message-----'><input type='hidden' name='refNum' value='c2c2e4660b59cd18'><br/><button type='submit' class='btn btn-success btn-block' name='submit2'>Continue to Linkaja</button></form></body>";
//        getUrl("");
        url = url.replace("\n", "\\n");
        Log.d("urlreplace ", url);
//        url = url.replace("hidden", "text");
        webview.loadData(url, "text/html; charset=utf-8", "UTF-8");



    }
    boolean isfirst = true;


//    public void getUrl(String htmlString){
//        PaymentService service = ServiceGenerator.createService(PaymentService.class);
//        String url = "https://checkout.linkaja.id:8443/v1/payment";
//        DirectCheckoutRequest request = new DirectCheckoutRequest();
//        request.pgpToken = "-----BEGIN Message-----\\n\\nwcFMAzkHiJj5T0ZnARAAyBIn4SNnLaJjLHipehUdhz+ghXFPXKmx0t8o5jR8gZMf\\ntA6YSpk8nMI4Yf\\/Qrw3jmA8VgUB1BvNkYjyDIBLOo1WlRUBbPze6GbWUwlPYthol\\nDMt5EVe824Ufv0lUhtz5ak+jDqodOW9QtYxJGexeztK943gwUyn\\/70\\/UjgwcCj7x\\ngayQaI2WpWAJwgHGqw3zUuxBCnsOOog9HrlfrKmEUkrFVd6VIKAOpOwo\\/toB8OWB\\n5dSEV0U4cZxlxD2M3TwZ7pW1xCojucpM5\\/PT9OgVOWn5x+fE8qv8JnAxCieRHQ\\/B\\nDF7htTvUhZ80yGS27RY9NxRkZuDBCChAM5c5QzHyZBZCwXirEYMDA1G5NUA\\/EaMk\\nyCbkMt1RbN3EBE6bTvmpgkYmttdCxHIWmuh9FW81Zjw9brPq8JirGtIi9xKoHXTN\\nv7mxHsb7J\\/8eFlqfDzNbnxjw9\\/2xOfNrvyKaFD9P2nT4KPN79hNy4MEf+OOYmJ35\\nbswDjcB\\/R+S\\/jucMaI2SzjOlsym90WAVV8fncogsf8DNqONekYMEZcUh1hDXPK2p\\nk5ksZYbPQnIAIKjltyh6V96NNhp4ETF2nPyMqKz5a8UCn4hBPGe3sk858LtoGYo2\\nigWLm17OsCyInNwvbLScBs\\/RMjPCVrLSK00nfEvfEpOGXI4BZGizrjfN2qY\\/ZK3S\\n6AGb9YCLDa0\\/kBgWKZpvXI845wZCD0wIvEcQUqRslAnGcmgfG83+hPeqmZxDy2EX\\n0E0R2xfnqdMhqMFkslMbAMdo5Y+5JOy9NafWaK2JobE4EWnP7w6LhQGFzHh0Xa3R\\nTZPjX963zdHix4Ek\\/O8kANMAtMWhFABUcfV1fTjRt7ZJWqb\\/bGto2qlwzhskF8Cx\\nyzwBBUQHTWtGVupg\\/vnFj2e\\/vJg4VFhT7dkGWhTXWRKTd0Zp+cU2ivfMOqrwu7+i\\nxlZadkQm3wVI\\/CrMaABHimbi\\/VhQUY0ypMJ0P3rd5FhTy45yUjl5fxmQn30KWjm4\\nHle+k\\/QhVMOIzWITgaMb1Zfn6vFPXQUZD6R6D1XJSVmZd75LzaLXxVTwrLglkQIM\\nfIdvcbrSd8NWDUUovx0MU9qraCzLsi4KUDYGVn5udU9BF4Snh3xiAP6MH3Yo4ABx\\nlaFklMV89+0LImByxOXwxUZnKG7392q9ENqetqi\\/pWiOsKlNMaUeHJlOTSmp6c8b\\nSQ\\/lsclTO60Ou345LDTFXjyRxOJ2FhYBXzxEtRIsJtu83+LkBNabNoO4JD2thr8P\\nFpADzuIdx0H74MYA\\n=GM7J\\n-----END Message-----";
//        request.refNum = "f1f48c8056c0192d";
//        service.directCheckout(url, request).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                webview.loadData(url, "text/html; charset=utf-8", "UTF-8");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }


    private class MyWebClient extends WebViewClient {



        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("link87", url+" "+view.getProgress());
//            progress.setVisibility(View.VISIBLE);
//            webview.loadUrl("javascript:document.getElementsByName(\"submit2\")[0].click();");

        }

        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            resend.sendToTarget();
            super.onFormResubmission(view, dontResend, resend);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("link101", url+" "+view.getProgress());
            progress.setVisibility(View.GONE);
            webview.loadUrl("javascript:document.getElementsByName(\"submit2\")[0].click();");
//            if(url.contains("checkout") && isfirst) {
//                Log.d("link70", url+" "+view.getProgress());
//                isfirst = false;
//                view.reload();
//            }


            if(url.contains("success_enable")){
                Toast.makeText(WebcheckoutActivity.this, "Anda sukses mengaktifkan metode pembayaran LinkAja, Silahkan lakukan pembayaran", Toast.LENGTH_LONG).show();
                finish();
            } else if (url.contains("success_ppobpayment")) {
                Toast.makeText(WebcheckoutActivity.this, "Pembelian pulsa/paket data berhasil.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(WebcheckoutActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
            } else if(url.contains("success_transactionpayment")){
                Toast.makeText(WebcheckoutActivity.this, "Pembayaran berhasil.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(WebcheckoutActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
            }
            else if(url.contains("success_updateuserpayment")){
                Toast.makeText(WebcheckoutActivity.this, "Isi Saldo berhasil.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(WebcheckoutActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
            }

//            if (url!!.contains("trxId")){
//
//                if(url!!.contains("success")){
//                    val sanitizer = UrlQuerySanitizer(url)
//                    val trxId = sanitizer.getValue("trxId")
//                    val receiptNumber = sanitizer.getValue("receiptNumber")
//                    val refNum = sanitizer.getValue("refNum")
//                    val src = sanitizer.getValue("src")
//
//
//
//
//                    if(source==1) {// qrstatic wco
//                        val requestPayment = PaymentQrStaticRequest()
//                        requestPayment.vehicleType = preferencesHelper.vehicleTypeTrans.toString()
//                        requestPayment.amount = Integer.parseInt(preferencesHelper.amountTrans)
//                        requestPayment.paymentMethod = "wallet"
//                        requestPayment.plateNumber = preferencesHelper.plateNumberTrans
//                        requestPayment.ref_num_agg = receiptNumber
//
//                        val bundle = Bundle()
//                        bundle.putString("receiptNumber", receiptNumber)
//                        bundle.putString("paymentmethod", "wco_linkaja")
//                        goToActivity(ReceiptParkingActivity::class.java, bundle, true)
//                    }
//                    else if(source==2) {// booking wco
//
//                        val amountIntent = Intent()
//                        amountIntent.putExtra("amount", preferencesHelper.amountTrans)
//                        setResult(Activity.RESULT_OK, amountIntent)
//                        finish()
//                        //goToActivity(ActiveBookingActivity::class.java, bundle, true)
//                    }
//                    //presenter.paymentQrStatic(preferencesHelper.qrStatic, requestPayment)
//
//                }
//                else if(url!!.contains("failed")){
//
//                }
//
//
//            }

//            progress.hide()
        }




    }

}
