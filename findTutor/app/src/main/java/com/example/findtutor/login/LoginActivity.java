package com.example.findtutor.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.findtutor.R;
import com.example.findtutor.tutor.EditTutorActivity;
import com.example.findtutor.tutor.GetTutorActivity;
import com.example.findtutor.tutor.TutorDetailsActivity;
import com.example.findtutor.tutor.adapter.TutorsAdapter;
import com.example.findtutor.tutor.model.Tutors;
import com.example.findtutor.user.EditUserActivity;
import com.example.findtutor.user.GetUserActivity;
import com.example.findtutor.user.RegisterUserActivity;
import com.example.findtutor.user.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);

        String loginType = getIntent().getStringExtra("loginType");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                String URL = "http://192.168.124.194:8080/api/v1/" + loginType + "/token";

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("email", email);
                jsonBody.put("password", password);

                final String mRequestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        response -> {
                            LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);
                            LoginState.token = loginResponse.getToken();
                            LoginState.userType = loginType;

                            if ("user".equals(loginType)) {
                                getUserDetails();
                            } else if ("tutor".equals(loginType)) {
                                getTutorDetails();
                            } else {
                                throw new RuntimeException("Invalid login type");
                            }
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
                            responseString = new String(response.data, StandardCharsets.UTF_8);
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

    private void getTutorDetails() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            String URL = "http://192.168.124.194:8080/api/v1/tutor/details";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                    response -> {
                        Tutors tutor = new Gson().fromJson(response, Tutors.class);
                        Intent intent = new Intent(LoginActivity.this, TutorDetailsActivity.class);
                        LoginState.tutor = tutor;
                        intent.putExtra("currentTutor", tutor);
                        startActivity(intent);
                    },
                    error -> Log.e("LOG_VOLLEY", error.toString())) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = new String(response.data, StandardCharsets.UTF_8);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", LoginState.token);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserDetails() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            String URL = "http://192.168.124.194:8080/api/v1/user/details";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                    response -> {
                        User user = new Gson().fromJson(response, User.class);
                        LoginState.user = user;
                        Intent intent = new Intent(LoginActivity.this, GetTutorActivity.class);
                        startActivity(intent);
                    },
                    error -> Log.e("LOG_VOLLEY", error.toString())) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = new String(response.data, StandardCharsets.UTF_8);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", LoginState.token);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
