package com.example.dalitso.india;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new PlaceholderFragment().newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                Context context = getApplicationContext();
                CharSequence text = "selected section 2 patient";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private View currentView;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

//            if(sectionNumber == 2){
//                ItemFragment ifg = new ItemFragment();
//                return ifg;
//            }

            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2 || getArguments().getInt(ARG_SECTION_NUMBER) == 1 ){
                rootView = inflater.inflate(R.layout.fragment_item, container, false);
                Context context = getApplicationContext();
                CharSequence text = "selected section 2 patient";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //ListView mPatientListView = (ListView) rootView.findViewById(R.id.patientListView);
                ListView mPatientListView = (ListView) rootView.findViewById(R.id.itemlist);

                // list of patients
                final ArrayList<String> patientList = new ArrayList<String>();
                patientList.add("Kapaya Katongo");
                patientList.add("Tawanda Zimuto");
                patientList.add("Fidelis Chibombe");
                patientList.add("Philip Abel");
                // set list adapter for patients
                final ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_1, patientList);

                // Set The Adapter
                mPatientListView.setAdapter(arrayAdapter);
                if (getArguments().getInt(ARG_SECTION_NUMBER) == 2 ) {
                    mPatientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(HomeActivity.this, CheckActivity.class);
                            //                EditText editText = (EditText) findViewById(R.id.edit_message);
                            //                String message = editText.getText().toString();
                            //                intent.putExtra(EXTRA_MESSAGE, message);
                            intent.putExtra("patientName", patientList.get(position));
                            startActivity(intent);
                        }
                    });
                }else{
                    mPatientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(HomeActivity.this, PatientHistory.class);
                            //                EditText editText = (EditText) findViewById(R.id.edit_message);
                            //                String message = editText.getText().toString();
                            //                intent.putExtra(EXTRA_MESSAGE, message);
                            intent.putExtra("patientName", patientList.get(position));
                            startActivity(intent);
                        }
                    });
                }

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground (Void...params){

                        // These two need to be declared outside the try/catch
                        // so that they can be closed in the finally block.
                        HttpURLConnection urlConnection = null;
                        BufferedReader reader = null;

                        // Will contain the raw JSON response as a string.
                        String forecastJsonStr = null;

                        String format = "json";
                        String units = "metric";
                        int numDays = 7;

                        try {
                            // Construct the URL for the OpenWeatherMap query
                            // Possible parameters are avaiable at OWM's forecast API page, at
                            // http://openweathermap.org/API#forecast
                            final String FORECAST_BASE_URL =
                                    "http://dalitsobanda.com/node?";
                            final String QUERY_PARAM = "q";
                            final String FORMAT_PARAM = "mode";
                            final String UNITS_PARAM = "units";
                            final String DAYS_PARAM = "cnt";

                            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                                    .appendQueryParameter(QUERY_PARAM, "02139")
                                    .appendQueryParameter(FORMAT_PARAM, format)
                                    .appendQueryParameter(UNITS_PARAM, units)
                                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                                    .build();

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
                                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                                // But it does make debugging a *lot* easier if you print out the completed
                                // buffer for debugging.
                                buffer.append(line + "\n");
                            }

                            if (buffer.length() == 0) {
                                // Stream was empty.  No point in parsing.
                                return null;
                            }
                            forecastJsonStr = buffer.toString();
                        } catch (IOException e) {
                            //Log.e("adfsa", "Error ", e);
                            // If the code didn't successfully get the weather data, there's no point in attemping
                            // to parse it.
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


                        return forecastJsonStr;


                        // This will only happen if there was an error getting or parsing the forecast.
                        //return null;
                    };

                    @Override
                    protected void onPostExecute(String result) {
                        //arrayAdapter.clear();

                        arrayAdapter.add(result);
                        //arrayAdapter.notifyDataSetChanged();
                        //patientList.add(result);
                        Log.e("server", "got server response");
                    // New data is back from the server.  Hooray!
                        }
                }.execute();
                currentView = rootView;

            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                // start patient add activity
                Intent intent = new Intent(HomeActivity.this, NewPatient.class);
//                EditText editText = (EditText) findViewById(R.id.edit_message);
//                String message = editText.getText().toString();
//                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
            return rootView;


        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
