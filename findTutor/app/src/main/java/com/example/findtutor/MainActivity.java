package com.example.findtutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText tutorFirstNameEditText;
    private EditText tutorLastNameEditText;
    private EditText tutorUsernameEditText;
    private EditText tutorPasswordEditText;
    private EditText tutorEmailEditText;
    private EditText tutorCityEditText;
    private EditText tutorPhoneNumberEditText;

    private Button registerNewTutorButton;

    private String tutorFirstName;
    private String tutorLastName;
    private String tutorUsername;
    private String tutorPassword;
    private String tutorEmail;
    private String tutorCity;
    private String tutorPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                tutorFirstName=tutorFirstNameEditText.getText().toString();
                tutorLastName=tutorLastNameEditText.getText().toString();
                tutorUsername=tutorUsernameEditText.getText().toString();
                tutorEmail=tutorEmailEditText.getText().toString();
                tutorPassword=tutorPasswordEditText.getText().toString();
                tutorCity=tutorCityEditText.getText().toString();
                tutorPhoneNumber=tutorPhoneNumberEditText.getText().toString();

                Toast.makeText(MainActivity.this, "successfully registered!", Toast.LENGTH_LONG).show();
            }
        });
    }
    }
