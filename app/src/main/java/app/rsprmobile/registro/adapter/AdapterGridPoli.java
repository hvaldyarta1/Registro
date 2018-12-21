package app.rsprmobile.registro.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.rsprmobile.registro.DokterPoli;
import app.rsprmobile.registro.R;
import app.rsprmobile.registro.data.DataPoli;

public class AdapterGridPoli extends RecyclerView.Adapter<AdapterGridPoli.ViewHolder> {
    private Context context;
    private List<DataPoli> itemPoli;

    public AdapterGridPoli(Context context, List<DataPoli> itemPoli){
        this.context = context;
        this.itemPoli = itemPoli;
    }


    @NonNull
    @Override
    public AdapterGridPoli.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_poli, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGridPoli.ViewHolder viewHolder, final int position) {
        DataPoli dataPoli = itemPoli.get(position);

        String urlImage = dataPoli.getFotoKlinik();
        // Split
        String[] imageStringSplit = urlImage.split(",");
        String part1 = imageStringSplit[0];
        String part2 = imageStringSplit[1];

        String[] getBase64 = part2.split("'");
        String imageBase64 = getBase64[0];

        byte[] imageByteArray = Base64.decode(imageBase64, Base64.DEFAULT);
        // here imageBytes is base64String

        Glide.with(context)
                .load(imageByteArray)
                .into(viewHolder.imagePoli);

            viewHolder.txtPoli.setText(String.valueOf(itemPoli.get(position).getNamaKlinik()));

            /*String imagePoli = dataPoli.getFotoKlinik();
            imagePoli.trim().replace("<>'", "");*/

            viewHolder.imagePoli.setImageResource(R.drawable.poli);

        viewHolder.imagePoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                String namaPoli = itemPoli.get(position).getNamaKlinik();
                String idPoli = itemPoli.get(position).getIdKlinik();
                String keterangan = itemPoli.get(position).getKeterangan();
                String ruanganKlinik = itemPoli.get(position).getRuanganKlinik();

                Bundle bundlePoli = new Bundle();
                bundlePoli.putString("namaKlinik", namaPoli);
                bundlePoli.putString("idKlinik", idPoli);
                bundlePoli.putString("keterangan", keterangan);
                bundlePoli.putString("ruanganKlinik", ruanganKlinik);

                DokterPoli dokterPoli = new DokterPoli();
                dokterPoli.setArguments(bundlePoli);
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, dokterPoli)
                        .addToBackStack(null)
                        .commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemPoli.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtPoli;
        ImageView imagePoli;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPoli = (TextView) itemView.findViewById(R.id.txtViewNamaPoli);
            imagePoli = (ImageView) itemView.findViewById(R.id.imagePoli);
        }
    }
}
