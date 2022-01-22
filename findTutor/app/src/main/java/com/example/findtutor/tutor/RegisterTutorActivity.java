package com.example.findtutor.tutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class RegisterTutorActivity extends AppCompatActivity {

    private EditText tutorFirstNameEditText;
    private EditText tutorLastNameEditText;
    private EditText tutorUsernameEditText;
    private EditText tutorPasswordEditText;
    private EditText tutorEmailEditText;
    private EditText tutorCityEditText;
    private EditText tutorPhoneNumberEditText;

    private Button registerNewTutorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);

        // assign values to each control on the layout
        tutorFirstNameEditText=findViewById(R.id.TutorFirstNameEditText);
        tutorLastNameEditText=findViewById(R.id.TutorLastNameEditText);
        tutorUsernameEditText=findViewById(R.id.TutorUsernameEditText);
        tutorPasswordEditText=findViewById(R.id.TutorPasswordEditText);
        tutorEmailEditText=findViewById(R.id.TutorEmailEditText);
        tutorCityEditText=findViewById(R.id.TutorCityEditText);
        tutorPhoneNumberEditText=findViewById(R.id.TutorPhoneNumberEditText);

        registerNewTutorButton=findViewById(R.id.TutorRegisterButton);

        registerNewTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String firstName= tutorFirstNameEditText.getText().toString();
                String lastName=tutorLastNameEditText.getText().toString();
                String username=tutorUsernameEditText.getText().toString();
                String password=tutorPasswordEditText.getText().toString();
                String email=tutorEmailEditText.getText().toString();
                String city= tutorCityEditText.getText().toString();
                String phoneNumber=tutorPhoneNumberEditText.getText().toString();

                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterTutorActivity.this);
                    String URL = "http://192.168.124.194:8080/api/v1/tutor";

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
                            response -> System.out.println(response), new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_VOLLEY", error.toString());
                        }
                    }) {
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
            }
        });
    }
}