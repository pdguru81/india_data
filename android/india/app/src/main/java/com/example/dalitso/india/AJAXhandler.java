package com.example.dalitso.india;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dalitso on 2/14/15.
 */
public class AJAXhandler {

    Activity activity;

    public void createMothers (View view) {
        TextView Vname = (TextView) view.findViewById(R.id.newPatientName);
        TextView Vnumber = (TextView) view.findViewById(R.id.newPatientNumber);
        String name = (String) Vname.getText();
        String number = (String) Vnumber.getText();
    }

    AJAXhandler(Activity activity){
        this.activity =  activity;
    }

    public interface task {
        public Uri getBuiltUri();
        public String getMethod();
        public void response(String result);
    }
    public Login login(int phonenumber, String password){
        return new Login(phonenumber, password);
    }

    public aTask sendTask(task t){
        return new aTask(t);
    }

    public class Login implements task{
        Uri builtUri;
        Login (int phonenumber, String password) {
            final String BASE_URL =
                    "http://dalitsobanda.com/node/asha/login";
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("password", password)
                    .appendQueryParameter("phone", phonenumber +"")
                    .build();

        }
        @Override
        public Uri getBuiltUri() {
            return builtUri;
        }

        @Override
        public String getMethod() {
            return "POST";
        }

        @Override
        public void response(String result) {;
            Context context = activity.getApplicationContext();
            CharSequence text = "login successful";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            if (result != null) {
//                Context context = activity.getApplicationContext();
//                CharSequence text = "login successful";
//                int duration = Toast.LENGTH_SHORT;
                toast.show();
//                Toast toast = Toast.makeText(context, text, duration);
                ((LoginActivity) activity).showProgress(false);
                Intent intent = new Intent(activity, HomeActivity.class);
                activity.startActivity(intent);



                //finish();
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }
    }




//    public class ashaSignUp implements task (int phonenumber, String password, String name, String hospital){
//
//        final String BASE_URL =
//                "http://dalitsobanda.com/node/ashas";
//        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
//
//                .appendQueryParameter("password", password)
//                .appendQueryParameter("phone",phonenumber+"")
//                .appendQueryParameter("hospital",hospital)
//                .appendQueryParameter("name",name)
//                .build();
//
////        sendJSONtoServer(data, builtUri);
//    }



     public class createRecord  implements task{

         Uri builtUri;

        createRecord(String mother_id){
            final String BASE_URL =
                    "http://dalitsobanda.com/node//asha/records";
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("mother",mother_id)
                    .build();
        }

         @Override
         public Uri getBuiltUri() {
             return builtUri;
         }

         @Override
         public String getMethod() {
             return "POST";
         }

         @Override
         public void response(String result) {

         }


//        sendJSONtoServer(data, builtUri);
    }


    public getMothers getMothers(ArrayAdapter arrayAdapter    ){
        return new getMothers(arrayAdapter);
    }

    public class getMothers implements task{
        Uri builtUri;
        ArrayAdapter arrayAdapter;
        getMothers(ArrayAdapter arrayAdapter){
            final String BASE_URL =
                    "http://dalitsobanda.com/node/asha/mothers";
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .build();
            this.arrayAdapter = arrayAdapter;
            arrayAdapter.add("dalitso");
            arrayAdapter.notifyDataSetChanged();
        }


        @Override
        public Uri getBuiltUri() {
            return builtUri;
        }

        @Override
        public String getMethod() {
            return "GET";
        }

        @Override
        public void response(String result) {
            arrayAdapter.add(result);
            arrayAdapter.add("dalitso response");
            arrayAdapter.notifyDataSetChanged();
            try {
                JSONArray data = new JSONArray(result);
                for(int i =0; i < data.length(); i++ ){
                    arrayAdapter.add(data.getString(i));
                }
                arrayAdapter.add(result);
                arrayAdapter.add("dalitso");
                arrayAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        // sendJSONtoServer(data, builtUri);
    }


    public class getMotherRecords implements  task{
        Uri builtUri;

        getMotherRecords(String mother_id){
            final String BASE_URL =
                    "http://dalitsobanda.com/node//mothers";
            builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("mother", mother_id)
                    .build();

        }

        @Override
        public Uri getBuiltUri() {
            return builtUri;
        }

        @Override
        public String getMethod() {
            return "GET";
        }

        @Override
        public void response(String result) {

        }


//        sendJSONtoServer(data, builtUri);
    }


    public class aTask extends  AsyncTask<Void, Void, String> {
        public task t;
        aTask(task t){
            this.t = t;
        }
        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String result = null;
            try {

                String headerName=null;
                URL url = new URL(t.getBuiltUri().toString());
                urlConnection = (HttpURLConnection) url.openConnection();

                for (int i=1; (headerName = urlConnection.getHeaderFieldKey(i))!=null; i++) {
                    if (headerName.equals("Set-Cookie")) {
                        String cookie = urlConnection.getHeaderField(i);
                        cookie = cookie.substring(0, cookie.indexOf(";"));
                        String cookieName = cookie.substring(0, cookie.indexOf("="));
                        String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());

                urlConnection.setRequestMethod(t.getMethod());
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
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
        }

        @Override
        protected void onPostExecute(String result) {
            t.response(result);
        }
    }
}
