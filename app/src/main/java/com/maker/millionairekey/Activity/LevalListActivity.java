package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.maker.millionairekey.Adapter.RecyclerView_Leval_List;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.Leveldatum;
import com.maker.millionairekey.Rest.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LevalListActivity extends AppCompatActivity {
    ApiService apiService;
    RecyclerView recyclerView;
    String token;
    private List<Leveldatum> list = new ArrayList<>();
    private RecyclerView_Leval_List mAdapter;
    ImageView iv_loder;
    String Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leval_list);

        if (Extra.isNetwork(getApplicationContext())) {
            recyclerView = (RecyclerView) findViewById(R.id.rv_tran_recycle);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setVisibility(View.GONE);
            apiService = RetroClient.getClient().create(ApiService.class);
            GetToken getToken = new GetToken(LevalListActivity.this);
            token = getToken.mGetMyPref();
            iv_loder = (ImageView) findViewById(R.id.iv_loder);
            Glide.with(LevalListActivity.this)
                    .load(R.raw.orig)
                    .into(iv_loder);
            iv_loder.setVisibility(View.GONE);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Leval "+ getIntent().getStringExtra("LEVAL_NAME") + " : Member");
            apiService = RetroClient.getClient().create(ApiService.class);
            actionBar.setDisplayHomeAsUpEnabled(true);
            Status =  getIntent().getStringExtra("PAYSTATUS");

            Intent intent = getIntent();
            getLevalList(token, intent.getExtras().getString("LEVAL_ID"));

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

    private void getLevalList(String token, String leval_id) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Leveldatum example = new Leveldatum();
            example.setLevelno(leval_id);
            example.setUsername("");

            Gson gson = new Gson();
            String json = gson.toJson(example);

            Log.d(">>>. ", "getAllTransaction: " + token);
            Call<Example> call = apiService.GetLevalListData(token, json);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);
                    try {
                        int size = response.body().getClassdata().getLeveldata().size();
                        if (size <= 0) {
//                            Toast.makeText(LevalListActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            list = response.body().getClassdata().getLeveldata();
                            mAdapter = new RecyclerView_Leval_List(list, LevalListActivity.this);
                            recyclerView.setAdapter(mAdapter);

//                            Toast.makeText(LevalListActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        SharedPreferences settings = LevalListActivity.this.getSharedPreferences("myPref", LevalListActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(LevalListActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
//                        Toast.makeText(LevalListActivity.this, "Some Thing Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
//                    Toast.makeText(LevalListActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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
