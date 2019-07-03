package id.csie.ase.ro.bilet3again;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Autovehicul> listAutos = new ArrayList<>();

    private static int REQUEST_CODE_ADD = 1;
    private static int REQUEST_CODE_LIST = 2;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        Button btnAdd = findViewById(R.id.btnAdauga);
        Button btnList = findViewById(R.id.btnList);
        Button btnJson = findViewById(R.id.btnJson);
        Button btnSave = findViewById(R.id.btnSaveToDB);
        Button btnReport = findViewById(R.id.btnRaport);
        Button btnAbout = findViewById(R.id.btnDespre);

        btnAbout.setOnClickListener((v) -> {
            Toast.makeText(this, getString(R.string.creator), Toast.LENGTH_SHORT).show();
        });

        btnAdd.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, addAuto.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaAutoActivity.class);
            intent.putExtra(getString(R.string.send_to_list), (Serializable) listAutos);
            startActivityForResult(intent, REQUEST_CODE_LIST);
        });
        btnSave.setOnClickListener(v -> {
            dbHelper.addList(listAutos);
        });
        btnReport.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            intent.putExtra(getString(R.string.lista_raport), (Serializable) listAutos);
            startActivity(intent);
        });
        btnJson.setOnClickListener(v -> {
            JsonWorker jw = new JsonWorker(this);
             String s ="http://localhost:8080/json";
//            String s = "https://e71384509ed34ac5a0144db6e76808d2.vfs.cloud9.us-east-2.amazonaws.com/json";
//            String s = "https://pdm.ase.ro/examen/autovehicule.json";
//            jw.execute()
//            JSONArray array =
            Object array = jw.execute(s);
            System.out.println("");
//            for(int i = 0; i < array.length(); i++){
//                try {
//                    JSONObject obj = array.getJSONObject(i);
//                    listAutos.add(extractAuto(obj));
//                } catch (JSONException e) {
//                    Log.i("Eroare: Parsare obiect", "Obiectul: " + i + "/" + array.length());
//                    e.printStackTrace();
//                }
//            }
        });
    }

    private Autovehicul extractAuto(JSONObject obj) throws JSONException {
        Autovehicul auto = new Autovehicul();
        auto.setNumarAuto(obj.getString("numarAuto"));
        try {
            auto.setDataInregistrarii(obj.getString("dataInregistrare"));
        } catch (ParseException e) {
            auto.setDataInregistrarii(Calendar.getInstance().getTime());
            Log.i("Eroare: Formatare Data", auto.getNumarAuto() + " - " + e.getMessage());
            e.printStackTrace();
        }
        auto.setIdLocParcare(obj.getInt("idLocParcare"));
        auto.setaPlatit(obj.getBoolean("aPlatit"));
        return auto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            listAutos.add((Autovehicul) data.getSerializableExtra(getString(R.string.add_auto_intent_name)));
        }
        if (requestCode == REQUEST_CODE_LIST && resultCode == RESULT_OK) {
            String string = getString(R.string.list_to_main);
            Serializable objects = data.getSerializableExtra(string);
            listAutos.addAll((List<Autovehicul>) objects);
        }
    }
}
