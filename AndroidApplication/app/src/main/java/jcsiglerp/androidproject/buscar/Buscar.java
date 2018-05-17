package jcsiglerp.androidproject.buscar;

import android.content.Intent;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import jcsiglerp.androidproject.Suggestions;
import jcsiglerp.androidproject.comprar.Comprar;
import jcsiglerp.androidproject.historial.Historial;
import jcsiglerp.androidproject.Model.Carrito;
import jcsiglerp.androidproject.Model.Prenda;
import jcsiglerp.androidproject.Model.Usuario;
import jcsiglerp.androidproject.MyApplication;
import jcsiglerp.androidproject.R;

import static java.util.Map.Entry.comparingByValue;

public class Buscar extends AppCompatActivity implements BuscarAdapter.AddToCartClickedListener {

    Carrito carrito;
    EditText etBuscar;
    RecyclerView rvBusqueda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        // Asociamos variables a controles gráficos
        etBuscar = (EditText) findViewById(R.id.etBuscar);
        rvBusqueda = (RecyclerView) findViewById(R.id.rvBusqueda);

        // Asociamos la función "buscar" al botón
        etBuscar.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                buscar(Buscar.this.getCurrentFocus());
                return true;
            }
        });

        // Cambiamos de vista a Ver Carrito
        this.findViewById(R.id.btCarrito).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buscar.this, Comprar.class);
                intent.putExtras(Buscar.this.getIntent().getExtras());
                startActivity(intent);
            }
        });
        // Cambiamos a la vista del Historial
        this.findViewById(R.id.btHistorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buscar.this, Historial.class);
                intent.putExtras(Buscar.this.getIntent().getExtras());
                startActivity(intent);
            }
        });
        // Cambiamos a la vista del botón de Sugerencias
        this.findViewById(R.id.btSugerencias).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buscar.this, Suggestions.class);
                startActivity(intent);
            }
        });

        rvBusqueda.setLayoutManager(new LinearLayoutManager(this));
        rvBusqueda.setAdapter(new BuscarAdapter(this));

        // Obtenemos todas las prendas de la base de Datos
        Realm realm = ((MyApplication) getApplication()).getRealm();
        RealmResults < Prenda > results = realm.where(Prenda.class).findAll();
        // Llenamos el RecyclerView con todas las prendas de la base de datos
        ((BuscarAdapter)rvBusqueda.getAdapter()).setData(results);
        Bundle b = this.getIntent().getExtras();
        carrito = realm.where(Usuario.class).equalTo("correo", b.get("correo").toString()).findFirst().carrito;
    }

    // Esta función despliega el resultado de la búsqueda de prendas
    protected void buscar(View v) {
        Realm realm = ((MyApplication) getApplication()).getRealm();
        // Guardamos todas las palabras que se teclearon en la búsqueda
        String keyWords[] = etBuscar.getText().toString().split(" ");
        List < Prenda > prendaList; // El resultado lo insertamos en esta lista
        if(keyWords[0] != "") {
            // Creamos un hash map con las ocurrencias de las prendas
            HashMap <Prenda, Integer > ocurrencias = new HashMap<>();
            prendaList = new ArrayList<>();
            Integer value = keyWords.length;
            for(String kw : keyWords) {
                // Por cada palabra escrita vemos las prendas que tienen como etiqueta esta palabra
                // Le damos prioridad a las prendas con más etiquetas coincidentes
                RealmResults<Prenda> results = realm.where(Prenda.class).equalTo("etiquetas.nombre", kw).findAll();
                for(Prenda prenda : results) {
                    if(ocurrencias.containsKey(prenda))
                        ocurrencias.put(prenda, ocurrencias.get(prenda) + value);
                    else
                        ocurrencias.put(prenda, value);
                }
                value--;
            }
            List <Map.Entry<Prenda, Integer >> ordenados = new ArrayList<>(ocurrencias.entrySet());
            Collections.sort(ordenados, new Comparator<Map.Entry<Prenda, Integer>>() {
                @Override
                public int compare(Map.Entry<Prenda, Integer> o1, Map.Entry<Prenda, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });
            // Metemos los resultados en la lista
            for(Map.Entry<Prenda, Integer> o : ordenados)
                prendaList.add(o.getKey());
        } else {
            // En caso de que no se haya buscado por ninguna palabra, devuelve todas las prendas
            prendaList = realm.where(Prenda.class).findAll();
        }
        // Cambiamos el RecyclerView
        ((BuscarAdapter)rvBusqueda.getAdapter()).setData(prendaList);
    }

    @Override
    // Esta función se ejecuta cada que hacemos clic en Añadir al Carrito
    public void itemClicked(Prenda prenda, int cantidad) {
        // Añadimos "cantidad" prendas "prenda" a la base de datos
        Realm realm = ((MyApplication) getApplication()).getRealm();
        realm.beginTransaction();
        for(int i = 0; i < cantidad; ++i)
            carrito.agregarPrenda(prenda);
        realm.commitTransaction();
        Toast.makeText(Buscar.this, "Añadido exitosamente al carrito!", Toast.LENGTH_SHORT).show();
    }
}
