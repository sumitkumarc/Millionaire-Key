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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.maker.millionairekey.Adapter.Rec_AchievementList;
import com.maker.millionairekey.Adapter.RecyclerView_Leval_List;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.Achievementdatum;
import com.maker.millionairekey.Rest.AchievmentExample;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.Leveldatum;
import com.maker.millionairekey.Rest.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AchievementActivity extends AppCompatActivity {
    ApiService apiService;
    RecyclerView recyclerView;
    String token;
    private List<Achievementdatum> list = new ArrayList<>();
    private Rec_AchievementList mAdapter;
    ImageView iv_loder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        if (Extra.isNetwork(getApplicationContext())) {
            recyclerView = (RecyclerView) findViewById(R.id.rv_tran_recycle);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setVisibility(View.GONE);
            apiService = RetroClient.getClient().create(ApiService.class);
            GetToken getToken = new GetToken(AchievementActivity.this);
            token = getToken.mGetMyPref();
            iv_loder = (ImageView) findViewById(R.id.iv_loder);
            Glide.with(AchievementActivity.this)
                    .load(R.raw.orig)
                    .into(iv_loder);
            iv_loder.setVisibility(View.GONE);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("User Achievement");
            apiService = RetroClient.getClient().create(ApiService.class);
            actionBar.setDisplayHomeAsUpEnabled(true);

            Intent intent = getIntent();
            getAchievementList(token);

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

    private void getAchievementList(String token) {
        iv_loder.setVisibility(View.VISIBLE);
        try {

            Call<AchievmentExample> call = apiService.GetAchievementListData(token);
            call.enqueue(new Callback<AchievmentExample>() {
                @Override
                public void onResponse(Call<AchievmentExample> call, retrofit2.Response<AchievmentExample> response) {
                    iv_loder.setVisibility(View.GONE);
                   try {

                        if (response.body().getStatus().equals("fail")) {
                            Toast.makeText(AchievementActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            list = response.body().getClassdata();
                            Log.d("DATASIZE","MAINDATASIZE" +list);
                            mAdapter = new Rec_AchievementList(list, AchievementActivity.this);
                            recyclerView.setAdapter(mAdapter);
                        }

                    } catch (Exception e) {
                        SharedPreferences settings = AchievementActivity.this.getSharedPreferences("myPref", AchievementActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Intent inlogout = new Intent(AchievementActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<AchievmentExample> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
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
