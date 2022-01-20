package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class addPersonAdmin extends AppCompatActivity {

    private EditText edtUserName, edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        edtUserName = findViewById(R.id.UserName);
        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtPassword = findViewById(R.id.Password);
    }



    public void btnClkRegister(View view) {
        String UserName = edtUserName.getText().toString();
        String Email = edtEmail.getText().toString();
        String Password = edtPassword.getText().toString();

        if (UserName.isEmpty())
            Toast.makeText(addPersonAdmin.this, ("Please fill username field"), Toast.LENGTH_SHORT).show();

        else if (Email.isEmpty())
            Toast.makeText(addPersonAdmin.this, ("Please fill email field"), Toast.LENGTH_SHORT).show();

        else if (Password.isEmpty())
            Toast.makeText(addPersonAdmin.this, ("Please fill password field"), Toast.LENGTH_SHORT).show();

        else addPerson(UserName, Email, Password);
    }

    private void addPerson(String UserName, String Email, String Password) {
        String url = "http://10.0.2.2:80/FinalProject/AddUsers.php";
        RequestQueue queue = Volley.newRequestQueue(addPersonAdmin.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(addPersonAdmin.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addPersonAdmin.this,
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

                params.put("username", UserName);
                params.put("email", Email);
                params.put("password", Password);

                return params;
            }
        };
        queue.add(request);
    }


}