package br.com.w2bapp.integration;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by marco on 28/04/2016.
 */
public class HttpGetTask extends AsyncTask<Void, Void, String> {

    private URL url;
    private HttpURLConnection connection;
    private Context context;

    public HttpGetTask(Context context, String endpointAddress) throws MalformedURLException {
        this.url = new URL(endpointAddress);
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            //connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            String resposta = new Scanner(connection.getInputStream()).next();
            return resposta;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String strings) {
        Toast.makeText(context, strings, Toast.LENGTH_LONG);
    }
}
