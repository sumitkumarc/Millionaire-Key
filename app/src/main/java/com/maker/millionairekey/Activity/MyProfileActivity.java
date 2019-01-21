package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.Model.UserGetInfoResponse;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;

public class MyProfileActivity extends AppCompatActivity {
    ApiService apiService;
    String token;
    ScrollView sv_main;
    TextView txt_name, txt_referralcode,
            txt_birthdate, txt_phoneno,
            txt_gender, txt_address,
            txt_adharcard, txt_pancard,
            txt_bankname, txt_branchname, txt_accountno, txt_ifsccode, txt_fname;
    ImageView iv_loder;
    LinearLayout ll_main_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_profile);
        actionBar.setDisplayHomeAsUpEnabled(true);
        apiService = RetroClient.getClient().create(ApiService.class);
        GetToken getToken = new GetToken(MyProfileActivity.this);
        token = getToken.mGetMyPref();
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
        Glide.with(MyProfileActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        sv_main = findViewById(R.id.sv_main);
        sv_main.setVisibility(View.GONE);
        ll_main_data = findViewById(R.id.ll_main_data);
        ll_main_data.setVisibility(View.GONE);
        txt_name = (TextView) findViewById(R.id.txt_fname);
        txt_referralcode = (TextView) findViewById(R.id.txt_referralcode);
        txt_birthdate = (TextView) findViewById(R.id.txt_birthdate);
        txt_phoneno = (TextView) findViewById(R.id.txt_phoneno);
        txt_gender = (TextView) findViewById(R.id.txt_gender);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_adharcard = (TextView) findViewById(R.id.txt_adharcard);
        txt_pancard = (TextView) findViewById(R.id.txt_pancard);
        txt_bankname = (TextView) findViewById(R.id.txt_bankname);
        txt_branchname = (TextView) findViewById(R.id.txt_branchname);
        txt_accountno = (TextView) findViewById(R.id.txt_accountno);
        txt_ifsccode = (TextView) findViewById(R.id.txt_ifsccode);
//        txt_fname = (TextView) findViewById(R.id.txt_fname);
        getUserInfo(token);
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

    public void getUserInfo(String token) {
        iv_loder.setVisibility(View.VISIBLE);
//        try {
        Log.d(">>>. ", "getAllTransaction: " + token);
        Call<UserGetInfoResponse> call = apiService.GetAllUserInfo(token);
        call.enqueue(new Callback<UserGetInfoResponse>() {
            @Override
            public void onResponse(Call<UserGetInfoResponse> call, retrofit2.Response<UserGetInfoResponse> response) {
                iv_loder.setVisibility(View.GONE);

//                    try {
                if (response.body().getStatus().equals("fail")) {
                 //   Toast.makeText(MyProfileActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                } else {
                    sv_main.setVisibility(View.VISIBLE);
                    txt_name.setText("" + ": " + response.body().getClassdata().getFname());
//                            txt_fname.setText("" + ": " + response.body().getClassdata().getFname());
                    txt_referralcode.setText("" + ": " + response.body().getClassdata().getRefcode());
                    txt_birthdate.setText("" + ": " + response.body().getClassdata().getDob());
                    txt_phoneno.setText("" + ": " + response.body().getClassdata().getMobileno());
                    txt_gender.setText("" + ": " + response.body().getClassdata().getGender());
                    txt_address.setText("" + ": " + response.body().getClassdata().getAddress());
                    if (response.body().getClassdata().getAdharcard().equals(null)
                            || response.body().getClassdata().getAdharcard().isEmpty() ||
                            response.body().getClassdata().getAdharcard().equals("")) {
                        ll_main_data.setVisibility(View.GONE);
                    } else {
                        ll_main_data.setVisibility(View.VISIBLE);
                        txt_adharcard.setText("" + ": " + response.body().getClassdata().getAdharcard());
                        txt_pancard.setText("" + ": " + response.body().getClassdata().getPanno());
                        txt_bankname.setText("" + ": " + response.body().getClassdata().getBankname());
                        txt_branchname.setText("" + ": " + response.body().getClassdata().getBranchname());
                        txt_accountno.setText("" + ": " + response.body().getClassdata().getAccno());
                        txt_ifsccode.setText("" + ": " + response.body().getClassdata().getIfsccode());
                    }


                  //  Toast.makeText(MyProfileActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                }
//                    } catch (Exception ex) {
//                        SharedPreferences settings = MyProfileActivity.this.getSharedPreferences("myPref", MyProfileActivity.this.MODE_PRIVATE);
//                        settings.edit().clear().commit();
//
//                        Intent inlogout = new Intent(MyProfileActivity.this, HomeActivity.class);
//                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                                Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(inlogout);
//                        finish();
//                        Toast.makeText(MyProfileActivity.this, "Some Thing Wrong", Toast.LENGTH_SHORT).show();
//
//                    }

            }

            @Override
            public void onFailure(Call<UserGetInfoResponse> call, Throwable t) {
                iv_loder.setVisibility(View.GONE);
                onBackPressed();
              //  Toast.makeText(MyProfileActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });
//        } catch (Exception e) {
//        }
    }

    @Override
    public void onBackPressed()

    {
        super.onBackPressed();
        this.finish();
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
