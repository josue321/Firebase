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

public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;

    private String Lemail = "";
    private String Lpass = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Email = (EditText) findViewById(R.id.Emailedt);
        Password = (EditText) findViewById(R.id.passwordedt);
        Login = (Button) findViewById(R.id.btnloggin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lemail = Email.getText().toString();
                Lpass = Password.getText().toString();

                if (!Lemail.isEmpty() && !Lpass.isEmpty()){
                    loginUser();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void loginUser() {
        mAuth.signInWithEmailAndPassword(Lemail, Lpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, activity_home.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Datos Invalidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

