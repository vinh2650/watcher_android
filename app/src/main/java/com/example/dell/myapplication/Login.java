package com.example.dell.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapplication.utils.NDialog;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {

    EditText etxEmail;
    EditText etxPassword;

    TextView txtForgotPassword;

    Button btnLogin;

    String access_token;
    String refresh_token;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ProgressDialog progressDialog = NDialog.setProgressDialog(Login.this, null, "Please wait...");
        final AlertDialog alertDialog = NDialog.setAlertDialog(Login.this, "Error", null, "OK");

        etxEmail = (EditText) findViewById(R.id.etxEmail);
        etxPassword = (EditText) findViewById(R.id.etxPassword);

        etxPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("keycode", "" + keyCode);
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case 66:
                            btnLogin.performClick();
                            break;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etxEmail.getText().toString();
                String password = etxPassword.getText().toString();

                if (password.isEmpty()) {
                    alertDialog.setMessage(getString(R.string.password_null));
                    alertDialog.show();
                } else {
                    progressDialog.show();

                    RequestParams rp = new RequestParams();
                    rp.add("grant_type", "password");
                    rp.add("username", email);
                    rp.add("password", password);
                    rp.add("client_id", "3421c917-7520-45c3-ab52-9bca75707a6d");
                    rp.add("client_secret", "mSezuSyxfnBgceEWhlmer62HHSDegEMCdVTosK%2FF0%2BI%3D");

                    HttpUtils.post("/token", rp, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                progressDialog.dismiss();

                                // get access_token and refresh_token
                                access_token = response.getString("access_token");
                                refresh_token = response.getString("refresh_token");
                                Intent intent = new Intent(Login.this, UserProfile.class);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                            progressDialog.dismiss();

                            if (statusCode == 0) {
                                alertDialog.setMessage(getString(R.string.internet_error));
                                alertDialog.show();
                            } else {
                                alertDialog.setMessage(getString(R.string.email_or_password_invalid));
                                alertDialog.show();
                            }
                        }
                    });
                }
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        final EditText input = new EditText(Login.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint(getString(R.string.etxEmail));
        input.setPadding(20, 15, 0, 20);


        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setTitle(getString(R.string.title_forgot_password))
                            .setMessage(getString(R.string.message_forgot_password))
                            .setView(input)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String email = input.getText().toString();
                                    if (email.isEmpty())
                                        Toast.makeText(Login.this, getString(R.string.email_null), Toast.LENGTH_LONG).show();
                                    else {
                                        RequestParams rp = new RequestParams();
                                        rp.add("email", "forgotPassword_email");

                                        HttpUtils.post("/api/v1/account/forgotpassword", rp, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                Toast.makeText(Login.this, getString(R.string.send_email_success), Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                if (statusCode == 0)
                                                    Toast.makeText(Login.this, getString(R.string.internet_error), Toast.LENGTH_LONG).show();
                                                else
                                                    Toast.makeText(Login.this, getString(R.string.email_invalid), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog a = builder.create();
                    a.show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dell.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dell.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
