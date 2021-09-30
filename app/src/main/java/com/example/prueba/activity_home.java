package com.example.prueba;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba.model.Alumno;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class activity_home extends AppCompatActivity {
    private List<Alumno> listAlumno= new ArrayList<Alumno>();
    ArrayAdapter<Alumno> arrayAdapterAlumno;

    private FirebaseAuth mAuth;

    EditText codigoT, nombreT, apellidoT;
    ListView listV;


    FirebaseDatabase Database;
    DatabaseReference Datareference;

    Alumno alumnoSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        codigoT = findViewById(R.id.txt_Codigo);
        nombreT = findViewById(R.id.txt_Nombre);
        apellidoT = findViewById(R.id.txt_Apellidos);

        mAuth = FirebaseAuth.getInstance();

        listV = findViewById(R.id.lv_alumnos);
        iniciarFirebase();
        listarDatos();

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alumnoSelect = (Alumno) parent.getItemAtPosition(position);
                nombreT.setText(alumnoSelect.getNombre());
                apellidoT.setText(alumnoSelect.getApellido());
                codigoT.setText(alumnoSelect.getCodigo());
            }
        });
    }

    private void listarDatos() {
        Datareference.child("Alumno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAlumno.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Alumno a = objSnaptshot.getValue(Alumno.class);
                    listAlumno.add(a);

                    arrayAdapterAlumno = new ArrayAdapter<Alumno>(activity_home.this, android.R.layout.simple_list_item_1, listAlumno);
                    listV.setAdapter(arrayAdapterAlumno);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        Database = FirebaseDatabase.getInstance();
        Datareference = Database.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String nombre = nombreT.getText().toString();
        String apellido = apellidoT.getText().toString();
        String codigo = codigoT.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add: {
                if (nombre.equals("") || apellido.equals("") || codigo.equals("")) {
                    validacion();
                } else {
                    Alumno a = new Alumno();
                    a.setUid(UUID.randomUUID().toString());
                    a.setNombre(nombre);
                    a.setApellido(apellido);
                    a.setCodigo(codigo);
                    Datareference.child("Alumno").child(a.getUid()).setValue(a);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save:
            {
                Alumno a = new Alumno();
                a.setUid(alumnoSelect.getUid());
                a.setNombre(nombreT.getText().toString().trim());
                a.setApellido(apellidoT.getText().toString().trim());
                a.setCodigo(codigoT.getText().toString().trim());
                Datareference.child("Alumno").child(a.getUid()).setValue(a);
                Toast.makeText(this, "Guardar", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:
            {
                Alumno a = new Alumno();
                a.setUid(alumnoSelect.getUid());
                Datareference.child("Alumno").child(a.getUid()).removeValue();
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_exit:
            {
                mAuth.signOut();
                startActivity(new Intent(activity_home.this, MainActivity.class));
                finish();
                break;
            }
        }
        
        return true;
    }


    private void limpiarCajas() {
        nombreT.setText("");
        apellidoT.setText("");
        codigoT.setText("");
    }

    private void validacion() {
        String nombre = nombreT.getText().toString();
        String apellido = apellidoT.getText().toString();
        String codigo = codigoT.getText().toString();

        if (nombre.equals("")){
            nombreT.setError("Required");
        }

        else if (apellido.equals("")){
            apellidoT.setError("Required");
        }
        else if (codigo.equals("")){
            codigoT.setError("Required");
        }
    }
    }



