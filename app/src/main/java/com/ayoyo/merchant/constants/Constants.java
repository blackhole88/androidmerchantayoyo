package com.ayoyo.merchant.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ourdevelops Team on 10/23/2020.
 */

public class Constants {

//    private static final String BASE_URL = "https://pm.demopm.com/ayoyo1/";
    public static final String  BASE_URL = "https://ayoyo.id/aplikasi/";
    public static final String FCM_KEY = "AAAAGAGAZxU:APA91bEbpzOe5F9eWAiYcG1TxHhUJoASmard95xPR2Ixbd4ozy4RKVSiFky1HevodV0P0qdBEujJrk-bc8GvzQeNPwAQ4NDQwiXAfKiVVIn94GoA9TvXLqIBjz5_UM9gD2XNcf1p2DM4";
    public static final String CONNECTION = BASE_URL + "api/";
    public static final String IMAGESDRIVER = BASE_URL + "images/fotodriver/";
    public static final String IMAGESMERCHANT = BASE_URL + "images/merchant/";
    public static final String IMAGESBANK = BASE_URL + "images/bank/";
    public static final String IMAGESITEM = BASE_URL + "images/itemmerchant/";
    public static final String IMAGESPELANGGAN = BASE_URL + "images/pelanggan/";

    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String LOCATION;

    public static String USERID = "uid";

    public static String PREF_NAME = "pref_name";

    public static int permission_camera_code = 786;
    public static int permission_write_data = 788;
    public static int permission_Read_data = 789;
    public static int permission_Recording_audio = 790;

    public static SimpleDateFormat df =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static String versionname = "1.0";

}
