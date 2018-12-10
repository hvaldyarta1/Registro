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
import android.support.v4.app.FragmentManager;
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
import android.widget.ImageView;
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
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import app.rsprmobile.registro.adapter.AdapterDataKlinik;
import app.rsprmobile.registro.adapter.AdapterGridDokter;
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
public class Pendaftaran extends Fragment {

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
    AdapterDataKlinik adapterDataKlinik;
    List<DataDokter> semuaDokter = new ArrayList<DataDokter>();
    List<DataDokterPoli> dokterPoli = new ArrayList<DataDokterPoli>();
    List<DataPoli> poli = new ArrayList<DataPoli>();
    List<DataJadwal> jadwalSemuaDokter = new ArrayList<DataJadwal>();
    List<DataJadwalPoli> jadwalDokterPoli = new ArrayList<DataJadwalPoli>();
    List<DataJamPraktek> itemJamPraktek = new ArrayList<DataJamPraktek>();
    List<DataKlinik> itemDataKlinik = new ArrayList<DataKlinik>();
    List<DataNomorAntrianDipakai> nomorAntrianDipakai = new ArrayList<>();

    public final String urlJamPraktek = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganidKlinikidDokteridTanggal/";
    public final String urlJamPraktekDokter = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganTanggalDokter/";
    public final String urlKlinik = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganIdKlinikIdDokterIdTanggalWaktuAwal/";
    public final String urlAntrianDipakai = "http://192.168.11.211:8080/PendaftaranV3/PendaftaranV3/getAntrianByKlinikDokterDanTanggalPeriksa/";
    public final String urlPendaftaranKunjungan = "http://192.168.29.7:8888/PendaftaranV3/tambahKunjunganBaru";

    ListView listJadwalDokter, listButton;

    TextView textNamaDokter;

    Bundle bundle;

    SharedPreferences sharedPreferences;
    String jaminan, iddokter, klinikid, tgl, jamAwal
            , idKlinikDokter, namaKlinik, noAntrian, statusPasien, tracer, tglDaftar;

    RecyclerView rvButtonNomor;

    private static final int[] idArray = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15,
            R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24, R.id.btn25};
    private Button[] button = new Button[idArray.length];

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

        /*listButton = (ListView) viewPendaftaran.findViewById(R.id.listButton);
        adapterDataKlinik = new AdapterDataKlinik(getActivity(), itemDataKlinik);*/

        /*rvButtonNomor = (RecyclerView) viewPendaftaran.findViewById(R.id.rvButton);
        rvButtonNomor.setLayoutManager(new GridLayoutManager(getContext(), 5));*/

        /*mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);*/

        textViewKuotaPerjam = (TextView) viewPendaftaran.findViewById(R.id.textViewKuotaPerjam);

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
                statusPasien = poli.get(position).getNamaKlinik();

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

                TextView bpjs, nonBpjs;
                ImageView warnaBpjs, warnaUmum;

                bpjs = (TextView) viewPendaftaran.findViewById(R.id.teksJam);
                nonBpjs = (TextView) viewPendaftaran.findViewById(R.id.teksJam1);
                warnaBpjs = (ImageView) viewPendaftaran.findViewById(R.id.imageWarnaBPJS);
                warnaUmum = (ImageView) viewPendaftaran.findViewById(R.id.imageWarnaUMUM);

                bpjs.setVisibility(View.VISIBLE);
                nonBpjs.setVisibility(View.VISIBLE);
                warnaBpjs.setVisibility(View.VISIBLE);
                warnaUmum.setVisibility(View.VISIBLE);

                DataJamPraktek dataJamPraktek = itemJamPraktek.get(position);
                String jam = dataJamPraktek.getJamAwal();
                String splitJam[] = jam.split("-");
                String jam1 = splitJam[0];
                String jam2 = splitJam[1];

                //range

                /*String jamAwal[] = jam1.split(":");
                String jamAkhir[] = jam2.split(":");
                int jamAwal1 = Integer.parseInt(jamAwal[0]);
                int jamAkhir1 = Integer.parseInt(jamAkhir[0]);

                int jamrange;
                String jamranges1;
                String jamranges2;
                ArrayList<String> arj = new ArrayList<String>();
                for (int lit = 1; lit <= jamAkhir1; lit ++){
                    jamrange = jamAwal1 + 1;
                    if (jamAwal1 > 24) {
                        jamAwal1 = jamAwal1 - 24;
                    } else if (jamAwal1 == 24) {
                        jamAwal1 = 00;
                    }

                    jamranges1 = String.valueOf(jamAwal1);
                    if ("0".equals(jamranges1)){
                        jamranges1 = "00";
                    }

                    if (jamrange > 24) {
                        jamrange = jamrange - 24;
                    } else if (jamrange == 24) {
                        jamrange = 00;
                    }
                    jamranges2 = String.valueOf(jamrange);
                    if("0".equals(jamranges2)){
                        jamranges2 = "00";
                    }

                    String range1 = (jamranges1 + ":" + jampart2);
                    String range2 = (jamranges2 + ":" + jampart4);
                    String[] rd;
                    System.out.println(range1 + " - " + range2);
                    jamAwal1++;
                    arj.add(range1 + " - " + range2);

                }*/

                int pos = spinnerJamPraktek.getSelectedItemPosition();

                if (pos == 0){

                    /*jamAwal = itemJamPraktek.get(position).getJamAwal();*/
                    dataKlinik(tgl, iddokter, klinikid, jam1/*"2018-12-05","1", "27","09:00"*/);
                    nomorAntrianDipakai(idKlinikDokter, tgl/*"58", "2018-12-05"*/);

                    String txtKuotaPerjam = String.valueOf(kuotaperjam);
                    textViewKuotaPerjam.setText("Kuota pasien perjam: " + txtKuotaPerjam);
                } else {

                    /*jamAwal = itemJamPraktek.get(position).getJamAwal();*/
                    dataKlinik(tgl, iddokter, klinikid, jam1/*"2018-12-05","1", "27","09:00"*/);
                    nomorAntrianDipakai(idKlinikDokter, tgl/*"58", "2018-12-05"*/);

                    String txtKuotaPerjam = String.valueOf(kuotaperjam);
                    textViewKuotaPerjam.setText("Kuota pasien perjam: " + txtKuotaPerjam);
                }

                for (i = 0; i<kuotapasien; i++){
                    button[i] = (Button) viewPendaftaran.findViewById(idArray[i]);
                    button[i].setVisibility(View.VISIBLE);
                    button[i].setText("" + (i+1));

                    /*button[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));*/

                    /*if (button[i].getText().toString().equals()) {
                        button[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }*/
                    /*if (button[i].getText().toString().equals(nomorAntrianDipakai.toString())) {
                        button[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }*/

                    button[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()); //get tgl hari ini

                            tglDaftar = format.format(new Date());

                            switch (v.getId()){
                                case R.id.btn1:

                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn2:
                                    if (!jaminan.trim().equals("BPJS")) {
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn3:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn4:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn5:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn6:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn7:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn8:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn9:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn10:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn11:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn12:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn13:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn14:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn15:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn16:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn17:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn18:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn19:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn20:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn21:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn22:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn23:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn24:
                                    if (jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien non BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                                case R.id.btn25:
                                    if (!jaminan.trim().equals("BPJS")){
                                        Toast.makeText(getActivity(), "Hanya pasien BPJS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        createAndShowAlertDialog();
                                    }
                                    break;
                            }

                        }
                    });
                }
                /*listButton.setAdapter(adapterDataKlinik);*/

                /*adapterDataKlinik = new AdapterDataKlinik(getContext(), itemDataKlinik);
                rvButtonNomor.setAdapter(adapterDataKlinik);*/

                /*"2018-12-05/and/1/and/27/and/09:00"*/
                /*String namaklinik = itemDataKlinik.get(position).getNamaKlinik();

                Toast.makeText(getContext(), namaklinik, Toast.LENGTH_LONG).show();*/
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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat Nomor Urut...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlKlinik + tanggalJadwal + "/and/" + idDokter + "/and/"
                + idKlinik + "/and/" + waktuAwal/*"2018-12-05/and/1/and/27/and/09:00/"*/, new Response.Listener<JSONArray>() {
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
        nomorAntrianDipakai.clear();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAntrianDipakai + idKlinikDokter + "/" + tanggal, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DataNomorAntrianDipakai dataNomorAntrianDipakai = new DataNomorAntrianDipakai();

                        dataNomorAntrianDipakai.setNoAntrian(jsonObject.getString("noAntrian"));


                        nomorAntrianDipakai.add(dataNomorAntrianDipakai);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /*adapterDataKlinik.notifyDataSetChanged();*/
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

                        dataJamPraktek.setJamAwal(JSONobj.getString("jamAwal") + " - " + JSONobj.getString("jamAkhir"));
                        /*dataJamPraktek.setJamAkhir(JSONobj.getString("jamAkhir"));*/

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

    private void daftarPeriksaKunjungan(){
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
                /*String image = getStringImage(bitmap);
                String nama_jasa = editTextJasa.getText().toString().trim();
                String harga_jasa = editTextHarga.getText().toString().trim();
                String deskripsi_jasa = editTextDeskripsi.getText().toString().trim();
                String alamat = textDetail.getText().toString().trim();
                String latitude = txt_latitude.getText().toString().trim();
                String longitude = txt_longitude.getText().toString().trim();
                String kategori = txt_kategori.getText().toString().trim();
                String HPjasa = editTextWA.getText().toString().trim();

                \n" +
                "method : POST\n" +
                        "{\n" +
                        "\"\": \"\",\n" +
                        "\"\": 58,\n" +
                        "\"\": \"Pendaftaran Kunjungan Poliklinik\",\n" +
                        "\"\": 1,\n" +
                        "\"\": \"1\",\n" +
                        "\"\": \"Sendiri\",\n" +
                        "\"\": \"2018-11-28\",\n" +
                        "\"\": \"Tidak\",\n" +
                        "\"\": \"UMUM\",\n" +
                        "\"\": \"11\",\n" +
                        "\"\": \"2018-11-28\"\n" +
                        "}*/

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



    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    interface OnFragmentInteractionListener {
    }
}
