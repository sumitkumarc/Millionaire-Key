package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;

public class LogInActivity extends AppCompatActivity {
    Button iVProceed;
    ImageButton bt_login;
    EditText etEmail, etPassword;
    //    TextInputLayout input_layout_password, input_layout_email;
    ApiService apiService;
    String ppassword = "password";
    ImageView iv_loder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);
        apiService = RetroClient.getClient().create(ApiService.class);
        if (Extra.isNetwork(getApplicationContext())) {
            bindview();


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

    private void bindview() {
        iv_loder = findViewById(R.id.iv_loder);
        Glide.with(LogInActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        iVProceed = (Button) findViewById(R.id.iVProceed);
        iVProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extra.isNetwork(getApplicationContext())) {
                    Intent inlogin = new Intent(LogInActivity.this, VerifiedquestionActivity.class);
                    startActivity(inlogin);
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bt_login = (ImageButton) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extra.isNetwork(getApplicationContext())) {
                    if (etPassword.getText().toString() == null || etPassword.getText().toString().isEmpty() ||
                            etEmail.getText().toString() == null || etEmail.getText().toString().isEmpty()) {

                        if (etPassword.getText().toString() == null || etPassword.getText().toString().isEmpty()) {
                            Toast.makeText(LogInActivity.this, "Please Enter Password.", Toast.LENGTH_SHORT).show();
                        }
                        if (etEmail.getText().toString() == null || etEmail.getText().toString().isEmpty()) {
                            Toast.makeText(LogInActivity.this, "Please Enter User Id.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String Referral_Id = etEmail.getText().toString();
                        String Password = etPassword.getText().toString();
                        LoginUser(Referral_Id, Password, ppassword);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void LoginUser(final String referral_Id, String password, String ppassword) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Call<Example> call = apiService.getloginuser(referral_Id, password, ppassword);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        if (response.body().getAccessToken().length() > 0) {
                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE).edit();
                            editor.putString("ACCESSTOKEN", response.body().getTokenType() + " " + response.body().getAccessToken());
                            editor.putString("USERNAME", referral_Id);
                            editor.putString("USERCODE", referral_Id);
                            editor.commit();
                            Intent intent = new Intent(LogInActivity.this, DashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
//                            Toast.makeText(LogInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(LogInActivity.this, "Some wrong Try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(LogInActivity.this, "Enter Valid Username Password.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
//                    Toast.makeText(LogInActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//
//            case android.R.id.home:
//                finish();
//                return true;
//
////            case R.id.share:
////                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
////                String imgBitmapPath = MediaStore.Images.Media.insertImage(BankInfoActivity.this.getContentResolver(), imgBitmap, "title", null);
////                Uri imgBitmapUri = Uri.parse(imgBitmapPath);
////                Intent shareIntent = new Intent(Intent.ACTION_SEND);
////                shareIntent.setType("image/*");
////                shareIntent.putExtra(Intent.EXTRA_TEXT, app_name + " Created By : " + acc_link);
////                shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
////                startActivity(shareIntent);
////                return true;
////
////            case R.id.rate:
////                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
////                return true;
//
//            default:
//                return super.onOptionsItemSelected(menuItem);
//        }
//    }
}
