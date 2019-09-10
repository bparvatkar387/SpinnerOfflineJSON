package com.example.spinnerofflinejson;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    //Declaring an Spinner
    private Spinner spnState, spnDistrict;
    private Button getHospitals;

    //An ArrayList for Spinner Items
    private ArrayList<String> states;
    private ArrayList<String> arrayDistricts;


    //JSON Array
    private JSONArray result;
    JSONObject j = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the ArrayList
        states = new ArrayList<String>();
        arrayDistricts = new ArrayList<String>();

        getHospitals = findViewById(R.id.getHospitals);
        getHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = spnState.getSelectedItem().toString();
                String district = spnDistrict.getSelectedItem().toString();
                String query = "SELECT * FROM HOSPITALS WHERE STATE LIKE " + state + " AND DISTRICT LIKE " + district;
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
            }
        });

        //Initializing Spinner
        spnState = (Spinner) findViewById(R.id.spinner);
        spnDistrict = (Spinner) findViewById(R.id.spinner2);
        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jDistricts = result.getJSONObject(i);
                    System.out.println(jDistricts);
                    JSONArray districts = jDistricts.getJSONArray("districts");
                    arrayDistricts.clear();
                    for (int j=0; j < districts.length(); j++){

                        arrayDistricts.add(districts.getString(j));
                    }
                    spnDistrict.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayDistricts));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        try {
            JSONObject objStates = new JSONObject(OpenJSON.readJSONFromAsset(this, "states-and-districts.json"));
            result = objStates.getJSONArray("states");
            getStates(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getStates(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                states.add(json.getString("state"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spnState
        spnState.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, states));
    }




}