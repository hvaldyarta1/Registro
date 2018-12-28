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
import app.rsprmobile.registro.data.DataDokter;

public class AdapterSpinnerSemuaDokter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataDokter> semuaDokter;

    public AdapterSpinnerSemuaDokter(Activity activity, List<DataDokter> semuaDokter){
        this.activity = activity;
        this.semuaDokter = semuaDokter;
    }


    @Override
    public int getCount() {
        return semuaDokter.size();
    }

    @Override
    public Object getItem(int location) {
        return semuaDokter.get(location);
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
            convertView = inflater.inflate(R.layout.list_spinner_dokter, null);

        TextView namaDokter = convertView.findViewById(R.id.textDokter);

        DataDokter dataDokter = semuaDokter.get(position);
        String dokterNama = dataDokter.getNamaDokter();
        namaDokter.setText(dokterNama);

        return convertView;
    }
}
