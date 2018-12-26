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

import app.rsprmobile.registro.adapter.AdapterGridDokter;
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataDokter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dokter extends Fragment {
    ProgressDialog progressDialog;
    AdapterGridDokter adapterGridDokter;

    public static final String urlDokter = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/DataSemuaDokter";
    List<DataDokter> itemdokter = new ArrayList<DataDokter>();
    RecyclerView rvDokter;

    private static final String TAG = MainActivity.class.getSimpleName();

    public Dokter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewDokter = inflater.inflate(R.layout.fragment_dokter, container, false);

        rvDokter = (RecyclerView) viewDokter.findViewById(R.id.rvDokter);

        rvDokter.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataDokter();

        return viewDokter;
    }

    private void dataDokter(){
        itemdokter.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat Data Dokter. . .");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlDokter, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        DataDokter dataDokter = new DataDokter();

                        dataDokter.setDokterId(jsonObject.getString("dokterId"));
                        dataDokter.setNamaDokter(jsonObject.getString("namaDokter"));

                        itemdokter.add(dataDokter);

                        rvDokter.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        adapterGridDokter = new AdapterGridDokter(getContext(), itemdokter);
                        rvDokter.setAdapter(adapterGridDokter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapterGridDokter.notifyDataSetChanged();
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

        getActivity().setTitle("Dokter");
    }

    public interface OnFragmentInteractionListener {
    }
}
