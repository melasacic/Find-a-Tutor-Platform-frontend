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
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.findtutor.R;
import com.example.findtutor.login.LoginActivity;
import com.example.findtutor.tutor.GetTutorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class RegisterUserActivity extends AppCompatActivity {

    private EditText userFirstNameEditText;
    private EditText userLastNameEditText;
    private EditText userUsernameEditText;
    private EditText userPasswordEditText;
    private EditText userEmailEditText;
    private EditText userCityEditText;
    private EditText userPhoneNumberEditText;

    private Button registerNewUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // assign values to each control on the layout
        userFirstNameEditText =findViewById(R.id.UserFirstNameEditText);
        userLastNameEditText =findViewById(R.id.UserLastNameEditText);
        userUsernameEditText =findViewById(R.id.UserUsernameEditText);
        userPasswordEditText =findViewById(R.id.UserPasswordEditText);
        userEmailEditText =findViewById(R.id.UserEmailEditText);
        userCityEditText =findViewById(R.id.UserCityEditText);
        userPhoneNumberEditText =findViewById(R.id.UserPhoneNumberEditText);

        registerNewUserButton =findViewById(R.id.UserRegisterButton);

        registerNewUserButton.setOnClickListener(view -> {
            String firstName= userFirstNameEditText.getText().toString();
            String lastName= userLastNameEditText.getText().toString();
            String username= userUsernameEditText.getText().toString();
            String password= userPasswordEditText.getText().toString();
            String email= userEmailEditText.getText().toString();
            String city= userCityEditText.getText().toString();
            String phoneNumber= userPhoneNumberEditText.getText().toString();

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(RegisterUserActivity.this);
                String URL = "http://192.168.1.89:8080/api/v1/user";

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("firstName",  firstName);
                jsonBody.put("lastName", lastName);
                jsonBody.put("username", username);
                jsonBody.put("password",  password);
                jsonBody.put("email", email);
                jsonBody.put("city", city);
                jsonBody.put("phoneNumber", phoneNumber);

                final String mRequestBody = jsonBody.toString();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        response -> {
                            Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                            intent.putExtra("loginType", "user");
                            startActivity(intent);
                        }, error -> Log.e("LOG_VOLLEY", error.toString())) {
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