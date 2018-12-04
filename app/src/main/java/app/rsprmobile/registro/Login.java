package app.rsprmobile.registro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.rsprmobile.registro.Captcha.TextCaptcha;
import app.rsprmobile.registro.app.AppController;

public class Login extends AppCompatActivity {
    EditText textRM;
    EditText textTgLahir;
    Button buttonLogin, btnLihat;
    private static final String urlLogin = "http://192.168.11.183:8080/PasienV2/PasienV2/cekLoginRegistro/";

    TextCaptcha textCaptcha = new TextCaptcha(600, 150, 4, TextCaptcha.TextOptions.LETTERS_ONLY);
    ImageView imageCaptcha;
    EditText textJawabanCaptcha;

    SharedPreferences sharedPreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_prefernces";
    public static final String session_status = "session_status";

    private static final String TAG = Login.class.getSimpleName();

    public final static String tagIdPasien = "idPasien";
    public final static String tagNoIdentitas = "noIdentitas";
    public final static String tagJenisIdentitas = "jenisIdentitas";
    public final static String tagNama = "nama";
    public final static String tagKelamin = "kelamin";
    public final static String tagTanggalLahir = "tanggalLahir";
    public final static String tagTanggalDaftar = "tanggalDaftar";
    public final static String tagNoHP1 = "noHP1";
    public final static String tagNoTelp = "noTelp";
    public final static String tagNoBpjs = "noBpjs";
    public final static String tagPekerjaan = "pekerjaan";
    public final static String tagStatus = "status";
    public final static String tagAgama = "agama";
    public final static String tagNoRm = "noRm";
    public final static String tagStatusPasien = "statusPasien";
    public final static String tagStatusBpjs = "statusBpjs";
    public final static String tagStatusKematian = "statusKematian";
    public final static String tagNoKartu = "noKartu";
    public final static String tagUmur = "umur";
    public final static String tagGolDarah = "golDarah";

    String tag_json_obj = "json_obj_req";

    String nama, noRm, noBpjs, idPasien;

    ConnectivityManager connectivityManager;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView lewati = (TextView) findViewById(R.id.textLewati);
        lewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "Tidak ada koneksi Internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        textRM = (EditText) findViewById(R.id.editTextRM);
        textTgLahir = (EditText) findViewById(R.id.editTextTgLahir);
        buttonLogin = (Button) findViewById(R.id.btnLogin);
        btnLihat = (Button) findViewById(R.id.button2);

        btnLihat.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        textTgLahir.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        textTgLahir.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });

        imageCaptcha = (ImageView) findViewById(R.id.imageViewCaptcha);
        textJawabanCaptcha = (EditText) findViewById(R.id.editTextCaptcha);

        imageCaptcha.setImageBitmap(textCaptcha.getImage());

        //===Cek sesi==//
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, true);
        nama = sharedPreferences.getString(tagNama, null);
        noRm = sharedPreferences.getString(tagNoRm, null);
        noBpjs = sharedPreferences.getString(tagNoBpjs, null);
        idPasien = sharedPreferences.getString(tagIdPasien, null);

        if (session) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra(tagNama, nama);
            intent.putExtra(tagNoRm, noRm);
            intent.putExtra(tagNoBpjs, noBpjs);
            intent.putExtra(tagIdPasien, idPasien);
            finish();
            startActivity(intent);
        }
        //=======//


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String noRM = textRM.getText().toString();
                final String tgLahir = textTgLahir.getText().toString();

                if (!textCaptcha.checkAnswer(textJawabanCaptcha.getText().toString().trim())){
                    textJawabanCaptcha.setError("Captcha tidak cocok");
                    int numberOfCaptchaFalse = 0;
                    numberOfCaptchaFalse++;
                } else {
                    if (noRM.trim().length() > 0 && tgLahir.trim().length() > 0) {
                        if (connectivityManager.getActiveNetworkInfo() != null
                                && connectivityManager.getActiveNetworkInfo().isAvailable()
                                && connectivityManager.getActiveNetworkInfo().isConnected()){
                            cekLogin(noRM, tgLahir);
                        } else {
                            Toast.makeText(getApplicationContext(), "Tidak ada koneksi!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG ).show();
                    }
                }

            }
        });
    }

    private void cekLogin(final String nomorRM, final String tgLahir){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLogin + nomorRM + "/" + tgLahir, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    /*String respon = jsonObject.getString("idPasien");*/

                    if (jsonObject.has("idPasien")) {
                        String idPasien = jsonObject.getString("idPasien");
                        String noIdentitas = jsonObject.getString("noIdentitas");
                        String jenisIdentitas = jsonObject.getString("jenisIdentitas");
                        String nama = jsonObject.getString("nama");
                        String kelamin = jsonObject.getString("kelamin");
                        String tanggalLahir = jsonObject.getString("tanggalLahir");
                        String tanggalDaftar = jsonObject.getString("tanggalDaftar");
                        String noHP1 = jsonObject.getString("noHp1");
                        String noTelp = jsonObject.getString("noTelp");
                        String pekerjaan = jsonObject.getString("pekerjaan");
                        String status = jsonObject.getString("status");
                        String agama = jsonObject.getString("agama");
                        String noRm = jsonObject.getString("noRm");
                        String statusPasien = jsonObject.getString("statusPasien");
                        String statusBpjs = jsonObject.getString("statusBpjs");
                        String noBpjs = jsonObject.getString("noBpjs");
                        String statusKematian = jsonObject.getString("statusKematian");
                        String noKartu = jsonObject.getString("noKartu");
                        String umur = jsonObject.getString("umur");
                        String golDarah = jsonObject.getString("golDarah");

                        //Menyimpan ke sharedpreferences

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(tagIdPasien, idPasien);
                        editor.putString(tagNoIdentitas, noIdentitas);
                        editor.putString(tagJenisIdentitas, jenisIdentitas);
                        editor.putString(tagNama, nama);
                        editor.putString(tagKelamin, kelamin);
                        editor.putString(tagTanggalLahir, tanggalLahir);
                        editor.putString(tagTanggalDaftar, tanggalDaftar);
                        editor.putString(tagNoHP1, noHP1);
                        editor.putString(tagNoTelp, noTelp);
                        editor.putString(tagPekerjaan, pekerjaan);
                        editor.putString(tagStatus, status);
                        editor.putString(tagAgama, agama);
                        editor.putString(tagStatusPasien, statusPasien);
                        editor.putString(tagStatusBpjs, statusBpjs);
                        editor.putString(tagNoBpjs, noBpjs);
                        editor.putString(tagStatusKematian, statusKematian);
                        editor.putString(tagNoKartu, noKartu);
                        editor.putString(tagUmur, umur);
                        editor.putString(tagGolDarah, golDarah);
                        editor.putString(tagNoRm, noRm);
                        editor.putString(tagTanggalLahir, tanggalLahir);
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Selamat datang " + nama, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra(tagNama, nama);
                        intent.putExtra(tagNoRm, noRm);
                        intent.putExtra(tagNoBpjs, noBpjs);
                        intent.putExtra(tagIdPasien, idPasien);
                        finish();
                        startActivity(intent);


                    } else {

                        String errorMessage = jsonObject.getString("errorMessage");
                        Toast.makeText(getApplicationContext(), errorMessage , Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login error: " + error.getMessage());
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest,tag_json_obj);

    }
}
