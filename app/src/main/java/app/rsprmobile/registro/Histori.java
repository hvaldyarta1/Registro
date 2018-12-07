package app.rsprmobile.registro;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import app.rsprmobile.registro.adapter.AdapterHistori;
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataHistori;
import app.rsprmobile.registro.data.DataPoli;

import static app.rsprmobile.registro.Poli.urlPoli;


/**
 * A simple {@link Fragment} subclass.
 */
public class Histori extends Fragment {
    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    ListView listHistori;
    List<DataHistori> itemHistori = new ArrayList<DataHistori>();
    AdapterHistori adapterHistori;

    public final static String TAG = MainActivity.class.getSimpleName();
    public final static String urlHistori = "http://192.168.11.211:8080/PendaftaranV3/PendaftaranV3/getHistoriKunjunganByRm/";


    public Histori() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewHistori = inflater.inflate(R.layout.fragment_histori, container, false);

        listHistori = (ListView) viewHistori.findViewById(R.id.listHistori);
        adapterHistori = new AdapterHistori(getActivity(), itemHistori);
        listHistori.setAdapter(adapterHistori);
        /*recyclerView = (RecyclerView) viewHistori.findViewById(R.id.rvHistori);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapterHistori = new AdapterHistori(getActivity(), itemHistori);

        recyclerView.setAdapter(adapterHistori);*/

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        String noRm = sharedPreferences.getString("noRm", null);
        dataHistori(noRm);

    return viewHistori;
    }

    private void dataHistori(String noRm){
        itemHistori.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat Data Histori...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlHistori + noRm, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject object = jsonObject.getJSONObject("kunjungan");
                        DataHistori dataHistori = new DataHistori();

                        dataHistori.setNamaKlinik(jsonObject.getString("namaKlinik"));
                        dataHistori.setDokterJadwal(jsonObject.getString("dokterJadwal"));
                        dataHistori.setNoAntrean(object.getString("noAntrian"));
                        dataHistori.setWaktuCheckin(object.getString("waktuCheckin"));
                        dataHistori.setRuang(jsonObject.getString("noRuang"));

                        itemHistori.add(dataHistori);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapterHistori.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getActivity()).setTitle("Histori Periksa");
    }

    public interface OnFragmentInteractionListener {
    }
}
