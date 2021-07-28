package com.restaurante.antojitoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.restaurante.antojitoapp.Model.ListElement;

public class QrActivity extends AppCompatActivity {

    ImageView imageView;
    String idOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        String element = (String) getIntent().getSerializableExtra("idOrder");
        idOrder = element.toString();
        imageView = findViewById(R.id.imageQR);
        genereteQR();

    }

    public void genereteQR(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(idOrder, BarcodeFormat.QR_CODE, 100, 100 );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}