package jcsiglerp.androidproject;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jcsiglerp.androidproject.Model.Etiqueta;
import jcsiglerp.androidproject.Model.Prenda;
import jcsiglerp.androidproject.Model.Usuario;

public class MyApplication extends Application {
    Realm realm;


    public void onCreate() {
        super.onCreate();
        // Se inicializa el realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                // Esta parte llena la base de datos en el momento que descargas la aplicación
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        // Cargamos las etiquetas que pueden tener las prendas
                        Etiqueta etiquetas[] = {
                                realm.createObject(Etiqueta.class, "pantalon"),//0
                                realm.createObject(Etiqueta.class, "falda"),//1
                                realm.createObject(Etiqueta.class, "shorts"),//2
                                realm.createObject(Etiqueta.class, "vestido"), //3
                                realm.createObject(Etiqueta.class, "chamarra"), //4
                                realm.createObject(Etiqueta.class, "playera"), //5
                                realm.createObject(Etiqueta.class, "camisa"), //6
                                realm.createObject(Etiqueta.class, "blanco"), //7
                                realm.createObject(Etiqueta.class, "negro"), //8
                                realm.createObject(Etiqueta.class, "azul"), //9
                                realm.createObject(Etiqueta.class, "gris"), //10
                                realm.createObject(Etiqueta.class, "amarillo"), //11
                                realm.createObject(Etiqueta.class, "primavera"), //12
                                realm.createObject(Etiqueta.class, "verano"), //13
                                realm.createObject(Etiqueta.class, "otoño"), //14
                                realm.createObject(Etiqueta.class, "invierno"), //15
                                realm.createObject(Etiqueta.class, "rojo"), //16
                                realm.createObject(Etiqueta.class, "impermeable"), //17
                                realm.createObject(Etiqueta.class, "hombre"), // 18
                                realm.createObject(Etiqueta.class, "mujer") // 19
                        };


                        // Cargamos las prendas a la base de datos
                        Prenda p = new Prenda("Pantalon gris", 1000,"https://anf.scene7.com/is/image/anf/hol_187654_02_prod1?$product-hol-v1$&wid=1900&hei=2375");
                        p.addEtiqueta(etiquetas[0]);
                        p.addEtiqueta(etiquetas[10]);
                        p.addEtiqueta(etiquetas[14]);
                        realm.copyToRealm(p);

                        p = new Prenda("Pantalon negro", 800,"https://anf.scene7.com/is/image/anf/hol_187654_01_prod1?$product-hol-v1$&wid=1900&hei=2375");
                        p.addEtiqueta(etiquetas[0]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[15]);
                        realm.copyToRealm(p);

                        p = new Prenda("Pantalon azul de jeans rotos", 900,"https://anf.scene7.com/is/image/anf/hol_211883_07_prod1?$product-hol-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[0]);
                        p.addEtiqueta(etiquetas[9]);
                        p.addEtiqueta(etiquetas[14]);
                        realm.copyToRealm(p);

                        p = new Prenda("Pantalon negro", 800,"https://anf.scene7.com/is/image/anf/hol_202223_01_prod1?$product-hol-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[0]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[12]);
                        realm.copyToRealm(p);

                        p = new Prenda("Falda corta negra", 600,"https://anf.scene7.com/is/image/anf/anf_200027_01_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[1]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[13]);
                        p.addEtiqueta(etiquetas[19]);
                        realm.copyToRealm(p);

                        p = new Prenda("Shorts negros", 600,"https://anf.scene7.com/is/image/anf/anf_199052_01_prod1?$product-anf-v1$&wid=1900&hei=2375");
                        p.addEtiqueta(etiquetas[2]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[13]);
                        realm.copyToRealm(p);

                        p = new Prenda("Shorts grises", 500,"https://anf.scene7.com/is/image/anf/hol_202719_04_prod1?$product-hol-v1$&wid=800&hei=1000&locale=es_WD");
                        p.addEtiqueta(etiquetas[2]);
                        p.addEtiqueta(etiquetas[10]);
                        p.addEtiqueta(etiquetas[13]);
                        realm.copyToRealm(p);

                        p = new Prenda("Shorts amarillos con moño", 500,"https://anf.scene7.com/is/image/anf/anf_200897_08_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[2]);
                        p.addEtiqueta(etiquetas[11]);
                        p.addEtiqueta(etiquetas[12]);
                        p.addEtiqueta(etiquetas[19]);
                        realm.copyToRealm(p);

                        p = new Prenda("Vestido azul con blanco", 650,"https://anf.scene7.com/is/image/anf/hol_205785_01_prod1?$product-hol-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[3]);
                        p.addEtiqueta(etiquetas[9]);
                        p.addEtiqueta(etiquetas[13]);
                        p.addEtiqueta(etiquetas[19]);
                        realm.copyToRealm(p);

                        p = new Prenda("Vestido negro con encaje", 700,"https://anf.scene7.com/is/image/anf/anf_203394_01_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[3]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[12]);
                        p.addEtiqueta(etiquetas[19]);
                        realm.copyToRealm(p);

                        p = new Prenda("Chamarra negra", 1500,"https://anf.scene7.com/is/image/anf/anf_205002_01_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[4]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[15]);
                        realm.copyToRealm(p);

                        p = new Prenda("Chamarra impermeable azul con gorro", 1700,"https://anf.scene7.com/is/image/anf/anf_197006_03_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[4]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[15]);
                        p.addEtiqueta(etiquetas[17]);
                        realm.copyToRealm(p);

                        p = new Prenda("Playera blanca con estampado de palomitas", 450,"https://cdn.shopify.com/s/files/1/1040/3796/products/tshirt_nino_2.jpg?v=1491405332");
                        p.addEtiqueta(etiquetas[5]);
                        p.addEtiqueta(etiquetas[7]);
                        p.addEtiqueta(etiquetas[12]);
                        realm.copyToRealm(p);

                        p = new Prenda("Playera blanca con estampado de perico", 400,"https://cdn.shopify.com/s/files/1/1040/3796/products/TSHIRT_ADULTO_PERICO_WEB.jpg?v=1498834755");
                        p.addEtiqueta(etiquetas[5]);
                        p.addEtiqueta(etiquetas[7]);
                        p.addEtiqueta(etiquetas[13]);
                        realm.copyToRealm(p);

                        p = new Prenda("Playera lisa negra, de cuello cerrado", 430,"https://anf.scene7.com/is/image/anf/anf_199739_01_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[5]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[14]);
                        realm.copyToRealm(p);

                        p = new Prenda("Playera roja con flor bordada", 480,"https://anf.scene7.com/is/image/anf/hol_208526_02_prod1?$product-hol-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[5]);
                        p.addEtiqueta(etiquetas[16]);
                        p.addEtiqueta(etiquetas[12]);
                        realm.copyToRealm(p);

                        p = new Prenda("Playera blanca con azul", 450,"https://anf.scene7.com/is/image/anf/hol_204725_02_prod1?$product-hol-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[5]);
                        p.addEtiqueta(etiquetas[7]);
                        p.addEtiqueta(etiquetas[9]);
                        p.addEtiqueta(etiquetas[13]);
                        realm.copyToRealm(p);

                        p = new Prenda("Playera negra de tirantes con encaje", 400,"https://anf.scene7.com/is/image/anf/anf_197342_03_prod1?$product-anf-v1$&wid=1900&hei=2375");
                        p.addEtiqueta(etiquetas[5]);
                        p.addEtiqueta(etiquetas[8]);
                        p.addEtiqueta(etiquetas[13]);
                        p.addEtiqueta(etiquetas[19]);
                        realm.copyToRealm(p);

                        p = new Prenda("Camisa azul", 600,"https://anf.scene7.com/is/image/anf/anf_169649_04_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[6]);
                        p.addEtiqueta(etiquetas[9]);
                        p.addEtiqueta(etiquetas[15]);
                        realm.copyToRealm(p);

                        p = new Prenda("Camisa de cuadros azul con blanco", 640,"https://anf.scene7.com/is/image/anf/anf_197331_01_prod1?$product-anf-v1$&wid=800&hei=1000");
                        p.addEtiqueta(etiquetas[6]);
                        p.addEtiqueta(etiquetas[7]);
                        p.addEtiqueta(etiquetas[9]);
                        p.addEtiqueta(etiquetas[14]);
                        realm.copyToRealm(p);

                    }
                })
                .build();
        realm = Realm.getInstance(config);
    }

    public Realm getRealm() {
        return realm;
    }
}
