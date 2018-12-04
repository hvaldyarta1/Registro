package app.rsprmobile.registro;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.rsprmobile.registro.adapter.AdapterJadwal;
import app.rsprmobile.registro.adapter.AdapterJadwalPoli;
import app.rsprmobile.registro.adapter.AdapterSinnerSemuaDokter;
import app.rsprmobile.registro.adapter.AdapterSpinnerDokterPoli;
import app.rsprmobile.registro.adapter.AdapterSpinnerJamPraktek;
import app.rsprmobile.registro.adapter.AdapterSpinnerPoli;
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataDokter;
import app.rsprmobile.registro.data.DataDokterPoli;
import app.rsprmobile.registro.data.DataJadwal;
import app.rsprmobile.registro.data.DataJadwalPoli;
import app.rsprmobile.registro.data.DataJamPraktek;
import app.rsprmobile.registro.data.DataPoli;

import static app.rsprmobile.registro.Dokter.urlDokter;
import static app.rsprmobile.registro.DokterPoli.urlDokterPoli;
import static app.rsprmobile.registro.JadwalDokter.urlJadwal;
import static app.rsprmobile.registro.JadwalDokterPoli.urlJadwalDokterPoli;
import static app.rsprmobile.registro.Poli.urlPoli;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pendaftaran extends Fragment {

    ProgressDialog progressDialog;

    private Button btnTanggal;
    private TextView textTanggalDipilih;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat, formatTanggal;

    Spinner spinnerPenjamin, spinnerJenis, spinnerPoli, spinnerDokter, spinnerJamPraktek;
    AdapterSpinnerPoli adapterSpinnerPoli;
    AdapterSpinnerDokterPoli adapterSpinnerDokter;
    AdapterSinnerSemuaDokter adapterSinnerSemuaDokter;
    AdapterJadwal adapterJadwal;
    AdapterJadwalPoli adapterJadwalPoli;
    AdapterSpinnerJamPraktek adapterSpinnerJamPraktek;
    List<DataDokter> semuaDokter = new ArrayList<DataDokter>();
    List<DataDokterPoli> dokterPoli = new ArrayList<DataDokterPoli>();
    List<DataPoli> poli = new ArrayList<DataPoli>();
    List<DataJadwal> jadwalSemuaDokter = new ArrayList<DataJadwal>();
    List<DataJadwalPoli> jadwalDokterPoli = new ArrayList<DataJadwalPoli>();
    List<DataJamPraktek> itemJamPraktek = new ArrayList<DataJamPraktek>();

    public final String urlJamPraktek = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganidKlinikidDokteridTanggal/";
    public final String urlJamPraktekDokter = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganTanggalDokter/";

    ListView listJadwalDokter;

    TextView textNamaDokter;

    Bundle bundle;

    SharedPreferences sharedPreferences;
    String jaminan, iddokter, klinikid, tgl;
    int kuotapasien, kuotaperjam;

    private static final String TAG = MainActivity.class.getSimpleName();

    public Pendaftaran() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewPendaftaran = inflater.inflate(R.layout.fragment_pendaftaran, container, false);

        spinnerPenjamin = (Spinner) viewPendaftaran.findViewById(R.id.spinnerJaminan);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.penjamin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenjamin.setAdapter(adapter);

        spinnerJenis = (Spinner) viewPendaftaran.findViewById(R.id.spinnerJenisPeriksa);
        ArrayAdapter<CharSequence> adapterJenis = ArrayAdapter.createFromResource(getActivity(), R.array.jenis_periksa, android.R.layout.simple_spinner_item);
        adapterJenis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenis.setAdapter(adapterJenis);

        spinnerPoli = (Spinner) viewPendaftaran.findViewById(R.id.spinnerPoli);
        adapterSpinnerPoli = new AdapterSpinnerPoli(getActivity(), poli);
        spinnerPoli.setAdapter(adapterSpinnerPoli);

        adapterSpinnerJamPraktek = new AdapterSpinnerJamPraktek(getActivity(), itemJamPraktek);

        spinnerDokter = (Spinner) viewPendaftaran.findViewById(R.id.spinnerDokter);
        spinnerJamPraktek = (Spinner) viewPendaftaran.findViewById(R.id.spinnerJamPraktek);

        textNamaDokter = (TextView) viewPendaftaran.findViewById(R.id.txtViewDokterDipilih);

        listJadwalDokter = (ListView) viewPendaftaran.findViewById(R.id.listJadwal);
        listJadwalDokter.setOnTouchListener(new View.OnTouchListener() { //Scrollable list view dlm ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        adapterJadwal = new AdapterJadwal(getActivity(), jadwalSemuaDokter);
        adapterJadwalPoli = new AdapterJadwalPoli(getActivity(), jadwalDokterPoli);

        sharedPreferences = this.getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        spinnerPenjamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jaminan = spinnerPenjamin.getSelectedItem().toString();

                if (jaminan.trim().equals("BPJS")){

                   String noBpjs = sharedPreferences.getString("noBpjs", null);
                   if (noBpjs != null){
                       Toast.makeText(getContext(), jaminan + " dipilih", Toast.LENGTH_SHORT).show();
                       spinnerJenis.setVisibility(View.VISIBLE);
                   } else {
                       Toast.makeText(getContext(), "Anda tidak memiliki no. BPJS", Toast.LENGTH_LONG).show();
                   }

                } else if (jaminan.trim().equals("Asuransi")){
                    Toast.makeText(getContext(), jaminan + " dipilih", Toast.LENGTH_SHORT).show();
                    spinnerJenis.setVisibility(View.VISIBLE);
                } else if (jaminan.trim().equals("UMUM")){
                    Toast.makeText(getContext(), jaminan + " dipilih", Toast.LENGTH_SHORT).show();
                    spinnerJenis.setVisibility(View.VISIBLE);
                } else if (jaminan.trim().equals("-- Pilih Penjamin --")){
                    spinnerJenis.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String jenis = spinnerJenis.getSelectedItem().toString();

                if (jenis.trim().equals("Poli")){
                    spinnerPoli.setVisibility(View.VISIBLE);
                    spinnerDokter.setVisibility(View.GONE);
                    dataPoli();
                } else if (jenis.trim().equals("Dokter")){
                    spinnerDokter.setVisibility(View.VISIBLE);
                    spinnerPoli.setVisibility(View.GONE);
                    adapterSinnerSemuaDokter = new AdapterSinnerSemuaDokter(getActivity(), semuaDokter);
                    spinnerDokter.setAdapter(adapterSinnerSemuaDokter);

                    dataDokter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinnerPoli.getSelectedItem() == "--Pilih Klinik--"){
                    spinnerDokter.setVisibility(View.GONE);
                    jadwalSemuaDokter.clear();
                } else {
                    klinikid = poli.get(position).getIdKlinik();
                    loadDokterPoli(klinikid);
                    adapterSpinnerDokter = new AdapterSpinnerDokterPoli(getActivity(), dokterPoli);
                    spinnerDokter.setAdapter(adapterSpinnerDokter);

                    spinnerDokter.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerDokter.getAdapter() == adapterSpinnerDokter){ //Dokter Poli
                    String dokter = dokterPoli.get(position).getNamaDokterTetap();
                    iddokter = dokterPoli.get(position).getIdDokterTetap();
                    /*kuotapasien = jadwalDokterPoli.get(position).getKuotaPasien();*/
                    /*kuotaperjam = jadwalDokterPoli.get(position).getKuotaPerJam();*/
                    textNamaDokter.setText(dokter);
                    dataJadwalDokterPoli(iddokter, klinikid);
                    listJadwalDokter.setVisibility(View.VISIBLE);
                    listJadwalDokter.setAdapter(adapterJadwalPoli);
                    btnTanggal.setEnabled(true);

                } else if (spinnerDokter.getAdapter() == adapterSinnerSemuaDokter){ //Semua Dokter
                    String dokter = semuaDokter.get(position).getNamaDokter();
                    iddokter = semuaDokter.get(position).getDokterId();
                    textNamaDokter.setText(dokter);
                    jadwalDokter(iddokter);
                    /*kuotapasien = jadwalSemuaDokter.get(position).getKuotaPasien();*/
                    listJadwalDokter.setVisibility(View.VISIBLE);
                    listJadwalDokter.setAdapter(adapterJadwal);
                    btnTanggal.setEnabled(true);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //-----DatePicker
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        formatTanggal = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        textTanggalDipilih =(TextView) viewPendaftaran.findViewById(R.id.textViewTanggal);
        btnTanggal = (Button) viewPendaftaran.findViewById(R.id.buttonTanggal);
        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        //---------------

    return viewPendaftaran;
    }

    private void dataPoli(){
        poli.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat Data Poli...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlPoli, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DataPoli dataPoli = new DataPoli();

                        dataPoli.setKeterangan(jsonObject.getString("keterangan"));
                        dataPoli.setFotoKlinik(jsonObject.getString("fotoKlinik"));
                        dataPoli.setNamaKlinik(jsonObject.getString("namaKlinik"));
                        dataPoli.setIdKlinik(jsonObject.getString("idKlinik"));
                        dataPoli.setRuanganKlinik(jsonObject.getString("ruanganKlinik"));

                        poli.add(dataPoli);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapterSpinnerPoli.notifyDataSetChanged();
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

    private void loadDokterPoli(String idPoli){
        dokterPoli.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat Dokter. . .");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlDokterPoli + idPoli, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DataDokterPoli dataDokterPoli = new DataDokterPoli();

                        if (jsonObject.has("idDokterTetap")){
                            dataDokterPoli.setIdDokterTetap(jsonObject.getString("idDokterTetap"));
                            dataDokterPoli.setNamaDokterTetap(jsonObject.getString("namaDokterTetap"));
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("errorMessage"), Toast.LENGTH_LONG).show();
                        }

                        dokterPoli.add(dataDokterPoli);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterSpinnerDokter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    private void dataDokter(){
        semuaDokter.clear();
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

                        semuaDokter.add(dataDokter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapterSinnerSemuaDokter.notifyDataSetChanged();
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


    private void jadwalDokter(String idDokter){
        jadwalSemuaDokter.clear();
        adapterJadwal.notifyDataSetChanged();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Memuat Jadwal Dokter. . .");
        progressDialog.show();

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
                        dataJadwal.setKuotaPasien(JSONobj.getString("kuotaPasien"));
                        dataJadwal.setJamAwal(JSONobj.getString("jamAwal"));
                        dataJadwal.setJamAkhir(JSONobj.getString("jamAkhir"));
                        dataJadwal.setNamaKlinik(JSONobj.getString("namaKlinik"));
                        dataJadwal.setRuangPeriksa(JSONobj.getString("ruangPeriksa"));
                        dataJadwal.setHariPraktek(JSONobj.getString("hariPraktek"));

                        jadwalSemuaDokter.add(dataJadwal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterJadwal.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                /*swipe.setRefreshing(false);*/
            }
        });

        AppController.getInstance().addToRequestQueue(jArr);
    }


    private void dataJadwalDokterPoli(String idDokter, String klinikId){
        jadwalDokterPoli.clear();

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
                        dataJadwalPoli.setKuotaPasien(jsonObject.getString("kuotaPasien"));
                        dataJadwalPoli.setKuotaPerJam(jsonObject.getString("kuotaPerJam"));
                        jadwalDokterPoli.add(dataJadwalPoli);
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

    private void jamPraktekTanggal(String tanggal, String idDokter, String klinikId){
        itemJamPraktek.clear();
        adapterSpinnerJamPraktek.notifyDataSetChanged();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Memuat Jam Praktek. . .");
        progressDialog.show();

        JsonArrayRequest jArr = new JsonArrayRequest(urlJamPraktek + tanggal + "/and/" + klinikId+ "/and/" + idDokter, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject JSONobj = response.getJSONObject(i);

                        DataJamPraktek dataJamPraktek = new DataJamPraktek();

                        dataJamPraktek.setJamAwal(JSONobj.getString("jamAwal"));
                        dataJamPraktek.setJamAkhir(JSONobj.getString("jamAkhir"));

                        itemJamPraktek.add(dataJamPraktek);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterSpinnerJamPraktek.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                /*swipe.setRefreshing(false);*/
            }
        });

        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void jamPraktekTanggalDokter(String tanggal, String idDokter){
        itemJamPraktek.clear();
        adapterSpinnerJamPraktek.notifyDataSetChanged();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Memuat Jam Praktek. . .");
        progressDialog.show();

        JsonArrayRequest jArr = new JsonArrayRequest(urlJamPraktekDokter + tanggal + "/dan/" + idDokter, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject JSONobj = response.getJSONObject(i);

                        DataJamPraktek dataJamPraktek = new DataJamPraktek();

                        dataJamPraktek.setJamAwal(JSONobj.getString("jamMulaiPraktek"));
                        dataJamPraktek.setJamAkhir(JSONobj.getString("jamSelesaiPraktek"));

                        itemJamPraktek.add(dataJamPraktek);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterSpinnerJamPraktek.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                /*swipe.setRefreshing(false);*/
            }
        });

        AppController.getInstance().addToRequestQueue(jArr);
    }


    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                textTanggalDipilih.setText("Tanggal dipilih: " + dateFormat.format(newDate.getTime()));
                tgl = formatTanggal.format(newDate.getTime());
                spinnerJamPraktek.setAdapter(adapterSpinnerJamPraktek);

                if (klinikid == null){
                    jamPraktekTanggalDokter(tgl, iddokter);
                    spinnerJamPraktek.setVisibility(View.VISIBLE);

                } else {
                    jamPraktekTanggal(tgl, iddokter, klinikid);
                    spinnerJamPraktek.setVisibility(View.VISIBLE);

                }

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    datePickerDialog.show();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getActivity()).setTitle("Daftar Periksa");
    }

    interface OnFragmentInteractionListener {
    }
}
