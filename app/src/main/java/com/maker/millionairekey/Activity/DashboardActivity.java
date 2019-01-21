package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.maker.millionairekey.AboutUsActivity;
import com.maker.millionairekey.Adapter.RecyclerView_Leval;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Example;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.Leveldatum;
import com.maker.millionairekey.Rest.RetroClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import retrofit2.Call;
import retrofit2.Callback;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout ll_name;
    TextView tv_name, tv_total_Ern, tv_paid_total, tv_current_bal, tv_Reference, tv_start;
    ApiService apiService;
    String token;
    LinearLayout ll_main;
    RecyclerView recyclerView;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private List<Leveldatum> list = new ArrayList<>();
    private RecyclerView_Leval mAdapter;
    Toolbar mytoolbar;
    ImageView iv_loder;
    String Username;
    String USERCODE;
    double amount, totalpaid;
    String currentVersion;
    String status;
    String SHAREDATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        mytoolbar.setTitle(R.string.app_desbord);
        setSupportActionBar(mytoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_desbord);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        apiService = RetroClient.getClient().create(ApiService.class);
        actionBar.setDisplayHomeAsUpEnabled(true);
        iv_loder = (ImageView) findViewById(R.id.iv_loder);
        Glide.with(DashboardActivity.this).load(R.raw.orig).into(iv_loder);
        iv_loder.setVisibility(View.GONE);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USERCODE = prefs.getString("USERCODE", "");
        recyclerView = (RecyclerView) findViewById(R.id.rv_tran_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        apiService = RetroClient.getClient().create(ApiService.class);
        GetToken getToken = new GetToken(DashboardActivity.this);
        token = getToken.mGetMyPref();
        if (Extra.isNetwork(getApplicationContext())) {
            bindview();
            getDashBord(token);
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

        BranchUniversalObject buo = new BranchUniversalObject()
                .setCanonicalIdentifier("content/12345")
                .setTitle("Millionaire Key")
                .setContentImageUrl("https://lorempixel.com/400/400")
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setContentMetadata(new ContentMetadata().addCustomMetadata("key1", "value1"));


        LinkProperties lp = new LinkProperties()
                .setChannel("facebook")
                .setFeature("sharing")
                .setCampaign("content 123 launch")
                .setStage("new user")
                .addControlParameter("$desktop_url", "http://example.com/home")
                .addControlParameter("custom", "data")
                .addControlParameter("REFDATA", USERCODE.toString().toUpperCase())
                .addControlParameter("custom_random", Long.toString(Calendar.getInstance().getTimeInMillis()));

        buo.generateShortUrl(this, lp, new Branch.BranchLinkCreateListener() {
            @Override
            public void onLinkCreate(String url, BranchError error) {
                if (error == null) {
                    SHAREDATA = url;
                    //   Toast.makeText(DashboardActivity.this, "" + SHAREDATA, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDashBord(String token) {
        ll_main.setVisibility(View.GONE);
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Call<Example> call = apiService.GetDashboard(token);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                    iv_loder.setVisibility(View.GONE);

                    try {
                        if (response.body().getStatus().equals("fail")) {
                            //    Toast.makeText(DashboardActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        } else {

                            ll_main.setVisibility(View.VISIBLE);
                            Username = response.body().getClassdata().getUsername();
                            status = response.body().getClassdata().getIncomestatus().toLowerCase();
                            tv_name.setText("User Name : " + response.body().getClassdata().getUsername());

                            try {
                                tv_start.setText("( " + response.body().getClassdata().getAchievementLevel() + " )");
                            } catch (Exception e) {
                            }
                            tv_total_Ern.setText("");
                            tv_paid_total.setText("");
                            if (response.body().getClassdata().getBalance() == null) {
                                tv_current_bal.setText("0.00");
                                amount = 0;
                            } else {
                                amount = response.body().getClassdata().getBalance();
                                tv_current_bal.setText("" + response.body().getClassdata().getBalance());
                            }
                            SharedPreferences.Editor editor = DashboardActivity.this.getSharedPreferences("myPref", MODE_PRIVATE).edit();
                            editor.putBoolean("BankInfo", response.body().getClassdata().getIsBankInfo());
                            editor.putInt("BANKBAL", (int) amount);
                            editor.commit();
                            tv_Reference.setText("" + response.body().getClassdata().getTotalreffral());
                            tv_total_Ern.setText("" + response.body().getClassdata().getEarning());
                            tv_paid_total.setText("" + response.body().getClassdata().getTotalpaid());
                            list = response.body().getClassdata().getLeveldata();
                            int largeMember = response.body().getClassdata().getTotalreffral();
                            mAdapter = new RecyclerView_Leval(list, DashboardActivity.this, largeMember);
                            recyclerView.setAdapter(mAdapter);

                            //  Toast.makeText(DashboardActivity.this, "" + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        SharedPreferences settings = DashboardActivity.this.getSharedPreferences("myPref", DashboardActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Intent inlogout = new Intent(DashboardActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();
////                        Toast.makeText(DashboardActivity.this, "Some Thing Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    iv_loder.setVisibility(View.GONE);
                    onBackPressed();
//                    Toast.makeText(DashboardActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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

    private void bindview() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationview);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_total_Ern = (TextView) findViewById(R.id.tv_total_Ern);
        tv_paid_total = (TextView) findViewById(R.id.tv_paid_total);
        tv_current_bal = (TextView) findViewById(R.id.tv_current_bal);
        tv_Reference = (TextView) findViewById(R.id.tv_Reference);
        ll_name = (LinearLayout) findViewById(R.id.ll_name);
        ll_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inlogin = new Intent(DashboardActivity.this, ChangPasswordActivity.class);
                startActivity(inlogin);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();
                    }
                }, 200);


                if (menuItem.getItemId() == R.id.nav_about) {
                    Intent inlogin = new Intent(getApplicationContext(), AboutUsActivity.class);
                    startActivity(inlogin);
                }
                if (menuItem.getItemId() == R.id.nav_profile) {
                    if (Extra.isNetwork(getApplicationContext())) {
                        Intent intent = new Intent(DashboardActivity.this, MyProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashboardActivity.this, "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                    }
                }
                if (menuItem.getItemId() == R.id.nav_chnagepwd) {
                    if (Extra.isNetwork(getApplicationContext())) {
                        Intent intent = new Intent(DashboardActivity.this, ChangPasswordActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashboardActivity.this, "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                    }
                }
                if (menuItem.getItemId() == R.id.nav_contact) {
                    Intent inlogin = new Intent(DashboardActivity.this, ContactUsActivity.class);
                    startActivity(inlogin);
                }
                if (menuItem.getItemId() == R.id.nav_home) {

                }
                if (menuItem.getItemId() == R.id.nav_Des) {
                    Toast.makeText(DashboardActivity.this, "Coming Soon......", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.nav_achievement) {
                    Intent inlogin = new Intent(DashboardActivity.this, AchievementActivity.class);
                    startActivity(inlogin);
                    //   Toast.makeText(DashboardActivity.this, "Coming Soon......", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.nav_news) {
                    if (Extra.isNetwork(getApplicationContext())) {
                        Intent intent = new Intent(DashboardActivity.this, NewsActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashboardActivity.this, "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItem.getItemId() == R.id.nav_rate) {
                    if (Extra.isNetwork(getApplicationContext())) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } else {
                        Toast.makeText(DashboardActivity.this, "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                    }
//                    Intent intent = new Intent(DashboardActivity.this, StaffActivity.class);
//                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_share) {
                    if (Extra.isNetwork(getApplicationContext())) {
//                        Intent sendIntent = new Intent();
//                        sendIntent.setAction(Intent.ACTION_SEND);
//                        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.maker.millionairekey&referrer=utm_source=MK0001");
//                        sendIntent.putExtra(Intent.EXTRA_SUBJECT,"invitation_subject");
//                        sendIntent.setType("text/plain");
//                        startActivity(Intent.createChooser(sendIntent, "invitation_extended_title"));
                        try {
                            String ExternalString = String.valueOf(Html.fromHtml("<p>I'am inviting you to start your personal business, a proper and perfect application to earn. Here's my code " + USERCODE.toString().toUpperCase() + " just enter it while your registration. If you will install you will also get lots of reward! </p><p>" + SHAREDATA + "</p>"));
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, ExternalString);
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                        } catch (Exception e) {
                        }
                    } else {
                        Toast.makeText(DashboardActivity.this, "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }

        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mytoolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.mmWithdraw:
                // if (Extra.isNetwork(getApplicationContext())) {
//                if (amount < 1000) {
//                    Toast.makeText(this, "Your Amount is 1000/- Rupees to Low so You Can Not Withdraw Money", Toast.LENGTH_LONG).show();
//                } else {
                Intent intent = new Intent(DashboardActivity.this, WithdrawActivity.class);
                intent.putExtra("PAYSTATUS", status);
                startActivity(intent);
//                }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
//                }

                return true;

            case R.id.mmTransaction:
                if (Extra.isNetwork(getApplicationContext())) {
//                    Toast.makeText(this, "You Are Not Complete Any level..", Toast.LENGTH_LONG).show();
//                    } else {
                    Intent inlogin = new Intent(DashboardActivity.this, TransactionActivity.class);
                    startActivity(inlogin);
//
//                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }


                return true;
            case R.id.mmLogOut:
                if (Extra.isNetwork(getApplicationContext())) {
                    SharedPreferences settings = DashboardActivity.this.getSharedPreferences("myPref", DashboardActivity.this.MODE_PRIVATE);
                    settings.edit().clear().commit();

                    Intent inlogout = new Intent(DashboardActivity.this, HomeActivity.class);
                    inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(inlogout);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }
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

    @Override
    protected void onStart() {
        super.onStart();
        if (Extra.isNetwork(getApplicationContext())) {
            bindview();
            getDashBord(token);
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
}
