package chavales.los.practica1android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;
    private static final Producto[] productos = {
            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.recyclerView);
        lista.setAdapter(new AdaptadorDeProductos(productos));
        lista.setLayoutManager(new LinearLayoutManager(this));
    }
}