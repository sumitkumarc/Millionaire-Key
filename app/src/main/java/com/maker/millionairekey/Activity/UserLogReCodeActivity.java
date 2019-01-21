package com.maker.millionairekey.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.ReqestJson;
import com.maker.millionairekey.Rest.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;

public class UserLogReCodeActivity extends AppCompatActivity {
    Button btLogin, iVProceed;
    TextView txt_username;
    String USERNAME;
    String USERCODE;
    ProgressDialog pDialog;
    ImageView iv_loder;
    ApiService apiService;
    TextInputLayout in_conpassword, in_password;
    TextInputEditText etPassword, etConPassword;
    String ppassword = "password";
    String txid = "0";
    String payrespo ;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_log_re_code);
        apiService = RetroClient.getClient().create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USERCODE = prefs.getString("USERCODE", "");
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_username.setText("USERID : " + USERCODE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        payrespo = getIntent().getStringExtra("PAYERESPO");
        response = getIntent().getStringExtra("RESPONSE");
        GetUpdatePayStatus(USERCODE, payrespo, txid, response);
        if (Extra.isNetwork(getApplicationContext())) {
            bindView();


        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    //set icon

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    //set title
                    .setTitle("Internet Is Not Available")
                    //set message
                    .setMessage("Please Check Your Internet Connection")
                    //set positive button
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
            alertDialog.setCancelable(false);
            //    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();

        }
    }

    private void bindView() {
        iv_loder = findViewById(R.id.iv_loder);
        Glide.with(UserLogReCodeActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        in_conpassword = (TextInputLayout) findViewById(R.id.in_conpassword);
        in_password = (TextInputLayout) findViewById(R.id.in_password);
        etPassword = (TextInputEditText) findViewById(R.id.etPassword);
        etConPassword = (TextInputEditText) findViewById(R.id.etConPassword);

        iVProceed = (Button) findViewById(R.id.iVProceed);
        iVProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Extra.isNetwork(getApplicationContext())) {
                    Intent intent = new Intent(UserLogReCodeActivity.this, ChangPasswordActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });
        btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extra.isNetwork(getApplicationContext())) {
                    if (etPassword.getText().toString() == null || etPassword.getText().toString().isEmpty() ||
                            etConPassword.getText().toString() == null || etConPassword.getText().toString().isEmpty()) {
                        if (etPassword.getText().toString() == null || etPassword.getText().toString().isEmpty()) {
                            in_password.setError("Please Enter Password.");
                        } else {
                            in_password.setErrorEnabled(false);
                        }
                        if (etConPassword.getText().toString() == null || etConPassword.getText().toString().isEmpty()) {
                            in_conpassword.setError("Please Enter Conform Password.");
                        } else {
                            in_conpassword.setErrorEnabled(false);
                        }

                    } else {
                        in_conpassword.setErrorEnabled(false);
                        in_password.setErrorEnabled(false);
                        if (!etPassword.getText().toString().equals(etConPassword.getText().toString())) {

                            Toast.makeText(UserLogReCodeActivity.this, "Password Not Match", Toast.LENGTH_SHORT).show();
                        } else {
                            createpassword(etConPassword.getText().toString());
                            // Toast.makeText(UserLogReCodeActivity.this, "Password Match", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void createpassword(final String password) {
        iv_loder.setVisibility(View.VISIBLE);
        try {


            ReqestJson reqestJson = new ReqestJson();
            reqestJson.setUsername(USERCODE);
            reqestJson.setPassword(password);

            Gson gson = new Gson();
            String json = gson.toJson(reqestJson);

            Call<Example> call = apiService.getcreatepassword(json);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus() == "fail") {
                            // Toast.makeText(UserLogReCodeActivity.this, " " + response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                            Call<Example> call2 = apiService.getloginuser(USERCODE, password, "password");
                            call2.enqueue(new Callback<Example>() {
                                @Override
                                public void onResponse(Call<Example> call2, retrofit2.Response<Example> response) {
                                    iv_loder.setVisibility(View.GONE);
                                    try {
                                        if (response.body().getAccessToken().length() > 0) {
                                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE).edit();
                                            editor.putString("ACCESSTOKEN", response.body().getTokenType() + " " + response.body().getAccessToken());
                                            editor.commit();
                                            Intent intent = new Intent(UserLogReCodeActivity.this, DashboardActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            //                  Toast.makeText(UserLogReCodeActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                                        } else {
                                            //      Toast.makeText(UserLogReCodeActivity.this, "Some wrong Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception ex) {
                                        //    Toast.makeText(UserLogReCodeActivity.this, "Enter Valid UserName Password.", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onFailure(Call<Example> call2, Throwable t) {
                                    iv_loder.setVisibility(View.GONE);
                                    //     Toast.makeText(UserLogReCodeActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                                }
                            });
                            //        Toast.makeText(UserLogReCodeActivity.this, "" + response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        SharedPreferences settings = UserLogReCodeActivity.this.getSharedPreferences("myPref", UserLogReCodeActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(UserLogReCodeActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                        //        Toast.makeText(UserLogReCodeActivity.this, "Invalid User Login", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    pDialog.dismiss();
                    onBackPressed();
                    //  Toast.makeText(UserLogReCodeActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

//            case R.id.share:
//                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//                String imgBitmapPath = MediaStore.Images.Media.insertImage(BankInfoActivity.this.getContentResolver(), imgBitmap, "title", null);
//                Uri imgBitmapUri = Uri.parse(imgBitmapPath);
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("image/*");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, app_name + " Created By : " + acc_link);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
//                startActivity(shareIntent);
//                return true;
//
//            case R.id.rate:
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
//                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }
    private void GetUpdatePayStatus(String USERCODE, String response, String txid, String payrespo) {
        try {
            Example example = new Example();
            example.setUsername(USERCODE);
            example.setStatus(response);
            example.setTxid(txid);
            example.setPayrespo(payrespo);
            Gson gson = new Gson();
            String json = gson.toJson(example);
            Call<Example> call = apiService.getRegistrationUpdateStatus(json);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, final retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus() == "fail") {
                            //  Toast.makeText(UserLogReCodeActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
//                            Intent intent = new Intent(UserLogReCodeActivity.this, UserLogReCodeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
                            //   Toast.makeText(UserLogReCodeActivity.this, "" + response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    //    Toast.makeText(UserLogReCodeActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }
    }
}
