package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Classdatum;
import com.maker.millionairekey.Rest.Datum;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class VerifiedquestionActivity extends AppCompatActivity {
    ApiService apiService;
    RelativeLayout rl_main;

    ImageView iv_loder, iv_back;
    Button bt_prosstopay;
    Spinner sp_1, sp_2;
    EditText ed_Que_1, ed_Que_2, ed_username;
    int question2, question1;
    List<Classdatum> data;
    List<String> Queslist = new ArrayList<String>();
    String ans_1, ans_2, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verifiedquestion);
        apiService = RetroClient.getClient().create(ApiService.class);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Forgot Password");
//        actionBar.setDisplayHomeAsUpEnabled(true);

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
        bt_prosstopay = findViewById(R.id.bt_prosstopay);
        rl_main = findViewById(R.id.rl_main);
        rl_main.setVisibility(View.GONE);
        iv_loder = findViewById(R.id.iv_loder);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Glide.with(VerifiedquestionActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        ed_Que_1 = findViewById(R.id.ed_Que_1);
//        ed_Que_2 = findViewById(R.id.ed_Que_2);
        ed_username = findViewById(R.id.ed_username);
        sp_1 = (Spinner) findViewById(R.id.sp_1);
        sp_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                question1 = data.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        sp_2 = (Spinner) findViewById(R.id.sp_2);
//        sp_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                question2 = data.get(i).getId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        bt_prosstopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Extra.isNetwork(getApplicationContext())) {
                    ans_1 = ed_Que_1.getText().toString();
//                    ans_2 = ed_Que_2.getText().toString();
                    username = ed_username.getText().toString();
                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE).edit();
                    editor.putString("USERNAME", username);
                    editor.putString("USERCODE", username);
                    editor.commit();

                    if (ans_1.equals(null) || ans_1.isEmpty()  || username.equals(null) || username.isEmpty()) {
                        if (ans_1.equals(null) || ans_1.isEmpty()) {
                            Toast.makeText(VerifiedquestionActivity.this, "Please Enter Ans 1.", Toast.LENGTH_LONG).show();
                        }
//                        if (ans_2.equals(null) || ans_2.isEmpty()) {
//                            Toast.makeText(VerifiedquestionActivity.this, "Please Enter Ans 2.", Toast.LENGTH_LONG).show();
//                        }
                        if (username.equals(null) || username.isEmpty()) {
                            Toast.makeText(VerifiedquestionActivity.this, "Please Enter User Name.", Toast.LENGTH_LONG).show();
                        }

                    } else {

                        forgrtpassword( question1, ans_1,  username);
//                        question2,ans_2,
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });
        setQListData();

    }
//    int question2,String ans_2,

    private void forgrtpassword( int question1, String ans_1,  String username) {
        iv_loder.setVisibility(View.VISIBLE);
//        try {
            Example example = new Example();
            example.setQ1(question1);
//            example.setQ2(question2);
            example.setAns1(ans_1);
//            example.setAns2(ans_2);
            example.setUsername(username);
            Gson gson = new Gson();
            String json = gson.toJson(example);
            Call<Datum> call = apiService.Postforgotpassword(json);
            call.enqueue(new Callback<Datum>() {
                @Override
                public void onResponse(Call<Datum> call, retrofit2.Response<Datum> response) {
                    rl_main.setVisibility(View.VISIBLE);
                    iv_loder.setVisibility(View.GONE);
//                    try {
                        data = response.body().getClassdata();
                        if (response.body().getStatus().equals("fail")) {
                            Toast.makeText(VerifiedquestionActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        } else {
                            Intent inlogout = new Intent(VerifiedquestionActivity.this, UserLogReCodeActivity.class);
                            inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(inlogout);
                            Toast.makeText(VerifiedquestionActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
//                    } catch (Exception ex) {
//                        SharedPreferences settings = VerifiedquestionActivity.this.getSharedPreferences("myPref", VerifiedquestionActivity.this.MODE_PRIVATE);
//                        settings.edit().clear().commit();
//
//                        Intent inlogout = new Intent(VerifiedquestionActivity.this, LogInActivity.class);
//                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                                Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(inlogout);
//                        finish();
//                    }

                }

                @Override
                public void onFailure(Call<Datum> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
                //    Toast.makeText(VerifiedquestionActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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

    private void setQListData() {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Call<Datum> call = apiService.GetAllQuestion();
            call.enqueue(new Callback<Datum>() {
                @Override
                public void onResponse(Call<Datum> call, retrofit2.Response<Datum> response) {
                    rl_main.setVisibility(View.VISIBLE);
                    iv_loder.setVisibility(View.GONE);
                    try {
                        data = response.body().getClassdata();
                        if (response.body().getStatus().equals("fail")) {
//                            Toast.makeText(VerifiedquestionActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        } else {
                            for (int i = 0; i < data.size(); i++) {
                                Queslist.add(data.get(i).getQuestion());
                            }
                            ArrayAdapter dataAdapter = new ArrayAdapter(VerifiedquestionActivity.this, android.R.layout.simple_spinner_item, Queslist);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_1.setAdapter(dataAdapter);
//                            sp_2.setAdapter(dataAdapter);
//                            Toast.makeText(VerifiedquestionActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        SharedPreferences settings = VerifiedquestionActivity.this.getSharedPreferences("myPref", VerifiedquestionActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(VerifiedquestionActivity.this, HomeActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<Datum> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
                 //   Toast.makeText(VerifiedquestionActivity.this, "Try..", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
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
