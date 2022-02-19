package com.example.findtutor.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.example.findtutor.login.LoginState;
import com.example.findtutor.tutor.model.RatingModel;
import com.example.findtutor.tutor.model.Tutors;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TutorDetailsActivity extends AppCompatActivity {
    private TextView tutorNameTextView;
    private TextView tutorEmailTextView;
    private TextView tutorPhoneTextView;
    private TextView tutorCityTextView;
    private TextView tutorRatingTextView;
    private TextView tutorInstructionsType;
    private Button tutorEditButton;
    private Button tutorRateButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_details_activity);

        tutorNameTextView = findViewById(R.id.tutorNameTextView);
        tutorEmailTextView = findViewById(R.id.tutorEmailTextView);
        tutorPhoneTextView = findViewById(R.id.tutorPhoneTextView);
        tutorCityTextView = findViewById(R.id.tutorCityTextView);
        tutorRatingTextView = findViewById(R.id.tutorRatingTextView);
        tutorInstructionsType = findViewById(R.id.tutorInstructionsTypeTextView);

        tutorEditButton = findViewById(R.id.editTutorButton);
        tutorRateButton = findViewById(R.id.rateTutorButton);

        Tutors tutor = getIntent().getParcelableExtra("currentTutor");

        tutorNameTextView.setText(tutor.getFirstName() + " " + tutor.getLastName());
        tutorEmailTextView.setText(tutor.getEmail());
        tutorPhoneTextView.setText(tutor.getPhoneNumber());
        tutorCityTextView.setText(tutor.getCity());
        tutorRatingTextView.setText("Rating: " + tutor.getRating() == null ? "0" : tutor.getRating().toString());
        tutorInstructionsType.setText(tutor.getInstructionsType());

        if ("user".equals(LoginState.userType)) {
            tutorRateButton.setVisibility(View.VISIBLE);
        }

        if ("tutor".equals(LoginState.userType)) {
            tutorEditButton.setVisibility(View.VISIBLE);
        }

        tutorRateButton.setOnClickListener(v -> {
            String[] ratingOptions = {"1", "2", "3", "4", "5"};
            AlertDialog.Builder builder = new AlertDialog.Builder(TutorDetailsActivity.this);
            builder.setTitle("Rate Tutor")
                    .setItems(ratingOptions, (dialog, which) -> {
                        rateTutor(ratingOptions[which], Long.valueOf(tutor.getId()));
                    });

            builder.create().show();
        });

        tutorEditButton.setOnClickListener(v -> {
            Intent intent = new Intent(TutorDetailsActivity.this, EditTutorActivity.class);
            intent.putExtra("currentTutor", (Parcelable) tutor);
            startActivity(intent);
        });
    }

    private void rateTutor(String rating, Long tutorId){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(TutorDetailsActivity.this);
            String URL = "http://192.168.1.89:8080/api/v1/tutor/rate/"+ tutorId;

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("rating", rating);

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        RatingModel model = new Gson().fromJson(response, RatingModel.class);
                        tutorRatingTextView.setText("Rating: " + model.getRating() == null ? "0" : model.getRating().toString());
                    }, new Response.ErrorListener() {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
