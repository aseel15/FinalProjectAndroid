package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivityEm extends AppCompatActivity {

    private RequestQueue queue;
    private static final String BASE_URL = "http://10.0.2.2:80/RoomDataBase/getUserId.php";
    String dateCheckIn;
    String dateCheckOut;
    List<Room> roomsList;
    int roomNumber;
    TextView textTry;
    ArrayList<Integer>userIds;
    int days;
    EditText edtUserId;
    int idAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_em);


        queue=Volley.newRequestQueue(this);
        userIds=new ArrayList<>();
        Intent intent = getIntent();
        textTry=findViewById(R.id.txtTryEm);
        edtUserId=findViewById(R.id.edtUserId);
        String item = intent.getStringExtra("roomNum");
        dateCheckIn=intent.getStringExtra("checkInDate");
        dateCheckOut=intent.getStringExtra("checkOutDate");
        roomNumber = Integer.parseInt(item);
        roomsList=(List<Room>) getIntent().getSerializableExtra("arrayList");
        populateData(roomNumber);
        populateUser();
    }
    public Room getRoomObject(int objectNumber){
        for(int i=0;i<roomsList.size();i++)
            if(objectNumber==roomsList.get(i).getId())
                return roomsList.get(i);
        return null;
    }
    public void populateData(int roomNum){

        Room room=getRoomObject(roomNum);
        ListView listView=findViewById(R.id.listViewEm);
        ArrayList<String> arr=new ArrayList<>();
        arr.add("Room Number : "+room.getId());
        arr.add("Room Type : "+room.getRoomType());
        arr.add("Price : "+room.getPrice()+"$");
        arr.add("Room Size : "+room.getRoomSize());
        arr.add("Bed Type : "+room.getBedType());
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        listView.setAdapter(arrayAdapter);


    }
    public Date formatDate(String date){
        String[]split=date.split("-");
        String readyFormat="";
        for(int i=split.length-1;i>=0;i--){
            if(split[i].length()==1)
                split[i]="0"+split[i];

            readyFormat+=split[i];
            if(i!=0)
                readyFormat+="-";
        }
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date d1=null;
        try {
            d1 = sdf.parse(readyFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d1;


    }
    public int calculateDays(){
        Date dateIn=formatDate(dateCheckIn);
        Date dateOut=formatDate(dateCheckOut);
        Long date1InMs = dateIn.getTime();
        int date1Min=date1InMs.intValue();
        Long date2InMs = dateOut.getTime();
        int date2Min=date2InMs.intValue();
        int timeDif=date2Min-date1Min;
        int day= (int) (timeDif / (1000 * 60 * 60* 24));
        return day;
    }
    public void postData(){
        String url="http://10.0.2.2:80/FinalProject/reserveRoom.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       days=calculateDays();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textTry.setText(error.getMessage());
                Toast.makeText(DetailActivityEm.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType(){
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Room room=getRoomObject(roomNumber);
                Map<String, String> params = new HashMap<>();

                params.put("roomsID", roomNumber+"");
                //by shared preference
                params.put("userId", idAdded+"");
                params.put("check_In", dateCheckIn);
                params.put("check_Out",dateCheckOut);
                params.put("totalPrice",(days*room.getPrice())+"");

                return params;
            }
        };
        queue.add(request);
    }



    public void btnReserveOnClick(View view) {
        if(dateCheckIn==null||dateCheckOut==null){
            Toast.makeText(DetailActivityEm.this,
                    "You should enter check in & check out date", Toast.LENGTH_SHORT).show();
        }
        else{

            //call method to post it to database
            postData();
        }
    }
    public void populateUser(){

        StringRequest request=new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray userIdList=new JSONArray(response);
                            for(int i=0;i< userIdList.length();i++){
                                JSONObject jsonObject= userIdList.getJSONObject(i);
                                int user= jsonObject.getInt("id");
                                userIds.add(user);
                            }
                           // populateUserToList();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivityEm.this, error.toString(),
                        Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }
    public ArrayList<Integer>populateUserToList(){
        return userIds;
    }


    public void readText(){

    }
    public void btnEnterOnClick(View view) {


        String s = String.valueOf(edtUserId.getText());
        int uId=Integer.parseInt(s);
        boolean flag=false;
        for(int i=0;i<userIds.size();i++){
            if(uId== userIds.get(i)){
                idAdded=uId;
                flag=true;
                textTry.setText("true");
                break;
            }
        }
        if(!flag){
            textTry.setText("false");
            //add user
        }


    }
}