package app.rsprmobile.registro;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import app.rsprmobile.registro.adapter.AdapterButtonNomor;
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
import app.rsprmobile.registro.data.DataKlinik;
import app.rsprmobile.registro.data.DataNomorAntrianDipakai;
import app.rsprmobile.registro.data.DataPoli;

import static app.rsprmobile.registro.Dokter.urlDokter;
import static app.rsprmobile.registro.DokterPoli.urlDokterPoli;
import static app.rsprmobile.registro.JadwalDokter.urlJadwal;
import static app.rsprmobile.registro.JadwalDokterPoli.urlJadwalDokterPoli;
import static app.rsprmobile.registro.Poli.urlPoli;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pendaftaran extends Fragment implements AdapterView.OnItemClickListener {

    ProgressDialog progressDialog;

    int mKuotaPasien;

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
    AdapterButtonNomor adapterButtonNomor;
    ArrayAdapter<String> adapterJamPraktek;
    List<DataDokter> semuaDokter = new ArrayList<DataDokter>();
    List<DataDokterPoli> dokterPoli = new ArrayList<DataDokterPoli>();
    List<DataPoli> poli = new ArrayList<DataPoli>();
    List<DataJadwal> jadwalSemuaDokter = new ArrayList<DataJadwal>();
    List<DataJadwalPoli> jadwalDokterPoli = new ArrayList<DataJadwalPoli>();
    List<DataKlinik> itemDataKlinik = new ArrayList<DataKlinik>();
    List<DataNomorAntrianDipakai> nomorAntrianDipakai = new ArrayList<>();

    ArrayList<String> arJamPraktek = new ArrayList<String>(); //rev Spinner item
    ArrayList<Integer> arrayKuota = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> nomorDipakai = new ArrayList<Integer>(); //noAntrian Dipakai

    public final String urlJamPraktek = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganidKlinikidDokteridTanggal/";
    public final String urlJamPraktekDokter = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganTanggalDokter/";
    public final String urlKlinik = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganIdKlinikIdDokterIdTanggalWaktuAwal/";
    public final String urlAntrianDipakai = "http://192.168.11.211:8080/PendaftaranV3/PendaftaranV3/getAntrianByKlinikDokterDanTanggalPeriksa/";
    public final String urlPendaftaranKunjungan = "http://192.168.29.7:8888/PendaftaranV3/tambahKunjunganBaru";

    ListView listJadwalDokter, listRange;

    TextView textNamaDokter;

    Bundle bundle;

    SharedPreferences sharedPreferences;
    String jaminan, iddokter, klinikid, tgl, jamAwal
            , idKlinikDokter, namaKlinik, statusPasien, tracer, tglDaftar;

    public String noAntrian;

    RecyclerView rvButtonNomor;

    private int i, kuotapasien, kuotaperjam;
    TextView textViewKuotaPerjam;
    int txtKuotaPerjam;

    private static final String TAG = MainActivity.class.getSimpleName();


    public Pendaftaran() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viewPendaftaran = inflater.inflate(R.layout.fragment_pendaftaran, container, false);

        textViewKuotaPerjam = (TextView) viewPendaftaran.findViewById(R.id.textViewKuotaPerjam);
        rvButtonNomor = (RecyclerView) viewPendaftaran.findViewById(R.id.rvButton);
        rvButtonNomor.setLayoutManager(new LinearLayoutManager(getActivity()));

        listRange = (ListView) viewPendaftaran.findViewById(R.id.listRange);

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

        /*adapterSpinnerJamPraktek = new AdapterSpinnerJamPraktek(getActivity(), itemJamPraktek);*/

        spinnerDokter = (Spinner) viewPendaftaran.findViewById(R.id.spinnerDokter);
        spinnerJamPraktek = (Spinner) viewPendaftaran.findViewById(R.id.spinnerJamPraktek);

        arJamPraktek.add("-- Pilih Jam Praktek --");
        adapterJamPraktek = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                arJamPraktek);


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
                statusPasien = poli.get(position).getNamaKlinik();

                if (spinnerPoli.getSelectedItem() == "--Pilih Klinik--"){
                    spinnerDokter.setVisibility(View.GONE);
                    jadwalSemuaDokter.clear();
                } else {
                    klinikid = poli.get(position).getIdKlinik();
                    loadDokterPoli(klinikid);
                    Toast.makeText(getActivity(), klinikid, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), iddokter, Toast.LENGTH_SHORT).show();
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
                    listJadwalDokter.setVisibility(View.VISIBLE);
                    listJadwalDokter.setAdapter(adapterJadwal);
                    btnTanggal.setEnabled(true);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerJamPraktek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!spinnerJamPraktek.getSelectedItem().toString().equals("-- Pilih Jam Praktek --")){
                    String jam = spinnerJamPraktek.getSelectedItem().toString();//dataJamPraktek.getJamAwal();
                    Toast.makeText(getContext(), jam, Toast.LENGTH_SHORT).show();

                    String splitJam[] = jam.split("-");
                    String jam1 = splitJam[0];
                    String jam2 = splitJam[1];

                    dataKlinik(tgl, iddokter, klinikid, jam1);
                    nomorAntrianDipakai(idKlinikDokter, tgl);

                    // Range
                    String[] jamawal = jam1.split(":");
                    String jamawal1 = jamawal[0];
                    String jamawal2 = jamawal[1];

                    String[] jamakhir = jam2.split(":");
                    String jamselesai1 = jamakhir[0]; String nmrAkhir = String.valueOf(kuotapasien);
                    String jamselesai2 = jamakhir[1];

                    int jamp1 = Integer.parseInt(jamawal1);
                    int jamp3 = (Integer.parseInt(jamselesai1) - jamp1);

                    int jamrange; int nomorRange;

                    String jamranges1; String antriRange1;
                    String jamranges2; String antriRange2;

                    ArrayList<String> arj = new ArrayList<String>();
                    for (int lit = 1; lit <= jamp3; lit++) {
                        jamrange = jamp1 + 1;

                        if (jamp1 > 24) {
                            jamp1 = jamp1 - 24;
                        } else if (jamp1 == 24) {
                            jamp1 = 00;
                        }

                        jamranges1 = String.valueOf(jamp1);
                        if ("0".equals(jamranges1)) {
                            jamranges1 = "00";
                        }

                        if (jamrange > 24) {
                            jamrange = jamrange - 24;
                        } else if (jamrange == 24) {
                            jamrange = 00;
                        }

                        jamranges2 = String.valueOf(jamrange);
                        if ("0".equals(jamranges2)) {
                            jamranges2 = "00";
                        }

                        String range1 = (jamranges1 + ":" + jamawal2);
                        String range2 = (jamranges2 + ":" + jamselesai2);

                        jamp1++;

                        arj.add(range1 + " - " + range2);
                    }

                    final ArrayAdapter<String> adapterRange = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, arj);
                    listRange.setAdapter(adapterRange);

                    String txtKuotaPerjam = String.valueOf(kuotaperjam);
                    textViewKuotaPerjam.setText("Kuota pasien perjam: " + txtKuotaPerjam);
                }

                /*Toast.makeText(getContext(), idKlinikDokter, Toast.LENGTH_SHORT).show();*/


                for (int i = 1; i <= kuotapasien; i++){
                    arrayKuota.add(i);
                    arrayKuota.removeAll(nomorDipakai);

                }


                rvButtonNomor.setLayoutManager(new GridLayoutManager(getActivity(),5));
                adapterButtonNomor = new AdapterButtonNomor(getActivity(), arrayKuota);
                rvButtonNomor.setAdapter(adapterButtonNomor);

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

    private void dataKlinik(String tanggalJadwal, String idDokter, String idKlinik, String waktuAwal){
        itemDataKlinik.clear();
        arrayKuota.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat Nomor Urut...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlKlinik + tanggalJadwal + "/and/" + idDokter + "/and/"
                + idKlinik + "/and/" + waktuAwal, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject object = jsonObject.getJSONObject("klinikDokter");
                        DataKlinik dataKlinik = new DataKlinik();

                        namaKlinik = jsonObject.getString("namaKlinik");
                        dataKlinik.setNamaKlinik(jsonObject.getString("namaKlinik"));
                        idKlinikDokter = object.getString("idKlinikDokter");
                        dataKlinik.setIdKlinikDokter(object.getString("idKlinikDokter"));
                        dataKlinik.setDokterId(object.getString("dokterId"));
                        dataKlinik.setHariPraktek(object.getString("hariPraktek"));
                        dataKlinik.setJamAwal(object.getString("jamAwal"));
                        dataKlinik.setJamAkhir(object.getString("jamAkhir"));
                        dataKlinik.setKlinikId(object.getString("klinikId"));
                        kuotapasien = Integer.parseInt(object.getString("kuotaPasien"));
                        dataKlinik.setKuotaPasien(object.getString("kuotaPasien"));
                        kuotaperjam = Integer.parseInt(object.getString("kuotaPerjam"));
                        dataKlinik.setKuotaPerjam(object.getString("kuotaPerjam"));
                        dataKlinik.setWaktuJadwal(object.getString("waktuJadwal"));
                        dataKlinik.setKeteranganJadwal(object.getString("keteranganJadwal"));
                        dataKlinik.setRuangTetap(object.getString("ruangTetap"));
                        dataKlinik.setStatusPasien(object.getString("statusPasien"));
                        tracer = object.getString("tracerTetap");
                        dataKlinik.setTracerTetap(object.getString("tracerTetap"));

                        itemDataKlinik.add(dataKlinik);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private void nomorAntrianDipakai(String idKlinikDokter, String tanggal){
        //nomorAntrianDipakai.clear();
        nomorDipakai.clear();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAntrianDipakai + idKlinikDokter + "/" + tanggal, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        nomorDipakai.add(jsonObject.getInt("noAntrian"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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

                        klinikid = JSONobj.getString("klinik_id");
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
                        /*dataJadwalPoli.setKuotaPasien(jsonObject.getString("kuotaPasien"));
                        dataJadwalPoli.setKuotaPerJam(jsonObject.getString("kuotaPerJam"))*/;
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
        /*itemJamPraktek.clear();*/
        //adapterSpinnerJamPraktek.notifyDataSetChanged();
        arJamPraktek.subList(1, arJamPraktek.size()).clear();
        adapterJamPraktek.notifyDataSetChanged();
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

                        arJamPraktek.add(JSONobj.getString("jamAwal") + "-" + JSONobj.getString("jamAkhir"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //adapterSpinnerJamPraktek.notifyDataSetChanged();
                adapterJamPraktek.notifyDataSetChanged();
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

        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void jamPraktekTanggalDokter(String tanggal, String idDokter){
        /*itemJamPraktek.clear();*/
        //adapterSpinnerJamPraktek.notifyDataSetChanged();
        arJamPraktek.subList(1, arJamPraktek.size()).clear();
        adapterJamPraktek.notifyDataSetChanged();
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

                        arJamPraktek.add(JSONobj.getString("jamMulaiPraktek") + "-" + JSONobj.getString("jamSelesaiPraktek"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //adapterSpinnerJamPraktek.notifyDataSetChanged();
                adapterJamPraktek.notifyDataSetChanged();
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
                spinnerJamPraktek.setAdapter(adapterJamPraktek);

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

    public void daftarPeriksaKunjungan(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Mendaftar...","Mohon Tunggu...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPendaftaranKunjungan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {

                String idPasien = sharedPreferences.getString("idPasien", null);

                Map<String,String> params = new Hashtable<String, String>();
                params.put("keterangan", "");
                params.put("klinikDokterId", idKlinikDokter);
                if (!statusPasien.equals("Fisioterapi")){
                    params.put("statusTerakhirPasien", "Pendaftaran Kunjungan Poliklinik");
                } else {
                    params.put("statusTerakhirPasien", "Pendaftaran Kunjungan Fisioterapi");
                }
                params.put("noAntrian", noAntrian);
                params.put("jam", "1??");
                params.put("caraMasuk", "Sendiri");
                params.put("tanggalPeriksa", tgl);
                params.put("tracer", tracer);
                params.put("caraBayar", jaminan);
                assert idPasien != null;
                params.put("pasienId", idPasien);
                params.put("tanggalPendaftaran", tglDaftar);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);

    }



    public void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle("Konfirmasi pendaftaran periksa?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getActivity()).setTitle("Daftar Periksa");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    interface OnFragmentInteractionListener {
    }
}
