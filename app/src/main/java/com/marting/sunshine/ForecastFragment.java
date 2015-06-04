package com.marting.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Martin on 07/05/2015.
 */
public class ForecastFragment extends Fragment {

    ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecastfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id  = item.getItemId();
        if(id == R.id.action_refresh){
            Toast.makeText(getActivity(),"refresh has been clicked", Toast.LENGTH_SHORT).show();
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather(){
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);
        //getting the shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

        fetchWeatherTask.execute(location);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //an array list which contains dummy weather data

            String[] forecastArray = {

             };
             List<String> weekForecast = new ArrayList<String> ( Arrays.asList(forecastArray));


        //the above commented out code does the same thing as this one
//        ArrayList<String> weekForecast = new ArrayList<String>();
//        weekForecast.add("Today-sunny-88/63");
//        weekForecast.add("Tomorrow-Foggy-70/46");
//        weekForecast.add("Weds-Cloudy-72/63");
//        weekForecast.add("Thurs-Rainy-64/51");
//        weekForecast.add("Fri-Foggy-70/46");
//        weekForecast.add("Sat-Sunny-76/68");

        /**
         * the arrayAdapter will take the data from the source and
         * use it to populate the ListView its attached to
         */

            mForecastAdapter = new ArrayAdapter<String>(
                //current context, this fragment's parent activity
                getActivity(),
                //id of the list item layout
                R.layout.list_item_forecast,
                //id of the list view item to populate
                R.id.list_item_forecast_textview,
                //week forecast data
//                weekForecast
                    new ArrayList<String>()
            );
        //create the listView
        final ListView forecastView = (ListView) rootView.findViewById(R.id.listView_forecast);
        //then attach the adapter to the created listView item
        forecastView.setAdapter(mForecastAdapter);

        forecastView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String forecast = mForecastAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("item_clicked", forecast);
                startActivity(intent);
                Toast.makeText(getActivity(), "item clicked " + forecast , Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "item clicked " + forecastView.getItemAtPosition(i) , Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
