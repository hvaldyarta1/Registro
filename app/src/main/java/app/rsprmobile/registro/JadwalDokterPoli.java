package app.rsprmobile.registro;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.rsprmobile.registro.adapter.AdapterDokterPoli;
import app.rsprmobile.registro.adapter.AdapterJadwalPoli;
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataJadwal;
import app.rsprmobile.registro.data.DataJadwalPoli;


/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalDokterPoli extends Fragment {
    TextView txtNamaDokterJadwalPoli, txtNamaPoli;
    ListView listJadwalDokterPoli;
    List<DataJadwalPoli> itemJadwalPoli = new ArrayList<>();
    AdapterJadwalPoli adapterJadwalPoli;

    ProgressDialog progressDialog;

    public static final String urlJadwalDokterPoli = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/ViewDokterKlinikByIdDokterDanIdKlinik/";
    private static final String TAG = MainActivity.class.getSimpleName();

    public JadwalDokterPoli() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewJadwalDokterPoli = inflater.inflate(R.layout.fragment_jadwal_dokter_poli, container, false);

        listJadwalDokterPoli = (ListView) viewJadwalDokterPoli.findViewById(R.id.listJadwalDokterPoli);
        adapterJadwalPoli = new AdapterJadwalPoli(getActivity(), itemJadwalPoli);
        listJadwalDokterPoli.setAdapter(adapterJadwalPoli);

        Bundle bundleJadwalPoli = getArguments();
        String namaDokterPoli = bundleJadwalPoli.getString("namaDokterTetap");
        String idDokter = bundleJadwalPoli.getString("idDokter");
        String klinikId = bundleJadwalPoli.getString("klinikId");
        String namaKlinik = bundleJadwalPoli.getString("namaKlinik");

        txtNamaPoli = (TextView) viewJadwalDokterPoli.findViewById(R.id.txtNamaPoli);
        txtNamaPoli.setText("-- " + namaKlinik + " --");

        txtNamaDokterJadwalPoli = (TextView) viewJadwalDokterPoli.findViewById(R.id.txtDokterPoli);
        txtNamaDokterJadwalPoli.setText(namaDokterPoli);

        dataJadwalDokterPoli(idDokter, klinikId);


        return viewJadwalDokterPoli;
    }

    private void dataJadwalDokterPoli(String idDokter, String klinikId){
        itemJadwalPoli.clear();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Memuat jadwal dokter...");
        progressDialog.show();

        adapterJadwalPoli.notifyDataSetChanged();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlJadwalDokterPoli + idDokter + "/" + klinikId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DataJadwalPoli dataJadwalPoli = new DataJadwalPoli();

                        dataJadwalPoli.setNamaKlinik(jsonObject.getString("namaKlinik"));
                        dataJadwalPoli.setJamAwal(jsonObject.getString("jamAwal"));
                        dataJadwalPoli.setJamAkhir(jsonObject.getString("jamAkhir"));
                        dataJadwalPoli.setHariPraktek(jsonObject.getString("hariPraktek"));

                        itemJadwalPoli.add(dataJadwalPoli);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                adapterJadwalPoli.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

}
