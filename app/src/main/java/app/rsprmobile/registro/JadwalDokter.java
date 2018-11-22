package app.rsprmobile.registro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.rsprmobile.registro.adapter.AdapterJadwal;
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataJadwal;


/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalDokter extends Fragment {
    ListView listJadwal;
    AdapterJadwal adapterJadwal;
    public static final String urlJadwal = "jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganIdDokter/";
    List<DataJadwal> itemJadwal = new ArrayList<DataJadwal>();

    private static final String TAG = MainActivity.class.getSimpleName();



    public JadwalDokter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewJadwal = inflater.inflate(R.layout.fragment_jadwal_dokter, container, false);

        listJadwal = (ListView) viewJadwal.findViewById(R.id.listJadwal);

        adapterJadwal = new AdapterJadwal(getActivity(), itemJadwal);
        listJadwal.setAdapter(adapterJadwal);

        Bundle dokter = new Bundle();


    return viewJadwal;
    }

    private void jadwalDokter(String idDokter){
        itemJadwal.clear();
        adapterJadwal.notifyDataSetChanged();

        JsonArrayRequest jArr = new JsonArrayRequest(urlJadwal + idDokter, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject JSONobj = response.getJSONObject(i);

                        DataJadwal dataJadwal = new DataJadwal();

                        dataJadwal.setKlinik_id(JSONobj.getString("klinik_id"));
                        dataJadwal.setKuotaPasienPerjam(JSONobj.getString("kuotaPasienPerjam"));
                        dataJadwal.setJamAwal(JSONobj.getString("jamAwal"));
                        dataJadwal.setJamAkhir(JSONobj.getString("jamAkhir"));
                        dataJadwal.setNamaKlinik(JSONobj.getString("namaKlinik"));
                        dataJadwal.setRuangPeriksa(JSONobj.getString("ruangPeriksa"));
                        dataJadwal.setHariPraktek(JSONobj.getString("hariPraktek"));

                        itemJadwal.add(dataJadwal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterJadwal.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                /*swipe.setRefreshing(false);*/
            }
        });

        AppController.getInstance().addToRequestQueue(jArr);
    }



}