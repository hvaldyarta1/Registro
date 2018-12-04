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
import app.rsprmobile.registro.data.DataJamPraktek;

public class AdapterSpinnerJamPraktek extends BaseAdapter {
    private Activity activity;
    private List<DataJamPraktek> jamPratek;
    LayoutInflater inflater;

    public AdapterSpinnerJamPraktek (Activity activity, List<DataJamPraktek> jamPratek){
        this.activity = activity;
        this.jamPratek = jamPratek;
    }


    @Override
    public int getCount() {
        return jamPratek.size();
    }

    @Override
    public Object getItem(int location) {
        return jamPratek.get(location);
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
            convertView = inflater.inflate(R.layout.spinner_jam_praktek, null);

        TextView textJam = (TextView) convertView.findViewById(R.id.jamPraktek);

        DataJamPraktek dataJamPraktek = jamPratek.get(position);

        textJam.setText(dataJamPraktek.getJamAwal() + " - " + dataJamPraktek.getJamAkhir());
        return convertView;
    }
}

