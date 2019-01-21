package com.maker.millionairekey.Activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.maker.millionairekey.Adapter.RecyclerView_Transaction;
import com.maker.millionairekey.Model.Extra;
import com.maker.millionairekey.R;
import com.maker.millionairekey.Rest.ApiService;
import com.maker.millionairekey.Rest.Classdatum;
import com.maker.millionairekey.Rest.Datum;
import com.maker.millionairekey.Rest.GetToken;
import com.maker.millionairekey.Rest.RetroClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TransactionActivity extends AppCompatActivity {
    ApiService apiService;
    RecyclerView recyclerView;

    LinearLayout ivnotfound;
    TextView txt_warning;
    List<Classdatum> data;
    ImageView iv_loder;

    String token;
    File pdfFile;

    Font ftext = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL, BaseColor.WHITE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_transaction);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_transaction);
        actionBar.setDisplayHomeAsUpEnabled(true);

        apiService = RetroClient.getClient().create(ApiService.class);
        GetToken getToken = new GetToken(TransactionActivity.this);
//        createDirIfNotExists();
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
        Glide.with(TransactionActivity.this)
                .load(R.raw.orig)
                .into(iv_loder);
        iv_loder.setVisibility(View.GONE);


        ivnotfound = (LinearLayout) findViewById(R.id.iv_notfound);
        recyclerView = (RecyclerView) findViewById(R.id.rv_tran_recycle);
        txt_warning = (TextView) findViewById(R.id.txt_warning);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.GONE);


        GetToken getToken = new GetToken(TransactionActivity.this);
        String Token = getToken.mGetMyPref();
        getAllTransaction(Token);
    }

    private void getAllTransaction(String token) {
        iv_loder.setVisibility(View.VISIBLE);
        try {
            Log.d(">>>. ", "getAllTransaction: " + token);
            Call<Datum> call = apiService.GetAllTransaction(token);
            call.enqueue(new Callback<Datum>() {
                @Override
                public void onResponse(Call<Datum> call, retrofit2.Response<Datum> response) {
                    iv_loder.setVisibility(View.GONE);

                    try {
                        data = response.body().getClassdata();
                        if (response.body().getStatus().equals("fail")) {
                            ivnotfound.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            txt_warning.setText("" + response.body().getInfo());

                        } else {
                            ivnotfound.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new RecyclerView_Transaction(TransactionActivity.this, data));

                        }
                    } catch (Exception ex) {
                        SharedPreferences settings = TransactionActivity.this.getSharedPreferences("myPref", TransactionActivity.this.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent inlogout = new Intent(TransactionActivity.this, LogInActivity.class);
                        inlogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inlogout);
                        finish();

                    }

                }

                @Override
                public void onFailure(Call<Datum> call, Throwable t) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.pdf:
                createPdf();
                return true;


            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void createPdf() {
        String FILE = Environment.getExternalStorageDirectory().toString() + "/MillionaireKeyPDF/" + "MillionaireKeyPDF.pdf";
        Document document = new Document(PageSize.A4);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/MillionaireKeyPDF");
        myDir.mkdirs();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        document.close();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MillionaireKeyPDF", "MillionaireKeyPDF.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent1 = Intent.createChooser(intent, "Open With");
        try {
            startActivity(intent1);
        } catch (ActivityNotFoundException e) {
        }


        Toast.makeText(this, "PDF File is Created. Location : " + FILE, Toast.LENGTH_LONG).show();
    }

    public void addMetaData(Document document)

    {
        document.addTitle("RESUME");
        document.addSubject("Person Info");
        document.addKeywords("Personal,	Education, Skills");
        document.addAuthor("TAG");
        document.addCreator("TAG");
    }

    public void addTitlePage(Document document) throws DocumentException {
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD, BaseColor.WHITE);
        Chunk c = new Chunk("Millionaire Key Transaction Details", f);
        PdfPTable myTable = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Phrase(c));

        cell.setBackgroundColor(BaseColor.RED);
        cell.setPadding(10);
        myTable.addCell(cell);
        myTable.setWidthPercentage(100);

        document.add(myTable);

        PdfPTable table = new PdfPTable(5);
        table.setSpacingBefore(10);
        PdfPCell c1 = new PdfPCell(new Phrase("No", ftext));
        c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        c1.setBorderColor(BaseColor.WHITE);
        c1.setPadding(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Date", ftext));
        c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        c1.setBorderColor(BaseColor.WHITE);
        c1.setPadding(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Transaction No", ftext));
        c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        c1.setBorderColor(BaseColor.WHITE);
        c1.setPadding(2);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("Amount", ftext));
        c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        c1.setBorderColor(BaseColor.WHITE);
        c1.setPadding(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Status", ftext));
        c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        c1.setBorderColor(BaseColor.WHITE);
        c1.setPadding(2);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (int i = 0; i < data.size(); i++) {
            table.addCell(String.valueOf(i + 1));
            table.addCell(data.get(i).getOndate().toString());
            table.addCell(data.get(i).getTransactionno().toString());
            table.addCell(data.get(i).getAmount().toString());
            table.addCell(data.get(i).getStatus().toString());

        }
        document.add(table);
        document.newPage();
    }


    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }


//    private void createPdf() throws FileNotFoundException, DocumentException {
//
//
//        OutputStream output;
//        String ss = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String path = String.valueOf(Environment.getExternalStoragePublicDirectory("Millionaire_Key_Tra" + "/" + "Millionaire_Key_Tra.pdf"));
//        Log.d("DATAMAIN001", "DATAMAIN001" + path);
//        pdfFile = new File(path);
//        output = new FileOutputStream(pdfFile);
//        PdfWriter.getInstance(document, output);
//
//        document.open();
//
//        //page title
//        addImageIndText();
//        addtbl_title();
//        addcreate_tbl();
//        document.close();
//        appViewpdf();
//
//    }
}
