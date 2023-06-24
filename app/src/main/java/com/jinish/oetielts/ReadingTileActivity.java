package com.jinish.oetielts;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class ReadingTileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] pdfFiles = {"file1.pdf", "file2.pdf", "file3.pdf", "file4.pdf", "file5.pdf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_tile);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PDFAdapter(this, pdfFiles));
    }

    public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> {

        private List<String> pdfFiles;
        private AssetManager assetManager;

        public PDFAdapter(Context context, String[] pdfFiles) {
            this.pdfFiles = new ArrayList<>(Arrays.asList(pdfFiles));
            this.assetManager = context.getAssets();
        }

        @NonNull
        @Override
        public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent, false);
            return new PDFViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
            String filename = pdfFiles.get(position);
            holder.fileName.setText(filename);
            holder.viewButton.setOnClickListener(v -> {
                // Open the PDF file when the view button is clicked
                openPDF(filename);
            });
        }

        @Override
        public int getItemCount() {
            return pdfFiles.size();
        }

        public class PDFViewHolder extends RecyclerView.ViewHolder {
            TextView fileName;
            Button viewButton;

            public PDFViewHolder(View itemView) {
                super(itemView);
                fileName = itemView.findViewById(R.id.file_name);
                viewButton = itemView.findViewById(R.id.view_button);
            }
        }

        private void openPDF(String filename) {
            try {
                // Load the PDF file from assets
                InputStream inputStream = assetManager.open(filename);

                // Create a temporary file to copy the PDF contents
                File tempFile = new File(getCacheDir(), filename);
                FileOutputStream outputStream = new FileOutputStream(tempFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();

                // Open the PDF file using an external PDF viewer
                Uri uri = Uri.fromFile(tempFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    // Handle the case where a PDF viewer app is not installed
                    e.printStackTrace();
                    // Display a message or fallback option to the user
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
