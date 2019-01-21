package com.maker.millionairekey.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.maker.millionairekey.AboutUsActivity;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    BottomSheetDialog dialog;
    Button bt_About, bt_login, bt_new_user, bt_contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        checkAndRequestPermissions();

    }
    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 12);
            return false;
        }
        return true;
    }


    private void bindview() {
        bt_About = (Button) findViewById(R.id.bt_About);
        bt_new_user = (Button) findViewById(R.id.bt_new_user);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extra.isNetwork(getApplicationContext())) {
                    Intent inlogin = new Intent(HomeActivity.this, LogInActivity.class);
                    startActivity(inlogin);
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        bt_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inlogin = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(inlogin);
                //  overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                //   openaboutusDialog();
            }
        });
        bt_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extra.isNetwork(getApplicationContext())) {
                    Intent inlogin = new Intent(HomeActivity.this, SignupActivity.class);
                    startActivity(inlogin);
                } else {
                    Toast.makeText(getApplicationContext(), "Internet is not working, Make sure that you have active internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });
        bt_contactus = findViewById(R.id.bt_contactus);
        bt_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inlogin = new Intent(HomeActivity.this, ContactUsActivity.class);
                inlogin.putExtra("DATA" ,1);
                startActivity(inlogin);
//                LayoutInflater li = (LayoutInflater) HomeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View promptsView1 = li.inflate(R.layout.contect_us_box, null);
//                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(HomeActivity.this);
//                ImageView icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
//                alertDialogBuilder1.setView(promptsView1);
//                alertDialogBuilder1.setCancelable(true);
//                final AlertDialog alertDialog1 = alertDialogBuilder1.create();
//                icon_cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog1.dismiss();
//                    }
//                });
//                alertDialog1.show();
//
            }
        });
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
