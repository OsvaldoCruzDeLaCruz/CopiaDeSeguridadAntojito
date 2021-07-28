package com.restaurante.antojitoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateACcountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog lodingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateACcountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_user_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        lodingBar = new ProgressDialog(this);

        CreateACcountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount() {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String admin = "0";


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Porfavor, ingresa tu nombre", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Porfavor, ingresa tu numero celular", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Porfavor, ingresa tu constraeña", Toast.LENGTH_SHORT).show();
        }
        else{
            lodingBar.setTitle("Cuenta creada!");
            lodingBar.setMessage("Porfavor espera, estamos comprobando los datos!");
            lodingBar.setCanceledOnTouchOutside(false);
            lodingBar.show();

            ValidatePhoneNumber(name, phone, password, admin);
        }


    }

    private void ValidatePhoneNumber(String name, String phone, String password, String admin) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(!(snapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("usuario", name);
                    userDataMap.put("admin", admin);
                    userDataMap.put("password", password);

                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "La cuenta se ha creado con exito!", Toast.LENGTH_SHORT).show();
                                    lodingBar.dismiss();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else {
                                    lodingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Error de conexion. Intentalo de nuevo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }else{
                    Toast.makeText(RegisterActivity.this, "Ya existe un usuario con ese numero celular", Toast.LENGTH_SHORT).show();
                    lodingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Usa otro numero de celular", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}