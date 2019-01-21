package com.maker.millionairekey.Activity;

import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.ReqestJson;
import com.maker.millionairekey.Rest.RetroClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

public class WithdrawActivity extends AppCompatActivity {
    TextView tv_current_bal;
    EditText ed_withdrewn;
    TextView ed_Requestid;
    Button iVProceed, iVGobackhome;
    ApiService apiService;
    Boolean Login;
    String adharno, ifcicode, accountno, branchname, bankname, panno;
    String Token;
    ImageView iv_loder;
    Dialog dialog;
    Boolean BANKINFO;
    int BANKBAL;
    String Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_withdraw);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        apiService = RetroClient.getClient().create(ApiService.class);
        GetToken getToken = new GetToken(WithdrawActivity.this);
        Token = getToken.mGetMyPref();
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        BANKINFO = prefs.getBoolean("BankInfo", false);
        BANKBAL = prefs.getInt("BANKBAL", 0);
        Status = getIntent().getStringExtra("PAYSTATUS");
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
        }
    }

    private void bindview() {
        iv_loder = findViewById(R.id.iv_loder);
        Glide.with(WithdrawActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        tv_current_bal = (TextView) findViewById(R.id.tv_current_bal);
        tv_current_bal.setText(BANKBAL + " ₹");
        ed_withdrewn = (EditText) findViewById(R.id.ed_withdrewn);
        ed_Requestid = (TextView) findViewById(R.id.ed_Requestid);
        iVProceed = (Button) findViewById(R.id.iVProceed);
        iVGobackhome = (Button) findViewById(R.id.iVGobackhome);
        iVGobackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WithdrawActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        if (Status.equals("yes")) {
            iVProceed.setEnabled(true);
            iVProceed.setClickable(true);
        } else {
            iVProceed.setEnabled(false);
            iVProceed.setClickable(false);

        }

        iVProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extra.isNetwork(getApplicationContext())) {

                    if (ed_withdrewn.getText().toString() == null || ed_withdrewn.getText().toString().isEmpty()) {
                        if (ed_withdrewn.getText().toString() == null || ed_withdrewn.getText().toString().isEmpty()) {
                            Toast.makeText(WithdrawActivity.this, "Please Enter Withdrawal Amount.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Integer amount = Integer.valueOf(ed_withdrewn.getText().toString());
                        if (10 > amount) {
                            Toast.makeText(WithdrawActivity.this, "Your Amount is 900/- Rupees to Low so You Can Not Withdraw Money", Toast.LENGTH_LONG).show();
                        } else {
                            if (BANKINFO == true) {

                                insertbankinfo(Token);

                            } else {
                                GetWithdrawAmount(Token, amount);
                            }
                        }
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    /// insert bank info
    private void insertbankinfo(String token) {
        dialog = new Dialog(WithdrawActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilog_bank_info);
        dialog.setCanceledOnTouchOutside(true);
        final TextInputLayout in_adharno = dialog.findViewById(R.id.in_adharno);
        final TextInputLayout in_panno = dialog.findViewById(R.id.in_panno);
        final TextInputLayout in_bankname = dialog.findViewById(R.id.in_bankname);
        final TextInputLayout in_brname = dialog.findViewById(R.id.in_brname);
        final TextInputLayout in_Acc_no = dialog.findViewById(R.id.in_Acc_no);
        final TextInputLayout in_ifcicode = dialog.findViewById(R.id.in_ifcicode);
        final TextInputEditText ed_AdharNo = dialog.findViewById(R.id.ed_AdharNo);
        final TextInputEditText ed_ifcicode = dialog.findViewById(R.id.ed_ifcicode);
        final TextInputEditText ed_Acc_no = dialog.findViewById(R.id.ed_Acc_no);
        final TextInputEditText ed_brname = dialog.findViewById(R.id.ed_brname);
        final TextInputEditText ed_bankname = dialog.findViewById(R.id.ed_bankname);
        final TextInputEditText ed_panno = dialog.findViewById(R.id.ed_panno);
        Button bt_prosstopay = dialog.findViewById(R.id.bt_prosstopay);
        bt_prosstopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                adharno = ed_AdharNo.getText().toString();
                ifcicode = ed_ifcicode.getText().toString();
                accountno = ed_Acc_no.getText().toString();
                branchname = ed_brname.getText().toString();
                panno = ed_panno.getText().toString();
                bankname = ed_bankname.getText().toString();
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(panno);


                if (adharno.equals(null) || adharno.isEmpty() || adharno.length() != 12 ||
                        ifcicode.equals(null) || ifcicode.isEmpty() ||
                        accountno.equals(null) || accountno.isEmpty() ||
                        branchname.equals(null) || branchname.isEmpty() ||
                        panno.equals(null) || panno.isEmpty() ||
                        bankname.equals(null) || bankname.isEmpty()) {
                    if (adharno.equals(null) || adharno.isEmpty()) {
                        in_adharno.setError("Please Enter Validation AdharNo.");
                    } else {
                        in_adharno.setErrorEnabled(false);
                    }
                    if (branchname.equals(null) || branchname.isEmpty()) {
                        in_brname.setError("Please Enter Baranch Name.");
                    } else {
                        in_brname.setErrorEnabled(false);
                    }
                    if (ifcicode.equals(null) || ifcicode.isEmpty()) {
                        in_ifcicode.setError("Please Enter IFCI Code.");
                    } else {
                        in_ifcicode.setErrorEnabled(false);
                    }
                    if (panno.equals(null) || panno.isEmpty()) {
                        in_panno.setError("Please Enter Validation  PAN No.");
                    } else {
                        in_panno.setErrorEnabled(false);
                    }
                    if (bankname.equals(null) || bankname.isEmpty()) {
                        in_bankname.setError("Please Enter Bank Name.");
                    } else {
                        in_bankname.setErrorEnabled(false);
                    }
                    if (accountno.equals(null) || accountno.isEmpty()) {
                        in_Acc_no.setError("Please Enter Account No.");
                    } else {
                        in_Acc_no.setErrorEnabled(false);
                    }
                } else {
                    if (matcher.matches()) {
                        insertBankinfo(adharno, ifcicode, accountno, branchname, panno, bankname);
                        // Toast.makeText(WithdrawActivity.this, "" + "is Validation PAN No.", Toast.LENGTH_SHORT).show();
                        //  Log.i("Matching","Yes");

                    } else {
                        Toast.makeText(WithdrawActivity.this, "" + "Please Enter Validation PAN No.", Toast.LENGTH_SHORT).show();
//
                    }

                }
            }
        });

        dialog.show();
    }

    private void insertBankinfo(String adharno, String ifcicode, String accountno, String branchname, final String panno, String bankname) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Example example = new Example();
            example.setPanno(panno);
            example.setBankname(bankname);
            example.setBranchname(branchname);
            example.setAcno(accountno);
            example.setIfsccode(ifcicode);
            example.setAadharcard(adharno);
            Gson gson = new Gson();
            String json = gson.toJson(example);
            Call<Example> call = apiService.getInsertBankinfo(Token, json);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, final retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus().equals("fail")) {
                            //      Toast.makeText(WithdrawActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            //  Toast.makeText(WithdrawActivity.this, ">>> " + response.body().getData(), Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = WithdrawActivity.this.getSharedPreferences("myPref", MODE_PRIVATE).edit();
                            editor.putBoolean("BankInfo", response.body().getClassdata().getIsBankInfo());
                            editor.commit();

                            dialog.dismiss();
                            //        Toast.makeText(WithdrawActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        SharedPreferences settings = WithdrawActivity.this.getSharedPreferences("myPref", WithdrawActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(WithdrawActivity.this, HomeActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
                    //  Toast.makeText(WithdrawActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed()

    {
        super.onBackPressed();
        this.finish();
    }
    // withdrawamount

    private void GetWithdrawAmount(String token, Integer amount) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            ReqestJson reqestJson = new ReqestJson();
            reqestJson.setAmount(amount);

            Gson gson = new Gson();
            String json = gson.toJson(reqestJson);

            Log.d("LOGIN", "LOGINDATA" + json + " >> " + token);
            Call<Example> call = apiService.getwithdrawamount(token, json);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        Log.e(">>>>>", "onResponse: " + response.body());
                        if (response.body().getStatus().equals("fail")) {
                            ed_Requestid.setText("" + response.body().getData());
                            Toast.makeText(WithdrawActivity.this, response.body().getInfo(), Toast.LENGTH_LONG).show();
                        } else {
                            ed_Requestid.setText("");
                            ed_Requestid.setText("" + response.body().getData());
                            Toast.makeText(WithdrawActivity.this, response.body().getInfo() + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        SharedPreferences settings = WithdrawActivity.this.getSharedPreferences("myPref", WithdrawActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(WithdrawActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                        //     Toast.makeText(WithdrawActivity.this, "Invalid User Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
                    //     Toast.makeText(WithdrawActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
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
}
