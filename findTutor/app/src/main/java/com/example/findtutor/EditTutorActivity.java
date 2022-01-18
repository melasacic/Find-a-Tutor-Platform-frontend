package com.example.findtutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.findtutor.model.Tutors;

import org.json.JSONException;
import org.json.JSONObject;

public class EditTutorActivity extends AppCompatActivity {

    private EditText EditTutorFirstNameEditText;
    private EditText EditTutorLastNameEditText;
    private EditText EditTutorUsernameEditText;
    private EditText EditTutorPasswordEditText;
    private EditText EditTutorEmailEditText;
    private EditText EditTutorCityEditText;
    private EditText EditTutorPhoneNumberEditText;

    private Button EditTutorButton;

    private String currentTutor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor);

        EditTutorFirstNameEditText = findViewById(R.id.EditTutorFirstNameEditText);
        EditTutorLastNameEditText = findViewById(R.id.EditTutorLastNameEditText);
        EditTutorUsernameEditText = findViewById(R.id.EditTutorUsernameEditText);
        EditTutorEmailEditText = findViewById(R.id.EditTutorEmailEditText);
        EditTutorCityEditText = findViewById(R.id.EditTutorCityEditText);
        EditTutorPhoneNumberEditText = findViewById(R.id.EditTutorPhoneNumberEditText);

        EditTutorButton = findViewById(R.id.TutorEditButton);

        Tutors tutors = getIntent().getParcelableExtra("currentTutor");

        EditTutorFirstNameEditText.setText(tutors.getFirstName());
        EditTutorLastNameEditText.setText(tutors.getLastName());
        EditTutorUsernameEditText.setText(tutors.getUsername());
        EditTutorEmailEditText.setText(tutors.getEmail());
        EditTutorCityEditText.setText(tutors.getCity());
        EditTutorPhoneNumberEditText.setText(tutors.getPhoneNumber());


        EditTutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = EditTutorFirstNameEditText.getText().toString();
                String lastName = EditTutorLastNameEditText.getText().toString();
                String username = EditTutorUsernameEditText.getText().toString();
                String email = EditTutorEmailEditText.getText().toString();
                String city = EditTutorCityEditText.getText().toString();
                String phoneNumber = EditTutorPhoneNumberEditText.getText().toString();

                try {
                    RequestQueue requestQueue= Volley.newRequestQueue(EditTutorActivity.this);
                    String URL = "http://192.168.1.14:8080/api/v1/tutor";

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("firstName",  firstName);
                    jsonBody.put("lastName", lastName);
                    jsonBody.put("username", username);
                    jsonBody.put("email", email);
                    jsonBody.put("city", city);
                    jsonBody.put("phoneNumber", phoneNumber);

                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("Response", response.toString());
                                   Intent intent=new Intent(EditTutorActivity.this,GetTutorActivity.class);
                                   startActivity(intent);
                                  // System.out.println(response);

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error.Response", error.toString());
                                }
                            });

                    requestQueue.add(stringRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}



