package app.rsprmobile.registro.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.rsprmobile.registro.R;
import app.rsprmobile.registro.data.DataDokter;
import app.rsprmobile.registro.data.DataDokterPoli;

public class AdapterDokterPoli extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<DataDokterPoli> itemDokterPoli;

    public AdapterDokterPoli(Activity activity, List<DataDokterPoli> itemDokterPoli){
        this.activity = activity;
        this.itemDokterPoli = itemDokterPoli;
    }


    @Override
    public int getCount() {
        return itemDokterPoli.size();
    }

    @Override
    public Object getItem(int location) {
        return itemDokterPoli.get(location);
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
            convertView = layoutInflater.inflate(R.layout.list_dokter_poli, null);


        TextView txtNamaDokterPoli = (TextView) convertView.findViewById(R.id.txtNamaDokterPoli);

        DataDokterPoli dataDokterPoli = itemDokterPoli.get(position);

        String namaDokterPoli = dataDokterPoli.getNamaDokterTetap();

        txtNamaDokterPoli.setText(namaDokterPoli);

        return convertView;
    }
}
