package id.csie.ase.ro.bilet3again;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            List<Autovehicul> autovehicule = (List<Autovehicul>) bundle.getSerializable(getString(R.string.lista_raport));
            barChart = (BarChart) findViewById(R.id.barChart);
            barChart.getDescription().setEnabled(false);



            setData(autovehicule);
            barChart.setFitBars(true);
        }

    }

    private void setData(List<Autovehicul> autovehicule) {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        int nrAchitat = 0;
        int nrNeachitat = 0;
        for (Autovehicul e : autovehicule) {
            if (e.isaPlatit()) {
                nrAchitat++;
            } else {
                nrNeachitat++;
            }
        }
        Legend legend = barChart.getLegend();
        LegendEntry entry1 = new LegendEntry("Neplatit",Legend.LegendForm.DEFAULT,10f,2f,null, Color.GREEN);
        LegendEntry entry2 = new LegendEntry("Platit",Legend.LegendForm.DEFAULT,10f,2f,null, Color.YELLOW);
        legend.setCustom(new LegendEntry[] { entry1, entry2 });

        yVals.add(new BarEntry(1, nrAchitat));
        yVals.add(new BarEntry(2, nrNeachitat));


        BarDataSet set = new BarDataSet(yVals, "Grafic plata parcare");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData barData = new BarData(set);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(500);


    }
}
