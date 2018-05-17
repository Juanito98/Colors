package jcsiglerp.androidproject.buscar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jcsiglerp.androidproject.Model.Prenda;
import jcsiglerp.androidproject.R;

public class BuscarAdapter extends RecyclerView.Adapter<BuscarAdapter.BuscarViewHolder> {

    // Contiene todas las prendas que se despliegan en el RecyclerView
    public List<Prenda> data = new ArrayList<>();
    private AddToCartClickedListener listener;

    BuscarAdapter(AddToCartClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public BuscarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Vas a poner el xml de la vista por item R.layout.view_item_buscar
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_buscar, parent, false);
        return new BuscarViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(BuscarViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Prenda> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    interface AddToCartClickedListener {
        void itemClicked(Prenda prenda, int cantidad);
    }

    // Es la clase de la vista de una única prenda
    class BuscarViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        TextView tvPrecio;
        TextView tvCantidad;
        ImageView image;
        Prenda prenda;

        public BuscarViewHolder(View itemView, final AddToCartClickedListener lister) {
            super(itemView);
            // Asociamos variables a controles gráficos
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            image = itemView.findViewById(R.id.ivPrenda);
            itemView.findViewById(R.id.btAgregar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (prenda != null) {
                        lister.itemClicked(prenda, Integer.parseInt(tvCantidad.getText().toString()));
                        tvCantidad.setText("1");
                    }
                }
            });
            itemView.findViewById(R.id.btMore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer newCantidad = Integer.parseInt(tvCantidad.getText().toString()) + 1;
                    tvCantidad.setText(newCantidad.toString());
                }
            });
            itemView.findViewById(R.id.btLess).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer newCantidad = Integer.parseInt(tvCantidad.getText().toString()) - 1;
                    if(newCantidad > 0)
                        tvCantidad.setText(newCantidad.toString());
                }
            });
        }

        void bind(Prenda prenda) {
            this.prenda = prenda;
            tvNombre.setText(prenda.nombre);
            tvPrecio.setText("$" + prenda.precio);
            Picasso.get().load(prenda.urlImg).placeholder(R.mipmap.ic_launcher).error(android.R.drawable.stat_notify_error).into(image);
        }
    }
}
