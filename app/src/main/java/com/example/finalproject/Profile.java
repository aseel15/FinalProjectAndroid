package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    SharedPreferences preferences;
    private int user_id,totalPayment=0;
    private TextView edtUserID, edtUserName,edtRooms,edtParties,edtTrips,edtPayment,edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferences=getSharedPreferences("session",MODE_PRIVATE);
        user_id=preferences.getInt("login",-1);
        edtUserName=findViewById(R.id.UserName);
        edtUserID=findViewById(R.id.UserId);
        edtRooms=findViewById(R.id.GivenRooms);
        edtParties=findViewById(R.id.GivenParties);
        edtPayment=findViewById(R.id.Payments);
        edtTrips=findViewById(R.id.GivenTrips);
        edtEmail=findViewById(R.id.Email);

        edtUserID.setText("ID: "+ String.valueOf(user_id));
        GetPersonalInfo();
        getData();


    }

    public void GetPersonalInfo(){
        String url = "http://10.0.2.2:80/FinalProject/Search.php?user_id="+user_id;
        RequestQueue queue = Volley.newRequestQueue(Profile.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("error").equals("false")){

                        edtUserName.setText("Name: "+jsonObject.getString("username"));
                        edtEmail.setText("Email: "+jsonObject.getString("email"));

                }


                }   catch (JSONException e) {
                    e.printStackTrace();
                }

            }} ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();}});

        queue.add(request);

        }



    public void btnClkDeleteAccount(View view) {
        String url = "http://10.0.2.2:80/FinalProject/AddUsers.php";
        RequestQueue queue = Volley.newRequestQueue(Profile.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Profile.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    SharedPreferences remember = getSharedPreferences("checkBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = remember.edit();
                    editor.putString("remember", "false");
                    editor.apply();

                    SharedPreferences.Editor editor2 = preferences.edit();
                    editor2.putInt("login", -1);
                    editor2.apply();

                    Intent intent =new Intent(Profile.this,SignIn.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this,
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
                return params;
            }
        };
        queue.add(request);
    }

    public void getData(){
        String url = "http://10.0.2.2:80/FinalProject/profile.php?user_id="+user_id;
        RequestQueue queue = Volley.newRequestQueue(Profile.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getString("tripName").equals("false")){

                        edtTrips.setText(jsonObject.getString("tripName"));
                        totalPayment+=Integer.parseInt(jsonObject.getString("tripPrice"));
                    }else
                        edtTrips.setText("There is not reserved trips");

                    Toast.makeText(Profile.this,jsonObject.getString("roomName"),Toast.LENGTH_SHORT).show();
                    if (!jsonObject.getString("roomName").equals("false")){
                        edtRooms.setText(jsonObject.getString("roomName"));
                        totalPayment+=Integer.parseInt(jsonObject.getString("roomPrice"));
                    }else
                        edtRooms.setText("There is no reserved rooms");


                    if (!jsonObject.getString("servicePrice").equals("false")){
                        totalPayment+=Integer.parseInt(jsonObject.getString("servicePrice"));}


//                    if (!jsonObject.getString("partyName").equals("false")){
//                        edtParties.setText(jsonObject.getString("partyName"));
//                        totalPayment+=Integer.parseInt(jsonObject.getString("partyPrice"));
//                    }else
//                        edtRooms.setText("There is no reserved parties");


                    edtPayment.setText(String.valueOf(totalPayment));
                }   catch (JSONException e) {
                    e.printStackTrace();
                }

            }} ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();}});

        queue.add(request);

    }
}