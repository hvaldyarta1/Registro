package app.rsprmobile.registro;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import app.rsprmobile.registro.adapter.AdapterButtonJam1;
import app.rsprmobile.registro.adapter.AdapterButtonJam10;
import app.rsprmobile.registro.adapter.AdapterButtonJam2;
import app.rsprmobile.registro.adapter.AdapterButtonJam3;
import app.rsprmobile.registro.adapter.AdapterButtonJam4;
import app.rsprmobile.registro.adapter.AdapterButtonJam5;
import app.rsprmobile.registro.adapter.AdapterButtonJam6;
import app.rsprmobile.registro.adapter.AdapterButtonJam7;
import app.rsprmobile.registro.adapter.AdapterButtonJam8;
import app.rsprmobile.registro.adapter.AdapterButtonJam9;
import app.rsprmobile.registro.adapter.AdapterJadwal;
import app.rsprmobile.registro.adapter.AdapterJadwalPoli;
import app.rsprmobile.registro.adapter.AdapterSinnerSemuaDokter;
import app.rsprmobile.registro.adapter.AdapterSpinnerDokterPoli;
import app.rsprmobile.registro.adapter.AdapterSpinnerPoli;
import app.rsprmobile.registro.app.AppController;
import app.rsprmobile.registro.data.DataDokter;
import app.rsprmobile.registro.data.DataDokterPoli;
import app.rsprmobile.registro.data.DataJadwal;
import app.rsprmobile.registro.data.DataJadwalPoli;
import app.rsprmobile.registro.data.DataKlinik;
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
    //AdapterSpinnerJamPraktek adapterSpinnerJamPraktek;
    ArrayAdapter<String> adapterJamPraktek;
    List<DataDokter> semuaDokter = new ArrayList<DataDokter>();
    List<DataDokterPoli> dokterPoli = new ArrayList<DataDokterPoli>();
    List<DataPoli> poli = new ArrayList<DataPoli>();
    List<DataJadwal> jadwalSemuaDokter = new ArrayList<DataJadwal>();
    List<DataJadwalPoli> jadwalDokterPoli = new ArrayList<DataJadwalPoli>();
    List<DataKlinik> itemDataKlinik = new ArrayList<DataKlinik>();
    //List<DataNomorAntrianDipakai> nomorAntrianDipakai = new ArrayList<>();

    ArrayList<String> arJamPraktek = new ArrayList<String>(); //rev Spinner item
    //ArrayList<Integer> arrayKuota = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> nomorDipakai = new ArrayList<Integer>(); //noAntrian Dipakai

    public final String urlJamPraktek = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganidKlinikidDokteridTanggal/";
    public final String urlJamPraktekDokter = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganTanggalDokter/";
    public final String urlKlinik = "http://192.168.11.213:8080/jadwaldokter-v04-0.0.1/Jadwal/JadwalDokterDenganIdKlinikIdDokterIdTanggalWaktuAwal/";
    public final String urlAntrianDipakai = "http://192.168.11.211:8080/PendaftaranV3/PendaftaranV3/getAntrianByKlinikDokterDanTanggalPeriksa/";

    ListView listJadwalDokter, listRange;

    TextView textNamaDokter, txtKuotaMax, txtDokterPraktek;

    Bundle bundle;

    SharedPreferences sharedPreferences;
    String jaminan, iddokter, klinikid, tgl, idPasien,
            idKlinikDokter, namaKlinik, statusPasien, tracer, tglDaftar, dokter;

    public String noAntrian;

    RecyclerView rvButtonNomor;

    private int i, kuotapasien, kuotaperjam;
    TextView textViewKuotaPerjam;
    int txtKuotaPerjam;

    // Modifikasi
    LinearLayout holder1, holder2, holder3, holder4, holder5,
                 holder6, holder7, holder8, holder9, holder10;

    RecyclerView rvBtnAntri1, rvBtnAntri2, rvBtnAntri3, rvBtnAntri4, rvBtnAntri5,
                 rvBtnAntri6, rvBtnAntri7, rvBtnAntri8, rvBtnAntri9, rvBtnAntri10;

    TextView txtRange1, txtRange2, txtRange3, txtRange4, txtRange5,
             txtRange6, txtRange7, txtRange8, txtRange9, txtRange10;

    AdapterButtonJam1 adapterButtonJam1;
    AdapterButtonJam2 adapterButtonJam2;
    AdapterButtonJam3 adapterButtonJam3;
    AdapterButtonJam4 adapterButtonJam4;
    AdapterButtonJam5 adapterButtonJam5;
    AdapterButtonJam6 adapterButtonJam6;
    AdapterButtonJam7 adapterButtonJam7;
    AdapterButtonJam8 adapterButtonJam8;
    AdapterButtonJam9 adapterButtonJam9;
    AdapterButtonJam10 adapterButtonJam10;

    ArrayList<Integer> arrayKuota1 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota2 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota3 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota4 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota5 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota6 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota7 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota8 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota9 = new ArrayList<Integer>(); //array Kuota Pasien
    ArrayList<Integer> arrayKuota10 = new ArrayList<Integer>(); //array Kuota Pasien
    // *********

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

        // Modifikasi
        holder1 = viewPendaftaran.findViewById(R.id.layoutHolder1);
        holder2 = viewPendaftaran.findViewById(R.id.layoutHolder2);
        holder3 = viewPendaftaran.findViewById(R.id.layoutHolder3);
        holder4 = viewPendaftaran.findViewById(R.id.layoutHolder4);
        holder5 = viewPendaftaran.findViewById(R.id.layoutHolder5);
        holder6 = viewPendaftaran.findViewById(R.id.layoutHolder6);
        holder7 = viewPendaftaran.findViewById(R.id.layoutHolder7);
        holder8 = viewPendaftaran.findViewById(R.id.layoutHolder8);
        holder9 = viewPendaftaran.findViewById(R.id.layoutHolder9);
        holder10 = viewPendaftaran.findViewById(R.id.layoutHolder10);

        rvBtnAntri1 = viewPendaftaran.findViewById(R.id.rvButtonAntri1);
        rvBtnAntri2 = viewPendaftaran.findViewById(R.id.rvButtonAntri2);
        rvBtnAntri3 = viewPendaftaran.findViewById(R.id.rvButtonAntri3);
        rvBtnAntri4 = viewPendaftaran.findViewById(R.id.rvButtonAntri4);
        rvBtnAntri5 = viewPendaftaran.findViewById(R.id.rvButtonAntri5);
        rvBtnAntri6 = viewPendaftaran.findViewById(R.id.rvButtonAntri6);
        rvBtnAntri7 = viewPendaftaran.findViewById(R.id.rvButtonAntri7);
        rvBtnAntri8 = viewPendaftaran.findViewById(R.id.rvButtonAntri8);
        rvBtnAntri9 = viewPendaftaran.findViewById(R.id.rvButtonAntri9);
        rvBtnAntri10 = viewPendaftaran.findViewById(R.id.rvButtonAntri10);

        txtRange1 = viewPendaftaran.findViewById(R.id.txtRange1);
        txtRange2 = viewPendaftaran.findViewById(R.id.txtRange2);
        txtRange3 = viewPendaftaran.findViewById(R.id.txtRange3);
        txtRange4 = viewPendaftaran.findViewById(R.id.txtRange4);
        txtRange5 = viewPendaftaran.findViewById(R.id.txtRange5);
        txtRange6 = viewPendaftaran.findViewById(R.id.txtRange6);
        txtRange7 = viewPendaftaran.findViewById(R.id.txtRange7);
        txtRange8 = viewPendaftaran.findViewById(R.id.txtRange8);
        txtRange9 = viewPendaftaran.findViewById(R.id.txtRange9);
        txtRange10 = viewPendaftaran.findViewById(R.id.txtRange10);

        // *********


        // ===== Proses Modifikasi
        /*rvButtonNomor = (RecyclerView) viewPendaftaran.findViewById(R.id.rvButton);
        rvButtonNomor.setLayoutManager(new LinearLayoutManager(getActivity()));

        listRange = (ListView) viewPendaftaran.findViewById(R.id.listRange);*/
        // ===== Proses Modifikasi

        txtDokterPraktek = viewPendaftaran.findViewById(R.id.txtDokterPraktek);
        txtDokterPraktek.setVisibility(View.GONE);
        txtKuotaMax = viewPendaftaran.findViewById(R.id.txtKuotaMax);
        txtKuotaMax.setVisibility(View.GONE);
        textViewKuotaPerjam = (TextView) viewPendaftaran.findViewById(R.id.textViewKuotaPerjam);
        textViewKuotaPerjam.setVisibility(View.GONE);

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
        spinnerJamPraktek.setVisibility(View.GONE);

        arJamPraktek.add("-- Pilih Jam Praktek --");
        adapterJamPraktek = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                arJamPraktek);


        //textNamaDokter = (TextView) viewPendaftaran.findViewById(R.id.txtViewDokterDipilih);

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
        idPasien = sharedPreferences.getString("idPasien", null);

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
                //statusPasien = poli.get(position).getNamaKlinik();

                if (spinnerPoli.getSelectedItem() == "--Pilih Klinik--"){
                    spinnerDokter.setVisibility(View.GONE);
                    jadwalSemuaDokter.clear();
                } else {
                    klinikid = poli.get(position).getIdKlinik();
                    loadDokterPoli(klinikid);
                    //Toast.makeText(getActivity(), klinikid, Toast.LENGTH_SHORT).show();
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
                    statusPasien = dokterPoli.get(position).getNamaDokterTetap();
                    dokter = dokterPoli.get(position).getNamaDokterTetap();
                    iddokter = dokterPoli.get(position).getIdDokterTetap();
                    //Toast.makeText(getActivity(), iddokter, Toast.LENGTH_SHORT).show();
                    //textNamaDokter.setText(dokter);
                    dataJadwalDokterPoli(iddokter, klinikid);
                    listJadwalDokter.setVisibility(View.VISIBLE);
                    listJadwalDokter.setAdapter(adapterJadwalPoli);
                    btnTanggal.setEnabled(true);

                } else if (spinnerDokter.getAdapter() == adapterSinnerSemuaDokter){ //Semua Dokter
                    dokter = semuaDokter.get(position).getNamaDokter();
                    iddokter = semuaDokter.get(position).getDokterId();
                    statusPasien = semuaDokter.get(position).getNamaDokter();
                    //textNamaDokter.setText(dokter);
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
                    //Toast.makeText(getContext(), jam, Toast.LENGTH_SHORT).show();

                    String splitJam[] = jam.split("-");
                    String jam1 = splitJam[0];
                    String jam2 = splitJam[1];

                    dataKlinik(tgl, iddokter, klinikid, jam1);

                    // Range
                    String[] jamawal = jam1.split(":");
                    String jamawal1 = jamawal[0];
                    String jamawal2 = jamawal[1];


                    String[] jamakhir = jam2.split(":");
                    String jamselesai1 = jamakhir[0]; String nmrAkhir = String.valueOf(kuotapasien);
                    String jamselesai2 = jamakhir[1];

                    int jamp1 = Integer.parseInt(jamawal1);
                    int jamp3 = (Integer.parseInt(jamselesai1) - jamp1);

                    int jamrange;

                    String jamranges1;
                    String jamranges2;

                    /*String nA = "1";
                    int aW = Integer.parseInt(nA);
                    int noRange;

                    String nRange1;
                    String noRange2;*/

                    ArrayList<String> arrayrange = new ArrayList<String>();
                    for (int lit = 1; lit <= jamp3; lit++) {
                        jamrange = jamp1 + 1;
                        //noRange = aW + kuotaperjam - 1;

                        if (jamp1 > 24) {
                            jamp1 = jamp1 - 24;
                        } else if (jamp1 == 24) {
                            jamp1 = 00;
                        }

                        //nRange1 = String.valueOf(aW);
                        jamranges1 = String.valueOf(jamp1);
                        if ("0".equals(jamranges1)) {
                            jamranges1 = "00";
                        }

                        if (jamrange > 24) {
                            jamrange = jamrange - 24;
                        } else if (jamrange == 24) {
                            jamrange = 00;
                        }

                        //noRange2 = String.valueOf(noRange);
                        jamranges2 = String.valueOf(jamrange);
                        if ("0".equals(jamranges2)) {
                            jamranges2 = "00";
                        }

                        String range1 = (jamranges1 + ":" + jamawal2);
                        String range2 = (jamranges2 + ":" + jamselesai2);

                        //aW+=kuotaperjam;
                        jamp1++;

                        /*if (noRange2.equals(nRange1)){*/
                            arrayrange.add(/* "no." + nRange1 + " ("+ */range1 + " - " + range2/*+")"*/);
                        /*} else {
                            arrayrange.add( "no." + nRange1 + "-" + noRange2 + " ("+ range1 + " - " + range2+")");
                        }*/

                    }

                    arrayKuota1.clear();
                    arrayKuota2.clear();
                    arrayKuota3.clear();
                    arrayKuota4.clear();
                    arrayKuota5.clear();
                    arrayKuota6.clear();
                    arrayKuota7.clear();
                    arrayKuota8.clear();
                    arrayKuota9.clear();
                    arrayKuota10.clear();

                    holder1.setVisibility(View.GONE);
                    holder2.setVisibility(View.GONE);
                    holder3.setVisibility(View.GONE);
                    holder4.setVisibility(View.GONE);
                    holder5.setVisibility(View.GONE);
                    holder6.setVisibility(View.GONE);
                    holder7.setVisibility(View.GONE);
                    holder8.setVisibility(View.GONE);
                    holder9.setVisibility(View.GONE);
                    holder10.setVisibility(View.GONE);

                    if (arrayrange.size() == 1){
                        txtRange1.setText(arrayrange.get(0));
                    } else if (arrayrange.size() == 2){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                    } else if (arrayrange.size() == 3){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                    } else if (arrayrange.size() == 4){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                        txtRange4.setText(arrayrange.get(3));
                    } else if (arrayrange.size() == 5){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                        txtRange4.setText(arrayrange.get(3));
                        txtRange5.setText(arrayrange.get(4));
                    } else if (arrayrange.size() == 6){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                        txtRange4.setText(arrayrange.get(3));
                        txtRange5.setText(arrayrange.get(4));
                        txtRange6.setText(arrayrange.get(5));
                    } else if (arrayrange.size() == 7){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                        txtRange4.setText(arrayrange.get(3));
                        txtRange5.setText(arrayrange.get(4));
                        txtRange6.setText(arrayrange.get(5));
                        txtRange7.setText(arrayrange.get(6));
                    } else if (arrayrange.size() == 8){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                        txtRange4.setText(arrayrange.get(3));
                        txtRange5.setText(arrayrange.get(4));
                        txtRange6.setText(arrayrange.get(5));
                        txtRange7.setText(arrayrange.get(6));
                        txtRange8.setText(arrayrange.get(7));
                    } else if (arrayrange.size() == 9){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                        txtRange4.setText(arrayrange.get(3));
                        txtRange5.setText(arrayrange.get(4));
                        txtRange6.setText(arrayrange.get(5));
                        txtRange7.setText(arrayrange.get(6));
                        txtRange8.setText(arrayrange.get(7));
                        txtRange9.setText(arrayrange.get(8));
                    } else if (arrayrange.size() == 10){
                        txtRange1.setText(arrayrange.get(0));
                        txtRange2.setText(arrayrange.get(1));
                        txtRange3.setText(arrayrange.get(2));
                        txtRange4.setText(arrayrange.get(3));
                        txtRange5.setText(arrayrange.get(4));
                        txtRange6.setText(arrayrange.get(5));
                        txtRange7.setText(arrayrange.get(6));
                        txtRange8.setText(arrayrange.get(7));
                        txtRange9.setText(arrayrange.get(8));
                        txtRange1.setText(arrayrange.get(10));
                    }

                    //Toast.makeText(getActivity(), idKlinikDokter, Toast.LENGTH_SHORT).show();
                    txtDokterPraktek.setVisibility(View.VISIBLE);
                    txtDokterPraktek.setText(dokter);

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

    private void dataKlinik(String tanggalJadwal, String idDokter, String idKlinik, String waktuAwal){
        itemDataKlinik.clear();
        //arrayKuota.clear();
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
                        dataKlinik.setKuotaPasien(object.getInt("kuotaPasien"));
                        kuotaperjam = Integer.parseInt(object.getString("kuotaPerjam"));
                        dataKlinik.setKuotaPerjam(object.getInt("kuotaPerjam"));
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
                }

                txtKuotaMax.setVisibility(View.VISIBLE);
                txtKuotaMax.setText("Kuota Pasien: " + String.valueOf(kuotapasien));

                dataNomorDipakai(/*"58", "2018-12-05"*/idKlinikDokter, tgl);

                adapterButtonJam1 = new AdapterButtonJam1(getActivity(), arrayKuota1, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri1.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam2 = new AdapterButtonJam2(getActivity(), arrayKuota2, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri2.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam3 = new AdapterButtonJam3(getActivity(), arrayKuota3, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri3.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam4 = new AdapterButtonJam4(getActivity(), arrayKuota4, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri4.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam5 = new AdapterButtonJam5(getActivity(), arrayKuota5, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri5.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam6 = new AdapterButtonJam6(getActivity(), arrayKuota6, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri6.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam7 = new AdapterButtonJam7(getActivity(), arrayKuota7, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri7.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam8 = new AdapterButtonJam8(getActivity(), arrayKuota8, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri8.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam9 = new AdapterButtonJam9(getActivity(), arrayKuota9, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri9.setLayoutManager(new GridLayoutManager(getActivity(),5));

                adapterButtonJam10 = new AdapterButtonJam10(getActivity(), arrayKuota10, kuotapasien, kuotaperjam, idKlinikDokter,
                        idPasien, jaminan, tracer, tgl, statusPasien);
                rvBtnAntri10.setLayoutManager(new GridLayoutManager(getActivity(),5));

                String txtKuotaPerjam = String.valueOf(kuotaperjam);
                textViewKuotaPerjam.setVisibility(View.VISIBLE);
                textViewKuotaPerjam.setText("Kuota pasien perjam: " + txtKuotaPerjam);

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

    private void dataNomorDipakai(String idKlinikDokter, String tanggal){
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

                int sesi = kuotapasien / kuotaperjam;
                if (sesi==1){
                    holder1.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }
                } else if (sesi==2){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                    }
                } else if (sesi==3){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                    }

                    for (int b = kuotaperjam* 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                    }
                } else if (sesi==4){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);
                    holder4.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                        arrayKuota2.removeAll(nomorDipakai);
                    }

                    for (int b = kuotaperjam * 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                        arrayKuota3.removeAll(nomorDipakai);
                    }

                    for (int c = kuotaperjam * 3 + 1; c <= kuotaperjam * 4; c++){
                        arrayKuota4.add(c);
                        arrayKuota4.removeAll(nomorDipakai);
                    }
                } else if (sesi==5){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);
                    holder4.setVisibility(View.VISIBLE);
                    holder5.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                        arrayKuota2.removeAll(nomorDipakai);
                    }

                    for (int b = kuotaperjam * 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                        arrayKuota3.removeAll(nomorDipakai);
                    }

                    for (int c = kuotaperjam * 3 + 1; c <= kuotaperjam * 4; c++){
                        arrayKuota4.add(c);
                        arrayKuota4.removeAll(nomorDipakai);
                    }

                    for (int d = kuotaperjam * 4 + 1; d <= kuotaperjam * 5; d++){
                        arrayKuota5.add(d);
                        arrayKuota5.removeAll(nomorDipakai);
                    }
                } else if (sesi==6){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);
                    holder4.setVisibility(View.VISIBLE);
                    holder5.setVisibility(View.VISIBLE);
                    holder6.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                        arrayKuota2.removeAll(nomorDipakai);
                    }

                    for (int b = kuotaperjam * 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                        arrayKuota3.removeAll(nomorDipakai);
                    }

                    for (int c = kuotaperjam * 3 + 1; c <= kuotaperjam * 4; c++){
                        arrayKuota4.add(c);
                        arrayKuota4.removeAll(nomorDipakai);
                    }

                    for (int d = kuotaperjam * 4 + 1; d <= kuotaperjam * 5; d++){
                        arrayKuota5.add(d);
                        arrayKuota5.removeAll(nomorDipakai);
                    }

                    for (int e = kuotaperjam * 5 + 1; e <= kuotaperjam * 6; e++ ){
                        arrayKuota6.add(e);
                        arrayKuota6.removeAll(nomorDipakai);
                    }
                } else if (sesi==7){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);
                    holder4.setVisibility(View.VISIBLE);
                    holder5.setVisibility(View.VISIBLE);
                    holder6.setVisibility(View.VISIBLE);
                    holder7.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                        arrayKuota2.removeAll(nomorDipakai);
                    }

                    for (int b = kuotaperjam * 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                        arrayKuota3.removeAll(nomorDipakai);
                    }

                    for (int c = kuotaperjam * 3 + 1; c <= kuotaperjam * 4; c++){
                        arrayKuota4.add(c);
                        arrayKuota4.removeAll(nomorDipakai);
                    }

                    for (int d = kuotaperjam * 4 + 1; d <= kuotaperjam * 5; d++){
                        arrayKuota5.add(d);
                        arrayKuota5.removeAll(nomorDipakai);
                    }

                    for (int e = kuotaperjam * 5 + 1; e <= kuotaperjam * 6; e++ ){
                        arrayKuota6.add(e);
                        arrayKuota6.removeAll(nomorDipakai);
                    }

                    for (int f = kuotaperjam * 6 + 1; f <= kuotaperjam * 7; f++){
                        arrayKuota7.add(f);
                        arrayKuota7.removeAll(nomorDipakai);
                    }
                } else if (sesi==8){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);
                    holder4.setVisibility(View.VISIBLE);
                    holder5.setVisibility(View.VISIBLE);
                    holder6.setVisibility(View.VISIBLE);
                    holder7.setVisibility(View.VISIBLE);
                    holder8.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                        arrayKuota2.removeAll(nomorDipakai);
                    }

                    for (int b = kuotaperjam * 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                        arrayKuota3.removeAll(nomorDipakai);
                    }

                    for (int c = kuotaperjam * 3 + 1; c <= kuotaperjam * 4; c++){
                        arrayKuota4.add(c);
                        arrayKuota4.removeAll(nomorDipakai);
                    }

                    for (int d = kuotaperjam * 4 + 1; d <= kuotaperjam * 5; d++){
                        arrayKuota5.add(d);
                        arrayKuota5.removeAll(nomorDipakai);
                    }

                    for (int e = kuotaperjam * 5 + 1; e <= kuotaperjam * 6; e++ ){
                        arrayKuota6.add(e);
                        arrayKuota6.removeAll(nomorDipakai);
                    }

                    for (int f = kuotaperjam * 6 + 1; f <= kuotaperjam * 7; f++){
                        arrayKuota7.add(f);
                        arrayKuota7.removeAll(nomorDipakai);
                    }

                    for (int g = kuotaperjam * 7 + 1; g <= kuotaperjam *8; g++){
                        arrayKuota8.add(g);
                        arrayKuota8.removeAll(nomorDipakai);
                    }
                } else if (sesi==9){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);
                    holder4.setVisibility(View.VISIBLE);
                    holder5.setVisibility(View.VISIBLE);
                    holder6.setVisibility(View.VISIBLE);
                    holder7.setVisibility(View.VISIBLE);
                    holder8.setVisibility(View.VISIBLE);
                    holder9.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                        arrayKuota2.removeAll(nomorDipakai);
                    }

                    for (int b = kuotaperjam * 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                        arrayKuota3.removeAll(nomorDipakai);
                    }

                    for (int c = kuotaperjam * 3 + 1; c <= kuotaperjam * 4; c++){
                        arrayKuota4.add(c);
                        arrayKuota4.removeAll(nomorDipakai);
                    }

                    for (int d = kuotaperjam * 4 + 1; d <= kuotaperjam * 5; d++){
                        arrayKuota5.add(d);
                        arrayKuota5.removeAll(nomorDipakai);
                    }

                    for (int e = kuotaperjam * 5 + 1; e <= kuotaperjam * 6; e++ ){
                        arrayKuota6.add(e);
                        arrayKuota6.removeAll(nomorDipakai);
                    }

                    for (int f = kuotaperjam * 6 + 1; f <= kuotaperjam * 7; f++){
                        arrayKuota7.add(f);
                        arrayKuota7.removeAll(nomorDipakai);
                    }

                    for (int g = kuotaperjam * 7 + 1; g <= kuotaperjam * 8; g++){
                        arrayKuota8.add(g);
                        arrayKuota8.removeAll(nomorDipakai);
                    }

                    for (int h = kuotaperjam * 8 + 1; h <= kuotaperjam * 9; h++){
                        arrayKuota9.add(h);
                        arrayKuota9.removeAll(nomorDipakai);
                    }
                } else if (sesi==10){
                    holder1.setVisibility(View.VISIBLE);
                    holder2.setVisibility(View.VISIBLE);
                    holder3.setVisibility(View.VISIBLE);
                    holder4.setVisibility(View.VISIBLE);
                    holder5.setVisibility(View.VISIBLE);
                    holder6.setVisibility(View.VISIBLE);
                    holder7.setVisibility(View.VISIBLE);
                    holder8.setVisibility(View.VISIBLE);
                    holder9.setVisibility(View.VISIBLE);
                    holder10.setVisibility(View.VISIBLE);

                    for (i = 1; i <= kuotaperjam; i++){
                        arrayKuota1.add(i);
                        arrayKuota1.removeAll(nomorDipakai);
                    }

                    for (int a = kuotaperjam+1; a <= kuotaperjam * 2; a++){
                        arrayKuota2.add(a);
                        arrayKuota2.removeAll(nomorDipakai);
                    }

                    for (int b = kuotaperjam * 2 + 1; b <= kuotaperjam * 3; b++){
                        arrayKuota3.add(b);
                        arrayKuota3.removeAll(nomorDipakai);
                    }

                    for (int c = kuotaperjam * 3 + 1; c <= kuotaperjam * 4; c++){
                        arrayKuota4.add(c);
                        arrayKuota4.removeAll(nomorDipakai);
                    }

                    for (int d = kuotaperjam * 4 + 1; d <= kuotaperjam * 5; d++){
                        arrayKuota5.add(d);
                        arrayKuota5.removeAll(nomorDipakai);
                    }

                    for (int e = kuotaperjam * 5 + 1; e <= kuotaperjam * 6; e++ ){
                        arrayKuota6.add(e);
                        arrayKuota6.removeAll(nomorDipakai);
                    }

                    for (int f = kuotaperjam * 6 + 1; f <= kuotaperjam * 7; f++){
                        arrayKuota7.add(f);
                        arrayKuota7.removeAll(nomorDipakai);
                    }

                    for (int g = kuotaperjam * 7 + 1; g <= kuotaperjam * 8; g++){
                        arrayKuota8.add(g);
                        arrayKuota8.removeAll(nomorDipakai);
                    }

                    for (int h = kuotaperjam * 8 + 1; h <= kuotaperjam * 9; h++){
                        arrayKuota9.add(h);
                        arrayKuota9.removeAll(nomorDipakai);
                    }

                    for (int j = kuotaperjam * 9 + 1; j <= kuotaperjam *10; j++){
                        arrayKuota10.add(i);
                        arrayKuota10.removeAll(nomorDipakai);
                    }
                }

                adapterButtonJam1.notifyDataSetChanged();
                adapterButtonJam2.notifyDataSetChanged();
                adapterButtonJam3.notifyDataSetChanged();
                adapterButtonJam4.notifyDataSetChanged();
                adapterButtonJam5.notifyDataSetChanged();
                adapterButtonJam6.notifyDataSetChanged();
                adapterButtonJam7.notifyDataSetChanged();
                adapterButtonJam8.notifyDataSetChanged();
                adapterButtonJam9.notifyDataSetChanged();
                adapterButtonJam9.notifyDataSetChanged();
                adapterButtonJam10.notifyDataSetChanged();

                rvBtnAntri1.setAdapter(adapterButtonJam1);
                rvBtnAntri2.setAdapter(adapterButtonJam2);
                rvBtnAntri3.setAdapter(adapterButtonJam3);
                rvBtnAntri4.setAdapter(adapterButtonJam4);
                rvBtnAntri5.setAdapter(adapterButtonJam5);
                rvBtnAntri6.setAdapter(adapterButtonJam6);
                rvBtnAntri7.setAdapter(adapterButtonJam7);
                rvBtnAntri8.setAdapter(adapterButtonJam8);
                rvBtnAntri9.setAdapter(adapterButtonJam9);
                rvBtnAntri10.setAdapter(adapterButtonJam10);
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

                        if (JSONobj.has("jamAwal")){
                            arJamPraktek.add(JSONobj.getString("jamAwal") + "-" + JSONobj.getString("jamAkhir"));
                        } else if (JSONobj.has("errorCode")) {
                            arJamPraktek.clear();
                            arJamPraktek.add("Tidak Praktek");
                        }


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

                        if (JSONobj.has("jamMulaiPraktek")){
                            arJamPraktek.add(JSONobj.getString("jamMulaiPraktek") + "-" + JSONobj.getString("jamSelesaiPraktek"));
                        } else if (JSONobj.has("errorCode")){
                            arJamPraktek.clear();
                            arJamPraktek.add("Tidak Praktek");
                        }


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
