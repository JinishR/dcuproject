package com.jinish.oetielts;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> {

    private List<String> pdfFiles;
    private AssetManager assetManager;
    private Context context;

    public PDFAdapter(Context context, String[] pdfFiles) {
        this.context = context;
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
            openPDF(filename);
          //  String filePath = "file:///android_asset/" + filename;
          //  Uri pdfUri = Uri.parse(filePath);
           // Intent intent = new Intent(Intent.ACTION_VIEW);
          //  intent.setDataAndType(pdfUri, "application/pdf");
           // intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           // v.getContext().startActivity(intent);
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
            File tempFile = new File(context.getCacheDir(), filename);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            // Generate a content URI using FileProvider
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", tempFile);

            // Open the PDF file using an external PDF viewer
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(contentUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                context.startActivity(intent);
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
