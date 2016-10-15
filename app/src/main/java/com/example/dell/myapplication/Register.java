package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    //EditText
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtPhoneNumber;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;

    //Button
    Button btnSignUp_register;

    //TextView
    TextView txtSignUp_register;

    //CheckBox
    CheckBox chkb_showpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtSignUp_register = (TextView) findViewById(R.id.txtSignUp_register);
        txtSignUp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //region Contructor

        //EditText
        edtFirstName = (EditText) findViewById(R.id.edtFirstName_register);
        edtLastName = (EditText) findViewById(R.id.edtLastName_register);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber_register);
        edtEmail = (EditText) findViewById(R.id.edtEmail_register);
        edtPassword = (EditText) findViewById(R.id.edtPassword_register);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword_register);

        //Button
        btnSignUp_register = (Button) findViewById(R.id.btnSignUp_register);

        //CheckBox
        chkb_showpassword = (CheckBox) findViewById(R.id.chkb_register_showpassword);

        //endregion

        btnSignUp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FirstName = edtFirstName.getText().toString();
                String LastName = edtLastName.getText().toString();
                String PhoneNumber = edtPhoneNumber.getText().toString();
                String Email = edtEmail.getText().toString();
                String Password = edtPassword.getText().toString();
                String ConfirmPassword = edtConfirmPassword.getText().toString();
            }
        });

        chkb_showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edtConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    edtPassword.setInputType(129);
                    edtConfirmPassword.setInputType(129);
                }
            }
        });
    }
}
