package app.rsprmobile.registro.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import app.rsprmobile.registro.Pendaftaran;
import app.rsprmobile.registro.R;

public class AdapterButtonNomor extends RecyclerView.Adapter<AdapterButtonNomor.ViewHolder> {
    private Context context;
    private ArrayList<Integer> arrayJumlah;
    // private List<DataKlinik> itemDataKlinik;

    public AdapterButtonNomor(Context context, ArrayList<Integer> arrayJumlah){
        this.context = context;
        this.arrayJumlah = arrayJumlah;
    }


    @NonNull
    @Override
    public AdapterButtonNomor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_nomor, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterButtonNomor.ViewHolder viewHolder, final int position) {
        viewHolder.button.setText(String.valueOf(arrayJumlah.get(position).toString()));

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = viewHolder.button.getText().toString();
                Toast.makeText(v.getContext(), txt, Toast.LENGTH_SHORT).show();

                final Pendaftaran pendaftaran = new Pendaftaran();
                pendaftaran.noAntrian = txt;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi pendaftaran periksa?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO
                        //pendaftaran.daftarPeriksaKunjungan();
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
        });

    }

    @Override
    public int getItemCount() {
        return arrayJumlah.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            button = (Button) itemView.findViewById(R.id.btnNomor);
        }
    }
}