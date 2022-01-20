package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SignIn extends AppCompatActivity {
    private EditText  edtPassword ,edtEmail;
    private CheckBox edtRemember;
    private int id;
    private int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtEmail=findViewById(R.id.Email);
        edtPassword=findViewById(R.id.Password);
        edtRemember=findViewById(R.id.checkBox1);


    }


    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences preferences=getSharedPreferences("session",MODE_PRIVATE);
        int session=preferences.getInt("login",-1);
//        if (session!=-1){
//            Intent intent=new Intent(SignIn.this,Registration.class);
//            startActivity(intent);
//        }
         {
            SharedPreferences remember = getSharedPreferences("checkBox", MODE_PRIVATE);
            String rem = remember.getString("remember", "");
            if (rem.equals("true")) {
                String email = remember.getString("Email", "");
                String pass = remember.getString("password", "");
                edtEmail.setText(email);
                edtPassword.setText(pass);

            }
        }

    }


    public void btnClkForRegister(View view) {
        Intent intent =new Intent(SignIn.this,Registration.class);
        startActivity(intent);

    }




    public void btnClkLogin(View view) {
       String email = edtEmail.getText().toString();
       String pass= edtPassword.getText().toString();

        if (email.isEmpty())
            Toast.makeText(SignIn.this,("Please fill email field"), Toast.LENGTH_SHORT).show();

        else if (pass.isEmpty())
            Toast.makeText(SignIn.this,("Please fill password field"), Toast.LENGTH_SHORT).show();

        else {
            String url = "http://10.0.2.2:80/FinalProject/Search.php?email="+email+"&password="+pass;
            RequestQueue queue = Volley.newRequestQueue(SignIn.this);
            StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").equals("true"))
                        Toast.makeText(SignIn.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    else{
                        id=Integer.parseInt(jsonObject.getString("id"));
                        role=Integer.parseInt(jsonObject.getString("role"));

                        if (edtRemember.isChecked()){
                            SharedPreferences remember = getSharedPreferences("checkBox",MODE_PRIVATE);
                            SharedPreferences.Editor editor = remember.edit();
                            String rem=remember.getString("remember","");
                            if (!rem.equals("true")){
                                editor.putString("remember","true");
                                editor.putString("Email", email);
                                editor.putString("password",pass);
                                editor.apply();}
                        }
                        else{
                            SharedPreferences remember = getSharedPreferences("checkBox",MODE_PRIVATE);
                            SharedPreferences.Editor editor = remember.edit();
                            editor.putString("remember", "false");
                            editor.apply();
                        }

                        SharedPreferences session = getSharedPreferences("session",MODE_PRIVATE);
                        SharedPreferences.Editor editor = session.edit();
                        editor.putInt("login", id);
                        editor.apply();

                        if (role==0){
                            Intent intent=new Intent(SignIn.this,Registration.class);
                            startActivity(intent);
                            Toast.makeText(SignIn.this,
                                    jsonObject.getString("id"), Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(SignIn.this,
                                    "you are admin", Toast.LENGTH_SHORT).show();

                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }} ,new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {Toast.makeText(SignIn.this,
                    "Fail to get response = " + error, Toast.LENGTH_SHORT).show();}});

           queue.add(request);



    }



    }

    public void btnClkSentEmail(View view) {
        String email = edtEmail.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(SignIn.this,
                    "Please fill email field", Toast.LENGTH_SHORT).show();
        }
        else{
            String url = "http://10.0.2.2:80/FinalProject/SearchPassword.php?email="+email;
            RequestQueue queue = Volley.newRequestQueue(SignIn.this);
            StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("error").equals("true"))
                            Toast.makeText(SignIn.this,
                                    jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        else{
                            String realPassword;
                            realPassword=jsonObject.getString("password");
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_EMAIL,email);
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Your password");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, realPassword);
                            sendIntent.setType("message/rfc822");

                            Intent shareIntent = Intent.createChooser(sendIntent, "choose an email client");
                            startActivity(shareIntent);
                          }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }} ,new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {Toast.makeText(SignIn.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();}});

            queue.add(request);


        }
    }

}