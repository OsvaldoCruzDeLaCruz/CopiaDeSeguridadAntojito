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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurante.antojitoapp.Model.Users;

public class LoginActivity extends AppCompatActivity {

    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog lodingBar;
    private String parentDbName = "Users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        lodingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();




        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Porfavor, ingresa tu numero celular", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Porfavor, ingresa tu constraeña", Toast.LENGTH_SHORT).show();
        }else{
            lodingBar.setTitle("Cuenta accedida!");
            lodingBar.setMessage("Porfavor espera, estamos comprobando los datos!");
            lodingBar.setCanceledOnTouchOutside(false);
            lodingBar.show();

            AllowAccessToAcount(phone, password);
        }

    }

    private void AllowAccessToAcount(String phone, String password) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(phone).exists()){

                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)){

                        if(usersData.getPassword().equals(password)){

                            Toast.makeText(LoginActivity.this, "Accedio correctamente", Toast.LENGTH_SHORT).show();
                            lodingBar.dismiss();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            lodingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "No existe ninguna cuenta con este numero celular", Toast.LENGTH_SHORT).show();
                    lodingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Usa una cuenta existente o crea una", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}