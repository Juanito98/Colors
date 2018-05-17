package jcsiglerp.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import jcsiglerp.androidproject.Model.Usuario;
import jcsiglerp.androidproject.buscar.Buscar;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etContra;
    Button btLogin;
    TextView tvRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asociamos variables a controles gráficos
        etUsuario = (EditText) findViewById(R.id.etCorreo);
        etContra = (EditText) findViewById(R.id.etContra);
        btLogin = (Button) findViewById(R.id.btIniciarSesion);
        tvRegistrarse = (TextView) findViewById(R.id.tvRegistrarse);

        // Función de login
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hacemos la query a la base de datos
                Realm realm = ((MyApplication) getApplication()).getRealm();
                RealmResults < Usuario > results = realm.where(Usuario.class)
                        .equalTo("correo", etUsuario.getText().toString())
                        .equalTo("contra", etContra.getText().toString())
                        .findAll();

                // Preguntamos si el usuario es válido
                if(results.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                } else {
                    // Se encontró el usuario
                    Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Buscar.class);
                    Bundle b = new Bundle();
                    b.putString("correo", results.first().correo);
                    intent.putExtras(b);
                    startActivity(intent);
                    etUsuario.setText("");
                    etContra.setText("");
                }
            }
        });

        // Cambia de actividad a Registrarse
        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });
    }
}
