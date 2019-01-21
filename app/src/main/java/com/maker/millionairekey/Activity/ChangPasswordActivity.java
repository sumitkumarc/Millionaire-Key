package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;

public class ChangPasswordActivity extends AppCompatActivity {
    Button iVProceed;
    ApiService apiService;
    TextInputLayout in_Curpassword, in_Newpassword, in_conpassword;
    TextInputEditText etConPassword, etNewPassword, etCurpassword;
    String token;
    ImageView iv_loder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chang_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        apiService = RetroClient.getClient().create(ApiService.class);
        actionBar.setDisplayHomeAsUpEnabled(true);
        GetToken getToken = new GetToken(ChangPasswordActivity.this);
        token = getToken.mGetMyPref();
        Log.d("DATA", "DATATYPE" + token);


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
        iv_loder = (ImageView) findViewById(R.id.iv_loder);
        Glide.with(ChangPasswordActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        iVProceed = (Button) findViewById(R.id.iVProceed);
        in_Curpassword = (TextInputLayout) findViewById(R.id.in_Curpassword);
        in_Newpassword = (TextInputLayout) findViewById(R.id.in_Newpassword);
        in_conpassword = (TextInputLayout) findViewById(R.id.in_conpassword);
        etConPassword = (TextInputEditText) findViewById(R.id.etConPassword);
        etNewPassword = (TextInputEditText) findViewById(R.id.etNewPassword);
        etCurpassword = (TextInputEditText) findViewById(R.id.etCurpassword);
        iVProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Extra.isNetwork(ChangPasswordActivity.this)) {
                    try {
                        if (etNewPassword.getText().toString() == null || etNewPassword.getText().toString().isEmpty() ||
                                etNewPassword.getText().toString() == null || etNewPassword.getText().toString().isEmpty() ||
                                etCurpassword.getText().toString() == null || etCurpassword.getText().toString().isEmpty()) {
                            if (etNewPassword.getText().toString() == null || etNewPassword.getText().toString().isEmpty()) {
                                in_Newpassword.setError("Please Enter New Password.");
                            } else {
                                in_Newpassword.setErrorEnabled(false);
                            }
                            if (etCurpassword.getText().toString() == null || etCurpassword.getText().toString().isEmpty()) {
                                in_Curpassword.setError("Please Enter Current Password.");
                            } else {
                                in_Curpassword.setErrorEnabled(false);
                            }
                            if (etConPassword.getText().toString() == null || etConPassword.getText().toString().isEmpty()) {
                                in_conpassword.setError("Please Enter Conform Password.");
                            } else {
                                in_conpassword.setErrorEnabled(false);
                            }

                        } else {
                            in_conpassword.setErrorEnabled(false);
                            in_Newpassword.setErrorEnabled(false);
                            in_Curpassword.setErrorEnabled(false);
                            String newpassword = etConPassword.getText().toString();
                            String oldpassword = etCurpassword.getText().toString();
                            if (!etNewPassword.getText().toString().equals(etConPassword.getText().toString())) {

                                Toast.makeText(ChangPasswordActivity.this, "Password Not Match", Toast.LENGTH_SHORT).show();
                            } else {
                                Resetpassword(token, newpassword, oldpassword);
                                // Toast.makeText(ChangPasswordActivity.this, "Password Match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        SharedPreferences settings = ChangPasswordActivity.this.getSharedPreferences("myPref", ChangPasswordActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(ChangPasswordActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                        //   Toast.makeText(ChangPasswordActivity.this, "Some Thing Wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangPasswordActivity.this, "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    private void Resetpassword(String token, String newpassword, String oldpassword) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Call<Example> call = apiService.getRestpassword(token, newpassword, oldpassword);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                    try {
                        iv_loder.setVisibility(View.GONE);
                        if (response.body().getStatus().equals("fail")) {
//                            Toast.makeText(ChangPasswordActivity.this, "" + response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
                            String USERNAME = prefs.getString("USERNAME", "");
                            Call<Example> call2 = apiService.getloginuser(USERNAME, etConPassword.getText().toString(), "password");
                            call2.enqueue(new Callback<Example>() {
                                @Override
                                public void onResponse(Call<Example> call2, retrofit2.Response<Example> response) {
                                    iv_loder.setVisibility(View.GONE);
                                    Log.d(">>> ", "onResponse: " + response.body().toString());
                                    try {
                                        if (response.body().getAccessToken().length() > 0) {
                                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE).edit();
                                            editor.putString("ACCESSTOKEN", response.body().getTokenType() + " " + response.body().getAccessToken());
                                            editor.commit();
                                            Intent intent = new Intent(ChangPasswordActivity.this, DashboardActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
//                                            Toast.makeText(ChangPasswordActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                                        } else {
//                                            Toast.makeText(ChangPasswordActivity.this, "Some wrong Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception ex) {
//                                        Toast.makeText(ChangPasswordActivity.this, "Enter Valid UserName Password.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Example> call2, Throwable t) {
                                    iv_loder.setVisibility(View.GONE);
                                    onBackPressed();
                             //       Toast.makeText(ChangPasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    Toast.makeText(ChangPasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed()

    {
        super.onBackPressed();
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

}
