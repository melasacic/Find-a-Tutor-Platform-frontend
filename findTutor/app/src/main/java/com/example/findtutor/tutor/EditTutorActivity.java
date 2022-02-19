package com.example.findtutor.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.findtutor.R;
import com.example.findtutor.login.LoginActivity;
import com.example.findtutor.login.LoginState;
import com.example.findtutor.tutor.model.Tutors;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class EditTutorActivity extends AppCompatActivity {

    private EditText EditTutorFirstNameEditText;
    private EditText EditTutorLastNameEditText;
    private EditText EditTutorUsernameEditText;
    private EditText EditTutorPasswordEditText;
    private EditText EditTutorEmailEditText;
    private EditText EditTutorCityEditText;
    private EditText EditTutorPhoneNumberEditText;
    private EditText EditTutorInstructionsTypeEditText;

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
        EditTutorInstructionsTypeEditText = findViewById(R.id.EditTutorInstructionsTypeEditText);

        EditTutorButton = findViewById(R.id.TutorEditButton);

        Tutors tutors = LoginState.tutor;

        EditTutorFirstNameEditText.setText(tutors.getFirstName());
        EditTutorLastNameEditText.setText(tutors.getLastName());
        EditTutorUsernameEditText.setText(tutors.getUsername());
        EditTutorEmailEditText.setText(tutors.getEmail());
        EditTutorCityEditText.setText(tutors.getCity());
        EditTutorPhoneNumberEditText.setText(tutors.getPhoneNumber());
        EditTutorInstructionsTypeEditText.setText(tutors.getInstructionsType());


        EditTutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = EditTutorFirstNameEditText.getText().toString();
                tutors.setFirstName(firstName);
                String lastName = EditTutorLastNameEditText.getText().toString();
                tutors.setLastName(lastName);
                String username = EditTutorUsernameEditText.getText().toString();
                tutors.setUsername(username);
                String email = EditTutorEmailEditText.getText().toString();
                tutors.setEmail(email);
                String city = EditTutorCityEditText.getText().toString();
                tutors.setCity(city);
                String phoneNumber = EditTutorPhoneNumberEditText.getText().toString();
                tutors.setPhoneNumber(phoneNumber);
                String instructionsType =  EditTutorInstructionsTypeEditText.getText().toString();
                tutors.setInstructionsType(instructionsType);

                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(EditTutorActivity.this);
                    String URL = "http://192.168.1.89:8080/api/v1/tutor/" + tutors.id;

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("firstName", firstName);
                    jsonBody.put("lastName", lastName);
                    jsonBody.put("username", username);
                    jsonBody.put("email", email);
                    jsonBody.put("city", city);
                    jsonBody.put("phoneNumber", phoneNumber);
                    jsonBody.put("instructionsType", instructionsType);

                    final String mRequestBody = jsonBody.toString();


                    StringRequest stringRequest = new StringRequest(Request.Method.PATCH, URL,
                            response -> {
                                LoginState.tutor = tutors;
                                Intent intent = new Intent(EditTutorActivity.this, TutorDetailsActivity.class);
                                intent.putExtra("currentTutor", tutors);
                                startActivity(intent);
                            },
                            error -> Log.e("LOG_VOLLEY", error.toString())) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        mRequestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<>();
                            params.put("Authorization", LoginState.token);
                            return params;
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {

                                responseString = String.valueOf(response.statusCode);

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}



