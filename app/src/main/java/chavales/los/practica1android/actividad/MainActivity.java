package chavales.los.practica1android.actividad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;

import chavales.los.practica1android.R;
import chavales.los.practica1android.modelo.Calidad;
import chavales.los.practica1android.modelo.Nutricion;
import chavales.los.practica1android.modelo.Producto;
import chavales.los.practica1android.util.AdaptadorDeProductos;
import chavales.los.practica1android.util.SqliteParaUltimasVisualizaciones;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;
    private AdaptadorDeProductos adaptadorDeProductos;
    private FirebaseDatabase db;

    /*pkg*/ static SqliteParaUltimasVisualizaciones sqlite;

    public static final int REQ_CODE_CAMARA = 123;
    public static final int REQ_CODE_INSPEC = 456;

    private static final Producto[] productos = {
            new Producto("Carne de vaca estilo T-Bone", "Grand Western Steak", R.drawable.tbone, Calidad.EXCELENTE, null, 80, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.BUENO, null, 64, Arrays.asList(
                    new Producto.Detalle("Contiene", Nutricion.AZUCAR, 12, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 23, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 9, Calidad.BUENO))),

            new Producto("Aguacate", "Lidl", R.drawable.aguacate, Calidad.EXCELENTE, null, 91, Arrays.asList(
                    new Producto.Detalle("Alta en", Nutricion.CALORIAS, 160, Calidad.BUENO),
                    new Producto.Detalle("Tiene", Nutricion.GRASA, 15, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.COLESTEROL, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Bajo en", Nutricion.SODIO, 7, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 485, Calidad.BUENO),
                    new Producto.Detalle("Bajo en", Nutricion.Hidratos, 9, Calidad.EXCELENTE),
                    new Producto.Detalle("Bajo en", Nutricion.PROTEINAS, 9, Calidad.BUENO))),

            new Producto("Desayuno para campeones", "Lidl", R.drawable.food_platter_2175326_640, Calidad.MEDIOCRE, null, 30, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.VALOR_ENERGETICO, 1200, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Alto en", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.MALO, null, 05, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 343, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, .05f, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.AZUCAR, 46, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.Hidratos, 77, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 69f, Calidad.BUENO))),

            new Producto("Helado", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MEDIOCRE, null, 40, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.AZUCAR, 100, Calidad.BUENO))),

            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.BUENO, null, 70, Arrays.asList(
                    new Producto.Detalle("Contiene", Nutricion.CALORIAS, 884, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 100, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.COLESTEROL, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 2, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.POTASIO, 1, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 0, Calidad.EXCELENTE))),

            new Producto("Cerveza AMBAR", "100%", R.drawable.ambar, Calidad.BUENO, null, 60, Arrays.asList(
                    new Producto.Detalle("Contiene", Nutricion.CALORIAS, 46, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 0, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.VALOR_ENERGETICO, 198, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.FIBRA, 5, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.SAL, .07f, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.Hidratos, 9.2f, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.AZUCAR, 0, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 0, Calidad.BUENO))),

            new Producto("Carne delux Kobe", "Grand Western Steak", R.drawable.kobe, Calidad.EXCELENTE, null, 90, Arrays.asList(
                    new Producto.Detalle("Contiene", Nutricion.VALOR_ENERGETICO, 289, Calidad.REGULAR),
                    new Producto.Detalle("Contiene", Nutricion.Hidratos, .02f, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 56, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.AZUCAR, 0, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.FIBRA, 0, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 17.76f, Calidad.BUENO))),

            new Producto("Nueces de La Rioja", "Mercadona", R.drawable.nueces, Calidad.BUENO, null, 77, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Coca-Cola Normal", "Dia Market", R.drawable.coucacolanormal, Calidad.MALO, null, 30, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.VALOR_ENERGETICO, 357, Calidad.MALO),
                    new Producto.Detalle("Nada de", Nutricion.GRASA, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 8, Calidad.MEDIOCRE),
                    new Producto.Detalle("Contiene", Nutricion.Hidratos, 22, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.AZUCAR, 22, Calidad.MALO))),

            new Producto("Coca-Cola Zero", "Dia Market", R.drawable.coucacolazero, Calidad.MALO, null, 40, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.VALOR_ENERGETICO, 357, Calidad.MALO),
                    new Producto.Detalle("Nada de", Nutricion.GRASA, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.SAL, .02f, Calidad.MEDIOCRE),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.AZUCAR, 22, Calidad.BUENO))),

            new Producto("Aquarius Limón", "Dia Market", R.drawable.aquarius, Calidad.MEDIOCRE, null, 43, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Pasta Penne Prodotto in Italia", "Alcampo", R.drawable.pennepasta, Calidad.BUENO, null, 66, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Vino de Nieves", "La Garnacha", R.drawable.vinonieves, Calidad.BUENO, null, 74, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Lata de Atun Dia ", "Dia ", R.drawable.atunlata, Calidad.BUENO, null, 76, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, 73, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Cafe Colombiano", "Mercadona", R.drawable.cafe, Calidad.EXCELENTE, null, 88, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Bitter Kas lata", "Lidl", R.drawable.bitterkaslata, Calidad.REGULAR, null, 55, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Brocoli", "Lidl", R.drawable.brocolo, Calidad.BUENO, null, 69, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Gallestas Digestive de Chocolate", "Hacendado", R.drawable.digestive, Calidad.BUENO, null, 71, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Ketchup", "Masibon", R.drawable.ketchup2, Calidad.BUENO, null, 57, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Maiz", "Marca", R.drawable.maiz, Calidad.BUENO, null, 73, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Manzanas Fuji", "Erruz", R.drawable.fujimanzanas, Calidad.BUENO, null, 76, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.MALO, null, 0, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Manzanas Golden", "Erruz", R.drawable.manzanasgolden, Calidad.EXCELENTE, null, 79, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Manzanas Verde", "Erruz", R.drawable.greenapple, Calidad.BUENO, null, 66, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Mayonesa Hellmann's", "Lidl", R.drawable.mayonesa, Calidad.REGULAR, null, 49, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Melon fresco", "Dia", R.drawable.melon, Calidad.EXCELENTE, null, 85, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Salmon pack Carrefour", "Carrefour", R.drawable.salmon, Calidad.REGULAR, null, 79, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Mostaza", "Prima", R.drawable.mostaza, Calidad.BUENO, null, 64, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Mortadela", "Alcampo", R.drawable.mortadelacampocold, Calidad.BUENO, null, 67, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.MALO, null, 01, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Pack de Sushi ", "Carrefour", R.drawable.packsushi, Calidad.MALO, null, 23, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Sandia Fresca", "Mercadona", R.drawable.sandiadia, Calidad.EXCELENTE, null, 91, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Solan de Cabras 1.5L", "Dia", R.drawable.solacanraspeque, Calidad.BUENO, null, 70, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Te de Melocoton", "Mercadona", R.drawable.temeloco, Calidad.BUENO, null, 85, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Tortelinni con carne", "Mercadona", R.drawable.tortelinnicarne, Calidad.EXCELENTE, null, 89, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Razimo de Uvas verdes", "Dia", R.drawable.uvasverdes, Calidad.MEDIOCRE, null, 36, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Chocolate con leche", "Nestle", R.drawable.chocolatenestle, Calidad.REGULAR, null, 44, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Chocolate blanco", "Nestle", R.drawable.blancochocolate, Calidad.REGULAR, null, 44, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Pack 4 de Yogures DANONE", "DANONE", R.drawable.danone, Calidad.BUENO, null, 86, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

            new Producto("Pack de lonchas d queso", "HAVARTI", R.drawable.havarti, Calidad.BUENO, null, 80, Arrays.asList(
                    new Producto.Detalle("Alto en", Nutricion.CALORIAS, 247, Calidad.BUENO),
                    new Producto.Detalle("Contiene", Nutricion.GRASA, 16, Calidad.MALO),
                    new Producto.Detalle("Alto en", Nutricion.COLESTEROL, 60, Calidad.MALO),
                    new Producto.Detalle("Contiene", Nutricion.SODIO, 67, Calidad.BUENO),
                    new Producto.Detalle("Alto en", Nutricion.POTASIO, 302, Calidad.BUENO),
                    new Producto.Detalle("Nada de", Nutricion.Hidratos, 0, Calidad.EXCELENTE),
                    new Producto.Detalle("Contiene", Nutricion.PROTEINAS, 24, Calidad.BUENO))),

    };

    /*
    private static final Producto[] productos = {
            new Producto("Manzana con libros", "Apple", R.drawable.apple_256261_640, Calidad.BUENO, null, 73, Arrays.asList(new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 35.75f, Calidad.BUENO), new Producto.Detalle("Hasta arriba de", Nutricion.AZUCAR, 500, Calidad.MALO), new Producto.Detalle("Así asá de", Nutricion.PROTEINAS, 0.05f, Calidad.REGULAR), new Producto.Detalle("Un poquito de", Nutricion.TACOS, .5f, Calidad.BUENO), new Producto.Detalle("Bastantes ", Nutricion.NUECES, 30, Calidad.EXCELENTE))),
            new Producto("Frutas del bosque", "Mercadona", R.drawable.dark_mood_food_2986532_640, Calidad.MEDIOCRE, null, 23, Arrays.asList(new Producto.Detalle("Un par de", Nutricion.NUECES, 2, Calidad.MALO), new Producto.Detalle("Alto en", Nutricion.PROTEINAS, 23, Calidad.EXCELENTE), new Producto.Detalle("Moderada", Nutricion.ENERGIA, 1500, Calidad.BUENO), new Producto.Detalle("Un poco de", Nutricion.SODIO, 9, Calidad.BUENO))),
            new Producto("Ingredientes para pasta", "Samsung", R.drawable.food_1932466_640, Calidad.EXCELENTE, null, 91, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 100, Calidad.MEDIOCRE), new Producto.Detalle("Alto en", Nutricion.SODIO, 1290, Calidad.MALO), new Producto.Detalle("Algo de", Nutricion.AMIANTO, 0.02f, Calidad.BUENO))),
            new Producto("Tabla embutido", "Lidl", R.drawable.food_platter_2175326_640, Calidad.REGULAR, null, 49, Arrays.asList(new Producto.Detalle("Moderados", Nutricion.TACOS, 10, Calidad.EXCELENTE), new Producto.Detalle("Moderadamente", Nutricion.RAMEN, 1.6729f, Calidad.REGULAR), new Producto.Detalle("Moderados", Nutricion.NUECES, .77f, Calidad.MEDIOCRE), new Producto.Detalle("Una cantidad de", Nutricion.GASOLINA, 995, Calidad.BUENO))),
            new Producto("Ositos gominolas", "Haribo", R.drawable.gummibarchen_318362_640, Calidad.BUENO, null, 85, Arrays.asList(new Producto.Detalle("Demasiados", Nutricion.AZUCAR, 5000, Calidad.MALO), new Producto.Detalle("Demasiado pocos", Nutricion.WEBOS, 0, Calidad.REGULAR))),
            new Producto("Elao", "Masibon", R.drawable.ice_cream_cone_1274894_640, Calidad.MALO, null, 9, Arrays.asList(new Producto.Detalle("Pocos", Nutricion.NUECES, 10.05f, Calidad.EXCELENTE), new Producto.Detalle("Blip blup", Nutricion.SODIO, .0043f, Calidad.BUENO), new Producto.Detalle("AAAAAA", Nutricion.WEBOS, 10000, Calidad.REGULAR), new Producto.Detalle("😂😂😂👌👌👌💯", Nutricion.HIERRO, 69, Calidad.EXCELENTE), new Producto.Detalle("10", Nutricion.VITAMINAS, 10, Calidad.REGULAR))),
            new Producto("Aceite de oliva virgen extra", "Marca", R.drawable.olive_oil_968657_640, Calidad.EXCELENTE, null, 97, Arrays.asList(new Producto.Detalle("Alto en", Nutricion.AZUCAR, 333.67f, Calidad.MALO), new Producto.Detalle("Bajo en", Nutricion.VENENO, -.5f, Calidad.EXCELENTE), new Producto.Detalle("Bajo en", Nutricion.FERTILIZANTE, 600, Calidad.MEDIOCRE), new Producto.Detalle("Casi sin", Nutricion.ENERGIA, 0.007f, Calidad.REGULAR))),
            new Producto("Verduras 100%", "100%", R.drawable.vegetables_1085063_640, Calidad.BUENO, null, 76, Arrays.asList(new Producto.Detalle("Un puñado de", Nutricion.VITAMINAS, 25, Calidad.BUENO)))
    };
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean primeroRuneo = db == null;
        db = FirebaseDatabase.getInstance();
        if (primeroRuneo) db.setPersistenceEnabled(true);

        // abrir o crear la base de datos
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

        findViewById(R.id.fabAbrirCamara).setOnClickListener(v -> {
            Intent actividadCamara = new Intent(this, Camara.class);
            startActivity(actividadCamara);
        });

        findViewById(R.id.fabAniadirProducto).setOnClickListener(v -> {
            Intent actividadAniadir = new Intent(this, CrearProducto.class);
            startActivity(actividadAniadir);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_CAMARA:
                // Resultado de la cámara pero no queremos hacer nada
                break;
            case REQ_CODE_INSPEC:
                // Resultado de inspeccionar un producto, actualiza el timestamp de ultima vez visitado
                if (data != null) {
                    int i = data.getIntExtra("indice", -1);
                    if (i > -1) {
                        long justoAhora = Calendar.getInstance().getTimeInMillis();
                        productos[i].setUltimaConsulta(justoAhora);
                        sqlite.getWritableDatabase()
                                .execSQL("update visualizacion set fecha=? where indiceProducto=?",
                                        new Object[]{justoAhora, i});
                        adaptadorDeProductos.notifyDataSetChanged();
                    }
                }
                break;
            default: break;
        }
    }
}