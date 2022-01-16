package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ControlTripAdmin extends AppCompatActivity {
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_trip_admin);
        queue = Volley.newRequestQueue(this);
        populateList();
    }
    public void btnClkAddTrip(View view) {
        Intent intent=new Intent(ControlTripAdmin.this,AddTripAdmin.class);
        startActivity(intent);
    }

    public void populateList() {
        ListView lst = findViewById(R.id.ListTrip);
        String url = "http://10.0.2.2:80/FinalProject/trip.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            int tripid, price;
            String name, description, date;

            Trip[] tripObj;

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> trips = new ArrayList<>();
                tripObj = new Trip[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        trips.add(obj.getString("name"));
                        tripid = obj.getInt("id");
                        name = obj.getString("name");
                        description = obj.getString("description");
                        price = obj.getInt("price");
                        date = obj.getString("date");
                        tripObj[i] = new Trip(tripid, name, description, date, price);

                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                String[] arr = new String[trips.size()];
                arr = trips.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ControlTripAdmin.this, android.R.layout.simple_list_item_1,
                        arr);
                lst.setAdapter(adapter);

                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position != -1) {
                            Gson gson = new Gson();
                            String tripString = gson.toJson(tripObj[position]);
                            Intent intent = new Intent(view.getContext(), DeleteReserveTripAdmin.class);
                            intent.putExtra("trip", tripString);
                            startActivity(intent);
                        }
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ControlTripAdmin.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

}
