package com.example.findtutor;

import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.example.findtutor.adapter.TutorsAdapter;
import com.example.findtutor.model.Tutors;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class GetTutorActivity extends AppCompatActivity {
    TextView textView;
    ListView listOfTutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tutor);

        textView = findViewById(R.id.text);

        listOfTutors=findViewById(R.id.tutorsListView);

       RequestQueue queue=Volley.newRequestQueue(GetTutorActivity.this);
       String url= "http://192.168.1.14:8080/api/v1/tutor";

       JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {

               //List<Tutors> tutorsInfo=new ArrayList<>();

               Gson gson = new Gson();
               Type type = new TypeToken<List<Tutors>>(){}.getType();
               List<Tutors> tutorsInfo = gson.fromJson(String.valueOf(response), type);
               for (Tutors tutor : tutorsInfo){
                   Log.i("Tutor Details", tutor.getFirstName() + "-" + tutor.getLastName());
               }

               tutorsInfo=Tutors.getTutors();
               TutorsAdapter<Tutors> adapter = new TutorsAdapter<Tutors>( GetTutorActivity.this, (ArrayList<Tutors>) tutorsInfo);
               ListView listView = (ListView) findViewById(R.id.tutorsListView);

               listView.setAdapter(adapter);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       });


    }
}