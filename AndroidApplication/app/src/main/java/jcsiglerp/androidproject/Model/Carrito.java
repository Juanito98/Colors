package jcsiglerp.androidproject.Model;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Carrito extends RealmObject {
    @PrimaryKey
    public String id = UUID.randomUUID().toString();

    public RealmList < Prenda > contenido = new RealmList<>();
    public Double precioTotal = new Double(0.0);

    // Agrega una prenda del carrito
    public void agregarPrenda(Prenda prenda) {
        contenido.add(prenda);
        precioTotal += prenda.precio;
    }

    // Quita prenda del carrito
    public void quitarPrenda(Prenda prenda) {
        if(contenido.remove(prenda))
            precioTotal -= prenda.precio;
        if(contenido.isEmpty())
            precioTotal = 0.0;
    }

    // Limpia completamente el carrito
    public void clear() {
        contenido = new RealmList<>();
        precioTotal = 0.0;
    }
}
