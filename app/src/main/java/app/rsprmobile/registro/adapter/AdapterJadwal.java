package app.rsprmobile.registro.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.rsprmobile.registro.R;
import app.rsprmobile.registro.data.DataJadwal;

public class AdapterJadwal extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataJadwal> itemJadwal;

    public AdapterJadwal(Activity activity, List<DataJadwal> itemJadwal){
        this.activity = activity;
        this.itemJadwal = itemJadwal;
    }

    public AdapterJadwal(){

    }

    @Override
    public int getCount() {
        return itemJadwal.size();
    }

    @Override
    public Object getItem(int location) {
        return itemJadwal.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_jadwal, null);

        TextView namaKlinik = (TextView) convertView.findViewById(R.id.txtNamaKlinik);
        TextView ruangPeriksa = (TextView) convertView.findViewById(R.id.txtRuangPeriksa);
        TextView jamAwal = (TextView) convertView.findViewById(R.id.txtJamAwal);
        TextView jamAkhir = (TextView) convertView.findViewById(R.id.txtJamAkhir);
        TextView kuotaPasienPerjam = (TextView) convertView.findViewById(R.id.txtKuotaPasien);
        TextView hariPraktek = (TextView) convertView.findViewById(R.id.txtHariPraktek);

        DataJadwal dataJadwal = itemJadwal.get(position);

        namaKlinik.setText("Nama Klinik: " + dataJadwal.getNamaKlinik());
        ruangPeriksa.setText("Ruang Periksa: " + dataJadwal.getRuangPeriksa());
        jamAwal.setText("Waktu: " + dataJadwal.getJamAwal() + " - ");
        jamAkhir.setText(dataJadwal.getJamAkhir());
        kuotaPasienPerjam.setText("Kuota Pasien Per Jam: " + dataJadwal.getKuotaPasienPerjam());
        /*hariPraktek.setText("Hari Praktek: " + dataJadwal.getHariPraktek());*/

        String hari = dataJadwal.getHariPraktek();

        if (hari.trim().equals("0")){
            hariPraktek.setText("Hari Praktek: Senin");
        } else if (hari.trim().equals("1")){
            hariPraktek.setText("Hari Praktek: Selasa");
        } else if (hari.trim().equals("2")){
            hariPraktek.setText("Hari Praktek: Rabu");
        } else if (hari.trim().equals("3")) {
            hariPraktek.setText("Hari Praktek: Kamis");
        } else if (hari.trim().equals("4")){
            hariPraktek.setText("Hari Praktek: Jumat");
        } else if (hari.trim().equals("5")){
            hariPraktek.setText("Hari Praktek: Sabtu");
        } else if (hari.trim().equals("6")){
            hariPraktek.setText("Hari Praktek: Minggu");
        }

        /*switch (hari){
            case "0":
                hariPraktek.setText("Hari Praktek: Senin");
            case "1":
                hariPraktek.setText("Hari Praktek: Selasa");
            case "2":
                hariPraktek.setText("Hari Praktek: Rabu");
            case "3":
                hariPraktek.setText("Hari Praktek: Kamis");
            case "4":
                hariPraktek.setText("Hari Praktek: Jumat");
            case "5":
                hariPraktek.setText("Hari Praktek: Sabtu");
            case "6":
                hariPraktek.setText("Hari Praktek: Minggu");
        }*/
        return convertView;
    }
}