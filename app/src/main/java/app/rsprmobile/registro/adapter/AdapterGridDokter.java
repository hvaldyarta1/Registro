package app.rsprmobile.registro.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.rsprmobile.registro.JadwalDokter;
import app.rsprmobile.registro.R;
import app.rsprmobile.registro.data.DataDokter;

public class AdapterGridDokter extends RecyclerView.Adapter<AdapterGridDokter.ViewHolder> {
    private Context context;
    private List<DataDokter> itemDokter;

    public AdapterGridDokter(Context context, List<DataDokter> itemDokter){
        this.context = context;
        this.itemDokter = itemDokter;
    }


    @NonNull
    @Override
    public AdapterGridDokter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_dokter, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGridDokter.ViewHolder viewHolder, final int position) {
        DataDokter dataDokter = itemDokter.get(position);

        viewHolder.textNamaDokter.setText(String.valueOf(itemDokter.get(position).getNamaDokter()));
        viewHolder.fotoDokter.setImageResource(R.drawable.dokter);

        viewHolder.fotoDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                String namaDokter = itemDokter.get(position).getNamaDokter();
                String dokterId = itemDokter.get(position).getDokterId();

                Bundle bundleDokter = new Bundle();
                bundleDokter.putString("iddokter", dokterId);
                bundleDokter.putString("namaDokter", namaDokter);

                JadwalDokter jadwalDokter = new JadwalDokter();
                jadwalDokter.setArguments(bundleDokter);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, jadwalDokter)
                        .addToBackStack(null).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemDokter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNamaDokter;
        ImageView fotoDokter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textNamaDokter = (TextView) itemView.findViewById(R.id.txtNamaDokter);
            fotoDokter = (ImageView) itemView.findViewById(R.id.imageDokter);

        }
    }
}