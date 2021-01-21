package chavales.los.practica1android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class InspeccionarProducto extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccionar_producto);

        final Producto producto = getIntent().getParcelableExtra("producto");
        ((ImageView) findViewById(R.id.imagenProductoInspeccion)).setImageResource(producto.getImagen());

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
    }
}