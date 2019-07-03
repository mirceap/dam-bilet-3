package id.csie.ase.ro.bilet3again;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JsonWorker extends AsyncTask<String, String, JSONArray> {
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param strings The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */

    private Activity context;

    public JsonWorker(Activity context){
        this.context = context;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader bf = null;

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            bf = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = bf.readLine()) != null){
                buffer.append(line + "\n");
                Log.i("Response : ", "> " + line);
            }
            return new JSONArray(buffer.toString());
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        Toast.makeText(context, "Citit din URL", Toast.LENGTH_SHORT);
        super.onPostExecute(jsonArray);
    }
}
