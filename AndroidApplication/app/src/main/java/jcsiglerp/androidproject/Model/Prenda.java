package jcsiglerp.androidproject.Model;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Prenda extends RealmObject {
    @PrimaryKey
    public String id = UUID.randomUUID().toString();


    public String nombre;
    public Double precio;
    public String urlImg;
    public RealmList < Etiqueta > etiquetas = new RealmList<>();

    public Prenda() {}

    public Prenda(String nombre, double precio, String urlImg) {
        this.nombre = nombre;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    public void addEtiqueta(Etiqueta etiqueta) {
        this.etiquetas.add(etiqueta);
    }
}
