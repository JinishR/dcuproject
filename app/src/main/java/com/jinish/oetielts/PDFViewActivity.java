package com.jinish.oetielts;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PDFViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        RecyclerView recyclerView = findViewById(R.id.pdfRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PDFAdapter pdfAdapter = new PDFAdapter(this, getPDFFilesFromAssets());
        recyclerView.setAdapter(pdfAdapter);
    }

    private String[] getPDFFilesFromAssets() {
        try {
            return getAssets().list("pdfs");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
