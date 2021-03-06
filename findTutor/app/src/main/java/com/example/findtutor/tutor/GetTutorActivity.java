package com.example.findtutor.tutor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.example.findtutor.R;
import com.example.findtutor.login.LoginState;
import com.example.findtutor.tutor.adapter.TutorsAdapter;
import com.example.findtutor.tutor.model.Tutors;

import com.example.findtutor.user.EditUserActivity;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GetTutorActivity extends AppCompatActivity {
    // TextView textView;
    ListView listOfTutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tutor);
        listOfTutors = findViewById(R.id.tutorsListView);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(GetTutorActivity.this);
        String url = "http://192.168.1.89:8080/api/v1/tutor";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            //List<Tutors> tutorsInfo=new ArrayList<>();

            Gson gson = new Gson();
            Type type = new TypeToken<List<Tutors>>() {
            }.getType();
            List<Tutors> tutorsInfo = gson.fromJson(String.valueOf(response), type);

            TutorsAdapter<Tutors> adapter = new TutorsAdapter<Tutors>(GetTutorActivity.this, (ArrayList<Tutors>) tutorsInfo);
            ListView listView = (ListView) findViewById(R.id.tutorsListView);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // List<Tutors> currentTutor = tutorsInfo.getItemAtPosition(position);

                    Tutors currentTutor = tutorsInfo.get(position);
                    Intent intent = new Intent(GetTutorActivity.this, TutorDetailsActivity.class);
                    intent.putExtra("currentTutor", (Parcelable) currentTutor);
                    startActivity(intent);
                }
            });
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("LOG_VOLLEY", error.toString());
                System.out.println("error");
            }
        });

        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_menu_button:
                if("user".equals(LoginState.userType)){
                    Intent intent = new Intent(GetTutorActivity.this, EditUserActivity.class);
                    startActivity(intent);
                }
                if("tutor".equals(LoginState.userType)){
                    Intent intent = new Intent(GetTutorActivity.this, EditUserActivity.class);
                    startActivity(intent);
                }

                return true;
        }

        return true;
    }
}