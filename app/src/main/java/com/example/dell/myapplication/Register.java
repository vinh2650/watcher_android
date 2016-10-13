package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class Register extends AppCompatActivity {

    EditText edtFirstName;
    EditText edtLastName;
    EditText edtPhoneNumber;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;

    Button  btnSignUp_register;

    TextView txtSignUp_register;
    TextView txtMessage_register;

    LinearLayout llMessage_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtSignUp_register = (TextView)findViewById(R.id.txtSignUp_register);
        txtSignUp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_signIn = new Intent(Register.this, Login.class);
                startActivity(intent_signIn);
            }
        });

        edtFirstName    = (EditText)findViewById(R.id.edtFirstName_register);
        edtLastName     = (EditText)findViewById(R.id.edtLastName_register);
        edtPhoneNumber  = (EditText)findViewById(R.id.edtPhoneNumber_register);
        edtEmail        = (EditText)findViewById(R.id.edtEmail_register);
        edtPassword     = (EditText)findViewById(R.id.edtPassword_register);
        edtConfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword_register);
        txtMessage_register = (TextView)findViewById(R.id.txtMessage_register);
        llMessage_register = (LinearLayout)findViewById(R.id.llMessage_register);

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
                String message = "";
                if (!Password.equals(ConfirmPassword))
                {
                    llMessage_register.setVisibility(View.VISIBLE);
                    txtMessage_register.setText(getString(R.string.match_password));
                }
                else {
                    RequestParams rp = new RequestParams();
                    rp.add("email", Email);
                    rp.add("firstName", FirstName);
                    rp.add("lastName", LastName);
                    rp.add("phoneNumber", PhoneNumber);
                    rp.add("organizationDestinationId", "");
                    rp.add("passwords", Password);
                    rp.add("confirmPasswords", ConfirmPassword);

                    HttpUtils.post("/api/v1/user/register", rp, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("statusCode: ", "" + statusCode);
                            Log.d("headers: ", Arrays.toString(headers));
                            Log.d("response: ", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("statusCode: ", "" + statusCode);
                            Log.d("headers: ", Arrays.toString(headers));
                            Log.d("throwable: ", throwable.toString());
                            Log.d("errorResponse: ", errorResponse.toString());
                        }
                    });
                }
            }
        });
    }
}
