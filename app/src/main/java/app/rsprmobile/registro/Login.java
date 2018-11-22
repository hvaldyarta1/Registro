package app.rsprmobile.registro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText textRM;
    EditText textTgLahir;
    Button buttonLogin;
    private static final String urlLogin = "http://192.168.11.183:8080/PasienV2/PasienV2/cekLoginRegistro/";

    String idPasien;

    private static final String TAG = Login.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textRM = (EditText) findViewById(R.id.editTextRM);
        textTgLahir = (EditText) findViewById(R.id.editTextTgLahir);
        buttonLogin = (Button) findViewById(R.id.btnLogin);

        String noRM = textRM.getText().toString();
        String tgLahir = textTgLahir.getText().toString();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /*private void prosesLogin(String nomorRM, String tgLahir){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login response: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    idPasien = jsonObject.getString("idPasien");

                    if (idPasien != null) {

                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG ).show();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
            }
        })
    }*/
}
