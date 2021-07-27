package com.restaurante.antojitoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QrActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
    }

    public void genereteQR(View view){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
//            BitMatrix bitMatrix = multiFormatWriter.encode()
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}