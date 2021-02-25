package chavales.los.practica1android.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import chavales.los.practica1android.R;
import chavales.los.practica1android.modelo.Producto;

public class InspeccionarProducto extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccionar_producto);

        // El botón este solo finaliza la actividad
        ((ImageButton) findViewById(R.id.botonAtras)).setOnClickListener(v -> finish());

        // Rellena las vistas con la info del parcelable
        final Producto producto = getIntent().getParcelableExtra("producto");
        ((ImageView) findViewById(R.id.imagenProductoInspeccion)).setImageResource(producto.getImagen());
        ((TextView) findViewById(R.id.textoNombreProductoInsp)).setText(producto.getNombre());
        ((TextView) findViewById(R.id.textoMarcaProductoInsp)).setText(producto.getMarca());
        ((TextView) findViewById(R.id.textoCalidadInsp)).setText(producto.getCalidad().getString());
        ((TextView) findViewById(R.id.textoRatingProductoInsp)).setText(producto.getPuntuacion() + "/100");

        // Son listas cortas así que hemos hecho ListView en vez de RecyclerView
        lista = findViewById(R.id.listaValoresNutricionales);
        lista.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return producto.getDetalles().size();
            }

            @Override
            public Object getItem(int position) {
                return producto.getDetalles().get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_valor_nutricional, parent, false);
                }
                Producto.Detalle detalle = producto.getDetalles().get(position);
                ((ImageView) convertView.findViewById(R.id.imagenInfo)).setImageResource(detalle.getIngrediente().getImg());
                ((TextView) convertView.findViewById(R.id.textoTituloInfo)).setText(detalle.getIngrediente().getNombre());
                ((TextView) convertView.findViewById(R.id.textoCantidadInfo)).setText(detalle.getBajoAltoEn() + " " + detalle.getIngrediente().getNombreUd());
                ((TextView) convertView.findViewById(R.id.textoCuantiaPor100Info)).setText(detalle.getCantidad() + " " + detalle.getIngrediente().getUdMedicion() + " " + detalle.getCalidad().getEmoji());

                return convertView;
            }
        });

        // Y esto es para comunicar el indice del producto para cuando termine la actividad
        setResult(RESULT_OK, new Intent(this, MainActivity.class).putExtra("indice", getIntent().getIntExtra("indice", -1)));
    }
}