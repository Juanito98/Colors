package jcsiglerp.androidproject.historial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmResults;
import jcsiglerp.androidproject.Model.Pedido;
import jcsiglerp.androidproject.MyApplication;
import jcsiglerp.androidproject.R;
import jcsiglerp.androidproject.pedido.VerPedido;

public class Historial extends AppCompatActivity implements HistorialAdapter.viewOnClickPedidoListener {

    RecyclerView rvHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        // Asociamos variables a controles gráficos
        rvHistorial = (RecyclerView) findViewById(R.id.rvHistorial);
        rvHistorial.setLayoutManager(new LinearLayoutManager(this));
        rvHistorial.setAdapter(new HistorialAdapter(this));

        // Obtenemos los datos del intent
        Realm realm = ((MyApplication) getApplication()).getRealm();
        Bundle b = this.getIntent().getExtras();
        // Desplegamos el historial en el RecyclerView
        RealmResults <Pedido> results = realm.where(Pedido.class).equalTo("usuario.correo", b.get("correo").toString()).findAll();
        ((HistorialAdapter) rvHistorial.getAdapter()).setData(results);
    }

    @Override
    // Esta función se ejecuta cada que dan clic a "Ver detalles"
    public void itemClicked(Pedido pedido) {
        // Iniciamos una nueva actividad para hacer display del pedido
        Intent intent = new Intent(Historial.this, VerPedido.class);
        Bundle b = new Bundle();
        b.putString("idPedido", pedido.id);
        intent.putExtras(b);
        startActivity(intent);
    }
}
