package com.example.findtutor.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.findtutor.R;
import com.example.findtutor.tutor.EditTutorActivity;
import com.example.findtutor.tutor.adapter.TutorsAdapter;
import com.example.findtutor.tutor.model.Tutors;
import com.example.findtutor.user.adapter.UserAdapter;
import com.example.findtutor.user.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GetUserActivity extends AppCompatActivity {
    // TextView textView;
    ListView listOfUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user);

        listOfUsers = findViewById(R.id.usersListView);

        RequestQueue queue = Volley.newRequestQueue(GetUserActivity.this);
        String url = "http://192.168.1.89:8080/api/v1/user";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Gson gson = new Gson();
            Type type = new TypeToken<List<User>>() {
            }.getType();
            List<User> users = gson.fromJson(String.valueOf(response), type);

            UserAdapter<User> adapter = new UserAdapter<User>(GetUserActivity.this, (ArrayList<User>) users);
            ListView listView = (ListView) findViewById(R.id.usersListView);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {

                User currentUser = users.get(position);
                Intent intent = new Intent(GetUserActivity.this, EditUserActivity.class);
                intent.putExtra("currentUser", (Parcelable) currentUser);
                startActivity(intent);
            });
        }, error -> {
            //Log.e("LOG_VOLLEY", error.toString());
            System.out.println("error");
        });

        queue.add(request);
    }


}