package app.rsprmobile.registro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataDokter;
import app.rsprmobile.registro.data.DataDokterPoli;


/**
 * A simple {@link Fragment} subclass.
 */
public class DokterPoli extends Fragment {
    TextView textViewNamaPoli, textViewKeterangan, textViewRuanganKlinik;
    ListView listDokterPoli;
    AdapterDokterPoli adapterDokterPoli;
    List<DataDokterPoli> itemDokterPoli = new ArrayList<DataDokterPoli>();
    public static final String urlDokterPoli = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokteridKlinikWeb/";

    private static final String TAG = MainActivity.class.getSimpleName();

    public DokterPoli() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewDokterPoli = inflater.inflate(R.layout.fragment_dokter_poli, container, false);

        textViewNamaPoli = (TextView) viewDokterPoli.findViewById(R.id.txtViewNamaPoli);
        textViewKeterangan = (TextView) viewDokterPoli.findViewById(R.id.txtViewKeterangan);
        textViewRuanganKlinik = (TextView) viewDokterPoli.findViewById(R.id.txtViewRuanganKlinik);

        Bundle bundlePoli = getArguments();
        assert bundlePoli != null;
        final String idKlinik = bundlePoli.getString("idKlinik");
        final String namaKlinik = bundlePoli.getString("namaKlinik");
        String keterangan = bundlePoli.getString("keterangan");
        String ruanganKlinik = bundlePoli.getString("ruanganKlinik");

        textViewNamaPoli.setText(namaKlinik);
        textViewKeterangan.setText("Keterangan: " + keterangan);
        textViewRuanganKlinik.setText("Ruangan Klinik: " + ruanganKlinik);

        listDokterPoli = (ListView) viewDokterPoli.findViewById(R.id.listDokterPoli);
        adapterDokterPoli = new AdapterDokterPoli(getActivity(), itemDokterPoli);
        listDokterPoli.setAdapter(adapterDokterPoli);

        listDokterPoli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idDokter = itemDokterPoli.get(position).getIdDokterTetap();
                String namaDokterPoli = itemDokterPoli.get(position).getNamaDokterTetap();

                Bundle bundleDokterPoli = new Bundle();
                bundleDokterPoli.putString("idDokter", idDokter);
                bundleDokterPoli.putString("klinikId", idKlinik);
                bundleDokterPoli.putString("namaDokterTetap", namaDokterPoli);
                bundleDokterPoli.putString("namaKlinik", namaKlinik);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                JadwalDokterPoli jadwalDokterPoli = new JadwalDokterPoli();
                jadwalDokterPoli.setArguments(bundleDokterPoli);

                fragmentTransaction.replace(R.id.fragment_container, jadwalDokterPoli).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        /*listDokterPoli.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dokterId = itemDokterPoli.get(position).getIdDokterTetap();

                JadwalDokterPoli jadwalDokterPoli = new JadwalDokterPoli();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.fragment_container, jadwalDokterPoli)
                        .addToBackStack(null)
                        .commit();

            }
        });*/
        dokterPoli(idKlinik);

    return viewDokterPoli;
    }

    private void dokterPoli(String idPoli){
        itemDokterPoli.clear();
        adapterDokterPoli.notifyDataSetChanged();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlDokterPoli + idPoli, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DataDokterPoli dataDokterPoli = new DataDokterPoli();

                        dataDokterPoli.setIdDokterTetap(jsonObject.getString("idDokterTetap"));
                        dataDokterPoli.setNamaDokterTetap(jsonObject.getString("namaDokterTetap"));

                        itemDokterPoli.add(dataDokterPoli);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterDokterPoli.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

}
