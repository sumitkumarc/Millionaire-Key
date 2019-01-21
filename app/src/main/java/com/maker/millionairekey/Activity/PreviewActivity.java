package com.maker.millionairekey.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

//import com.github.barteksc.pdfviewer.PDFView;
import com.maker.millionairekey.R;

import java.io.File;

public class PreviewActivity extends AppCompatActivity {

  // PDFView pdfView;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

       //pdfView=findViewById(R.id.pdfview);

        String path=getIntent().getStringExtra("Path");

        Bundle bundle=getIntent().getExtras();

        if (path.isEmpty()){

            Toast.makeText(getApplicationContext(),"Path Must be Empty",Toast.LENGTH_SHORT).show();

        }else{

            file=new File(path);
//            pdfView.fromFile(file)
//                    .enableSwipe(true)
//                    .swipeHorizontal(false)
//                    .enableDoubletap(true)
//                    .enableAntialiasing(true)
//                    .load();
        }

    }
}
