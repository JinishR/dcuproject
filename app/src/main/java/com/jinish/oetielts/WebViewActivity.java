package com.jinish.oetielts;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WebViewActivity extends AppCompatActivity {

    private static final String PDF_DIRECTORY = "pdfs";
    private static final String FILE_EXTENSION = ".pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ImageView pdfImageView = findViewById(R.id.pdfImage);

        String filename = getIntent().getStringExtra("filename");
        String pdfFilePath = copyPdfFileToCache(filename);

        if (pdfFilePath != null) {
            displayPdf(pdfFilePath, pdfImageView);
        }
    }

    private String copyPdfFileToCache(String filename) {
        File cacheDir = getCacheDir();
        String pdfFileName = filename + FILE_EXTENSION;
        String cacheFilePath = cacheDir.getAbsolutePath() + File.separator + PDF_DIRECTORY + File.separator + pdfFileName;

        try {
            File cacheFile = new File(cacheFilePath);

            // Create the parent directory if it doesn't exist
            File parentDir = cacheFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!cacheFile.exists()) {
                // Copy the PDF file from assets to the cache directory
                InputStream inputStream = getAssets().open(pdfFileName);
                FileOutputStream outputStream = new FileOutputStream(cacheFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }

            return cacheFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void displayPdf(String pdfFilePath, ImageView imageView) {
        try {
            ParcelFileDescriptor fileDescriptor = ParcelFileDescriptor.open(new File(pdfFilePath), ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);
            PdfRenderer.Page currentPage = pdfRenderer.openPage(0);

            // Create a bitmap and render the PDF page onto it
            Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
            currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            // Set the bitmap to the ImageView
            imageView.setImageBitmap(bitmap);

            currentPage.close();
            pdfRenderer.close();
            fileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
