package com.example.dalitso.india;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
                    .appendQueryParameter("phone", String.valueOf(phonenumber))
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


//    public void login (int phonenumber, String password){
//
//        JSONObject data  = new JSONObject();
//        try {
//            data.put("password", password);
//            data.put("phone",phonenumber);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        final String BASE_URL =
//                "http://dalitsobanda.com/node";
//        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
//                .appendQueryParameter("password", password)
//                .appendQueryParameter("phone", String.valueOf(phonenumber))
//                .build();
//
////        sendJSONtoServer(data, builtUri, "POST");
//    }


    public void ashaSignUp (int phonenumber, String password, String name, String hospital){

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

                .appendQueryParameter("password", password)
                .appendQueryParameter("phone",phonenumber+"")
                .appendQueryParameter("hospital",hospital)
                .appendQueryParameter("name",name)
                .build();

//        sendJSONtoServer(data, builtUri);
    }



     public void createRecord (String mother_id){

        final String BASE_URL =
                "http://dalitsobanda.com/node//asha/records";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("mother",mother_id)
                .build();

//        sendJSONtoServer(data, builtUri);
    }



    public void getMothers (){

        final String BASE_URL =
                "http://dalitsobanda.com/node//mothers";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .build();

       // sendJSONtoServer(data, builtUri);
    }


    public void getMotherRecords (String mother_id){


        final String BASE_URL =
                "http://dalitsobanda.com/node//mothers";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("mother", mother_id)
                .build();

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
                URL url = new URL(t.getBuiltUri().toString());
                urlConnection = (HttpURLConnection) url.openConnection();
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
