package app.rsprmobile.registro.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.rsprmobile.registro.DokterPoli;
import app.rsprmobile.registro.R;
import app.rsprmobile.registro.data.DataHistori;
import app.rsprmobile.registro.data.DataPoli;

public class AdapterHistori extends BaseAdapter {
    Activity activity;
    List<DataHistori> itemHistori;
    LayoutInflater inflater;

    public AdapterHistori (Activity activity, List<DataHistori> itemHistori){
        this.itemHistori = itemHistori;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return itemHistori.size();
    }

    @Override
    public Object getItem(int location) {
        return itemHistori.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(app.rsprmobile.registro.R.layout.list_histori, null);

        TextView txtNoAntrean, txtNamaKlinik, txtDokter, txtJam, txtRuang;
        txtNoAntrean = (TextView) convertView.findViewById(app.rsprmobile.registro.R.id.txtNomorAntrian);
        txtNamaKlinik = (TextView) convertView.findViewById(app.rsprmobile.registro.R.id.txtPoli);
        txtDokter = (TextView) convertView.findViewById(app.rsprmobile.registro.R.id.textDokter);
        txtJam = (TextView) convertView.findViewById(app.rsprmobile.registro.R.id.textJamPelayanan);
        txtRuang = (TextView) convertView.findViewById(app.rsprmobile.registro.R.id.textRuang);

        DataHistori dataHistori = itemHistori.get(position);

        txtNoAntrean.setText(dataHistori.getNoAntrean());
        txtNamaKlinik.setText("Poli: " + dataHistori.getNamaKlinik());
        txtDokter.setText("Dokter: " + dataHistori.getDokterJadwal());

        String jamAwal = dataHistori.getWaktuCheckin();
        String splitWaktuChekin[] = jamAwal.split("\\:");
        String jam1 = splitWaktuChekin[0];
        String jam2 = splitWaktuChekin[1];
        txtJam.setText("Dilayani pukul: " + jam1 + ":" + jam2 + " - " + dataHistori.getJamAkhir());

        txtRuang.setText("Ruang: " + dataHistori.getRuang());

        return convertView;
    }
}

/*public class AdapterHistori extends RecyclerView.Adapter<AdapterHistori.ViewHolder> {
    private Context context;
    List<DataHistori> itemHistori;

    public AdapterHistori(Context context, List<DataHistori> itemHistori){
        this.context = context;
        this.itemHistori = itemHistori;
    }


    @NonNull
    @Override
    public AdapterHistori.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_histori, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistori.ViewHolder viewHolder, final int position) {
        DataHistori dataHistori = itemHistori.get(position);

        viewHolder.txtNoAntrean.setText(String.valueOf(itemHistori.get(position).getNoAntrean()));
        viewHolder.txtNamaKlinik.setText(String.valueOf(itemHistori.get(position).getNamaKlinik()));
        viewHolder.txtDokter.setText(String.valueOf(itemHistori.get(position).getDokterJadwal()));
        viewHolder.txtJam.setText(String.valueOf(itemHistori.get(position).getWaktuCheckin()));
    }

    @Override
    public int getItemCount() {
        return itemHistori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNoAntrean, txtNamaKlinik, txtDokter, txtJam, txtRuang;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNoAntrean = (TextView) itemView.findViewById(app.rsprmobile.registro.R.id.txtNomorAntrian);
            txtNamaKlinik = (TextView) itemView.findViewById(app.rsprmobile.registro.R.id.txtPoli);
            txtDokter = (TextView) itemView.findViewById(app.rsprmobile.registro.R.id.textDokter);
            txtJam = (TextView) itemView.findViewById(app.rsprmobile.registro.R.id.textJamPelayanan);
            txtRuang = (TextView) itemView.findViewById(app.rsprmobile.registro.R.id.textRuang);
        }
    }
}*/
