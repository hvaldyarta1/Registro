package app.rsprmobile.registro;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

import app.rsprmobile.registro.adapter.AdapterGridPoli;
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataPoli;


/**
 * A simple {@link Fragment} subclass.
 */
public class Poli extends Fragment {
    public static final String urlPoli = "http://192.168.11.212:8080/Poliklinik-v02-0.0.2/Poli/DataSemuaKlinik";
    List<DataPoli> itemPoli = new ArrayList<DataPoli>();
    RecyclerView rvPoli;
    AdapterGridPoli adapterGridPoli;

    private static final String TAG = MainActivity.class.getSimpleName();

    ProgressDialog progressDialog;

    public Poli() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewPoli = inflater.inflate(R.layout.fragment_poli, container, false);

        rvPoli = (RecyclerView) viewPoli.findViewById(R.id.rvPoli);

        rvPoli.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataPoli();

        return viewPoli;
    }



    private void dataPoli(){
        itemPoli.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat Data Poli...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlPoli, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 1; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DataPoli dataPoli = new DataPoli();

                        dataPoli.setKeterangan(jsonObject.getString("keterangan"));
                        dataPoli.setFotoKlinik(jsonObject.getString("fotoKlinik"));
                        dataPoli.setNamaKlinik(jsonObject.getString("namaKlinik"));
                        dataPoli.setIdKlinik(jsonObject.getString("idKlinik"));
                        dataPoli.setRuanganKlinik(jsonObject.getString("ruanganKlinik"));

                        itemPoli.add(dataPoli);

                        rvPoli.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        adapterGridPoli = new AdapterGridPoli(getContext(), itemPoli);
                        rvPoli.setAdapter(adapterGridPoli);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapterGridPoli.notifyDataSetChanged();
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

        getActivity().setTitle("Poli");
    }

    public interface OnFragmentInteractionListener {
    }
}
