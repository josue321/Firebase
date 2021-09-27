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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class activity_home extends AppCompatActivity {

    private Button mbutton;
    private FirebaseAuth mAuth;
    private EditText codigo;
    private EditText nombres;
    private EditText apellidos;
    private Button regbtn;
    private String CodigoR;
    private String NombresR;
    private String ApellidosR;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        codigo = (EditText) findViewById(R.id.codigotxt);
        nombres = (EditText) findViewById(R.id.Nombretxt);
        apellidos = (EditText) findViewById(R.id.Apellidostxt);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        regbtn = (Button) findViewById(R.id.regbtn);

        mAuth = FirebaseAuth.getInstance();
        mbutton = (Button) findViewById(R.id.btncerrar);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CodigoR = codigo.getText().toString();
                NombresR = nombres.getText().toString();
                ApellidosR = apellidos.getText().toString();
                if (!CodigoR.isEmpty() && !NombresR.isEmpty() && !ApellidosR.isEmpty()) {
                    RegistarAlumno();
                }else{
                    Toast.makeText(activity_home.this, "Debe completar los datos" ,Toast.LENGTH_SHORT).show();
                }

            }
        });


        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(activity_home.this, MainActivity.class));
                finish();
            }
        });
    }

    private void RegistarAlumno() {
        Map<String, Object> alumnoMap = new HashMap<>();
        alumnoMap.put("codigo", CodigoR);
        alumnoMap.put("Nombres", NombresR);
        alumnoMap.put("Apellidos", ApellidosR);

        mDatabase.child("Alumnos").push().setValue(alumnoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_home.this, "Datos Creados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity_home.this, "No se crear datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}




