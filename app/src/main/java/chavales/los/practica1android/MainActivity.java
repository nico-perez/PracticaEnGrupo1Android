package chavales.los.practica1android;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button camarabuttton;
    private RecyclerView lista;
    private AdaptadorDeProductos adaptadorDeProductos;

    /*pkg*/ static SqliteParaUltimasVisualizaciones sqlite;

    public static final int REQ_CODE_CAMARA = 123;
    public static final int REQ_CODE_INSPEC = 456;

    private static final Producto[] productos = {
            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, 73, Arrays.asList(new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 35.75f, Calidad.BUENO), new Producto.Detalle("Hasta arriba de", Nutricion.AZUCAR, 500, Calidad.MALO), new Producto.Detalle("Así asá de", Nutricion.PROTEINAS, 0.05f, Calidad.REGULAR), new Producto.Detalle("Un poquito de", Nutricion.TACOS, .5f, Calidad.BUENO), new Producto.Detalle("Bastantes ", Nutricion.NUECES, 30, Calidad.EXCELENTE))),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null, 23, Arrays.asList(new Producto.Detalle("Un par de", Nutricion.NUECES, 2, Calidad.MALO), new Producto.Detalle("Alto en", Nutricion.PROTEINAS, 23, Calidad.EXCELENTE), new Producto.Detalle("Moderada", Nutricion.ENERGIA, 1500, Calidad.BUENO), new Producto.Detalle("Un poco de", Nutricion.SODIO, 9, Calidad.BUENO))),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null, 91, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 100, Calidad.MEDIOCRE), new Producto.Detalle("Alto en", Nutricion.SODIO, 1290, Calidad.MALO), new Producto.Detalle("Algo de", Nutricion.AMIANTO, 0.02f, Calidad.BUENO))),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null, 49, Arrays.asList(new Producto.Detalle("Moderados", Nutricion.TACOS, 10, Calidad.EXCELENTE), new Producto.Detalle("Moderadamente", Nutricion.RAMEN, 1.6729f, Calidad.REGULAR), new Producto.Detalle("Moderados", Nutricion.NUECES, .77f, Calidad.MEDIOCRE), new Producto.Detalle("Una cantidad de", Nutricion.GASOLINA, 995, Calidad.BUENO))),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null, 85, Arrays.asList(new Producto.Detalle("Demasiados", Nutricion.AZUCAR, 5000, Calidad.MALO), new Producto.Detalle("Demasiado pocos", Nutricion.WEBOS, 0, Calidad.REGULAR))),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null, 9, Arrays.asList(new Producto.Detalle("Pocos", Nutricion.NUECES, 10.05f, Calidad.EXCELENTE), new Producto.Detalle("Blip blup", Nutricion.SODIO, .0043f, Calidad.BUENO), new Producto.Detalle("AAAAAA", Nutricion.WEBOS, 10000000, Calidad.REGULAR), new Producto.Detalle("😂😂😂👌👌👌💯", Nutricion.HIERRO, 69, Calidad.EXCELENTE), new Producto.Detalle("10", Nutricion.VITAMINAS, 10, Calidad.REGULAR))),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null, 97, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 333.67f, Calidad.MALO), new Producto.Detalle("Bajo en", Nutricion.VENENO, -.5f, Calidad.EXCELENTE), new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 600, Calidad.MEDIOCRE), new Producto.Detalle("Casi sin", Nutricion.ENERGIA, 0.007f, Calidad.REGULAR))),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null, 76, Arrays.asList(new Producto.Detalle("Un puñado de", Nutricion.VITAMINAS, 25, Calidad.BUENO))),
            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, 73, Arrays.asList(new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 35.75f, Calidad.BUENO), new Producto.Detalle("Hasta arriba de", Nutricion.AZUCAR, 500, Calidad.MALO), new Producto.Detalle("Así asá de", Nutricion.PROTEINAS, 0.05f, Calidad.REGULAR), new Producto.Detalle("Un poquito de", Nutricion.TACOS, .5f, Calidad.BUENO), new Producto.Detalle("Bastantes ", Nutricion.NUECES, 30, Calidad.EXCELENTE))),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null, 23, Arrays.asList(new Producto.Detalle("Un par de", Nutricion.NUECES, 2, Calidad.MALO), new Producto.Detalle("Alto en", Nutricion.PROTEINAS, 23, Calidad.EXCELENTE), new Producto.Detalle("Moderada", Nutricion.ENERGIA, 1500, Calidad.BUENO), new Producto.Detalle("Un poco de", Nutricion.SODIO, 9, Calidad.BUENO))),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null, 91, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 100, Calidad.MEDIOCRE), new Producto.Detalle("Alto en", Nutricion.SODIO, 1290, Calidad.MALO), new Producto.Detalle("Algo de", Nutricion.AMIANTO, 0.02f, Calidad.BUENO))),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null, 49, Arrays.asList(new Producto.Detalle("Moderados", Nutricion.TACOS, 10, Calidad.EXCELENTE), new Producto.Detalle("Moderadamente", Nutricion.RAMEN, 1.6729f, Calidad.REGULAR), new Producto.Detalle("Moderados", Nutricion.NUECES, .77f, Calidad.MEDIOCRE), new Producto.Detalle("Una cantidad de", Nutricion.GASOLINA, 995, Calidad.BUENO))),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null, 85, Arrays.asList(new Producto.Detalle("Demasiados", Nutricion.AZUCAR, 5000, Calidad.MALO), new Producto.Detalle("Demasiado pocos", Nutricion.WEBOS, 0, Calidad.REGULAR))),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null, 9, Arrays.asList(new Producto.Detalle("Pocos", Nutricion.NUECES, 10.05f, Calidad.EXCELENTE), new Producto.Detalle("Blip blup", Nutricion.SODIO, .0043f, Calidad.BUENO), new Producto.Detalle("AAAAAA", Nutricion.WEBOS, 10000000, Calidad.REGULAR), new Producto.Detalle("😂😂😂👌👌👌💯", Nutricion.HIERRO, 69, Calidad.EXCELENTE), new Producto.Detalle("10", Nutricion.VITAMINAS, 10, Calidad.REGULAR))),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null, 97, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 333.67f, Calidad.MALO), new Producto.Detalle("Bajo en", Nutricion.VENENO, -.5f, Calidad.EXCELENTE), new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 600, Calidad.MEDIOCRE), new Producto.Detalle("Casi sin", Nutricion.ENERGIA, 0.007f, Calidad.REGULAR))),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null, 76, Arrays.asList(new Producto.Detalle("Un puñado de", Nutricion.VITAMINAS, 25, Calidad.BUENO))),new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, 73, Arrays.asList(new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 35.75f, Calidad.BUENO), new Producto.Detalle("Hasta arriba de", Nutricion.AZUCAR, 500, Calidad.MALO), new Producto.Detalle("Así asá de", Nutricion.PROTEINAS, 0.05f, Calidad.REGULAR), new Producto.Detalle("Un poquito de", Nutricion.TACOS, .5f, Calidad.BUENO), new Producto.Detalle("Bastantes ", Nutricion.NUECES, 30, Calidad.EXCELENTE))),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null, 23, Arrays.asList(new Producto.Detalle("Un par de", Nutricion.NUECES, 2, Calidad.MALO), new Producto.Detalle("Alto en", Nutricion.PROTEINAS, 23, Calidad.EXCELENTE), new Producto.Detalle("Moderada", Nutricion.ENERGIA, 1500, Calidad.BUENO), new Producto.Detalle("Un poco de", Nutricion.SODIO, 9, Calidad.BUENO))),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null, 91, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 100, Calidad.MEDIOCRE), new Producto.Detalle("Alto en", Nutricion.SODIO, 1290, Calidad.MALO), new Producto.Detalle("Algo de", Nutricion.AMIANTO, 0.02f, Calidad.BUENO))),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null, 49, Arrays.asList(new Producto.Detalle("Moderados", Nutricion.TACOS, 10, Calidad.EXCELENTE), new Producto.Detalle("Moderadamente", Nutricion.RAMEN, 1.6729f, Calidad.REGULAR), new Producto.Detalle("Moderados", Nutricion.NUECES, .77f, Calidad.MEDIOCRE), new Producto.Detalle("Una cantidad de", Nutricion.GASOLINA, 995, Calidad.BUENO))),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null, 85, Arrays.asList(new Producto.Detalle("Demasiados", Nutricion.AZUCAR, 5000, Calidad.MALO), new Producto.Detalle("Demasiado pocos", Nutricion.WEBOS, 0, Calidad.REGULAR))),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null, 9, Arrays.asList(new Producto.Detalle("Pocos", Nutricion.NUECES, 10.05f, Calidad.EXCELENTE), new Producto.Detalle("Blip blup", Nutricion.SODIO, .0043f, Calidad.BUENO), new Producto.Detalle("AAAAAA", Nutricion.WEBOS, 10000000, Calidad.REGULAR), new Producto.Detalle("😂😂😂👌👌👌💯", Nutricion.HIERRO, 69, Calidad.EXCELENTE), new Producto.Detalle("10", Nutricion.VITAMINAS, 10, Calidad.REGULAR))),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null, 97, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 333.67f, Calidad.MALO), new Producto.Detalle("Bajo en", Nutricion.VENENO, -.5f, Calidad.EXCELENTE), new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 600, Calidad.MEDIOCRE), new Producto.Detalle("Casi sin", Nutricion.ENERGIA, 0.007f, Calidad.REGULAR))),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null, 76, Arrays.asList(new Producto.Detalle("Un puñado de", Nutricion.VITAMINAS, 25, Calidad.BUENO))),new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, 73, Arrays.asList(new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 35.75f, Calidad.BUENO), new Producto.Detalle("Hasta arriba de", Nutricion.AZUCAR, 500, Calidad.MALO), new Producto.Detalle("Así asá de", Nutricion.PROTEINAS, 0.05f, Calidad.REGULAR), new Producto.Detalle("Un poquito de", Nutricion.TACOS, .5f, Calidad.BUENO), new Producto.Detalle("Bastantes ", Nutricion.NUECES, 30, Calidad.EXCELENTE))),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null, 23, Arrays.asList(new Producto.Detalle("Un par de", Nutricion.NUECES, 2, Calidad.MALO), new Producto.Detalle("Alto en", Nutricion.PROTEINAS, 23, Calidad.EXCELENTE), new Producto.Detalle("Moderada", Nutricion.ENERGIA, 1500, Calidad.BUENO), new Producto.Detalle("Un poco de", Nutricion.SODIO, 9, Calidad.BUENO))),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null, 91, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 100, Calidad.MEDIOCRE), new Producto.Detalle("Alto en", Nutricion.SODIO, 1290, Calidad.MALO), new Producto.Detalle("Algo de", Nutricion.AMIANTO, 0.02f, Calidad.BUENO))),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null, 49, Arrays.asList(new Producto.Detalle("Moderados", Nutricion.TACOS, 10, Calidad.EXCELENTE), new Producto.Detalle("Moderadamente", Nutricion.RAMEN, 1.6729f, Calidad.REGULAR), new Producto.Detalle("Moderados", Nutricion.NUECES, .77f, Calidad.MEDIOCRE), new Producto.Detalle("Una cantidad de", Nutricion.GASOLINA, 995, Calidad.BUENO))),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null, 85, Arrays.asList(new Producto.Detalle("Demasiados", Nutricion.AZUCAR, 5000, Calidad.MALO), new Producto.Detalle("Demasiado pocos", Nutricion.WEBOS, 0, Calidad.REGULAR))),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null, 9, Arrays.asList(new Producto.Detalle("Pocos", Nutricion.NUECES, 10.05f, Calidad.EXCELENTE), new Producto.Detalle("Blip blup", Nutricion.SODIO, .0043f, Calidad.BUENO), new Producto.Detalle("AAAAAA", Nutricion.WEBOS, 10000000, Calidad.REGULAR), new Producto.Detalle("😂😂😂👌👌👌💯", Nutricion.HIERRO, 69, Calidad.EXCELENTE), new Producto.Detalle("10", Nutricion.VITAMINAS, 10, Calidad.REGULAR))),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null, 97, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 333.67f, Calidad.MALO), new Producto.Detalle("Bajo en", Nutricion.VENENO, -.5f, Calidad.EXCELENTE), new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 600, Calidad.MEDIOCRE), new Producto.Detalle("Casi sin", Nutricion.ENERGIA, 0.007f, Calidad.REGULAR))),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null, 76, Arrays.asList(new Producto.Detalle("Un puñado de", Nutricion.VITAMINAS, 25, Calidad.BUENO))),new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, 73, Arrays.asList(new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 35.75f, Calidad.BUENO), new Producto.Detalle("Hasta arriba de", Nutricion.AZUCAR, 500, Calidad.MALO), new Producto.Detalle("Así asá de", Nutricion.PROTEINAS, 0.05f, Calidad.REGULAR), new Producto.Detalle("Un poquito de", Nutricion.TACOS, .5f, Calidad.BUENO), new Producto.Detalle("Bastantes ", Nutricion.NUECES, 30, Calidad.EXCELENTE))),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null, 23, Arrays.asList(new Producto.Detalle("Un par de", Nutricion.NUECES, 2, Calidad.MALO), new Producto.Detalle("Alto en", Nutricion.PROTEINAS, 23, Calidad.EXCELENTE), new Producto.Detalle("Moderada", Nutricion.ENERGIA, 1500, Calidad.BUENO), new Producto.Detalle("Un poco de", Nutricion.SODIO, 9, Calidad.BUENO))),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null, 91, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 100, Calidad.MEDIOCRE), new Producto.Detalle("Alto en", Nutricion.SODIO, 1290, Calidad.MALO), new Producto.Detalle("Algo de", Nutricion.AMIANTO, 0.02f, Calidad.BUENO))),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null, 49, Arrays.asList(new Producto.Detalle("Moderados", Nutricion.TACOS, 10, Calidad.EXCELENTE), new Producto.Detalle("Moderadamente", Nutricion.RAMEN, 1.6729f, Calidad.REGULAR), new Producto.Detalle("Moderados", Nutricion.NUECES, .77f, Calidad.MEDIOCRE), new Producto.Detalle("Una cantidad de", Nutricion.GASOLINA, 995, Calidad.BUENO))),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null, 85, Arrays.asList(new Producto.Detalle("Demasiados", Nutricion.AZUCAR, 5000, Calidad.MALO), new Producto.Detalle("Demasiado pocos", Nutricion.WEBOS, 0, Calidad.REGULAR))),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null, 9, Arrays.asList(new Producto.Detalle("Pocos", Nutricion.NUECES, 10.05f, Calidad.EXCELENTE), new Producto.Detalle("Blip blup", Nutricion.SODIO, .0043f, Calidad.BUENO), new Producto.Detalle("AAAAAA", Nutricion.WEBOS, 10000000, Calidad.REGULAR), new Producto.Detalle("😂😂😂👌👌👌💯", Nutricion.HIERRO, 69, Calidad.EXCELENTE), new Producto.Detalle("10", Nutricion.VITAMINAS, 10, Calidad.REGULAR))),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null, 97, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 333.67f, Calidad.MALO), new Producto.Detalle("Bajo en", Nutricion.VENENO, -.5f, Calidad.EXCELENTE), new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 600, Calidad.MEDIOCRE), new Producto.Detalle("Casi sin", Nutricion.ENERGIA, 0.007f, Calidad.REGULAR))),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null, 76, Arrays.asList(new Producto.Detalle("Un puñado de", Nutricion.VITAMINAS, 25, Calidad.BUENO)))
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlite = new SqliteParaUltimasVisualizaciones(this, "bdvis", null, 4, productos.length);

        try (SQLiteDatabase db = sqlite.getReadableDatabase()) {
            Cursor cursor = db.rawQuery("select * from visualizacion where fecha is not null", null);
            while (cursor.moveToNext()) {
                productos[cursor.getInt(0)].setUltimaConsulta(cursor.getLong(1));
            }
        }

        lista = findViewById(R.id.recyclerView);
        lista.setAdapter(adaptadorDeProductos = new AdaptadorDeProductos(this, productos));
        lista.setLayoutManager(new LinearLayoutManager(this));

        camarabuttton = (Button)findViewById(R.id.buttonCamara);
        camarabuttton.setOnClickListener(v -> {
            Intent intentoCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentoCamara, REQ_CODE_CAMARA );
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_CAMARA:
                // nada
                break;
            case REQ_CODE_INSPEC:
                if (data != null) {
                    int i = data.getIntExtra("indice", -1);
                    if (i > -1) {
                        long justoAhora = Calendar.getInstance().getTimeInMillis();
                        productos[i].setUltimaConsulta(justoAhora);
                        sqlite.getWritableDatabase().execSQL("update visualizacion set fecha=? where indiceProducto=?", new Object[]{justoAhora, i});
                        adaptadorDeProductos.notifyDataSetChanged();
                    }
                }
                break;
            default: break;
        }
    }
}