package jcsiglerp.androidproject.pedido;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jcsiglerp.androidproject.Model.Pedido;
import jcsiglerp.androidproject.Model.Prenda;
import jcsiglerp.androidproject.MyApplication;
import jcsiglerp.androidproject.R;
import jcsiglerp.androidproject.comprar.ComprarAdapter;

public class VerPedido extends AppCompatActivity {

    private Pedido pedido;
    TextView tvIdPedido, tvCantidad;
    RecyclerView rvPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedido);

        // Asociamos variables a controles gráficos
        tvIdPedido = (TextView) findViewById(R.id.tvPedido);
        tvCantidad = (TextView) findViewById(R.id.tvCantidad);
        rvPedido = (RecyclerView) findViewById(R.id.rvPedido);
        rvPedido.setLayoutManager(new LinearLayoutManager(this));
        rvPedido.setAdapter(new VerPedidoAdapter());

        // Obtenemos el pedido de la base de datos dado el correo del bundle
        Realm realm = ((MyApplication) getApplication()).getRealm();
        String id = this.getIntent().getExtras().get("idPedido").toString();
        pedido = realm.where(Pedido.class).equalTo("id", id).findFirst();

        // Desplegamos las prendas del pedido en el RecyclerView
        List <Prenda> prendas = pedido.contenido;
        ((VerPedidoAdapter) rvPedido.getAdapter()).setData(prendas);
        tvIdPedido.setText("Pedido ID: " + id);
        tvCantidad.setText("Cantidad total\n$" + pedido.cantidad);
    }
}
