package chavales.los.practica1android.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import chavales.los.practica1android.R;
import chavales.los.practica1android.modelo.Producto;

public class InspeccionarProducto extends AppCompatActivity {

    private LinearLayout lista;

    private GoogleMap mapa;
    private MapView mapView;
    private List<MarkerOptions> ubicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccionar_producto);

        mapView = findViewById(R.id.mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);

        // El botón este solo finaliza la actividad
        ((ImageButton) findViewById(R.id.botonAtras)).setOnClickListener(v -> finish());

        // Rellena las vistas con la info del parcelable
        final Producto producto = getIntent().getParcelableExtra("producto");

        final String urlImagen = "prods/" + producto.getImagen();
        FirebaseStorage.getInstance().getReference(urlImagen).getDownloadUrl().addOnSuccessListener(
                r -> Glide.with(this).load(r).into((ImageView) findViewById(R.id.imagenProductoInspeccion))
        );

        //((ImageView) findViewById(R.id.imagenProductoInspeccion)).setImageResource(producto.getImagen());
        ((TextView) findViewById(R.id.textoNombreProductoInsp)).setText(producto.getNombre());
        ((TextView) findViewById(R.id.textoMarcaProductoInsp)).setText(producto.getMarca());
        ((TextView) findViewById(R.id.textoCalidadInsp)).setText(producto.getCalidad().getString());
        ((TextView) findViewById(R.id.textoRatingProductoInsp)).setText(producto.getPuntuacion() + "/100");

        ubicaciones = producto.getUbicaciones();

        // Son listas cortas así que hemos hecho ListView en vez de RecyclerView
        lista = findViewById(R.id.listaValoresNutricionales);

        for (Producto.Detalle detalle : producto.getDetalles()) {

            View vistaDetalle = getLayoutInflater().inflate(R.layout.item_valor_nutricional, lista, false);

            ((ImageView) vistaDetalle.findViewById(R.id.imagenInfo)).setImageResource(detalle.getIngrediente().getImg());
            ((TextView) vistaDetalle.findViewById(R.id.textoTituloInfo)).setText(detalle.getIngrediente().getNombre());
            ((TextView) vistaDetalle.findViewById(R.id.textoCantidadInfo)).setText(detalle.getBajoAltoEn() + " " + detalle.getIngrediente().getNombreUd());
            ((TextView) vistaDetalle.findViewById(R.id.textoCuantiaPor100Info)).setText(detalle.getCantidad() + " " + detalle.getIngrediente().getUdMedicion() + " " + detalle.getCalidad().getEmoji());

            lista.addView(vistaDetalle);
        }

        // Y esto es para comunicar el indice del producto para cuando termine la actividad
        setResult(RESULT_OK, new Intent(this, MainActivity.class).putExtra("indice", getIntent().getIntExtra("indice", -1)));
    }

    private void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);

        mapa.setOnCameraMoveStartedListener(
                i -> mapView.getParent().requestDisallowInterceptTouchEvent(true)
        );

        mapa.setOnCameraIdleListener(
                () -> mapView.getParent().requestDisallowInterceptTouchEvent(false)
        );

        if (ubicaciones != null && ubicaciones.size() > 0) {
            LatLngBounds.Builder llb = new LatLngBounds.Builder();
            for (MarkerOptions m : ubicaciones) {
                mapa.addMarker(m);
                llb.include(m.getPosition());
            }

            int trescientosDipEnPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            int sesentaycuatroDipEnPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, getResources().getDisplayMetrics());
            mapa.animateCamera(CameraUpdateFactory.newLatLngBounds(llb.build(), trescientosDipEnPx, trescientosDipEnPx, sesentaycuatroDipEnPx));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null) mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }
}