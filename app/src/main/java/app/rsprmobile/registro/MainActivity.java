package app.rsprmobile.registro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Poli.OnFragmentInteractionListener,
        Dokter.OnFragmentInteractionListener,
        Histori.OnFragmentInteractionListener,
        Pendaftaran.OnFragmentInteractionListener,
        PeringatanLogin.OnFragmentInteractionListener {

    SharedPreferences sharedPreferences;

    private Bundle bundleMain;

    NavigationView navigationView;

    public final static String tagIdPasien = "idPasien";
    public final static String tagNoIdentitas = "noIdentitas";
    public final static String tagJenisIdentitas = "jenisIdentitas";
    public final static String tagNama = "nama";
    public final static String tagKelamin = "kelamin";
    public final static String tagTanggalLahir = "tanggalLahir";
    public final static String tagTanggalDaftar = "tanggalDaftar";
    public final static String tagNoHP1 = "noHP1";
    public final static String tagNoTelp = "noTelp";
    public final static String tagNoBpjs = "noBpjs";
    public final static String tagPekerjaan = "pekerjaan";
    public final static String tagStatus = "status";
    public final static String tagAgama = "agama";
    public final static String tagNoRm = "noRm";
    public final static String tagStatusPasien = "statusPasien";
    public final static String tagStatusBpjs = "statusBpjs";
    public final static String tagStatusKematian = "statusKematian";
    public final static String tagNoKartu = "noKartu";
    public final static String tagUmur = "umur";
    public final static String tagGolDarah = "golDarah";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        timer.start();

        String noBpjs = getIntent().getStringExtra("noBpjs");
        String noRm = getIntent().getStringExtra("noRm");
        String tglLahir = getIntent().getStringExtra("tglLahir");
        String nama = getIntent().getStringExtra("nama");

        bundleMain = new Bundle();
        bundleMain.putString("noBpjs", noBpjs);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textNamaPasien);
        TextView navRM = (TextView) headerView.findViewById(R.id.textViewRM);

        if (sharedPreferences.getString("nama", null) != null){
            navUsername.setText(sharedPreferences.getString("nama", null));
            navRM.setText(sharedPreferences.getString("noRm", null));
        } else {
           navUsername.setText("Nama Pasien");
           navRM.setText("no. RM");
        }

        bundleMain = new Bundle();
        bundleMain.putString("noRm", noRm);
        bundleMain.putString("noBpjs", noBpjs);
        bundleMain.putString("tglLahir", tglLahir);
        bundleMain.putString("nama", nama);

        if (sharedPreferences.getString("noRm", null) != null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Pendaftaran pendaftaran = new Pendaftaran();
            fragmentTransaction.replace(R.id.fragment_container, pendaftaran)
                    .commit();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Dokter dokter = new Dokter();
            hideItem();
            fragmentTransaction.replace(R.id.fragment_container, dokter)
                    .commit();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_share).setVisible(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_daftar) {
            // Handle the camera action
            if (sharedPreferences.getString("noRm", null) != null){
                fragmentClass = Pendaftaran.class;
            } else {
                fragmentClass = PeringatanLogin.class;
            }

        } else if (id == R.id.nav_dokter) {
            fragmentClass = Dokter.class;
        } else if (id == R.id.nav_poli) {
            fragmentClass = Poli.class;
        }  else if (id == R.id.nav_share) {
            fragmentClass = Histori.class;
        } else if (id == R.id.nav_send) {

        } try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        assert fragment != null;
        fragment.setArguments(bundleMain);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Login.session_status, false);
            editor.putString(tagIdPasien, null);
            editor.putString(tagNama, null);
            editor.putString(tagJenisIdentitas, null);
            editor.putString(tagNoHP1, null);
            editor.putString(tagNoBpjs,null);
            editor.putString(tagKelamin, null);
            editor.putString(tagNoTelp, null);
            editor.putString(tagGolDarah, null);
            editor.putString(tagNoKartu, null);
            editor.putString(tagNoRm, null);
            editor.putString(tagStatusBpjs, null);
            editor.putString(tagTanggalDaftar, null);
            editor.putString(tagStatus, null);
            editor.putString(tagAgama, null);
            editor.putString(tagPekerjaan, null);
            editor.putString(tagStatusKematian, null);
            editor.putString(tagUmur, null);
            editor.putString(tagNoIdentitas, null);
            editor.putString(tagStatusPasien, null);
            editor.putString(tagTanggalLahir, null);
            editor.apply();
            Intent intentLogin = new Intent(MainActivity.this, Login.class);
            startActivity(intentLogin);
            finish();
            Toast.makeText(getApplicationContext(), "Sesi habis, silakan login kembali", Toast.LENGTH_LONG).show();

        }
    };

    /*@Override
    public void onUserInteraction() {
        super.onUserInteraction();

        timer.cancel();
    }*/

    /*@Override
    protected void onResume(){
        super.onResume();

        timer.start();
    }*/

}
