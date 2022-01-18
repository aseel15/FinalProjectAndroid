package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTripAdmin extends AppCompatActivity {

    private EditText edtTripName, edtPrice, edtDescription;
    private TextView edtDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_admin);
        edtTripName = findViewById(R.id.TripName);
        edtDate = findViewById(R.id.date_picker_actions);
        edtPrice = findViewById(R.id.Price);
        edtDescription=findViewById(R.id.Description);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =Calendar.getInstance();
                final int year=cal.get(Calendar.YEAR);
                final int month=cal.get(Calendar.MONTH);
                final int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(AddTripAdmin.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calendar newDate = Calendar.getInstance();
                        DateFormat DFormat = DateFormat.getDateInstance();

                        newDate.set(year, month, day);
                        edtDate.setText(DFormat.format(newDate.getTime()));
                    }

                },  cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }


    public void btnClkAddTrip(View view) {
        String TripName = edtTripName.getText().toString();
        String date = edtDate.getText().toString();
        String Description=edtDescription.getText().toString();

        if (TripName.isEmpty())
            Toast.makeText(AddTripAdmin.this, ("Please fill Trip Name field"), Toast.LENGTH_SHORT).show();

        else if (date.isEmpty())
            Toast.makeText(AddTripAdmin.this, ("Please fill Date field"), Toast.LENGTH_SHORT).show();

        else if (edtPrice.getText().toString().isEmpty())
            Toast.makeText(AddTripAdmin.this, ("Please fill Price field"), Toast.LENGTH_SHORT).show();

        else if (Description.isEmpty())
            Toast.makeText(AddTripAdmin.this, ("Please fill Price field"), Toast.LENGTH_SHORT).show();

        else {
            int Price = Integer.parseInt(edtPrice.getText().toString());
            addTrip(TripName, date, Price, Description);}
    }

    private void addTrip(String TripName, String date, int Price,String Description) {
        String url = "http://10.0.2.2:80/FinalProject/addTrip.php";
        RequestQueue queue = Volley.newRequestQueue(AddTripAdmin.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(AddTripAdmin.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddTripAdmin.this,
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

                params.put("name", TripName);
                params.put("date", date);
                params.put("price", String.valueOf(Price));
                params.put("description", Description);


                return params;
            }
        };
        queue.add(request);
    }


}