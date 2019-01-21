package com.maker.millionairekey.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maker.millionairekey.R;

public class ContactUsActivity extends AppCompatActivity {
    ImageView icon_cancel;
    int dataitem = 0;
    LinearLayout ll_4,ll_3,ll_2,ll_1;
    View v_4,v_1,v_2,v_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contect_us_box);
        dataitem = getIntent().getIntExtra("DATA", 0);
        ll_4 =  findViewById(R.id.ll_4);
        ll_3 =  findViewById(R.id.ll_3);
        ll_2 =  findViewById(R.id.ll_2);
        ll_1 =  findViewById(R.id.ll_1);
        v_4=  findViewById(R.id.v_4);
        v_1=  findViewById(R.id.v_1);
        v_3=  findViewById(R.id.v_3);
        v_2=  findViewById(R.id.v_2);
        if (dataitem == 0) {
            ll_4.setVisibility(View.VISIBLE);
            ll_3.setVisibility(View.VISIBLE);
            ll_2.setVisibility(View.VISIBLE);
            ll_1.setVisibility(View.VISIBLE);
            v_4.setVisibility(View.VISIBLE);
            v_1.setVisibility(View.VISIBLE);
            v_2.setVisibility(View.VISIBLE);
            v_3.setVisibility(View.VISIBLE);
        } else {
            ll_4.setVisibility(View.GONE);
            ll_3.setVisibility(View.GONE);
            ll_2.setVisibility(View.GONE);
            ll_1.setVisibility(View.GONE);
            v_4.setVisibility(View.GONE);
            v_1.setVisibility(View.GONE);
            v_2.setVisibility(View.GONE);
            v_3.setVisibility(View.GONE);
        }
        icon_cancel = findViewById(R.id.icon_cancel);
        // show it
        icon_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
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
