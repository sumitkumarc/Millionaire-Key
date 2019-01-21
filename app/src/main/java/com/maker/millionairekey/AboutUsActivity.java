package com.maker.millionairekey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ImageView icon_cancel = (ImageView) findViewById(R.id.icon_cancel);
        Button bt_Ok = findViewById(R.id.bt_Ok);
        WebView webView = findViewById(R.id.webview);
        webView.loadUrl("http://millionairekey.in/aboutus.html");
        // show it
        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
        icon_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }

}
