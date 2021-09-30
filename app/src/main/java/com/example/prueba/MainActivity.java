package com.example.prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Registrar;
    private Button Loggin;
    private String Emailt = "";
    private String Passwordt = "";

    FirebaseAuth mAuth;
    DatabaseReference Datareference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Datareference = FirebaseDatabase.getInstance().getReference();

        Email = (EditText) findViewById(R.id.Emailedt);
        Password = (EditText) findViewById(R.id.passwordedt);
        Registrar = (Button) findViewById(R.id.registrarbtn);
        Loggin = (Button) findViewById(R.id.logginbtn);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Emailt = Email.getText().toString();
                Passwordt = Password.getText().toString();

                if (!Emailt.isEmpty() && !Passwordt.isEmpty()){
                    if (Passwordt.length() >=6){
                        registerUser();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Debe completar los datos" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(Emailt, Passwordt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("email",Emailt);
                    map.put("password",Passwordt);

                    String id = mAuth.getCurrentUser().getUid();
                    Datareference.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(MainActivity.this, activity_home.class));
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "No se crear datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() !=null){
            startActivity(new Intent(MainActivity.this, activity_home.class));
            finish();
        }
    }
}
