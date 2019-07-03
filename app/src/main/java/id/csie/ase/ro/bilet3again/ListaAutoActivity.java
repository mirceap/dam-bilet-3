package id.csie.ase.ro.bilet3again;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListaAutoActivity extends AppCompatActivity {
    private static final int EDIT_REQUEST_CODE = 1;
    public static final SimpleDateFormat formater = new SimpleDateFormat("MM-DD-YYYY HH:MM");

    AutoAdapter adapter;
    DatabaseHelper myDb;
    private List<Autovehicul> list;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_auto);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            list = (List<Autovehicul>) bundle.getSerializable(getString(R.string.send_to_list));
        }
        myDb = new DatabaseHelper(this);
        Cursor data = myDb.getLista();
        if (data.getCount() == 0){
            Toast.makeText(this, "Empty DB", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()){
                int idDb = data.getInt(1);
                String nrAuto = data.getString(2);
                String dataS = data.getString(3);
                Date dataAuto;
                try {
                    dataAuto = formater.parse(dataS);
                } catch (ParseException e) {
                    Toast.makeText(this,  idDb + " - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    dataAuto = null;
                }
                int idParcare = data.getInt(4);
                boolean aPlatit = Boolean.parseBoolean(data.getString(5));
                Autovehicul autovehicul = new Autovehicul(nrAuto, dataAuto, idParcare, aPlatit);
                Log.i("Auto salvat - " + idDb, autovehicul.toString());
                list.add(autovehicul);

            }
        }
        adapter = new AutoAdapter(list,this);
        ListView listView = findViewById(R.id.lvAuto);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent,view,position, id) -> {
            Intent intent = new Intent(ListaAutoActivity.this, addAuto.class);
            intent.putExtra(getString(R.string.edit_auto), list.get(position));
            this.position = position;
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        });
        listView.setOnItemLongClickListener((parent,view,position, id)-> {
            list.remove(position);
            adapter.notifyDataSetChanged();
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK){
            Autovehicul auto = (Autovehicul) data.getSerializableExtra(getString(R.string.add_auto_intent_name));
            if (auto !=null){
                list.remove(position);
                list.add(position,auto);
                myDb.updateItem(auto);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.list_to_main), (Serializable) list);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
