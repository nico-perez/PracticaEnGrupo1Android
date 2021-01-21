package chavales.los.practica1android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;



    private static final Producto[] productos = {
            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, Arrays.asList(new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 35.75f, Calidad.BUENO), new Producto.Detalle("Hasta arriba de", Nutricion.AZUCAR, 500, Calidad.MALO), new Producto.Detalle("Así asá de", Nutricion.PROTEINAS, 0.05f, Calidad.REGULAR), new Producto.Detalle("Un poquito de", Nutricion.TACOS, .5f, Calidad.BUENO), new Producto.Detalle("Bastantes ", Nutricion.NUECES, 30, Calidad.EXCELENTE))),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null, new ArrayList<Producto.Detalle>(){  }),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null, new ArrayList<Producto.Detalle>(){  }),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null, new ArrayList<Producto.Detalle>(){  }),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null, new ArrayList<Producto.Detalle>(){  }),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null, new ArrayList<Producto.Detalle>(){  }),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null, new ArrayList<Producto.Detalle>(){  }),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null, new ArrayList<Producto.Detalle>(){  })
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.recyclerView);
        lista.setAdapter(new AdaptadorDeProductos(this, productos));
        lista.setLayoutManager(new LinearLayoutManager(this));
    }
}