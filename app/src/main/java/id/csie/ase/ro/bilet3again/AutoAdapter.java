package id.csie.ase.ro.bilet3again;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class AutoAdapter extends BaseAdapter {
    private List<Autovehicul> list;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    private Activity activity;
    private static LayoutInflater inflater;

    public AutoAdapter(List<Autovehicul> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Autovehicul getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getIdLocParcare();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        itemView = itemView == null? inflater.inflate(R.layout.row_list_item, null) : itemView;

        TextView tvNrAuto = itemView.findViewById(R.id.tvNrAuto);
        TextView tvData = itemView.findViewById(R.id.tvDataInregistrare);
        TextView tvLoc = itemView.findViewById(R.id.tvLocParcare);
        TextView tvPlatit = itemView.findViewById(R.id.tvPlatit);

        Autovehicul auto = getItem(position);
        tvNrAuto.setText(auto.getNumarAuto());
        String date = format.format(auto.getDataInregistrarii());
        tvData.setText(date);
        tvLoc.setText(String.valueOf(auto.getIdLocParcare()));
        tvPlatit.setText(auto.isaPlatit()? "Da" : "Nu");

        return itemView;
    }
}
