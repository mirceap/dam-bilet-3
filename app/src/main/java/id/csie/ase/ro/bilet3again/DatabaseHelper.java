package id.csie.ase.ro.bilet3again;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final SimpleDateFormat formater = new SimpleDateFormat("MM-DD-YYYY HH:MM");
    public static final String DATABASE_NAME = "listaAuto.db";
    public static final String TABLE_NAME = "Autovehicule";
    public static final String COLUMN_1 = "ID_AUTO";
    public static final String COLUMN_2 = "NR_AUTO";
    public static final String COLUMN_3 = "DATA";
    public static final String COLUMN_4 = "ID_LOC_PARCARE";
    public static final String COLUMN_5 = "PLATIT";
    public static int classID = 0;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +  "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_1 + " INTEGER, " + COLUMN_2 + " TEXT," +  COLUMN_3 + " DATETIME, " + COLUMN_4 + " INTEGER, " + COLUMN_5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addAuto(Autovehicul auto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValues(auto);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            Log.e("Auto NESALVAT - ", auto.toString());
            return false;
        }
        Log.i("Auto salvat - ", auto.toString());
        return true;
    }

    @NonNull
    private ContentValues getContentValues(Autovehicul auto) {
        String dateS = formater.format(auto.getDataInregistrarii());
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, classID++);
        contentValues.put(COLUMN_2, auto.getNumarAuto());
        contentValues.put(COLUMN_3, dateS);
        contentValues.put(COLUMN_4, auto.getIdLocParcare());
        contentValues.put(COLUMN_5, auto.isaPlatit());
        return contentValues;
    }

    public Cursor getLista(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public boolean addList(List<Autovehicul> list){
        boolean res = true;
        for (Autovehicul auto : list){
            res = res | addAuto(auto);
        }
        return res;
    }

    public boolean updateItem(Autovehicul auto){
        ContentValues cv = getContentValues(auto);
        SQLiteDatabase db = this.getWritableDatabase();
        long rowNo = db.update(TABLE_NAME, cv, COLUMN_2 + " = ?", new String[] {auto.getNumarAuto()});
        if (rowNo > 0){
            return true;
        }
        return false;
    }

}
