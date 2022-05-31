package com.example.base64converter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        String path = getIntent().getStringExtra("IMG_ARRAY_PATH");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        saveText(bitmap);
    }

    protected void saveText(Bitmap newBitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded =Base64.encodeToString(byteArray,Base64.NO_WRAP);
        File root = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"APP");
        if (!root.exists()){
            root.mkdirs();
        }
        File file = new File(root,"base64.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.append(encoded);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("PATH: "+file.getAbsolutePath());
        textView.setText("File saved at "+file.getAbsolutePath());
        imageView.setImageBitmap(newBitmap);
    }
}