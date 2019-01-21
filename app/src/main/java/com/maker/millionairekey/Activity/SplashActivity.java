package com.maker.millionairekey.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.GetToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Branch branch = Branch.getInstance();
        ImageView imageView = findViewById(R.id.iv_img);
        Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        imageView.setAnimation(zoomout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetToken getToken = new GetToken(SplashActivity.this);
                String Token = getToken.mGetMyPref();
                if (Token == null) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);

        // Branch init
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {

                    String str = referringParams.toString();
                    String mycode ="";
                    try {
                        JSONObject obj = new JSONObject(str);
                        mycode = obj.getString("REFDATA");
                        SharedPreferences.Editor editor = SplashActivity.this.getSharedPreferences("myPref", MODE_PRIVATE).edit();
                        editor.putString("MYCODEDATA", mycode);
                        editor.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

               //     Toast.makeText(SplashActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, this.getIntent().getData(), this);


    }
    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

}
