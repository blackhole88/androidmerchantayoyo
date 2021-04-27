package com.ayoyo.merchant.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.models.User;

import java.text.DecimalFormat;

/**
 * Created by Ourdevelops Team on 12/2/2019.
 */

public class Utility {


    public static TextWatcher currencyTW(final EditText editText, final Context context) {
        final SettingPreference sp = new SettingPreference(context);
        return new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    long longval;
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("[$.]", "");
                    }
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    if (originalString.contains(sp.getSetting()[0]+" ")) {
                        originalString = originalString.replaceAll(sp.getSetting()[0]+" ", "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replaceAll(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(" ")) {
                        originalString = originalString.replaceAll(" ", "");
                    }
                    longval = Long.parseLong(originalString);
                    if (longval == 0) {
                        editText.setText("");
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 1) {
                        editText.setText(sp.getSetting()[0]+"0.0" + String.valueOf(longval));
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 2) {
                        editText.setText(sp.getSetting()[0]+"0." + String.valueOf(longval));
                        editText.setSelection(editText.getText().length());
                    } else {

                        SettingPreference sp = new SettingPreference(context);
                        DecimalFormat formatter = new DecimalFormat("#,###,##");
                        String formattedString = formatter.format(longval);
                        editText.setText(sp.getSetting()[0] + formattedString.replace(",","."));
                        editText.setSelection(editText.getText().length());
                    }
                    Log.e("aaaa", String.valueOf(longval));
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }


    public static void currencyTXT(TextView text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0]+"0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0]+"0." + nomninal);
        } else {
            Double getprice = Double.valueOf(nomninal);
            DecimalFormat formatter = new DecimalFormat("#,###,##");
            String formattedString = String.format("%,.2f", getprice);
            text.setText(sp.getSetting()[0] + formattedString.replace(",","."));
        }
    }

    public static TextWatcher currencyTWWithout00(final EditText editText, final Context context) {
        final SettingPreference sp = new SettingPreference(context);
        return new TextWatcher() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("[$.]", "");
                    }
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    if (originalString.contains(sp.getSetting()[4]+" ")) {
                        originalString = originalString.replaceAll(sp.getSetting()[4]+" ", "");
                    }
                    if (originalString.contains(sp.getSetting()[4])) {
                        originalString = originalString.replaceAll(sp.getSetting()[4], "");
                    }
                    if (originalString.contains(sp.getSetting()[4])) {
                        originalString = originalString.replace(sp.getSetting()[4], "");
                    }
                    if (originalString.contains(sp.getSetting()[4])) {
                        originalString = originalString.replace(sp.getSetting()[4], "");
                    }
                    if (originalString.contains(" ")) {
                        originalString = originalString.replaceAll(" ", "");
                    }

                    longval = Long.parseLong(originalString);
                    if(originalString.length()>3) {
                        String priceAwal = originalString.substring(0, originalString.length()-3);
                        String tempAngka = originalString.substring(originalString.length()-3 , originalString.length());
                        originalString = priceAwal+"."+tempAngka;

                    }
                    if(originalString.length()>7){
                        String priceAwal = originalString.substring(0, originalString.length()-7);
                        String tempAngka = originalString.substring(originalString.length()-7 , originalString.length());
                        originalString = priceAwal+"."+tempAngka;
                    }
                    if(originalString.length()>11){
                        String priceAwal = originalString.substring(0, originalString.length()-11);
                        String tempAngka = originalString.substring(originalString.length()-11 , originalString.length());
                        originalString = priceAwal+"."+tempAngka;
                    }
                    if(originalString.length()>15){
                        String priceAwal = originalString.substring(0, originalString.length()-15);
                        String tempAngka = originalString.substring(originalString.length()-15 , originalString.length());
                        originalString = priceAwal+"."+tempAngka;
                    }
                    editText.setText(originalString);
                    editText.setSelection(editText.getText().length());

//                    else {
//
//                        SettingPreference sp = new SettingPreference(context);
//                        DecimalFormat formatter = new DecimalFormat("#,###");
//                        String formattedString = formatter.format(longval);
//                        editText.setText(sp.getSetting()[4] + formattedString.replace(",","."));
//                        editText.setSelection(editText.getText().length());
//                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }


    public static boolean isSaldoEnough(long harga, Context context){
        User userLogin = BaseApp.getInstance(context).getLoginUser();
        long saldowallet = userLogin.getWalletSaldo();
        Log.d("tessaldoo", saldowallet+"");
        if(saldowallet<harga){
            Toast.makeText(context, "Saldo Anda tidak mencukupi, silahkan isi ulang atau gunakan metode pembayaran lain", Toast.LENGTH_LONG).show();
            return false;
        }
        else return true;


    }

}
