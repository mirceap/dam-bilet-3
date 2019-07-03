package id.csie.ase.ro.bilet3again;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addAuto extends AppCompatActivity {
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auto);

        EditText txtNrAuto = findViewById(R.id.tvNrAuto);
        EditText txtData = findViewById(R.id.tvDataInregistrare);
        EditText txtLoc = findViewById(R.id.tvLoc);
        EditText txtPlatit = findViewById(R.id.tvPlatit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Autovehicul autoEdit = (Autovehicul) bundle.getSerializable(getString(R.string.edit_auto));
            if (autoEdit == null){
                return;
            }
            txtNrAuto.setText(autoEdit.getNumarAuto());
            String dataString = format.format(autoEdit.getDataInregistrarii());
            txtData.setText(dataString);
            txtLoc.setText(String.valueOf(autoEdit.getIdLocParcare()));
            txtPlatit.setText(autoEdit.isaPlatit() ? "Da" : "Nu");
        }
    }

    public void sendAuto(View v){
        EditText txtNrAuto = findViewById(R.id.tvNrAuto);
        EditText txtData = findViewById(R.id.tvDataInregistrare);
        EditText txtLoc = findViewById(R.id.tvLoc);
        EditText txtPlatit = findViewById(R.id.tvPlatit);

        String nrAuto = txtNrAuto.getText().toString();
        String stringDate = txtData.getText().toString();
        Date date = null;
        int loc;
        try{
            loc = Integer.parseInt(txtLoc.getText().toString());
        } catch (NumberFormatException e){
            loc = -1;
        }
        boolean platit = txtPlatit.getText().toString().toUpperCase().equals("DA");

        if(nrAuto.equals("")){
            txtNrAuto.setError("Nr auto trebuie sa aiba mai mult de 2 caractere.");
            return;
        }
        if(!stringDate.equals("")){
            try {
                date = format.parse(stringDate);
            } catch (ParseException e) {
                date = null;
                Toast.makeText(this,"Date parsing error", Toast.LENGTH_SHORT).show();
                txtData.setError("Date parsing error");
                return;
            }
        }
        if (!(loc != -1 && loc > 0)){
            txtLoc.setError("Numar loc invalid!");
        }

        Autovehicul auto = new Autovehicul(nrAuto, date, loc, platit);
        Intent intent = new Intent();
        intent.putExtra( getString(R.string.add_auto_intent_name), auto);
        setResult(RESULT_OK, intent);
        finish();
    }
}
