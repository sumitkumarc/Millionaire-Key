package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maker.millionairekey.Adapter.RecyclerView_NewsAdapter;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Classdatum;
import com.maker.millionairekey.Rest.Datum;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.RetroClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sumit on 8/12/2018.
 */

public class NewsActivity extends AppCompatActivity {
    ApiService apiService;
    ImageView iv_loder;
    RecyclerView recyclerView;
    LinearLayout ivnotfound;
    TextView txt_warning;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_transaction);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_news);
        actionBar.setDisplayHomeAsUpEnabled(true);

        apiService = RetroClient.getClient().create(ApiService.class);
        GetToken getToken = new GetToken(NewsActivity.this);
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
        Glide.with(NewsActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        ivnotfound = (LinearLayout) findViewById(R.id.iv_notfound);
        recyclerView = (RecyclerView) findViewById(R.id.rv_tran_recycle);
        txt_warning = (TextView) findViewById(R.id.txt_warning);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GetToken getToken = new GetToken(NewsActivity.this);
        String Token = getToken.mGetMyPref();
        getAllnews(Token);
    }

    private void getAllnews(String token) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Log.d(">>>. ", "getAllTransaction: " + token);
            Call<Datum> call = apiService.GetAllNews();
            call.enqueue(new Callback<Datum>() {
                @Override
                public void onResponse(Call<Datum> call, retrofit2.Response<Datum> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        List<Classdatum> data = response.body().getClassdata();
                        if (response.body().getStatus().equals("fail")) {
                            ivnotfound.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            txt_warning.setText("" + response.body().getInfo());
                       //     Toast.makeText(NewsActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        } else {
                            ivnotfound.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new RecyclerView_NewsAdapter(NewsActivity.this, data));
                         //   Toast.makeText(NewsActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        SharedPreferences settings = NewsActivity.this.getSharedPreferences("myPref", NewsActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(NewsActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                     //   Toast.makeText(NewsActivity.this, "Invalid User Login", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Datum> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
                    Toast.makeText(NewsActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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
