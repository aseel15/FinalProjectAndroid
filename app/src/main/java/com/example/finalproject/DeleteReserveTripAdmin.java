package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.model.Trip;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleteReserveTripAdmin extends AppCompatActivity {
    private TextView edtName,edtDate,edtDesc,edtPrice,edtAdduser;
    private EditText edtNumber,edtID;
    private String tripData;
    private Trip tripObj;
    private int user_id,tripID,totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_reserve_trip_admin);
        edtName=findViewById(R.id.name);
        edtDate=findViewById(R.id.date);
        edtDesc=findViewById(R.id.description);
        edtPrice=findViewById(R.id.price);
        edtNumber=findViewById(R.id.Number);
        edtID=findViewById(R.id.UserID);
        edtAdduser=findViewById(R.id.addUser);
       if (savedInstanceState!=null){
            onRestoreInstanceState(savedInstanceState);
        }
       else{
        Intent intent=getIntent();
        tripData=intent.getStringExtra("trip");
        Gson gson = new Gson();
        tripObj = gson.fromJson(tripData, Trip.class);
        tripID=tripObj.getId();
        fillTripData();
       }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("Name",edtName.getText().toString());
        outState.putString("Date",edtDate.getText().toString());
        outState.putString("Desc",edtDesc.getText().toString());
        outState.putString("price",edtPrice.getText().toString());
        outState.putString("AddUser",edtAdduser.getText().toString());
        outState.putString("ID",edtID.getText().toString());
        outState.putString("Number",edtNumber.getText().toString());

        outState.putString("tripData",tripData);
        outState.putInt("user_id",user_id);
        outState.putInt("tripID",tripID);
        outState.putInt("totalPrice",totalPrice);



        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        edtName.setText(savedInstanceState.getString("Name"));
        edtID.setText(savedInstanceState.getString("ID"));
        edtDate.setText(savedInstanceState.getString("Date"));
        edtDesc.setText(savedInstanceState.getString("Desc"));
        edtPrice.setText(savedInstanceState.getString("price"));
        edtAdduser.setText(savedInstanceState.getString("AddUser"));
        edtNumber.setText(savedInstanceState.getString("Number"));
        tripData=savedInstanceState.getString("Data");
        user_id=savedInstanceState.getInt("user_id");
        tripID=savedInstanceState.getInt("tripID");
        totalPrice=savedInstanceState.getInt("totalPrice");

    }
    public void fillTripData(){

        edtName.setText(tripObj.getName());
        edtDate.setText("Date of trip: "+tripObj.getDate());
        edtDesc.setText("About: \n\n"+tripObj.getDescription());
        edtPrice.setText("Price of trip: "+String.valueOf(tripObj.getPrice()));
    }

    public void btnClkDeleteTrip(View view) {
        Delete("Trip");
        Intent intent=new Intent(DeleteReserveTripAdmin.this,ControlTripAdmin.class);
        startActivity(intent);
    }

    public void btnClkDeletePerson(View view) {
        user_id=Integer.parseInt(edtID.getText().toString());
        if (edtID.getText().toString().isEmpty())
            Toast.makeText(DeleteReserveTripAdmin.this,"Please fill empty field",
                    Toast.LENGTH_SHORT).show();
        else
            checkReserved("Delete");
    }

    public void btnClkReservePerson(View view) {

       if(edtID.getText().toString().isEmpty())
        Toast.makeText(DeleteReserveTripAdmin.this,"Please fill Id field",
                Toast.LENGTH_SHORT).show();
       else if (edtNumber.getText().toString().isEmpty())
            Toast.makeText(DeleteReserveTripAdmin.this,"Please fill Number of people field",
                    Toast.LENGTH_SHORT).show();
        else{
            int Number=Integer.parseInt(edtNumber.getText().toString());
            totalPrice=Number*tripObj.getPrice();
            user_id=Integer.parseInt(edtID.getText().toString());
            SearchUser();

        }
    }



    public void SearchUser(){
        String url = "http://10.0.2.2:80/FinalProject/Search.php?user_id="+user_id;
        RequestQueue queue = Volley.newRequestQueue(DeleteReserveTripAdmin.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").equals("true")){
                        Toast.makeText(DeleteReserveTripAdmin.this,
                                "No User has id: "+user_id+", add him please", Toast.LENGTH_SHORT).show();
                        edtAdduser.setVisibility(View.VISIBLE);
                    }

                    else {
                        checkReserved("Reserve");
                    }


                }   catch (JSONException e) {
                    e.printStackTrace();
                }

            }} ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleteReserveTripAdmin.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();}});

        queue.add(request);
    }



    public void checkReserved(String Type){

        String url = "http://10.0.2.2:80/FinalProject/SearchReservedTrip.php?user_id="+user_id+"&tripID="+tripID;
        RequestQueue queue = Volley.newRequestQueue(DeleteReserveTripAdmin.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("reserved").equals("true")){

                        if (Type.equals("Delete")){
                            Delete("User");
                        }
                        else {
                        Toast.makeText(DeleteReserveTripAdmin.this,
                                "User has already reserved in this trip", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        if (Type.equals("Reserve")){
                            addTrip();
                        }
                        else{
                            Toast.makeText(DeleteReserveTripAdmin.this,
                                    "User hasn't been reserved", Toast.LENGTH_SHORT).show();
                        }
                        }


                }   catch (JSONException e) {
                    e.printStackTrace();
                }

            }} ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleteReserveTripAdmin.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();}});

        queue.add(request);
    }





    private void addTrip() {
        String url = "http://10.0.2.2:80/FinalProject/addTrip.php";
        RequestQueue queue = Volley.newRequestQueue(DeleteReserveTripAdmin.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(DeleteReserveTripAdmin.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleteReserveTripAdmin.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(user_id));
                params.put("tripID", String.valueOf(tripID));
                params.put("totalprice", String.valueOf(totalPrice));


                return params;
            }
        };
        queue.add(request);
    }


    public void Delete(String Type){
        String url = "http://10.0.2.2:80/FinalProject/deleteTrip.php";
        RequestQueue queue = Volley.newRequestQueue(DeleteReserveTripAdmin.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(DeleteReserveTripAdmin.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleteReserveTripAdmin.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
               return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                if (Type.equals("User")){
                params.put("user_id", String.valueOf(user_id));
                }
                params.put("tripID", String.valueOf(tripID));

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }


    public void clkAddUser(View view) {
        Intent intent=new Intent(DeleteReserveTripAdmin.this,addPersonAdmin.class);
        startActivity(intent);
    }
}