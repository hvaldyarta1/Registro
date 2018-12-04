package app.rsprmobile.registro.adapter;

import android.content.Context;
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
import app.rsprmobile.registro.data.DataDokter;
import app.rsprmobile.registro.data.DataNomor;

public class AdapterGridNomor extends RecyclerView.Adapter<AdapterGridNomor.ViewHolder> {
    private Context context;
    private List<DataNomor> itemNomor;

    public AdapterGridNomor(Context context, List<DataNomor> itemNomor){
        this.context = context;
        this.itemNomor = itemNomor;
    }

    @NonNull
    @Override
    public AdapterGridNomor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(app.rsprmobile.registro.R.layout.grid_nomor, null);

        return new AdapterGridNomor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGridNomor.ViewHolder viewHolder, final int position) {
        DataNomor dataNomor = itemNomor.get(position);

        viewHolder.textNomor.setText(String.valueOf(itemNomor.get(position).getNomor()));

    }

    @Override
    public int getItemCount() {
        return itemNomor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNomor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textNomor = (TextView) itemView.findViewById(app.rsprmobile.registro.R.id.txtNomor);

        }
    }
}