package com.maker.millionairekey.Activity;

import android.app.Activity;

import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;

import org.json.JSONObject;
import org.json.JSONException;

import android.content.IntentFilter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Classdatum;
import com.maker.millionairekey.Rest.Datum;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.RetroClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    Button bt_login;
    private Calendar calendar;
    private DateFormat dateFormat;
    EditText ed_date;
    ApiService apiService;
    LinearLayout ll_main;
    TextView txt_accepte;
    String Gender, BOD;
    int question1;
    AlertDialog alertDialog, alertDialog1;
    AlertDialog.Builder alertDialogBuilder1;
    //            , question2;
    List<Classdatum> data;
    CheckBox macos_option;
    String refcode;
    private Spinner spinnerOfferType;
    //    , sp_2
    EditText ed_ans1;
    //    , ed_ans2
    TextInputLayout in_name, in_phoneno, in_pinno, in_add, in_city, in_landmark, in_state, in_email;
    TextInputEditText ed_refcode, ed_name, ed_phoneno, ed_add, ed_city, ed_Landmark, ed_state, ed_pincode, ed_email;
    String name, phoneno, address, city, landmark, state, pincode, email, ans_1;
    //    , ans_2
    ImageView iv_loder;
    String USERCODE;
    List<String> cityName = new ArrayList<String>();
    String MYCODEDATA;
    String payrespo;
    InstapayListener listener;

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                String txid = "0";
                payrespo = "success";
                Intent intent = new Intent(SignupActivity.this, UserLogReCodeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("PAYERESPO",payrespo);
                intent.putExtra("TXID",txid);
                intent.putExtra("RESPONSE",response);
                startActivity(intent);
              //  GetUpdatePayStatus(USERCODE, payrespo, txid, response);
            }

            @Override
            public void onFailure(int code, String reason) {
                String txid = String.valueOf(code);
                payrespo = "fail";
                GetUpdatePayStatus(USERCODE, payrespo, txid, reason);
            }
        };
    }

    private void GetUpdatePayStatus(String USERCODE, final String payrespo, final String txid, String reason) {
        try {
            Example example = new Example();
            example.setUsername(USERCODE);
            example.setStatus(payrespo);
            example.setTxid(txid);
            example.setPayrespo(reason);
            Gson gson = new Gson();
            String json = gson.toJson(example);
            Call<Example> call = apiService.getRegistrationUpdateStatus(json);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, final retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus() == "fail") {
                            SharedPreferences settings = SignupActivity.this.getSharedPreferences("myPref", SignupActivity.this.MODE_PRIVATE);
                            settings.edit().clear().commit();
                            //   Toast.makeText(SignupActivity.this, "DATA-0" + response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                         //   if (payrespo.equals("fail")) {
                                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                SharedPreferences settings = SignupActivity.this.getSharedPreferences("myPref", SignupActivity.this.MODE_PRIVATE);
                                settings.edit().clear().commit();
//                            } else {
//                                Intent intent = new Intent(SignupActivity.this, UserLogReCodeActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                            }
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USERCODE = prefs.getString("USERCODE", "");
        MYCODEDATA = prefs.getString("MYCODEDATA", "");

        // Call the function callInstamojo to start payment here
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        apiService = RetroClient.getClient().create(ApiService.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (Extra.isNetwork(getApplicationContext())) {
            bindView();
            update();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Is Not Available")
                    .setMessage("Please Check Your Internet Connection")
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
        }
    }

    private void bindView() {
        ll_main = findViewById(R.id.ll_main);
        ll_main.setVisibility(View.GONE);
        spinnerOfferType = (Spinner) findViewById(R.id.spinnerOfferType);
        spinnerOfferType.setOnItemSelectedListener(this);
//        sp_2 = (Spinner) findViewById(R.id.sp_2);
//        sp_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                question2 = data.get(i).getId();
//            //    Getsamequestion();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        iv_loder = findViewById(R.id.iv_loder);
        Glide.with(SignupActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        setQListData();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)
                radioGroup.findViewById(radioButtonID);
        Gender = radioButton.getText().toString();
        macos_option = (CheckBox) findViewById(R.id.macos_option);
        txt_accepte = (TextView) findViewById(R.id.txt_accepte);
        in_email = (TextInputLayout) findViewById(R.id.in_email);
        in_name = (TextInputLayout) findViewById(R.id.in_name);
        in_phoneno = (TextInputLayout) findViewById(R.id.in_phoneno);
        in_pinno = (TextInputLayout) findViewById(R.id.in_pinno);
        in_add = (TextInputLayout) findViewById(R.id.in_add);
        in_city = (TextInputLayout) findViewById(R.id.in_city);
        in_landmark = (TextInputLayout) findViewById(R.id.in_landmark);
        in_state = (TextInputLayout) findViewById(R.id.in_state);
        ed_name = (TextInputEditText) findViewById(R.id.ed_name);
        ed_email = (TextInputEditText) findViewById(R.id.ed_email);
        ed_phoneno = (TextInputEditText) findViewById(R.id.ed_phoneno);
        ed_add = (TextInputEditText) findViewById(R.id.ed_add);
        ed_city = (TextInputEditText) findViewById(R.id.ed_city);
        ed_Landmark = (TextInputEditText) findViewById(R.id.ed_Landmark);
        ed_state = (TextInputEditText) findViewById(R.id.ed_state);
        ed_pincode = (TextInputEditText) findViewById(R.id.ed_pincode);
        ed_refcode = (TextInputEditText) findViewById(R.id.ed_refcode);
        ed_ans1 = (EditText) findViewById(R.id.ed_ans1);
        // ed_ans2 = (EditText) findViewById(R.id.ed_ans2);
        ed_date = (EditText) findViewById(R.id.ed_date);
        ed_refcode.setText(MYCODEDATA.toString().toUpperCase());
        ed_date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                DatePickerDialog.newInstance(SignupActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        macos_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //   Toast.makeText(SignupActivity.this, "Thank You for Accept Term and Condition.", Toast.LENGTH_LONG).show();
                }
            }
        });
        txt_accepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = (LayoutInflater) SignupActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View promptsView1 = li.inflate(R.layout.termsandconditions, null);
                alertDialogBuilder1 = new AlertDialog.Builder(SignupActivity.this);
                ImageView icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                Button bt_Ok = promptsView1.findViewById(R.id.bt_Ok);
                WebView webView = promptsView1.findViewById(R.id.webview);
                alertDialogBuilder1.setView(promptsView1);
                alertDialogBuilder1.setCancelable(true);
                alertDialog = alertDialogBuilder1.create();
                webView.loadUrl("http://millionairekey.in/termsandconditions.html");
                bt_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
                icon_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        bt_login = (Button) findViewById(R.id.bt_prosstopay);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if (Extra.isNetwork(getApplicationContext())) {
                    insertdata();
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void setQListData() {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Call<Datum> call = apiService.GetAllQuestion();
            call.enqueue(new Callback<Datum>() {
                @Override
                public void onResponse(Call<Datum> call, retrofit2.Response<Datum> response) {
                    ll_main.setVisibility(View.VISIBLE);
                    iv_loder.setVisibility(View.GONE);
                    try {
                        data = response.body().getClassdata();
                        if (response.body().getStatus().equals("fail")) {
//                            Toast.makeText(SignupActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        } else {
                            for (int i = 0; i < data.size(); i++) {
                                cityName.add(data.get(i).getQuestion());
                            }
                            ArrayAdapter dataAdapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_spinner_item, cityName);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerOfferType.setAdapter(dataAdapter);
                            //     sp_2.setAdapter(dataAdapter);
//                            Toast.makeText(SignupActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        SharedPreferences settings = SignupActivity.this.getSharedPreferences("myPref", SignupActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Intent inlogout = new Intent(SignupActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<Datum> call, Throwable t) {
                    onBackPressed();
                    iv_loder.setVisibility(View.GONE);
                    //  Toast.makeText(SignupActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void insertdata() {
        name = ed_name.getText().toString();
        phoneno = ed_phoneno.getText().toString();
        address = ed_add.getText().toString() + "," + ed_city.getText().toString() + "," + ed_Landmark.getText().toString() + "," + ed_state.getText().toString() + "," + ed_pincode.getText().toString();
        city = ed_city.getText().toString();
        landmark = ed_Landmark.getText().toString();
        state = ed_state.getText().toString();
        pincode = ed_pincode.getText().toString();
        email = ed_email.getText().toString();
        if (email.isEmpty() || email.equals("")) {
            email = "millionaireekey@gmail.com";
        }
        refcode = ed_refcode.getText().toString();
        ans_1 = ed_ans1.getText().toString();
        //   ans_2 = ed_ans2.getText().toString();
        if (name.equals(null) || name.isEmpty() || Gender.equals(null) || Gender.isEmpty() ||
                ans_1.equals(null) || ans_1.isEmpty() ||
                //   ans_2.equals(null) || ans_2.isEmpty() ||
                question1 == 0 ||
//                question2 == 0 ||
                address.equals(null) || address.isEmpty() ||
                city.equals(null) || city.isEmpty() ||
                landmark.equals(null) || landmark.isEmpty() ||
                state.equals(null) || state.isEmpty() ||
                phoneno.equals(null) || phoneno.isEmpty() || phoneno.length() != 10 ||
                pincode.equals(null) || pincode.isEmpty() || pincode.length() != 6 || macos_option.isChecked() == false) {

            if (Gender.equals(null) || Gender.isEmpty()) {
                in_name.setError("Please Select Gender.");
            }
            if (macos_option.isChecked() == false) {
                Toast.makeText(this, "Please accept terms and condition", Toast.LENGTH_LONG).show();
            }
            if (ans_1.equals(null) || ans_1.isEmpty()) {
                Toast.makeText(this, "Please Enter Ans 1.", Toast.LENGTH_LONG).show();
            }
//            if (ans_2.equals(null) || ans_2.isEmpty()) {
//                Toast.makeText(this, "Please Enter Ans 2.", Toast.LENGTH_LONG).show();
//            }

            if (name.equals(null) || name.isEmpty()) {
                in_name.setError("Please Enter Name.");
            } else {
                in_name.setErrorEnabled(false);
            }
            if (address.equals(null) || address.isEmpty()) {
                in_add.setError("Please Enter Address.");
            } else {
                in_add.setErrorEnabled(false);
            }
            if (city.equals(null) || city.isEmpty()) {
                in_city.setError("Please Enter City.");
            } else {
                in_city.setErrorEnabled(false);
            }
            if (landmark.equals(null) || landmark.isEmpty()) {
                in_landmark.setError("Please Enter Landmark.");
            } else {
                in_landmark.setErrorEnabled(false);
            }
            if (state.equals(null) || state.isEmpty()) {
                in_state.setError("Please Enter State.");
            } else {
                in_state.setErrorEnabled(false);
            }
            if (pincode.length() == 6) {
                in_pinno.setErrorEnabled(false);
            } else {
                in_pinno.setError("Please Enter 6 Digit Pincode.");

            }
            if (phoneno.length() != 10) {
                in_phoneno.setError("Please Enter 10 Digit No.");


            } else {
                in_phoneno.setErrorEnabled(false);
            }
        } else {
            String fulladdress = ed_add.getText().toString() + "," + ed_city.getText().toString() + "," + ed_Landmark.getText().toString() + "," + ed_state.getText().toString() + "," + ed_pincode.getText().toString();

//            if (question1 == question2) {
//                ed_ans1.setText("");
//                ed_ans2.setText("");
//                Toast.makeText(this, "" + "Your Question Are Same so Change One Question", Toast.LENGTH_LONG).show();

//            } else {
            insertRegistration(name, fulladdress, phoneno, refcode, BOD, Gender, refcode, question1, ans_1);
//                , ans_2, question2
//            }
        }
    }

    //, String ans_2
    private void insertRegistration(final String name,
                                    String fulladdress,
                                    final String phoneno,
                                    String refcode,
                                    String BOD,
                                    String gender,
                                    String s,
                                    int question1, String ans_1) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Example example = new Example();
            example.setEmail(email);
            example.setFname(name);
            example.setMobileno(phoneno);
            example.setRefcode(refcode);
            example.setGender(gender);
            example.setDob(ed_date.getText().toString());
            example.setAddress(fulladdress);
            example.setRefcode(s);
            example.setQ1(question1);
            example.setAns1(ans_1);
//            example.setQ2(question2);
//            example.setAns2(ans_2);
            Gson gson = new Gson();
            String json = gson.toJson(example);

            Log.e(">>>>>> ", "insertRegistration: " + json);
            Call<Example> call = apiService.getRegistration(json);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, final retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus() == "fail") {
//                            Toast.makeText(SignupActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        } else {


                            String s = response.body().getData();
                            String[] parts = s.split("\\|");
                            String uname = parts[0];
                            String ucode = parts[1];
                            USERCODE = uname;
//                            Toast.makeText(SignupActivity.this, ">>> " + response.body().getData(), Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE).edit();
//                            String uname = response.body().getData().split("|")[0];
//                            String ucode = response.body().getData().split("|")[1];

//                            Log.d("DATA","DATAUSERNAME" + uname);
//                            Log.d("DATA","DATAREGCODE" + ucode);

                            editor.putString("USERNAME", uname);
                            editor.putString("USERCODE", uname);
                            editor.commit();
                            cleardata();
                            LayoutInflater li = (LayoutInflater) SignupActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            ;
                            View promptsView1 = li.inflate(R.layout.dilog_payment_pro, null);

                            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(SignupActivity.this);
                            ImageView icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                            TextView tv_re = promptsView1.findViewById(R.id.tv_re);
                            Button bt_prosstopay = promptsView1.findViewById(R.id.bt_prosstopay);
                            alertDialogBuilder1.setView(promptsView1);
                            alertDialogBuilder1.setCancelable(true);
                            tv_re.setText("Your registration token number is" + " \n " + ucode + "\n" + "Kindly save and use if you find any paymnent issues.");
                            alertDialog1 = alertDialogBuilder1.create();
                            // show it
                            bt_prosstopay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    alertDialog1.cancel();
//                                    Intent intent = new Intent(SignupActivity.this, UserLogReCodeActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);


                                    String MMamount = "10";
                                    String MMpurpose = "Registration Fees for Millionaireekey";
                                    callInstamojoPay(email, phoneno, MMamount, MMpurpose, name);
                                }
                            });
                            icon_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog1.cancel();
                                }
                            });
                            alertDialog1.show();
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
                    //    Toast.makeText(SignupActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }
    }


    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    private void cleardata() {
        ed_name.setText("");
        ed_phoneno.setText("");
        ed_add.setText("");
        ed_city.setText("");
        ed_Landmark.setText("");
        ed_state.setText("");
        ed_pincode.setText("");
        ed_city.setText("");
        ed_Landmark.setText("");
        ed_state.setText("");
        ed_pincode.setText("");
        ed_ans1.setText("");
//        ed_ans2.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }

    private void update() {
        ed_date.setText(dateFormat.format(calendar.getTime()));

    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {

        question1 = data.get(position).getId();

        //    Getsamequestion();

    }

//    private void Getsamequestion() {
//        if (question1 == question2 || question2 == question1) {
//            ed_ans1.setText("");
//            ed_ans2.setText("");
//            Toast.makeText(this, "" + "Your Question Are Same so Change One Question", Toast.LENGTH_LONG).show();
//        }
//    }

    public void onNothingSelected(AdapterView arg0) {
        // TODO Auto-generated method stub

    }


    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        //       Intent intent = getIntent();
//        Uri uri = intent.getData();
//        String foo = uri.getQueryParameter("foo");
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

}
