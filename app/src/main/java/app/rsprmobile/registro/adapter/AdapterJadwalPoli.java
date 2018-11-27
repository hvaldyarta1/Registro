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
import app.rsprmobile.registro.data.DataJadwalPoli;

public class AdapterJadwalPoli extends BaseAdapter {
    Activity activity;
    List<DataJadwalPoli> itemJadwalPoli;
    LayoutInflater layoutInflater;

    public AdapterJadwalPoli(Activity activity, List<DataJadwalPoli> itemJadwalPoli){
        this.activity = activity;
        this.itemJadwalPoli = itemJadwalPoli;
    }

    @Override
    public int getCount() {
        return itemJadwalPoli.size();
    }

    @Override
    public Object getItem(int location) {
        return itemJadwalPoli.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_jadwal_dokter_poli, null);

        TextView txtJamAwal = (TextView) convertView.findViewById(R.id.txtJamAwal);
        TextView txtJamAkhir = (TextView) convertView.findViewById(R.id.txtJamAkhir);
        TextView txtHariPraktek = (TextView) convertView.findViewById(R.id.txtHariPraktek);

        DataJadwalPoli dataJadwalPoli = itemJadwalPoli.get(position);

        String jamAwal = dataJadwalPoli.getJamAwal();
        String jamAkhir = dataJadwalPoli.getJamAkhir();

        txtJamAwal.setText(jamAwal);
        txtJamAkhir.setText(jamAkhir);

        String hari = dataJadwalPoli.getHariPraktek();

        if (hari.trim().equals("0")){
            txtHariPraktek.setText("Hari Praktek: Senin");
        } else if (hari.trim().equals("1")){
            txtHariPraktek.setText("Hari Praktek: Selasa");
        } else if (hari.trim().equals("2")){
            txtHariPraktek.setText("Hari Praktek: Rabu");
        } else if (hari.trim().equals("3")) {
            txtHariPraktek.setText("Hari Praktek: Kamis");
        } else if (hari.trim().equals("4")){
            txtHariPraktek.setText("Hari Praktek: Jumat");
        } else if (hari.trim().equals("5")){
            txtHariPraktek.setText("Hari Praktek: Sabtu");
        } else if (hari.trim().equals("6")){
            txtHariPraktek.setText("Hari Praktek: Minggu");
        }

        return convertView;
    }
}
