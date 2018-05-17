package jcsiglerp.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import jcsiglerp.androidproject.Model.Usuario;
import jcsiglerp.androidproject.buscar.Buscar;

public class Registrarse extends AppCompatActivity {

    EditText nombre, apellidos, correo, contra, direccion;
    Button btRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        // Asociamos variables a controles gráficos
        nombre = (EditText) findViewById(R.id.etNombre);
        apellidos = (EditText) findViewById(R.id.etApellidos);
        correo = (EditText) findViewById(R.id.etCorreo);
        contra = (EditText) findViewById(R.id.etContra);
        direccion = (EditText) findViewById(R.id.etDireccion);
        btRegistro = (Button) findViewById(R.id.btRegistrarse);

        // Botón de registrarse
        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos usuario dados los EditTexts
                Usuario a = new Usuario(nombre.getText().toString(), apellidos.getText().toString(), correo.getText().toString(), contra.getText().toString(), direccion.getText().toString());
                Realm realm = ((MyApplication) getApplication()).getRealm();
                // Preguntamos si el correo ya fue usado
                if(realm.where(Usuario.class).equalTo("correo", a.correo).count() > 0) {
                    Toast.makeText(Registrarse.this, "Este correo ya fue usado", Toast.LENGTH_LONG).show();
                } else {
                    // Agregamos al nuevo usuario a la base de datos
                    realm.beginTransaction();
                    realm.copyToRealm(a);
                    realm.commitTransaction();
                    Intent intent = new Intent(Registrarse.this, Buscar.class);
                    Bundle b = new Bundle();
                    b.putString("correo", a.correo);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
