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
import app.rsprmobile.registro.data.DataPoli;

public class AdapterSpinnerPoli extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPoli> poli;

    public AdapterSpinnerPoli(Activity activity, List<DataPoli> poli){
        this.poli = poli;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return poli.size();
    }

    @Override
    public Object getItem(int location) {
        return poli.get(location);
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
            convertView = inflater.inflate(R.layout.list_spinner_poli, null);

        TextView list_poli = (TextView) convertView.findViewById(R.id.Poli);

        DataPoli dataKategori;
        dataKategori = poli.get(position);

        list_poli.setText(dataKategori.getNamaKlinik());

        return convertView;
    }
}
