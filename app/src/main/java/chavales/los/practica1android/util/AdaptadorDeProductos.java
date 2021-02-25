package chavales.los.practica1android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import chavales.los.practica1android.actividad.InspeccionarProducto;
import chavales.los.practica1android.actividad.MainActivity;
import chavales.los.practica1android.modelo.Producto;
import chavales.los.practica1android.R;

/**
 * Sacado, principalmente, de https://developer.android.com/guide/topics/ui/layout/recyclerview
 */
public class AdaptadorDeProductos extends RecyclerView.Adapter<AdaptadorDeProductos.ContenedorDeVistas>  {

    private static final int LAYOUT_NORMAL = 0,
                             LAYOUT_INVERTIDO = 1;

    private Producto[] productos;
    private Context context; // El contexto lo necesitamos para llamar a la actividad de inspeccionar producto
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    public AdaptadorDeProductos(Context context, Producto[] productos) {
        this.context = context;
        this.productos = productos;
    }

    /**
     * Devuelve un contenedor de vistas sin datos
     */
    @NonNull
    @Override
    public ContenedorDeVistas onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View vistaDeUnItem;

        switch (viewType) {
            default: case LAYOUT_NORMAL:
                vistaDeUnItem = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_de_lista_de_productos, viewGroup, false);
                break;
            case LAYOUT_INVERTIDO:
                vistaDeUnItem = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_de_lista_de_productos_r, viewGroup, false);
        }

        return new ContenedorDeVistas(vistaDeUnItem);
    }

    /**
     * Este método recibe un contenedor de vistas y lo rellena con datos. Es este
     * método el que recicla los contenedores de vistas para mejorar el rendimiento
     * o lo que sea.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContenedorDeVistas contenedor, int posicion) {

        final Producto producto = productos[posicion];
        contenedor.getImagenProducto().setImageResource(producto.getImagen());
        contenedor.getNombreProducto().setText(producto.getNombre());
        contenedor.getMarcaProducto().setText(producto.getMarca());
        contenedor.getCalidadProducto().setText(producto.getCalidad().getString());

        Long ultimaConsulta = producto.getUltimaConsulta();
        contenedor.getUltimaConsulta().setText("🕑 " + (ultimaConsulta == null
                                                        ? "Nunca"
                                                        : formatoFecha.format(new Date(ultimaConsulta))));

        contenedor.itemView.setOnClickListener(
            (v) -> {
                Intent i = new Intent(context, InspeccionarProducto.class);
                i.putExtra("producto", productos[posicion]);
                i.putExtra("indice", posicion);
                // En el intent hemos puesto el producto, para rellenar la actividad de inspección con
                // todos sus datos, y el indice, para después, al finalizar la actividad, saber de cuál de
                // los productos es la fecha de última visita que hay que actualizar
                ((Activity) context).startActivityForResult(i, MainActivity.REQ_CODE_INSPEC);
            }
        );
    }

    @Override
    public int getItemCount() {
        return productos.length;
    }

    /**
     * Override para elegir entre un layout y otro según la posición
     */
    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? LAYOUT_NORMAL : LAYOUT_INVERTIDO;
    }

    /**
     * Un «contenedor de vistas» representa un elemento de la lista, una de las
     * tarjetas con imagen, nombre de producto, etc; pero sin especificar el
     * contenido.
     */
    public static class ContenedorDeVistas extends RecyclerView.ViewHolder {
        private final ImageView imagenProducto;
        private final TextView nombreProducto;
        private final TextView marcaProducto;
        private final TextView calidadProducto;
        private final TextView ultimaConsulta;

        public ContenedorDeVistas(View vista) {
            super(vista);
            imagenProducto = vista.findViewById(R.id.imagenProducto);
            nombreProducto = vista.findViewById(R.id.textoNombreProducto);
            marcaProducto = vista.findViewById(R.id.textoMarcaProducto);
            calidadProducto = vista.findViewById(R.id.textoCalidadProducto);
            ultimaConsulta = vista.findViewById(R.id.textoUltimaConsulta);
        }

        public ImageView getImagenProducto() {
            return imagenProducto;
        }

        public TextView getNombreProducto() {
            return nombreProducto;
        }

        public TextView getMarcaProducto() {
            return marcaProducto;
        }

        public TextView getCalidadProducto() {
            return calidadProducto;
        }

        public TextView getUltimaConsulta() {
            return ultimaConsulta;
        }
    }
}
