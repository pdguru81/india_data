package com.example.dalitso.india;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dalitso on 2/14/15.
 */
public class AJAXhandler {
    public void createMothers (View view){
        TextView Vname = (TextView) view.findViewById(R.id.newPatientName);
        TextView Vnumber = (TextView) view.findViewById(R.id.newPatientNumber);
        String name = (String) Vname.getText();
        String number = (String) Vnumber.getText();

        JSONObject data  = new JSONObject();
        try {
              .appendQueryParameter("name", name)
              .appendQueryParameter("phone", phone)
              .appendQueryParameter("emergency_contact_phone", phone)
              .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void login (int phonenumber, String password){

        JSONObject data  = new JSONObject();
        try {
            data.put("password", password);
            data.put("phone",phonenumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String BASE_URL =
                "http://dalitsobanda.com/node";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("password", password)
                .appendQueryParameter("phone", phone)
                .build();

        sendJSONtoServer(data, builtUri);
    }


    public void ashaSignUp (int phonenumber, String password, String name, Strig hospital){

        JSONObject data  = new JSONObject();
        try {
            data.put("password", password);
            data.put("phone",phonenumber);
            data.put("hospital",hospital);
            data.put("name",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String BASE_URL =
                "http://dalitsobanda.com/node/ashas";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("password", password);
                .appendQueryParameter("phone",phonenumber);
                .appendQueryParameter("hospital",hospital);
                .appendQueryParameter("name",name);
                .build();

        sendJSONtoServer(data, builtUri);
    }


     public void createRecord (int mother_id){

        JSONObject data  = new JSONObject();
        try {
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String BASE_URL =
                "http://dalitsobanda.com/node//asha/records";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("mother",mother_id);
                .build();

        sendJSONtoServer(data, builtUri);
    }



    public void getMothers (){

        JSONObject data  = new JSONObject();
        try {
           
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String BASE_URL =
                "http://dalitsobanda.com/node//mothers";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .build();

        sendJSONtoServer(data, builtUri);
    }


    public void getMotherRecords (int mother_id){

        JSONObject data  = new JSONObject();
        try {
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String BASE_URL =
                "http://dalitsobanda.com/node//mothers";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("mother", mother_id)
                .build();

        sendJSONtoServer(data, builtUri);
    }



    public void sendJSONtoServer(JSONObject data , final Uri builtUri){
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground (Void...params){

                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                // Will contain the raw JSON response as a string.
                String result = null;

                try {
//                    final String BASE_URL =
//                            "http://api.openweathermap.org/data/2.5/forecast/daily?";
//                    Uri builtUri = Uri.parse(BASE_URL).buildUpon()
//                            .appendQueryParameter("param", "value")
//                            .build();

                    URL url = new URL(builtUri.toString());

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    result = buffer.toString();
                } catch (IOException e) {
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            //Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }


                return result;
            };

            @Override
            protected void onPostExecute(String result) {
            }
        }.execute();

    }
}
