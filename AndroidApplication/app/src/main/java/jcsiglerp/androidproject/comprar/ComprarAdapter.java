package jcsiglerp.androidproject.comprar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jcsiglerp.androidproject.Model.Prenda;
import jcsiglerp.androidproject.R;

public class ComprarAdapter extends RecyclerView.Adapter <ComprarAdapter.ComprarViewHolder> {

    // Contiene todas las prendas que se despliegan en el RecyclerView
    public List <Prenda > data = new ArrayList<>();
    private subtrackFromCartClickedListener listener;

    public ComprarAdapter(subtrackFromCartClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public ComprarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_comprar, parent, false);
        return new ComprarViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ComprarViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Cambiamos los datos de la lista
    public void setData(List < Prenda > data) {
        this.data = data;
        notifyDataSetChanged();
    }

    interface subtrackFromCartClickedListener {
        void itemClicked(Prenda prenda);
    }

    // Es la clase de la vista de una única prenda
    class ComprarViewHolder extends RecyclerView.ViewHolder {

        Prenda prenda;
        TextView tvNombre, tvPrecio;
        ImageView image;

        public ComprarViewHolder(View itemView, final subtrackFromCartClickedListener listener) {
            super(itemView);

            // Asociamos variables a controles gráficos
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            image = itemView.findViewById(R.id.ivPrenda);

            itemView.findViewById(R.id.btQuitar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(prenda != null) {
                        listener.itemClicked(prenda);
                    }
                }
            });
        }

        public void bind(Prenda prenda) {
            this.prenda = prenda;
            tvNombre.setText(prenda.nombre);
            tvPrecio.setText("$" + prenda.precio.toString());
            Picasso.get().load(prenda.urlImg).placeholder(R.mipmap.ic_launcher).error(android.R.drawable.stat_notify_error).into(image);
        }
    }
}
