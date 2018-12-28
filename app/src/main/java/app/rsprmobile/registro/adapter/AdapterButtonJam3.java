package app.rsprmobile.registro.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.rsprmobile.registro.R;

import static com.android.volley.VolleyLog.TAG;

public class AdapterButtonJam3 extends RecyclerView.Adapter<AdapterButtonJam3.ViewHolder> {
    private Context context;
    private ArrayList<Integer> arrayJumlah;
    private ArrayList<Integer> nomorDipakai;
    private int kuotaMax, kuotaPerjam;
    private String idKlinikDokter,idPasien, jaminan, tracer, tgl, statusPasien, tglDaftar, nomorAntri;
    private String range;
    private int noAntri;

    public AdapterButtonJam3(Context context, ArrayList<Integer> arrayJumlah, int kuotaMax, int kuotaPerjam,
                             String idKlinikDokter, String idPasien, String jaminan, String tracer,
                             String tgl, String statusPasien, ArrayList<Integer> nomorDipakai){
        this.context = context;
        this.arrayJumlah = arrayJumlah;
        this.kuotaMax = kuotaMax;
        this.kuotaPerjam = kuotaPerjam;
        this.idKlinikDokter = idKlinikDokter;
        this.idPasien = idPasien;
        this.jaminan = jaminan;
        this.tracer = tracer;
        this.tgl = tgl;
        this.statusPasien = statusPasien;
        this.nomorDipakai = nomorDipakai;
    }


    @NonNull
    @Override
    public AdapterButtonJam3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_nomor, null);

        return new AdapterButtonJam3.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterButtonJam3.ViewHolder viewHolder, final int position) {
        viewHolder.button.setText(String.valueOf(arrayJumlah.get(position).toString()));

        for (int i=0; i < nomorDipakai.size(); i++){
            if(arrayJumlah.contains(nomorDipakai.get(i))){
                //do something for equals
                if (viewHolder.button.getText().toString().equals(nomorDipakai.get(i).toString())){
                    viewHolder.button.setEnabled(false);
                }
                //Toast.makeText(context, "Sudah dipakai", Toast.LENGTH_SHORT).show();
            }/*else{
                //do something for not equals
                *//*int index = B_arraylist.indexOf(A_arraylist.get(i));
                B_arraylist.remove(index);*//*
            }*/
        }

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                if (pos < 2){
                    if (!jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 2 && pos < 4){
                    if (jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus non-BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 4 && pos < 6){
                    if (!jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 6 && pos < 8){
                    if (jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus non-BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 8 && pos < 10){
                    if (!jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 10 && pos < 12){
                    if (jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus non-BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 12 && pos < 14){
                    if (!jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 14 && pos < 16){
                    if (jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus non-BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 16 && pos < 18){
                    if (!jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                } else if (pos >= 18 && pos < 20){
                    if (jaminan.equals("BPJS")){
                        Toast.makeText(context, "Nomor khusus non-BPJS", Toast.LENGTH_SHORT).show();
                    } else {
                        createDialog();
                    }
                }

                String txtBtn = viewHolder.button.getText().toString();
                //Toast.makeText(v.getContext(), txtBtn, Toast.LENGTH_SHORT).show();
                nomorAntri = txtBtn;

                noAntri = Integer.parseInt(txtBtn);

                if (noAntri <= kuotaMax) {
                    if (noAntri <= kuotaPerjam) {
                        range = "1";
                    } else if (noAntri > kuotaPerjam) {
                        for (int i = 2; i <= 10; i++) {
                            if (noAntri <= kuotaPerjam * i) {
                                range = Integer.toString(i);
                                break;
                            }
                        }

                    }
                }

                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                tglDaftar = formatter.format(todayDate);

                //Toast.makeText(v.getContext(), tglDaftar, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Konfirmasi pendaftaran periksa?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                if (jaminan.equals("BPJS")){
                    daftarKunjunganBpjs();
                } else {
                    daftarKunjungan();
                }

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void daftarKunjunganBpjs() {
        final ProgressDialog loading = ProgressDialog.show(context,"Mendaftar...","Mohon Tunggu...",false,false);
        JSONObject json = new JSONObject();
        try {
            json.put("keterangan", "");
            json.put("klinikDokterId", idKlinikDokter);
            if (!statusPasien.equals("Fisioterapi")){
                json.put("statusTerakhirPasien", "Pendaftaran Kunjungan Poliklinik");
            } else {
                json.put("statusTerakhirPasien", "Pendaftaran Kunjungan Fisioterapi");
            }
            json.put("noAntrian", nomorAntri);
            json.put("jam", range);
            json.put("caraMasuk", "Sendiri");
            json.put("tanggalPeriksa", tgl);
            json.put("tracer", tracer);
            json.put("caraBayar", jaminan);
            assert idPasien != null;
            json.put("pasienId", idPasien);
            json.put("tanggalPendaftaran", tglDaftar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://192.168.11.211:8080/PendaftaranV3/PendaftaranV3/tambahKunjunganBpjsBaru", json,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("errorCode")){
                                loading.dismiss();
                                Toast.makeText(context, response.getString("errorMessage"), Toast.LENGTH_LONG).show();
                            } else {
                                loading.dismiss();
                                Toast.makeText(context, "Berhasil mendaftar no. Antri " + response.getString("noAntrian"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hideProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        requestQueue.add(jsonObjReq);

    }

    private void daftarKunjungan() {
        final ProgressDialog loading = ProgressDialog.show(context,"Mendaftar...","Mohon Tunggu...",false,false);
        JSONObject json = new JSONObject();
        try {
            json.put("keterangan", "");
            json.put("klinikDokterId", idKlinikDokter);
            if (!statusPasien.equals("Fisioterapi")){
                json.put("statusTerakhirPasien", "Pendaftaran Kunjungan Poliklinik");
            } else {
                json.put("statusTerakhirPasien", "Pendaftaran Kunjungan Fisioterapi");
            }
            json.put("noAntrian", nomorAntri);
            json.put("jam", range);
            json.put("caraMasuk", "Sendiri");
            json.put("tanggalPeriksa", tgl);
            json.put("tracer", tracer);
            json.put("caraBayar", jaminan);
            assert idPasien != null;
            json.put("pasienId", idPasien);
            json.put("tanggalPendaftaran", tglDaftar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://192.168.11.211:8080/PendaftaranV3/PendaftaranV3/tambahKunjunganBaru", json,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("errorCode")){
                                loading.dismiss();
                                Toast.makeText(context, response.getString("errorMessage"), Toast.LENGTH_LONG).show();
                            } else {
                                loading.dismiss();
                                Toast.makeText(context, "Berhasil mendaftar no. Antri " + response.getString("noAntrian"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                loading.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        requestQueue.add(jsonObjReq);
    }


    @Override
    public int getItemCount() {
        return arrayJumlah.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            button = (Button) itemView.findViewById(R.id.btnNomor);
        }
    }
}
