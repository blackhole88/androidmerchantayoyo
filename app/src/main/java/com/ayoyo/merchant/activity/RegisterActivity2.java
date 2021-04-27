package com.ayoyo.merchant.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.Constants;
import com.ayoyo.merchant.json.GetFiturResponseJson;
import com.ayoyo.merchant.json.HistoryRequestJson;
import com.ayoyo.merchant.json.KecamatanRequest;
import com.ayoyo.merchant.json.KecamatanResponse;
import com.ayoyo.merchant.json.KotaRequest;
import com.ayoyo.merchant.json.KotaResponse;
import com.ayoyo.merchant.json.ProvinsiResponse;
import com.ayoyo.merchant.json.RegisterRequestJson;
import com.ayoyo.merchant.json.RegisterResponseJson;
import com.ayoyo.merchant.models.FiturModel;
import com.ayoyo.merchant.models.KategoriModel;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity2 extends AppCompatActivity {

    ImageView bannermerchant, foto, gantifoto, backbtn, backButtonverify, fotosim, fotoktp, fotoStnk, fotoKk, fotoSkck, fotoDocument;
    EditText etReferal, merchantname, idtype, phone, nama, namamitra, email, numOne, numTwo, numThree, numFour, numFive, numSix, alamat, brand, type, vehiclenumber, color, idcardtext, driverlicensetext, etRef;
    TextView tanggal, countryCode, sendTo, textnotif, textnotif2, privacypolicy, merchantloc, opentime, closetime;
    Button submit, confirmButton, addimage, addimagektp;
    RelativeLayout rlnotif, rlprogress, rlnotif2;
    Spinner gender, job, spProv, spKota, spKecamatan, merchantype, merchantcat, spinnerProvinsi, spinnerKota, spinnerKecamatan;
    private SimpleDateFormat dateFormatter, dateFormatterview;
    String phoneNumber;
    FirebaseUser firebaseUser;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    FirebaseAuth mAuth;
    public static byte[] imageByteArray, imageByteArrayktp, imageByteArraysim, imageByteArrayStnk, imageByteArraySkck, imagebyteArrayKk, imageByteArrayDocument;
    Bitmap decoded, decodedktp, decodedsim, decodedKk, decodedSkck, decodedStnk, decodedDocument;
    String dateview, disableback;
    String[] spinnergender;
    String[] spinnerjob;
    ViewFlipper viewFlipper;
    String country_iso_code = "en";
    String verify, token;
    //List<JobModel> joblist;
    //ArrayList<FiturModel> fiturlist;
    //ArrayList<String> jobdata;
    public static final int SIGNUP_ID = 110;
    public static final String USER_KEY = "UserKey";
    RegisterRequestJson request;
    private final int DESTINATION_ID = 1;

    String latitude, longitude;

    ArrayList<FiturModel> fiturlist;
    ArrayList<String> jobsdata;
    private List<FiturModel> fiturdata;

    ArrayList<KategoriModel> katlist;
    private List<KategoriModel> katdata;
    ArrayList<String> jobskatdata;

    private int compressQuality = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fiturlist = new ArrayList<>();
        jobskatdata = new ArrayList<>();
        jobsdata = new ArrayList<>();
        katlist = new ArrayList<>();
        fbAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        backbtn = findViewById(R.id.back_btn);
        phone = findViewById(R.id.phonenumber);
        namamitra = findViewById(R.id.namamitra);
        email = findViewById(R.id.email);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        countryCode = findViewById(R.id.countrycode);
        viewFlipper = findViewById(R.id.viewflipper);
        backButtonverify = findViewById(R.id.back_btn_verify);
        rlprogress = findViewById(R.id.rlprogress);
        rlnotif2 = findViewById(R.id.rlnotif2);
        textnotif2 = findViewById(R.id.textnotif2);
        confirmButton = findViewById(R.id.buttonconfirm);
        token = FirebaseInstanceId.getInstance().getToken();
        numOne = findViewById(R.id.numone);
        numTwo = findViewById(R.id.numtwo);
        numThree = findViewById(R.id.numthree);
        numFour = findViewById(R.id.numfour);
        numFive = findViewById(R.id.numfive);
        numSix = findViewById(R.id.numsix);
        sendTo = findViewById(R.id.sendtotxt);
        privacypolicy = findViewById(R.id.privacypolice);
        alamat = findViewById(R.id.address);
        idcardtext = findViewById(R.id.noktp);
        fotoktp = findViewById(R.id.fotoktp);
        idtype = findViewById(R.id.idtype);
        bannermerchant = findViewById(R.id.bannermerchant);
        merchantype = findViewById(R.id.merchanttype);
        merchantname = findViewById(R.id.merchantname);
        merchantcat = findViewById(R.id.merchantcat);
        merchantloc = findViewById(R.id.merchantloc);
        opentime = findViewById(R.id.opentime);
        closetime = findViewById(R.id.closetime);
        addimage = findViewById(R.id.addimage);
        addimagektp = findViewById(R.id.addfotoktp);
        spinnerProvinsi = findViewById(R.id.spProvinsi);
        spinnerKota = findViewById(R.id.spKota);
        spinnerKecamatan = findViewById(R.id.spKecamatan);
        etReferal = findViewById(R.id.etReferal);


        imageByteArray = null;
        imageByteArrayktp = null;

        String priv = getResources().getString(R.string.privacy_register);
        privacypolicy.setText(Html.fromHtml(priv));

        privacypolicy.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity2.this, PrivacyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        });

        merchantloc.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity2.this, PicklocationActivity.class);
            intent.putExtra(PicklocationActivity.FORM_VIEW_INDICATOR, DESTINATION_ID);
            startActivityForResult(intent, PicklocationActivity.LOCATION_PICKER_ID);
        });

        opentime.setOnClickListener(v -> opentanggal());

        closetime.setOnClickListener(v -> closetanggal());

        addimage.setOnClickListener(v -> selectImage());

        addimagektp.setOnClickListener(v -> selectImagektp());

        backbtn.setOnClickListener(view -> onBackPressed());

        countryCode.setOnClickListener(v -> {
            final CountryPicker picker = CountryPicker.newInstance("Select Country");
            picker.setListener((name, code, dialCode, flagDrawableResID) -> {
                countryCode.setText(dialCode);
                picker.dismiss();
                country_iso_code = code;
            });
            picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
            picker.show(getSupportFragmentManager(), "Select Country");
        });
        spinner();

        submit.setOnClickListener(v -> {
            final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            final String emailvalidate = email.getText().toString();

            if (TextUtils.isEmpty(phone.getText().toString())) {
                notif(getString(R.string.phoneempty));
            } else if (TextUtils.isEmpty(namamitra.getText().toString())) {
                notif("Name cant be empty!");
            } else if (TextUtils.isEmpty(email.getText().toString())) {
                notif(getString(R.string.emailempty));
            } else if (TextUtils.isEmpty(alamat.getText().toString())) {
                notif("please enter andress!");
            } else if (TextUtils.isEmpty(idcardtext.getText().toString())) {
                notif("please enter ID Card number!");
            } else if (TextUtils.isEmpty(idtype.getText().toString())) {
                notif("please enter ID Card type!");
            } else if (imageByteArrayktp == null) {
                notif("please upload Image ID Card!");
            } else if (spinnerProvinsi.getSelectedItemId() == 0) {
                notif("Provinsi belum dipilih");
            } else if (((KotaResponse.Kota) spinnerKota.getSelectedItem()).id.equals("-1")) {
                notif("Kota belum dipilih");
            } else if (((KecamatanResponse.Kecamatan) spinnerKecamatan.getSelectedItem()).id.equals("-1")) {
                notif("Kecamatan belum dipilih");
            } else if (imageByteArray == null) {
                notif("please add photo!");
            } else if (!emailvalidate.matches(emailPattern)) {
                notif("wrong email format!");
            } else if (merchantype.getSelectedItemPosition() == 0) {
                notif("Please select service");
            } else if (TextUtils.isEmpty(merchantname.getText().toString())) {
                notif("merchant name cant be empty!");
            } else if (merchantcat.getSelectedItemPosition() == 0) {
                notif("category cant be empty");
            } else if (TextUtils.isEmpty(merchantloc.getText().toString())) {
                notif("location cant be empty!");
            } else if (TextUtils.isEmpty(opentime.getText().toString())) {
                notif("open time cant be empty!");
            } else if (TextUtils.isEmpty(closetime.getText().toString())) {
                notif("close time cant be empty!");
            } else {
                upload("true");
            }
        });


        backButtonverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        disableback = "false";
        verify = "false";
        loadProv();
    }



    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }



    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }


    /**
     * uploadfoto-------------start.
     */
    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.permission_Read_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    private void selectImage() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    private void selectImagektp() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }



    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }
    BitmapFactory.Options options = new BitmapFactory.Options();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            options.inSampleSize = 2;
            if (requestCode == PicklocationActivity.LOCATION_PICKER_ID) {
                String addressset = data.getStringExtra(PicklocationActivity.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(PicklocationActivity.LOCATION_LATLNG);
                merchantloc.setText(addressset);
                latitude = String.valueOf(Objects.requireNonNull(latLng).latitude);
                longitude = String.valueOf(latLng.longitude);
            }else
            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream, null, options);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
                bannermerchant.setImageBitmap(rotatedBitmap);
                imageByteArray = baos.toByteArray();
//                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            } else if (requestCode == 3) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream, null, options);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
                fotoktp.setImageBitmap(rotatedBitmap);
                imageByteArrayktp = baos.toByteArray();
//                decodedktp = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            } else if (requestCode == 4) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream, null, options);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
                fotosim.setImageBitmap(rotatedBitmap);
                imageByteArraysim = baos.toByteArray();
//                decodedsim = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
            else if (requestCode == 5) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream, null, options);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
                fotoStnk.setImageBitmap(rotatedBitmap);
                imageByteArrayStnk = baos.toByteArray();
//                decodedStnk = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
            }
            else if (requestCode == 6) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream, null, options);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
                fotoSkck.setImageBitmap(rotatedBitmap);
                imageByteArraySkck = baos.toByteArray();
//                decodedSkck = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
            }
            else if (requestCode == 7) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream, null, options);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);

                fotoKk.setImageBitmap(rotatedBitmap);
                imagebyteArrayKk = baos.toByteArray();
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 2;
//                decodedKk = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

//                Log.d("baossss", baos.size()+" a " + decodedKk.getByteCount());
            }
            else if (requestCode == 8) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream, null, options);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
                fotoDocument.setImageBitmap(rotatedBitmap);
                imageByteArrayDocument = baos.toByteArray();
//                decodedDocument = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

//                Log.d("baossss2", baos.size()+" a " + imageByteArrayDocument.length);

            }
        }
    }

    public void getStringImage(Bitmap bmp) {
        if(bmp!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
            imageByteArray = baos.toByteArray();
        }
//        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    public void getStringImagektp(Bitmap bmp) {
        if(bmp!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
            imageByteArrayktp = baos.toByteArray();
        }
//        return Base64.encodeToString(imageByteArrayktp, Base64.DEFAULT);
    }


    void loadProv() {
        MerchantService service = ServiceGenerator.createService(MerchantService.class,
                "admin", "admin");
        service.getProvinsi().enqueue(new Callback<ProvinsiResponse>() {
            @Override
            public void onResponse(Call<ProvinsiResponse> call, Response<ProvinsiResponse> response) {
                ProvinsiResponse.Provinsi provinsi = new ProvinsiResponse.Provinsi();
                provinsi.id = "-1";
                provinsi.name = "Provinsi";
                List<ProvinsiResponse.Provinsi> spResponse = response.body().data;
                spResponse.add(0, provinsi);
                ArrayAdapter<ProvinsiResponse.Provinsi> adapter = new ArrayAdapter<>(RegisterActivity2.this, R.layout.spinner, spResponse);
                spinnerProvinsi.setAdapter(adapter);
                spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position > 0)
                            loadKota((ProvinsiResponse.Provinsi) spinnerProvinsi.getSelectedItem());
                        else loadKota(null);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ProvinsiResponse> call, Throwable t) {

            }
        });
    }

    void loadKota(ProvinsiResponse.Provinsi provinsi) {
        if (provinsi == null) {
            KotaResponse.Kota kota = new KotaResponse.Kota();
            kota.id = "-1";
            kota.nama = "Kota";
            List<KotaResponse.Kota> spResponse = new ArrayList<>();
            spResponse.add(0, kota);
            ArrayAdapter<KotaResponse.Kota> adapter = new ArrayAdapter(RegisterActivity2.this, R.layout.spinner, spResponse);
            spinnerKota.setAdapter(adapter);
            loadKecamatan(null);
        } else {
            MerchantService service = ServiceGenerator.createService(MerchantService.class,
                    "admin", "admin");
            KotaRequest kotaRequest = new KotaRequest();
            kotaRequest.province_id = provinsi.id;
            service.getKota(kotaRequest).enqueue(new Callback<KotaResponse>() {
                @Override
                public void onResponse(Call<KotaResponse> call, Response<KotaResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ArrayAdapter<KotaResponse.Kota> adapter = new ArrayAdapter<>(RegisterActivity2.this, R.layout.spinner, response.body().data);
                        spinnerKota.setAdapter(adapter);
                        spinnerKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                loadKecamatan((KotaResponse.Kota) spinnerKota.getSelectedItem());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<KotaResponse> call, Throwable t) {

                }
            });
        }
    }

    void loadKecamatan(KotaResponse.Kota kota) {
        if (kota == null) {
            Log.d("TEST","kecamatan kosong");
            KecamatanResponse.Kecamatan kecamatan = new KecamatanResponse.Kecamatan();
            kecamatan.id = "-1";
            kecamatan.nama = "Kecamatan";
            List<KecamatanResponse.Kecamatan> spResponse = new ArrayList<>();
            spResponse.add(0, kecamatan);
            ArrayAdapter<KotaResponse.Kota> adapter = new ArrayAdapter(RegisterActivity2.this, R.layout.spinner, spResponse);
            spinnerKecamatan.setAdapter(adapter);
        } else {
            Log.d("TEST","kecamatan ada");
            MerchantService service = ServiceGenerator.createService(MerchantService.class,
                    "admin", "admin");
            KecamatanRequest kecamatanRequest = new KecamatanRequest();
            kecamatanRequest.kota_id = kota.id;
            service.getKecamatan(kecamatanRequest).enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {

                    ArrayAdapter<KecamatanResponse.Kecamatan> adapter = new ArrayAdapter<>(RegisterActivity2.this, R.layout.spinner, response.body().data);
                    spinnerKecamatan.setAdapter(adapter);
                    spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {

                }
            });
        }
    }
    /**
     * uploadfoto-------------end.
     */

//sendcode-----------------------
    public void notif2(String text) {
        rlnotif2.setVisibility(View.VISIBLE);
        textnotif2.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif2.setVisibility(View.GONE);
            }
        }, 3000);
    }


    private void upload(final String check) {
        progressshow();
        RegisterRequestJson request = new RegisterRequestJson();
        request.setNama_mitra(namamitra.getText().toString());
        request.setJenis_identitas(idtype.getText().toString());
        request.setNo_ktp(idcardtext.getText().toString());
        request.setNo_telepon(countryCode.getText().toString().replace("+", "") + phone.getText().toString());
        request.setEmail(email.getText().toString());
        request.setPhone(phone.getText().toString());
        request.setAlamat_mitra(alamat.getText().toString());
        request.setCountrycode(countryCode.getText().toString());
        request.setId_fitur(String.valueOf(fiturlist.get(merchantype.getSelectedItemPosition()).getIdFitur()));
        request.setNama_merchant(merchantname.getText().toString());
        request.setAlamat_merchant(merchantloc.getText().toString());
        request.setLatitude_merchant(latitude);
        request.setLongitude_merchant(longitude);
        request.setJam_buka(opentime.getText().toString());
        request.setJam_tutup(closetime.getText().toString());
        request.setCategory_merchant(String.valueOf(katlist.get(merchantcat.getSelectedItemPosition()).getIdkategori()));
        request.setFoto(Base64.encodeToString(imageByteArray, Base64.DEFAULT));
        request.setFoto_ktp(Base64.encodeToString(imageByteArrayktp, Base64.DEFAULT));
        request.setKotaId(((KotaResponse.Kota) spinnerKota.getSelectedItem()).id);
        request.setKecamatanId(((KecamatanResponse.Kecamatan) spinnerKecamatan.getSelectedItem()).id);
        request.setChecked("false");
        request.setRefCode(etReferal.getText().toString());
        request.setToken(token);

        MerchantService service = ServiceGenerator.createService(MerchantService.class, request.getEmail(), request.getNo_telepon());
        service.register(request).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(Call<RegisterResponseJson> call, Response<RegisterResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity2.this, IntroActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(RegisterActivity2.this, response.body().getData(), Toast.LENGTH_SHORT).show();

                } else {
                    notif("error");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseJson> call, Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error!");
            }
        });
    }

    private void opentanggal() {

        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                try {

                    String timeString = hourOfDay + ":" + minute;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date time = sdf.parse(timeString);

                    sdf = new SimpleDateFormat("HH:mm");
                    String formatedTime = sdf.format(Objects.requireNonNull(time));
                    opentime.setText(formatedTime);

                } catch (Exception ignored) {
                }
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorgradient));
        datePicker.show(getFragmentManager(), "Timepickerdialog");

    }


    private void closetanggal() {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                try {

                    String timeString = hourOfDay + ":" + minute;
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                    Date time = sdf.parse(timeString);

                    sdf = new SimpleDateFormat("HH:mm", Locale.US);
                    String formatedTime = sdf.format(Objects.requireNonNull(time));
                    closetime.setText(formatedTime);

                } catch (Exception ignored) {
                }
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorgradient));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }

    private void spinner() {
        FiturModel jobs = new FiturModel();
        jobs.setFitur("0");
        jobs.setIdFitur(-1);
        fiturlist.add(jobs);
        jobsdata.add("Pilih Layanan");
        MerchantService userService = ServiceGenerator.createService(MerchantService.class,
                "admin", "admin");
        userService.getFitur().enqueue(new Callback<GetFiturResponseJson>() {
            @Override
            public void onResponse(Call<GetFiturResponseJson> call, Response<GetFiturResponseJson> response) {
                if (response.isSuccessful()) {
                    fiturdata = Objects.requireNonNull(response.body()).getData();
                    for (int i = 0; i < fiturdata.size(); i++) {
                        FiturModel jobss = new FiturModel();
                        jobss.setFitur(fiturdata.get(i).getFitur());
                        jobss.setIdFitur(fiturdata.get(i).getIdFitur());
                        fiturlist.add(jobss);
                        jobsdata.add(fiturdata.get(i).getFitur());
                    }

                    ArrayAdapter<String> citySpinner = new ArrayAdapter<>(RegisterActivity2.this, R.layout.spinner, jobsdata);
                    citySpinner.setDropDownViewResource(R.layout.spinner);
                    merchantype.setAdapter(citySpinner);
                    merchantype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            // TODO Auto-generated method stub

                            if (position == 0) {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                spinner2(String.valueOf(fiturlist.get(merchantype.getSelectedItemPosition()).getIdFitur()));
                            } else {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                spinner2(String.valueOf(fiturlist.get(merchantype.getSelectedItemPosition()).getIdFitur()));
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<GetFiturResponseJson> call, Throwable t) {

            }
        });
    }

    private void spinner2(String id) {
        if (jobskatdata != null) {
            jobskatdata.clear();
        }
        if (katlist != null) {
            katlist.clear();
        }

        if (katdata != null) {
            katdata.clear();
        }
        KategoriModel job = new KategoriModel();
        job.setNama("0");
        job.setIdkategori(0);
        katlist.add(job);
        jobskatdata.add("Pilih Kategori");
        MerchantService userService = ServiceGenerator.createService(MerchantService.class,
                "admin", "admin");
        HistoryRequestJson request = new HistoryRequestJson();
        request.setIdmerchant(id);
        userService.getKategori(request).enqueue(new Callback<GetFiturResponseJson>() {
            @Override
            public void onResponse(Call<GetFiturResponseJson> call, Response<GetFiturResponseJson> response) {
                if (response.isSuccessful()) {
                    katdata = Objects.requireNonNull(response.body()).getKategori();
                    for (int j = 0; j < katdata.size(); j++) {
                        KategoriModel jobs = new KategoriModel();
                        jobs.setNama(katdata.get(j).getNama());
                        jobs.setIdkategori(katdata.get(j).getIdkategori());
                        katlist.add(jobs);
                        jobskatdata.add(katdata.get(j).getNama());
                    }
                    ArrayAdapter<String> citySpinner = new ArrayAdapter<>(RegisterActivity2.this, R.layout.spinner, jobskatdata);
                    citySpinner.setDropDownViewResource(R.layout.spinner);
                    merchantcat.setAdapter(citySpinner);
                    merchantcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            // TODO Auto-generated method stub

                            if (position == 0) {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                com.ayoyo.merchant.utils.Log.e("", String.valueOf(katlist.get(merchantcat.getSelectedItemPosition()).getIdkategori()));
                            } else {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                com.ayoyo.merchant.utils.Log.e("", String.valueOf(katlist.get(merchantcat.getSelectedItemPosition()).getIdkategori()));
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetFiturResponseJson> call, Throwable t) {

            }
        });
    }


}
