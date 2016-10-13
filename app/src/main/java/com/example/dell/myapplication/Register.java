package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText edtFirstName;
    EditText edtLastName;
    EditText edtPhoneNumber;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;

    Button  btnSignUp_register;

    TextView txtSignUp_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtSignUp_register = (TextView)findViewById(R.id.txtSignUp_register);
        txtSignUp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtFirstName    = (EditText)findViewById(R.id.edtFirstName_register);
        edtLastName     = (EditText)findViewById(R.id.edtLastName_register);
        edtPhoneNumber  = (EditText)findViewById(R.id.edtPhoneNumber_register);
        edtEmail        = (EditText)findViewById(R.id.edtEmail_register);
        edtPassword     = (EditText)findViewById(R.id.edtPassword_register);
        edtConfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword_register);

        btnSignUp_register = (Button)findViewById(R.id.btnSignUp_register);
        btnSignUp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FirstName    = edtFirstName.getText().toString();
                String LastName     = edtLastName.getText().toString();
                String PhoneNumber  = edtPhoneNumber.getText().toString();
                String Email        = edtEmail.getText().toString();
                String Password     = edtPassword.getText().toString();
                String ConfirmPassword = edtConfirmPassword.getText().toString();
            }
        });
    }
}
