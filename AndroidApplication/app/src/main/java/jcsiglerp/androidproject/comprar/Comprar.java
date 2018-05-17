package jcsiglerp.androidproject.comprar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletionService;

import io.realm.Realm;
import jcsiglerp.androidproject.Model.Carrito;
import jcsiglerp.androidproject.Model.Pedido;
import jcsiglerp.androidproject.Model.Prenda;
import jcsiglerp.androidproject.Model.Usuario;
import jcsiglerp.androidproject.MyApplication;
import jcsiglerp.androidproject.R;
import jcsiglerp.androidproject.buscar.BuscarAdapter;

public class Comprar extends AppCompatActivity implements ComprarAdapter.subtrackFromCartClickedListener {

    Carrito carrito;
    TextView tvTotal;
    RecyclerView rvCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);

        // Asociamos variables a controles gráficos
        rvCompra = (RecyclerView) findViewById(R.id.rvCompra);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        // Le damos funcionalidad al botón de comprar
        this.findViewById(R.id.btComprar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!carrito.contenido.isEmpty()) {
                    // Instanciamos un nuevo pedido y lo añadimos a la base de datos
                    Realm realm = ((MyApplication) getApplication()).getRealm();
                    realm.beginTransaction();
                    Pedido pedido = new Pedido();
                    pedido.cantidad = carrito.precioTotal;
                    pedido.fecha = Calendar.getInstance().getTime();
                    pedido.usuario = realm.where(Usuario.class).equalTo("carrito.id", carrito.id).findFirst();
                    pedido.contenido = carrito.contenido;
                    realm.copyToRealm(pedido);
                    carrito.clear();
                    realm.commitTransaction();
                    // Vaciamos los objetos del RecyclerView
                    ((ComprarAdapter) rvCompra.getAdapter()).setData(carrito.contenido);
                    tvTotal.setText(String.format("Cantidad total a pagar: $%.2f", carrito.precioTotal));
                    Toast.makeText(Comprar.this, "Compra realizada exitosamente!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Comprar.this, "No hay nada en el carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rvCompra.setLayoutManager(new LinearLayoutManager(this));
        rvCompra.setAdapter(new ComprarAdapter(this));

        // Obtenemos el carrito de la base de datos del usuario
        Realm realm = ((MyApplication) getApplication()).getRealm();
        Bundle b = this.getIntent().getExtras();
        carrito = realm.where(Usuario.class).equalTo("correo", b.get("correo").toString()).findFirst().carrito;
        ((ComprarAdapter) rvCompra.getAdapter()).setData(carrito.contenido);
        tvTotal.setText(String.format("Cantidad total a pagar: $%.2f", carrito.precioTotal));
    }

    @Override
    // Este código se ejecuta cuando hacemos clic en Quitar
    public void itemClicked(Prenda prenda) {
        // Modificamos los datos del carrito y le quitamos prenda
        Realm realm = ((MyApplication) getApplication()).getRealm();
        realm.beginTransaction();
        carrito.quitarPrenda(prenda);
        realm.commitTransaction();
        ((ComprarAdapter) rvCompra.getAdapter()).setData(carrito.contenido);
        tvTotal.setText(String.format("Cantidad total a pagar: $%.2f", carrito.precioTotal));
    }
}
