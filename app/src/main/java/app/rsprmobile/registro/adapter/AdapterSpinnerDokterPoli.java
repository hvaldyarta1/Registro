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
import app.rsprmobile.registro.data.DataDokterPoli;

public class AdapterSpinnerDokterPoli extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<DataDokterPoli> dokterPoli;

    public AdapterSpinnerDokterPoli(Activity activity, List<DataDokterPoli> dokterPoli){
        this.activity = activity;
        this.dokterPoli = dokterPoli;
    }

    @Override
    public int getCount() {
        return dokterPoli.size();
    }

    @Override
    public Object getItem(int location) {
        return dokterPoli.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_spinner_dokter, null);

        TextView textDokter = (TextView) convertView.findViewById(R.id.textDokter);

        DataDokterPoli dataDokterPoli = dokterPoli.get(position);
        String namaDokter = dataDokterPoli.getNamaDokterTetap();

        textDokter.setText(namaDokter);

        return convertView;
    }
}
