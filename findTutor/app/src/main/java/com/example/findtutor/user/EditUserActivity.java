package com.example.findtutor.user;

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
import com.example.findtutor.tutor.GetTutorActivity;
import com.example.findtutor.tutor.model.Tutors;
import com.example.findtutor.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class EditUserActivity extends AppCompatActivity {

    private EditText EditUserFirstNameEditText;
    private EditText EditUserLastNameEditText;
    private EditText EditUserUsernameEditText;
    private EditText EditUserEmailEditText;
    private EditText EditUserCityEditText;
    private EditText EditUserPhoneNumberEditText;
    private Button EditUserButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        EditUserFirstNameEditText = findViewById(R.id.EditUserFirstNameEditText);
        EditUserLastNameEditText = findViewById(R.id.EditUserLastNameEditText);
        EditUserUsernameEditText = findViewById(R.id.EditUserUsernameEditText);
        EditUserEmailEditText = findViewById(R.id.EditUserEmailEditText);
        EditUserCityEditText = findViewById(R.id.EditUserCityEditText);
        EditUserPhoneNumberEditText = findViewById(R.id.EditUserPhoneNumberEditText);

        EditUserButton = findViewById(R.id.UserEditButton);

        User user = getIntent().getParcelableExtra("currentUser");

        EditUserFirstNameEditText.setText(user.getFirstName());
        EditUserLastNameEditText.setText(user.getLastName());
        EditUserUsernameEditText.setText(user.getUsername());
        EditUserEmailEditText.setText(user.getEmail());
        EditUserCityEditText.setText(user.getCity());
        EditUserPhoneNumberEditText.setText(user.getPhoneNumber());


        EditUserButton.setOnClickListener(v -> {
            String firstName = EditUserFirstNameEditText.getText().toString();
            String lastName = EditUserLastNameEditText.getText().toString();
            String username = EditUserUsernameEditText.getText().toString();
            String email = EditUserEmailEditText.getText().toString();
            String city = EditUserCityEditText.getText().toString();
            String phoneNumber = EditUserPhoneNumberEditText.getText().toString();

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(EditUserActivity.this);
                String URL = "http://192.168.124.194:8080/api/v1/user/" + user.id;

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("firstName", firstName);
                jsonBody.put("lastName", lastName);
                jsonBody.put("username", username);
                jsonBody.put("email", email);
                jsonBody.put("city", city);
                jsonBody.put("phoneNumber", phoneNumber);

                final String mRequestBody = jsonBody.toString();


                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, URL,
                        response -> {
                            Intent intent = new Intent(EditUserActivity.this, GetUserActivity.class);
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
        });
    }
}



